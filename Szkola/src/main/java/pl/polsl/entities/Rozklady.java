package pl.polsl.entities;

import pl.polsl.model.Classroom;
import pl.polsl.model.Subject;
import pl.polsl.model.Teacher;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "rozklady.findByTeacher",
                query = "SELECT r FROM Rozklady r WHERE r.idNauczyciela = :id"),
        @NamedQuery(name = "rozklady.findBySubject",
                query = "SELECT r FROM Rozklady r WHERE r.idPrzedmiotu = :id"),
        @NamedQuery(name = "rozklady.findByClass",
                query = "SELECT r FROM Rozklady r WHERE r.idKlasy = :id"),
        @NamedQuery(name = "rozklady.findBySubject",
                query = "SELECT r FROM Rozklady r WHERE r.idPrzedmiotu = :id"),
        @NamedQuery(name = "rozklady.findByClassroom",
                query = "SELECT r FROM Rozklady r WHERE r.idSali = :id")
})
@Table(name = "rozklady")

public class Rozklady implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "idKlasy", nullable = false)
    private Integer idKlasy;

    @Column(name = "dzien", nullable = false)
    private String dzien;

    @Column(name = "godzina", nullable = false)
    private Integer godzina;

    @Column(name = "idNauczyciela", nullable = false)
    private Integer idNauczyciela;

    @Column(name = "idPrzedmiotu", nullable = false)
    private Integer idPrzedmiotu;

    @Column(name = "idSali", nullable = false)
    private Integer idSali;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setIdKlasy(Integer idKlasy) {
        this.idKlasy = idKlasy;
    }

    public Integer getIdKlasy() {
        return idKlasy;
    }

    public void setDzien(String dzien) {
        this.dzien = dzien;
    }

    public String getDzien() {
        return dzien;
    }

    public void setGodzina(Integer godzina) {
        this.godzina = godzina;
    }

    public Integer getGodzina() {
        return godzina;
    }

    public void setIdNauczyciela(Integer idNauczyciela) {
        this.idNauczyciela = idNauczyciela;
    }

    public Integer getIdNauczyciela() {
        return idNauczyciela;
    }

    public void setIdPrzedmiotu(Integer idPrzedmiotu) {
        this.idPrzedmiotu = idPrzedmiotu;
    }

    public Integer getIdPrzedmiotu() {
        return idPrzedmiotu;
    }

    public void setIdSali(Integer idSali) {
        this.idSali = idSali;
    }

    public Integer getIdSali() {
        return idSali;
    }

    @Override
    public String toString() {
        String subjectName = (new Subject()).getSubjectName(idPrzedmiotu);
        if (subjectName.length() >= 30) {
            String shortenedName = "";
            for (String word : subjectName.split(" ")) {
                shortenedName += word.charAt(0);
            }
            subjectName = shortenedName;
        }
        else if (subjectName.length() >= 20) {
            String shortenedName = "";
            for (String word : subjectName.split(" ")) {
                shortenedName += word.charAt(0);
                shortenedName += ". ";
            }
            subjectName = shortenedName;
        }
        return subjectName + "\n" + (idSali != null ? "s. " + (new Classroom()).getNameById(idSali) : "") + (idNauczyciela != null ? "\nnaucz. " + (new Teacher()).getTeacherById(idNauczyciela).getImie().charAt(0) + (new Teacher()).getTeacherById(idNauczyciela).getNazwisko().charAt(0) : "");
    }
}
