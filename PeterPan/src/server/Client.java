package server;

import game.GameLogIn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Client {
	private String destinationIPAddr;
	private int destinationPortNum;
	private Socket socket;
	private PrintWriter writer;
	private ClientThread connection;
	private GameLogIn login;
	public String name;
	public int coin;
	public int highScore;
	public boolean[] haveCharacterFlag=new boolean[10];
	public String[] topTen=new String[10];
	public void connect() {
		try {
			this.socket = new Socket(this.destinationIPAddr,this.destinationPortNum);
			this.writer=new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.connection = new ClientThread(reader);
			this.connection.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private class ClientThread extends Thread{
		private BufferedReader reader;
		public ClientThread(BufferedReader reader){
			this.reader=reader;
		}
		public void run (){
			while(!socket.isClosed()){
				String line;
				try {
					if((line = reader.readLine()) != null){
						JSONObject message = new JSONObject(line);
						String type = message.getString("Type");
						if(type.equals("LogIn")){
							receiveLogIn();
						}
						else if(type.equals("Correct")){
							receiveCorrect(message.getString("Name"),
											message.getString("Coin"),
											message.getString("HighScore"),
											message.getJSONArray("Characters")
											);
						}
						else if(type.equals("Worng")){
							receiveWorng(message.getString("Value"));
						}
						else if(type.equals("Top Ten")){
							receiveTopTen(message.getJSONArray("Rank"));
						}
					}
					else 
						break;
				} catch (SocketException e) {
					try {
						socket.close();
						System.out.println("server"
								+ socket.getInetAddress() + ":"
								+ socket.getPort() + "close");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendUser(String userName, String password) throws JSONException {
		JSONObject message = new JSONObject();
		message.put("Type", "User");
		message.put("UserName", userName);
		message.put("Password", password);
		String messageString = message.toString();
		sendMessage(messageString);
	}
	public void sendRegister(String userName, String password) throws JSONException {
		JSONObject message = new JSONObject();
		message.put("Type", "Register");
		message.put("UserName", userName);
		message.put("Password", password);
		String messageString = message.toString();
		sendMessage(messageString);
		
	}
	public void sendNewScore() throws JSONException {
		JSONObject message = new JSONObject();
		message.put("Type", "Score");
		message.put("UserName", name);
		message.put("Score", this.highScore);
		String messageString = message.toString();
		sendMessage(messageString);
	}
	public void sendNewCoin() throws JSONException {
		JSONObject message = new JSONObject();
		message.put("Type", "Coin");
		message.put("UserName", name);
		message.put("Coin", this.coin);
		String messageString = message.toString();
		sendMessage(messageString);
	}
	public void sendSong(String feel, int musicNum) throws JSONException {
		JSONObject message = new JSONObject();
		message.put("Type", "List");
		message.put("UserName", name);
		message.put("Feel", feel);
		message.put("Song", musicNum);
		String messageString = message.toString();
		sendMessage(messageString);		
	}
	public void sendOver() throws JSONException {
		JSONObject message = new JSONObject();
		message.put("Type", "Over");
		String messageString = message.toString();
		sendMessage(messageString);
	}
	public void sendGameOver() throws JSONException {
		JSONObject message = new JSONObject();
		message.put("Type", "GameOver");
		String messageString = message.toString();
		sendMessage(messageString);
	}
	public void sendMessage(String message){
		this.writer.println(message);
		this.writer.flush();
	}
	
	public void receiveLogIn() {
		login = new GameLogIn(this);
	}
	public void receiveCorrect(String name,String coin, String highScore, JSONArray characters) {
		setCoin(Integer.parseInt(coin));
		sethighScore(Integer.parseInt(highScore));
		this.name=name;
		for(int i=0;i<10;i++){
			try {
				setCharacter(i,characters.getBoolean(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		login.gameStart();
	}
	public void receiveWorng(String error) {
		if(error.equals("1")){
			login.error("user name or password uncorrect");
		}
		else if(error.equals("2")){
			login.error("user name exist");
		}
	}
	public void receiveTopTen(JSONArray topTen) {
		for(int i=0;i<10;i++){
			try {
				this.topTen[i]=topTen.getString(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void setCoin(int coin) {
		this.coin=coin;
	}
	public void sethighScore(int highScore) {
		this.highScore=highScore;
	}
	public void setCharacter(int i, boolean flag) {
		this.haveCharacterFlag[i]=flag;
	}
	public Client setIPAddress(String IPAddress) {
		this.destinationIPAddr = IPAddress;
		return this;
	}
	
	public Client setPort(int portNum) {
		this.destinationPortNum = portNum;
		return this;
	}
	public static void main(String[] args) {
		Client client = new Client();
		client.setIPAddress("127.0.0.1").setPort(8000).connect();
	}
	

}
