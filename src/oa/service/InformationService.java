package oa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oa.dao.InformationDao;
import oa.pojo.Department;
import oa.pojo.InforDep;
import oa.pojo.Information;
import oa.pojo.User;
import oa.util.BeanCopyUtil;
import oa.util.DateUtil;
import oa.util.SessionUtil;
import oa.util.StaticCodeBook;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component("informationService")
public class InformationService {

	@Resource(name="informationDao")
	private InformationDao dao;

	public InformationDao getDao() {
		return dao;
	}

	public void setDao(InformationDao dao) {
		this.dao = dao;
	}

	/**
	 * 新增或者修改消息
	 * @param map
	 * @return
	 */
	public Information saveInfor(Map<String, String> map,List<String> depids) {
		Information infor = new Information();
		if(StringUtils.isNotEmpty(map.get("id"))) {
			infor = (Information) dao.get(Information.class, map.get("id"));
		}
		BeanCopyUtil.setFieldValue(infor, map);
		//更新
		if(StringUtils.isNotEmpty(infor.getId())) {
			dao.deleteInforDep(infor.getId());
			dao.updateObject(infor);
		}else {//新增
			infor.setIssuetime(DateUtil.getDateTime(new Date()));
			infor.setIssueuser(SessionUtil.getUser().getId());
			infor.setIsdel("0");
			dao.saveObject(infor);
		}
		
		if(depids != null) {
			for(String depid : depids) {
				InforDep inforDep = new InforDep();
				inforDep.setDepid(depid);
				inforDep.setInforid(infor.getId());
				dao.saveObject(inforDep);
			}
		}
		infor.setDepids(depids);
		return infor;
	}

	public void delInfor(String id) {
		Information infor = (Information) dao.get(Information.class, id);
		infor.setIsdel("1");
		infor.setDeltime(DateUtil.getDateTime(new Date()));
		infor.setDeluser(SessionUtil.getUser().getId());
		dao.updateObject(infor);
	}

	public Map<String, Object> getInforByPage(Map<String, String> map) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<Information> inforlist = new ArrayList<Information>();
		int count = dao.getInforCount(map);
		if(count > 0) {
			inforlist = dao.getInforByPage(map);
		}
		data.put(StaticCodeBook.PAGE_TOTAL, count);
		data.put(StaticCodeBook.PAGE_ROWS, inforlist);
		return data;
	}

	public Information getInforById(String id) {
		Information infor = (Information) dao.get(Information.class, id);
		User user = (User) dao.get(User.class, infor.getIssueuser());
		List<InforDep> infordeplist = dao.getInforDep(infor.getId());
		List<String> depids = new ArrayList<String>();
		for(InforDep infordep : infordeplist) {
			depids.add(infordep.getDepid());
		}
		infor.setDepids(depids);
		infor.setIssueuserBean(user);
		return infor;
	}

	public Map<String, Object> getShowInforByPage(Map<String, String> map) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<Information> inforlist = new ArrayList<Information>();
		//自己所属公司
		Department mydep = SessionUtil.getUser().getOrg();
		//查询总公司
		Department corporation = dao.getCorporation();
		String[] depids = new String[]{mydep.getId(),corporation.getId()};
		
		int count = dao.getShowInforCount(map,depids);
		if(count > 0) {
			inforlist = dao.getShowInforByPage(map,depids);
		}
		data.put(StaticCodeBook.PAGE_TOTAL, count);
		data.put(StaticCodeBook.PAGE_ROWS, inforlist);
		return data;
	}
	
}
