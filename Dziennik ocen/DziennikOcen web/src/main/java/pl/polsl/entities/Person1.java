package pl.polsl.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Represents student in the database
 * @author Hanna Podeszwa
 * @version 2.1
 */
@Entity
@Table(name = "PERSONS")
@NamedQueries({
    @NamedQuery(name = "Person1.findAll", query = "SELECT p FROM Person1 p"),
    @NamedQuery(name = "Person1.findById", query = "SELECT p FROM Person1 p WHERE p.id = :id"),
    @NamedQuery(name = "Person1.findByName", query = "SELECT p FROM Person1 p WHERE p.name = :name"),
    @NamedQuery(name = "Person1.findBySurname", query = "SELECT p FROM Person1 p WHERE p.surname = :surname"),
    @NamedQuery(name = "Person1.findByClass1", query = "SELECT p FROM Person1 p WHERE p.class1 = :class1")})
public class Person1 implements Serializable {

    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    @Size(max = 50)
    @Column(name = "SURNAME")
    private String surname;
    @Size(max = 50)
    @Column(name = "CLASS")
    private String class1;
    @OneToMany(mappedBy = "personId", fetch = FetchType.LAZY)
    private Collection<PersonGrade> personGradeCollection;
    @OneToMany(mappedBy = "idPerson", fetch = FetchType.LAZY)
    private Collection<PersonSubject> personSubjectCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    public Person1() {
    }

    public Person1(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
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
        if (!(object instanceof Person1)) {
            return false;
        }
        Person1 other = (Person1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.polsl.entities.Person1[ id=" + id + " ]";
    }

    public Collection<PersonSubject> getPersonSubjectCollection() {
        return personSubjectCollection;
    }

    public void setPersonSubjectCollection(Collection<PersonSubject> personSubjectCollection) {
        this.personSubjectCollection = personSubjectCollection;
    }

    public Collection<PersonGrade> getPersonGradeCollection() {
        return personGradeCollection;
    }

    public void setPersonGradeCollection(Collection<PersonGrade> personGradeCollection) {
        this.personGradeCollection = personGradeCollection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
