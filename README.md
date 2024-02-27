# Overview 🔮

Make search over products using natural language [french only].



https://github.com/mendrika261/S5-PROG-searchengine/assets/97053149/ff8b3b20-bafe-4d2f-bb89-13bb4ed8c47a



# How it works ℹ️
Keywords are extracted from the query, labeled and translated into SQL queries.

### Example ⤵️
#### Query:
```
top 2 meilleur qualité de légumes et fruit supérieure ou égale à 7 avec un prix entre 3000 et 5000
```
#### Keywords extracted: (*key is index of the word in the query)
```
Adjectives: (1)
    {
        6=Adjective{id=0, name='meilleur', effect='+'}
    }
Categories: (2)
    {
        26=Category{id=1, name='légume'},
        37=Category{id=0, name='fruit'}
    }
Criteria: (2)
    {
        15=Criteria{id=2, name='qualité', label='quality', typeCriteria='numeric', bestValue='+'},
        75=Criteria{id=0, name='prix', label='price', typeCriteria='numeric', bestValue='-'}
    }
Comparators: (3)
    {
        43=Comparator{id=0, name='supérieur', value='>', parameterType='numeric', parameterNumber=1},
        57=Comparator{id=2, name='égal', value='=', parameterType='numeric', parameterNumber=1},
        80=Comparator{id=1, name='entre', value='BETWEEN', parameterType='numeric', parameterNumber=2}
    }
Products: (0){}
```
#### SQL Query:
```sql
SELECT *, c.name AS category_name, +(+quality / COALESCE(NULLIF(+price,0),1))  as priority FROM product p JOIN category c ON c.id = p.category_id WHERE (category_id = 1 OR category_id = 0) AND quality > 7 OR  (price = 7 AND price BETWEEN 3000 AND 5000) ORDER BY priority DESC LIMIT 2
```

# How to use it 🛠
> Required java >= 17 and maven installed
- After cloning the repository, run the following command to install the required packages:
```bash
mvn clean install
```
- To run the application, run the following command:
```bash
mvn spring-boot:run
```
- Insert rows in `data.sql` into the database
- The application will be available at the following address:`
http://localhost:8080/search
`
