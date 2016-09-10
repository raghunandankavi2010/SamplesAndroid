package com.pucho.domain;

/**
 * Created by dinesh.rathore on 22/02/15.
 */

import org.minnal.instrument.entity.Searchable;

import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class SoftDeletableModel extends BaseDomain {
	
	@Searchable
	private Boolean active = true;
	
	@Override
	public void delete() {
		this.active = false;
		persist();
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
