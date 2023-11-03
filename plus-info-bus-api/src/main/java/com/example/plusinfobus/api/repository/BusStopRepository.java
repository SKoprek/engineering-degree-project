package com.example.plusinfobus.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.plusinfobus.api.model.BusStop;

public interface BusStopRepository extends CrudRepository<BusStop, Long>{
	
	BusStop findByStopName(String name);

}