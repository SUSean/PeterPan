package game;

import model.Model;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import server.Client;

public class Shop extends PApplet{
	private PImage[] shopKeepers;
	private PImage[] characters;
	private PImage background;
	private PImage mayIHelpYou;
	private Game game;
	private int changeShopKeeper;
	private int characterNum;
	private int nowWhichCharacterChosed = 1;
	private boolean[] boughtCharacter;
	private boolean isAllCharacterBought = true;
	private Client client;
	private PImage moneyIcon;
	private String money = "100";
	
	
	public Shop(Game game,Client client, Model model){
		this.game=game;
		this.client=client;
		//load moneyIcon
		moneyIcon = new PImage();
		this.moneyIcon = loadImage(this.getClass().getResource("/res/Shop/money_icon.png").getPath());
		//load shopKeepers' images
		shopKeepers = new PImage[3];
		this.shopKeepers[1] = loadImage(this.getClass().getResource("/res/Shop/shopkeeper_1.png").getPath());
		this.shopKeepers[2] = loadImage(this.getClass().getResource("/res/Shop/shopkeeper_2.png").getPath());
		
		//load background
		background = new PImage();
		this.background = loadImage(this.getClass().getResource("/res/Shop/shelf.png").getPath());
		
		//load characters' images & go_back_sign image
		this.characterNum=model.getCharatorNum();
		characters = new PImage[characterNum+2];
		for(int i=0;i<this.characterNum;i++){
			this.characters[i+1]=loadImage(this.getClass().getResource(model.getCharacterImg(i)).getPath());
		}
		this.characters[11] = loadImage(this.getClass().getResource("/res/Shop/go_back_sign_1.png").getPath());
		
		//load "may I help you"
		mayIHelpYou = new PImage();
		this.mayIHelpYou = loadImage(this.getClass().getResource("/res/Shop/may_I_help_you.png").getPath());
		
/*		//set new font
		this.font = new PFont();
		this.font = createFont(this.getClass().getResource("JLSDataGothicC_NC.otf").getPath());
*/

		//random choose shopKeeper
		changeShopKeeper = (int)(Math.random()*100);
		changeShopKeeper%=2;
		
		//use an array to store which character was bought
		boughtCharacter = new boolean[13];
		for(int i=1;i<11;i++){
			boughtCharacter[i]=this.client.haveCharacterFlag[i-1];
		}

	}
	public void setup(){
		size(500, 700);
		frameRate(15);
	}
	public void draw(){
		
		//draw background
		image(background, 0, 0);
		
		//draw shopKeepers
		if (changeShopKeeper == 0)
			image(shopKeepers[1], width/2 + 100, height/2 + 150, 150, 200);
		else
			image(shopKeepers[2], width/2 + 100, height/2 + 150, 150, 200);
		
		//check if the characters are all bought
		for (int i=1; i < 11; i++){
			if (boughtCharacter[i] == false)
				isAllCharacterBought = false;
		}
		
		//the situation of all characters are bought
		if (isAllCharacterBought == true){
			nowWhichCharacterChosed = 11;
		}
		
		//if the first character is bought
		while(boughtCharacter[nowWhichCharacterChosed] == true){
			nowWhichCharacterChosed++;
		}
		
		//listen key to change character selected
		if(wantToGoBack()&&keyPressed&&key==ENTER){
			game.shopGotoGameStart();
		}
		else if (keyPressed && keyCode == UP){
			int temp = nowWhichCharacterChosed;
			while(true){
				temp--;
				if(temp==0)
					temp=11;
				if(boughtCharacter[temp]==false){
					nowWhichCharacterChosed=temp;
					break;
				}
					
			}
		}
		else if (keyPressed && keyCode == DOWN){
			int temp = nowWhichCharacterChosed;
			while(true){
				temp++;
				if(temp==12)
					temp=1;
				if(boughtCharacter[temp]==false){
					nowWhichCharacterChosed=temp;
					break;
				}
					
			}
		}
		else if (keyPressed && keyCode == LEFT){
			int temp = nowWhichCharacterChosed;
			while(true){
				if(temp==4)
					temp=8;
				else
					temp=(temp+8)%12;
				if(boughtCharacter[temp]==false){
					nowWhichCharacterChosed=temp;
					break;
				}
					
			}
			
		}
		else if (keyPressed && keyCode == RIGHT){
			int temp = nowWhichCharacterChosed;
			while(true){
				if(temp==8)
					temp=4;
				else
					temp=(temp+4)%12;
				if(boughtCharacter[temp]==false){
					nowWhichCharacterChosed=temp;
					break;
				}
			}
			
		}
		else if (!wantToGoBack()&&keyPressed && keyCode == ENTER){
			this.client.setCharacter(nowWhichCharacterChosed,true);
		}
		
		for(int i=1;i<11;i++){
			if (boughtCharacter[i] == true)
				tint(120);
			else 
				tint(0);
			if (nowWhichCharacterChosed == i)
				tint(255);
			image(characters[i], width/13*(1+((i-1)/4)*4), height/15+150*((i%4+3)%4), 100, 150);
			tint(0);
		}
		
		if (nowWhichCharacterChosed == 11){
			tint(255);
			image(characters[11], width/13*9, height/15*9-10, 150, 100);
		}
		else{
			tint(255);
			image(mayIHelpYou, width/13*8, height/15*9, 150, 100);
		}
		tint(255);
		
		//text "~Shop~"
		fill(255, 255, 0);
		text("~Shop~", 40, 35);
		text("Money:"+money, 240, 35);
		textSize(40);

	}
	public int getBoughtCharacter(){
		//if return 11 means want to go back
		return nowWhichCharacterChosed;
	}
	public boolean wantToGoBack(){
		if (nowWhichCharacterChosed == 11){
			return true;
		}
		else return false;
	}
}
