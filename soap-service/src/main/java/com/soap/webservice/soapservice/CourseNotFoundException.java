package com.soap.webservice.soapservice;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class CourseNotFoundException extends RuntimeException {

	public CourseNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
