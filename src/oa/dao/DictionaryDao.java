package oa.dao;

import java.util.ArrayList;
import java.util.List;

import oa.pojo.Dictionary;

import org.springframework.stereotype.Component;

@Component("dictionaryDao")
public class DictionaryDao extends BaseDao{

	public List<Dictionary> getDictionaryByType(String type) {
		String hql = "from Dictionary where type=?";
		List<String> params = new ArrayList<String>();
		params.add(type);
		List<Dictionary> list = this.query(hql, params);
		return list;
	}

	
}
