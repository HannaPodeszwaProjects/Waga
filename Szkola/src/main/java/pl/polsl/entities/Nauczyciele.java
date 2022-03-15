package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "nauczyciele")
@NamedQueries({
        @NamedQuery(name = "nauczyciele.getUnusedTeachers",
                query = "SELECT n FROM Nauczyciele n " +
                        "LEFT JOIN Uzytkownicy usr ON n.ID = usr.ID " +
                        "WHERE usr.ID IS NULL "),
        @NamedQuery(name = "nauczyciele.findAll", query = "SELECT n FROM Nauczyciele n"),
        @NamedQuery(name = "nauczyciele.findById", query = "SELECT n FROM Nauczyciele n WHERE n.ID = :id"),
        @NamedQuery(name = "nauczyciele.findByName", query = "SELECT n FROM Nauczyciele n WHERE n.imie = :name AND n.nazwisko = :surname"),
        @NamedQuery(name = "nauczyciele.getTeacherEmailByID", query = "SELECT n.email FROM Nauczyciele n WHERE n.ID = :ID")
})
public class Nauczyciele implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "imie", nullable = false)
    private String imie;

    @Column(name = "drugieImie")
    private String drugieImie;

    @Column(name = "Nazwisko", nullable = false)
    private String nazwisko;

    @Column(name = "nrKontaktowy")
    private String nrKontaktowy;


    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getImie() {
        return imie;
    }

    public void setDrugieImie(String drugieImie) {
        this.drugieImie = drugieImie;
    }

    public String getDrugieImie() {
        return drugieImie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNrKontaktowy(String nrKontaktowy) {
        this.nrKontaktowy = nrKontaktowy;
    }

    public String getNrKontaktowy() {
        return nrKontaktowy;
    }

    @Override
    public String toString() {
        return "Nauczyciele{" +
                "ID=" + ID + '\'' +
                "imie=" + imie + '\'' +
                "drugieImie=" + drugieImie + '\'' +
                "nazwisko=" + nazwisko + '\'' +
                "nrKontaktowy=" + nrKontaktowy + '\'' +
                '}';
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
