package predict;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import gui.GUI;
import socket.Socknet;

public class main {

	private static final int PAST_LENGTH = 5; 
	private static LinkedList<Point> Past = new LinkedList<Point>();		
	
	public static void main(String[] args){

		double time = 0;
		
		for( int i = 0; i <PAST_LENGTH; ++i){
			Past.add( Function.function(++time).p );
		}
		
		PredictionNode predict = new Socknet();
		
		try {
			GUI sim = GUI.startSimulation();
			
			while(true){
				Pose p = Function.function(++time);
				Past.removeFirst();
				Past.add(p.p);
				
				ArrayList<Point> prediction = predict.predict(Past);
				
				sim.update(p.p, p.o, prediction);
				Thread.sleep(100);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
