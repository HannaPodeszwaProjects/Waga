package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "klasy")
@NamedQueries({
        @NamedQuery(name = "klasy.findAll", query = "SELECT k FROM Klasy k"),
        @NamedQuery(name = "klasy.findByNumber", query = "SELECT k FROM Klasy k WHERE k.numer = :number"),
        @NamedQuery(name = "klasy.findById", query = "SELECT k FROM Klasy k WHERE k.ID = :id"),
        @NamedQuery(name = "klasy.findStudentsById", query = "SELECT u FROM Klasy k, Uczniowie u WHERE k.ID = :id" +
                " AND u.idKlasy = k.ID"),
        @NamedQuery(name = "klasy.findByTutor", query = "SELECT k FROM Klasy k WHERE k.idWychowawcy = :idWychowawcy"),
        @NamedQuery(name = "klasy.findByTeacherId", query = "SELECT DISTINCT k FROM Klasy k INNER JOIN Rozklady r ON k.ID = r.idKlasy WHERE r.idNauczyciela = :idNauczyciela"),
        @NamedQuery(name = "klasy.findByLeader", query = "SELECT k FROM Klasy k WHERE k.idPrzewodniczacego = :idPrzewodniczacego")})
public class Klasy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "numer", nullable = false)
    private String numer;

    @Column(name = "idWychowawcy")
    private Integer idWychowawcy;

    @Column(name = "idPrzewodniczacego")
    private Integer idPrzewodniczacego;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public String getNumer() {
        return numer;
    }

    public void setIdWychowawcy(Integer idWychowawcy) {
        this.idWychowawcy = idWychowawcy;
    }

    public Integer getIdWychowawcy() {
        return idWychowawcy;
    }

    public void setIdPrzewodniczacego(Integer idPrzewodniczacego) {
        this.idPrzewodniczacego = idPrzewodniczacego;
    }

    public Integer getIdPrzewodniczacego() {
        return idPrzewodniczacego;
    }

    @Override
    public String toString() {
        return "Klasy{" +
                "ID=" + ID + '\'' +
                "numer=" + numer + '\'' +
                "idWychowawcy=" + idWychowawcy + '\'' +
                "idPrzewodniczacego=" + idPrzewodniczacego + '\'' +
                '}';
    }
}
