package co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Hashtable;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Referenciado de:
 * https://web.archive.org/web/20080516181120/http://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html
 * 
 * @author jdcm
 *
 */
public class Filter {

	private static final Logger log = LoggerFactory.getLogger(Filter.class);

	private static final String OS = File.separator;
	private static final String LIST_YES = "." + OS + "positivos.txt";
	private static final String LIST_NO = "." + OS + "negativos.txt";
	private static final String SOURCE = "." + OS + "images";
	public static final String YES_ROUTE = "." + OS + "datasource" + OS + "Leishmaniasis";
	public static final String NO_ROUTE = "." + OS + "datasource" + OS + "Non_Leishmaniasis";
	private static final String TEST_SET_YES = "." + OS + "testDir" + OS + "Leishmaniasis";
	private static final String TEST_SET_NO = "." + OS + "testDir" + OS + "Non_Leishmaniasis";

	// http://stackoverflow.com/questions/7951290/re-sizing-an-image-without-losing-quality
	// http://stackoverflow.com/questions/6390964/decrease-image-resolution-in-java
	// https://web.archive.org/web/20080516181120/http://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html

	public static void divideAndResizeImages() {

		File yes_route = new File(YES_ROUTE);
		File no_route = new File(NO_ROUTE);

		try {
			if (!yes_route.createNewFile()) {
				FileUtils.cleanDirectory(yes_route);
			}
			if (!no_route.createNewFile()) {
				FileUtils.cleanDirectory(no_route);
			}

			log.info("dataset subdirectories created");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		BufferedReader br = null;
		Hashtable<String, String> positives = new Hashtable<>(1000);
		Hashtable<String, String> negatives = new Hashtable<>(1000);
		try {
			br = new BufferedReader(new FileReader(LIST_NO));
			String line = br.readLine();
			while (line != null) {
				negatives.put(line, line);
				line = br.readLine();
			}
			br.close();
			br = new BufferedReader(new FileReader(LIST_YES));
			line = br.readLine();

			while (line != null) {
				positives.put(line, line);
				line = br.readLine();
			}

			log.info("wrote images' ids");

			File[] dirs = new File(SOURCE).listFiles();
			int testSegmentY = 0;
			int testSegmentN = 0;
			log.info("resizing images, moving to folders");
			
			for (File file : dirs) {
				String[] whole = file.getName().split("_");
				String varNombre = whole[1];

				if (positives.containsKey(varNombre)) {

					FileUtils.copyFileToDirectory(file, yes_route);
					//positives.remove(varNombre);
					File dest = new File(yes_route + OS + file.getName());
					BufferedImage img = ImageIO.read(dest);
					img = getScaledInstance(img, 400, 400, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
					ImageIO.write(img, "jpg", dest);
					testSegmentY++;
					//ColorSpaceTransformer.imageToLAB(dest);

				} else if (negatives.containsKey(varNombre)) {

					FileUtils.copyFileToDirectory(file, no_route);
					//negatives.remove(varNombre);
					File dest = new File(no_route + OS + file.getName());
					BufferedImage img = ImageIO.read(dest);
					img = getScaledInstance(img, 400, 400, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
					ImageIO.write(img, "jpg", dest);
					testSegmentN++;
					//ColorSpaceTransformer.imageToLAB(dest);

				} else {
					// no action
				}
				
			}
			int divY = (testSegmentY / 5);
			int divN = (testSegmentN / 5);
			File[] leishDirs = yes_route.listFiles();
			File[] nonLDirs = no_route.listFiles();
			
			FileUtils.forceMkdir(new File(TEST_SET_YES));
			FileUtils.forceMkdir(new File(TEST_SET_NO));
			
			for (int i = 0; i < divY; i++) {
				FileUtils.moveFile(leishDirs[i], new File(TEST_SET_YES + OS + leishDirs[i].getName()));
				
			}
			for (int i = 0; i < divN; i++) {
				FileUtils.moveFile(nonLDirs[i], new File(TEST_SET_NO + OS + nonLDirs[i].getName()));
			}

		} catch (Exception e) {

		} finally {
			try {
				br.close();
				positives.clear();
				negatives.clear();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.info("Image preprocessing finished");
	}

	public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint,
			boolean higherQuality) {
		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = (BufferedImage) img;
		int w, h;
		if (higherQuality) {
			// Use multi-step technique: start with original size, then
			// scale down in multiple passes with drawImage()
			// until the target size is reached
			w = img.getWidth();
			h = img.getHeight();
		} else {
			// Use one-step technique: scale directly from original
			// size to target size with a single drawImage() call
			w = targetWidth;
			h = targetHeight;
		}

		do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}

			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}

			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret = tmp;
		} while (w != targetWidth || h != targetHeight);

		return ret;
	}

}
