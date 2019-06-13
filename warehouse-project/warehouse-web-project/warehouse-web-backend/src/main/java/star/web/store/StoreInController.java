package star.web.store;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import star.bizbase.enums.OnOffDelEnums;
import star.bizbase.exception.BizRuleException;
import star.bizbase.util.BizRuleCheck;
import star.bizbase.util.ConvertUtil;
import star.facade.ecmanager.manager.vo.ManagerVo;
import star.facade.warehouse.StoreInFacade;
import star.facade.warehouse.vo.StoreInVo;
import star.facade.warehouse.vo.request.StoreInReqVo;
import star.fw.web.util.SearchUtil;
import star.fw.web.vo.SearchDataVo;
import star.fw.web.xss.XSSUtil;
import star.share.util.Results;
import star.util.ExceptionUtil;
import star.vo.result.ResultVo;
import star.vo.search.SearchResult;
import star.web.BasicController;

/**
 *
 * @author:chistar
 * @since:2018年11月18日下午12:35:07
 */
@Controller
@RequestMapping("/store/in")
public class StoreInController extends BasicController {

	@Autowired
	private StoreInFacade storeInFacade;

	/**
	 * list 列表查询页面
	 * @param model
	 * @param dataVo
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年8月27日 上午11:25:26
	 */
	@RequestMapping(value = "/list.html")
	public String list(Model model, StoreInReqVo dataVo) throws BizRuleException {
		SearchDataVo vo = SearchUtil.getVo();
		HashMap<String, Object> searchMap = null;
		try {
			searchMap = (HashMap<String, Object>) ConvertUtil.objectToMap(dataVo);
		} catch (Exception e) {
			logger.error("list error:{}",ExceptionUtil.getMessage(e));
		}
		
		if(searchMap==null || searchMap.size()==0) {
			searchMap = new HashMap<>();
			searchMap.put("status", OnOffDelEnums.ON.getType());	
		}else {
			searchMap.put("status", OnOffDelEnums.ON.getType());
		}
		
		//加入搜索条件
		if(dataVo!=null&&dataVo.getCode()!=null) {
			searchMap.put("code",dataVo.getCode());//单号
		}
		if(dataVo!=null&&dataVo.getCause()!=null) {
			searchMap.put("cause", dataVo.getCause());//原因
		}
		if(dataVo!=null&&dataVo.getWarehouseName()!=null) {
			searchMap.put("warehouseName",dataVo.getWarehouseName());//仓库
		}
		if(dataVo!=null&&dataVo.getSupplierName()!=null) {
			searchMap.put("supplierName", dataVo.getSupplierName());//供应商
		}
		if(dataVo!=null && StringUtil.isNotBlank(dataVo.getSearchKey()) && StringUtil.isNotBlank(dataVo.getKeyValue())) {
			if(dataVo.getSearchKey().equals("code")) {
				searchMap.put("code",dataVo.getKeyValue());//单号
			}
			if(dataVo.getSearchKey().equals("cause")) {
				searchMap.put("cause",dataVo.getKeyValue());//入库原因
			}
			if(dataVo.getSearchKey().equals("warehouseName")) {
				searchMap.put("warehouseName",dataVo.getKeyValue());//仓库名称
			}
			
		}
		
		ManagerVo sysUser = this.getSysUser();
		SearchResult<StoreInVo> searchResult = storeInFacade.getListTotalByWhere(sysUser.getMerchantId(),searchMap,vo.getStart(),vo.getSize());

		vo.setList(searchResult.getList());
		vo.setCount(searchResult.getTotal());
		SearchUtil.putToModel(model, vo);
		
		return "/store/in/list";

	}

	/**
	 * 编辑页面
	 * @param model
	 * @param id
	 * @return
	 * @throws BizRuleException 
	 * @Author:chistar
	 * @Since : 2018年11月18日下午1:53:53
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Long id) throws BizRuleException{
		StoreInVo dataVo=new StoreInVo();
		if(id!=null&&id.intValue()!=0){//修改
			ManagerVo sysUser = this.getSysUser();
			dataVo = storeInFacade.getByPrimaryKey(sysUser.getMerchantId(),id);									
		}
		model.addAttribute("dataVo",dataVo);				
		return "/store/in/edit";
	}

	/**
	 * 新增或保存dataVo
	 * @param reqeust
	 * @param dataVo
	 * @return
	 * @throws BizRuleException
	 * @Author:chistar
	 * @Since : 2018年11月18日下午2:10:29
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Model save(HttpServletRequest reqeust, StoreInVo dataVo) throws BizRuleException {	
		ResultVo<String> result = Results.newResultVo();
		ExtendedModelMap model = new ExtendedModelMap();
		//保存前校验
		dataVo=XSSUtil.cleanXSS(dataVo);
		dataVo.setStatus(OnOffDelEnums.ON.getType());//上线
		ManagerVo loginUser = this.getSysUser();
		if(dataVo.getMerchantId()==null) {
			dataVo.setMerchantId(loginUser.getMerchantId());
		}
		//需要dataVo里增加必填选项的注解
		BizRuleCheck.validateByUseridAnnotation(dataVo,"","");
		Long dataid=0L;		
		
		logger.info("save dataVo={},sysUser={}",dataVo,loginUser);
		if(dataVo.getId() == null){
			dataid = storeInFacade.insertStoreInVo(loginUser.getMerchantId(),dataVo);
		}else{
			dataid = storeInFacade.updateStoreIn(loginUser.getMerchantId(),dataVo);
		}				
		result = Results.checkArguments(dataid!=0, "保存失败！");

		model.addAttribute("ret", result);
		return model;
	}
	
	
	/**
	 * 删除id
	 * @param model
	 * @param reqeust
	 * @param id
	 * @return
	 * @throws BizRuleException
	 * @Author:chistar
	 * @Since : 2018年11月18日下午2:11:52
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Model delete(Model model, HttpServletRequest reqeust, Long id) throws BizRuleException {
		ResultVo<Boolean> ret = new ResultVo<>();
		ret.setSuccess(false);
		if (id == null || id < 1) {
			ret.setResultDes("id 不能为空");
			ret.setSuccess(false);
			model.addAttribute("ret", ret);
			return model;
		}
		ManagerVo sysUser = this.getSysUser();
		storeInFacade.deleteBy(sysUser.getMerchantId(),id);
		
		ret.setSuccess(true);
		model.addAttribute("ret", ret);
		return model;
	}
	



}
