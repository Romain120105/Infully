package fr.romain120105.infully.server.login;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import fr.romain120105.infully.server.InfullyServer;
import fr.romain120105.infully.server.entity.Client;
import fr.romain120105.infully.server.entity.Phone;
import fr.romain120105.infully.server.utils.FileUtils;
import io.netty.channel.Channel;

public class LoginHandler {

	private Gson GSON = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();

	private	ArrayList<UserEntry> users;

	
	@SuppressWarnings({ "unchecked", "serial" })
	public LoginHandler load() {
		try {
			File usersFile = new File("users.json");
			if (!usersFile.exists()) {
				usersFile.createNewFile();
				users = new ArrayList<>();
				this.save();
			}else {
				Type listType = new TypeToken<ArrayList<UserEntry>>(){}.getType();
				users = GSON.fromJson(FileUtils.loadFile(usersFile), listType);
			}
		} catch (Exception e) {
			users = new ArrayList<>();
			InfullyServer.LOGGER.catching(e);
		}
		return this;
	}

	public boolean login(String email, String password) {
		for(UserEntry entry : users) {
			if(entry.getEmail().equals(email)) {
				if(entry.getPassword().equals(password)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void register(String name, String email, String password) {
		this.users.add(new UserEntry(UUID.randomUUID().toString(), name, email, password));
		save();

	}
	
	public void save() {
		File usersFile = new File("users.json");

		FileUtils.save(usersFile, GSON.toJson(users));
	}

	public Client createClient(String email, String password, Channel channel) {
		for(UserEntry entry : users) {
			if(entry.getEmail().equals(email)) {
				if(entry.getPassword().equals(password)) {
					Phone phone = new Phone(entry.getUuid(), entry.getName(), channel);
					
					return phone;
				}
			}
		}
		return null;
	}

	private class UserEntry {
		private String uuid;
		private String name;
		private String email;
		private String password;
		

		public UserEntry(String uuid, String name, String email, String password) {
			this.uuid = uuid;
			this.name = name;
			this.email = email;
			this.password = password;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}

}
