package game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONException;

import model.Model;
import model.Music;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import server.Client;

public class GameScene extends PApplet /*implements KeyListener*/{
	
	public static final int MARGIN_TOP = 50;
	public static final int NUM_OF_STARS = 10;
	public static final int NUM_OF_CLOUDS = 30;
	public static final int tunnelNum = 3;
	public static final int keypressedTime = 10;
	public boolean tunnelMode=false;
	public boolean tunnelModeStart=true;
	public boolean tunnelModeEnd=false;
	private boolean keyLock=false;
	private int level=1;
	private int chosenCharacter;
	private int time=0;
	private int levelUpAnimationTime=0;
	private int messageShowTime=0;
	private PImage backgroundImg,starImg,tunnelBackgroundImg,moneyIcon;
	public PImage[] characters,cloudImg,coinImg;
	private Tunnel[] tunnels;
	private Character character;
	private List<Stars> stars;
	private List<Cloud> clouds;
	private List<Coin> coins;
	boolean[] keys = new boolean[255];
	private Game parentFrame;
	private Model model;
	private Music music;
	private PVector characterVector;
	private PVector starVector;
	private TopBarDelegate topBarDelegate;
	private ScoreBarDelegate scoreBarDelegate;
	public int score=0;
	private int currentCoin=0;
	private int nowScore;
	private int earnCoin=0;
	private int targetScore=5;
	private Client client;
	private int hitNumber,t;
	/**
	 * Constructor of a game scene.
	 * 
	 * @param  parentFrame | the JFrame that owns this game scene
	 * @throws IOException 
	 */
	public GameScene(Game parentFrame,Client client,Model model) throws IOException{
		this.parentFrame = parentFrame;
		this.backgroundImg = loadImage(this.getClass().getResource(model.getBackground(0)).getPath());
		tunnelBackgroundImg = loadImage(this.getClass().getResource("/res/Background/background.png").getPath());
		this.characters=parentFrame.getCharacterImages();
		this.model=model;
		this.client=client;
		this.currentCoin=this.client.coin;
		this.starImg = loadImage(this.getClass().getResource("/res/starGold.png").getPath());
		this.moneyIcon = loadImage(this.getClass().getResource("/res/Shop/money_icon.png").getPath());
		this.cloudImg=new PImage[2];
		this.cloudImg[0] = loadImage(this.getClass().getResource("/res/Background/cloud_1.png").getPath());
		this.cloudImg[1] = loadImage(this.getClass().getResource("/res/Background/cloud_2.png").getPath());
		this.coinImg = new PImage[4];
		this.coinImg[0] = loadImage(this.getClass().getResource("/res/Coin/coins_1.png").getPath());
		this.coinImg[1] = loadImage(this.getClass().getResource("/res/Coin/coins_2.png").getPath());
		this.coinImg[2] = loadImage(this.getClass().getResource("/res/Coin/coins_3.png").getPath());
		this.coinImg[3] = loadImage(this.getClass().getResource("/res/Coin/coins_4.png").getPath());
		this.requestFocus();
		this.addKeyListener(this);
	}
	
	public void setup(){
		size(500, 700);
		frameRate(60);
		try {
			this.music=new Music();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		music.start();
		this.chosenCharacter=parentFrame.getChosenCharacter();
		this.character = new Character(this,this.characters);
		this.stars = new CopyOnWriteArrayList<Stars>();
		for(int i=0;i<NUM_OF_STARS;i++)addStar();	
		
		this.clouds = new CopyOnWriteArrayList<Cloud>();
		this.coins = new CopyOnWriteArrayList<Coin>();
		makeClouds();
		makeCoins();
		this.tunnels=new Tunnel[tunnelNum];

		this.tunnels[0]=new Tunnel(this,this,"happy",0,-1);
		this.tunnels[1]=new Tunnel(this,this,"normal",170,-1);
		this.tunnels[2]=new Tunnel(this,this,"sad",340,-1);
		nowScore=0;
		scoreBarDelegate.setFullScore(targetScore);
	}
	
	//called by Game to  topBarDelegate to topBar of the game
	public void setTopBarDelegate(TopBarDelegate d){
		this.topBarDelegate=d;
	}
	public void setScoreBarDelegate(ScoreBarDelegate d){
		this.scoreBarDelegate=d;
	}
	public void draw(){
		background(255, 255, 255);

		if(!tunnelMode){
			
				if(time++==500){
					time=0;
					if(nowScore<targetScore){
						try {
							parentFrame.gameOver();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						tunnelMode=true;
					}
				}
				
				image(this.backgroundImg, 0, 50, 500, 700);
				image(this.moneyIcon, 10, 600, 50, 50);
				textSize(20);
				fill(0, 0, 0);
				text("Money:"+currentCoin, 60, 635);
				if(keys[LEFT]){
					this.character.setMovement(Character.LEFT);this.character.display();
				}
				if(keys[RIGHT]){
					this.character.setMovement(character.RIGHT);this.character.display();
				}
				if(keys[UP]){
					this.character.setMovement(Character.UP);this.character.display();
				}		
				if(keys[DOWN]){
					this.character.setMovement(Character.DOWN);this.character.display();
				}
				
				if(!keys[LEFT]&&!keys[RIGHT]&&!keys[UP]&&!keys[DOWN])this.character.setMovement(Character.STAY);
				
				this.character.display();
				//Refresh top bar message
				
				topBarDelegate.setScore(this.score);
				topBarDelegate.setLevel(this.level);
				scoreBarDelegate.setCurrentScore(this.nowScore);
				if(this.score<(this.client.highScore))
					topBarDelegate.setHighestScore(this.client.highScore);
				else
					topBarDelegate.setHighestScore(this.score);
				for(Stars stars : this.stars){
					for(int i=0;i<level;i++)stars.display();
				}
					
					
				if(this.stars.size()==0){
					addStar();
				}
				
				if(isHitStars()){
					this.score++;
					this.nowScore++;
				}
				
		}else{	//tunnel Mode
			
			if(tunnelModeStart){
				this.character.moveToTunnelStartMode();
				if(++messageShowTime==100){
					messageShowTime=0;
					tunnelModeStart=false;
					this.character.setMovement(Character.STAY);
				}
			}
			else if(tunnelModeEnd){
				if(++levelUpAnimationTime==500){
					levelUpAnimationTime=0;
					try {
						nextLevel();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				image(this.tunnelBackgroundImg, 0, t++/5+700-this.tunnelBackgroundImg.height, this.tunnelBackgroundImg.width, this.tunnelBackgroundImg.height);
				image(this.moneyIcon, 10, 600, 50, 50);
				textSize(20);
				fill(0, 0, 0);
				text("Money:"+currentCoin, 60, 635);
				for(Cloud cloud : this.clouds)
					cloud.display();
				for(Coin coin : this.coins){
					coin.display();
					if(coin.getY()>600){
						this.coins.remove(coin);
						currentCoin++;
					}
				}
				this.character.levelUpAnimation();
			}
			else{
				choosingCategoryBackground();
				setTunnelColor();
				displayTunnel();
				isHitTunnel();
				if(!keyLock){
					if(keyPressed&&keyCode==LEFT){
						keyLock=true;
						this.character.setMovement(Character.LEFT);
					}
					else if(keyPressed&&keyCode==RIGHT){
						keyLock=true;
						this.character.setMovement(Character.RIGHT);
					}
					else if(keyPressed&&keyCode==UP){
						this.character.setMovement(Character.UP);
					}		
				}
				else this.character.setMovement(Character.STAY);
				this.character.tunnelModeDisplay();

			}
		}
		
	}
	
	public void keyPressed(){
		keys[keyCode] = true;
	}
	public void keyReleased() {
		  keys[keyCode] = false;
		  keyLock=false;
	}
	private void newBackground(){
		this.backgroundImg=loadImage(this.getClass().getResource(model.getBackground(level)).getPath());
	}
	
	/**
	 * A method for checking whether the plane crashed or not
	 * 
	 * The method will first check the plane hit the top or the ground, if so, return true.
	 * After that, the method will check the plane overlapped to a rock or not, if so, return true.
	 * If there's no remaining rock, the player wins.
	 * Otherwise, return false.
	 *
	 * @return true if the plane is crashed
	 */
	private void addStar(){
		int x,y;
		Random random = new Random();
		
		x=random.nextInt(this.width-this.starImg.width*2);
		y=random.nextInt(this.height);
		this.stars.add( new Stars(this, this.starImg, x,-y*2) );
		
	}
	private void makeClouds(){
		int x,y,n;
		Random random = new Random();
		for(int i=0;i<NUM_OF_CLOUDS;i++){
			n=random.nextInt(100);
			x=random.nextInt(this.width-this.cloudImg[0].width);
			y=random.nextInt(this.height*10);
			this.clouds.add( new Cloud(this, this.cloudImg[n%2], x,-y) );
		}
		
	}
	private void makeCoins(){
		for(int i=1;i<=level;i++){
			this.coins.add(new Coin(this,this.coinImg,500,-i*15));
		}
	}
	private boolean isHitTunnel(){
		float Y = this.character.getY();
		float X = this.character.getX();
		float W = this.character.getW();
		for(int a=0;a<tunnelNum;a++){
					if( tunnels[a].getX()<X+W/2 && X<(tunnels[a].getX()+160)
							&& Y<tunnels[a].getY()+30){
						System.out.printf("hit tunnel W=%f X=%f TX=%f\n",W,X,tunnels[a].getX());
						setHitNumber(a);
						tunnelModeEnd=true;
						for(int i=0;i<tunnelNum;i++)tunnels[i].setY(-1);
						return true;
					}
		}
		return false;
	}
	public boolean isHitStars(){
		
		int Y = this.character.getY();
		int X = this.character.getX();
		characterVector=new PVector(X,Y);
		
		for(Stars star :stars){
			starVector=new PVector(star.getX(),star.getY());
					if( characterVector.dist(starVector)<50 ){
						stars.remove(star);
						addStar();
						return true;
					}
			if(star.getY()>this.height){stars.remove(star);addStar();}
		}
		return false;
	}
	private void displayTunnel(){
		for(Tunnel tunnel : this.tunnels)
			tunnel.display();
	}
	public void nextLevel() throws JSONException{
		
		if(this.score>this.client.highScore){
			this.client.highScore=this.score;
			this.client.sendNewScore();
		}
		earnCoin+=level;
		this.client.coin+=this.earnCoin;
		this.client.sendNewCoin();
		level++;
		nowScore=0;
		tunnelMode=false;
		tunnelModeStart=true;
		tunnelModeEnd=false;
		this.nowScore=0;
		scoreBarDelegate.setFullScore(targetScore);
		this.clouds.removeAll(clouds);
		this.coins.removeAll(coins);
		makeCoins();
		makeClouds();
		this.stars.removeAll(stars);
		for(int i=0;i<NUM_OF_STARS;i++)addStar();
		this.music.musicStop();
		this.client.sendSong(this.tunnels[hitNumber].string,this.music.musicName);
		this.music.musicRestart();
		System.out.println("next");
		newBackground();
		if(level==11){
			try {
				this.parentFrame.winFlag=true;
				parentFrame.gameOver();
			} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	}
	public int getChosenCharater(){
		return this.chosenCharacter;
	}
	private void setHitNumber(int x){
		this.hitNumber=x;
		System.out.printf("tunnel %d\n",x);
	}
	/**
	 * Keep removing the passed rock.
	 */
	
	
	public void choosingCategoryBackground(){
		this.fill(240, 248, 255);
		this.stroke(240, 248, 255);
		this.rect(0, 0, (int)500/3, 700);
		this.fill(230, 230, 250);
		this.stroke(230, 230, 250);
		this.rect((int)500/3, 0, (int)500/3+1, 700);
		this.fill(255, 240, 245);
		this.stroke(255, 240, 245);
		this.rect((int)1000/3, 0, (int)500/3, 700);
	}
	
	public void setTunnelColor(){
		if(this.character.getX() < (int)500/3){
			this.tunnels[0].tunnelHighlight();
			this.tunnels[1].tunnelColorRecover();
			this.tunnels[2].tunnelColorRecover();
		}
		else if(this.character.getX() < (int)1000/3){
			this.tunnels[1].tunnelHighlight();
			this.tunnels[0].tunnelColorRecover();
			this.tunnels[2].tunnelColorRecover();
		}
		else if(this.character.getX() < (int)1500/3){
			this.tunnels[2].tunnelHighlight();
			this.tunnels[1].tunnelColorRecover();
			this.tunnels[0].tunnelColorRecover();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void exit(){
		this.music.musicStop();
		this.music.stop();
	}
}
