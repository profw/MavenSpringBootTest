package com.example.cartest.repo;

import com.example.cartest.domain.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Custom car repository to filter by multiple criteria
 */
@Repository
public class CarCustomRepositoryImpl implements CarCustomRepository {
    @Autowired
    EntityManager em;

    /**
     * Find car by one or more criteria with like clause
     *
     * @param filterRules Each rule map contains 'field','op' and 'value' properties.
     *                    op: the filter operation, possible values are:
     *                    contains,equal,notequal,beginwith,endwith,less,lessorequal,greater,greaterorequal.
     * @return List of cars
     */
    @Override
    public List<Car> findCars(List<Map<String, String>> filterRules) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Car> cq = cb.createQuery(Car.class);

        Root<Car> book = cq.from(Car.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filterRules == null || filterRules.isEmpty()) {
            return em.createQuery(cq).getResultList();
        }

        for (Map<String, String> filterRule : filterRules) {
            String field = filterRule.get("field");
            String value = filterRule.get("value");
            String op = filterRule.get("op");
            //TODO: here we can add anoher predicate with other op
            predicates.add(cb.like(book.get(field), "%" + value + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }

}
