package co.edu.icesi.deepLeishmaniaScan.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class API implements IAPI{

	public static final String CLASIFY_SCRIPT="";
	public static final String TRAIN_SCRIPT="";
	
	@Override
	public double[] entrenar(String modelo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double clasificar(String modelo) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void run() throws Exception{
		Process p = Runtime.getRuntime().exec("");
		  final InputStream stream = p.getInputStream();
		  new Thread(new Runnable() {
		    public void run() {
		      BufferedReader reader = null;
		      try {
		        reader = new BufferedReader(new InputStreamReader(stream));
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		          System.out.println(line);
		        }
		      } catch (Exception e) {
		        // TODO
		      } finally {
		        if (reader != null) {
		          try {
		            reader.close();
		          } catch (IOException e) {
		            // ignore
		          }
		        }
		      }
		    }
		  }).start();
	}
	
}
