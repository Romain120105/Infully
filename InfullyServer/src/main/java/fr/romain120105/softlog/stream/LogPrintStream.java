package fr.romain120105.softlog.stream;

import java.io.OutputStream;
import java.io.PrintStream;

import fr.romain120105.softlog.Time;

/**
 * Created by @Romain120105
 */
public class LogPrintStream extends PrintStream {

	private boolean error;

	public LogPrintStream(OutputStream out, boolean error) {
		super(out);
		this.error = error;
	}

	@Override
	public void print(String s) {

		super.print(getToPrint(s));
	}

	@Override
	public void print(boolean b) {
		super.print(getToPrint(Boolean.toString(b)));
		
	}

	@Override
	public void print(char c) {
		super.print(getToPrint(Character.toString(c)));

	}

	@Override
	public void print(int i) {
		super.print(getToPrint(Integer.toString(i)));

	}

	@Override
	public void print(long l) {
		super.print(getToPrint(Long.toString(l)));
		
	}

	@Override
	public void print(float f) {

		super.print(getToPrint(Float.toString(f)));

	}

	@Override
	public void print(double d) {
		super.print(getToPrint(Double.toString(d)));

		

	}

	@Override
	public void print(char[] cs) {
		StringBuilder s = new StringBuilder();
		for (Character c : cs) {
			s.append(c);
		}
		super.println(getToPrint(s.toString()));

	}

	@Override
	public void println(Object obj) {
		if (obj instanceof Throwable) {
			super.print(obj + System.getProperty("line.separator"));
			return;
		}
		if (obj == null) {
			obj = new String("null");
		}
		super.println(getToPrint(obj.toString()));

	}
	
	public String getToPrint(String s) {
		boolean sys = true;
		if(s.startsWith("[SoftLogger]")) {
			sys = false;
			String so = s.split(" ")[0];
			
			for(char c : so.toCharArray()) {
				s = s.replaceFirst(Character.toString(c), "");
			}
		}
		
		String text = "[" + Time.getDate() + "] ";
		if(sys) {
			if(this.error) {
				text += "[ERROR] ";
			}else {
				text += "[INFO] ";
			}
		}
		text += s;
		return text;
		
	}

	/*
	private String getPrintedString(String s) {
		if (s == null) {
			s = "null";
		}
		String type = s.split(" ")[0];
		if (!type.startsWith("[") && !type.endsWith("]") && !type.equals("[INFO]") && !type.equals("[ERROR]")
				&& !type.equals("[DEBUG]") && !type.equals("[WARNING]")) {
			if (this.error) {
				type = "[ERROR] ";
			} else {
				type = "[INFO] ";
			}
		} else {
			type = "";
		}
		return "[" + Time.getDate() + "] " + type + s;
	}

	private String getPrintedString2(String s) {
		if (s == null) {
			s = "null";
		}
		
		if(s.startsWith("[SoftLogger]")) {
			
		}
		
		String type = s.split(" ")[0];
		if (!type.startsWith("[") && !type.endsWith("]") && !type.equals("[INFO]") && !type.equals("[ERROR]")
				&& !type.equals("[DEBUG]") && !type.equals("[WARNING]")) {
			if (this.error) {
				type = "[ERROR] ";
			} else {
				type = "[INFO] ";
			}
		} else {
			type = "";
		}
		return type + s;
		
	}
	
	public void newLine() {
	}
	*/

}
