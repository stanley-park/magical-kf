package socket;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedList;

import predict.PredictionNode;

public class RNNternet implements PredictionNode{
	
	private final static int PORT = 12322;
	private ServerSocket soc;
	private DataOutputStream out;
	private BufferedReader in;
	private Socket server;
	
	public RNNternet() {
		
		try{
			soc = new ServerSocket(PORT);
			server = soc.accept();
			out = new DataOutputStream(server.getOutputStream());
			in = new BufferedReader( new InputStreamReader( server.getInputStream()));
		}catch(SocketTimeoutException s){
			System.out.println("Socket timed out!");
		}catch(IOException e){
			e.printStackTrace();
		}			
	}
	
	public void close(){
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Point> predict( LinkedList<Point> ps){
		ArrayList<Point> predict = new ArrayList<Point>();
		if( ps.size() != 5 ){
			System.out.println("RNNternet: weird number of points recieved");
			return predict;
		}
		String plsSend = "";
		for( int i = 0; i < 5; ++i){
			plsSend = plsSend +  Double.toString(ps.get(i).getX()) + "," + Double.toString(ps.get(i).getY()) + ";";
		}
		String plsRead = "";
		try {
			out.writeUTF( plsSend);
			plsRead = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(plsRead);
		
		for( String pair : plsRead.split(";")){
			String[] nums = pair.split(",");
			
			double x = Double.parseDouble(nums[0]);
			double y = Double.parseDouble(nums[1]);
			
			predict.add( new Point((int)x,(int)y) );
		}		
		return predict;
	}
}
