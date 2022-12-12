package pl.trytek.easytrip.common.util;

import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import pl.trytek.easytrip.common.exception.EasyTripApiException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;
import static pl.trytek.easytrip.common.exception.ExceptionCode.BAD_REQUEST;

@Data
public class CriteriaQueryUtils {

    private CriteriaQueryUtils(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static CriteriaQueryUtils newInstance(EntityManager entityManager){
        return new CriteriaQueryUtils(entityManager);
    }

    private EntityManager entityManager;

    public static List<Order> getOrders(Pageable pageable, Map<String, String> sortfieldAlias, From<?, ?> root, CriteriaBuilder cb) {
        return getOrders(pageable, sortfieldAlias, root, cb, null); }

    public static List<Order> getOrders(Pageable pageable, Map<String, String> sortfieldAlias, From<?, ?> root, CriteriaBuilder cb, List<String> skipped) {
        if (nonNull(pageable) && pageable.getSort().isSorted()) {
            List<Order> orders = new ArrayList<>();
            for (Sort.Order order : pageable.getSort()) {
                if (sortfieldAlias.containsKey(order.getProperty())) {
                    if (sortfieldAlias.get(order.getProperty()).equals(order.getProperty())) {
                        orders.addAll(QueryUtils.toOrders(Sort.by(order), root, cb));
                    } else {
                        orders.addAll(QueryUtils.toOrders(Sort.by(order.getDirection(), sortfieldAlias.get(order.getProperty())), root, cb));
                    }
                } else if(!isEmpty(skipped) && skipped.contains(order.getProperty())){
                    continue; //Obsługa sortowania manualna poprzez dodanie do listy order zbudowanego cryterium
                } else
                    throw new EasyTripApiException(BAD_REQUEST, "Błędne parametry sortowania.");
            }
            return orders;
        } else
            return Collections.emptyList();
    }

    public <T> List<T> getResultList(CriteriaQuery<T> cq, List<Order> orders, Predicate predicate, Pageable pageable) {

        if (!isEmpty(orders))
            cq.orderBy(orders);

        return getResultList(cq, predicate, pageable);
    }

    private <T> List<T> getResultList(CriteriaQuery<T> cq, Predicate predicate, Pageable pageable) {
        cq.distinct(true).where(predicate);

        TypedQuery<T> query = entityManager.createQuery(cq);
        if(pageable.isPaged()){
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();
    }

    public <T> long getCount(final CriteriaBuilder cb, final Predicate predicate, Root<T> root, Class<T> resultClass, int resultSize, Pageable pageable) {
        return getCount(cb, predicate, root, resultClass, resultSize, pageable, false);
    }

    private <T> long getCount(CriteriaBuilder cb, Predicate predicate, Root<T> root, Class<T> resultClass, int resultSize, Pageable pageable, boolean distinct) {
        if (resultSize < pageable.getPageSize() && pageable.getPageNumber() == 0) {
            return resultSize;
        } else {
            CriteriaQuery<Long> query = createCountQuery(cb, predicate, root, resultClass, distinct);
            return this.entityManager.createQuery(query).getSingleResult();
        }
    }

    private <T> CriteriaQuery<Long> createCountQuery(final CriteriaBuilder cb,
                                                     final Predicate predicate, final Root<T> root, Class<T> resultClass, boolean distinct) {

        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        final Root<T> countRoot = countQuery.from(resultClass);

        doJoins(root.getJoins(), countRoot);
        doJoinsOnFetches(root.getFetches(), countRoot);

        if (distinct) {
            countQuery.select(cb.countDistinct(countRoot));
        } else {
            countQuery.select(cb.count(countRoot));
        }

        countQuery.where(predicate);
        countRoot.alias(root.getAlias());
        return countQuery;
    }

    @SuppressWarnings("unchecked")
    private void doJoinsOnFetches(Set<? extends Fetch<?, ?>> joins, Root<?> root) {
        doJoins((Set<? extends Join<?, ?>>) joins, root);
    }

    private void doJoins(Set<? extends Join<?, ?>> joins, Root<?> countRoot) {
        for (Join<?, ?> join : joins) {
            Join<?, ?> joined = countRoot.join(join.getAttribute().getName(), join.getJoinType());
            Optional.ofNullable(join.getOn()).ifPresent(joined::on);
            joined.alias(join.getAlias());
            doJoins(join.getJoins(), joined);
        }
    }

    private void doJoins(Set<? extends Join<?, ?>> joins, Join<?, ?> root) {
        for (Join<?, ?> join : joins) {
            Join<?, ?> joined = root.join(join.getAttribute().getName(), join.getJoinType());
            Optional.ofNullable(join.getOn()).ifPresent(joined::on);
            joined.alias(join.getAlias());
            doJoins(join.getJoins(), joined);
        }
    }
}
