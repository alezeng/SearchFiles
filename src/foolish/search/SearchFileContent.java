package foolish.search;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class SearchFileContent extends SearchInputStreamReaderContent {

	private String fileName;
	private String regStr;
	
	public SearchFileContent(String fileName,  String regStr) throws FileNotFoundException {
		super(new FileReader(fileName), regStr);
		
		this.fileName = fileName;
		this.regStr = regStr;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRegStr() {
		return regStr;
	}

	public void setRegStr(String regStr) {
		this.regStr = regStr;
	}
	
	
}
