package oa.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

@Entity
@Table(name = "tb_department")
@Scope(value = "prototype")
public class Department implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	@Id
    @GeneratedValue(generator="hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	private String id;
	
	/**
	 * 父id
	 */
	private String pid;
	/**
	 * 分公司/部门名称
	 */
	private String name;
	/**
	 * 类型
	 * 0：公司
	 * 1：分公司
	 * 2：部门
	 */
	private String type;
	/**
	 * 备注
	 */
	private String notes;
	/**
	 * 创建时间
	 */
	@Column(updatable=false)
	private String createtime;
	/**
	 * 创建人
	 */
	@Column(updatable=false)
	private String createuser;
	/**
	 * 是否已删除
	 * 0：否
	 * 1：是
	 */
	private String isdel;
	/**
	 * 删除时间
	 */
	private String deltime;
	/**
	 * 删除人
	 */
	private String deluser;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	public String getDeltime() {
		return deltime;
	}
	public void setDeltime(String deltime) {
		this.deltime = deltime;
	}
	public String getDeluser() {
		return deluser;
	}
	public void setDeluser(String deluser) {
		this.deluser = deluser;
	}
	
}
