package com.example.demo.control;

import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.MapPoint;
import com.example.demo.service.MapPointServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value= "/api/MapPoints")
@Component
public class MapPointRestController {
	
	@Autowired 
	MapPointServiceImpl serv;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping(value= "/Insert")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<MapPoint> insert(@RequestBody MapPoint mp) {
		logger.info("saving map point...");
		return serv.saveMapPoint(mp);		
	}
	
	@GetMapping(value= "/getall")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<MapPoint>> getAll() {
        logger.info("Getting all map points.");
        return serv.listMapPoints();
	}
	
	@GetMapping(value= "/get/{MapPoint-id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Optional<MapPoint>> getById(@PathVariable(value= "MapPoint-id") String id) {
		logger.info("getting map point "+ id +"...");
		return serv.getMapPointById(id);
	}
	
	@PutMapping(value= "/update/{MapPoint-id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<MapPoint> update(@PathVariable(value= "MapPoint-id") String id, @RequestBody MapPoint mp) {
        logger.info("Updating map point "+ id +"...");
        mp.setId(id); 
        logger.info("map point "+ id +" updated");
        return serv.updateMapPoint(mp);
    }
	
	@DeleteMapping(value= "/delete/{MapPoint-id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable(value= "MapPoint-id") String id) {
        logger.info("Deleting map point with map point-id "+ id +"...");       
        return serv.deleteMapPointById(id);
    }
	
	
}
