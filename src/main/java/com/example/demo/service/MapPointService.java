package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.MapPoint;

@Service
public interface MapPointService {
	
	public ResponseEntity<MapPoint> saveMapPoint(MapPoint mappoint);
	public ResponseEntity<MapPoint> updateMapPoint(MapPoint mappoint);
	ResponseEntity<Optional<MapPoint>> getMapPointById(String id);
	ResponseEntity<String> deleteMapPointById(String id);
	public ResponseEntity<List<MapPoint>> listMapPoints();
}
