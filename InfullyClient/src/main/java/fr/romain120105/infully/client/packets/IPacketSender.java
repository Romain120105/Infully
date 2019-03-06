package fr.romain120105.infully.client.packets;

import java.util.HashMap;

public interface IPacketSender extends IPacket{

	public HashMap<String, Object> writeToPacket();

	
}
