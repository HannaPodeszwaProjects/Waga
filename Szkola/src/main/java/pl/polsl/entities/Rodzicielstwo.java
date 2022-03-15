package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "Rodzicielstwo.getParentsByChildId",
                query = "SELECT R FROM Rodzicielstwo R WHERE R.idRodzica = :ID"),
        @NamedQuery(name = "rodzicielstwo.findByParent",
                query = "SELECT R FROM Rodzicielstwo R WHERE R.idRodzica = :id"),
        @NamedQuery(name = "rodzicielstwo.findByStudent",
                query = "SELECT R FROM Rodzicielstwo R WHERE R.idUcznia = :id")
})
@Table(name = "rodzicielstwo")
public class Rodzicielstwo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idUcznia", nullable = false)
    private Integer idUcznia;

    @Column(name = "idRodzica", nullable = false)
    private Integer idRodzica;

    public void setIdUcznia(Integer idUcznia) {
        this.idUcznia = idUcznia;
    }

    public Integer getIdUcznia() {
        return idUcznia;
    }

    public void setIdRodzica(Integer idRodzica) {
        this.idRodzica = idRodzica;
    }

    public Integer getIdRodzica() {
        return idRodzica;
    }

    @Override
    public String toString() {
        return "Rodzicielstwo{" +
                "idUcznia=" + idUcznia + '\'' +
                "idRodzica=" + idRodzica + '\'' +
                '}';
    }
}
