package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import predict.Function;

@SuppressWarnings("serial")
public class GUI extends JPanel{
	
	//ratio to increase window size
	private final static int RATIO = 1;
	
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
	private final static Color LEGEND_COLOR = Color.white;
	
	// update variables
	private static LinkedList<Point> QUEUE = new LinkedList<Point>();
	private static Point DOT = new Point(0,0);
	private static double ORIENTATION = 0;
	private static ArrayList<Point> PREDICTION = new ArrayList<Point>();

	// misc. variables
	private static int index = 1;
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(Color.BLACK);
		drawDot(g);
		drawFootprints(g);
		drawPrediction(g);
		drawTicks(g);
		drawLegend(g);
	}

	private void drawLegend(Graphics g) {
		g.setColor(LEGEND_COLOR);
		// set font
		Font font = new Font("Helvetica", Font.BOLD, 20);
		g.setFont(font);
		FontMetrics metr = getFontMetrics(font);

		//set message
		String s = "Setting X: 10*time + [7]*sin(time/[8]) + [9]*cos(time/[0])";
		String s1 = "Setting Y: 10*time + [2]*sin(time/[3]) + [4]*cos(time/[5])";

		g.drawString(s, 10, HEIGHT - font.getSize()*4);
		g.drawString(s1, 10, HEIGHT - font.getSize()*2);
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
		window.addKeyListener(new ArrowAdapter());
		
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

	private static class ArrowAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_2) {
                index = 1;
            }

            if (key == KeyEvent.VK_3) {
                index = 2;
            }

            if (key == KeyEvent.VK_4) {
                index = 3;
            }

            if (key == KeyEvent.VK_5) {
                index = 4;
            }

            if (key == KeyEvent.VK_7) {
                index = 6;
            }

            if (key == KeyEvent.VK_8) {
                index = 7;
            }

            if (key == KeyEvent.VK_9) {
                index = 8;
            }

            if (key == KeyEvent.VK_0) {
                index = 9;
            }

            if (key == KeyEvent.VK_LEFT) {
                Function.update(index, -10);
            }

            if (key == KeyEvent.VK_RIGHT) {
                Function.update(index, 10);
            }

            if (key == KeyEvent.VK_DOWN) {
                Function.update(index, -1);
            }

            if (key == KeyEvent.VK_UP) {
                Function.update(index, 1);
            }
        }
    }
	
	
	public static void main(String[] args){
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
