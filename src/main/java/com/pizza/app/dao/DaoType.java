package com.pizza.app.dao;

import com.pizza.app.bo.TypeProduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class DaoType implements IdaoType{


        @Autowired
        private JdbcTemplate jdbcTemplate;
        @Autowired
        private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

        @Override
        public List<TypeProduit> findAll() {

            String sql = "select id_type_produit as id,libelle from type_Produit";

            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<TypeProduit>(TypeProduit.class));
        }

        @Override
        public TypeProduit findById(Long id) {
            String sql = "select id_type_produit as id,libelle from type_Produit where id_type_produit= :idTypeProduit";

            MapSqlParameterSource map = new MapSqlParameterSource();

            map.addValue("idTypeProduit", id);

            return namedParameterJdbcTemplate.queryForObject(sql, map, new BeanPropertyRowMapper<>(TypeProduit.class));
        }
    }





