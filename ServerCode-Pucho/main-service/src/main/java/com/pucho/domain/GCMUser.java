package com.pucho.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="gcmuser")
public class GCMUser extends BaseDomain {

    private Long id;
    
    public void setId(Long id) {
        this.id = id;
    }

    private String registrationId;
    

    @Column(name="reg_id")
    public String getRegistrationId() {
        return this.registrationId;
    }

    
    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    @Override
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long getId() {
        return id;
    }

}
