package com.pucho.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.minnal.instrument.entity.AggregateRoot;
import org.minnal.instrument.entity.Searchable;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by dinesh.rathore on 22/02/15.
 */
@Entity
@Table(name="users")
@AggregateRoot
public class User extends SoftDeletableModel {
    private Long id;

    private String fullName;
    private String profession;
    private String externalUserId;

    private String provider;
    private String username;
    private String phone;

    @Searchable
    private String email;
    private String password;
    private String shaPassword;
    private String linkedin;
    private String personalUrl;
    private Set<UserEducation>  userEducations;
    private GCMUser gcmUser;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGcmUser(GCMUser gcmUser) {
        this.gcmUser = gcmUser;
    }

    @Override
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Searchable
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    @Column(unique = true, nullable=true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(unique = true, nullable=true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @JsonIgnore
    public String getShaPassword() {
        return shaPassword;
    }

    public void setShaPassword(String shaPassword) {
        this.shaPassword = shaPassword;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getPersonalUrl() {
        return personalUrl;
    }

    public void setPersonalUrl(String personalUrl) {
        this.personalUrl = personalUrl;
    }

    @OneToMany(fetch=FetchType.EAGER,cascade= CascadeType.ALL, mappedBy = "userId")
    public Set<UserEducation> getUserEducations() {
        return userEducations;
    }
    

    public void setUserEducations(Set<UserEducation> userEducations) {
        this.userEducations = userEducations;
    }

    @OneToOne(optional = true)
    @JoinColumn(name = "gcmuser_id", nullable = true)
    public GCMUser getGcmUser() {
        return this.gcmUser;
    }
}
