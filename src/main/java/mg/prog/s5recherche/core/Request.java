package mg.prog.s5recherche.core;

import lombok.Data;
import mg.prog.s5recherche.entity.Adjective;
import mg.prog.s5recherche.entity.Category;
import mg.prog.s5recherche.entity.Criteria;
import mg.prog.s5recherche.entity.Product;
import mg.prog.s5recherche.entity.Comparator;

import java.sql.Connection;
import java.util.*;

@Data
public class Request {
    private String query;
    private StringBuilder clearQuery = new StringBuilder();
    private TreeMap<Integer, Adjective> adjectives = new TreeMap<>();
    private TreeMap<Integer, Category> categories = new TreeMap<>();
    private TreeMap<Integer, Criteria> criteria = new TreeMap<>();
    private TreeMap<Integer, Product> products = new TreeMap<>();
    private TreeMap<Integer, Comparator> comparators = new TreeMap<>();
    private String sqlQuery;


    public Request(String query, List<Adjective> adjectives, List<Category> categories,
                   List<Criteria> criteria, List<Product> products, List<Comparator> comparators) {
        setQuery(query);
        generateQueryLabel(adjectives, categories, criteria, products, comparators);
        generateSqlQuery();
    }

    public int[] getIndexes(String word) {
        List<Integer> indexes = new ArrayList<>();
        int index = getQuery().indexOf(word);
        while (index != -1) {
            indexes.add(index);
            index = getQuery().indexOf(word, index + 1);
        }
        return indexes.stream().mapToInt(i -> i).toArray();
    }

    public void generateQueryLabel(List<Adjective> adjectives, List<Category> categories,
                                   List<Criteria> criteria, List<Product> products, List<Comparator> comparators) {
        for (Adjective adjective : adjectives) {
            for (int index : getIndexes(adjective.getName())) {
                this.getAdjectives().put(index, adjective);
            }
        }
        for (Category category : categories) {
            for (int index : getIndexes(category.getName())) {
                this.getCategories().put(index, category);
            }
        }
        for (Criteria criterion : criteria) {
            for (int index : getIndexes(criterion.getName())) {
                this.getCriteria().put(index, criterion);
            }
        }
        for (Product product : products) {
            for (int index : getIndexes(product.getName())) {
                this.getProducts().put(index, product);
            }
        }
        for (Comparator comparator : comparators) {
            for (int index : getIndexes(comparator.getName())) {
                this.getComparators().put(index, comparator);
            }
        }
    }

    public String categoryToSqlQuery() {
        StringBuilder sqlQuery = new StringBuilder();
        for (Category category : getCategories().values()) {
            sqlQuery.append("category_id = ").append(category.getId()).append(" OR ");

            getClearQuery().append("<span class='badge rounded-pill bg-info mx-1'>")
                    .append(category.getName()).append("</span>");
        }
        for (Product product : getProducts().values()) {
            sqlQuery.append("product.id = ").append(product.getId()).append(" OR ");

            getClearQuery().append("<span class='badge rounded-pill bg-primary mx-1'>")
                    .append(product.getName()).append("</span>");
        }

        sqlQuery.delete(sqlQuery.length() - 4, sqlQuery.length());
        return sqlQuery.toString();
    }

    public String criteriaToSql() {
        StringBuilder sqlQuery = new StringBuilder();
        boolean first = true;
        List<Criteria> alreadyPresent = new ArrayList<>();
        for (Integer index : getCriteria().keySet()) {
            if (alreadyPresent.contains(getCriteria().get(index)))
                continue;
            Criteria criterion = getCriteria().get(index);
            Adjective adjective = Utils.getNearIn(index, getAdjectives());
            String sign = Utils.getSign(adjective, criterion);

            sqlQuery
                    .append(first ? "":"COALESCE(NULLIF(")
                    .append(sign).append(criterion.getLabel())
                    .append(first ? "": ",0),1)")
                    .append(" / ");
            first = false;
            alreadyPresent.add(criterion);

            getClearQuery().append("<span class='badge rounded-pill bg-success mx-1'>")
                    .append(adjective.getName()).append(" ").append(criterion.getName())
                    .append("</span>");
        }
        // remove last comma
        sqlQuery.delete(sqlQuery.length() - 3, sqlQuery.length());
        return sqlQuery.toString();
    }

    public String comparatorToSql() {
        StringBuilder sqlQuery = new StringBuilder();
        boolean orParanthesis = false;
        for (int index : getComparators().keySet()) {
            Criteria criterion = Utils.getNearIn(index, getCriteria());
            Comparator comparator = getComparators().get(index);
            String[] values = Utils.getNextValues(comparator.getParameterNumber(), comparator.getParameterType(), index, getQuery());
            String value = String.join(" AND ", values);

            if(comparator.getValue().equals("=")) sqlQuery.append(" (");

            sqlQuery.append(criterion.getLabel()).append(" ")
                    .append(comparator.getValue()).append(" ")
                    .append(value);

            if(orParanthesis) {
                sqlQuery.append(") ");
                orParanthesis = false;
            }

            if(comparator.getValue().equals("=")) orParanthesis = true;

            sqlQuery.append(" ")
                    .append(Utils.getNextOperator(getQuery(), index + value.length()))
                    .append(" ");

            getClearQuery().append("<span class='badge rounded-pill bg-secondary mx-1'>")
                    .append(criterion.getName()).append(" ")
                    .append(comparator.getName()).append(" ")
                    .append(value.replace("AND", "et")).append("</span>");
        }
        if(sqlQuery.toString().strip().endsWith("AND"))
            sqlQuery.delete(sqlQuery.length() - 4, sqlQuery.length());
        if (orParanthesis) sqlQuery.append(")");
        return sqlQuery.toString();
    }

    public void generateSqlQuery() {
        String priority = "id";
        if (!getCriteria().isEmpty()) {
            priority = criteriaToSql();
        }
        StringBuilder sqlQuery = new StringBuilder("SELECT *, c.name AS category_name, ")
                .append(priority)
                .append(" as priority FROM product")
                .append(" JOIN category c ON c.id = product.category_id");

        if (!getCategories().isEmpty() || !getProducts().isEmpty()) {
            sqlQuery.append(" WHERE (").append(categoryToSqlQuery()).append(")");
        }

        if (!getComparators().isEmpty()) {
            sqlQuery.append(getCategories().isEmpty() ? " WHERE " : " AND ").append(comparatorToSql());
        }

        setSqlQuery(sqlQuery.append(" ORDER BY priority").toString());
    }

    public List<Product> getResults(Connection connection) {
        List<Product> results = new ArrayList<>();
        try {
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery(getSqlQuery());
            while (resultSet.next()) {
                var product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQuality(resultSet.getDouble("quality"));
                product.setCategory(new Category(resultSet.getInt("category_id"),
                        resultSet.getString("category_name")));
                results.add(product);
            }
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public void setQuery(String query) {
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("Query must not be empty");
        }
        this.query = query.toLowerCase();
    }

    @Override
    public String toString() {
        return "Query: " + getQuery() + "\n" +
                "SQL Query: " + getSqlQuery() + "\n" +
                "Adjectives: (" + getAdjectives().size() + ")" + getAdjectives() + "\n" +
                "Categories: (" + getCategories().size() + ")" + getCategories() + "\n" +
                "Criteria: (" + getCriteria().size() + ")" + getCriteria() + "\n" +
                "Comparators: (" + getComparators().size() + ")" + getComparators() + "\n" +
                "Products: (" + getProducts().size() + ")" + getProducts() + "\n";
    }
}
