package oa.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import oa.pojo.Dictionary;
import oa.service.DictionaryService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("DictionaryAction.do")
public class DictionaryAction {

	@Resource(name="dictionaryService")
	private DictionaryService service;

	public DictionaryService getService() {
		return service;
	}

	public void setService(DictionaryService service) {
		this.service = service;
	}
	
	/**
	 * 根据类型查询字段信息
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getDictionaryByType")
	@ResponseBody
	public List<Dictionary> getDictionaryByType(HttpServletRequest request,@RequestParam Map<String,String> map) {
		String type = map.get("type");
		return service.getDictionaryByType(type);
	}
}
