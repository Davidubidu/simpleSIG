package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MapPointDAO;
import com.example.demo.model.MapPoint;
import com.example.demo.security.services.UserDetailsImpl;

@Service
@Component
public class MapPointServiceImpl implements MapPointService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired 
	MapPointDAO dao;

	@Override
	public ResponseEntity<MapPoint> saveMapPoint(MapPoint mappoint) {
		HttpStatus response;
		if(dao.findMapPointByxCoordinateAndYCoordinateAndHeightAndTypeIgnoreCase(mappoint.getxCoordinate(), mappoint.getyCoordinate(), 
				mappoint.getHeight(),mappoint.getType()).isPresent()) {
			logger.info("That map point is already saved in the database");
			response = HttpStatus.FORBIDDEN; // 403
		} else {
			try {
				dao.save(mappoint);
				logger.info("map point added");
				response = HttpStatus.OK; // 200
			} catch(Exception e) {
				logger.error(e.toString());
				response = HttpStatus.INTERNAL_SERVER_ERROR; // 500
			}
		}
		
		return new ResponseEntity<MapPoint>(mappoint, response); 
	}

	@Override
	public ResponseEntity<MapPoint> updateMapPoint(MapPoint mappoint) {
		HttpStatus response;
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
	            .getPrincipal();
		try {
			if (userDetails.getAuthorities().toString().contains("ROLE_ADMIN") || userDetails.getAuthorities().toString().contains("ROLE_MOD") ||
					mappoint.getownername().equals(userDetails.getUsername())) {	
			logger.info("map point"+ mappoint.getType() +"updated:");
			dao.save(mappoint);
			response = HttpStatus.OK; //200
			} else {
				logger.info("you are not the owner of this map point and you dont have the authorities to update it");
				response = HttpStatus.FORBIDDEN; // 403
			}
		} catch (Exception e) {
			logger.error(e.toString());
			response = HttpStatus.INTERNAL_SERVER_ERROR; //500
		}		
		return new ResponseEntity<MapPoint>(mappoint, response);
	}

	@Override
	public ResponseEntity<Optional<MapPoint>> getMapPointById(String id) {
		HttpStatus response;
		Optional<MapPoint> m;
		try {
			logger.info("map point found:");
			m = dao.findById(id);
			response = HttpStatus.OK; //200
		} catch (Exception e) {
			logger.error(e.toString());
			m = null;
			response = HttpStatus.INTERNAL_SERVER_ERROR; //500
		}
		
		return new ResponseEntity<Optional<MapPoint>>(m, response);
	}

	@Override
	public ResponseEntity<String> deleteMapPointById(String id) {
		HttpStatus response;
		String msg;
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
	            .getPrincipal();
		Optional<MapPoint> m = dao.findById(id);
		try {
			if (userDetails.getAuthorities().toString().contains("ROLE_ADMIN") || userDetails.getAuthorities().toString().contains("ROLE_MOD") ||
					m.get().getownername().equals(userDetails.getUsername())) {	
				dao.deleteById(id);
				logger.info("map point deleted");
				response = HttpStatus.OK; //200
				msg = "map point deleted correctky";
			} else {
				logger.info("you are not the owner of this map point and dont have the authorities to delete it");
				response = HttpStatus.FORBIDDEN; // 403
				msg = "unauthorized";
			}
		}	catch(Exception e) {
			logger.error(e.toString());
			response = HttpStatus.INTERNAL_SERVER_ERROR; //500
			msg = "an error happened";
		}
		return new ResponseEntity<String>(msg, response);
	}

	@Override
	public ResponseEntity<List<MapPoint>> listMapPoints() {
		HttpStatus response;
		List<MapPoint> points;
		try {
			logger.info("here they are:");
			points = dao.findAll();
			response = HttpStatus.OK; //200
		}	catch (Exception e) {
			logger.error(e.toString());
			points = new ArrayList<MapPoint>();
			response = HttpStatus.INTERNAL_SERVER_ERROR; //500
		}
		return new ResponseEntity<List<MapPoint>>(points, response);
	}

	@Override
	public ResponseEntity<List<MapPoint>> listOwnedMapPoints() {
		HttpStatus response;
		List<MapPoint> points;
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
	            .getPrincipal();
		String ownername = userDetails.getUsername();
		try {
			logger.info("here they are:");
			points = dao.findMapPointByOwnername(ownername);
			response = HttpStatus.OK; //200
			
		}	catch(Exception e) {
			logger.error(e.toString());
			points = new ArrayList<MapPoint>();
			response = HttpStatus.INTERNAL_SERVER_ERROR; //500
		}
		return new ResponseEntity<List<MapPoint>>(points, response);
	}

	@Override
	public ResponseEntity<List<MapPoint>> listUserMapPoints(String ownername) {
		HttpStatus response;
		List<MapPoint> points;
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
	            .getPrincipal();
		try {
			if (userDetails.getAuthorities().toString().contains("ROLE_ADMIN") || userDetails.getAuthorities().toString().contains("ROLE_MOD")
					|| ownername == userDetails.getUsername()) {				
				logger.info("here they are:");
				points = dao.findMapPointByOwnername(ownername);
				response = HttpStatus.OK; //200
			} else {
				logger.info("here they are:");
				points = dao.findMapPointByOwnernameAndVisible(ownername, true);
				response = HttpStatus.OK; //200
			}
			
		}	catch(Exception e) {
			logger.error(e.toString());
			points = new ArrayList<MapPoint>();
			response = HttpStatus.INTERNAL_SERVER_ERROR; //500
		}
		return new ResponseEntity<List<MapPoint>>(points, response);
	}

}
