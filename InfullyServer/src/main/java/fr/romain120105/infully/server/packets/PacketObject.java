package fr.romain120105.infully.server.packets;

import java.util.HashMap;

public class PacketObject {

	private int id;

	private HashMap<String, Object> datas;

	public PacketObject(int id, HashMap<String, Object> datas) {
		this.id = id;
		this.datas = datas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HashMap<String, Object> getDatas() {
		return datas;
	}

	public void setDatas(HashMap<String, Object> datas) {
		this.datas = datas;
	}

}
