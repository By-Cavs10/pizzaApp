package com.pizza.app.dao;

import com.pizza.app.bo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Profile("mysql")
@Component
public class DAOBasketMySQL implements IDAOBasket {

    @Autowired
    private IdaoProduit daoProduit;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    /*
    Le code qui permet de savoir comment convertir/mapper un résultat en SQL en

    Comment mppaer un résultat SQL en Aliment
     */
    public DAOBasketMySQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    static final RowMapper<Commande> COMMANDE_ROW_MAPPER = new RowMapper<Commande>() {

        @Override
        public Commande mapRow(ResultSet rs, int rowNum) throws SQLException {
            Commande commande = new Commande();

            commande.setId(rs.getLong("id_commande"));
            commande.setDate(rs.getString("date"));
            commande.setHeure(rs.getString("heure"));
            commande.setLivraison(rs.getString("livraison"));
            commande.setPrixTotal(rs.getDouble("prix_total"));
            commande.setMontantPaye(rs.getDouble("montant_paye"));
            EtatCommande etatCommande = new EtatCommande();
            etatCommande.setId(rs.getLong("id_etat"));
            etatCommande.setLibelle(rs.getString("libelle"));
            commande.setEtatCommande(etatCommande);


            return commande;
        }
    };

     final RowMapper<DetailCommande> DETAILCOMMANDE_ROW_MAPPER = new RowMapper<DetailCommande>() {

        @Override
        public DetailCommande mapRow(ResultSet rs, int rowNum) throws SQLException {
            DetailCommande detailCommande = new DetailCommande();

            detailCommande.setQuantite(rs.getLong(("quantite")));
            Produit produit = new Produit();
            produit.setId(rs.getLong("produit_id_produit"));
            produit.setNom(rs.getString("produit_nom"));
            produit.setPrix(rs.getDouble("produit_prix"));
            produit.setImage(rs.getString("produit_image"));
            produit.setDescription(rs.getString("produit_description"));
//            Produit produit = daoProduit.selectProduitById(rs.getLong("produit_id_produit"));
//            detailCommande.setProduit(produit);
//            TypeProduit typeProduit = new TypeProduit();
//            typeProduit.setLibelle(rs.getString("type_produit"));




            return detailCommande;
        };

        ;

    };

    @Override
    public List<Commande> selectCommande() {

        return jdbcTemplate.query("SELECT * FROM commande INNER JOIN etat ON COMMANDE.ETAT_id_etat = etat.id_etat", COMMANDE_ROW_MAPPER);

    }

    @Override
    public Commande selectCommandeById(Long id) {
        List<Commande> commandes = jdbcTemplate.query("SELECT * FROM commande WHERE id_commande = ?", COMMANDE_ROW_MAPPER, id);

        //Si on trouve aucun élément on retourne null
        if (commandes.size() == 0) {
            return null;
        }

        //Retourner le premier élément
        return commandes.get(0);
    }
// Etat commande association avec Commande

    @Override
    public List<EtatCommande> findAll() {
        String sql = "select id_etat as id, libelle from etat";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(EtatCommande.class));
    }

    @Override
    public EtatCommande findById(Long id) {
        String sql = "select id_etat as id, libelle from etat WHERE id_etat = :idetat";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("idetat", id);

        return namedParameterJdbcTemplate.queryForObject(sql, map, new BeanPropertyRowMapper<>(EtatCommande.class));

    }

    @Override
    public List<DetailCommande> findAllDetailCommande() {
        String sql = "SELECT " +
                "    dc.quantite, " +
                "    dc.produit_id_produit, " +
                "    p.nom AS produit_nom, " +
                "    p.description AS produit_description, " +
                "    p.prix AS produit_prix, " +
                "    p.image AS produit_image, " +
                "tp.id_type_produit AS type_produit_id," +
                "    tp.libelle AS type_produit " +
                "FROM " +
                "    detail_commande dc " +
                "JOIN " +
                "    produit p ON dc.produit_id_produit = p.id " +
                "JOIN " +
                "    TYPE_PRODUIT tp ON p.id_type_produit = tp.id_type_produit;";

        return jdbcTemplate.query(sql,  DETAILCOMMANDE_ROW_MAPPER);
    }

    @Override
    public DetailCommande findByIdDetailCommande(Long id) {
        String sql = "SELECT " +
                "    dc.quantite, " +
                "    dc.COMMANDE_id_commande, " +
                "    dc.PRODUIT_id_produit, " +
                "    p.nom AS produit_nom, " +
                "    p.description AS produit_description, " +
                "    p.prix AS produit_prix, " +
                "    p.image AS produit_image " +
                "FROM " +
                "    DETAIL_COMMANDE dc " +
                "JOIN " +
                "    PRODUIT p ON dc.PRODUIT_id_produit = p.id " ;


        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("iddetcom", id);

        return namedParameterJdbcTemplate.queryForObject(sql, map, new BeanPropertyRowMapper<>(DetailCommande.class));

    }






}


