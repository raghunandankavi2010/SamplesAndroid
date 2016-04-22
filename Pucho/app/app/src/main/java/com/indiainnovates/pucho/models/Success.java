package com.indiainnovates.pucho.models;

/**
 * Created by Raghunandan on 19-12-2015.
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Success {


    @Expose
    private Boolean active;

    @Expose
    private Integer id;

    @Expose
    private String fullName;


    @Expose
    private String profession;


    @Expose
    private String externalUserId;


    @Expose
    private String username;


    @Expose
    private List<Object> userEducations = new ArrayList<Object>();

    /**
     *
     * @return
     * The active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     *
     * @param active
     * The active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName
     * The full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * @return
     * The profession
     */
    public String getProfession() {
        return profession;
    }

    /**
     *
     * @param profession
     * The profession
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     *
     * @return
     * The externalUserId
     */
    public String getExternalUserId() {
        return externalUserId;
    }

    /**
     *
     * @param externalUserId
     * The external_user_id
     */
    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The userEducations
     */
    public List<Object> getUserEducations() {
        return userEducations;
    }

    /**
     *
     * @param userEducations
     * The user_educations
     */
    public void setUserEducations(List<Object> userEducations) {
        this.userEducations = userEducations;
    }

}
