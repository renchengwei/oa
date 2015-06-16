package oa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oa.dao.PostDao;
import oa.pojo.Post;
import oa.pojo.PostAndReply;
import oa.pojo.PostReply;
import oa.util.BeanCopyUtil;
import oa.util.DateUtil;
import oa.util.SessionUtil;
import oa.util.StaticCodeBook;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component("postService")
public class PostService {

	@Resource(name="postDao")
	private PostDao postDao;

	public PostDao getPostDao() {
		return postDao;
	}

	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}

//	public PostFile saveUploadFile(MultipartFile file) throws IOException {
//		byte[] bytes = file.getBytes();  
//        String path = StaticCodeBook.getPostFilePath();
//        
//        File uploadedFile = new File(path + DateUtil.getDateString4(new Date()));  
//        PostFile postFile = new PostFile();
//        postFile.setFilename(file.getOriginalFilename());
//        postFile.setFilesize(file.getSize() / 1.0);
//        postFile.setFilepath(uploadedFile.getPath());
//		postDao.saveObject(postFile);
//		FileCopyUtils.copy(bytes, uploadedFile);
//		return postFile;
//	}

	/**
	 * 保存帖子
	 * @param map
	 * @return
	 */
	public Post savePost(Map<String,String> map) {
		Post post = new Post();
		if(StringUtils.isNotEmpty(map.get("id"))) {
			post = (Post) postDao.get(Post.class, map.get("id"));
		}
		
		BeanCopyUtil.setFieldValue(post, map);
		
		if(StringUtils.isNotEmpty(post.getId())) {
			postDao.updateObject(post);
		}else {
			post.setIsshield("0");
			post.setIsdel("0");
			post.setIssueuser(SessionUtil.getUser().getId());
			post.setIssuetime(DateUtil.getDateTime(new Date()));
			postDao.saveObject(post);
		}
		return post;
	}

	/**
	 * 分页查询帖子
	 * @param map
	 * @return
	 */
	public Map<String, Object> getPostByPage(Map<String, String> map) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<Post> newslist = new ArrayList<Post>();
		int count = postDao.getPostCount(map);
		if(count > 0) {
			newslist = postDao.getPostByPage(map);
		}
		data.put(StaticCodeBook.PAGE_TOTAL, count);
		data.put(StaticCodeBook.PAGE_ROWS, newslist);
		return data;
	}

	/**
	 * 分页查询帖子回复
	 * @param map
	 * @return
	 */
	public Map<String, Object> getPostAndReplyByPage(Map<String, String> map) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<PostAndReply> newslist = new ArrayList<PostAndReply>();
		int count = postDao.getPostAndReplyCount(map);
		if(count > 0) {
			newslist = postDao.getPostAndReplyByPage(map);
		}
		data.put(StaticCodeBook.PAGE_TOTAL, count);
		data.put(StaticCodeBook.PAGE_ROWS, newslist);
		return data;
	}

	public Post getPostById(String id) {
		Post post = (Post) postDao.get(Post.class, id);
		return post;
	}

	/**
	 * 保存帖子回复
	 * @param map
	 * @return
	 */
	public PostReply savePostReply(Map<String, String> map) {
		PostReply postreply = new PostReply();
		if(StringUtils.isNotEmpty(map.get("id"))) {
			postreply = (PostReply) postDao.get(PostReply.class, map.get("id"));
		}
		
		BeanCopyUtil.setFieldValue(postreply, map);
		
		if(StringUtils.isNotEmpty(postreply.getId())) {
			postDao.updateObject(postreply);
		}else {
			int ordernum = postDao.getPostReplyOrderNum(postreply.getPostid());
			postreply.setIsshield("0");
			postreply.setIsdel("0");
			postreply.setOrdernum(++ordernum);
			postreply.setReplyuser(SessionUtil.getUser().getId());
			postreply.setReplytime(DateUtil.getDateTime(new Date()));
			postDao.saveObject(postreply);
		}
		return postreply;
	}

	/**
	 * 置顶帖子
	 * @param id
	 */
	public void updateToptime(String id) {
		Post post = (Post) postDao.get(Post.class, id);
		post.setToptime(DateUtil.getDateTime(new Date()));
		postDao.updateObject(post);
	}

	/**
	 * 屏蔽/取消屏蔽帖子或者回复
	 * @param id
	 */
	public void updateIsshield(String id) {
		Object obj1 = postDao.get(PostReply.class, id);
		Object obj2 = postDao.get(Post.class, id);
		
		if(obj1 != null) {
			PostReply reply = (PostReply) obj1;
			if("0".equals(reply.getIsshield())) {
				reply.setIsshield("1");
			}else {
				reply.setIsshield("0");
			}
			postDao.updateObject(reply);
		}else {
			Post post = (Post) obj2;
			if("0".equals(post.getIsshield())) {
				post.setIsshield("1");
			}else {
				post.setIsshield("0");
			}
			postDao.updateObject(post);
		}
	}

	public void deletePost(String id) {
		Post post = (Post) postDao.get(Post.class, id);
		post.setIsdel("1");
		post.setDeltime(DateUtil.getDateString5(new Date()));
		post.setDeluser(SessionUtil.getUser().getId());
		postDao.updateObject(post);
	}
}
