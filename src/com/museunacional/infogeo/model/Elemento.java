package com.museunacional.infogeo.model;

public class Elemento {

	private String name;
	private String unity;
	private Double quantity;
	
	public Elemento(){
		
	}
	
	public String getName() {
		return name;
	}
	public String getUnity() {
		return unity;
	}
	public Double getQuantity() {
		return quantity;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUnity(String unity) {
		this.unity = unity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
}
