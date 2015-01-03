package server;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


public class Server {
	private ServerSocket serverSocket;
	private List<ConnectionThread> connections = new ArrayList<ConnectionThread>();
	private Data data;
	public Server(int portNum) {
		try {
			this.data = new Data();
			this.serverSocket = new ServerSocket(portNum);
			System.out.printf("Server starts listening on port %d.\n", portNum);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	public void runForever() throws JSONException {
		System.out.println("Server starts waiting for client.");
		while(!serverSocket.isClosed()){	
			try {
				Socket connectionToClient = this.serverSocket.accept();
				System.out.println("get connection from client"
						+ connectionToClient.getInetAddress() + ":"
						+ connectionToClient.getPort());
				ConnectionThread connThread = new ConnectionThread(connectionToClient);
				connThread.start();
				this.connections.add(connThread);
				this.sendStart(connThread);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private class ConnectionThread extends Thread{
		private Socket socket;
		private PrintWriter writer;
		private BufferedReader reader;
		public ConnectionThread(Socket socket){
			this.socket=socket;
			try {
				this.writer=new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
				this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public void run (){
			while(!this.socket.isClosed()){
				String line;
				try {
					if((line = reader.readLine()) != null){
						JSONObject message = new JSONObject(line);
						String type = message.getString("Type");
						if(type.equals("User")){
							receiveUser(this,message.getString("UserName"),message.getString("Password"));
						}
						else if(type.equals("Register")){
							receiveRegister(this,message.getString("UserName"),message.getString("Password"));
						}
						else if(type.equals("Score")){
							receiveNewScore(message.getString("UserName"),message.getInt("Score"));
						}
						else if(type.equals("Coin")){
							receiveNewCoin(message.getString("UserName"),message.getInt("Coin"));
						}
						else if(type.equals("Over")){
							sendTopTen(this,data.getTopTen());
						}
						else if(type.equals("List")){
							receiveSong(message.getString("UserName"),message.getString("Feel"),message.getInt("Song"));
						}
						else if(type.equals("GameOver")){
							data.dataWrite();
						}
					}
					else 
						break;
					
				}catch (SocketException e) {
					try {
						this.socket.close();
						data.dataWrite();
						System.out.println("client"
								+ socket.getInetAddress() + ":"
								+ socket.getPort() + "close");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		public void sendMessage(String message){
			this.writer.println(message);
			this.writer.flush();
		}
	}
	
	private void sendStart(ConnectionThread connThread) throws JSONException {
		JSONObject message = new JSONObject();
		message.put("Type", "LogIn");
		String messageString = message.toString();
		sendMessageTo(connThread,messageString);
	}
	private void sendCorrect(ConnectionThread connThread,String name) throws JSONException, IOException {
		newFile(name);
		JSONObject message = new JSONObject();
		message.put("Type", "Correct");
		message.put("Name", name);
		message.put("Coin", data.users.get(name).coin);
		message.put("HighScore", data.users.get(name).score);
		message.put("Characters", data.users.get(name).haveCharacter);
		String messageString = message.toString();
		sendMessageTo(connThread,messageString);
	}
	private void sendWorng(ConnectionThread connThread,int i) throws JSONException {
	JSONObject message = new JSONObject();
		message.put("Type", "Worng");
		if(i==1){
			message.put("Value","1");
		}
		else if(i==2){
			message.put("Value","2");
		}
		String messageString = message.toString();
		sendMessageTo(connThread,messageString);
	}
	public void sendTopTen(ConnectionThread connThread,String[] topTen) throws JSONException {
		JSONObject message = new JSONObject();
		message.put("Type", "Top Ten");
		message.put("Rank", topTen);
		String messageString = message.toString();
		sendMessageTo(connThread,messageString);
		
	}
	public void receiveUser(ConnectionThread connThread,String userName, String password) throws JSONException, IOException {
		for(String name : data.users.keySet()){
			if(name.equals(userName)){
				if(password.equals(data.users.get(name).password)){
					sendCorrect(connThread,name);
				}
				else{
					sendWorng(connThread,1);
				}
				return;
			}
		}
		sendWorng(connThread,1);
	}
	public void receiveRegister(ConnectionThread connThread,String userName, 
								String password) throws JSONException, IOException{
		for(String name : data.users.keySet()){
			if(name.equals(userName)){
				sendWorng(connThread,2);
				return;
			}
		}
		User temp= new User(userName,password);
		data.users.put(userName,temp);
		temp.addCoin("0");
		temp.addHighScore("0");
		temp.addCharacter(0,"true");
		for(int i=1;i<10;i++)
			temp.addCharacter(i,"false");
		sendCorrect(connThread,userName);
	}
	public void receiveSong(String name, String feel, int num) throws IOException {
		data.users.get(name).addSong(feel,num);
	}
	public void receiveNewScore(String name, int score) {
		data.users.get(name).addHighScore(Integer.toString(score));
	}
	public void receiveNewCoin(String name, int coin) {
		data.users.get(name).addCoin(Integer.toString(coin));
	}
	private void sendMessageTo(ConnectionThread client,String message) {
		client.sendMessage(message);
	}
	public void newFile(String filePathAndName) throws IOException {
		String filePath = "List/"+filePathAndName+".csv";
		filePath = filePath.toString();
		File myFilePath = new File(filePath);
		if (!myFilePath.exists()) {
			System.out.println(filePath);
			myFilePath.createNewFile();			
		}
	}
	public static void main(String[] args) throws JSONException {
		
		Server server = new Server(8000);
		server.runForever();
	}
	
}
