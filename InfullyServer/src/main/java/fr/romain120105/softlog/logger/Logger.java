package fr.romain120105.softlog.logger;

import java.util.List;

/**
 * Created by @Romain120105
 */
public interface Logger {

	void info(String text);

	void info(List<String> text);
	
	void debug(String text);
	
	void debug(List<String> text);
	
	void warning(String text);

	void catching(Throwable error, String text);

	void catching(Throwable error);
	
	void error(String error);
	
	void error(String error, Throwable t);

	String log(LogType type, String text);

	void setName(String name);
	
	String getName();
	
	void addListener(ILogListener listener);
	
}
