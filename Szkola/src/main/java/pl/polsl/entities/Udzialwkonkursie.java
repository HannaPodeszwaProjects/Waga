package pl.polsl.entities;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "udzialwkonkursie.findByBoth",
                query = "SELECT u FROM Udzialwkonkursie u WHERE u.idUcznia = :studentID AND u.idKonkursu = :competitionID"),
        @NamedQuery(name = "udzialwkonkursie.findByStudent",
                query = "SELECT u FROM Udzialwkonkursie u WHERE u.idUcznia = :id"),
        @NamedQuery(name = "udzialwkonkursie.findByTeacher",
                query = "SELECT u FROM Udzialwkonkursie u WHERE u.idNauczyciela = :id"),
        @NamedQuery(name = "udzialwkonkursie.findByCompetition",
                query = "SELECT u FROM Udzialwkonkursie u WHERE u.idKonkursu = :id")
})
@Table(name = "udzialwkonkursie")
public class Udzialwkonkursie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idKonkursu", nullable = false)
    private Integer idKonkursu;

    @Id
    @Column(name = "idNauczyciela", nullable = false)
    private Integer idNauczyciela;

    @Id
    @Column(name = "idUcznia", nullable = false)
    private Integer idUcznia;

    @Column(name = "osiagniecie")
    private String osiagniecie;

    public void setIdKonkursu(Integer idKonkursu) {
        this.idKonkursu = idKonkursu;
    }

    public Integer getIdKonkursu() {
        return idKonkursu;
    }

    public void setIdNauczyciela(Integer idNauczyciela) {
        this.idNauczyciela = idNauczyciela;
    }

    public Integer getIdNauczyciela() {
        return idNauczyciela;
    }

    public void setIdUcznia(Integer idUcznia) {
        this.idUcznia = idUcznia;
    }

    public Integer getIdUcznia() {
        return idUcznia;
    }

    public void setOsiagniecie(String osiagniecie) {
        this.osiagniecie = osiagniecie;
    }

    public String getOsiagniecie() {
        return osiagniecie;
    }

    @Override
    public String toString() {
        return "Udzialwkonkursie{" +
                "idKonkursu=" + idKonkursu + '\'' +
                "idNauczyciela=" + idNauczyciela + '\'' +
                "idUcznia=" + idUcznia + '\'' +
                "osiagniecie=" + osiagniecie + '\'' +
                '}';
    }
}
