package mg.prog.s5recherche.repository;

import mg.prog.s5recherche.entity.Adjective;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdjectiveRepository extends JpaRepository<Adjective, Integer> {
}