package oa.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oa.pojo.Department;
import oa.pojo.InforDep;
import oa.pojo.Information;
import oa.pojo.User;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component("informationDao")
public class InformationDao extends BaseDao{

	public void deleteInforDep(String id) {
		String hql = "delete InforDep where inforid=?";
		List<String> params = new ArrayList<String>();
		params.add(id);
		this.updateByQuery(hql, params);
	}

	public int getInforCount(Map<String, String> map) {
		String hql = "select count(id) from Information inf where inf.isdel='0' ";
		List<String> params = new ArrayList<String>();
		String ret = setParams(params, map);
		hql += ret;
		int count = this.queryCountByHql(hql, params);
		return count;
	}

	public List<Information> getInforByPage(Map<String, String> map) {
		String hql = "select inf,u from Information inf,User u where inf.isdel='0' and u.id=inf.issueuser ";
		List<String> params = new ArrayList<String>();
		String ret = setParams(params, map);
		hql += ret + " order by inf.issuetime desc,inf.id";
		int pageNo=Integer.parseInt(map.get("page").toString());
		int maxResult=Integer.parseInt(map.get("rows").toString());
		List<Object[]> list = this.queryForPage(hql, params, pageNo, maxResult);
		
		List<Information> inforlist = new ArrayList<Information>();
		
		if(list != null) {
			for(Object[] o : list) {
				Information inf = (Information) o[0];
				User issueuserBean = (User) o[1];
				inf.setIssueuserBean(issueuserBean);
				inforlist.add(inf);
			}
		}
		return inforlist;
	}
	
	private String setParams(List<String> params, Map<String, String> map) {
		String ret = "";
		if(StringUtils.isNotEmpty(map.get("title"))) {
			ret += " and inf.title like ?";
			params.add("%" + map.get("title") + "%");
		}
		return ret;
	}

	public List<InforDep> getInforDep(String id) {
		String hql = "from InforDep where inforid=?";
		List<String> params = new ArrayList<String>();
		params.add(id);
		List<InforDep> list = this.query(hql, params);
		return list;
	}

	public int getShowInforCount(Map<String, String> map,String[] depids) {
		String hql = "select count(id) from Information where id in (select inforid from InforDep where depid in (:depids)) and isdel='0' ";
		
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameterList("depids", depids);
		
		if(StringUtils.isNotEmpty(map.get("title"))) {
			hql += " and title like ?";
			query.setString(0, '%' + map.get("title") + "%");
		}
		
		Object obj = query.uniqueResult(); 
		if(obj != null) {
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}

	public List<Information> getShowInforByPage(Map<String, String> map,String[] depids) {
		
		String hql = "from Information where id in (select inforid from InforDep where depid in (:depids)) and isdel='0'  order by issuetime desc,id";
		int pageNo=Integer.parseInt(map.get("page").toString());
		int maxResult=Integer.parseInt(map.get("rows").toString());
		
		Session session = this.getSessionFactory().getCurrentSession();
		
		Query query = session.createQuery(hql);
		query.setParameterList("depids", depids);
		
		query.setFirstResult((pageNo - 1) * maxResult);
		query.setMaxResults(maxResult);
		List<Information> list = query.list();
		return list;
	}

	public Department getCorporation() {
		String hql = "from Department where isdel='0' and type='0'";
		List<Department> list = this.query(hql, null);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
