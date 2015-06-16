package oa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class StaticCodeBook {
	
	private static final Logger log = Logger.getLogger(StaticCodeBook.class);
	public static Properties properties = new Properties();
	
	//分页使用的常量
	public static String PAGE_TOTAL="total";
	public static String PAGE_ROWS="rows";
	public static String PAGE_NO="page";
	
	public static String SESSION_USER = "session_user";
	public static String SESSION_LOGIN_IP = "session_login_ip";
	public static String SESSION_ROLE = "session_role";
	public static String SESSION_MENUS = "session_menus";
	public static String userphotopath;
	
	//jsp页面
	public static String  LOGIN_PAGE="login.jsp";

	//初始化数据
	public static void init() {
		StringBuilder configpath = new StringBuilder(System.getProperty("oa.root"));
		userphotopath = System.getProperty("oa.root") + "userphoto" + File.separatorChar;
		configpath.append("WEB-INF").append(File.separatorChar).append("config").append(File.separatorChar).append("config.properties");
		InputStream in;
		try {
			in = new FileInputStream(configpath.toString());
			properties.load(in);
		    in.close();
		} catch (FileNotFoundException e) {
			log.error("",e);
		} catch (IOException e) {
			log.error("",e);
		}
	}
	
	public static String getUserPhotoPath() {
		return userphotopath;
	}
	public static String getEmailFilePath() {
		return properties.getProperty("emailfilepath");
	}
//	public static String getPostFilePath() {
//		return properties.getProperty("postfilepath");
//	}
}
