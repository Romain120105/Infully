package fr.romain120105.softlog.logger;

/**
 * Created by @Romain120105
 */
public interface ILogListener {

	public boolean onLog(Logger logger, LogType type, String message);
	
}
