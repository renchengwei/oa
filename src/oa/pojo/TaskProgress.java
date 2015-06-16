package oa.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

@Entity
@Table(name="tb_taskpprogress")
@Scope(value = "prototype")
public class TaskProgress {

	@Id
	@GeneratedValue(generator="hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	private String id;
	
	private String taskid;
	
	private String content;
	
	private String writetime;
	
	private String writeuser;
	@Transient
	private String writeusername;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWritetime() {
		if(StringUtils.isNotEmpty(writetime)) {
			return writetime.substring(0, 10);
		}
		return writetime;
	}

	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}

	public String getWriteuser() {
		return writeuser;
	}

	public void setWriteuser(String writeuser) {
		this.writeuser = writeuser;
	}

	public String getWriteusername() {
		return writeusername;
	}

	public void setWriteusername(String writeusername) {
		this.writeusername = writeusername;
	}
	
}
