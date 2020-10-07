package com.example.demo.dao;

public class DynamicQuery {
	//in this class we will define parameters to use in future mongo template queries
	private int[] xCoordinates;
	private int[] yCoordinates;
	private int[] heights;	
	private String[] types;
	private String[] description;
	private String[] ownernames;
	
	
	public int[] getxCoordinates() {
		return xCoordinates;
	}
	public void setxCoordinates(int[] xCoordinates) {
		this.xCoordinates = xCoordinates;
	}
	public int[] getyCoordinates() {
		return yCoordinates;
	}
	public void setyCoordinates(int[] yCoordinates) {
		this.yCoordinates = yCoordinates;
	}
	public int[] getHeights() {
		return heights;
	}
	public void setHeights(int[] heights) {
		this.heights = heights;
	}
	public String[] getTypes() {
		return types;
	}
	public void setTypes(String[] types) {
		this.types = types;
	}
	public String[] getDescription() {
		return description;
	}
	public void setDescription(String[] description) {
		this.description = description;
	}
	public String[] getOwnernames() {
		return ownernames;
	}
	public void setOwnernames(String[] ownernames) {
		this.ownernames = ownernames;
	}
	public Boolean[] getAreVisible() {
		return areVisible;
	}
	public void setAreVisible(Boolean[] areVisible) {
		this.areVisible = areVisible;
	}
	private Boolean[] areVisible;
}
