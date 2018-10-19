package br.wotr.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {
//	private static BufferedWriter bw;

//	public FileUtil() {
//		try {
//			bw = Files.newBufferedWriter(this.getOutputPath());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public static String getContents(String filename) {
		InputStream is = FileUtil.class.getResourceAsStream("/" + filename + ".txt");
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

//	public String getCurrentFolder() {
//		return System.getProperty("user.dir");
//	}
//
//	public Path getOutputPath() {
//		return Paths.get(getCurrentFolder() + "\\log_" + getTimestamp() + ".txt");
//	}
//
//	public String getTimestamp() {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH.mm");
//		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//		return sdf.format(timestamp);
//	}

//	public static void toLog(String s) {
//		try {
//			bw.write(s);
//			bw.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

//	public static void closeBW() {
//		try {
//			bw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
