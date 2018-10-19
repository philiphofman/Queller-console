package br.wotr.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class FileUtil {
	private static BufferedWriter bw;

//	public FileUtil() {
//		
//	}

	public static String getContent(String filename) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			InputStream is = FileUtil.class.getResourceAsStream("/" + filename + ".txt");
			String line;
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
			try {
				bw = Files.newBufferedWriter(FileUtil.getOutputPath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			toLog(stackTraceString);
			closeLogfile();
			System.exit(1);
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

	public static String getCurrentFolder() {
		return System.getProperty("user.dir");
	}

	public static Path getOutputPath() {
		return Paths.get(getCurrentFolder() + "\\log_" + getTimestamp() + ".txt");
	}

	public static String getTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH.mm");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}

	public static void toLog(String s) {
		try {
			bw.write(s);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void closeLogfile() {
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
