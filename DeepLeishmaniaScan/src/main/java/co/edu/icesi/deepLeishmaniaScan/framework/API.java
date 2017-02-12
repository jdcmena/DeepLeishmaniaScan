package co.edu.icesi.deepLeishmaniaScan.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class API implements IAPI{

	public static final String CLASIFY_SCRIPT="";
	public static final String TRAIN_SCRIPT="";
	
	private static String test_com = "cd ~/DeepLeishmaniaScan/";
	
	public API(){
		
	}
	
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

	@Override
	public void runCommand(String command) throws Exception{
		Process p;
		try{
		p = Runtime.getRuntime().exec(test_com);
		}
		catch(IOException io){
			System.out.println("catched IOExcp");
			p = Runtime.getRuntime().exec("cd ~");
			p.waitFor();
			p = Runtime.getRuntime().exec("mkdir DeepLeishmaniaScan");
			p.waitFor();
			p = Runtime.getRuntime().exec(test_com);
			p.waitFor();
			System.out.println("folder created");
		}
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
