package co.edu.icesi.deepLeishmaniaScan.framework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

public class API implements IAPI {

	public static final String CLASIFY_SCRIPT = "";
	public static final String TRAIN_SCRIPT = "";
	
	private StringWriter writer;
	private ScriptEngineManager manager;
	private ScriptContext context;
	private ScriptEngine engine;

	public API() {
		writer = new StringWriter(); //ouput will be stored here
	    manager = new ScriptEngineManager();
	    context = new SimpleScriptContext();
	    context.setWriter(writer); //configures output redirection
	    engine = manager.getEngineByName("python");
	}

	@Override
	public double[] entrenar(String modelo) throws Exception {
		runCommand(TRAIN_SCRIPT);
		return null;
	}

	@Override
	public double clasificar(String modelo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String runCommand(String command) throws Exception {
		
		String[] relevantOutput = new String[2];
		/*
		Process p = Runtime.getRuntime().exec(command);

		final InputStream stream = p.getInputStream();
		new Thread(new Runnable() {
			public void run() {
				BufferedReader reader = null;

				reader = new BufferedReader(new InputStreamReader(stream));
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						System.out.println(line);
					}
				} catch (IOException io) {
					try {
						throw new Exception(io.getMessage());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (reader != null) {

				}
			}

		}).start();
		*/
		return null;
	}
	
	private void scriptRunner(String command) throws Exception {
//////////////
    engine.eval(new FileReader("/home/jdcm/hello.py"), context);
    System.out.println(writer.toString()); 
	//////////////Single line script
	/*
	Process p;
	try {
		p = Runtime.getRuntime().exec("python hello.py");
	} catch (IOException io) {
		p = Runtime.getRuntime().exec("cd ~");
		p.waitFor();
		p = Runtime.getRuntime().exec("mkdir DeepLeishmaniaScan");
		p.waitFor();
		p = Runtime.getRuntime().exec("python hello.py");
		p.waitFor();
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
*/
}

}
