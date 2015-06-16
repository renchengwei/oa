package oa.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	protected FilterConfig fc = null;

	public void destroy() {
	}

	String nocheckUrl = "";

	String notCheckMethod = "";

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse repsonse = (HttpServletResponse) res;
		String url = request.getRequestURL().toString();
		String method = request.getParameter("method");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		//不过滤的地址
		String[] urls = nocheckUrl.split(",");
		for (int i = 0; i < urls.length; i++) {
			String temp = urls[i];
			if (url.indexOf(urls[i]) > 0) {
				chain.doFilter(request, repsonse);
				return;
			}
			if (temp.endsWith("*")
					&& url.indexOf(temp.substring(0, temp.length() - 1)) > 0) {
				chain.doFilter(request, repsonse);
				return;
			}
		}

		String[] methods = notCheckMethod.split(",");
		for (int i = 0; i < methods.length; i++) {
			if (method != null && method.equals(methods[i])) {
				chain.doFilter(request, repsonse);
				return;
			}
		}
		if(!SessionUtil.checkLogin(request)){
			 repsonse.sendRedirect(basePath+StaticCodeBook.LOGIN_PAGE);
			 return;
		}else{
			 chain.doFilter(request, repsonse);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.fc = config;
		nocheckUrl = fc.getInitParameter("notCheckUrl");
		notCheckMethod = fc.getInitParameter("notCheckMethod");
	}
}
