package model;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class Music extends Thread{
		private String filename;
	    private Player player;	
	    private BufferedInputStream buffer;
	    private boolean isContinue; 
	    private ArrayList<Player> players;
	    
	    
	    // give music name 
	    public Music() throws FileNotFoundException {
	    	this.players = new ArrayList<Player>();
	    	this.isContinue = true;
	    	String temp = "/res/Music/music_";
			for(int i = 1; i <= Model.musicNum; i++){
				this.filename = temp+i+".mp3";
				this.buffer = new BufferedInputStream(
	                    new FileInputStream(this.getClass().getResource(filename).getPath()));
				try {
					this.player = new Player(buffer);
				} catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.players.add(this.player);
				
			}
	    }
	    
	    //tell whether to continue playing by continue
	    public void run(){
	    	while(isContinue){
	    		play();
	    	}
	    }
	    
	    // load and play this song
	    public void play() {
	    	Random random = new Random();
	    	int a = (int)(random.nextInt(Model.musicNum-1));
	        try {
	            player = players.get(a);
	            player.play();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	    
	    //stop the music
	    public void musicStop(){
	    	 try {
		            player.close();
		            this.setMusicPlaying(false);
		        } catch (Exception e) {
		            System.out.println(e);
		        }
	    }
	    
	    //keep playing another random song
	    public void musicRestart(){
	    	this.setMusicPlaying(true);
	    }
	    
	    //decide whether to keep playing song
	    private void setMusicPlaying(boolean b){
	    	this.isContinue = b;
	    }
	    
}
