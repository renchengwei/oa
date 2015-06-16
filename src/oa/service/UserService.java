package oa.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oa.dao.DepartmentDao;
import oa.dao.UserDao;
import oa.pojo.Department;
import oa.pojo.Menu;
import oa.pojo.Role;
import oa.pojo.User;
import oa.util.BeanCopyUtil;
import oa.util.DateUtil;
import oa.util.SessionUtil;
import oa.util.StaticCodeBook;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("userService")
public class UserService {

	@Resource(name="userDao")
	private UserDao userDao;
	
	@Resource(name="departmentDao")
	private DepartmentDao depDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	

	public DepartmentDao getDepDao() {
		return depDao;
	}

	public void setDepDao(DepartmentDao depDao) {
		this.depDao = depDao;
	}

	/**
	 * 根据用户名密码查询用户
	 * @param username
	 * @param password
	 * @return
	 */
	public User getUserByNamePassword(String username, String password) {
		return userDao.getUserByNamePassword(username,password);
	}

	/**
	 * 设置用户角色菜单等信息
	 * @param user
	 */
	public void getUserRoleMenu(User user) {
		Role role = userDao.getRoleByUser(user.getId());
		List<Menu> menulist = userDao.getMenuByRole(role.getId());
		role.setMenulist(menulist);
		user.setRole(role);
		//如果人员是挂在公司下面的，直接将部门作为公司
		if(user.getDep().getType().equals("0") || user.getDep().getType().equals("1")) {
			user.setOrg(user.getDep());
		}else {//如果人员是挂在部门下面，则查询部门所属的公司
			Department org = (Department) userDao.get(Department.class, user.getDep().getPid());
			user.setOrg(org);
		}
	}

	/**
	 * 保存或者更新用户信息
	 * @param map
	 * @return
	 * @throws IOException 
	 */
	public User saveUser(Map<String, String> map) throws IOException {
		User user = new User();
		if(StringUtils.isNotEmpty(map.get("id"))) {
			user = (User) userDao.get(User.class, map.get("id"));
			if(!user.getPassword().equals(map.get("password"))) {
				map.put("password",  DigestUtils.md5Hex(map.get("password")));
			}
		}
		
		BeanCopyUtil.setFieldValue(user, map);

		if(StringUtils.isNotEmpty(user.getId())) {
			userDao.updateObject(user);
		}else {
			user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			user.setEnableSendMsg("0");
			user.setCreatetime(DateUtil.getDateTime(new Date()));
			user.setCreateuser(SessionUtil.getUser().getId());
			user.setIsdel("0");
			user.setMark(0);
			user.setPhoto("defaultphoto.jpg");
			userDao.saveObject(user);
		}
		return user;
	}

	/**
	 * 查询所有角色
	 * @param map
	 * @return
	 */
	public List<Role> getRoles(Map<String, String> map) {
		return userDao.getRoles(map);
	}
	
	public boolean checkUser(String loginName) {
		return userDao.checkUser(loginName);
	}

	/**
	 * 分页查询用户
	 * @param map
	 * @return
	 */
	public Map<String, Object> getUsersByPage(Map<String, String> map) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<User> userlist = new ArrayList<User>();
		int count = userDao.getUsersCount(map);
		if(count > 0) {
			userlist = userDao.getUsersByPage(map);
		}
		data.put(StaticCodeBook.PAGE_TOTAL, count);
		data.put(StaticCodeBook.PAGE_ROWS, userlist);
		return data;
	}

	public User getUserById(String id) {
		return (User) userDao.get(User.class, id);
	}

	/**
	 * 根据ID删除用户
	 * @param id
	 */
	public void deleteUser(String id) {
		User user = (User) userDao.get(User.class, id);
		user.setIsdel("1");
		user.setDeltime(DateUtil.getDateTime(new Date()));
		user.setDeluser(SessionUtil.getUser().getId());
		userDao.saveObject(user);
	}

	public List<Object> getTreeDepUser(Map<String, String> map) {
		List<User> users = userDao.getAllUsers(map);
		List<Department> deps = depDao.getAllDepartments(map);
		List<Object> ret = new ArrayList<Object>();
		ret.addAll(deps);
		for(User u : users) {
			Map<String, String> bean = BeanCopyUtil.getFieldValueMap(u);
			bean.put("type", "3");
			ret.add(bean);
		}
		return ret;
	}

	/**
	 * 修改用户
	 * @param map
	 * @param photofile
	 * @return
	 * @throws IOException
	 */
	public User updateUser(Map<String, String> map, MultipartFile photofile) throws IOException {
		User user = (User) userDao.get(User.class, map.get("id"));
		BeanCopyUtil.setFieldValue(user, map);
		//保存头像图片
		if(photofile != null && photofile.getSize() > 0) {
			String originalFilename = photofile.getOriginalFilename();
			String filename = user.getId() + originalFilename.substring(originalFilename.lastIndexOf("."));
			String filepath = StaticCodeBook.getUserPhotoPath() + filename;
			File file = new File(filepath);
			FileUtils.writeByteArrayToFile(file, photofile.getBytes());
			user.setPhoto(filename);
		}
		userDao.updateObject(user);
		return user;
	}

	/**
	 * 修改密码
	 * @param map
	 */
	public void updatePassword(Map<String, String> map) {
		String password = map.get("password");
		if(StringUtils.isNotEmpty(password)) {
			password = DigestUtils.md5Hex(password);
		}
		String userid = SessionUtil.getUser().getId();
		User user = (User) userDao.get(User.class, userid);
		user.setPassword(password);
		userDao.updateObject(user);
	}

	/**
	 * 禁言/取消禁言用户
	 * @param id
	 * @return
	 */
	public User updateEnableSendMsg(String id) {
		User user = (User) userDao.get(User.class, id);
		if("0".equals(user.getEnableSendMsg())) {
			user.setEnableSendMsg("1");
		}else {
			user.setEnableSendMsg("0");
		}
		userDao.updateObject(user);
		return null;
	}
}
