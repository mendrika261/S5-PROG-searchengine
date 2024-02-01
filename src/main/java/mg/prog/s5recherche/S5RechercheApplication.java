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

    }
}

