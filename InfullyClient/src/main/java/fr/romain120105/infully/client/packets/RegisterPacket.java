package fr.romain120105.infully.client.packets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.xml.bind.DatatypeConverter;

import fr.romain120105.infully.client.login.FutureCallback;
import io.netty.channel.Channel;

public class RegisterPacket implements IPacketSender {

	private static FutureCallback callback;
	
	private String username;
	private String email;
	private String password;
	

	public RegisterPacket(String username, String email, String password, FutureCallback callback) {
		this.username = username;
		this.email = email;
		this.password = password;
		RegisterPacket.callback = callback;
	}
	
	@Override
	public HashMap<String, Object> writeToPacket() {
		HashMap<String, Object> datas = new HashMap<>();
		datas.put("username", this.username);

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    md.update(password.getBytes());
	    byte[] digest = md.digest();
	    String hashedPassword = DatatypeConverter
	      .printHexBinary(digest).toUpperCase();
		
		datas.put("email", this.email);
		datas.put("password", hashedPassword);
		
		return datas;
	}

	public static class RegisterPacketResponse implements IPacketReceiver{

		public RegisterPacketResponse() {
		}
		
		@Override
		public void readPacket(HashMap<String, Object> datas, Channel channel) {
			if(datas.containsKey("message")) {
				Object messageObj = datas.get("message");
				if(messageObj instanceof String) {
					String message = (String)messageObj;
					callback.callback(true, message);
				}
			}
		}
		
	}
	

}
