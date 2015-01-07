package model;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;


public class Music extends Thread{
		private String filename;
	    private AdvancedPlayer player;	
	    private BufferedInputStream buffer;
	    private boolean isContinue; 
	    private boolean isReplay; 
	    private ArrayList<AdvancedPlayer> players;
	    public int musicName = 0; 
	    private int pausedOnFrame = 0;
	    // give music name 
	    public Music() throws FileNotFoundException {
	    	this.players = new ArrayList<AdvancedPlayer>();
	    	this.isContinue = true;
	    	this.isReplay = false;
	    	String temp = "/res/Music/music_";
			for(int i = 1; i <= Model.musicNum; i++){
				this.filename = temp+i+".mp3";
				this.buffer = new BufferedInputStream(
	                    new FileInputStream(this.getClass().getResource(filename).getPath()));
				try {
					this.player = new AdvancedPlayer(buffer);

					this.player.setPlayBackListener(new PlaybackListener() {
					    @Override
					    public void playbackFinished(PlaybackEvent event) {
					        pausedOnFrame = event.getFrame();
					    }
					});
				} catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.players.add(this.player);
				
			}
	    }
	    
	    //tell whether to continue playing by continue
	    public void run(){
	    	while(true){
	    		if(isContinue){
	    			if(isReplay)
	    				replay();
	    			else
	    				play();
	    		}
	    	}
	    }
	    
	    // load and play this song
	    public void play() {
	    	Random random = new Random();
	    	int a = (int)(random.nextInt(Model.musicNum));
	        try {
	        	this.player = players.get(a);
	        	pausedOnFrame=0;
	            musicName = a+1;
	            this.player.play();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    }
	    //replay
	    public void replay()  {
	    	this.filename = "/res/Music/music_"+musicName+".mp3";
			try {
				this.buffer = new BufferedInputStream(
				        new FileInputStream(this.getClass().getResource(filename).getPath()));
				this.player = new AdvancedPlayer(buffer);
				this.player.setPlayBackListener(new PlaybackListener() {
				    @Override
				    public void playbackFinished(PlaybackEvent event) {
				        pausedOnFrame += event.getFrame();
				    }
				});
		    	
				this.player.play(pausedOnFrame/50,Integer.MAX_VALUE);
			} catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	    //stop the music
	    public void musicStop(){
	    	 try {
	    		 	this.player.stop();
		            this.setMusicPlaying(false);
		            this.isReplay=true;
		        } catch (Exception e) {
		        	e.printStackTrace();
		        }
	    }
	    //music close
	    public void musicClose(){
	    	 try {
	    		 	System.out.println("OK"+musicName);
	    		 	this.player.close();
		            this.setMusicPlaying(false);
		            this.isReplay=false;
		        } catch (Exception e) {
		        	e.printStackTrace();
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
