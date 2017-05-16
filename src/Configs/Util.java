package Configs;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Properties;

public class Util {

	/**
	 * Error codes
	 */
	public enum ErrorCode {
		ERR_WRONG_ARGS
	}
	
	private static Properties p = new Properties();

	public static Properties loadPropertiesFile(File f) throws FileNotFoundException, IOException {
		p = new Properties();
		InputStream inputStream;

		inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(f.getPath());

		if (inputStream != null) {
			p.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + p + "' not found in the classpath");
		}

		return p;
	}

	public static Properties loadPropertiesFile(String file) throws FileNotFoundException, IOException {
		File f = new File(file);

		return loadPropertiesFile(f);
	}

	public static Properties getProperties() {
		return p;
	}

	public void saveProperties(File f) throws IOException {
			OutputStream out = new FileOutputStream(f);
			p.store(out, "Saved" + Calendar.getInstance().getTime());
	}

}
