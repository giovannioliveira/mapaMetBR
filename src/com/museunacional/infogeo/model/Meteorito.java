package com.museunacional.infogeo.model;

import ufrj.museunacional.infogeo.util.GeneralUtil;

public class Meteorito {
	
	private Integer id;
	private String nome;
	private String fato;
	private String uf;
	private String ano;
	private String tipo;
	private String classe;
	private String grupo;
	private Double latitude;
	private Double longitude;
	private Double massa;
	private String massa_observacoes;
	private String observacoes;
	private String informacoes;
	private String image_url;
	
	public Integer getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getFato(){
		return fato.equals("a") ? "Achado" : "Queda";
	}
	public String getUf() {
		return uf;
	}
	public String getData() {
		if(ano == null || ano.length() == 0){
			return null;
		}
		switch(ano.length()){
		case 4:
			return String.valueOf(ano);
		case 7:
			return ano.substring(5)+"/"+ano.substring(0,4); 
		case 10:
			return ano.substring(8)+"/"+ano.substring(5, 7)+"/"+ano.substring(0, 4);
		default:
			return null;
		}
	}
	public String getTipo() {
		return tipo;
	}
	public String getClasse() {
		return classe;
	}
	public String getGrupo() {
		return grupo;
	}
	public Double getLatitude() {
		return latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public Double getMassa() {
		return massa;
	}
	public String getMassa_observacao() {
		return massa_observacoes;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public String getInformacoes() {
		return informacoes;
	}
	public String getImage_url() {
		return image_url;
	}
	
	public String getInfoMass(){
		String result = new String();
		if(massa == 0){
			result = massa_observacoes;
		}else{
			result = GeneralUtil.massConversion(massa);
			if(massa_observacoes!=null){
				result+= " ("+massa_observacoes+")";
			}
		}
		return result;
	}
	
	
}
