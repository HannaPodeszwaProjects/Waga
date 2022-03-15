package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "wiadomosci")
@NamedQueries({
        @NamedQuery(name = "Wiadomosci.getReceivedMessagesByUserLogin",
                query = "SELECT w FROM Wiadomosci w WHERE w.odbiorca = :LOGIN"),
        @NamedQuery(name = "Wiadomosci.getSentMessagesByUserLogin",
                query = "SELECT w FROM Wiadomosci w WHERE w.nadawca = :LOGIN"),
        @NamedQuery(name = "Wiadomosci.getMessageByReceiverSenderAndDate",
                query = "SELECT w.tresc FROM Wiadomosci w WHERE w.nadawca = :SENDER AND w.odbiorca = :RECEIVER AND w.data = :DATE"),
        @NamedQuery(name = "wiadomosci.findByTeacher",
        query = "SELECT w FROM Wiadomosci w WHERE (w.odbiorca = :l) OR (w.nadawca = :l)"),
@NamedQuery(name = "wiadomosci.findByParent",
                query = "SELECT w FROM Wiadomosci w WHERE (w.odbiorca = :l) OR (w.nadawca = :l)")

})
public class Wiadomosci implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name = "temat", nullable = false)
    private String temat;

    @Column(name = "tresc", nullable = false)
    private String tresc;

    @Column(name = "data")
    private Date data;

    @Column(name = "odbiorca", nullable = false)
    private String odbiorca;

    @Column(name = "nadawca", nullable = false)
    private String nadawca;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }


    public void setTemat(String temat) {
        this.temat = temat;
    }

    public String getTemat() {
        return temat;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getTresc() {
        return tresc;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setOdbiorca(String odbiorca) {
        this.odbiorca = odbiorca;
    }

    public String getOdbiorca() {
        return odbiorca;
    }

    public void setNadawca(String nadawca) {
        this.nadawca = nadawca;
    }

    public String getNadawca() {
        return nadawca;
    }


    @Override
    public String toString() {
        return "Wiadomosci{" +
                "ID=" + ID + '\'' +
                "temat" + temat + '\'' +
                "tresc=" + tresc + '\'' +
                "data=" + data + '\'' +
                "odbiorca=" + odbiorca + '\'' +
                "nadawca=" + nadawca + '\'' +
                '}';
    }
}
