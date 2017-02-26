package co.edu.icesi.deepLeishmaniaScan.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class API implements IAPI {

	public static final String CLASIFY_SCRIPT = "";
	public static final String TRAIN_SCRIPT = "";

	public API() {

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
	public void runCommand(String command) throws Exception {
		String[] relevantOutput = new String[2];

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
	}

}
