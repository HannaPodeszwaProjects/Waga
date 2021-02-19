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
 * Represents the relationship of student, subject and grade in the database
 * 
 * @author Hanna Podeszwa
 * @version 2.1
 */
@Entity
@Table(name = "PERSONS_GRADES")
@NamedQueries({
    @NamedQuery(name = "PersonGrade.findAll", query = "SELECT p FROM PersonGrade p"),
    @NamedQuery(name = "PersonGrade.findById", query = "SELECT p FROM PersonGrade p WHERE p.id = :id")})
public class PersonGrade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
     @GeneratedValue
    private Integer id;
    @JoinColumn(name = "GRADE_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Grade1 gradeId;
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Person1 personId;
    @JoinColumn(name = "SUBJECT_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Subject1 subjectId;

    public PersonGrade() {
    }

    public PersonGrade(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Grade1 getGradeId() {
        return gradeId;
    }

    public void setGradeId(Grade1 gradeId) {
        this.gradeId = gradeId;
    }

    public Person1 getPersonId() {
        return personId;
    }

    public void setPersonId(Person1 personId) {
        this.personId = personId;
    }

    public Subject1 getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Subject1 subjectId) {
        this.subjectId = subjectId;
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
        if (!(object instanceof PersonGrade)) {
            return false;
        }
        PersonGrade other = (PersonGrade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.polsl.entities.PersonGrade[ id=" + id + " ]";
    }
    
}
