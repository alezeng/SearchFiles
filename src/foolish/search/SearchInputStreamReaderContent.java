package foolish.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchInputStreamReaderContent {
	private InputStreamReader srcIn;
	private String regStr;

	public SearchInputStreamReaderContent(InputStreamReader srcIn, String regStr) {
		this.srcIn = srcIn;
		this.regStr = regStr;
	}

	public List<String> Search() throws IOException {
		List<String> urlName = new ArrayList<String>();
		BufferedReader bufIn = new BufferedReader(this.srcIn);

		Pattern p = Pattern.compile(regStr);

		String line = null;
		while ((line = bufIn.readLine()) != null) {
			Matcher m = p.matcher(line);
			
//			System.out.println(line);
			while (m.find()) {
				StringBuilder sb = new StringBuilder();
				for(int j=1;j<=m.groupCount();j++){
					if(j>1)
						sb.append(":");
					sb.append(m.group(j));
				}
				if(sb.length() == 0){
					sb.append(m.group());
				}
				urlName.add(sb.toString());
			}
		}

		return urlName;

	}

}
