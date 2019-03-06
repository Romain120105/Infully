package fr.romain120105.infully.server.packets;

import java.util.HashMap;

import fr.romain120105.infully.server.InfullyServer;
import fr.romain120105.infully.server.entity.Client;
import io.netty.channel.Channel;

public class LoginPacket implements IPacketReceiver {

	@Override
	public void readPacket(HashMap<String, Object> datas, Channel channel) {
		Client client = null;
		/*
		 * if (clientType.equals("Raspberry")) { if (datas.containsKey("AppId")) {
		 * Object appIdObj = datas.get("AppId");
		 * 
		 * if (appIdObj instanceof String) { String appId = (String) appIdObj; client =
		 * new Raspberry(appId, channel); } } } else
		 */
		if (datas.containsKey("email") && datas.containsKey("password")) {
			Object emailObj = datas.get("email");
			Object passwordObj = datas.get("password");

			if (emailObj instanceof String && passwordObj instanceof String) {
				String email = (String) emailObj;
				String password = (String) passwordObj;
				if (InfullyServer.INSTANCE.getLoginHandler().login(email, password)) {
					client = InfullyServer.INSTANCE.getLoginHandler().createClient(email, password, channel);
				} else {
					InfullyServer.INSTANCE.getNetworkWrapper().sendPacketToChannel(
							new LoginPacketResponse(false, "Bad credentials, invalid email or password"), channel);
					return;
				}
			}
		}
		if (client != null) {
			client.sendPacket(new LoginPacketResponse(true, "Successful Login"));

			InfullyServer.INSTANCE.getNetworkWrapper().onClientConnect(client);

		} else {
			InfullyServer.INSTANCE.getNetworkWrapper()
					.sendPacketToChannel(new LoginPacketResponse(false, "Invalid packet structure"), channel);
		}

	}

	public class LoginPacketResponse implements IPacketSender {

		private boolean sucess;
		private String message;

		public LoginPacketResponse(boolean sucess, String message) {
			this.message = message;
			this.sucess = sucess;
		}

		@Override
		public HashMap<String, Object> writeToPacket(Client client) {
			HashMap<String, Object> datas = new HashMap<>();
			datas.put("message", this.message);
			datas.put("sucess", this.sucess);
			return datas;
		}

	}

}
