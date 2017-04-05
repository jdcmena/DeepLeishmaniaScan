package co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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

	// http://stackoverflow.com/questions/7951290/re-sizing-an-image-without-losing-quality
	// http://stackoverflow.com/questions/6390964/decrease-image-resolution-in-java
	// https://web.archive.org/web/20080516181120/http://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html

	public static void divideAndResizeImages() {

		ArrayList<String> yes = new ArrayList<>();
		ArrayList<String> no = new ArrayList<>();

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
		
		/// GOTTA READ TXT
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(LIST_NO));
			String line = br.readLine();
			while (line != null) {
				no.add(line);
				line = br.readLine();
			}
			br.close();
			br = new BufferedReader(new FileReader(LIST_YES));
			line = br.readLine();

			while (line != null) {
				yes.add(line);
				line = br.readLine();
			}
			yes.trimToSize();
			no.trimToSize();

			log.info("wrote images' ids");
			/// /GOTTA READ TXT

			// yes and no arraylists contains images' ids to divide positive &
			// negative images
			File[] dirs = new File(SOURCE).listFiles();
			
			log.info("resizing images, moving to folders");
			for (File file : dirs) {
				boolean found = false;

				for (int i = 0; i < yes.size(); i++) {

					if (file.getName().contains(yes.get(i))) {
						FileUtils.copyFileToDirectory(file, yes_route);
						yes.remove(i);
						found = true;
						File dest = new File(yes_route + OS + file.getName());
						BufferedImage img = ImageIO.read(dest);
						img = getScaledInstance(img, 400, 400, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
						ImageIO.write(img, "jpg", dest);

						// FileUtils.copyFile(srcFile, destFile);
						// lower res

						break;
					}
				}

				if (!found) {
					FileUtils.copyFileToDirectory(file, no_route);
					File dest = new File(no_route + OS + file.getName());
					BufferedImage img = ImageIO.read(dest);
					img = getScaledInstance(img, 400, 400, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);

					ImageIO.write(img, "jpg", dest);
				}

			}
			
		} catch (Exception e) {

		} finally {
			try {
				br.close();
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
