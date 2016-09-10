package com.pucho.domain;

import javax.persistence.*;

/**
 * Created by dinesh.rathore on 12/04/16.
 */
@Entity
@Table(name = "user_followers")
@EntityListeners(value = {UserFollowerEventLIstener.class})
public class UserFollower extends SoftDeletableModel {

    private Long id;
    private Long followedUserId;
    private Long followerUserId;

    public void setFollowedUserId(Long followedUserId) {
        this.followedUserId = followedUserId;
    }

    public Long getFollowedUserId() {
        return followedUserId;
    }

    public void setFollowerUserId(Long followerUserId) {
        this.followerUserId = followerUserId;
    }

    public Long getFollowerUserId() {
        return followerUserId;
    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
