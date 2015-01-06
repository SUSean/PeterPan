package game;

import java.awt.Color;

import javax.swing.JPanel;

public class ScoreBar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JPanel Score;
	private int fullScore;//get what score will enter another level
	private int currentScore;
	
	public ScoreBar(int fullScore, int currentScore){
		this.setBounds(0,50,500,15);
		this.setBackground(Color.black);
		this.setOpaque(true);
		this.setLayout(null);
		this.Score = new JPanel();
		this.Score.setBounds(0, 0, 0, this.getHeight());
		this.Score.setBackground(Color.green);
		this.Score.setOpaque(true);
		this.fullScore = fullScore;
		this.currentScore = currentScore;
		
		this.add(Score);
	}
	
	//adjust the length of the progress bar
	public void plusScore(){
		this.Score.setBounds(0, 0, (int)(this.Score.getWidth()*this.currentScore/this.fullScore), this.Score.getHeight());
		if (this.currentScore < this.fullScore/4){
			this.Score.setBackground(Color.red);
		}
		else if (this.currentScore >= this.fullScore/2 && this.currentScore <= this.fullScore*3/4){
			this.Score.setBackground(Color.yellow);
		}
		else if (this.currentScore > this.fullScore*3/4){
			this.Score.setBackground(Color.green);
		}
	}
	
	//reset the progressBar to 0
	public void reSet(){
		this.Score.setBounds(0, 0, 0, this.getHeight());
	}
}
