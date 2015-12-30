	
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
  
  private static ArrayList<String> getCveLogs() throws FileNotFoundException{
	  ArrayList<String> cveLog = new ArrayList<String>();
	  
	try {
		FileReader file = new FileReader("cvelog.txt");
		BufferedReader reader = new BufferedReader(file);	
		String linha = reader.readLine();	
	
		while(linha != null){
			cveLog.add(linha);
			linha = reader.readLine();
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}  
	  return cveLog;
  } 	
  
  private static ArrayList<String> getLinks() throws FileNotFoundException{
	  ArrayList<String> links = new ArrayList<String>();
	  
	  try {
			FileReader file = new FileReader("link.txt");
			BufferedReader reader = new BufferedReader(file);	
			String linha = reader.readLine();	
		
			while(linha != null){
				links.add(linha);
				linha = reader.readLine();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	  
	  return links;
  }
  
 public static void main(String[] args) throws FileNotFoundException {
	  System.setProperty("jsse.enableSNIExtension", "false");
	  ArrayList<String> cvelogs = getCveLogs();
	  ArrayList<String> links = getLinks();
	  Document doc = null;
	  
	  for(int i = 0;i<cvelogs.size();i++){  
	    try {
	      ModifiedStructure structure = new ModifiedStructure();
	      Collector collector = new Collector();
	      
	      String cvename = cvelogs.get(i);	
	      String urldiff = links.get(i);
	      
	      structure.setCveid(cvename);
	      structure.setLinkDiff(urldiff);
	      structure.setVunerability(collector.getTypeVulnFromCVE(cvename));
	      structure.setRepository(collector.getRepository(urldiff));
	   
	      System.out.println("Connecting to "+ urldiff);
	      doc = Jsoup.connect(urldiff).timeout(100000).get();
	      Elements filepath = doc.select("h1");
	      
	      if (!filepath.isEmpty()) {
		      for (Element content: filepath) {
		    	  if(content.text().contains("trunk") && content.text().contains("apr")){
		    		  //System.out.println("1");
		    		  structure.setFilePath(content.text().substring(23,content.text().length())); 
		 	          System.out.println(content.text().substring(23,content.text().length()));
		    	  }else if(content.text().contains("branch") && content.text().contains("http")){
		    		  //System.out.println("2");
		    		  structure.setFilePath(content.text().substring(36,content.text().length())); 
		 	          System.out.println(content.text().substring(36,content.text().length()));
		    	  }else if(content.text().contains("branch") && content.text().contains("apr")){
		    		  //System.out.println("3");
		    		  structure.setFilePath(content.text().substring(32,content.text().length())); 
		 	          System.out.println(content.text().substring(32,content.text().length()));
		    	  }else if(content.text().contains("trunk") && content.text().contains("http")){
		    		  //System.out.println("4");
		    		  structure.setFilePath(content.text().substring(27,content.text().length())); 
		 	          System.out.println(content.text().substring(27,content.text().length()));  
		    	  }else if(content.text().contains("trunk") && content.text().contains("subversion")){
		    		  //System.out.println("5");
		    		  structure.setFilePath(content.text().substring(26,content.text().length())); 
		 	          System.out.println(content.text().substring(26,content.text().length()));  
		    	  }else if(content.text().contains("tags")){
		    		  //System.out.println("6");
		    		  structure.setFilePath(content.text().substring(26,content.text().length())); 
		    		  System.out.println(content.text().substring(26,content.text().length()));
		    	  }else{
		    		  structure.setFilePath(""); 
		    	  }
		      }
	      }else{
	    	  structure.setFilePath("");
	      }
	      
	      Elements revisions = doc.getElementsByClass("vc_diff_add");
	      if (!revisions.isEmpty()) {
		      for (Element content: revisions) {
		    	  if(content.text().contains("Added in v.")){
		    		  structure.setRevision(content.text().substring(11, content.text().length()));
		    		  System.out.println(content.text().substring(11, content.text().length()));
		    	  }
		      }
	      }else{
	    	  structure.setRevision("Repository unidentified");
	      }
	      
	      Elements structures = doc.getElementsByClass("vc_diff_chunk_extra");
		  
	      if (!structures.isEmpty()) {
	    	  String lastContent = " ";
	    	  for (Element content: structures) {
		    	  structure.setStructure(content.text());
		    	  if((!(content.text().equals(lastContent))) && (!(content.text().equals("")))){
		    		  Database.getInstace().persistStructure(structure);
		    		  System.out.println(content.text());
		    		  lastContent = content.text();
		      	  }else{
		      		System.out.println("Ignored: " +content.text());	
		      		lastContent = content.text();
		      	  }
		      }
		  }else{
			  structure.setStructure("");
			  Database.getInstace().persistStructure(structure);
		  }    
	      
	      structure = null;
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
	  System.out.println("Done!");
  }
}

	