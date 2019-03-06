package fr.romain120105.infully.server.packets;

import java.util.HashMap;

import fr.romain120105.infully.server.InfullyServer;
import fr.romain120105.infully.server.entity.Client;
import fr.romain120105.infully.server.entity.Phone;
import io.netty.channel.Channel;

public class RegisterPacket implements IPacketReceiver{

	@Override
	public void readPacket(HashMap<String, Object> datas, Channel channel) {

		if (datas.containsKey("username") && datas.containsKey("email") && datas.containsKey("password")) {
			Object nameObj = datas.get("username");

			Object emailObj = datas.get("email");
			Object passwordObj = datas.get("password");

			
			if (nameObj instanceof String && emailObj instanceof String && passwordObj instanceof String) {
				String name = (String) nameObj;

				String email = (String) emailObj;
				String password = (String) passwordObj;
				InfullyServer.INSTANCE.getLoginHandler().register(name, email, password);
				Phone phone = (Phone) InfullyServer.INSTANCE.getLoginHandler().createClient(email, password, channel);
				InfullyServer.INSTANCE.getNetworkWrapper().onClientConnect(phone);
				phone.sendPacket(new RegisterPacketResponse(true, "Successful Registration"));
				
			}
		}
	}
	
	public class RegisterPacketResponse implements IPacketSender{

		private boolean sucess;
		private String message;
		
		public RegisterPacketResponse(boolean sucess,String message) {
			this.message= message;
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
