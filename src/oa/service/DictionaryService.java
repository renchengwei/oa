package oa.service;

import java.util.List;

import javax.annotation.Resource;

import oa.dao.DictionaryDao;
import oa.pojo.Dictionary;

import org.springframework.stereotype.Component;

@Component("dictionaryService")
public class DictionaryService {

	@Resource(name="dictionaryDao")
	private DictionaryDao dao;

	public DictionaryDao getDao() {
		return dao;
	}

	public void setDao(DictionaryDao dao) {
		this.dao = dao;
	}

	public List<Dictionary> getDictionaryByType(String type) {
		return dao.getDictionaryByType(type);
	}
	
}
