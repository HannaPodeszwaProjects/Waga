package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Konkursy.findAll", query = "SELECT k FROM Konkursy k"),
        @NamedQuery(name = "Konkursy.findByStudent", query = "SELECT k FROM Konkursy k JOIN Udzialwkonkursie u ON k.ID = u.idKonkursu WHERE u.idUcznia = :id"),
        @NamedQuery(name = "Konkursy.findByTeacher", query = "SELECT DISTINCT k FROM Konkursy k JOIN Udzialwkonkursie u ON k.ID = u.idKonkursu WHERE u.idNauczyciela = :id")

})
@Table(name = "konkursy")
public class Konkursy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "nazwa")
    private String nazwa;

    @Column(name = "opis")
    private String opis;

    @Column(name = "dataOdbyciaSie")
    private Date dataOdbyciaSie;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getOpis() {
        return opis;
    }

    public void setDataOdbyciaSie(Date dataOdbyciaSie) {
        this.dataOdbyciaSie = dataOdbyciaSie;
    }

    public Date getDataOdbyciaSie() {
        return dataOdbyciaSie;
    }

    @Override
    public String toString() {
        return "Konkursy{" +
                "ID=" + ID + '\'' +
                "nazwa=" + nazwa + '\'' +
                "opis=" + opis + '\'' +
                "dataOdbyciaSie=" + dataOdbyciaSie + '\'' +
                '}';
    }
}
