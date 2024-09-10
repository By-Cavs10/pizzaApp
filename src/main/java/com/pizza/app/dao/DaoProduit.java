package com.pizza.app.dao;

import com.pizza.app.bo.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
public class DaoProduit implements IdaoProduit {

    @Autowired
    JdbcTemplate jdbcTemplate;
    static final RowMapper<Produit> PRODUIT_ROW_MAPPER = new RowMapper<Produit>() {

        public Produit mapRow(ResultSet rs, int rowNum) throws SQLException {
            Produit produit = new Produit();
            produit.setId(rs.getLong("id"));
            produit.setNom(rs.getString("nom"));
            produit.setDescription(rs.getString("description"));
            produit.setPrix(rs.getDouble("prix"));  // Utilisation du setter pour attribuer la valeur
            produit.setImage(rs.getString("image"));
            return produit;
        }
    };

    @Override
    public List<Produit> selectProduit() {

        return jdbcTemplate.query("SELECT * FROM produit", PRODUIT_ROW_MAPPER);

    }

    @Override
    public Produit selectProduitById(long id) {
        List<Produit> produits = jdbcTemplate.query("SELECT* FROM produit WHERE id = ?", PRODUIT_ROW_MAPPER, id);
        if (produits.size() == 0) {
            return null;
        }
        return produits.get(0);
    }

    @Override
    public void saveProduit(Produit produit) {
        if (produit.getId() != null && selectProduitById(produit.getId()) != null) {
            jdbcTemplate.update("UPDATE produit SET nom = ?, description = ?, prix = ?, image = ? WHERE id =?",
                    produit.getNom(), produit.getDescription(), produit.getPrix(), produit.getImage(), produit.getId());
            return;
        }

    }
}