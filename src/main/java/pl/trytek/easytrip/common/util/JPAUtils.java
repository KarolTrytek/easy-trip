package pl.trytek.easytrip.common.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Klasa pomocnicza do budowania listy predykatów
 *
 */
public class JPAUtils {

    private JPAUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void setInOrEqual(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field, List<?> list) {
        if (list != null && !list.isEmpty()) {
            if (list.size() == 1) {
                predicates.add(cb.equal(path.get(field), list.get(0)));
            } else {
                predicates.add(path.get(field).in(list));
            }
        }

    }

    public static void setEqualWithChild(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
                                         String child, String field, Object value) {
        if (value != null) {
            predicates.add(cb.equal(path.get(child).get(field), value));
        }
    }

    public static void setEqual(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field, Object value) {
        if (value != null) {
            predicates.add(cb.equal(path.get(field), value));
        }
    }

    public static void setEqualOrLikeParamWithIgnoreCase(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field, String value) {
        if (!StringUtils.isEmpty(value)) {
            if (value.contains("%")) {
                predicates.add(cb.like(cb.upper(path.get(field)), value.toUpperCase()));
            } else {
                predicates.add(cb.equal(cb.upper(path.get(field)), value.toUpperCase()));
            }
        }

    }
}
