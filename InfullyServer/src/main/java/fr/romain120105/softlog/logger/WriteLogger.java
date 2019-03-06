package fr.romain120105.softlog.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.romain120105.softlog.Time;

/**
 * Created by @Romain120105
 */
public class WriteLogger implements Logger {

	private String name;
	
	private File file;

	private ArrayList<ILogListener> listeners = new ArrayList<>();
	
	private BufferedWriter writer = null;

	public WriteLogger(String name, File file) {
		this.name = name;

		this.file = file;
		
		if (!this.file.getParentFile().exists()) {
			this.file.getParentFile().mkdirs();
		}

		if (!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			this.writer = new BufferedWriter(new FileWriter(this.file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void info(String text) {
		log(LogType.INFO, text);
	}

	@Override
	public void info(List<String> text) {
		for (String string : text) {
			info(string);
		}
	}
	
	@Override
	public void debug(String text) {
		log(LogType.DEBUG, text);
	}

	@Override
	public void debug(List<String> text) {
		for (String string : text) {
			debug(string);
		}
	}

	@Override
	public void warning(String text) {
		log(LogType.WARNING, text);

	}

	@Override
	public void catching(Throwable error, String text) {
		if(!error.getMessage().equals(text)) {
			text += " : " + error.getMessage();
		}
		log(LogType.ERROR, error.getClass().getName() + ": " + text);
		for (StackTraceElement stackTrace : error.getStackTrace()) {
			log(LogType.ERROR, "   at " + stackTrace);
		}

	}

	@Override
	public void catching(Throwable error) {
		catching(error, error.getMessage());
	}

	@Override
	public void error(String error) {
		log(LogType.ERROR, error);
	}

	@Override
	public String log(LogType type, String text) {
		for(ILogListener listener : this.listeners) {
			listener.onLog(this, type, text);
		}
		String str = "";
		str = "[" + this.name + "] [" + type.toString() + "] " + text;
		if (type.isError()) {
			System.err.println("[" + Time.getDate()+ "] " + str);
		} else {
			System.out.println("[" + Time.getDate()+ "] " + str);
		}
		
		if (file != null) {
			try {
				this.writer.write("[" + Time.getDate()+ "] " + str + "\n");
				this.writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return str;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	public void addListener(ILogListener listener) {
		this.listeners.add(listener);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void error(String error, Throwable t) {
		catching(t, error);
	}

}
