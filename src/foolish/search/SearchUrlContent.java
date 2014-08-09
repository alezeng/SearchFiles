package foolish.search;

import java.io.InputStreamReader;
import java.net.URL;

public class SearchUrlContent extends SearchInputStreamReaderContent {

	public SearchUrlContent(String targetUrl, String regStr) throws Exception {

		super(new InputStreamReader(new URL(targetUrl).openStream()), regStr);
//		URL url = new URL(targetUrl);
		// new InputStreamReader(new url.openStream());
	}
	

	public SearchUrlContent(String targetUrl, String regStr, String charsetName) throws Exception {

		super(new InputStreamReader(new URL(targetUrl).openStream(), charsetName), regStr);

	}
}
