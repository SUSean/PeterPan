package game;

import java.io.IOException;

import javax.swing.JFrame;

import processing.core.PApplet;
import processing.core.PImage;

public class GameStart extends PApplet{
	PImage background, moneyIcon;
	PImage play;
	PImage buy;
	private Game game;
	private boolean isBuy;
	private String money = "100";
	
	public GameStart(Game game){
		//load moneyIcon
		moneyIcon = new PImage();
		this.moneyIcon = loadImage(this.getClass().getResource("/res/Shop/money_icon.png").getPath());
		//load background image
		background = new PImage();
		this.background = loadImage(this.getClass().getResource("/res/Background/background_4.jpg").getPath());
		this.game=game;
	}
	public void setup(){
		size(500, 700);
		frameRate(60);
	}
	public void draw(){
		background(255, 255, 255);
		image(this.background, 0, 0, this.background.width, this.background.height);
		image(this.moneyIcon, 60, 20, 70, 70);
		
		if (keyPressed && keyCode == UP){//if press UP, then go to "Game Start" choice mode
			isBuy = false;
		}
		if (keyPressed && keyCode == DOWN){	//if press DOWN, then go to "Buy" choice mode
			isBuy = true;
		}
		
		//set username text and money text
		fill(0, 255, 0);
		textSize(50);
		textAlign(LEFT);
		text("Money:"+money, 150, 80);
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
