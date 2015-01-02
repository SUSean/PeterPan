package server;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.writer.CSVWriter;
import com.googlecode.jcsv.writer.internal.CSVWriterBuilder;

public class User {
	public String coin ;
	public String score;
	public String name ;
	public String password ;
	public String[] haveCharacter=new String[10];
	private CSVReader<String[]> csvParser;
	private CSVWriter<String[]> csvWriter;
	private Reader reader;
	private Writer writer;
	private List<String[]> data;
	public HashMap<String,ArrayList<String>> lists=new HashMap<String,ArrayList<String>>();
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
	public void addList() throws IOException {
		String filePath = "List/"+name+".csv";
		filePath = filePath.toString();
		reader = new FileReader(filePath);
		csvParser = CSVReaderBuilder.newDefaultReader(reader);
		data = csvParser.readAll();
		for(String[] string:data){
			ArrayList<String> temp= new ArrayList<String>();
			lists.put(string[0],temp);
			for(int i=1;i<string.length;i++){
				temp.add(string[i]);
			}
				 
		}	
	}
	public void writeList() throws IOException {
		String filePath = "List/"+name+".csv";
		filePath = filePath.toString();
		writer = new FileWriter(filePath);
		csvWriter =CSVWriterBuilder.newDefaultWriter(writer);
		List<String[]> data = new ArrayList<String[]>();
		for(String list : lists.keySet()){

			String[] songs = new String[lists.get(list).size()+1];
			songs[0]=list;
			for(int i=1;i<=lists.get(list).size();i++)
				songs[i]=(String) lists.get(list).get(i-1);
			data.add(songs);
		}
		
		csvWriter.writeAll(data);
		writer.close();
	}
	public void addSong(String feel, int num) throws IOException {
		if(!lists.containsKey(feel)){
			ArrayList<String> temp= new ArrayList<String>();
			lists.put(feel,temp);
		}
		if(!lists.get(feel).contains("music_"+num))
			lists.get(feel).add("music_"+num);
		writeList();
	}
}
