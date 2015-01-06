package game;

import java.awt.Color;

import javax.swing.JPanel;

public class ScoreBar extends JPanel implements ScoreBarDelegate{
	private static final long serialVersionUID = 1L;
	
	private JPanel Score;
	private int fullScore;//get what score will enter another level
	private int currentScore;
	
	public ScoreBar(){
<<<<<<< HEAD
		this.setBounds(0,635,500,30);
=======
		this.setBounds(0,50,500,8);
>>>>>>> 1f22694a9e88d5e486957906160afeac8d63660d
		this.setBackground(Color.black);
		this.setOpaque(true);
		this.setLayout(null);
		this.setVisible(true);
		this.Score = new JPanel();
		this.Score.setBounds(0, 0, 0, this.getHeight());
		this.Score.setBackground(Color.green);
		Score.setVisible(true);
		this.Score.setOpaque(true);
		
		this.add(Score);
	}
	
<<<<<<< HEAD

=======
>>>>>>> 1f22694a9e88d5e486957906160afeac8d63660d
	//reset the progressBar to 0
	public void setCurrentScore(int score){
		this.currentScore=score;
		this.Score.setBounds(0, 0, (int)(this.getWidth()*this.currentScore/this.fullScore), this.Score.getHeight());
<<<<<<< HEAD
	}
	//adjust the length of the progress bar
	public void plusScore(){
		this.Score.setBounds(0, 0, (int)(this.Score.getWidth()*this.currentScore/this.fullScore), this.Score.getHeight());
=======
>>>>>>> 1f22694a9e88d5e486957906160afeac8d63660d
		if (this.currentScore < this.fullScore/4){
			this.Score.setBackground(Color.red);
		}
		else if (this.currentScore >= this.fullScore/4 && this.currentScore <= this.fullScore*3/4){
			this.Score.setBackground(Color.yellow);
		}
		else if (this.currentScore > this.fullScore*3/4){
			this.Score.setBackground(Color.green);
		}
<<<<<<< HEAD

=======
>>>>>>> 1f22694a9e88d5e486957906160afeac8d63660d
	}
	
	public void setFullScore(int full){
		this.fullScore=full;
	}
	
}
