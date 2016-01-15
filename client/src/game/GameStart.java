package game;

import java.io.IOException;

import javax.swing.JFrame;

import processing.core.PApplet;
import processing.core.PImage;
import server.Client;

public class GameStart extends PApplet{
	PImage background, moneyIcon,highestScoreIcon;
	PImage play;
	PImage buy;
	private Game game;
	private boolean isBuy;
	private int money ;
	private Client client;
	public GameStart(Game game, Client client){
		//load moneyIcon
		moneyIcon = new PImage();
		this.moneyIcon = loadImage(this.getClass().getResource("/res/Shop/money_icon.png").getPath());
		highestScoreIcon = new PImage();
		this.highestScoreIcon = loadImage(this.getClass().getResource("/res/Shop/HighestScore.png").getPath());
		//load background image
		background = new PImage();
		this.background = loadImage(this.getClass().getResource("/res/Background/logInBackground.jpg").getPath());
		this.game=game;
		this.client=client;
		this.money=this.client.coin;
	}
	public void setup(){
		size(500, 700);
		frameRate(60);
	}
	public void draw(){
		background(255, 255, 255);
		image(this.background, -200, -700, this.background.width, this.background.height);
		
		
		if (keyPressed && keyCode == UP){//if press UP, then go to "Game Start" choice mode
			isBuy = false;
		}
		if (keyPressed && keyCode == DOWN){	//if press DOWN, then go to "Buy" choice mode
			isBuy = true;
		}
		
		//set username text and money text
		fill(255, 0, 0);
		textSize(40);
		text(this.client.name, 40, 40);
		textSize(30);
		image(this.moneyIcon, 20, 50, 50, 50);
		text("Money :\n"+money, 70, 75);
		image(this.highestScoreIcon,width/2,50, 50, 50);
		text("High Score :\n"+this.client.highScore,width/2+50,75);
		noFill();
		
		if (isBuy == false){
			//display "Game Start" as orange color
			rect(50, 125, 400, 250);
			textSize(60);
			fill(255, 100, 0);
			text("Game Start", 90, 230);
			textSize(30);
			text("Press Enter To Start", 110, 300);
			stroke(0, 0, 0);
			strokeWeight(3);
			noFill();
			
			//display "Buy" as black color
			rect(50, 400, 400, 250);
			textSize(60);
			fill(0, 0, 0);
			text("Buy", 110, 500);
			textSize(30);
			text("Press Enter Goto Shop", 95, 580);
			stroke(255, 100, 0);
			strokeWeight(3);
			noFill();
			
			if(keyPressed&&key==ENTER){
				game.initChooseCharacter();
			}
		}
		else{
			//display "Game Start" as black color
			rect(50, 125, 400, 250);
			textSize(60);
			fill(0, 0, 0);
			text("Game Start", 90, 230);
			textSize(30);
			text("Press Enter To Start", 110, 300);
			stroke(255, 100, 0);
			strokeWeight(3);
			noFill();
			
			//display "Buy" as orange color
			rect(50, 400, 400, 250);
			textSize(60);
			fill(255, 100, 0);
			text("Buy", 110, 500);
			textSize(30);
			text("Press Enter Goto Shop", 95, 580);
			stroke(0, 0, 0);
			strokeWeight(3);
			noFill();
			if(keyPressed&&key==ENTER){
				game.initShop();
			}
		}	
	}
	public boolean isGame(){
		return !isBuy;
	}
	public boolean isBuy(){
		return isBuy;
	}
}
