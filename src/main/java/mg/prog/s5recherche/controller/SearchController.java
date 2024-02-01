package mg.prog.s5recherche.controller;

import mg.prog.s5recherche.core.Request;
import mg.prog.s5recherche.entity.*;
import mg.prog.s5recherche.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

@Controller
public class SearchController {
    private final AdjectiveRepository adjectiveRepository;
    private final CategoryRepository categoryRepository;
    private final CriteriaRepository criteriaRepository;
    private final ProductRepository productRepository;
    private final DataSource dataSource;
    private final ComparatorRepository comparatorRepository;
    private final CardinalRepository cardinalRepository;

    public SearchController(AdjectiveRepository adjectiveRepository, CategoryRepository categoryRepository, CriteriaRepository criteriaRepository, ProductRepository productRepository, DataSource dataSource, ComparatorRepository comparatorRepository, CardinalRepository cardinalRepository) {
        this.adjectiveRepository = adjectiveRepository;
        this.categoryRepository = categoryRepository;
        this.criteriaRepository = criteriaRepository;
        this.productRepository = productRepository;
        this.dataSource = dataSource;
        this.comparatorRepository = comparatorRepository;
        this.cardinalRepository = cardinalRepository;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(defaultValue = "") String query) {
        ModelAndView modelAndView = new ModelAndView("search");

        if(!query.isEmpty()) {
            try {
                List<Adjective> adjectives = adjectiveRepository.findAll();
                List<Category> categories = categoryRepository.findAll();
                List<Criteria> criteria = criteriaRepository.getList();
                List<Product> products = productRepository.findAll();
                List<Comparator> comparators = comparatorRepository.findAll();
                List<Cardinal> cardinals = cardinalRepository.findAll();

                Request request = new Request(query, adjectives, categories, criteria, products, comparators, cardinals);
                System.out.println(request);

                Connection connection = dataSource.getConnection();
                modelAndView.addObject("results", request.getResults(connection));
                modelAndView.addObject("query", query);
                modelAndView.addObject("resultFor", request.getClearQuery());
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return modelAndView;
    }
}
