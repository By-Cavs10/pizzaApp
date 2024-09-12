package com.pizza.app.dao;

import com.pizza.app.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DAOUtilisateur implements IdaoUtilisateur{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Utilisateur> findAll() {

        String sql = "select id,nom,prenom from utilisateur";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Utilisateur>(Utilisateur.class));
    }

    @Override
    public Utilisateur findById(Long id) {
        String sql = "select id,nom,prenom from Utilisateur where id= :idUtilisateur";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("idUtilisateur", id);

        return namedParameterJdbcTemplate.queryForObject(sql, map, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

}
