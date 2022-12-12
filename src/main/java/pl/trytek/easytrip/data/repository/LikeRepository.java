package pl.trytek.easytrip.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.trytek.easytrip.data.domain.Attraction;
import pl.trytek.easytrip.data.domain.Like;
import pl.trytek.easytrip.data.domain.User;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>,
        JpaSpecificationExecutor<Like> {

    Optional<Like> findByAttractionAndUser(Attraction attraction, User user);
}
