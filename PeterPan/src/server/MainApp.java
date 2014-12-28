package server;

import org.json.JSONException;

public class MainApp {
	public static void main(String args[]) throws JSONException{
		for(int i=1;i<11;i++)
			System.out.println(1+((i-1)/4)*4);
		Server server=new Server(8000);
		server.runForever();
		Client client=new Client();
		client.setIPAddress("127.0.0.1").setPort(8000).connect();
	}

}
