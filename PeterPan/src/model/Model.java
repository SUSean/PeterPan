package model;


import java.io.IOException;
import java.util.ArrayList;




public class Model {
	
	private String fileName;
	public static final int backgroundNum = 5;
	public static final int musicNum = 10;
	public static final int characterNum = 10;
	private ArrayList<String> backgroundList;
	private ArrayList<String> characterList;

	private String image;
	
	public Model() throws IOException{
		this.characterList = new ArrayList<String>();
		this.backgroundList = new ArrayList<String>();
		fileName = "/res/Charactor/character_";
		for(int i=1; i<=this.characterNum; i++){
			this.image = fileName+i+".png";
			this.characterList.add(image);
		}
		fileName = "/res/Background/background_";
		for(int i = 1; i<=this.backgroundNum; i++){
			this.image = fileName+i+".jpg";
			this.backgroundList.add(image);
		}

	}
	public int getCharatorNum(){
		return this.characterNum;
	}
	public String getBackground(int level){
		
		String temp = backgroundList.get(level%5);
		return temp;
	}
	public String getCharacterImg(int num){
		String temp = characterList.get(num);
		return temp;
	}
	
}
