package com.museunacional.infogeo.model;

import android.annotation.SuppressLint;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressLint("DefaultLocale")
@SuppressWarnings("unused")
public class Analise {
	
	private static final String PPM = "ppm";
	private static final String PER_CENT = "%";
	
	private Integer id;
	private Integer meteorito_id;
	private String analista;
	private Integer ano;
	private Double fe;//pc
	private Double ni;//pc
	private Double co;//pc
	private Double c;//pc
	private Double p;//pc
	private Double fa;//nb
	private Double fs;//nb
	private Double ga;//ppm
	private Double ge;//ppm
	private Double ir;//ppm
	private Double au;//ppm
	private Double as;//ppm
	private Double an;//ppm
	private Double wo;//nd
	private Double cu;//ppm
	private Double s;//ppm
	private Double si;//ppm
	private Double cr;//ppm
	private Double sb;//ppm
	private Double w;//ppm
	private Double re;//ppm
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMeteorito_id() {
		return meteorito_id;
	}

	public void setMeteorito_id(Integer meteorito_id) {
		this.meteorito_id = meteorito_id;
	}

	public String getAnalysis(){
		
		String analysis = new String();
		List<Elemento> elements = new ArrayList<Elemento>();
		
		Field[] fields = getClass().getDeclaredFields();
		for(Field f : fields){
			f.setAccessible(true);
			if(f.getName().length() <= 2 && !f.getName().equals("id")){
				try {
					Double value = (Double) f.get(this);
					if(value != 0){
						Elemento e = new Elemento();
						e.setQuantity(value);
						String name = f.getName();
						e.setName(name.substring(0,1).toUpperCase() + name.substring(1));
						if(name.equals("fe")||
								name.equals("ni")||
								name.equals("co")||
								name.equals("c")||
								name.equals("p")){
							e.setUnity(PER_CENT);
							
						}else if(name.equals("fa")||
								name.equals("fs")||
								name.equals("wo")){
							e.setUnity(null);
						}else{
							e.setUnity(PPM);
						}
						elements.add(e);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}	
		
		Collections.sort(elements, new Comparator<Elemento>() {
		    @Override
		    public int compare(Elemento c1, Elemento c2) {
		    	if(c1.getQuantity() == null){
		    		if(c2.getQuantity() == null){
		    			return c1.getName().compareTo(c2.getName());
		    		}else{
		    			return 1;
		    		}
		    	}
		    	if(c2.getQuantity() == null){
		    			return -1;
		    	}
		        return Double.compare(c1.getQuantity(), c2.getQuantity());
		    }
		});
		
		for(Elemento m : elements){
			if(m.getUnity()!=null){
				analysis += m.getName() + " "+String.format("%.2f", m.getQuantity())+" "+m.getUnity()+"; ";
			}else{
				analysis += m.getName()+"<sub><small><small>"+String.format("%.0f", m.getQuantity())+"</small></small></sub>; ";
			}
		}
		
		if(analista!=null){
			analysis += "(" + analista;
			if(ano != null){
				analysis += " " + String.valueOf(ano);
			}
			analysis += ")";
		}
		
		return analysis;
	}
}
