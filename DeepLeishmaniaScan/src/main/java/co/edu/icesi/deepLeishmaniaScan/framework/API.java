package co.edu.icesi.deepLeishmaniaScan.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JTextArea;

public class API implements IAPI {

	private static final char OS = File.separatorChar;
	public static final String CLASIFY_SCRIPT = "python classify.py ";
	public static final String TRAIN_SCRIPT = "python train.py ";

	/*
	 * private StringWriter writer; private ScriptEngineManager manager; private
	 * ScriptContext context; private ScriptEngine engine;
	 */
	public API() {
		/*
		 * writer = new StringWriter(); // ouput will be stored here manager =
		 * new ScriptEngineManager(); context = new SimpleScriptContext();
		 * context.setWriter(writer); // configures output redirection engine =
		 * manager.getEngineByName("python");
		 */
	}

	@Override
	public double[] entrenar(String modelo, JTextArea consola) throws Exception {
		return runCommand(TRAIN_SCRIPT + modelo + "runconfig.json", 1, consola);
	}

	@Override
	public double clasificar(String modelo, JTextArea consola) throws Exception {
		return runCommand(CLASIFY_SCRIPT + modelo, 2, consola)[0];
	}

	private double[] runCommand(String command, int flag, JTextArea consola) throws Exception {

		double[] relevantOutput = new double[2];

		Runtime r = Runtime.getRuntime();
		//ProcessBuilder pb = new ProcessBuilder("python","train.py",command);
		Process p = r.exec(command);
		final InputStream stream = p.getInputStream();
		new Thread(new Runnable() {
			public void run() {
				BufferedReader reader = null;

				reader = new BufferedReader(new InputStreamReader(stream));
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						System.out.println(line);
						consola.append(line);
						if (flag == 1) {
							if (line.contains("Accuracy")) {
								String temp = line.split(" ")[1];
								relevantOutput[0] = Double.parseDouble(temp.substring(0, temp.length() - 2));
							}
						}
						if (flag == 2) {
							// TODO Classification [#CLASSID]
						}

					}
					System.out.println(line);
					consola.append(line);
				} catch (IOException io) {
					io.printStackTrace();
				}

				if (reader != null) {

				}
			}

		}).start();
		

		while (relevantOutput[0] == 0 || relevantOutput[1] == 0) {
			Thread.sleep(50);
		}
		return relevantOutput;
	}

}
