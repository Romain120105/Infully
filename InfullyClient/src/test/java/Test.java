import java.io.IOException;

import fr.romain120105.infully.client.login.FutureCallback;
import fr.romain120105.infully.client.network.EnumClientType;
import fr.romain120105.infully.client.network.NetworkWrapper;

public class Test {

	public static void main(String[] args) {
		new Test().start();
	}

	private void start() {
		NetworkWrapper wrapper = new NetworkWrapper("51.255.223.161", 8080);
		try {
			wrapper.run();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		
		wrapper.setClientType(EnumClientType.PHONE);
		

		

		wrapper.login("email", "motdepasse", new FutureCallback() {
			
			@Override
			public void callback(boolean sucess, String message) {
				System.out.println(sucess + " / " +message);
			}
		});



	}
	
}
