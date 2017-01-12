package org.usfirst.frc.team2996.robot;

public class Threshold {
	   public static double threshold(double x){
	    	if(Math.abs(x) >0.15){
	    		return x;
	    	}else{
	    		return 0;
	    	}
	    }
	   public static double threshold(double x, double threshold){
	    	if(Math.abs(x) > threshold){
	    		return x;
	    	}else{
	    		return 0;
	    	}
	    }
}
