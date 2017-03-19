package predict;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import gui.GUI;
import socket.RNNternet;

public class Predict {

	private static final int PAST_LENGTH = 5; 
	private static LinkedList<Point> Past = new LinkedList<Point>();		
	
	public static void main(String[] args){

		double time = 0;
		
		for( int i = 0; i <PAST_LENGTH; ++i){
			Past.push( Function.function(++time).p );
		}
		
		PredictionNode rnn = new RNNternet();
		
		try {
			GUI sim = GUI.startSimulation();
			
			while(true){
				Pose p = Function.function(++time);
				Past.removeLast();
				Past.add(p.p);
				
				ArrayList<Point> prediction = rnn.predict(Past);
				
				sim.update(p.p, p.o, prediction);
				Thread.sleep(100);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
