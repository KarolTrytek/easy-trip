package pl.trytek.easytrip.data.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.trytek.easytrip.data.domain.Attraction;

import java.util.List;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long>,
		JpaSpecificationExecutor<Attraction> {

	Attraction findOneById (Long id);

	@NonNull List<Attraction> findAll();
}
