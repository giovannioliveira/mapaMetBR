package ufrj.museunacional.infogeo.util;

public class GeneralUtil {
	
	public static String massConversion(Double mass){
		if(mass<1){
			mass*=1000;
			return String.format("%.2fg", mass);
		}else if(mass<1000){
			return String.format("%.2fKg", mass);
		}else{
			mass/=1000;
			return String.format("%.2fton.", mass);
		}
	}

}
