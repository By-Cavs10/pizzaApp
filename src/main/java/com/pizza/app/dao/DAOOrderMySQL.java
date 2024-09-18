package com.pizza.app.dao;

import com.pizza.app.bo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.relational.core.sql.Join.JoinType.JOIN;

@Profile("mysql")
@Component
public class DAOOrderMySQL implements IDAOOrder {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    final RowMapper<Commande> COMMANDE_ROW_MAPPER = new RowMapper<Commande>() {

        @Override
        public Commande mapRow(ResultSet rs, int rowNum) throws SQLException {
            Commande commande = new Commande();
            commande.setId(rs.getLong("id_commande"));
            commande.setDate(rs.getString("date"));
            commande.setHeure(rs.getString("heure"));
            commande.setLivraison(rs.getBoolean("livraison"));
            commande.setPrixTotal(rs.getDouble("prix_total"));
            commande.setMontantPaye(rs.getDouble("montant_paye"));

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(rs.getLong("id_utilisateur"));
            commande.setUtilisateur(utilisateur);

            EtatCommande etatCommande = new EtatCommande();
            etatCommande.setId(rs.getLong("etat_id"));  // Utilisez l'alias "etat_id"
            etatCommande.setLibelle(rs.getString("etat_libelle"));  // Utilisez l'alias "etat_libelle"
            commande.setEtatCommande(etatCommande);

//            List<DetailCommande> detailCommandes = new ArrayList<>();
//            DetailCommande detailCommande = new DetailCommande();
//            detailCommande.setQuantite(rs.getLong("quantite"));
//
//            Produit produit = new Produit();
//            produit.setId(rs.getLong("PRODUIT_id_produit"));
//            produit.setNom(rs.getString("nom"));
//            produit.setPrix(rs.getDouble("prix"));
//            detailCommande.setProduit(produit);
//
//            detailCommandes.add(detailCommande);
//            commande.setDetailCommandes(detailCommandes);

            return commande;
        }

    };
    final RowMapper<DetailCommande> DETAILCOMMANDE_ROW_MAPPER = new RowMapper<DetailCommande>() {

        @Override
        public DetailCommande mapRow(ResultSet rs, int rowNum) throws SQLException {
            DetailCommande detailCommande = new DetailCommande();
            detailCommande.setQuantite(rs.getLong("quantite"));

            Produit produit = new Produit();
            produit.setId(rs.getLong("produit_id_produit"));
            produit.setNom(rs.getString("produit_nom"));
            produit.setDescription(rs.getString("produit_description"));
            produit.setPrix(rs.getDouble("produit_prix"));
            produit.setImage(rs.getString("produit_image"));

            TypeProduit typeProduit = new TypeProduit();
            typeProduit.setId(rs.getLong("type_produit_id"));
            typeProduit.setLibelle(rs.getString("type_produit"));

            produit.setTypeProduit(typeProduit);
            detailCommande.setProduit(produit);

            return detailCommande;
        }

    };

        public List<Commande> getAllCommandes() {
            String sql = "SELECT c.*, e.id_etat as etat_id, e.libelle as etat_libelle\n" +
                    "FROM COMMANDE c \n" +
                    "JOIN ETAT e ON c.ETAT_id_etat = e.id_etat \n" +
                    " ";

            return jdbcTemplate.query(sql, COMMANDE_ROW_MAPPER);
        }
        public List<DetailCommande> getAllDetailByIdCommandes(Long  idCommande) {
            String sql = "SELECT dc.quantite, " +
                    "dc.produit_id_produit, " +
                    "p.nom AS produit_nom, " +
                    "p.description AS produit_description, " +
                    "p.prix AS produit_prix, " +
                    "p.image AS produit_image, " +
                    "tp.id_type_produit AS type_produit_id, " +
                    "tp.libelle AS type_produit " +
                    "FROM detail_commande dc " +
                    "JOIN produit p ON dc.produit_id_produit = p.id " +
                    "JOIN TYPE_PRODUIT tp ON p.id_type_produit = tp.id_type_produit " +
                    "WHERE COMMANDE_id_commande = ?";

            return jdbcTemplate.query(sql, new Object[]{idCommande}, DETAILCOMMANDE_ROW_MAPPER);
        }

    public void updateEtatCommande(Long commandeId, Long etatId) {
        String sql = "UPDATE COMMANDE SET ETAT_id_etat = ? WHERE id_commande = ?";
        jdbcTemplate.update(sql, etatId, commandeId);
    }

}

