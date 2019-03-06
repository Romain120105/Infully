package fr.romain120105.softlog;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import fr.romain120105.softlog.logger.Logger;
import fr.romain120105.softlog.logger.SimpleLogger;
import fr.romain120105.softlog.logger.WriteLogger;

/**
 * Created by @Romain120105
 */
public class LoggerManager {

	private static HashMap<String, Logger> loggers = new HashMap<>();

	static {
		//System.setOut(new LogPrintStream(System.out, false));
		//System.setErr(new LogPrintStream(System.err, true));
	}

	public static Logger registerLogger(Logger logger, String name) {
		loggers.put(name, logger);
		return logger;
	}
	
	public static Logger registerSimpleLogger(String name) {
		Logger logger = new SimpleLogger(name);
		
		loggers.put(name, logger);
		
		return logger;
	}

	public static Logger getLogger(String name) {
		if(!loggers.containsKey(name)) {
			return registerSimpleLogger(name);
		}
		return loggers.get(name);
	}

	public static Logger registerWriteLogger(String name, File folder) {
		if (!folder.exists()) {
			folder.mkdirs();
		}
		int i = 0;
		File file = new File("e");
		while (!file.exists()) {
			File f = new File(folder, "logs-" + i + ".logs");
			if (f.exists()) {
				i += 1;
			} else {
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				file = f;

			}
		}
		return new WriteLogger(name, file);
	}

}
