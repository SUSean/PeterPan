package game;

import java.io.IOException;

import model.Model;
import processing.core.PApplet;
import processing.core.PImage;
import server.Client;

public class ChooseCharacter extends PApplet{
	PImage background;
	public PImage[] characters;
	private boolean[] boughtCharacter;
	private int nowWhichCharacterChosed = 1;
	private int characterNum;
	private Game game;
	private Client client;
	
	public ChooseCharacter(Game game,Client client, Model model){
		//load background
		this.client=client;
		background = new PImage();
		this.background = loadImage(this.getClass().getResource("/res/Shop/grass.jpg").getPath());
		
		this.game=game;
		//load characters' image & go_back_sign image
		this.characterNum=model.getCharatorNum();
		characters = new PImage[characterNum+2];
		for(int i=0;i<this.characterNum;i++){
			this.characters[i+1]=loadImage(this.getClass().getResource(model.getCharacterImg(i)).getPath());
		}
		this.characters[11] = loadImage(this.getClass().getResource("/res/Shop/go_back_sign_2.png").getPath());
		
		//identify which characters are bought
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
		//set background
		image(this.background, -500, 0);
		
		//boughtCharacter[11] must be set true because it is the "go_back_sign"
		boughtCharacter[11] = true;
		
		if(wantToGoBack() && keyPressed && key==ENTER){
			game.chooseCharacterGotoGameStart();
		}
		//listen key to change character selected
		/*else if (keyPressed && keyCode == UP){
			if (nowWhichCharacterChosed > 0 && nowWhichCharacterChosed < 12){
				nowWhichCharacterChosed--;
				
			//prevent less than zero situation
			if (nowWhichCharacterChosed <= 0)
				nowWhichCharacterChosed++;
				
			//if the character was bought, then change to the previous character
			while (boughtCharacter[nowWhichCharacterChosed] == false){
					nowWhichCharacterChosed--;
			}

			//prevent less than 1 situation
			if (nowWhichCharacterChosed < 1)
				nowWhichCharacterChosed++;
			}
		}
		else if (keyPressed && keyCode == DOWN){
			if (nowWhichCharacterChosed > 0 && nowWhichCharacterChosed < 12){
				nowWhichCharacterChosed++;
				
			//prevent more than 11 situation
			if (nowWhichCharacterChosed > 11)
				nowWhichCharacterChosed--;

			//if the character is not bought, then change to the previous character
			while (boughtCharacter[nowWhichCharacterChosed] == false && nowWhichCharacterChosed < 12){
				nowWhichCharacterChosed++;
			}

			//special prevent more than 11 situation, include testing if is bought character
			while (nowWhichCharacterChosed > 11 || boughtCharacter[nowWhichCharacterChosed] == false)
				nowWhichCharacterChosed--;
			}
		}
		else if (keyPressed && keyCode == LEFT){
			if (nowWhichCharacterChosed > 0 && nowWhichCharacterChosed < 12){
				nowWhichCharacterChosed-=4;

			//prevent less than zero situation
			if (nowWhichCharacterChosed <= 0)
				nowWhichCharacterChosed+=4;

			//if the character is not bought, then change to the previous character
			while (boughtCharacter[nowWhichCharacterChosed] == false){
				nowWhichCharacterChosed--;
			}
				
			//prevent less than 1 situation
			if (nowWhichCharacterChosed < 1)
				nowWhichCharacterChosed++;
			}
		}
		else if (keyPressed && keyCode == RIGHT){
			if (nowWhichCharacterChosed > 0 && nowWhichCharacterChosed < 12){
				nowWhichCharacterChosed+=4;
				
			//prevent more then 11 situation
			if (nowWhichCharacterChosed > 11)
				nowWhichCharacterChosed-=4;

			//if the character was not bought, then change to the next character
			while (boughtCharacter[nowWhichCharacterChosed] == false && nowWhichCharacterChosed < 12){
				nowWhichCharacterChosed++;
			}
			
			//prevent more than 11 situation
			if (nowWhichCharacterChosed > 11)
				nowWhichCharacterChosed--;
			}
		}*/
		else if (keyPressed && keyCode == UP){
			int temp = nowWhichCharacterChosed;
			while(true){
				temp--;
				if(temp==0)
					temp=11;
				if(boughtCharacter[temp]==true){
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
				if(boughtCharacter[temp]==true){
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
				if(boughtCharacter[temp]==true){
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
				if(boughtCharacter[temp]==true){
					nowWhichCharacterChosed=temp;
					break;
				}
			}
		}
		else if (!wantToGoBack()&&keyPressed && key == ENTER){
			try {
				game.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		

		//draw characters
		//if the character is bought then show the image
		/*if (boughtCharacter[1] == true){
			tint(120);
			
			//see if the character is now selected and then set as the brightest
			if (nowWhichCharacterChosed == 1)
				tint(255);
			image(characters[1], width/13, height/15, 100, 150);
		}
		tint(0);

		//if the character is bought then show the image
		if (boughtCharacter[2] == true){
			tint(120);
			
			//see if the character is now selected and then set as the brightest
			if (nowWhichCharacterChosed == 2)
				tint(255);
			image(characters[2], width/13, height/15*5-20, 100, 150);
		}
		tint(0);

		//if the character is bought then show the image
		if (boughtCharacter[3] == true){
			tint(120);
			
			//see if the character is now selected and then set as the brightest
			if (nowWhichCharacterChosed == 3)
				tint(255);
			image(characters[3], width/13, height/15*8+20, 100, 150);
		}
		tint(0);

		//if the character is bought then show the image
		if (boughtCharacter[4] == true){
			tint(120);
			
			//see if the character is now selected and then set as the brightest
			if (nowWhichCharacterChosed == 4)
				tint(255);
			image(characters[4], width/13, height/15*12-10, 100, 150);
		}
		tint(0);

		//if the character is bought then show the image
		if (boughtCharacter[5] == true){
			tint(120);
			
			//see if the character is now selected and then set as the brightest
			if (nowWhichCharacterChosed == 5)
				tint(255);
			image(characters[5], width/13*5, height/15, 100, 150);
		}
		tint(0);

		//if the character is bought then show the image
		if (boughtCharacter[6] == true){
			tint(120);
			
			//see if the character is now selected and then set as the brightest
			if (nowWhichCharacterChosed == 6)
				tint(255);
			image(characters[6], width/13*5, height/15*5-20, 100, 150);
		}
		tint(0);

		//if the character is bought then show the image
		if (boughtCharacter[7] == true){
			tint(120);
			
			//see if the character is now selected and then set as the brightest
			if (nowWhichCharacterChosed == 7)
				tint(255);
			image(characters[7], width/13*5, height/15*8+20, 100, 150);
		}
		tint(0);

		//if the character is bought then show the image
		if (boughtCharacter[8] == true){
			tint(120);
			
			//see if the character is now selected and then set as the brightest
			if (nowWhichCharacterChosed == 8)
				tint(255);
			image(characters[8], width/13*5, height/15*12-10, 100, 150);
		}
		tint(0);

		//if the character is bought then show the image
		if (boughtCharacter[9] == true){
			tint(120);
			
			//see if the character is now selected and then set as the brightest
			if (nowWhichCharacterChosed == 9)
				tint(255);
			image(characters[9], width/13*9, height/15, 100, 150);
		}
		tint(0);

		//if the character is bought then show the image
		if (boughtCharacter[10] == true){
			tint(120);
			
			//see if the character is now selected and then set as the brightest
		if (nowWhichCharacterChosed == 10)
			tint(255);
			image(characters[10], width/13*9, height/15*5-20, 100, 150);
		}*/
		for(int i=1;i<11;i++){
			if (boughtCharacter[i] == true){
				tint(120);
				
				//see if the character is now selected and then set as the brightest
				if (nowWhichCharacterChosed == i)
					tint(255);
				image(characters[i], width/13*(1+((i-1)/4)*4), height/15+150*((i%4+3)%4), 100, 150);
			}
			tint(0);
		}
		
		//show the "go_back_sign" image
		if (nowWhichCharacterChosed == 11){
			tint(255);
			image(characters[11], width/13*9, height/15*10, 150, 100);
		}
		else {
			tint(120);
			image(characters[11], width/13*9, height/15*10, 150, 100);
		}
		
		tint(255);
		

		//text "~Your Character~"
		fill(255, 100, 0);
		text("~Your Characters~", 60, 40);
		textSize(40);
	}
	public int getChoseWhichCharacter(){
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
