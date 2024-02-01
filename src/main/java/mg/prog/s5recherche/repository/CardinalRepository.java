package mg.prog.s5recherche.repository;

import mg.prog.s5recherche.entity.Cardinal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardinalRepository extends JpaRepository<Cardinal, Integer> {
}