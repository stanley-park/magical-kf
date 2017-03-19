package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUI extends JPanel{
	
	//ratio to increase window size
	private final static int RATIO = 2;
	
	//variables for Window
	private final static int HEIGHT = 700*RATIO;
	private final static int WIDTH = 600*RATIO;
	private final static int H_MIDDLE = HEIGHT/2;
	private final static int W_MIDDLE = WIDTH/2;
	
	// variables for objects
	private final static int CIRCLE_RADIUS = 25;
	private final static Color CIRCLE_COLOR = Color.white;
	private final static int NOSE_LENGTH = (int) (CIRCLE_RADIUS*1.5);
	private final static Color FOOTPRINT_COLOR = Color.green;
	private final static int FOOTPRINT_LENGTH = 500;
	private final static Color PREDICTION_COLOR = Color.red;
	
	// update variables
	private static LinkedList<Point> QUEUE = new LinkedList<Point>();
	private static Point DOT = new Point(0,0);
	private static double ORIENTATION = 0;
	private static ArrayList<Point> PREDICTION = new ArrayList<Point>();
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(Color.BLACK);
		drawDot(g);
		drawFootprints(g);
		drawPrediction(g);
		drawTicks(g);
	}

	private void drawTicks(Graphics g) {
		// TODO Auto-generated method stub	
	}

	private void drawPrediction(Graphics g) {
		g.setColor(PREDICTION_COLOR);
		Iterator<Point> iter = PREDICTION.iterator();
		int px, py, x, y;
		while(iter.hasNext()){
			Point past = iter.next();
			px = (int) (past.getX() - DOT.getX());
			py = (int) (past.getY() - DOT.getY());
			if(!iter.hasNext())
				break;
			Point current = iter.next();
			x = (int) (current.getX() - DOT.getX());
			y = (int) (current.getY() - DOT.getY());
			
			g.drawLine(W_MIDDLE + px,
					H_MIDDLE + py,
					W_MIDDLE + x,
					H_MIDDLE + y);
		}
	}

	private void drawFootprints(Graphics g) {
		g.setColor(FOOTPRINT_COLOR);
		ListIterator<Point> iter = (ListIterator<Point>) QUEUE.iterator();
		int px, py, x, y;
		if(iter.hasNext()){
			Point past = iter.next();
		    px = (int) (past.getX() - DOT.getX());
		    py = (int) (past.getY() - DOT.getY());
		}else{
			return;
		}
		while(iter.hasNext()){
			Point current = iter.next();
			x = (int) (current.getX() - DOT.getX());
			y = (int) (current.getY() - DOT.getY());
			g.drawLine(W_MIDDLE + px,
					H_MIDDLE + py,
					W_MIDDLE + x,
					H_MIDDLE + y);
			py = y;
			px = x;
		}
	}

	private void drawDot(Graphics g) {
		g.setColor(CIRCLE_COLOR);
		g.fillOval(W_MIDDLE - CIRCLE_RADIUS/2 , 
				H_MIDDLE - CIRCLE_RADIUS/2,
				CIRCLE_RADIUS,
				CIRCLE_RADIUS);
		g.drawLine(W_MIDDLE, H_MIDDLE,
				W_MIDDLE + (int) (NOSE_LENGTH * Math.cos(ORIENTATION)),
				H_MIDDLE + (int) (NOSE_LENGTH * -Math.sin(ORIENTATION)) );
	}

	private GUI(JFrame window){
		//TODO figure out frame width
		window.setSize(WIDTH + 20, HEIGHT);  
	}
	
	public static GUI startSimulation() throws InterruptedException{
		// initialize window
		JFrame window = new JFrame();
		window.setTitle("magical kalman filter"); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		// initialize game
		GUI simu = new GUI(window);
		window.add(simu);
		return simu;
	}
	
	public void update(Point dot, double orientation, ArrayList<Point> prediction){
		DOT = dot;
		ORIENTATION = orientation;
		QUEUE.push(dot);
		if( QUEUE.size() > FOOTPRINT_LENGTH )
			QUEUE.removeLast();
		PREDICTION = prediction;
		this.repaint();
	}
	
	
	public static void main(String args[]){
		GUI sim;
		try {
			sim = startSimulation();
			
			for( int i = 0; i < 1000; ++i){
				ArrayList a = new ArrayList<Point>();
				a.add(new Point(i+100, (int) (100*Math.sin((double)i/50))));
				a.add(new Point(i+200, (int) (100*Math.sin((double)i/50))));
				sim.update(new Point(1*i, (int) (100*Math.sin((double)i/50))),i, a);
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
