package fr.romain120105.infully.server.entity;

import io.netty.channel.Channel;

public class Phone extends Client{

	private String uuid;
	
	private String name;
	
	public Phone(String uuid, String name, Channel channel) {
		super(channel);
		this.uuid = uuid;
		this.name = name;

	}

	@Override
	public String getName() {
		return name;
	}
	
	public String getUuid() {
		return uuid;
	}

}
