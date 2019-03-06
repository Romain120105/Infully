package fr.romain120105.softlog.logger;

/**
 * Created by @Romain120105
 */
public enum LogType {

	INFO("INFO"),
	DEBUG("DEBUG"),
	WARNING("WARNING"),
	ERROR("ERROR");
	
	private String text;

	LogType(String text) {
		this.text = text;
	}
	
	public String toString() {
		return this.text;
	}
	
	public boolean isError() {
		return this.equals(ERROR);
	}
	
}
