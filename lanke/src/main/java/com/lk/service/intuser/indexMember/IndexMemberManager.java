package com.lk.service.intuser.indexMember;

import com.lk.entity.Message;
import com.lk.entity.Message2;
import com.lk.entity.weixin.JSAPI;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.lk.util.PageData;

public interface IndexMemberManager {

	
	/**
	 * 判断用户是否绑定会员
	 * @param pd（传入user_id和internet_id）
	 * @return 返回的json中字段result=false时表示未绑定，否则已绑定
	 * @throws Exception
	 */
	public ModelAndView judgeBind(PageData pd) throws Exception;


	/**
	 * 判断 门店加v下的绑定会员，其身份信息是否正确
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Message2 judgeUserInfo(String store_id, String carded, String name) throws Exception;

	/**
	 * 签到得积分
	 * @param pd（传入user_id和internet_id）
	 * @return 返回签到结果
	 * @throws Exception
	 */
	public JSONObject signIn(PageData pd) throws Exception;
	
	
	/**
	 * 检查后台是否设置了好友分享
	 * @param pd（传入internet_id）
	 * @return
	 * @throws Exception
	 */
	public JSONObject canShare(PageData pd) throws Exception ;
	
	
	/**
	 * 获取分享的设置数据
	 * @param pd（传入user_id和internet_id，url）
	 * @return 返回分享的设置数据
	 * @throws Exception
	 */
	public ModelAndView share(PageData pd) throws Exception ;
	

    /**
     * 获取卡券列表
     * @return
     * @throws Exception
     */
	Message getCardList(String type) throws Exception;
	
	
	/**
	 * 会员领取卡券
	 * @param pd（传入user_id和internet_id，mayId为卡卷的主键id）
	 * @return 返回领取结果
	 * @throws Exception
	 */
	public JSONObject receiveCard(PageData pd) throws Exception ;
	

	/**
	 * 加载积分商城
	 * @param pd（传入user_id和internet_id）
	 * @return
	 * @throws Exception
	 */
	public JSONObject loadMall(PageData pd) throws Exception ;
	
	
	/**
	 * 积分申请兑换商品
	 * @param pd（传入user_id和internet_id，Auction_ID商品id）
	 * @return 返回申请结果
	 * @throws Exception
	 */
	public JSONObject applyAuction(PageData pd) throws Exception ;
	
	
	/**
	 * 判断是否可以抽奖
	 * @param pd（传入internet_id）
	 * @return 返回判断结果
	 * @throws Exception
	 */
	public JSONObject canLottery(PageData pd) throws Exception ;
	
	
	/**
	 * 加载抽奖奖品列表
	 * @param pd（传入internet_id）
	 * @return
	 * @throws Exception
	 */
	public JSONObject loadLottery(PageData pd) throws Exception ;
	
	
	/**
	 * 申请抽奖
	 * @param pd（传入user_id和internet_id）
	 * @return 返回申请结果
	 * @throws Exception
	 */
	public JSONObject applyLottery(PageData pd) throws Exception ;
	
	
	/**
	 * 保存新增投诉
	 * @param pd（传入user_id和internet_id，name，投诉提交的LM_CONTENT，LM_TYPEID，STORE_ID，fileArr图片数组）
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveLm(PageData pd) throws Exception ;

	/**
	 * 通过条件判断领取间隔
	 * @param pd
	 * @param string
	 * @param string2
	 * @return
	 * @throws Exception
	 */
	public JSONObject judgeDateNumber(PageData pd, String string, String string2) throws Exception;

	/**
	 * 接下来通过卡券id,用户id去判断,该卡券是否适用于该用户的门店
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject judgeStore(PageData pd) throws Exception;


	/**
	 * 用户呼叫网管
	 * @param user_id 用户id
	 * @param internet_id 网吧id
	 * @return
	 * @throws Exception
	 */
	public JSONObject callStoreStaff(String user_id, String internet_id, String computer_name, String username) throws Exception;



	/**
	 * 检查是否关注公众号
	 * @param code 扫授权二维码后的微信推送的code
	 * @param appid 微信公众号id
	 * @return
	 */
	JSONObject checkUserSubscribe(String appid, String code, String cacheName, String sceneStr) throws Exception;


    JSAPI getWXJSdata(String url, String internet_id) throws Exception;
}
