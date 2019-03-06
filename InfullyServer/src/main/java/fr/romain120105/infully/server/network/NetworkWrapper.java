package fr.romain120105.infully.server.network;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.romain120105.infully.server.InfullyServer;
import fr.romain120105.infully.server.entity.Client;
import fr.romain120105.infully.server.entity.Raspberry;
import fr.romain120105.infully.server.packets.LoginPacket.LoginPacketResponse;
import fr.romain120105.infully.server.packets.IPacketSender;
import fr.romain120105.infully.server.packets.PacketObject;
import fr.romain120105.infully.server.packets.PacketRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NetworkWrapper extends Thread {

	private static Gson GSON = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();

	
	private int port;

	private ChannelNetworkInitializer channelNetworkInitializer = new ChannelNetworkInitializer(this);
	private NetworkHandler networkHandler = new NetworkHandler(this);
	
	private ArrayList<Client> connectedClients = new ArrayList<>();
	
	public NetworkWrapper(int port) {
		this.port = port;
	}
	

	@Override
	public void run() {
		super.run();
		PacketRegistry.registerDefaultPackets();
		InfullyServer.LOGGER.info("Starting InfullyServer at port : " + port);
		
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workedGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workedGroup).channel(NioServerSocketChannel.class)
					.childHandler(this.channelNetworkInitializer);
			
			 bootstrap.bind(port).sync().channel().closeFuture().sync();
		}catch(InterruptedException e) {
			InfullyServer.LOGGER.catching(e);
		} finally {
			bossGroup.shutdownGracefully();
			workedGroup.shutdownGracefully();

		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public ChannelNetworkInitializer getChannelNetworkInitializer() {
		return channelNetworkInitializer;
	}
	
	public NetworkHandler getNetworkHandler() {
		return networkHandler;
	}
	
	public ArrayList<Client> getConnectedClients() {
		return connectedClients;
	}
	
	public boolean isClientByChannelConnected(Channel channel) {
		for(Client client : connectedClients) {
			if(client.getChannel().equals(channel)) {
				return true;
			}
		}
		return false;
	}
	
	public Client getConnectedClientByChannel(Channel channel) {
		for(Client client : connectedClients) {
			if(client.getChannel().equals(channel)) {
				return client;
			}
		}
		return null;
	}


	public void onClientConnect(Client client) {
		System.out.println("A " + (client instanceof Raspberry ? "Raspberry" : "Phone") + " has connect to the server at the name : " + client.getName());
		this.connectedClients.add(client);
	}


	public void onClientDisconnect(Client client) {
		System.out.println("A " + (client instanceof Raspberry ? "Raspberry" : "Phone") + " has disconnect to the server at the name : " + client.getName());
		this.connectedClients.remove(client);
	}


	public void sendPacketToChannel(IPacketSender packet, Channel channel) {
		channel.writeAndFlush(GSON.toJson(new PacketObject(PacketRegistry.getPacketId(packet), packet.writeToPacket(null))) + "\r\n");

	}

}
