package pl.polsl.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "nieobecnosci")
@NamedQueries({
        @NamedQuery(name = "nieobecnosci.findAll", query = "SELECT n FROM Nieobecnosci n WHERE n.idUcznia = :id"),
        @NamedQuery(name = "nieobecnosci.findBySubject", query = "SELECT n FROM Nieobecnosci n WHERE n.idPrzedmiotu = :id")
})
public class Nieobecnosci implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    public Integer ID;

    @Column(name = "idPrzedmiotu", nullable = false)
    public Integer idPrzedmiotu;

    @Column(name = "idUcznia", nullable = false)
    public Integer idUcznia;

    @Column(name = "data", nullable = false)
    public Date data;

    @Column(name = "godzina", nullable = false)
    public Integer godzina;

    @Column(name = "czyUsprawiedliwiona", nullable = false)
    public Integer czyUsprawiedliwiona;


    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setUczniowieId(Integer uczniowieId) {
        this.idUcznia = uczniowieId;
    }

    public Integer getUczniowieId() {
        return idUcznia;
    }

    public void setPrzedmiotyId(Integer przedmiotyId) {
        this.idPrzedmiotu = przedmiotyId;
    }

    public Integer getPrzedmiotyId() {
        return idPrzedmiotu;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setGodzina(Integer data) {
        this.godzina = data;
    }

    public Integer getGodzina() {
        return godzina;
    }

    public void setCzyUsprawiedliwiona(Integer czyUsprawiedliwiona) {
        this.czyUsprawiedliwiona = czyUsprawiedliwiona;
    }

    public Integer getCzyUsprawiedliwiona() {
        return czyUsprawiedliwiona;
    }


    @Override
    public String toString() {
        return "Nieobecnosci{" +
                "ID=" + ID + '\'' +
                "uczniowieId=" + idUcznia + '\'' +
                "przedmiotyId=" + idPrzedmiotu + '\'' +
                "data=" + data + '\'' +
                "czyUsprawiedliwiona=" + czyUsprawiedliwiona + '\'' +
                '}';
    }
}
