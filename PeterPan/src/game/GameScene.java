package game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import model.Model;
import model.Music;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class GameScene extends PApplet{
	
	public static final int MARGIN_TOP = 50;
	public static final int NUM_OF_STARS = 10;
	public static final int tunnelNum = 3;
	private int level=1;
	private int chosenCharacter;
	private int time=0;
	private PImage backgroundImg,starImg;
	public PImage[] characters;
	private Tunnel[] tunnels;
	private Character character;
	private List<Stars> stars;
	boolean[] keys = new boolean[255];
	private Game parentFrame;
	private Model model;
	private Music music;
	private PVector characterVector;
	private PVector starVector;
	private PVector tunnelVector;
	private TopBarDelegate topBarDelegate;
	private int score=0;
	public boolean isDisplayTunnel=false;
	/**
	 * Constructor of a game scene.
	 * 
	 * @param  parentFrame | the JFrame that owns this game scene
	 * @throws IOException 
	 */
	public GameScene(Game parentFrame,Model model) throws IOException{
		this.parentFrame = parentFrame;
		this.backgroundImg = loadImage(this.getClass().getResource(model.getBackground(0)).getPath());
		this.characters=parentFrame.getCharacterImages();
		this.model=model;
		this.starImg = loadImage(this.getClass().getResource("/res/starGold.png").getPath());
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
		if(time++==1000){
			time=0;
			//���ƨS�F�� !!
			if(score<(level*level)*2){
				parentFrame.gameOver();
			}
			else {
				isDisplayTunnel=true;
			}
		}
		
		background(255, 255, 255);
		image(this.backgroundImg, 0, 50, 500, 700);
		
		/* The game is controlled by pressing SPACE to maintain the height of the plane.*/
		
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
		if(isDisplayTunnel)displayTunnel();
		
		if(isHitStars()){
			this.score++;
		}
		isHitTunnel();
		
		if(level==10){
			parentFrame.gameOver();
		}
	}
	
	public void keyPressed(){
		keys[keyCode] = true;
	}
	public void keyReleased() {
		  keys[keyCode] = false;
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
		this.stars.add( new Stars(this, this.starImg, x+starImg.width,-y*2) );
		
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
						isDisplayTunnel=false;
						for(int i=0;i<tunnelNum;i++)tunnels[i].setY(-1);
						nextLevel();
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
		this.music.musicStop();
		this.music.musicRestart();;
		System.out.println("next");
		newBackground();
	}
	public int getChosenCharater(){
		return this.chosenCharacter;
	}
	private void setHitNumber(int x){
		System.out.printf("tunnel %d\n",x);
	}
	/**
	 * Keep removing the passed rock.
	 */
	
}
