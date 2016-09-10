package com.pucho.helper;

import com.pucho.domain.SMSAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SMSWorkerRequest {
	private SMSAction smsAction;
	private String serializedEntity;

}
