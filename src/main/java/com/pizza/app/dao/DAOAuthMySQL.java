package com.pizza.app.dao;

import com.pizza.app.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Profile("mysql")
@Component
public class DAOAuthMySQL {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Utilisateur login(String email, String password) {
        List<Utilisateur> members = jdbcTemplate.query("SELECT * FROM UTILISATEUR WHERE email = ? AND password = ?", MEMBER_ROW_MAPPER, email, password);
        return members.isEmpty() ? null : utilisateurs.get(0);
    }

    @Override
    public List<Utilisateur> selectMovie() {
        return List.of();
    }

    @Override
    public List<Utilisateur> selectMember() {
        return List.of();
    }




    /*
    Le code qui permet de savoir comment convertir/mapper un résultat en SQL en

    Comment mppaer un résultat SQL en Aliment
     */

    static final RowMapper<Utilisateur> MEMBER_ROW_MAPPER = new RowMapper<Utilisateur>() {

        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            Utilisateur utilisateur = new Utilisateur();

            utilisateur.setEmail(rs.getString("email"));
            utilisateur.setPassword(rs.getString("password"));





            return member;
        }
    };



//    @Override
//    public List<Member> selectMember() {
//
//        return jdbcTemplate.query("SELECT * FROM MEMBRE", MEMBER_ROW_MAPPER);
//
//    }

    @Override
    public Member selectMemberById(long id) {
        List<Member> members = jdbcTemplate.query("SELECT * FROM membre WHERE id = ?", MEMBER_ROW_MAPPER, id);

        //Si on trouve aucun élément on retourne null
        //Retourner le premier élément
        return members.isEmpty() ? null : members.get(0);
    }

    @Override
    public void saveMember(Member member) {

        //Tester si il existe en base, SI OUI => Update SINON => Insert
        if (Objects.nonNull(member.getId()) && selectMemberById(member.getId()) != null) {
            jdbcTemplate.update("UPDATE member SET email = ?, lastname = ?, firstname = ?, password = ? WHERE email = ?"
                    , member.email, member.lastname, member.firstname, member.password);



            //PS : Return = Arreter la fonction
            return;
//
        }

        //Insérer en base un aliment

        jdbcTemplate.update("UPDATE member SET email, lastname , firstname , password) VALUES (?,?," +
                "?,?)", member.email, member.lastname, member.firstname, member.password);

        jdbcTemplate.update("UPDATE roles SET ROLE) VALUES (?,?)", member.isAdmin);

    }

    @Override
    public void deleteById(Member member) {

    }

}
