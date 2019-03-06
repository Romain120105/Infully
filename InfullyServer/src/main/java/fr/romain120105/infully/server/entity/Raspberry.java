package fr.romain120105.infully.server.entity;

import io.netty.channel.Channel;

public class Raspberry extends Client{

	private String appId;
	
	public Raspberry(String appId, Channel channel) {
		super(channel);
		this.appId = appId;

	}

	@Override
	public String getName() {
		return appId;
	}

}
