package predict;

import java.awt.Point;

public class Function {

	private static double[] constArr = {7.5, 7.5, 7.5, 7.5, 7.5, 7.5, 7.5, 7.5, 7.5, 7.5};
	//k0, k1, a0, b0, a1, b1, a2, b2, a3, b3;

	
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
		return 10*time + constArr[1]*Math.sin(time/constArr[2]) + constArr[3]*Math.cos(time/constArr[4]);
	}
	
	private static double getX(double time){
		return 10*time + constArr[6]*Math.sin(time/constArr[7]) + constArr[8]*Math.cos(time/constArr[9]);
	}

	public static void update(int index, int incValue) {
		constArr[index] += incValue;
	}
}