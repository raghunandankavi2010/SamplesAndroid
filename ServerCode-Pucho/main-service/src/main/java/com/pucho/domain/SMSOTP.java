package com.pucho.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="phone_otp")
public class SMSOTP extends SoftDeletableModel{

    private Long id;
    
    @Override
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return this.id;
    }
    
    private String phone;
    
    private String code;
    
    private Boolean consumed;

    
    public String getPhone() {
        return this.phone;
    }

    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    
    public String getCode() {
        return this.code;
    }

    
    public void setCode(String code) {
        this.code = code;
    }

    
    public Boolean getConsumed() {
        return this.consumed;
    }

    
    public void setConsumed(Boolean consumed) {
        this.consumed = consumed;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

}
