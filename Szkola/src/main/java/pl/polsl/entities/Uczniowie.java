package pl.polsl.entities;

import pl.polsl.model.SchoolClass;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "uczniowie")
@NamedQueries({
        @NamedQuery(name = "uczniowie.getUnusedStudents",
                query = "SELECT u FROM Uczniowie u " +
                        "LEFT JOIN Uzytkownicy usr ON u.ID = usr.ID " +
                        "WHERE usr.ID IS NULL"),
        @NamedQuery(name = "uczniowie.getHighestID", query = "SELECT u.ID FROM Uczniowie u ORDER BY u.ID DESC"),
        @NamedQuery(name = "uczniowie.findAll", query = "SELECT u FROM Uczniowie u"),
        @NamedQuery(name = "uczniowie.findById", query = "SELECT u FROM Uczniowie u WHERE u.ID = :id"),
        @NamedQuery(name = "uczniowie.getStudentEmailById", query = "SELECT u.email FROM Uczniowie u WHERE u.ID = :ID"),
        @NamedQuery(name = "uczniowie.findByParentsId", query = "SELECT u FROM Uczniowie u JOIN Rodzicielstwo r ON r.idUcznia=u.ID WHERE r.idRodzica= :id"),
        @NamedQuery(name = "uczniowie.getStudentInClass", query = "SELECT u FROM Uczniowie u WHERE u.idKlasy = :id"),
        @NamedQuery(name = "uczniowie.getStudentInClub",
                query = "SELECT DISTINCT u FROM Uczniowie u INNER JOIN Udzialwkole p ON p.idUcznia = u.ID WHERE p.idKola = :idKola"),
        @NamedQuery(name = "uczniowie.getStudentInCompetition",
                query = "SELECT DISTINCT u FROM Uczniowie u INNER JOIN Udzialwkonkursie p ON p.idUcznia = u.ID WHERE p.idKonkursu = :idKonkursu"),
        @NamedQuery(name = "uczniowie.getGradeFromSubject",
                query = "SELECT o, u FROM Uczniowie u, Oceny o, Przedmioty p  WHERE o.idUcznia = u.ID AND" +
                        " o.idPrzedmiotu = p.ID AND p.ID = :idPrzedmiotu AND o.data >= :data1 AND o.data <= :data2"),
        @NamedQuery(name = "uczniowie.getGradeFromStudent",
                query = "SELECT o, p FROM Uczniowie u, Oceny o, Przedmioty p  WHERE o.idUcznia = u.ID AND" +
                        " o.idPrzedmiotu = p.ID AND u.ID = :id AND o.data >= :data1 AND o.data <= :data2"),


})
public class Uczniowie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    public Integer ID;

    @Column(name = "email", nullable = false)
    public String email;

    @Column(name = "idKlasy", nullable = false)
    public Integer idKlasy;

    @Column(name = "imie", nullable = false)
    public String imie;

    @Column(name = "drugieImie")
    public String drugieImie;

    @Column(name = "nazwisko", nullable = false)
    public String nazwisko;

    @Column(name = "adres")
    public String adres;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setIdKlasy(Integer idKlasy) { this.idKlasy = idKlasy; }

    public Integer getIdKlasy() {
        return idKlasy;
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
        return "Uczniowie{" +
                "ID=" + ID + '\'' +
                "idKlasy=" + idKlasy + '\'' +
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
