package fr.romain120105.infully.server.packets;

import java.util.HashMap;

import fr.romain120105.infully.server.entity.Client;

public interface IPacketSender extends IPacket{

	public HashMap<String, Object> writeToPacket(Client client);

	
}
