package predict;

import java.awt.Point;
import java.util.ArrayList;

import gui.GUI;

public class Predict {

	public static void main(String[] args){
		double time = 0;
		try {
			GUI sim = GUI.startSimulation();
			
			while(true){
				Pose p = Function.function(++time);
				sim.update(p.p, p.o, new ArrayList<Point>());
				Thread.sleep(100);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
