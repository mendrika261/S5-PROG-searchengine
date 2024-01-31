package mg.prog.s5recherche.repository;

import mg.prog.s5recherche.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}