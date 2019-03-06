package fr.romain120105.infully.server.network;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.romain120105.infully.server.InfullyServer;
import fr.romain120105.infully.server.packets.IPacket;
import fr.romain120105.infully.server.packets.IPacketReceiver;
import fr.romain120105.infully.server.packets.LoginPacket;
import fr.romain120105.infully.server.packets.PacketObject;
import fr.romain120105.infully.server.packets.PacketRegistry;
import fr.romain120105.infully.server.packets.RegisterPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

@ChannelHandler.Sharable
public class NetworkHandler extends SimpleChannelInboundHandler<String> {
	private static Gson GSON = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();

	private static final ChannelGroup waitingChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	private NetworkWrapper wrapper;

	public NetworkHandler(NetworkWrapper wrapper) {
		this.wrapper = wrapper;
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();

		waitingChannels.add(incoming);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();

		if (waitingChannels.contains(incoming)) {
			waitingChannels.remove(incoming);
		} else {
			if (InfullyServer.INSTANCE.getNetworkWrapper().isClientByChannelConnected(incoming)) {
				InfullyServer.INSTANCE.getNetworkWrapper().onClientDisconnect(
						InfullyServer.INSTANCE.getNetworkWrapper().getConnectedClientByChannel(incoming));
			}
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
		if (msg instanceof String) {
			String s = (String) msg;
			Channel channel = channelHandlerContext.channel();
			PacketObject packetObj = GSON.fromJson(s, PacketObject.class);
			int packetID = packetObj.getId();
			HashMap<String, Object> datas = packetObj.getDatas();
			IPacket packet = PacketRegistry.createPacket(packetID);

			if (!InfullyServer.INSTANCE.getNetworkWrapper().isClientByChannelConnected(channel)) {

				if (packet instanceof LoginPacket || packet instanceof RegisterPacket) {
					IPacketReceiver packetReceiver = (IPacketReceiver) packet;
					packetReceiver.readPacket(datas, channel);
				}
			} else {
				if (packet instanceof IPacketReceiver) {
					((IPacketReceiver) packet).readPacket(datas, channel);
				}
			}
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

	}

}
