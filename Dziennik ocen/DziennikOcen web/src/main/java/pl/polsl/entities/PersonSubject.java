package pl.polsl.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Represents the relationship of student and subject in the database
 *
 * @author Hanna Podeszwa
 * @version 2.1
 */
@Entity
@Table(name = "PERSONS_SUBJECTS")
@NamedQueries({
    @NamedQuery(name = "PersonSubject.findAll", query = "SELECT p FROM PersonSubject p"),
    @NamedQuery(name = "PersonSubject.findById", query = "SELECT p FROM PersonSubject p WHERE p.id = :id")})
public class PersonSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;
    @JoinColumn(name = "ID_PERSON", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Person1 idPerson;
    @JoinColumn(name = "ID_SUBJECT", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Subject1 idSubject;

    public PersonSubject() {
    }

    public PersonSubject(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person1 getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Person1 idPerson) {
        this.idPerson = idPerson;
    }

    public Subject1 getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(Subject1 idSubject) {
        this.idSubject = idSubject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonSubject)) {
            return false;
        }
        PersonSubject other = (PersonSubject) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.polsl.entities.PersonSubject[ id=" + id + " ]";
    }

}
