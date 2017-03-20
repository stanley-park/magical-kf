package predict;

import java.awt.Point;

public class Function {

<<<<<<< HEAD
	private static double[] constArr = {7.5, 7.5, 7.5, 7.5, 7.5, 7.5, 7.5, 7.5, 7.5, 7.5};
=======
	public static double[] constArr = {7.5, 107.5, 17.5, 57.5, 7.5, 7.5, 7.5, 7.5, 7.5, 7.5};
>>>>>>> 3cd40d9db119faa866437cff272d09ecdf042348
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
<<<<<<< HEAD
		return 10*time + constArr[1]*Math.sin(time/constArr[2]) + constArr[3]*Math.cos(time/constArr[4]);
=======
		return constArr[1]*Math.sin(time/constArr[2]) + constArr[3]*Math.cos(time/constArr[4]);
>>>>>>> 3cd40d9db119faa866437cff272d09ecdf042348
	}
	
	private static double getX(double time){
		return 10*time + constArr[6]*Math.sin(time/constArr[7]) + constArr[8]*Math.cos(time/constArr[9]);
	}

	public static void update(int index, int incValue) {
		constArr[index] += incValue;
	}

	public static void update(int index, int incValue) {
		constArr[index] += incValue;
	}
}