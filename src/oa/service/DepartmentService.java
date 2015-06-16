package oa.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oa.dao.DepartmentDao;
import oa.pojo.Department;
import oa.util.BeanCopyUtil;
import oa.util.DateUtil;
import oa.util.SessionUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component("departmentService")
public class DepartmentService {

	@Resource(name="departmentDao")
	private DepartmentDao dao;

	public DepartmentDao getDao() {
		return dao;
	}

	public void setDao(DepartmentDao dao) {
		this.dao = dao;
	}

	/**
	 * 保存或者部门
	 * @param map
	 * @return
	 */
	public Department saveDepartment(Map<String, String> map) {
		Department dep = new Department();
		BeanCopyUtil.setFieldValue(dep, map);
		//更新部门信息
		if(StringUtils.isNotEmpty(dep.getId())) {
			dao.updateObject(dep);
		}else {//新增部门信息
			dep.setIsdel("0");
			dep.setCreatetime(DateUtil.getDateTime(new Date()));
			dep.setCreateuser(SessionUtil.getUser().getId());
			dao.saveObject(dep);
		}
		return dep;
	}

	/**
	 * 查询所有部门
	 * @param map
	 * @return
	 */
	public List<Department> getAllDepartments(Map<String, String> map) {
		return dao.getAllDepartments(map);
	}

	/**
	 * 根据ID查询部门
	 * @param id
	 * @return
	 */
	public Department getDepartmentById(String id) {
		return (Department) dao.get(Department.class, id);
	}

	/**
	 * 删除部门
	 * @param id
	 */
	public void deleteDepartment(String id) {
		dao.deleteDepartment(id);
	}
}
