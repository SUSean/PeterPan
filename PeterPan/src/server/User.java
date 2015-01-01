package server;

public class User {
	public String coin ;
	public String score;
	public String name ;
	public String password ;
	public String[] haveCharacter=new String[10];
	public User(String name,String password) {
		this.name=name;
		this.password=password;
	}
	public void addCoin(String coin) {
		this.coin=coin;
	}
	public void addHighScore(String score) {
		this.score=score;
	}
	public void addCharacter(int n,String haveFlag){
		this.haveCharacter[n]=haveFlag;
	}

}
