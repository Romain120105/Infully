package fr.romain120105.infully.server.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import fr.romain120105.infully.server.InfullyServer;

public class FileUtils {

	public static boolean emptyFile(File file) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			InfullyServer.LOGGER.catching(e);
		}
		try {
			if (br.readLine() == null) {
				return true;
			}
		} catch (IOException e) {
			InfullyServer.LOGGER.catching(e);
		}
		return false;
	}

	public static String loadFile(File file) {
		if (file.exists()) {
			String prefix = "\n";
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				StringBuilder string = new StringBuilder();

				String line;

				while ((line = reader.readLine()) != null) {
					string.append(line + prefix);
				}
				reader.close();
				return string.toString();
			} catch (IOException e) {
				InfullyServer.LOGGER.catching(e);
			}
		}
		return "";
	}

	public static void save(File file, String string) {
		if (file.getParentFile() != null && !file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		if (!file.exists() && !file.isDirectory()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				InfullyServer.LOGGER.catching(e);
			}
		}
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(string);
			fw.flush();
			fw.close();

		} catch (IOException e) {
			InfullyServer.LOGGER.catching(e);
		}
	}

}
