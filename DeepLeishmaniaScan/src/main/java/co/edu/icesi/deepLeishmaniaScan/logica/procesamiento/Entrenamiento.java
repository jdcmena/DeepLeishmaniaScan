package co.edu.icesi.deepLeishmaniaScan.logica.procesamiento;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Entrenamiento implements IEntrenamiento {

	private static final Logger log = LoggerFactory.getLogger(Entrenamiento.class);

	@Override
	public String[] obtenerMetricas(String path) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] entrenar(String path) throws Exception {
		// TODO Auto-generated method stub

		scriptRunner("p");
		log.info("-----");
		return null;
	}

	private void scriptRunner(String command) throws Exception {
//////////////
		StringWriter writer = new StringWriter(); //ouput will be stored here

	    ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptContext context = new SimpleScriptContext();

	    context.setWriter(writer); //configures output redirection
	    ScriptEngine engine = manager.getEngineByName("python");
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
