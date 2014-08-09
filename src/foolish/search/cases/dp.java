package foolish.search.cases;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import foolish.search.SearchFileContent;
import foolish.search.SearchUrlContent;
import foolish.search.WebFile;

public class dp {

	public static void main(String[] args) throws Exception {

		if(args.length < 1){
			System.out.println("Usage : please input htmlFileName");
			System.exit(0);
		}
				
		String workDir = System.getProperty("user.dir");
		String fileSeparator = System.getProperty("file.separator");
//		String urlReg = "href=\"(http://www.abc.com/[^\"]{30,}.html)\"";
		String urlReg = "href=\'(/[^>]{30,}.html)\'";
		String fileName = workDir + fileSeparator + args[0];
		System.out.println("Input fileName :" + fileName);
		
		String saveFileStart = ""; 
		String savePath = workDir;
		
		// get domain
		String domainReg = "www..*.com";
		SearchFileContent sd = new SearchFileContent(fileName, domainReg);
		List<String> domainList = sd.Search();
		String domain = domainList.get(0).replace("www.", "").replace(".com", "");
		
		
		// get img mother url and save it to arraylist
		List<String> htmlList = new ArrayList<>();
		SearchFileContent sf = new SearchFileContent(fileName, urlReg);

		System.out.println("Start get img mother url ");
		List<String> urlNames = sf.Search();
		for (String urlName : urlNames) {
			urlName = "http://www."+ domain +".com" + urlName;
			htmlList.add(urlName);
//			System.out.println(urlName);
			System.out.print(".");
		}
		System.out.println("\nget img mother url number : " + htmlList.size());

		saveFileStart = urlNames.get(0);
		saveFileStart = saveFileStart.substring(saveFileStart.indexOf("No."), saveFileStart.indexOf(".html"));
		savePath = savePath + fileSeparator + saveFileStart;
		System.out.println("Save Path :" + savePath);

		//get img url
		System.out.println("Start get img url ");
		List<String> imgList = new ArrayList<>();
		String imgReg = "img src='(http://original."+ domain +".com/o/[^\\.]+\\.x)\'";
		for(String htmlUrl : htmlList){
			SearchUrlContent su = new SearchUrlContent(htmlUrl, imgReg);
			imgList.addAll(su.Search());
			System.out.print(".");
		}
		System.out.println("\nget img url number : " + imgList.size());		

		//save download list
		String listFileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".txt";
		PrintWriter out = new PrintWriter(listFileName);
		for(String imgUrl : imgList){
			out.println(imgUrl);
		}
		out.close();
		System.out.println("Download URL saved to file " + listFileName);
		
		// download img
		long startTime = System.currentTimeMillis();
		DateFormat dataFormat = DateFormat.getDateTimeInstance();
		System.out.println("\nStart download img " + dataFormat.format(new Date(startTime)));
		int i = 1;
		for(String imgUrl : imgList){
			System.out.println("downloading " + i + " of " + imgList.size() + " : " + imgUrl);
			WebFile wf = new WebFile(imgUrl);
			try {
				wf.download(savePath, saveFileStart + "_" + i + ".jpg");
			} catch (Exception e) {
				System.out.println("Download file failed : " + imgUrl  );
				e.printStackTrace();
			}
			i++;
		}

		long endTime = System.currentTimeMillis();
		System.out.println("End download img " + dataFormat.format(new Date(endTime)));
		System.out.println("Downloaded img number :" + (--i));
		System.out.println("Elapses time : " + (endTime-startTime)/1000 + " seconds");
	}

}
