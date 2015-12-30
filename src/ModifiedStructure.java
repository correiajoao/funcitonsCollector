import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="modified_structure")
public class ModifiedStructure {
	@Id
	@GeneratedValue
	int cod;
	String cveId;
	Calendar data = Calendar.getInstance();
	String vunerability;
	String repository;
	String linkDiff;
	String filePath;
	String structure;
	String revision;
	
	public ModifiedStructure() {
		// TODO Auto-generated constructor stub
	}	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getCveid() {
		return cveId;
	}
	public void setCveid(String cveid) {
		this.cveId = cveid;
	}
	public String getVunerability() {
		return vunerability;
	}
	public void setVunerability(String vunerability) {
		this.vunerability = vunerability;
	}
	public String getRepository() {
		return repository;
	}
	public void setRepository(String repository) {
		this.repository = repository;
	}
	public String getLinkDiff() {
		return linkDiff;
	}
	public void setLinkDiff(String linkDiff) {
		this.linkDiff = linkDiff;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	
	
}
