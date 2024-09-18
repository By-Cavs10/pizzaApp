package com.pizza.app.dao;

import com.pizza.app.bo.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("mysql")
@Component
public class DAOOrderMySQL implements IDAOOrder {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Commande> getAllCommandes() {
        String sql = "SELECT * FROM COMMANDE";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Commande.class));
    }

    public void updateEtatCommande(Long commandeId, Long etatId) {
        String sql = "UPDATE COMMANDE SET ETAT_id_etat = ? WHERE id_commande = ?";
        jdbcTemplate.update(sql, etatId, commandeId);
    }
}

