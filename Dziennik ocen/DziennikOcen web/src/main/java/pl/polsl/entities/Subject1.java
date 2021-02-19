package pl.polsl.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Represents subject in the database
 * 
 * @author Hanna Podeszwa
 * @version 2.1
 */
@Entity
@Table(name = "SUBJECTS")
@NamedQueries({
    @NamedQuery(name = "Subject1.findAll", query = "SELECT s FROM Subject1 s"),
    @NamedQuery(name = "Subject1.findById", query = "SELECT s FROM Subject1 s WHERE s.id = :id"),
    @NamedQuery(name = "Subject1.findByName", query = "SELECT s FROM Subject1 s WHERE s.name = :name")})
public class Subject1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;

    public Subject1() {
    }

    public Subject1(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(object instanceof Subject1)) {
            return false;
        }
        Subject1 other = (Subject1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.polsl.entities.Subject1[ id=" + id + " ]";
    }
    
}
