package fr.romain120105.infully.client.packets;

import java.util.HashMap;

import io.netty.channel.Channel;

public interface IPacketReceiver extends IPacket{
	public void readPacket(HashMap<String, Object> datas, Channel channel);

}
