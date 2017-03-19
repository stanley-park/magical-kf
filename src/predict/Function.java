package predict;

import java.awt.Point;

public class Function {
	
	
	public static Pose function(double time){
		Point p = new Point((int) (10*time),(int)(100*Math.sin(time/10)) );
		double dy = 100*(Math.sin(time/10)-Math.sin((time-0.01)/10));
		double dx = 10*(0.01);
		
		double o = -Math.atan2(dy,dx);
		return new Pose(p, o);
	}
}