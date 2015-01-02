package game;

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

public class GameScene extends PApplet{
	
	public static final int MARGIN_TOP = 50;
	public static final int NUM_OF_STARS = 10;
	public static final int NUM_OF_CLOUDS = 40;
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
	private PImage backgroundImg,starImg,tunnelBackgroundImg;
	public PImage[] characters,cloudImg;
	private Tunnel[] tunnels;
	private Character character;
	private List<Stars> stars;
	private List<Cloud> clouds;
	boolean[] keys = new boolean[255];
	private Game parentFrame;
	private Model model;
	private Music music;
	private PVector characterVector;
	private PVector starVector;
	private TopBarDelegate topBarDelegate;
	private int score=0,t=0;
	/**
	 * Constructor of a game scene.
	 * 
	 * @param  parentFrame | the JFrame that owns this game scene
	 * @throws IOException 
	 */
	public GameScene(Game parentFrame,Model model) throws IOException{
		this.parentFrame = parentFrame;
		this.backgroundImg = loadImage(this.getClass().getResource(model.getBackground(0)).getPath());
		tunnelBackgroundImg = loadImage(this.getClass().getResource("/res/Background/background.png").getPath());
		this.characters=parentFrame.getCharacterImages();
		this.model=model;
		this.starImg = loadImage(this.getClass().getResource("/res/starGold.png").getPath());
		this.cloudImg=new PImage[2];
		this.cloudImg[0] = loadImage(this.getClass().getResource("/res/Background/cloud_1.png").getPath());
		this.cloudImg[1] = loadImage(this.getClass().getResource("/res/Background/cloud_2.png").getPath());
		this.requestFocus();
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
		makeClouds();
		
		this.tunnels=new Tunnel[tunnelNum];

		this.tunnels[0]=new Tunnel(this,this,0,-1);
		this.tunnels[1]=new Tunnel(this,this,170,-1);
		this.tunnels[2]=new Tunnel(this,this,340,-1);
		
	}
	
	//called by Game to set topBarDelegate to topBar of the game
	public void setTopBarDelegate(TopBarDelegate d){
		this.topBarDelegate=d;
	}
	public void draw(){
		background(255, 255, 255);
		if(!tunnelMode){
				if(time++==300){
					time=0;
					if(score<(level*level)*2){
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
				
				topBarDelegate.setMoney(this.score);
				topBarDelegate.setLevel(this.level);
				
				for(Stars stars : this.stars)
					for(int i=0;i<level;i++)stars.display();
				if(this.stars.size()==0){
					addStar();
				}
				
				if(isHitStars()){
					this.score++;
				}
				
				if(level==5){
					try {
						parentFrame.gameOver();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}else{																//tunnel Mode
			image(this.tunnelBackgroundImg, 0, t++/5+700-this.tunnelBackgroundImg.height, this.tunnelBackgroundImg.width, this.tunnelBackgroundImg.height);
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
					nextLevel();
				}
				for(Cloud cloud : this.clouds)
					for(int i=0;i<level;i++)cloud.display();
				this.character.levelUpAnimation();
			}
			else{
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
	private boolean isHitTunnel(){
		float Y = this.character.getY();
		float X = this.character.getX();
		float W = this.character.getW();
		for(int a=0;a<tunnelNum;a++){
					if( tunnels[a].getX()<X+W/2 && X<(tunnels[a].getX()+160)
							&& Y<tunnels[a].getY()+30){
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
			for(int i=0;i<5;i++)tunnel.display();
	}
	public void nextLevel(){
		level++;
		tunnelMode=false;
		tunnelModeStart=true;
		tunnelModeEnd=false;
		this.clouds.removeAll(clouds);
		makeClouds();
		this.music.musicStop();
		this.music.musicRestart();
		System.out.println("next");
		newBackground();
	}
	public int getChosenCharater(){
		return this.chosenCharacter;
	}
	private void setHitNumber(int x){
		System.out.printf("tunnel %d\n",x);
	}
	@SuppressWarnings("deprecation")
	public void exit(){
		this.music.musicStop();
		this.music.stop();
	}
	/**
	 * Keep removing the passed rock.
	 */
	
}
