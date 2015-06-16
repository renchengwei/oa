package oa.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oa.pojo.Department;
import oa.pojo.Menu;
import oa.pojo.Role;
import oa.pojo.User;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component("userDao")
public class UserDao extends BaseDao {

	/**
	 * 根据用户名密码查询用户
	 * @param username
	 * @param password
	 * @return
	 */
	public User getUserByNamePassword(String username, String password) {
		String hql = "select u,d,dict.name from User u,Department d,Dictionary dict where u.duty=dict.id and u.pid=d.id and d.isdel='0' and u.isdel='0' and u.loginname=? and u.password=?";
		List<String> params = new ArrayList<String>();
		params.add(username);
		params.add(password);
		
		List<Object[]> list = this.query(hql, params);
		if(list != null && list.size() > 0) {
			Object[] o = list.get(0);
			User user = (User) o[0];
			Department dep = (Department) o[1];
			String dutyname = (String) o[2];
			user.setDep(dep);
			user.setDutyname(dutyname);
			return user;
		}
		return null;
	}

	/**
	 * 根据用户查询角色
	 * @param userid
	 * @return
	 */
	public Role getRoleByUser(String userid) {
		String hql = "select r from Role r,User u where u.roleid=r.id and u.id=?";
		List<String> params = new ArrayList<String>();
		params.add(userid);
		List<Role> rolelist = this.query(hql, params);
		if(rolelist != null & rolelist.size() > 0) {
			return rolelist.get(0);
		}
		return null;
	}

	/**
	 * 根据角色查询菜单
	 * @param roleid
	 * @return
	 */
	public List<Menu> getMenuByRole(String roleid) {
		String hql = "select m from Menu m,RoleMenu rm where m.id=rm.menuid and rm.roleid=?";
		List<String> params = new ArrayList<String>();
		params.add(roleid);
		List<Menu> menulist = this.query(hql, params);
		return menulist;
	}

	/**
	 * 查询所有角色
	 * @param map
	 * @return
	 */
	public List<Role> getRoles(Map<String, String> map) {
		String hql = "from Role";
		List<Role> list = this.query(hql, null);
		return list;
	}

	/**
	 * 检查用户名是否重复
	 * @param loginName
	 * @return
	 */
	public boolean checkUser(String loginName) {
		String hql = "from User where isdel='0' and loginname=?";
		List<String> params = new ArrayList<String>();
		params.add(loginName);
		List<User> list = this.query(hql, params);
		if(list != null && list.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 查询用户总数
	 * @param map
	 * @return
	 */
	public int getUsersCount(Map<String, String> map) {
		String hql = "select count(id) from User u where u.isdel='0' ";
		List<String> params = new ArrayList<String>();
		String ret = setParams(params,map);
		hql += ret;
		int count = this.queryCountByHql(hql, params);
		return count;
	}

	/**
	 * 分页查询用户
	 * @param map
	 * @return
	 */
	public List<User> getUsersByPage(Map<String, String> map) {
		String hql = "select u,d from User u,Department d where u.isdel='0' and d.isdel='0' and u.pid=d.id";
		List<String> params = new ArrayList<String>();
		String ret = setParams(params,map);
		hql += ret + " order by u.createtime desc,u.id";
		int pageNo=Integer.parseInt(map.get("page").toString());
		int maxResult=Integer.parseInt(map.get("rows").toString());
		List<Object[]> list = this.queryForPage(hql, params, pageNo, maxResult);
		List<User> userlist = new ArrayList<User>();
		if(list != null && list.size() > 0) {
			User u;
			for(Object[] o : list) {
				u = (User) o[0];
				u.setDep(o[1] == null ? null : (Department)o[1]);
				userlist.add(u);
			}
		}
		
		return userlist;
	}
	
	private String setParams(List<String> params,Map<String, String> map) {
		String ret = "";
		if(StringUtils.isNotEmpty(map.get("pid"))) {
			ret += " and u.pid=?";
			params.add(map.get("pid"));
		}
		if(StringUtils.isNotEmpty(map.get("name"))) {
			ret += " and u.name like ?";
			params.add("%" + map.get("name") + "%");
		}
		if(StringUtils.isNotEmpty(map.get("duty"))) {
			ret += " and u.duty=?";
			params.add(map.get("duty"));
		}
		return ret;
	}

	public List<User> getAllUsers(Map<String, String> map) {
		String hql = "from User where isdel='0' order by createtime desc,id";
		List<User> userlist = this.query(hql, null);
		return userlist;
	}

}
