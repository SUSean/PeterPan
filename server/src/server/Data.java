package server;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.writer.CSVWriter;
import com.googlecode.jcsv.writer.internal.CSVWriterBuilder;

public class Data {
	private List<String[]> data;
	public HashMap<String,User> users=new HashMap<String,User>();
	public int[] rank;
	private CSVReader<String[]> csvParser;
	private CSVWriter<String[]> csvWriter;

	private Reader reader;
	private Writer writer;
	public Data() throws IOException
	{	
		reader = new FileReader("UserData.csv");
		csvParser = CSVReaderBuilder.newDefaultReader(reader);
		data = csvParser.readAll();
		for(String[] string:data){
			User temp= new User(string[0],string[1]);
			users.put(string[0],temp);
			temp.addCoin(string[2]);
			temp.addHighScore(string[3]);
			for(int i=0;i<10;i++){
				temp.addCharacter(i,string[i+4]);
			}
			temp.addList();
		}	
	}
	public String[] getTopTen(){
		ValueComparator bvc =  new ValueComparator(users);
		TreeMap<String,User> sorted = new TreeMap<String,User>(bvc);
		sorted.putAll(users);
		String [] topTen = new String [10];
		int i=0;
		for(String name : sorted.keySet()){
			topTen[i]=name + " : " + users.get(name).score;
			i++;
			if(i==10)
				break;
		}
		return topTen;
	}
	class ValueComparator implements Comparator<String> {

	    Map<String, User> base;
	    public ValueComparator(Map<String, User> base) {
	        this.base = base;
	    }
		@Override
		public int compare(String s1, String s2) {
			if(Integer.parseInt(base.get(s1).score)>=Integer.parseInt(base.get(s2).score))
				return -1;
		    else 
		    	return 1;
		}
	}
	public void dataWrite() throws IOException{
		writer = new FileWriter("UserData.csv");
		csvWriter =CSVWriterBuilder.newDefaultWriter(writer);
		List<String[]> data = new ArrayList<String[]>();
		for(String name : users.keySet()){
			data.add(new String[] {name,users.get(name).password,users.get(name).coin, 
									users.get(name).score,users.get(name).haveCharacter[0],
									users.get(name).haveCharacter[1],users.get(name).haveCharacter[2],
									users.get(name).haveCharacter[3],users.get(name).haveCharacter[4],
									users.get(name).haveCharacter[5],users.get(name).haveCharacter[6],
									users.get(name).haveCharacter[7],users.get(name).haveCharacter[8],
									users.get(name).haveCharacter[9]});
		}


		csvWriter.writeAll(data);
		csvWriter.close();
		writer.close();
	}
}
