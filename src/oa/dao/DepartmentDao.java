package oa.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oa.pojo.Department;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component("departmentDao")
public class DepartmentDao extends BaseDao {

	/**
	 * 查询所有部门
	 * @param map
	 * @return
	 */
	public List<Department> getAllDepartments(Map<String, String> map) {
		String hql = "from Department where isdel='0' order by type,id";
		List<Department> list = this.query(hql, null);
		return list;
	}

	/**
	 * 删除部门，并级联删除所有相关的部门和人员信息
	 * @param id
	 */
	public void deleteDepartment(String id) {
		//删除本部门
		String hql = "update Department set isdel='1' where id=?";
		List<String> params = new ArrayList<String>();
		params.add(id);
		this.updateByQuery(hql, params);
		//删除本部门下得人员
		hql = "update User set isdel='1' where pid=?";
		this.updateByQuery(hql, params);
		
		//删除相关的部门和人员
		hql = "select id from Department where pid=?";
		List<String> idlist = this.query(hql, params);
		deleteDepartmentAndUser(idlist);
	}
	
	/**
	 * 递归方法，用于循环删除部门和人员
	 * @param idlist
	 */
	private void deleteDepartmentAndUser(List<String> idlist) {
		if(idlist != null && idlist.size() > 0) {
			//删除部门
			String hql = "update Department set isdel='1' where id in (:ids)";
			Session session = getSessionFactory().getCurrentSession();
			Query query = session.createQuery(hql);
			query.setParameterList("ids", idlist);
			query.executeUpdate();
			//删除部门下面的人员
			hql = "update User set isdel='1' where pid in (:ids)";
			query = session.createQuery(hql);
			query.setParameterList("ids", idlist);
			query.executeUpdate();
			//查询子部门，如果有继续删除
			hql = "select id from Department where pid in (:ids)";
			query = session.createQuery(hql);
			query.setParameterList("ids", idlist);
			List<String> list = query.list();
			deleteDepartmentAndUser(list);
		}
	}
	
}
