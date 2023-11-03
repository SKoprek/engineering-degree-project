package com.example.plusinfobus.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.plusinfobus.api.model.BusStop;
import com.example.plusinfobus.api.repository.BusStopRepository;
import com.example.plusinfobus.api.service.BusStopService;

@RestController
@RequestMapping("/api")
public class BusStopController {
private final BusStopRepository busStopRepository;
private final BusStopService busStopService;
	
	public BusStopController(BusStopRepository busStopRepository,
			BusStopService busStopService) {
		this.busStopRepository = busStopRepository;
		this.busStopService = busStopService;
	}
	
	
	//GET
	@RequestMapping(value="/busstop", method=RequestMethod.GET)
	public BusStop getBusStopById(@PathVariable("id") Long id){
		BusStop busStop = new BusStop();
		busStop = busStopRepository.findById(id).orElse(null);
		return busStop;
	}
	@RequestMapping(value="/search/busstop", method=RequestMethod.GET)
	public List<BusStop> getBusStopByName(@RequestParam("query") String query){
		List<BusStop> busStops = new ArrayList<>();
		busStops = busStopService.search(query);
		
		return busStops;
	}
	@RequestMapping(value="/busstops", method=RequestMethod.GET)
	public List<BusStop> getAllBusStop(){
		List<BusStop> busStops = new ArrayList<>();
		busStops = (List<BusStop>) busStopRepository.findAll();
		return busStops;
	}
	//POST
	
	@RequestMapping(value="/busstop", method = RequestMethod.POST)
	public BusStop insertBusStop(@RequestParam String name,
			@RequestParam double lat,
			@RequestParam double lng){
		BusStop busStop = new BusStop();
		
		busStop.setStopName(name);
		busStop.setLat(lat);
		busStop.setLng(lng);
		
		busStopRepository.save(busStop);
		return busStop;
	}
}
