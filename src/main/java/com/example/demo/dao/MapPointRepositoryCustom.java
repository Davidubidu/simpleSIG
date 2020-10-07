package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.example.demo.model.MapPoint;

public interface MapPointRepositoryCustom {
	
	ResponseEntity<List<MapPoint>> findMapPointsByParms(String filter);
}
