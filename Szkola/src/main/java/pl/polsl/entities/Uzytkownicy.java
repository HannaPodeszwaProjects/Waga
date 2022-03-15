package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "Uzytkownicy.getUnusedStudentAccounts",
                query = "SELECT usr FROM Uzytkownicy usr " +
                        "LEFT JOIN Uczniowie u ON u.ID = usr.ID " +
                        "WHERE usr.dostep = \"uczen\" AND u.ID IS NULL"),

        @NamedQuery(name = "Uzytkownicy.getUnusedTeacherAccounts",
                query = "SELECT usr FROM Uzytkownicy usr " +
                        "LEFT JOIN Nauczyciele n ON n.ID = usr.ID " +
                        "WHERE usr.dostep = \"nauczyciel\" AND n.ID IS NULL"),

        @NamedQuery(name = "Uzytkownicy.getUnusedParentAccounts",
                query = "SELECT usr FROM Uzytkownicy usr " +
                        "LEFT JOIN Rodzice r ON r.ID = usr.ID " +
                        "WHERE usr.dostep = \"rodzic\" AND r.ID IS NULL"),

        @NamedQuery(name = "Uzytkownicy.getUserByLoginAndPassword",
                query = "SELECT U FROM Uzytkownicy U WHERE U.login = :LOGIN AND U.haslo = :PASSWORD"),

        @NamedQuery(name = "Uzytkownicy.getUserByLogin",
                query = "SELECT U FROM Uzytkownicy U WHERE U.login = :LOGIN"),

        @NamedQuery(name = "Uzytkownicy.getUserByIdAndRole",
                query = "SELECT U FROM Uzytkownicy U WHERE U.ID = :ID AND U.dostep = :ROLE"),

        @NamedQuery(name = "Uzytkownicy.updatePasswordByIdAndRole",
                query = "UPDATE Uzytkownicy U SET U.haslo = :PASSWORD WHERE U.ID = :ID AND U.dostep = :ROLE"),

        @NamedQuery(name = "Uzytkownicy.getLoginByIdAndRole",
                query = "SELECT U.login FROM Uzytkownicy U WHERE U.ID = :ID AND U.dostep = :ROLE"),
        @NamedQuery(name = "Uzytkownicy.getPrincipal",
                query = "SELECT U FROM Uzytkownicy U WHERE U.dostep = 'dyrektor'"),
})
@Table(name = "uzytkownicy")
public class Uzytkownicy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "ID")
    private Integer ID;

    @Column(name = "haslo", nullable = false)
    private String haslo;

    @Column(name = "dostep", nullable = false)
    private String dostep;

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setDostep(String dostep) {
        this.dostep = dostep;
    }

    public String getDostep() {
        return dostep;
    }

    @Override
    public String toString() {
        return "Uzytkownicy{" +
                "login=" + login + '\'' +
                "ID=" + ID + '\'' +
                "haslo=" + haslo + '\'' +
                "dostep=" + dostep + '\'' +
                '}';
    }
}
