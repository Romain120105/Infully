package fr.romain120105.infully.client.network;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.romain120105.infully.client.login.FutureCallback;
import fr.romain120105.infully.client.packets.IPacketSender;
import fr.romain120105.infully.client.packets.LoginPacket;
import fr.romain120105.infully.client.packets.PacketObject;
import fr.romain120105.infully.client.packets.PacketRegistry;
import fr.romain120105.infully.client.packets.RegisterPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NetworkWrapper {

	private static Gson GSON = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();

	private final String host;
	private final int port;

	private Channel channel;

	private EnumClientType clientType;

	public NetworkWrapper(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() throws InterruptedException, IOException {
		PacketRegistry.registerDefaultPackets();
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new io.netty.bootstrap.Bootstrap().group(group).channel(NioSocketChannel.class)
				.handler(new ChannelNetworkInitializer());

		channel = bootstrap.connect(host, port).sync().channel();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				group.shutdownGracefully();
			}
		}));

	}

	public Channel getChannel() {
		return channel;
	}

	public void sendToServer(IPacketSender packet) {
		try {
			this.channel.writeAndFlush(
					GSON.toJson(new PacketObject(PacketRegistry.getPacketId(packet), packet.writeToPacket())) + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setClientType(EnumClientType clientType) {
		this.clientType = clientType;
	}

	public void register(String username, String email, String password, FutureCallback callback) {
		if (this.clientType != null && this.clientType.equals(EnumClientType.PHONE)) {
			this.sendToServer(new RegisterPacket(username, email, password, callback));
		}
	}

	public void login(String email, String password, FutureCallback callback) {
		if(this.clientType != null && this.clientType.equals(EnumClientType.PHONE)) {
			this.sendToServer(new LoginPacket(email, password, callback));
		}
	}
}
