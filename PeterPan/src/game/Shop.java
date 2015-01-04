package game;

import javax.swing.JOptionPane;

import org.json.JSONException;

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
	private int[] cost={0,0,100,150,200,250,300,350,400,450,500};
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
		if(keyPressed&&key==ENTER){
			if(wantToGoBack())
				game.shopGotoGameStart();
			else{
				try {
					buy(nowWhichCharacterChosed);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if (keyPressed && keyCode == UP){
			int temp = nowWhichCharacterChosed;
			while(true){
				temp--;
				if(temp<=0)
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
				if(temp>=12)
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
					temp=9;
				else if(temp==1)
					temp=10;
				else if(temp==2)
					temp=11;
				else if(temp==3)
					temp=8;
				else
					temp-=4;;
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
					temp=1;
				else if(temp==9)
					temp=2;
				else if(temp==10)
					temp=3;
				else if(temp==11)
					temp=4;
				else
					temp+=4;
				if(boughtCharacter[temp]==false){
					nowWhichCharacterChosed=temp;
					break;
				}
			}
			
		}
		
		
		for(int i=1;i<11;i++){
			if (boughtCharacter[i] == true)
				tint(120);
			else{
				textSize(20);
				fill(255, 255, 0);
				text(cost[i], width/13*(1+((i-1)/4)*4)+100, height/15+150*((i%4+3)%4)+140);
				tint(0);
			}
				
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
			image(mayIHelpYou, width/13*9, height/15*9, 150, 100);
		}
		tint(255);
		
		//text "~Shop~"
		fill(255, 255, 0);
		textSize(40);
		text("~Shop~", 40, 35);
		image(this.moneyIcon, 200, 0, 60, 60);
		textSize(30);
		text("Money:"+this.client.coin, 260, 35);
		

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
	public void buy(int now) throws JSONException {
		String[] option = {"Yes","No"};
		int n=JOptionPane.showOptionDialog(this,"Do you want to buy this character","BUY",
									JOptionPane.OK_CANCEL_OPTION,
									JOptionPane.QUESTION_MESSAGE,
									null,option, option[0]);
		if(n==0){
			if(this.client.coin>=cost[nowWhichCharacterChosed]){
				this.client.coin-=cost[nowWhichCharacterChosed];
				this.client.setCharacter(nowWhichCharacterChosed-1,true);
				boughtCharacter[nowWhichCharacterChosed]=this.client.haveCharacterFlag[nowWhichCharacterChosed-1];
				this.client.sendNewCharacter(nowWhichCharacterChosed-1);
				this.client.sendNewCoin();
			}
			else
				error("Not enough coin");
		}
		else 
			return;
	}
	public void error(String error) {
		String[] option = {"OK"};
		JOptionPane.showOptionDialog(this,error,"warning",
									JOptionPane.OK_CANCEL_OPTION,
									JOptionPane.QUESTION_MESSAGE,
									null,option, option[0]);
	}
}
