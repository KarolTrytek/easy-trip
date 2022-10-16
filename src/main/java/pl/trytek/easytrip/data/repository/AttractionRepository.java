package pl.trytek.easytrip.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.trytek.easytrip.data.domain.Attraction;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long>,
		JpaSpecificationExecutor<Attraction> {

	Optional<Attraction> findOneById (Long id);

	List<Attraction> findAll();
}
