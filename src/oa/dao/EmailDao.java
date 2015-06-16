package oa.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import oa.pojo.EmailFile;
import oa.pojo.ReceiveEmail;
import oa.pojo.SendEmail;
import oa.pojo.User;
import oa.util.DateUtil;
import oa.util.SessionUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component("emailDao")
public class EmailDao extends BaseDao {
	public int updateFile(String[] fileid,String emailid) {
		String hql = "update EmailFile set emailid=? where id in (:ids)";
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setString(0, emailid);
		query.setParameterList("ids", fileid);
		return query.executeUpdate();
	}

	/**
	 * 根据邮件ID查询附件
	 * @param id
	 * @return
	 */
	public List<EmailFile> getEmailFileByEmailId(String emailid) {
		String hql = "from EmailFile where emailid=?";
		List<String> params = new ArrayList<String>();
		params.add(emailid);
		List<EmailFile> list = this.query(hql, params);
		return list;
	}

	public int getSendEmailCount(Map<String, String> map) {
		String hql = "select count(id) from SendEmail where isdel='0'";
		List<String> params = new ArrayList<String>();
		if(StringUtils.isNotEmpty(map.get("senduser"))) {
			hql += " and senduser=?";
			params.add(map.get("senduser"));
		}
		if(StringUtils.isNotEmpty(map.get("status"))) {
			hql += " and status = ?";
			params.add(map.get("status"));
		}
		if(StringUtils.isNotEmpty(map.get("title"))) {
			hql += " and title like ?";
			params.add("%" + map.get("title") + "%");
		}
		return this.queryCountByHql(hql, params);
	}

	public List<SendEmail> getSendEmailList(Map<String, String> map) {
		String hql = "from SendEmail where isdel='0'";
		List<String> params = new ArrayList<String>();
		if(StringUtils.isNotEmpty(map.get("senduser"))) {
			hql += " and senduser=?";
			params.add(map.get("senduser"));
		}
		if(StringUtils.isNotEmpty(map.get("status"))) {
			hql += " and status = ?";
			params.add(map.get("status"));
		}
		if(StringUtils.isNotEmpty(map.get("title"))) {
			hql += " and title like ?";
			params.add("%" + map.get("title") + "%");
		}
		hql += " order by sendtime desc,id";
		
		int pageNo=Integer.parseInt(map.get("page").toString());
		int maxResult=Integer.parseInt(map.get("rows").toString());
		List<SendEmail> list = this.queryForPage(hql, params, pageNo, maxResult);
		return list;
	}

	/**
	 * 删除发件
	 * @param idArr
	 */
	public void deleteSendEmails(String[] idArr) {
		String hql = "update SendEmail set isdel='1',deltime=?,deluser=? where id in (:ids)";
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setString(0, DateUtil.getDateTime(new Date()));
		query.setString(1, SessionUtil.getUser().getId());
		query.setParameterList("ids", idArr);
		query.executeUpdate();
	}

	public void deleteEmailFile(String id) {
		String hql = "delete EmailFile where id=?";
		List<String> params = new ArrayList<String>();
		params.add(id);
		this.updateByQuery(hql, params);
	}

	public int getReceiveEmailCount(Map<String, String> map) {
		String hql = "select count(email.id) from ReceiveEmail email,User u where email.isdel='0' and email.senduser=u.id ";
		List<String> params = new ArrayList<String>();
		if(StringUtils.isNotEmpty(map.get("receiveuser"))) {
			hql += " and email.receiveuser=?";
			params.add(map.get("receiveuser"));
		}
		if(StringUtils.isNotEmpty(map.get("title"))) {
			hql += " and email.title like ?";
			params.add("%" + map.get("title") + "%");
		}
		return this.queryCountByHql(hql, params);
	}

	/**
	 * 分页查询收件
	 * @param map
	 * @return
	 */
	public List<ReceiveEmail> getReceiveEmailList(Map<String, String> map) {
		String hql = "from ReceiveEmail email,User u where email.isdel='0' and email.senduser=u.id";
		List<String> params = new ArrayList<String>();
		if(StringUtils.isNotEmpty(map.get("receiveuser"))) {
			hql += " and email.receiveuser=?";
			params.add(map.get("receiveuser"));
		}
		if(StringUtils.isNotEmpty(map.get("title"))) {
			hql += " and email.title like ?";
			params.add("%" + map.get("title") + "%");
		}
		hql += " order by email.sendtime desc,email.id";
		
		int pageNo=Integer.parseInt(map.get("page").toString());
		int maxResult=Integer.parseInt(map.get("rows").toString());
		List<Object[]> list = this.queryForPage(hql, params, pageNo, maxResult);
		
		List<ReceiveEmail> emaillist = new ArrayList<ReceiveEmail>();
		for(Object[] obj : list) {
			ReceiveEmail email = (ReceiveEmail) obj[0];
			User u = (User) obj[1];
			email.setSenduserbean(u);
			emaillist.add(email);
		}
		return emaillist;
	}

	/**
	 * 删除收件
	 * @param idArr
	 */
	public void deleteReceiveEmails(String[] idArr) {
		String hql = "update ReceiveEmail set isdel='1',deltime=?,deluser=? where id in (:ids)";
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setString(0, DateUtil.getDateTime(new Date()));
		query.setString(1, SessionUtil.getUser().getId());
		query.setParameterList("ids", idArr);
		query.executeUpdate();
	}

	public int getShowEmailCount(String id) {
		String hql = "select count(id) from ReceiveEmail where isdel='0' and isshow='0' and receiveuser=?";
		List<String> params = new ArrayList<String>();
		params.add(id);
		return this.queryCountByHql(hql, params);
	}

	/**
	 * 根据ID查询发件
	 * @param id
	 * @return
	 */
//	public SendEmail getSendEmailById(String id) {
//		String hql = "select email,u from SendEmail email,User u where email.senduser=u.id and email.id=?";
//		List<String> params = new ArrayList<String>();
//		params.add(id);
//		List<Object[]> list = this.query(hql, params);
//		if(list != null && list.size() > 0) {
//			Object[] obj = list.get(0);
//			SendEmail email = (SendEmail) obj[0];
//			User user = (User) obj[1];
//			email.setSenduserbean(user);
//			return email;
//		}
//		return null;
//	}
}
