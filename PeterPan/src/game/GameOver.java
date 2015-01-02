package game;

import processing.core.PApplet;
import processing.core.PImage;
import server.Client;

public class GameOver extends PApplet{
	PImage background, textGameOver, playAgain, exit;
	String[] topTenName;
	int[] topTenScore;
	private Client client;
	private boolean rightButton;
	private int isTopTenPlayer;
	private Game game;

	public GameOver(Client client,Game game){
		this.game=game;
		this.client=client;
		//load background
		background = new PImage();
		this.background = loadImage(this.getClass().getResource("/res/Background/background_1.jpg").getPath());
	
		//load "Game Over" text
		textGameOver = new PImage();
		this.textGameOver = loadImage(this.getClass().getResource("/res/Shop/textGameOver.png").getPath());
		
		//load buttons
		playAgain = new PImage();
		this.playAgain = loadImage(this.getClass().getResource("/res/Shop/play_again_button.png").getPath());
		exit = new PImage();
		this.exit = loadImage(this.getClass().getResource("/res/Shop/exit_button.png").getPath());
		
		//set topTenName 
		topTenName = new String[11];	
		for(int i=1;i<11;i++){
			topTenName[i]=this.client.topTen[i-1];
		}
		//set topTenScore
		//topTenScore = new int[11];

	}
	public void setup(){
		size(500, 700);
	}
	public void draw(){
		
		//set background and "Game Over" text images
		image(this.background, 0, 0);
		image(this.textGameOver, 40, 20);
		
		
		isTopTenPlayer = 1;
		
		//list top ten score
		for (int i=1; i < 11; i++){
			if (i == isTopTenPlayer)
				fill(255, 255, 0);
			else fill(255, 255, 255);
			text(topTenName[i], 20, 160 + 45*(i-1));
			textSize(35);
			noFill();
		}
		
		if (keyPressed && keyCode == ENTER){
			
		}
		else if (keyPressed && keyCode == LEFT){
			rightButton = false;
		}
		else if (keyPressed && keyCode == RIGHT){
			rightButton = true;
		}
		//select play again or exit
		if (rightButton){
			tint(120);
			image(this.playAgain, width/9*2, 600, 80, 80);
			tint(255);
			image(this.exit, width/10*6, 595, 90, 90);
			tint(225);
			if(keyPressed&&key==ENTER){
				game.exitGame();
			}
		}
		else{
			tint(255);
			image(this.playAgain, width/9*2, 600, 80, 80);
			tint(120);
			image(this.exit, width/10*6, 595, 90, 90);
			tint(225);
			if(keyPressed&&key==ENTER){
				game.restart();
			}
		}
	}
}
