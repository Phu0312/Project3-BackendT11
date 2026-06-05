package com.devon.building.repository.custom.impl;

import com.devon.building.builder.BuildingSearchBuilder;
import com.devon.building.entity.Building;
import com.devon.building.repository.custom.BuildingRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private void joinCondition(BuildingSearchBuilder builder, StringBuilder sql) {
        if (builder.getAreaFrom() != null || builder.getAreaTo() != null) {
            sql.append(" LEFT JOIN rentarea ra ON b.id = ra.buildingid ");
        }

        if (builder.getStaffId() != null) {
            sql.append(" LEFT JOIN assignmentbuilding ab ON b.id = ab.buildingid ");
        }
    }

    private void whereCondition(BuildingSearchBuilder builder, StringBuilder sql) {
        try {
            Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();

            for (Field field : fields) {
                String key = field.getName();

                if (!key.equals("staffId")
                        && !key.equals("typeCodes")
                        && !key.startsWith("area")
                        && !key.startsWith("rentPrice")) {

                    field.setAccessible(true);
                    Object value = field.get(builder);

                    if (value != null && !"".equals(value.toString().trim())) {
                        if (value instanceof Number) {
                            sql.append(" AND b.")
                                    .append(key.toLowerCase())
                                    .append(" = ")
                                    .append(value)
                                    .append(" ");
                        } else {
                            sql.append(" AND b.")
                                    .append(key.toLowerCase())
                                    .append(" LIKE '%")
                                    .append(value)
                                    .append("%' ");
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (builder.getRentPriceFrom() != null) {
            sql.append(" AND b.rentprice >= ").append(builder.getRentPriceFrom());
        }

        if (builder.getRentPriceTo() != null) {
            sql.append(" AND b.rentprice <= ").append(builder.getRentPriceTo());
        }

        if (builder.getAreaFrom() != null) {
            sql.append(" AND ra.value >= ").append(builder.getAreaFrom());
        }

        if (builder.getAreaTo() != null) {
            sql.append(" AND ra.value <= ").append(builder.getAreaTo());
        }

        if (builder.getStaffId() != null) {
            sql.append(" AND ab.staffid = ").append(builder.getStaffId());
        }

        if (builder.getTypeCodes() != null && !builder.getTypeCodes().isEmpty()) {
            sql.append(" AND (");
            sql.append(
                    builder.getTypeCodes().stream()
                            .map(code -> "b.type LIKE '%" + code + "%'")
                            .collect(Collectors.joining(" OR "))
            );
            sql.append(")");
        }
    }

    @Override
    public List<Building> search(BuildingSearchBuilder builder) {
        StringBuilder sql = new StringBuilder("SELECT DISTINCT b.* FROM building b ");

        joinCondition(builder, sql);
        sql.append(" WHERE 1=1 ");
        whereCondition(builder, sql);

        Query query = entityManager.createNativeQuery(sql.toString(), Building.class);
        return query.getResultList();
    }
}