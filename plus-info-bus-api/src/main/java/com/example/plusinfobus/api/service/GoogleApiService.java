package com.example.plusinfobus.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.plusinfobus.api.Constans;
import com.example.plusinfobus.api.model.BusStop;
import com.example.plusinfobus.api.model.BusTransit;
import com.example.plusinfobus.api.model.TransitFullInfo;
import com.example.plusinfobus.api.model.GoogleApi.transit.GoogleApiTransit;
import com.example.plusinfobus.api.model.GoogleApi.transit.Step;
import com.example.plusinfobus.api.repository.BusStopRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GoogleApiService {
	private final BusStopRepository busStopRepository;	
	public GoogleApiService(BusStopRepository busStopRepository) {
		this.busStopRepository = busStopRepository;
	}
	public TransitFullInfo getGoogleApiTransit(String nameOrigin,
											String nameDestination,
											long timeEpoh) throws UnsupportedEncodingException, IOException{
		int limit = 7;
		int controlPoint = 0;
		String stringURL = "";
		long timeEpohNext = 0;
		TransitFullInfo transitFullInfo = new TransitFullInfo();
		transitFullInfo.setDeparture_stop(nameOrigin);
		transitFullInfo.setArrival_stop(nameDestination);
		BusStop busStopOrigin = busStopRepository.findByStopName(nameOrigin);
		BusStop busStopDestination = busStopRepository.findByStopName(nameDestination);
		if(busStopOrigin==null || busStopDestination==null) {
			return null;
			}else {
				List<List<BusTransit>> busTransitsList = new ArrayList<>();
					for (int i=0; i<limit; i++){
				        if(controlPoint == 0){
				            stringURL = Constans.DIRECTION_URL_API+ Constans.ORIGIN+ busStopOrigin.getLat()+ ","+busStopOrigin.getLng()
				                    + Constans.DESTINATION+ busStopDestination.getLat()+ ","+ busStopDestination.getLng()
				                    + Constans.DEPARTURE_TIME+ timeEpoh+ Constans.GOOGLE_APP_KEY;
				            controlPoint++;
				        }else{
				        	timeEpohNext++;
				            stringURL = Constans.DIRECTION_URL_API+ Constans.ORIGIN+ busStopOrigin.getLat()+ ","+busStopOrigin.getLng()
				                    + Constans.DESTINATION + busStopDestination.getLat()+ ","+ busStopDestination.getLng()
				                    + Constans.DEPARTURE_TIME+timeEpohNext+ Constans.GOOGLE_APP_KEY;
				        }					
				String jsonData = getJSONfromURL(stringURL);
			    GoogleApiTransit googleApiTransit = new GoogleApiTransit();
			    ObjectMapper mapper = new ObjectMapper();
			    googleApiTransit = mapper.readValue(jsonData, GoogleApiTransit.class);
			    for(int i1=0; i1<googleApiTransit.getRoutes().size();i1++){
        			List<BusTransit> busTransits = new ArrayList<>();	
			        for(int i2=0; i2<googleApiTransit.getRoutes().get(i1).getLegs().size();i2++)
			          {
			        for (int i3=0; i3<googleApiTransit.getRoutes().get(i1).getLegs().get(i2).getSteps().size();i3++){
			        String travel_mode = googleApiTransit.getRoutes().get(i1).getLegs().get(i2).getSteps().get(i3).getTravel_mode();
			        if(Constans.TRAVEL_MODE.equals(travel_mode)){
			        int timeControlPoint = 0;
			        System.out.println("+Check travel_mode: " + travel_mode + " ID:" + i3);
			        Step step = googleApiTransit.getRoutes().get(i1).getLegs().get(i2).getSteps().get(i3);
			        BusTransit busTransit = new BusTransit();
			        busTransit.setDeparture_stop_text(step.getTransit_details().getDeparture_stop().getName());
			        busTransit.setArrival_stop_text(step.getTransit_details().getArrival_stop().getName());
			        busTransit.setTimeFromEpoch(step.getTransit_details().getDeparture_time().getValue());
			        busTransit.setDistance_text(step.getDistance().getText());
			        busTransit.setDuration_text(step.getDuration().getText());
			        busTransit.setDeparture_time_text(step.getTransit_details().getDeparture_time().getText());
			        busTransit.setArrival_time_text(step.getTransit_details().getArrival_time().getText());
			        busTransit.setAgencies_name(step.getTransit_details().getLine().getAgencies().get(0).getName());
				        if(timeControlPoint==0){
				       		timeControlPoint++;
				        	timeEpohNext = step.getTransit_details().getDeparture_time().getValue();
				        	}
			        	busTransits.add(busTransit);
			            }
			        }
			       }										        					    
			       busTransitsList.add(busTransits);				   
			    } 
			}
			transitFullInfo.setBusTransit(busTransitsList);
		}
	return transitFullInfo;
	}	
	private String getJSONfromURL(String stringURL) throws UnsupportedEncodingException, IOException {
			 URL url;					    
				url = new URL(stringURL);
		    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));	    
		    String jsonData = "";
		    String line= "";
	     while(line !=null){
	         jsonData = jsonData+line;
	         line = bufferedReader.readLine();
	     }
	     return jsonData;
	}
}
