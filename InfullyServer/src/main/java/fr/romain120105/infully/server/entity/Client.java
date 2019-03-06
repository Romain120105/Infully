package fr.romain120105.infully.server.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.romain120105.infully.server.packets.IPacketSender;
import fr.romain120105.infully.server.packets.PacketObject;
import fr.romain120105.infully.server.packets.PacketRegistry;
import io.netty.channel.Channel;

public abstract class Client {
	
	private static Gson GSON = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();


	private Channel channel;
	
	public Client(Channel channel) {
		this.channel = channel;
	}
	
	public void sendPacket(IPacketSender packet) {
		this.getChannel().writeAndFlush(GSON.toJson(new PacketObject(PacketRegistry.getPacketId(packet), packet.writeToPacket(this))) + "\r\n");
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public abstract String getName();	
}
