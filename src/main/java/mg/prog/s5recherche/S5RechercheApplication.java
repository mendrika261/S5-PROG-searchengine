package mg.prog.s5recherche;

import mg.prog.s5recherche.core.Request;
import mg.prog.s5recherche.entity.*;
import mg.prog.s5recherche.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

@SpringBootApplication
public class S5RechercheApplication implements CommandLineRunner {
    private final AdjectiveRepository adjectiveRepository;
    private final CategoryRepository categoryRepository;
    private final CriteriaRepository criteriaRepository;
    private final ProductRepository productRepository;
    private final DataSource dataSource;
    private final ComparatorRepository comparatorRepository;

    public S5RechercheApplication(AdjectiveRepository adjectiveRepository, CategoryRepository categoryRepository,
                                  CriteriaRepository criteriaRepository, ProductRepository productRepository, DataSource dataSource,
                                  ComparatorRepository comparatorRepository) {
        this.adjectiveRepository = adjectiveRepository;
        this.categoryRepository = categoryRepository;
        this.criteriaRepository = criteriaRepository;
        this.productRepository = productRepository;
        this.dataSource = dataSource;
        this.comparatorRepository = comparatorRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(S5RechercheApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // String query = "le fruit le plus chère"
        String query = "le meilleur rapport qualité prix de la catégorie fruit avec prix entre 2000 et 5000";

        List<Adjective> adjectives = adjectiveRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        List<Criteria> criteria = criteriaRepository.getList();
        List<Product> products = productRepository.findAll();
        List<Comparator> comparators = comparatorRepository.findAll();

        Request request = new Request(query, adjectives, categories, criteria, products, comparators);

        Connection connection = dataSource.getConnection();
        System.out.println(request.getResults(connection));
        connection.close();

        System.out.println(request);
    }
}

