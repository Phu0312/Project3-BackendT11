package com.devon.building.repository.custom.impl;

import com.devon.building.entity.Customer;
import com.devon.building.model.request.CustomerSearchRequest;
import com.devon.building.repository.custom.CustomerRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> findAll(CustomerSearchRequest request) {
        StringBuilder sql = new StringBuilder("SELECT DISTINCT c FROM Customer c ");

        if (request.getStaffId() != null) {
            sql.append(" JOIN c.users u ");
        }

        sql.append(" WHERE c.active = true ");

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            sql.append(" AND c.fullName LIKE :fullName ");
        }

        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            sql.append(" AND c.phone LIKE :phone ");
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            sql.append(" AND c.email LIKE :email ");
        }

        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            sql.append(" AND c.status = :status ");
        }

        if (request.getStaffId() != null) {
            sql.append(" AND u.id = :staffId ");
        }

        sql.append(" ORDER BY c.createdDate DESC ");

        Query query = entityManager.createQuery(sql.toString(), Customer.class);

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            query.setParameter("fullName", "%" + request.getFullName() + "%");
        }

        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            query.setParameter("phone", "%" + request.getPhone() + "%");
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            query.setParameter("email", "%" + request.getEmail() + "%");
        }

        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            query.setParameter("status", request.getStatus());
        }

        if (request.getStaffId() != null) {
            query.setParameter("staffId", request.getStaffId());
        }

        return query.getResultList();
    }
}
