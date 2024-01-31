package mg.prog.s5recherche.repository;

import mg.prog.s5recherche.entity.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CriteriaRepository extends JpaRepository<Criteria, Integer> {
    @Query("SELECT c FROM Criteria c ORDER BY length(c.name) DESC")
    List<Criteria> getList();
}