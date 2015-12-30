import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Collector {
	
	public String getTypeVulnFromCVE(String id_cve) throws IOException{
		
	       Document doc = Jsoup.connect("http://www.cvedetails.com/cve/" + id_cve + "/").get();
	       Elements x1 = doc.getElementsByTag("tr");
	       String output = "";
	       if (x1.toString().contains("Vulnerability Type")) {
	           String elementString = x1.toString().split("\\<th\\>Vulnerability Type\\(s\\)\\<\\/th\\>")[1].split("CWE ID")[0];
	           elementString = elementString.replace("\n", "").replace(" ", "");
	           Pattern pattern = Pattern.compile(">[A-Za-z]+<");
	           Matcher matcher = pattern.matcher(elementString);
	           while (matcher.find()) {
	               output += matcher.group();
	           }
	           return output;
	       }
	       return "";
	   }
	
	public String getRepository(String url) throws IOException{
		
		if(url.contains("build/")){
			String[] parts = url.split("build/");
			return parts[0];
		}else if(url.contains("docs/")){
			String[] parts = url.split("docs/");
			return parts[0];
		}else if(url.contains("include/")){
			String[] parts = url.split("include/");
			return parts[0];
		}else if(url.contains("modules/")){
			String[] parts = url.split("modules/");
			return parts[0];
		}else if(url.contains("os/")){
			String[] parts = url.split("os/");
			return parts[0];
		}else if(url.contains("server/")){
			String[] parts = url.split("server/");
			return parts[0];
		}else if(url.contains("srclib/")){
			String[] parts = url.split("srclib/");
			return parts[0];
		}else if(url.contains("support/")){
			String[] parts = url.split("support/");
			return parts[0];
		}else if(url.contains("test/")){
			String[] parts = url.split("test/");
			return parts[0];
		}else if(url.contains("trunk/")){
			String[] parts = url.split("trunk/");
			return parts[0];
		}else if(url.contains("1.4.x/")){
			String[] parts = url.split("1.4.x/");
			return parts[0];
		}
	
	    return "";
	}
}
