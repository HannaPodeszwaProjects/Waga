package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rodzice")
@NamedQueries({
        @NamedQuery(name = "rodzice.getUnusedParents",
                query = "SELECT r FROM Rodzice r " +
                        "LEFT JOIN Uzytkownicy usr ON r.ID = usr.ID " +
                        "WHERE usr.ID IS NULL "),
        @NamedQuery(name = "rodzice.findAll", query = "SELECT r FROM Rodzice r"),
        @NamedQuery(name = "rodzice.getParentEmailById",
                query = "SELECT R.email FROM Rodzice R WHERE R.ID = :ID"),
        @NamedQuery(name = "rodzice.getAllParents",
                query = "SELECT R FROM Rodzice R"),
        @NamedQuery(name = "rodzice.getParentById",
                query = "SELECT R FROM Rodzice R WHERE R.ID = :ID"),
})
public class Rodzice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nrKontaktowy")
    private String nrKontaktowy;

    @Column(name = "imie", nullable = false)
    private String imie;

    @Column(name = "drugieImie")
    private String drugieImie;

    @Column(name = "nazwisko", nullable = false)
    private String nazwisko;

    @Column(name = "adres")
    private String adres;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setNrKontaktowy(String nrKontaktowy) {
        this.nrKontaktowy = nrKontaktowy;
    }

    public String getNrKontaktowy() {
        return nrKontaktowy;
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

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getAdres() {
        return adres;
    }

    @Override
    public String toString() {
        return "Rodzice{" +
                "ID=" + ID + '\'' +
                "nrKontaktowy=" + nrKontaktowy + '\'' +
                "imie=" + imie + '\'' +
                "drugieImie=" + drugieImie + '\'' +
                "nazwisko=" + nazwisko + '\'' +
                "adres=" + adres + '\'' +
                '}';
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
