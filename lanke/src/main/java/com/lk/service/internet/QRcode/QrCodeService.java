package com.lk.service.internet.QRcode;

import com.lk.entity.Page;
import com.lk.util.PageData;
import net.sf.json.JSONObject;

/**
 * @author myq
 * @description 网吧二维码--业务接口
 * @create 2018-12-14 15:10
 */
public interface QrCodeService {


    /**
     * 通过主键id查找
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData findById(PageData pd) throws Exception;


    /**
     * 加载二维码列表
     * @param pd
     * @return
     * @throws Exception
     */
    public JSONObject loadQrList(PageData pd, Page page) throws Exception;


    /**
     * 批量导入电脑名称
     * @param pd
     * @return
     * @throws Exception
     */
    public JSONObject importCN(PageData pd) throws Exception;


    /**
     * 批量下载二维码
     * @param pd
     * @return
     * @throws Exception
     */
    public JSONObject downloadQrs(PageData pd) throws Exception;


    /**
     * 启用/禁用，改变二维码状态
     * @param pd
     * @return
     * @throws Exception
     */
    public JSONObject editStatus(PageData pd) throws Exception;


    /**
     * 禁用所有电脑
     * @param pd
     * @return
     * @throws Exception
     */
    public JSONObject banAll(PageData pd) throws Exception;


    /**
     * 新增/编辑单个二维码
     * @param pd
     * @return
     * @throws Exception
     */
    public JSONObject saveQr(PageData pd) throws Exception;


    /**
     * 删除单个二维码
     * @param pd
     * @return
     * @throws Exception
     */
    public JSONObject deleteQr(PageData pd) throws Exception;


    /**
     * 删除电脑
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject delMore(PageData pd) throws Exception;




    /**
     * 获取微信端-一码通首页数据
     * @param pd（appid，code，computer_name，state）
     * @return
     * @throws Exception
     */
    public JSONObject userIndex(PageData pd) throws Exception;


    /**
     * 用户上机
     * @param pd（store_id，psd，card_id，computer_name）
     * @return
     * @throws Exception
     */
    public JSONObject userUp(PageData pd) throws Exception;


    /**
     * 查询用户正在上机情况
     * @param pd（store_id,computer_name,card_id）
     * @return
     * @throws Exception
     */
    public JSONObject userOnline(PageData pd) throws Exception;


    /**
     * 获取带场景值的二维码
     * @param pd
     * @return
     * @throws Exception
     */
    public JSONObject getSubscribeImg(PageData pd) throws Exception;


    /**
     * 用户下机
     * @param pd
     * @return
     * @throws Exception
     */
    public JSONObject userDown(PageData pd) throws Exception;


    /**
     * 设置顺网二维码参数
     * @param pd
     * @return
     * @throws Exception
     */
    public JSONObject setQrParam(PageData pd) throws Exception;


    /**
     * 获取wxConfig参数
     * @param pd
     * @return
     * @throws Exception
     */
    public JSONObject getWxConfig(PageData pd) throws Exception;


    /**
     * 获取用户上机状态
     * @param pd
     * @return
     * @throws Exception
     */
    public JSONObject getState(PageData pd) throws Exception;


    /**
     * 网络唤醒
     * @param pd （传store_id，computer_name）
     * @return
     * @throws Exception
     */
    public JSONObject wakePower(PageData pd) throws Exception;


    /**
     * 处理扫描二维码的用户信息
     * @return
     * @throws Exception
     */
    JSONObject handleUser(PageData pd) throws Exception;

    /**
     * 处理二维码信息
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject handleQrInfo(PageData pd, PageData pdQr) throws Exception;

    /**
     * 判断网吧电脑是否正在上机
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject handelComputerIsUp(PageData pd, PageData pdQr) throws Exception;

    /**
     * 判断用户是否正在上机
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject handleUserIsUp(PageData pd) throws Exception;


    /**
     * 加载二维码 首页信息
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject loadUserIndex(PageData pd) throws Exception;

}
