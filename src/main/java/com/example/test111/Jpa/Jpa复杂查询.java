//package com.example.test111.Jpa;
//
//import static com.google.common.collect.Lists.newArrayList;
//import static java.util.Objects.nonNull;
//import static org.apache.commons.lang3.StringUtils.isNotBlank;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import javax.persistence.criteria.CriteriaBuilder.In;
//import javax.persistence.criteria.Predicate;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.util.CollectionUtils;
//
//public class Jpa复杂查询 {
//  public Page<CustomerListResponse> getCustomers(CustomerListRequest request, Pageable pageable) {
//    Specification<CustomerView> specification = (root, query, criteriaBuilder) -> {
//      List<Predicate> predicates = newArrayList();
//      String email = request.getEmail();
//      if (isNotBlank(email)) {
//        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(CustomerView_.email)), "%" + email.toLowerCase() + "%"));
//      }
//      String name = request.getName();
//      if (isNotBlank(name)) {
//        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(CustomerView_.name)),
//            "%" + name.toLowerCase() + "%"));
//      }
//      String auth0Id = request.getAuth0Id();
//      if (isNotBlank(auth0Id)) {
//        predicates.add(criteriaBuilder.like(root.get(CustomerView_.auth0Id),
//            "%" + auth0Id + "%"));
//      }
//      List<Connection> connections = request.getConnections();
//      if (!CollectionUtils.isEmpty(connections)) {
//        In<Connection> inClause = criteriaBuilder.in(root.get(CustomerView_.connection));
//        connections.forEach(inClause::value);
//        predicates.add(inClause);
//      }
//      if (nonNull(request.getCreatedDateFrom())) {
//        LocalDateTime createdDateFrom = DateConvertUtil
//            .toLocalDateTime(request.getCreatedDateFrom());
//        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(CustomerView_.createdDate),
//            createdDateFrom));
//      }
//      if (nonNull(request.getCreatedDateTo())) {
//        LocalDateTime createdDateTo = DateConvertUtil.toLocalDateTime(request.getCreatedDateTo());
//        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(CustomerView_.createdDate),
//            createdDateTo));
//      }
//      if (nonNull(request.getIsB2b())) {
//        predicates.add(criteriaBuilder.equal(root.get(CustomerView_.IS_B2B), request.getIsB2b()));
//      }
//      if (nonNull(request.getIsB2c())) {
//        predicates.add(criteriaBuilder.equal(root.get(CustomerView_.IS_B2C), request.getIsB2c()));
//      }
//      if (nonNull(request.getOrganizationId())) {
//        predicates.add(criteriaBuilder
//            .equal(root.get(CustomerView_.organizationId), request.getOrganizationId()));
//      }
//      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//    };
//    return customerViewRepository.findAll(specification, pageable)
//        .map(customerMapper::toCustomerResponse);
//
//  }
//}
