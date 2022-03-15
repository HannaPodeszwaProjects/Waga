package pl.polsl.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "godzinyLekcji")
@NamedQueries({
        @NamedQuery(name = "godzinyLekcji.getHighestNumber", query = "SELECT g.numer FROM GodzinyLekcji g ORDER BY g.numer DESC"),
        @NamedQuery(name = "godzinyLekcji.getAll", query = "SELECT g FROM GodzinyLekcji g ORDER BY g.numer"),
        @NamedQuery(name = "godzinyLekcji.getNumber", query = "SELECT g.numer FROM GodzinyLekcji g WHERE g.ID = :id"),
        @NamedQuery(name = "godzinyLekcji.getById", query = "SELECT g FROM GodzinyLekcji g WHERE g.ID = :id")
})
public class GodzinyLekcji implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name = "poczatek", nullable = false)
    private Time poczatek;

    @Column(name = "koniec", nullable = false)
    private Time koniec;

    @Column(name = "numer", nullable = false)
    private Integer numer;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Time getPoczatek() {
        return poczatek;
    }

    public void setPoczatek(Time poczatek) {
        this.poczatek = poczatek;
    }

    public Time getKoniec() {
        return koniec;
    }

    public void setKoniec(Time koniec) {
        this.koniec = koniec;
    }

    public Integer getNumer() {
        return numer;
    }

    public void setNumer(Integer numer) {
        this.numer = numer;
    }

}
