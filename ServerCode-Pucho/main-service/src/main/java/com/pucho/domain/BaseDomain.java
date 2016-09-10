package com.pucho.domain;

/**
 * Created by dinesh.rathore on 22/02/15.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.activejpa.entity.Model;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class BaseDomain extends Model{

	@JsonIgnore
	private Timestamp createdAt;

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	@PrePersist
	protected void preSave() {
		createdAt = new Timestamp(System.currentTimeMillis());
	}

}
