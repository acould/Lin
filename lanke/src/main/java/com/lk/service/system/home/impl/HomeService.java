package com.lk.service.system.home.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.home.HomeManager;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * 说明： 首页 创建人：李泽华 创建时间：2018-10-18
 * 
 * @version
 */
@Service("homeService")
public class HomeService implements HomeManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 计算待查看消息,待发货商品,待核销卡券,待回复留言
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> count(Page page) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 计算待查看消息,待发货商品,待核销卡券,待回复留言
		Integer messageSum = 0;
		Integer orderSum = 0;
		Integer cardSum = 0; // 待核销的卡券
		Integer leaveSum = 0;
		List<PageData> messageList = (List<PageData>) dao.findForList("HomeMapper.messageSum", page);
		List<PageData> orderList = (List<PageData>) dao.findForList("HomeMapper.orderSum", page);
		List<PageData> cardList = (List<PageData>) dao.findForList("HomeMapper.cardSum", page); // 卡券核销(网吧老板通过网吧id,门店通过open_id去关联)
		List<PageData> leaveList = (List<PageData>) dao.findForList("HomeMapper.leaveSum", page);
		// 待查看消息
		if (StringUtil.isNotEmpty(messageList)) {
			messageSum = messageList.size();
		}
		// 待发货商品
		if (StringUtil.isNotEmpty(orderList)) {
			orderSum = orderList.size();
		}
		// 待核销卡券
		if (StringUtil.isNotEmpty(cardList)) {
			cardSum = cardList.size();
		}
		// 待回复留言
		if (StringUtil.isNotEmpty(leaveList)) {
			leaveSum = leaveList.size();
		}
		map.put("messageSum", messageSum);
		map.put("orderSum", orderSum);
		map.put("cardSum", cardSum);
		map.put("leaveSum", leaveSum);
		return map;
	}

	/**
	 * 通过条件查询收入
	 * @param page
	 * @throws Exception
	 */
	@Override
	public JSONObject incomeCount(Page page) throws Exception {// 日传小时,周和月传日,年传月
		JSONObject json = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar c = Calendar.getInstance();// 日历
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");// 格式工具
		String time = sdf.format(c.getTime());
		String times[]=time.split("-");
		map = this.incomeTotal(page); //计算揽客充值收入(字段数据)
		//下列计算增长概况(图表数据)
		if (StringUtil.isEmpty(page.getPd().get("time").toString()) || page.getPd().get("time").toString().equals("day")) {// 时间条件为空或者为day时,计算昨天(按小时算)
			json = this.incomeCount1(page, 24, 0);
		} else if (page.getPd().get("time").toString().equals("week")) {// 时间条件为week时,计算最近一周(按每日算)
			json = this.incomeCount1(page, 7, 1);
		} else if (page.getPd().get("time").toString().equals("month")) {// 时间条件为month时,计算最近一月(按每日算)
			Integer a = Integer.parseInt(times[1]);
			json = this.incomeCount1(page, a, 2);
		} else {// 时间条件year时,计算最近一年(按每月算)
			Integer a = Integer.parseInt(times[0]);
			json = this.incomeCount1(page, a, 3);
		}
		json.put("income", map.get("income"));
		json.put("income_title", map.get("income_title"));
		return json;
	}

	/**
	 * 计算揽客充值收入(字段数据)
	 * 
	 * @param page
	 * @param time
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> incomeTotal(Page page) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar c = Calendar.getInstance();// 日历
		Calendar c1 = Calendar.getInstance();// 日历
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 格式工具
		if (StringUtil.isEmpty(page.getPd().get("time").toString()) || page.getPd().get("time").toString().equals("sum")) {// 未选择或者选择总
			if(StringUtil.isEmpty(page.getPd().get("store").toString())) {//搜索条件为空
				map.put("income_title", "揽客充值总收入");
			}else {
				map.put("income_title", "该门店揽客充值总收入");
			}
		} else if (page.getPd().get("time").toString().equals("day")) {// 日
			map.put("income_title", "揽客昨日收入");
			c.add(Calendar.DAY_OF_MONTH, -1);
			page.getPd().put("time1", sdf.format(c.getTime()));
			page.getPd().put("time2", sdf.format(c1.getTime()));
		} else if (page.getPd().get("time").toString().equals("week")) {// 周
			map.put("income_title", "揽客最近一周收入");
			c.add(Calendar.WEEK_OF_MONTH, -1);
			c1.add(Calendar.DAY_OF_MONTH, +1);
			page.getPd().put("time1", sdf.format(c.getTime()));
			page.getPd().put("time2", sdf.format(c1.getTime()));
		} else if (page.getPd().get("time").toString().equals("month")) {// 月
			map.put("income_title", "揽客最近一月收入");
			c.add(Calendar.MONTH, -1);
			c1.add(Calendar.DAY_OF_MONTH, +1);
			page.getPd().put("time1", sdf.format(c.getTime()));
			page.getPd().put("time2", sdf.format(c1.getTime()));
		} else if(page.getPd().get("time").toString().equals("year")){// 年
			map.put("income_title", "揽客最近一年总收入");
			c.add(Calendar.YEAR, -1);
			c1.add(Calendar.DAY_OF_MONTH, +1);
			page.getPd().put("time1", sdf.format(c.getTime()));
			page.getPd().put("time2", sdf.format(c1.getTime()));
		} else { //当搜索时间不为空时   TIME=2018-10-01 - 2018-12-31
			String time = page.getPd().get("time").toString();
			String times[] = time.split(" - ");
			page.getPd().put("time1", times[0]);
			page.getPd().put("time2", times[1]);
			map.put("income_title", times[0]+"到"+times[1]+"时间段揽客充值总收入");
		}
		List<PageData> incomeList = (List<PageData>) dao.findForList("HomeMapper.incomeCount", page);
		double incomeSum = (double) incomeList.get(0).get("income");
		Integer income = (int) incomeSum;
		map.put("income", income);
		return map;
	}

	/**
	 * 按日或日计算粉丝和会员数量(图表数据)
	 * 
	 * @param page
	 * @param a
	 * @param b
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject incomeCount1(Page page, int a, int b) throws Exception {// b:0--day表示按小时,1--week表示按天,2--month表示按天,3--year表示按月
		JSONObject json = new JSONObject();
		String categories[] = new String[a]; // x轴
		int data[] = new int[a]; // x对应的值
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 格式工具
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");// 格式工具
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式工具
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");// 格式工具
		SimpleDateFormat sdf4 = new SimpleDateFormat("MM-dd");// 格式工具
		SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM");// 格式工具
		if (b == 0) {// 最近一天
			json.put("chart_title", "昨日收入趋势");
		} else if (b == 1) {// 最近一周
			json.put("chart_title", "最近一周收入趋势");
		} else if (b == 2) {// 最近一月
			json.put("chart_title", "最近一月收入趋势");
		} else {// 最近一年
			json.put("chart_title", "最近一年收入趋势");
		}
		if (b == 0) {// 按小时(24小时)
			c.add(Calendar.DAY_OF_MONTH, -1); //获取昨天日期
			String time = sdf1.format(c.getTime())+" 00:00:00";
			c.setTime(sdf2.parse(time));
			for (int i = 0 ; i < a; i++) {
				page.getPd().put("time1", sdf2.format(c.getTime()));
				categories[i] = sdf3.format(c.getTime());
				c.add(Calendar.HOUR_OF_DAY, +1);
				page.getPd().put("time2", sdf2.format(c.getTime()));
				List<PageData> incomeList = (List<PageData>) dao.findForList("HomeMapper.incomeCount", page);
				double income = (double) incomeList.get(0).get("income");
				data[i] = (int) income;
			}
		} else if (b == 1 || b == 2) {// 按每日
			c.add(Calendar.DAY_OF_MONTH, +1);
			c1.add(Calendar.DAY_OF_MONTH, +2);
			for (int i = a - 1; i > -1; i--) {
				c.add(Calendar.DAY_OF_MONTH, -1);
				c1.add(Calendar.DAY_OF_MONTH, -1);
				categories[i] = sdf4.format(c.getTime());
				page.getPd().put("time1", sdf.format(c.getTime()));
				page.getPd().put("time2", sdf.format(c1.getTime()));
				List<PageData> incomeList = (List<PageData>) dao.findForList("HomeMapper.incomeCount", page);
				double income = (double) incomeList.get(0).get("income");
				data[i] = (int) income;
			}
		} else {// 按每月
			c.add(Calendar.MONTH, +1);
			for (int i = a - 1; i > -1; i--) {
				c.add(Calendar.MONTH, -1);// 日期偏移,正数向前,负数向后!
				c1.add(Calendar.MONTH, -1);// 日期偏移,正数向前,负数向后!
				categories[i] = sdf5.format(c.getTime());
				c.add(Calendar.DAY_OF_MONTH, +1);
				page.getPd().put("time1", sdf.format(c1.getTime()));
				page.getPd().put("time2", sdf.format(c.getTime()));
				List<PageData> incomeList = (List<PageData>) dao.findForList("HomeMapper.incomeCount", page);
				double income = (double) incomeList.get(0).get("income");
				data[i] = (int) income;
			}
		}
		json.put("categories", categories);
		json.put("data", data);
		return json;
	}

	/**
	 * 通过条件查询粉丝/会员数量(字段数据)
	 * 
	 * @param page
	 * @throws Exception
	 */
	@Override
	public JSONObject userCount(Page page) throws Exception {// 日传小时,周和月传日,年传月
		JSONObject json = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar c = Calendar.getInstance();// 日历
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");// 格式工具
		String time = sdf.format(c.getTime());
		String times[]=time.split("-");
		//下面计算用户增长概况(图表数据)
		if (StringUtil.isEmpty(page.getPd().get("time").toString()) || page.getPd().get("time").toString().equals("user_week")) {// 时间条件为空或者为user_week时,计算最近一周(按每日算)
			map = this.userTotal(page,7); //计算会员粉丝数量(字段数据)
			json = this.userCount1(page, 7, 1);
		} else if (page.getPd().get("time").toString().equals("user_month")) {// 时间条件为user_month时,计算最近一月(按每日算)
			Integer a = Integer.parseInt(times[1]);
			map = this.userTotal(page,a); //计算会员粉丝数量(字段数据)
			json = this.userCount1(page, a, 2);
		} else {// 时间条件为user_year时,计算最近一年(按每月算)
			Integer a = Integer.parseInt(times[0]);
			map = this.userTotal(page,a); //计算会员粉丝数量(字段数据)
			json = this.userCount1(page, a, 3);
		}
		json.put("fans_title", map.get("fans_title"));
		json.put("fans_num", map.get("fans_num"));
		json.put("member_title", map.get("member_title"));
		json.put("member_num", map.get("member_num"));
		return json;
	}

	/**
	 * 计算揽客粉丝,会员
	 * 
	 * @param page
	 * @param a 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> userTotal(Page page, int a) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar c = Calendar.getInstance();// 日历
		Calendar c1 = Calendar.getInstance();// 日历
		c.add(Calendar.DAY_OF_MONTH, +1);
		c1.add(Calendar.DAY_OF_MONTH, +1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 格式工具
		if (StringUtil.isEmpty(page.getPd().get("time").toString())  || page.getPd().get("time").toString().equals("user_sum")) {// 未选择或者选择总
			if(StringUtil.isEmpty(page.getPd().get("store").toString())) {//搜索条件为空
				map.put("fans_title", "总粉丝");
				map.put("member_title", "总会员");
			}else {
				map.put("fans_title", "满足条件的粉丝");
				map.put("member_title", "满足条件的会员");
			}
		} else if (page.getPd().get("time").toString().equals("user_week")) {// 周
			map.put("fans_title", "近一周粉丝");
			map.put("member_title", "近一周会员");
			c.add(Calendar.WEEK_OF_MONTH, -1);
			page.getPd().put("time1", sdf.format(c.getTime()));
			page.getPd().put("time2", sdf.format(c1.getTime()));
		} else if (page.getPd().get("time").toString().equals("user_month")) {// 月
			map.put("fans_title", "近一月粉丝");
			map.put("member_title", "近一月会员");
			c.add(Calendar.DAY_OF_MONTH, -a);
			page.getPd().put("time1", sdf.format(c.getTime()));
			page.getPd().put("time2", sdf.format(c1.getTime()));
		} else if(page.getPd().get("time").toString().equals("user_year")) {// 年
			map.put("fans_title", "近一年粉丝");
			map.put("member_title", "近一年会员");
			c.add(Calendar.MONTH, -a);
			page.getPd().put("time1", sdf.format(c.getTime()));
			page.getPd().put("time2", sdf.format(c1.getTime()));
		} else { //当搜索时间不为空时   TIME=2018-10-01 - 2018-12-31
			String time = page.getPd().get("time").toString();
			String times[] = time.split(" - ");
			page.getPd().put("time1", times[0]);
			page.getPd().put("time2", times[1]);
			map.put("fans_title", "满足条件的粉丝");
			map.put("member_title", "满足条件的会员");
		}
		List<PageData> fansList = (List<PageData>) dao.findForList("HomeMapper.fansCount", page);
		List<PageData> memberList = (List<PageData>) dao.findForList("HomeMapper.memberCount", page);
		long fans_nums = (long) fansList.get(0).get("fans_num");
		int fans_num = (int) fans_nums;
		long member_nums = (long) memberList.get(0).get("member_num");
		int member_num = (int) member_nums;
		map.put("fans_num", fans_num);
		map.put("member_num", member_num);
		return map;
	}

	/**
	 * 按日或日计算粉丝和会员数量(图表数据)
	 * 
	 * @param page
	 * @param a
	 * @param b
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject userCount1(Page page, int a, int b) throws Exception {// b:1--week表示按天,2--month表示按天,3--year表示按月
		JSONObject json = new JSONObject();
		String categories[] = new String[a]; // x轴
		int fans[] = new int[a];
		int member[] = new int[a];
		Calendar c = Calendar.getInstance();// 日历
		Calendar c1 = Calendar.getInstance();// 日历
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 格式工具
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");// 格式工具
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");// 格式工具
		SimpleDateFormat sdf3 = new SimpleDateFormat("MM-dd");// 格式工具
		if (b == 1) {// 最近一周
			json.put("chart_title", "最近一周新增加的用户量");
		} else if (b == 2) {// 最近一月
			json.put("chart_title", "最近一月新增加的用户量");
		} else {// 最近一年
			json.put("chart_title", "最近一年新增加的用户量");
		}

		if (b == 1 || b == 2) {// 按每日
			c.add(Calendar.DAY_OF_MONTH, +1);
			c1.add(Calendar.DAY_OF_MONTH, +2);
			for (int i = a - 1; i > -1; i--) {
				c.add(Calendar.DAY_OF_MONTH, -1);
				c1.add(Calendar.DAY_OF_MONTH, -1);
				categories[i] = sdf3.format(c.getTime());
				page.getPd().put("time1", sdf.format(c.getTime()));
				page.getPd().put("time2", sdf.format(c1.getTime()));
				List<PageData> fansList = (List<PageData>) dao.findForList("HomeMapper.fansCount", page);
				List<PageData> memberList = (List<PageData>) dao.findForList("HomeMapper.memberCount", page);
				long fans_num = (long) fansList.get(0).get("fans_num");
				fans[i] = (int) fans_num;
				long member_num = (long) memberList.get(0).get("member_num");
				member[i] = (int) member_num;
			}
		} else {// 按每月
			c.add(Calendar.MONTH, +1);
			for (int i = a - 1; i > -1; i--) {
				c.add(Calendar.MONTH, -1);// 日期偏移,正数向前,负数向后!
				c1.add(Calendar.MONTH, -1);// 日期偏移,正数向前,负数向后!
				categories[i] = sdf2.format(c.getTime());
				if(i == a-1) {//第一次进来
					c.add(Calendar.DAY_OF_MONTH, +1);
					c1.add(Calendar.DAY_OF_MONTH, +1);
				}
				page.getPd().put("time1", sdf.format(c1.getTime()));
				page.getPd().put("time2", sdf.format(c.getTime()));
				List<PageData> fansList = (List<PageData>) dao.findForList("HomeMapper.fansCount", page);
				List<PageData> memberList = (List<PageData>) dao.findForList("HomeMapper.memberCount", page);
				long fans_num = (long) fansList.get(0).get("fans_num");
				fans[i] = (int) fans_num;
				long member_num = (long) memberList.get(0).get("member_num");
				member[i] = (int) member_num;
			}
		}
		json.put("categories", categories);
		json.put("fans", fans);
		json.put("member", member);
		return json;
	}

	/**
	 * 查询相关门店信息
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> storeList(Page page) throws Exception {
		List<PageData> messageList = (List<PageData>) dao.findForList("HomeMapper.storeList", page);
		return messageList;
	}

	/**
	 * 通过菜单url获取菜单目录id
	 * @param page
	 * @throws Exception
	 */
	@Override
	public PageData selectMenu(PageData pd) throws Exception {
		return (PageData) dao.findForObject("HomeMapper.selectMenu", pd);
	}

	/**
	 * 获取门店列表
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> storeLists(Page page) throws Exception {
		return (List<PageData>) dao.findForList("HomeMapper.storeLists", page);
	}

	/*public static void main(String[] args) throws ParseException {
		Calendar c = Calendar.getInstance();// 日历
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式工具
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式工具
		String time = sdf.format(c.getTime())+" 00:00:00";
		System.out.println("今天是:"+time);
		c.setTime(sdf1.parse(time));
		c.add(Calendar.HOUR_OF_DAY, +13);
		System.out.println("今天修改后是:"+Tools.date2Str(c.getTime()));
	}*/

}
