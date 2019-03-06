package fr.romain120105.infully.server;

import fr.romain120105.infully.server.login.LoginHandler;
import fr.romain120105.infully.server.network.NetworkWrapper;
import fr.romain120105.softlog.LoggerManager;
import fr.romain120105.softlog.logger.Logger;

public class InfullyServer {

	public static InfullyServer INSTANCE;
	public static Logger LOGGER = LoggerManager.getLogger("InfullyServer");
	
	private NetworkWrapper networkWrapper;
	
	private LoginHandler loginHandler;
	
	public static void main(String[] args) {

		try {
			INSTANCE = new InfullyServer();
			INSTANCE.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() throws Exception {
		loginHandler = new LoginHandler().load();
		networkWrapper = new NetworkWrapper(8080 );
		networkWrapper.start();
		
	}

	public LoginHandler getLoginHandler() {
		return this.loginHandler;
	}
	
	public NetworkWrapper getNetworkWrapper() {
		return networkWrapper;
	}
	
	
}
