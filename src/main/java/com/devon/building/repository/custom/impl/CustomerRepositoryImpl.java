package com.devon.building.repository.custom.impl;

import com.devon.building.builder.CustomerSearchBuilder;
import com.devon.building.constant.SystemConstant;
import com.devon.building.entity.Customer;
import com.devon.building.repository.custom.CustomerRepositoryCustom;
import com.devon.building.utils.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private void joinCondition(CustomerSearchBuilder builder, StringBuilder sql) {
        if (builder.getStaffId() != null || SecurityUtils.getAuthorities().contains(SystemConstant.STAFF_ROLE)) {
            sql.append(" INNER JOIN assignmentcustomer ac ON c.id = ac.customerid ");
        }

        if (SecurityUtils.getAuthorities().contains(SystemConstant.STAFF_ROLE)) {
            sql.append(" INNER JOIN user u ON ac.staffid = u.id ");
        }
    }

    private void whereCondition(CustomerSearchBuilder builder, StringBuilder sql) {
        sql.append(" AND c.is_active = true ");

        if (builder.getFullName() != null && !builder.getFullName().isBlank()) {
            sql.append(" AND c.fullname LIKE '%").append(builder.getFullName()).append("%' ");
        }

        if (builder.getPhone() != null && !builder.getPhone().isBlank()) {
            sql.append(" AND c.phone LIKE '%").append(builder.getPhone()).append("%' ");
        }

        if (builder.getEmail() != null && !builder.getEmail().isBlank()) {
            sql.append(" AND c.email LIKE '%").append(builder.getEmail()).append("%' ");
        }

        if (builder.getStatus() != null && !builder.getStatus().isBlank()) {
            sql.append(" AND c.status = '").append(builder.getStatus()).append("' ");
        }

        if (SecurityUtils.getAuthorities().contains(SystemConstant.STAFF_ROLE)) {
            sql.append(" AND u.username = '")
                    .append(SecurityUtils.getCurrentUser().getUsername())
                    .append("' ");
        } else if (builder.getStaffId() != null) {
            sql.append(" AND ac.staffid = ")
                    .append(builder.getStaffId())
                    .append(" ");
        }
    }

    @Override
    public List<Customer> search(CustomerSearchBuilder builder) {
        StringBuilder sql = new StringBuilder("SELECT DISTINCT c.* FROM customer c ");

        joinCondition(builder, sql);
        sql.append(" WHERE 1=1 ");
        whereCondition(builder, sql);
        sql.append(" ORDER BY c.createddate DESC ");

        Query query = entityManager.createNativeQuery(sql.toString(), Customer.class);
        return query.getResultList();
    }
}