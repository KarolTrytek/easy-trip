package pl.trytek.easytrip.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.trytek.easytrip.data.domain.Attraction;
import pl.trytek.easytrip.data.domain.Favorite;
import pl.trytek.easytrip.data.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<Favorite, Long>,
        JpaSpecificationExecutor<Favorite> {

     Favorite findByIdAndUserLogin(Long id, String login);
     Favorite findByAttractionIdAndUserLogin(Long id, String login);

     List<Favorite> findAllByAttractionId(Long attractionId);

    Optional<Favorite> findByAttractionAndUser(Attraction attraction, User user);

    List<Favorite> findAllByUserLogin(String login);
}
