package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity

@Table(name = "udzialwkole")
@NamedQueries({
        @NamedQuery(name = "udzialwkole.findByTeacher",
                query = "SELECT k FROM Kolanaukowe k WHERE k.idNauczyciela = :id"),
        @NamedQuery(name = "udzialwkole.findByStudent",
                query = "SELECT u FROM Udzialwkole u WHERE u.idUcznia = :id"),
        @NamedQuery(name = "udzialwkole.findByClub", query = "SELECT u FROM Udzialwkole u WHERE u.idKola = :idKola"),
        @NamedQuery(name = "udzialwkole.findByBoth",
                query = "SELECT u FROM Udzialwkole u WHERE u.idUcznia = :studentID AND u.idKola = :clubID")
})
public class Udzialwkole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idKola", nullable = false)
    private Integer idKola;

    @Id
    @Column(name = "idUcznia", nullable = false)
    private Integer idUcznia;

    @Column(name = "dataDolaczenia", nullable = false)
    private Date dataDolaczenia;

    public void setIdKola(Integer idKola) {
        this.idKola = idKola;
    }

    public Integer getIdKola() {
        return idKola;
    }

    public void setIdUcznia(Integer idUcznia) {
        this.idUcznia = idUcznia;
    }

    public Integer getIdUcznia() {
        return idUcznia;
    }

    public void setDataDolaczenia(Date dataDolaczenia) {
        this.dataDolaczenia = dataDolaczenia;
    }

    public Date getDataDolaczenia() {
        return dataDolaczenia;
    }

    @Override
    public String toString() {
        return "Udzialwkole{" +
                "idKola=" + idKola + '\'' +
                "idUcznia=" + idUcznia + '\'' +
                "dataDolaczenia=" + dataDolaczenia + '\'' +
                '}';
    }
}
