package pl.trytek.easytrip.api.attraction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.trytek.easytrip.api.attraction.dto.AttractionCriteriaDto;
import pl.trytek.easytrip.api.attraction.dto.AttractionDetailsDto;
import pl.trytek.easytrip.api.attraction.dto.AttractionDto;
import pl.trytek.easytrip.common.util.JPAUtils;
import pl.trytek.easytrip.data.domain.Attraction;
import pl.trytek.easytrip.data.domain.Attraction_;
import pl.trytek.easytrip.data.repository.AttractionRepository;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttractionService {

    private final EntityManager entityManager;

    private final AttractionRepository attractionRepository;
    private final AttractionMapper mapper;

    @Transactional
    public List<AttractionDto> getAttractions() {

        return mapper.map(attractionRepository.findAll());
    }

    public Page<AttractionDto> getAttractionList(AttractionCriteriaDto criteria) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = cb.createTupleQuery();
        Root<Attraction> root = criteriaQuery.from(Attraction.class);

        criteriaQuery.multiselect(
                root.get(Attraction_.ID),
                root.get(Attraction_.NAME),
                root.get(Attraction_.TYPE)

        );

        List<Predicate> predicateList = new ArrayList<>();

        if (!isEmpty(criteria.types())) {
            JPAUtils.setInOrEqual(root,cb, predicateList, Attraction_.TYPE, criteria.types());
        }

        JPAUtils.setEqualOrLikeParamWithIgnoreCase(root, cb, predicateList, Attraction_.NAME, criteria.name());

        Predicate predicates = cb.and(predicateList.toArray(new Predicate[0]));

        criteriaQuery.where(predicates);

        Map<String, String> sortfieldAlias = new HashMap<>();
        sortfieldAlias.put(Attraction_.NAME, Attraction_.NAME);

        return null;
    }

    public AttractionDetailsDto getAttractionDetails(Long id) {
        return mapper.mapForDetails(attractionRepository.findOneById(id));

    }

}
