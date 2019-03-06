package fr.romain120105.infully.client.network;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.romain120105.infully.client.packets.IPacket;
import fr.romain120105.infully.client.packets.IPacketReceiver;
import fr.romain120105.infully.client.packets.LoginPacket;
import fr.romain120105.infully.client.packets.PacketObject;
import fr.romain120105.infully.client.packets.PacketRegistry;
import fr.romain120105.infully.client.packets.RegisterPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NetworkHandler extends SimpleChannelInboundHandler<String> {

	private static Gson GSON = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
	
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	if (msg instanceof String) {
			String s = (String) msg;
			Channel channel = ctx.channel();
			PacketObject packetObj = GSON.fromJson(s, PacketObject.class);
			int packetID = packetObj.getId();
			HashMap<String, Object> datas = packetObj.getDatas();
			IPacket packet = PacketRegistry.createPacket(packetID);

			if(packet instanceof IPacketReceiver) {
				((IPacketReceiver) packet).readPacket(datas, channel);
			}
		}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
