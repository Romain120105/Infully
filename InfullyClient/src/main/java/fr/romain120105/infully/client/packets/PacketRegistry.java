package fr.romain120105.infully.client.packets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class PacketRegistry {

	private static HashMap<Integer, Class<? extends IPacket>> packets = new HashMap<>();
	
	private static ArrayList<Integer> receiverPackets = new ArrayList<>();
	private static ArrayList<Integer> senderPackets = new ArrayList<>();
	
	public static void registerPacket(int id, Class<? extends IPacket> packet, EnumPacketType type) {
		packets.put(id, packet);
		if(type.equals(EnumPacketType.RECEIVER)) {
			if(!receiverPackets.contains(id)) {
				receiverPackets.add(id);
			}
		}else {
			if(!senderPackets.contains(id)) {
				senderPackets.add(id);
			}
		}
	}
	
	public static void registerDefaultPackets() {
		registerPacket(0, LoginPacket.class, EnumPacketType.SENDER);
		registerPacket(1, RegisterPacket.class, EnumPacketType.SENDER);
		registerPacket(2, LoginPacket.LoginPacketResponse.class, EnumPacketType.RECEIVER);
		registerPacket(3, RegisterPacket.RegisterPacketResponse.class, EnumPacketType.RECEIVER);
	}

	public static int getPacketId(IPacket packet) {
		if(!packets.containsValue(packet.getClass())) {
			return -1;
		}
		for(Entry<Integer, Class<? extends IPacket>> entry : packets.entrySet()) {
			if(packet.getClass().equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return -1;
		
	}
	
	public static IPacket createPacket(int id) throws InstantiationException, IllegalAccessException {
		Class<? extends IPacket> clazz = packets.get(id);
		
		return clazz.newInstance();
	}
	
	
}
