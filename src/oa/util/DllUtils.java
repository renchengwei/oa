package oa.util;


import java.io.File;

import org.apache.log4j.Logger;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class DllUtils {
	
	private static final Logger logger = Logger.getLogger(DllUtils.class);
	
	private static JNADll instanceDll;

	//加载库函数
	static {
		StringBuilder dllpath = new StringBuilder(System.getProperty("VPNM.root"));
		dllpath.append("WEB-INF").append(File.separatorChar).append("config").append(File.separatorChar).append("dll").append(File.separatorChar);
		try {
		//	-lpthread -lsqlapi -lreadline -lncurses -lxml2 -lcrypto -lssl -lrt 
			
			
//			Native.loadLibrary("pthread",JNADll.class);
//			Native.loadLibrary("rt",JNADll.class);
//			Native.loadLibrary("sqlapi",JNADll.class);
//			Native.loadLibrary("ncurses",JNADll.class);
//			Native.loadLibrary("readline",JNADll.class);
//			Native.loadLibrary("xml2",JNADll.class);
//			Native.loadLibrary("/usr/lib/libcrypto",JNADll.class);
//			Native.loadLibrary("/usr/lib/libssl",JNADll.class);
			instanceDll  = (JNADll)Native.loadLibrary("vpnmgr",JNADll.class);
		}catch(Throwable t) {
			logger.error("加载库文件出错", t);
			throw new RuntimeException(t);
		}
	}


	public interface JNADll extends Library {    
	 
	    /**
	     * 禁用syslog
	     * @param host	主机地址
	     * @param port	主机端口
	     * @param password	密码
	     * @return
	     */
	    public int VMDisableSyslog(String host, int port,String password);
	    
	}  
}
