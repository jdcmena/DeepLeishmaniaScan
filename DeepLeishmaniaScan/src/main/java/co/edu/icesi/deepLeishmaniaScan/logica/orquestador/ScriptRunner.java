package co.edu.icesi.deepLeishmaniaScan.logica.orquestador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ScriptRunner implements Runnable{
	
	public static final String CLASIFY_SCRIPT="";
	public static final String TRAIN_SCRIPT="";
	
	private static String test_com = "cd ~/DeepLeishmaniaScan/";
	
	public void runScript(String script){
		try{
			Process process = Runtime.getRuntime().exec(script);
			final InputStream stream = process.getInputStream();
			BufferedReader br =  new BufferedReader(new InputStreamReader(stream));
	        String line = null;
	        while ((line = br.readLine()) != null) {
	          System.out.println(line);
	        }
			//process.waitFor();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		Process p = null;
		try{
		p = Runtime.getRuntime().exec("");
		}
		catch(IOException io){
			try{
			System.out.println("catched IOExcp");
			p = Runtime.getRuntime().exec("cd ~");
			p.waitFor();
			p = Runtime.getRuntime().exec("mkdir DeepLeishmaniaScan");
			p.waitFor();
			p = Runtime.getRuntime().exec("");
			p.waitFor();
			System.out.println("folder created");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		  final InputStream stream = p.getInputStream();
		
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

}
