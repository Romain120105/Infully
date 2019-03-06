package fr.romain120105.infully.server.network;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChannelNetworkInitializer extends ChannelInitializer<SocketChannel> {

	private NetworkWrapper wrapper;

	public ChannelNetworkInitializer(NetworkWrapper wrapper) {
		super();
		this.wrapper = wrapper;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast(new StringDecoder());
		pipeline.addLast(new StringEncoder());

		pipeline.addLast(new NetworkHandler(wrapper));
	}

}
