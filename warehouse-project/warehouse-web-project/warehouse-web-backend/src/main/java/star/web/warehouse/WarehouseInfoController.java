package star.web.warehouse;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import star.ass.WarehouseAndAddressUtil;
import star.bizbase.enums.OnOffDelEnums;
import star.bizbase.exception.BizRuleException;
import star.bizbase.util.BizRuleCheck;
import star.facade.ecmanager.manager.vo.ManagerVo;
import star.facade.warehouse.WarehouseAddressFacade;
import star.facade.warehouse.WarehouseClassFacade;
import star.facade.warehouse.WarehouseFacade;
import star.facade.warehouse.vo.WarehouseAddressVo;
import star.facade.warehouse.vo.WarehouseAndAddressVo;
import star.facade.warehouse.vo.WarehouseVo;
import star.facade.warehouse.vo.dto.WarehouseClassDTO;
import star.facade.warehouse.vo.request.WarehouseReqVo;
import star.fw.web.util.SearchUtil;
import star.fw.web.vo.SearchDataVo;
import star.fw.web.xss.XSSUtil;
import star.vo.result.ResultVo;
import star.vo.search.SearchResult;
import star.web.BasicController;

/**
 *
 * @author:chistar
 * @since:2018年11月18日下午12:35:07
 */
@Controller
@RequestMapping("/warehouse/info")
public class WarehouseInfoController extends BasicController {

	@Autowired
	private WarehouseFacade warehouseFacade;
	@Autowired 
	private WarehouseClassFacade warehouseClassFacade;
	@Autowired 
	private WarehouseAddressFacade warehouseAddressFacade;

	
	
	/**
	 * list 仓库信息列表查询页面
	 * @since：2019年6月9日上午9:04:30
	 * @user： chenhang
	 * @param model
	 * @param dataVo
	 * @return
	 * @throws BizRuleException
	 */
	@RequestMapping(value = "/list.html")
	public String list(Model model, WarehouseReqVo dataVo) throws BizRuleException {
		// 为输入的搜索条件去除前后空格
		if(dataVo!=null && dataVo.getKeyValue()!=null && !"".equals(dataVo.getKeyValue())) {
			dataVo.setKeyValue(dataVo.getKeyValue().trim());
		}		
		SearchDataVo vo = SearchUtil.getVo();
		//dataVO接收浏览器传入的参数,searchMap封装搜索条件
		HashMap<String, Object> searchMap = new HashMap<String, Object>();
		if(dataVo!=null && StringUtil.isNotBlank(dataVo.getWarehouseType())) {
			searchMap.put("warehouseType",dataVo.getWarehouseType());//仓库类别
		}
		if(dataVo!=null && dataVo.getWarehouseClassId()!=null) {
			searchMap.put("warehouseClassId",dataVo.getWarehouseClassId());//仓库分类
		}
		if(dataVo!=null && StringUtil.isNotBlank(dataVo.getSearchKey()) && StringUtil.isNotBlank(dataVo.getKeyValue())) {
			if(dataVo.getSearchKey().equals("code")) {
				searchMap.put("ckCode",dataVo.getKeyValue());//仓库编码
			}
			if(dataVo.getSearchKey().equals("name")) {//仓库名称
				searchMap.put("ckName",dataVo.getKeyValue());
			}
			
			
		}
		if(searchMap==null || searchMap.size()==0) {
			searchMap = new HashMap<>();
			searchMap.put("status", OnOffDelEnums.ON.getType());
		}else {
			searchMap.put("status", OnOffDelEnums.ON.getType());
		}
		ManagerVo sysUser = this.getSysUser();
		//SearchResult:封装返回页面的数据
		SearchResult<WarehouseVo> searchResult = warehouseFacade.getListTotalByWhere(sysUser.getMerchantId(),searchMap,vo.getStart(),vo.getSize());
		
		
		//获取仓库所有分类列表
		model.addAttribute("warehouseClassDTOList", warehouseClassFacade.getCacheWarehouseClassDTOList(sysUser.getMerchantId()));
		
		vo.setList(searchResult.getList());//所有记录
		vo.setCount(searchResult.getTotal());//总记录数
		SearchUtil.putToModel(model, vo);

		return "/warehouse/info/list";

	}

	
	/**
	 * 仓库信息编辑
	 * @since：2019年6月9日上午9:04:06
	 * @user： chenhang
	 * @param model
	 * @param id
	 * @return
	 * @throws BizRuleException
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Long id) throws BizRuleException{
		//WarehouseAndAddressVo接收和回显数据
		WarehouseAndAddressVo dataVo=new WarehouseAndAddressVo();
		//WarehouseVo dataVo=new WarehouseVo();
		if(id!=null&&id.intValue()>0){//修改
			ManagerVo sysUser = this.getSysUser();
			//dataVo = warehouseFacade.getByPrimaryKey(sysUser.getMerchantId(),id);	
			WarehouseVo  warehouseVo=warehouseFacade.getByPrimaryKey(sysUser.getMerchantId(),id);
			WarehouseAddressVo WarehouseAddressVo=warehouseAddressFacade.getByPrimaryKey(sysUser.getMerchantId(),id);
			dataVo=WarehouseAndAddressUtil.mergeToWarehouseAndAddressVo(warehouseVo, WarehouseAddressVo);
		}
		model.addAttribute("dataVo",dataVo);//数据返回到页面用来回显
		ManagerVo loginUser = this.getSysUser();
		//仓库类别获取
		 List<WarehouseClassDTO> warehouseClassDTOList = warehouseClassFacade.getCacheWarehouseClassDTOList(loginUser.getMerchantId());
		 logger.info("warehouseClassDTOList={},merchantId={}",warehouseClassDTOList,loginUser.getMerchantId());
		 model.addAttribute("warehouseClassDTOList",warehouseClassDTOList);
		 
		return "/warehouse/info/edit";
	}

	
	/**
	 * 新增或保存仓库信息dataVo
	 * @since：2019年6月9日上午9:05:14
	 * @user： chenhang
	 * @param reqeust
	 * @param dataVo
	 * @return
	 * @throws BizRuleException
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Model save(HttpServletRequest reqeust, WarehouseAndAddressVo dataVo) throws BizRuleException {	
		ExtendedModelMap model = new ExtendedModelMap();
		ResultVo<String> result = new ResultVo<>();
		result.setSuccess(false);
		ManagerVo loginUser = this.getSysUser();
		if(dataVo.getMerchantId()==null) {
			dataVo.setMerchantId(loginUser.getMerchantId());
		}
		//需要dataVo里增加必填选项的注解
		BizRuleCheck.validateByUseridAnnotation(dataVo,"","");
		//保存前校验
		dataVo=XSSUtil.cleanXSS(dataVo);
		
		logger.info("save dataVo={},sysUser={}",dataVo,loginUser);
		warehouseFacade.saveWarehouseAndAddressVo(dataVo.getMerchantId(), dataVo);
		result.setSuccess(true);

		model.addAttribute("ret", result);
		return model;
	}
	
	
	
	/**
	 * 根据id删除仓库信息
	 * @since：2019年6月9日上午9:06:14
	 * @user： chenhang
	 * @param model
	 * @param reqeust
	 * @param id
	 * @return
	 * @throws BizRuleException
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
		warehouseFacade.deleteWarehouseAndAddressBy(sysUser.getMerchantId(),id);
		
		ret.setSuccess(true);
		model.addAttribute("ret", ret);
		return model;
	}
	



}
