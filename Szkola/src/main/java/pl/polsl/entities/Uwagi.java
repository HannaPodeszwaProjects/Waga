package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "uwagi")
@NamedQueries({
        @NamedQuery(name = "uwagi.getStudentNote", query = "SELECT u FROM Uwagi u WHERE u.idUcznia = :id"),
        @NamedQuery(name = "uwagi.findByTeacher", query = "SELECT u FROM Uwagi u  WHERE u.idNauczyciela = :id")

})
public class Uwagi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "idUcznia", nullable = false)
    private Integer idUcznia;

    @Column(name = "idNauczyciela", nullable = false)
    private Integer idNauczyciela;

    @Column(name = "tresc")
    private String tresc;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setIdUcznia(Integer idUcznia) {
        this.idUcznia = idUcznia;
    }

    public Integer getIdUcznia() {
        return idUcznia;
    }

    public void setIdNauczyciela(Integer idNauczyciela) {
        this.idNauczyciela = idNauczyciela;
    }

    public Integer getIdNauczyciela() {
        return idNauczyciela;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getTresc() {
        return tresc;
    }

    @Override
    public String toString() {
        return "Uwagi{" +
                "ID=" + ID + '\'' +
                "idUcznia=" + idUcznia + '\'' +
                "idNauczyciela=" + idNauczyciela + '\'' +
                "tresc=" + tresc + '\'' +
                '}';
    }

    public Uwagi(){

    }

    public Uwagi(String desc,Integer idStudent, Integer idTeacher){
        this.tresc=desc;
        this.idNauczyciela=idTeacher;
        this.idUcznia=idStudent;
    }

}
