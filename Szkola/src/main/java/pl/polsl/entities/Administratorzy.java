package pl.polsl.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "administratorzy")
@NamedQueries({
        @NamedQuery(name = "administratorzy.getAdminEmailByID", query = "SELECT a.email FROM Administratorzy a WHERE a.ID = :ID"),
        @NamedQuery(name = "administratorzy.findAll", query = "SELECT a FROM Administratorzy a"),
        @NamedQuery(name = "administratorzy.getAdminById", query = "SELECT a FROM Administratorzy a WHERE a.ID = :ID")
})
public class Administratorzy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name = "imie", nullable = false)
    private String imie;

    @Column(name = "drugieImie")
    private String drugieImie;

    @Column(name = "nazwisko", nullable = false)
    private String nazwisko;

    @Column(name = "nrKontaktowy")
    private String nrKontaktowy;

    @Column(name = "email", nullable = false)
    private String email;

}
