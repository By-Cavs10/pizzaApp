package com.pizza.app.dao;

import com.pizza.app.bo.Produit;
import com.pizza.app.bo.TypeProduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
public class DaoProduit implements IdaoProduit {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    static final RowMapper<Produit> PRODUIT_ROW_MAPPER = new RowMapper<Produit>() {

        public Produit mapRow(ResultSet rs, int rowNum) throws SQLException {
            Produit produit = new Produit();
            produit.setId(rs.getLong("id"));
            produit.setNom(rs.getString("nom"));
            produit.setDescription(rs.getString("description"));
            produit.setPrix(rs.getDouble("prix"));  // Utilisation du setter pour attribuer la valeur
            produit.setImage(rs.getString("image"));
            TypeProduit typeProduit = new TypeProduit();
            typeProduit.setId(rs.getLong("id_type_produit"));
            typeProduit.setLibelle(rs.getString("libelle"));
            produit.setTypeProduit(typeProduit);

            return produit;
        }
    };

    @Override
    public List<Produit> selectProduit() {

        return jdbcTemplate.query("SELECT * FROM produit INNER JOIN type_produit ON PRODUIT.id_type_produit = type_produit.id_type_produit", PRODUIT_ROW_MAPPER);

    }

    @Override
    public Produit selectProduitById(long id) {
        Produit produit = jdbcTemplate.queryForObject("SELECT * FROM produit INNER JOIN type_produit ON PRODUIT.id_type_produit = type_produit.id_type_produit where id=?", PRODUIT_ROW_MAPPER, id);

        return produit;
    }
    @Override
    public boolean deleteProduit(Long id) {
        // Vérifier si l'ID du produit est valide (non nul)
        if (id != null) {
            // Supprimer le produit de la base de données
            String sql = "DELETE FROM produit WHERE id = ?";
            int deleteproduits = jdbcTemplate.update(sql, id);

            // Retourner true si au moins une ligne a été affectée (produit supprimé)
            return deleteproduits > 0;
        }
        // Retourner false si l'ID est nul ou si aucune ligne n'est affectée
        return false;
    }

    @Override
    public void saveProduit(Produit produit) {
        if (produit.getId() != null && selectProduitById(produit.getId()) != null) {
            jdbcTemplate.update("UPDATE produit SET nom = ?, description = ?, prix = ?, image = ?  WHERE id =?",
                    produit.getNom(), produit.getDescription(), produit.getPrix(), produit.getImage(), produit.getId());
            return;
        }
        String sql = "INSERT INTO produit (nom,description,prix,image,id_type_produit) VALUES (:nomProduit,:descriptionProduit," +
                ":prixProduit, :imageProduit,:idTypeProduit)";

//On renseigne les paramètres attendus dans la requête
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
//        mapSqlParameterSource.addValue("idProduit", produit.getId());
        mapSqlParameterSource.addValue("nomProduit", produit.getNom());
        mapSqlParameterSource.addValue("descriptionProduit", produit.getDescription());
        mapSqlParameterSource.addValue("prixProduit", produit.getPrix());
        mapSqlParameterSource.addValue("imageProduit", produit.getImage());
        mapSqlParameterSource.addValue("idTypeProduit",produit.getTypeProduit().getId()) ;


        //Insérer en base un produit
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }

}
