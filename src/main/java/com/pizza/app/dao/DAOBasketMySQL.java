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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Profile("mysql")
@Component
public class DAOBasketMySQL implements IDAOBasket {

    @Autowired
    private IdaoProduit daoProduit;

    @Autowired
    private IDAOAuth daoAuth;

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

            EtatCommande etatCommande = new EtatCommande();
            etatCommande.setId(rs.getLong("etat_id"));
            etatCommande.setLibelle(rs.getString("etat_libelle"));
            commande.setEtatCommande(etatCommande);

            TypeProduit typeProduit = new TypeProduit();
            typeProduit.setId(rs.getLong("id_type_produit"));
            typeProduit.setLibelle(rs.getString("type_produit_libelle"));

            Produit produit = new Produit();
            produit.setId(rs.getLong("produit_id"));
            produit.setNom(rs.getString("produit_nom"));
            produit.setPrix(rs.getDouble("produit_prix"));
            produit.setImage(rs.getString("produit_image"));
            produit.setTypeProduit(typeProduit);

            DetailCommande detailCommande = new DetailCommande();
            detailCommande.setQuantite(rs.getLong("quantite"));
            detailCommande.setProduit(produit);

            if (commande.getDetailCommandes() == null) {
                commande.setDetailCommandes(new ArrayList<>());
            }
            commande.getDetailCommandes().add(detailCommande);
            return commande;
        }

    }

        ;

        final RowMapper<DetailCommande> DETAILCOMMANDE_ROW_MAPPER = new RowMapper<DetailCommande>() {

            @Override
            public DetailCommande mapRow(ResultSet rs, int rowNum) throws SQLException {
                DetailCommande detailCommande = new DetailCommande();

                detailCommande.setQuantite(rs.getLong(("quantite")));
//            Produit produit = new Produit();
//            produit.setId(rs.getLong("produit_id_produit"));
//            produit.setNom(rs.getString("produit_nom"));
//            produit.setPrix(rs.getDouble("produit_prix"));
//            produit.setImage(rs.getString("produit_image"));
//            produit.setDescription(rs.getString("produit_description"));
                Produit produit = daoProduit.selectProduitById(rs.getLong("produit_id_produit"));
                detailCommande.setProduit(produit);
//            TypeProduit typeProduit = new TypeProduit();
//            typeProduit.setLibelle(rs.getString("type_produit"));


                return detailCommande;
            }

            ;;

        };

        @Override
        public List<Commande> selectCommande() {

            String query = "SELECT "
                    + "c.id_commande AS id_commande, "
                    + "c.date AS date, "
                    + "c.heure AS heure, "
                    + "c.livraison AS livraison, "
                    + "c.prix_total AS prix_total, "
                    + "c.montant_paye AS montant_paye, "
                    + "e.id_etat AS etat_id, "
                    + "e.libelle AS etat_libelle, "
                    + "dc.quantite AS quantite, "
                    + "p.id AS produit_id, "
                    + "p.nom AS produit_nom, "
                    + "p.prix AS produit_prix, "
                    + "p.image AS produit_image, "
                    + "tp.id_type_produit AS id_type_produit, "
                    + "tp.libelle AS type_produit_libelle "
                    + "FROM COMMANDE c "
                    + "JOIN ETAT e ON c.ETAT_id_etat = e.id_etat "
                    + "LEFT JOIN DETAIL_COMMANDE dc ON c.id_commande = dc.COMMANDE_id_commande "
                    + "LEFT JOIN PRODUIT p ON dc.PRODUIT_id_produit = p.id "
                    + "LEFT JOIN TYPE_PRODUIT tp ON p.id_type_produit = tp.id_type_produit ";

            return jdbcTemplate.query(query, COMMANDE_ROW_MAPPER);

        }

        @Override
        public Commande selectCommandeById(Long commandeId) {
             String query = "SELECT "
                     + "c.id_commande AS id_commande, "
                     + "c.date AS date, "
                     + "c.heure AS heure, "
                     + "c.livraison AS livraison, "
                     + "c.prix_total AS prix_total, "
                     + "c.montant_paye AS montant_paye, "
                     + "e.id_etat AS etat_id, "
                     + "e.libelle AS etat_libelle, "
                     + "dc.quantite AS quantite, "
                     + "p.id AS produit_id, "
                     + "p.nom AS produit_nom, "
                     + "p.prix AS produit_prix, "
                     + "p.image AS produit_image, "
                     + "tp.id_type_produit AS id_type_produit, "
                     + "tp.libelle AS type_produit_libelle "
                     + "FROM COMMANDE c "
                     + "JOIN ETAT e ON c.ETAT_id_etat = e.id_etat "
                     + "LEFT JOIN DETAIL_COMMANDE dc ON c.id_commande = dc.COMMANDE_id_commande "
                     + "LEFT JOIN PRODUIT p ON dc.PRODUIT_id_produit = p.id "
                     + "LEFT JOIN TYPE_PRODUIT tp ON p.id_type_produit = tp.id_type_produit "
                     + "WHERE c.id_commande = ?";

            List<Commande> commandes = jdbcTemplate.query(query, new Object[]{commandeId}, COMMANDE_ROW_MAPPER);

            if (commandes.isEmpty()) {
                return null;
            }

            Commande commande = commandes.get(0);
//            commande.setDetailCommandes(new ArrayList<>());
//
//            for (Commande cmd : commandes) {
//                if (cmd.getDetailCommandes() != null && !cmd.getDetailCommandes().isEmpty()) {
//                    commande.getDetailCommandes().addAll(cmd.getDetailCommandes());
//                }
//
//            }

            // Assurer que la commande contient tous les détails sans doublons
            if (commande.getDetailCommandes() == null) {
                commande.setDetailCommandes(new ArrayList<>());
            }

            // Ajouter les détails sans doublons
            for (Commande cmd : commandes) {
                if (cmd.getDetailCommandes() != null) {
                    for (DetailCommande detail : cmd.getDetailCommandes()) {
                        if (!commande.getDetailCommandes().contains(detail)) {
                            commande.getDetailCommandes().add(detail);
                        }
                    }
                }
            }

            return commande;
        }

        ;

        public Commande selectCommandeByUserId(Long id) {
            List<Commande> commandes = jdbcTemplate.query("SELECT * FROM commande WHERE id_utilisateur = ?", COMMANDE_ROW_MAPPER, id);

            if (commandes.size() == 0) {
                return null;
            }
            return commandes.get(0);
        }

        ;

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
                    "    detail_commande dc " + "JOIN " +
                    "    produit p ON dc.produit_id_produit = p.id "
                    + "JOIN " +
                    "    TYPE_PRODUIT tp ON p.id_type_produit = tp.id_type_produit;";

            return jdbcTemplate.query(sql, DETAILCOMMANDE_ROW_MAPPER);
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
                    "    PRODUIT p ON dc.PRODUIT_id_produit = p.id ";


            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("iddetcom", id);

            return namedParameterJdbcTemplate.queryForObject(sql, map, new BeanPropertyRowMapper<>(DetailCommande.class));

        }

        @Override
        public void ajouterProduit(Utilisateur utilisateur, Produit produit, int quantite, Boolean livraison) {
// Insérer une nouvelle commande dans la table COMMANDE
            String insertCommandeQuery = "INSERT INTO COMMANDE (date, heure, livraison, prix_Total, montant_Paye, id_utilisateur, ETAT_id_etat) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(insertCommandeQuery,
                    new java.sql.Date(new java.util.Date().getTime()),
                    new java.sql.Time(new java.util.Date().getTime()),
                    livraison, // true pour livraison, false pour récupération sur place
                    produit.getPrix() * quantite,
                    0.0, // Exemple de valeur pour montantPaye
                    utilisateur.getId(),
                    1); // ID de l'état "Panier"

            // Récupérer l'ID de la commande nouvellement insérée
            Long commandeId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

            // Insérer un détail de commande dans la table DETAIL_COMMANDE
            String insertDetailCommandeQuery = "INSERT INTO DETAIL_COMMANDE (COMMANDE_id_commande, PRODUIT_id_produit, quantite) VALUES (?, ?, ?)";
            jdbcTemplate.update(insertDetailCommandeQuery, commandeId, produit.getId(), quantite);

            System.out.println("Produit ajouté avec succès dans la commande.");
        }

        public Commande creerNouvelleCommande(Commande commande) {
            String insertCommandeQuery = "INSERT INTO COMMANDE (date, heure, livraison, prix_Total, montant_Paye, id_utilisateur, ETAT_id_etat) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(insertCommandeQuery,
                    new java.sql.Date(new java.util.Date().getTime()),
                    new java.sql.Time(new java.util.Date().getTime()),
                    commande.getLivraison(),
                    commande.getPrixTotal(),
                    commande.getMontantPaye(),
                    commande.getUtilisateur().getId(),
                    1); // ID de l'état "Panier"

            Long commandeId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            commande.setId(commandeId);
            return commande;
        }

        public void ajouterProduitACommande(Long commandeId, Produit produit, int quantite, Boolean livraison) {
            // Mettre à jour la commande existante
            Commande commande = getCommandeById(commandeId);
            commande.setPrixTotal(commande.getPrixTotal() + (produit.getPrix() * quantite));
            commande.setLivraison(livraison);

            String updateCommandeQuery = "UPDATE COMMANDE SET prix_Total = ?, livraison = ? WHERE id_commande = ?";
            jdbcTemplate.update(updateCommandeQuery, commande.getPrixTotal(), commande.getLivraison(), commandeId);

            // Vérifier si le produit existe déjà dans la commande
            String checkQuery = "SELECT COUNT(*) FROM DETAIL_COMMANDE WHERE COMMANDE_id_commande = ? AND PRODUIT_id_produit = ?";
            int count = jdbcTemplate.queryForObject(checkQuery, new Object[]{commandeId, produit.getId()}, Integer.class);

            if (count > 0) {
                // Mettre à jour la quantité du produit existant
                String updateDetailCommandeQuery = "UPDATE DETAIL_COMMANDE SET quantite = quantite + ? WHERE COMMANDE_id_commande = ? AND PRODUIT_id_produit = ?";
                jdbcTemplate.update(updateDetailCommandeQuery, quantite, commandeId, produit.getId());
            } else {
                // Insérer un nouveau détail de commande
                String insertDetailCommandeQuery = "INSERT INTO DETAIL_COMMANDE (COMMANDE_id_commande, PRODUIT_id_produit, quantite) VALUES (?, ?, ?)";
                jdbcTemplate.update(insertDetailCommandeQuery, commandeId, produit.getId(), quantite);
            }


            System.out.println("Produit ajouté avec succès dans la commande.");
        }

        public Commande getCommandeById(Long commandeId) {
            String query = "SELECT * FROM COMMANDE WHERE id_commande = ?";
            return jdbcTemplate.queryForObject(query, new Object[]{commandeId}, (rs, rowNum) -> {
                Commande commande = new Commande();
                commande.setId(rs.getLong("id_commande"));
                commande.setDate(rs.getString("date"));
                commande.setHeure(rs.getString("heure"));
                commande.setLivraison(rs.getBoolean("livraison"));
                commande.setPrixTotal(rs.getDouble("prix_Total"));
                commande.setMontantPaye(rs.getDouble("montant_Paye"));
                // Ajoutez d'autres champs si nécessaire
                return commande;
            });
        }

        public void changerEtatCommande(Long commandeId, Long nouvelEtat) {
            String sql = "UPDATE commande SET etat_id_etat = ? WHERE id_commande = ?";
            jdbcTemplate.update(sql, nouvelEtat, commandeId);
        }

    }



