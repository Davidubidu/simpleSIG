package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.omg.CORBA.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DynamicQuery;
import com.example.demo.dao.MapPointDAO;
import com.example.demo.dao.MapPointRepositoryCustom;
import com.example.demo.model.MapPoint;
import com.example.demo.security.services.UserDetailsImpl;

@Service
@Component
public class MapPointServiceImpl implements MapPointService, MapPointRepositoryCustom {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public MapPointServiceImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Autowired 
	MapPointDAO dao;
	
	@Override
	public ResponseEntity<List<MapPoint>> findMapPointsByParms(String filter) {
		HttpStatus response;
		List<MapPoint> points;
		
		final BasicQuery query = new BasicQuery(filter);
				
		try {
			
			points = mongoTemplate.find(query, MapPoint.class);				
			response = HttpStatus.OK; //200
			
		}	catch(Exception e) {
			logger.error(e.toString());
			points = new ArrayList<MapPoint>();
			response = HttpStatus.INTERNAL_SERVER_ERROR; //500
		}
		
		return new ResponseEntity<List<MapPoint>>( points, response);
	}
	
	
	
	@Override
	public ResponseEntity<List<MapPoint>> getMapPointsByParams(Map<String, String[]> data) {
		HttpStatus response;
		List<MapPoint> points;
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
	            .getPrincipal();
		final Query query = new Query();
		final List<Criteria> criteria = new ArrayList<>();
		
		Boolean check = ( data != null ) ? true : false;
		int x[] = {};
		int y[] = {};
		int h[] = {};
		String[] types = null;
		String[] descriptions = null;
		String[] ownername = null;
		Boolean[] visible = {};
		
		if (check) {
			
			x = (data.get("xCoordinate") != null) ? Arrays.stream(data.get("xCoordinate")).mapToInt(Integer::parseInt).toArray() : null;			
			if (x != null) criteria.add(Criteria.where("xCoordinate").in(Arrays.asList(intToInt(x))));
			
			y = (data.get("yCoordinate") != null) ? Arrays.stream(data.get("yCoordinate")).mapToInt(Integer::parseInt).toArray() : null;
			if (y != null) criteria.add(Criteria.where("yCoordinate").in(Arrays.asList(intToInt(y))));
			
			h = (data.get("height") != null) ? Arrays.stream(data.get("height")).mapToInt(Integer::parseInt).toArray() : null;
			if (h != null) criteria.add(Criteria.where("height").in(Arrays.asList(intToInt(h))));
			
			types = (data.get("type") != null) ? data.get("type") : null;
			if (types!=null) criteria.add(Criteria.where("type").in(Arrays.asList(types)));
			
			descriptions =  (data.get("description") != null) ? data.get("description") : null;
			if (descriptions!=null) Arrays.asList(descriptions).forEach((description) -> criteria.add(Criteria.where("description").regex(description)));
			
			ownername = (data.get("ownername") != null) ? data.get("ownername"): null;
			if (ownername!=null) criteria.add(Criteria.where("ownername").in(Arrays.asList(ownername)));
			
			visible = (data.get("visible") != null) ? Arrays.stream(data.get("visible")).map(Boolean::parseBoolean).toArray(Boolean[]::new) : new Boolean[] {false};
			if (visible!=null) criteria.add(Criteria.where("visible").in(Arrays.asList(visible)));
			
		}
		
		
		Boolean checkOwner = (check && data.get("ownername") != null) ? data.get("ownername").toString().equals(userDetails.getUsername()) : false;
		
		if (userDetails.getAuthorities().toString().contains("ROLE_ADMIN") || userDetails.getAuthorities().toString().contains("ROLE_MOD")
				|| checkOwner) {					
			
			/**
			criteria.add(Criteria.where("visible").in(Arrays.asList(visible)));
			
			the addition of criteria to the query is done here because until the checking of the authorization/ownership the value of the visible param is not
			 representative of the user's access to the map points  
			*/ 
			
			if(!criteria.isEmpty()) {
				query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
			}
			
			logger.info("query: " + query.toString());
			points = mongoTemplate.find(query, MapPoint.class);
			response = HttpStatus.OK; //200
		} else {
						
			
			criteria.add(Criteria.where("visible").is(new Boolean[] {true}));
			
			if(!criteria.isEmpty()) {
				query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
			}
			
			points = mongoTemplate.find(query, MapPoint.class);				
			response = HttpStatus.OK; //200
		}
		
		return new ResponseEntity<List<MapPoint>>(points, response);
	}

	
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

	private final Integer[] intToInt(int[] num) {
		
		Integer[] n = new Integer[num.length];
		
		for (int i = 0; i < num.length; i++){
			n[i] = new Integer(num[i]);
			logger.info(n[i].toString());
		}	
		
		return n;
	}

	
	
}
