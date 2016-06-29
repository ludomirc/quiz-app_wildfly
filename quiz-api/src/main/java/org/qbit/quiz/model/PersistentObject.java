package org.qbit.quiz.model;

import javax.persistence.Id;

public interface PersistentObject {

    public String getId();
    public void setId(String id);

    public Integer getVersion();
    public void setVersion(Integer version);
}