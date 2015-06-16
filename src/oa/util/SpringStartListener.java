package oa.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

@Repository
public class SpringStartListener implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		StaticCodeBook.init();
	}

}
