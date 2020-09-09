package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
 
import com.example.demo.model.MapPoint;

@Repository
@Component
public interface MapPointDAO extends MongoRepository<MapPoint, String> {
	@Query("{'xCoordinate': ?0, 'yCoordinate': ?1, 'height': ?2, 'type': ?3}")
	Optional<MapPoint> findMapPointByxCoordinateAndYCoordinateAndHeightAndTypeIgnoreCase(int xCoordinate, int yCoordinate, int height, String type);
	//Optional<List<MapPoint>> findByxCoordinateAndYCoordinateAndHeightAndTypeIgnoreCase(int xCoordinate, int yCoordinate, int height, String type);
	//Optional<List<MapPoint>> findByType(String type);
	List<MapPoint> findMapPointByOwnername(String ownername);
	List<MapPoint> findMapPointByOwnernameAndVisible(String ownername, boolean isVisible);
}
