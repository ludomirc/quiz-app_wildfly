package org.qbit.quiz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractPersistentObject
        implements PersistentObject, Serializable {

    @Id
    @Column(name = "ID")
    private String id = IdGenerator.createId();

    @Column(name = "VERSION")
    private Integer version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ||
                !(o instanceof PersistentObject)) {

            return false;
        }

        PersistentObject other
                = (PersistentObject) o;

        // if the id is missing, return false
        if (id == null) return false;

        // equivalence by id
        return id.equals(other.getId());
    }

    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        } else {
            return super.hashCode();
        }
    }

    @Override
    public String toString() {
        return "AbstractPersistentObject{" +
                "id='" + id + '\'' +
                ", version=" + version +
                '}';
    }
}