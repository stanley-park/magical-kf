package predict;

import java.awt.Point;

public class Function {
	
	public static Pose function(double time){
		double x = getX(time);
		double y = getY(time);
		double dx = x-getX(time-0.01);
		double dy = y-getY(time-0.01);
		
		Point p = new Point((int)(x),(int)(y));
		double o = Math.atan2(dy,dx);
		
		return new Pose(p, -o);
	}
	
	private static double getY(double time){
		return 100*Math.sin(time/10) + 50*Math.sin(time/3);
	}
	
	private static double getX(double time){
		return 10*time;
	}
}