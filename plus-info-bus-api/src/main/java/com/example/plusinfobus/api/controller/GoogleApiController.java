package com.example.plusinfobus.api.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.plusinfobus.api.model.TransitFullInfo;
import com.example.plusinfobus.api.service.GoogleApiService;

@RestController
@RequestMapping("/api")
public class GoogleApiController {
	private final GoogleApiService googleApiService;
	public GoogleApiController(GoogleApiService googleApiService) {
		this.googleApiService = googleApiService;
	}
	
	@RequestMapping(value="/google-api-transit", method=RequestMethod.GET)
	public TransitFullInfo getGoogleAPITransit(
										@RequestParam("nameOrigin") String nameOrigin,
										@RequestParam("nameDestination") String nameDestination,
										@RequestParam("timeEpoh") long timeEpoh
					){
		TransitFullInfo data = new TransitFullInfo();
		try {
			data = googleApiService.getGoogleApiTransit(nameOrigin, nameDestination, timeEpoh);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error Not Found2");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error Not Found3");
			e.printStackTrace();
		}
		return data;
	}
}
