package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
 
// Mongo database annotation.
@Document(collection= "MapPoints")
public class MapPoint {
	
	@Id
	private String id;
	private int xCoordinate;
	private int yCoordinate;
	private int height;
	private String type;
	private String description;
		
	public MapPoint(int xCoordinate, int yCoordinate, int height, String type, String description) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.height = height;
		this.type = type;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getxCoordinate() {
		return xCoordinate;
	}
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public int getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MapPoint [id=");
		builder.append(id);
		builder.append(", xCoordinate=");
		builder.append(xCoordinate);
		builder.append(", yCoordinate=");
		builder.append(yCoordinate);
		builder.append(", height=");
		builder.append(height);
		builder.append(", type=");
		builder.append(type);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}
}
