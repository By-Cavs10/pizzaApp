package com.pizza.app.dao;

import com.pizza.app.bo.Commande;
import com.pizza.app.bo.EtatCommande;
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



            return commande;
        }
    };

    @Override
    public List<Commande> selectCommande() {

        return jdbcTemplate.query("SELECT * FROM commande", COMMANDE_ROW_MAPPER);

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
        String sql = "select id_etat, libelle from etat";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(EtatCommande.class));
    }

    @Override
    public EtatCommande findById(Long id) {
        String sql = "select id_etat, libelle from etat WHERE id_etat = :idetat";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("idetat", id);

        return namedParameterJdbcTemplate.queryForObject(sql, map, new BeanPropertyRowMapper<>(EtatCommande.class));

    }




}


