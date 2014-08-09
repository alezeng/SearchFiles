package foolish.search;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class WebFile {

	private String urlString;

	public WebFile(String urlString) {
		this.urlString = urlString;
	}

	/**
	 * 根据路径 下载图片 然后 保存到对应的目录下
	 * 
	 * @param urlString
	 * @param fileName
	 * @param savePath
	 * @return
	 * @throws Exception
	 */
	public void download(String savePath, String fileName) throws Exception {
		// 构造URL
		URL url = new URL(this.urlString);
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求的路径
		con.setConnectTimeout(5 * 1000);
		// 输入流
		InputStream is = con.getInputStream();

		// 数据缓冲
		byte[] bs = new byte[102400];
		// 1M
		// byte[] bs = new byte[1048576];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}

		long bytesWritten = 0;
		long startTime = System.nanoTime();
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + fileName);
		BufferedOutputStream bufout = new BufferedOutputStream(os);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			bufout.write(bs, 0, len);
			bytesWritten += len;
		}

		double speedInKBps = 0.0D;
		double timeElapsed = (System.nanoTime() - startTime)/1000000000d; 
		if(timeElapsed == 0 ){
			timeElapsed = 1;
		}
		speedInKBps = (bytesWritten / timeElapsed) / 1024D;
		System.out.println("Size : " + String.format("%1$.1f", bytesWritten/1024d/1024d)+ " MB, Time : " + String.format("%1$.1f", timeElapsed) +" s, Speed is : " + String.format("%1$.1f", speedInKBps) + " kb/s");

		// 完毕，关闭所有链接
		bufout.close();

		is.close();
	}

	/**
	 * 根据文件名 获取文件的后缀名
	 * 
	 * @param fileUrl
	 * @return
	 */
	public String getExtension(String fileUrl) {
		return fileUrl.substring(fileUrl.lastIndexOf("."), fileUrl.length());
	}
}
