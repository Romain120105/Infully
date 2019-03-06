package fr.romain120105.infully.client.packets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.xml.bind.DatatypeConverter;

import fr.romain120105.infully.client.login.FutureCallback;
import io.netty.channel.Channel;

public class LoginPacket implements IPacketSender {

	private static FutureCallback callback;

	private String email;
	private String password;
	
	public LoginPacket(String email, String password, FutureCallback callback) {
		this.email = email;
		this.password = password;
		LoginPacket.callback = callback;
	}

	@Override
	public HashMap<String, Object> writeToPacket() {
		HashMap<String, Object> datas = new HashMap<>();
		
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
		
		datas.put("email", "romain120105@outlook.fr");
		datas.put("password", hashedPassword);
		
		return datas;
	}

	public static class LoginPacketResponse implements IPacketReceiver{

		@Override
		public void readPacket(HashMap<String, Object> datas, Channel channel) {
			if(datas.containsKey("message") && datas.containsKey("sucess")) {
				Object messageObj = datas.get("message");
				Object sucessObj = datas.get("sucess");
				if(messageObj instanceof String && sucessObj instanceof Boolean) {
					String message = (String)messageObj;
					boolean sucess = (Boolean) sucessObj;
					callback.callback(sucess, message);
				}
			}
		}

		
	}
}
