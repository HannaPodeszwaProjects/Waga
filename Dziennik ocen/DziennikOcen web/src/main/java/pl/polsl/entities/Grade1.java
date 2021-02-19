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

/**
 * Represents grade in the database
 * 
 * @author Hanna Podeszwa
 * @version 2.1
 */
@Entity
@Table(name = "GRADES")
@NamedQueries({
    @NamedQuery(name = "Grade1.findAll", query = "SELECT g FROM Grade1 g"),
    @NamedQuery(name = "Grade1.findById", query = "SELECT g FROM Grade1 g WHERE g.id = :id"),
    @NamedQuery(name = "Grade1.findByGrade", query = "SELECT g FROM Grade1 g WHERE g.grade = :grade")})
public class Grade1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
     @GeneratedValue
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "GRADE")
    private Double grade;
    @OneToMany(mappedBy = "gradeId", fetch = FetchType.LAZY)
    private Collection<PersonGrade> personGradeCollection;

    public Grade1() {
    }

    public Grade1(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Collection<PersonGrade> getPersonGradeCollection() {
        return personGradeCollection;
    }

    public void setPersonGradeCollection(Collection<PersonGrade> personGradeCollection) {
        this.personGradeCollection = personGradeCollection;
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
        if (!(object instanceof Grade1)) {
            return false;
        }
        Grade1 other = (Grade1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.polsl.entities.Grade1[ id=" + id + " ]";
    }
    
}
