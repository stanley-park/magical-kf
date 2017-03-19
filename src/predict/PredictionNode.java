package predict;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

public interface PredictionNode {

	public ArrayList<Point> predict( LinkedList<Point> ps);
}
