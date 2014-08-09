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
	 * ����·�� ����ͼƬ Ȼ�� ���浽��Ӧ��Ŀ¼��
	 * 
	 * @param urlString
	 * @param fileName
	 * @param savePath
	 * @return
	 * @throws Exception
	 */
	public void download(String savePath, String fileName) throws Exception {
		// ����URL
		URL url = new URL(this.urlString);
		// ������
		URLConnection con = url.openConnection();
		// ���������·��
		con.setConnectTimeout(5 * 1000);
		// ������
		InputStream is = con.getInputStream();

		// ���ݻ���
		byte[] bs = new byte[102400];
		// 1M
		// byte[] bs = new byte[1048576];
		// ��ȡ�������ݳ���
		int len;
		// ������ļ���
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}

		long bytesWritten = 0;
		long startTime = System.nanoTime();
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + fileName);
		BufferedOutputStream bufout = new BufferedOutputStream(os);
		// ��ʼ��ȡ
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

		// ��ϣ��ر���������
		bufout.close();

		is.close();
	}

	/**
	 * �����ļ��� ��ȡ�ļ��ĺ�׺��
	 * 
	 * @param fileUrl
	 * @return
	 */
	public String getExtension(String fileUrl) {
		return fileUrl.substring(fileUrl.lastIndexOf("."), fileUrl.length());
	}
}
