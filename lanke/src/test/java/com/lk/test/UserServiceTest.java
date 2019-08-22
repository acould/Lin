package com.lk.test;


import java.math.BigInteger;
import java.util.Date;

import com.lk.entity.system.BundUser;
import com.lk.service.intuser.wxML.impl.WxMLServiceImpl;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.wechat.util.WeChatOpenUtil;
import net.sf.json.JSONObject;


public class UserServiceTest {

    public BigInteger setBit(int n) {

        int intNum = n >>> 5;
        System.out.print(intNum);
        return null;
    }

    public static void main(String[] args) throws Exception {
        UserServiceTest userServiceTest = new UserServiceTest();
        userServiceTest.setBirth();
    }


    public void setBirth() {

        PageData pdBoard = new PageData();

        JSONObject json = new JSONObject();
        json.put("aa", 111);

        pdBoard = (PageData) JSONObject.toBean(JSONObject.fromObject(json), PageData.class);
        System.out.println(pdBoard.toString());


        long date1 = Tools.str2Date2("2019-07-07").getTime();// 过期日期
        long date2 = new Date().getTime(); // 当前日期
        date1 = date1 - (3 * 24 * 60 * 60 * 1000);//提前3天通知

//        过期 》 当前
//                提前3天通知
//                过期-3 》 当前
        if(date1 > date2){
            System.out.println("过期了");
        }

//        JSONObject jsonuserFlow = new JSONObject();
//        jsonuserFlow.put("begin_time", Tools.dateAddDay2(new Date(), -183));
//        jsonuserFlow.put("end_time", Tools.date2Str(new Date()));
//
//        PageData pd =new PageData();
//        pd.put("begin_time", jsonuserFlow.getString("begin_time"));
//        pd.put("end_time", jsonuserFlow.getString("end_time"));
//
//        long last = 0;
//        last = Tools.str2Date(pd.getString("begin_time")).getTime();
//        System.out.println(last);
//
//
//        double sum = 0;
//        int a = 47;
//        System.out.println( 0/a + "");



//        Date endDate = getNextTime(1 *( 3 - 1),
//                0, new Date());
//
//        System.out.println(Tools.dateStr(endDate));
//
//        for (int i = 0; i < 3; i++) {
//            int handsel_time = 1;
//            int handsel_times = 0;
//            Date date = getNextTime(handsel_time*i, handsel_times, new Date());
//            String s = Tools.dateStr(date);
//            if(i == 0){
//                s = Tools.dateStr(new Date());
//            }
//            System.out.println(s);
//        }


//        BundUser bundUser = new BundUser();
//        if(bundUser == null || bundUser.getOpen_id() == null){
//            System.out.println("空");
//        }
//
//        String begin_time = "2019-05-18";
//        String end_time = "2019-06-18";
//
//        long a = (Tools.str2Date2(end_time).getTime() - Tools.str2Date2(begin_time).getTime());
//        System.out.println(a);
//        int day = (int) (a / 1000 / 60 / 60 / 24);
//        System.out.println(day);
//
//        PageData pd = new PageData();
//        String now = Tools.date2Str(new Date()).substring(0,10);
//        pd.put("begin_time", now + " 00:00:00");
//        pd.put("end_time", now + " 23:59:59");
//        System.out.println(pd.toString());
//
//        Date begin = Tools.str2Date2("2019-06-24");
//        Date end = Tools.str2Date2("2019-06-25");
//        System.out.println(begin.getTime());
//        System.out.println(end.getTime());
//        System.out.println(new Date().getTime());
//
//        //去除 已过期的卡券
//        if (begin.getTime() > new Date().getTime() || new Date().getTime() < end.getTime()) {
//            System.out.println("sdfasd");
//        }






		/* String cardEd="362531198806180053";
		 String cardNew=cardEd.substring(cardEd.length()-8, cardEd.length()-4);
		 Date dateTimeUpdate=new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		 String dateNow = sdf.format(dateTimeUpdate); 
		 String dateNowStr = dateNow.substring(dateNow.length()-5, dateNow.length()).replace("-","");
		 if(!cardNew.endsWith(dateNowStr)){
			 System.out.print("生日和身份证绑定信息不符。");
			}else{
				System.out.print("生日和身份证绑定信息相符。");
			}*/
//        Cache cache = CacheManager.myGzhCahce();
//        Cache cachenEW = CacheManager.getOneMiniteCahce();
//        String s = "aaaaamy";
//        String b = "aaaaata";
//        Object o = s;
//        Object oo = b;
//        cache.insertObject("AAA", o);
//        cachenEW.insertObject("AAA", oo);
//        System.out.print(cache.getObject("AAA"));
//        for (int i = 0; i < 13; i++) {
//            try {
//                System.out.print("bbbb-----");
//                Thread.currentThread().sleep(5000);
//                if (i >= 6) {
//                    cachenEW.insertObject("BBB", "FFF");
//                    System.out.print(cachenEW.getObject("BBB"));
//                }
//                System.out.print(cachenEW.getObject("AAA"));
//                System.out.print("cccc---");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
    	
    	
//    	Pattern pattern = Pattern.compile(PublicManagerUtil.PHONE_REG);
//		Matcher matcher = pattern.matcher("19900009999");
//		if (!matcher.find()) {
//			System.out.println("手机号码格式不匹配");
//		}
//		System.out.println("1");

//        String token = "14_vCd9jKMhFEqP3ldJCRqf15tLv1YUI2z35bGtjsvgTKzoh9g_iojqKxe2r93TJmUOMXccbvWrkZ0FEUzutV6VPS0b3Ijgd6nI3OgTBrW54T_pqEt7QMQgnHctk1nexXeb7cEUOud--um6MaSbNLHhALDGEA";
//        String open_id = "otlSPv-IRhQVh2Gih_qkps9imyyM";
//         WeixinUtil.getUserInfo(token, open_id);

//        String url = "http://localhost:80/uploadFiles/uploadImgs/321dfm54lod846579985e1ss2135cvbn/1540974072103.jpg";
//
//        String a = "321dfm54lod846579985e1ss2135cvbn" + url.split("321dfm54lod846579985e1ss2135cvbn")[1];
//
//        System.out.println(a);
//        System.out.println(Tools.formatTime("1540282671"));
//
//        String up_time = "2018-10-18 上午 04:57:57";
//        String down_time = "2018-10-18 下午 04:57:57";
//
//
//        down_time = down_time.replaceAll("/", "-");//接口可能返回（2018-10-18 下午 04:57:57）
//        down_time = Tools.parseTime(down_time);
//        down_time =  Tools2.date2Str(Tools2.str2Date(down_time));
//
//        System.out.println(down_time);

//        String html = "<p>d的<br></p><section class=\"_wxbEditor\"><section class=\"RankEditor\" style=\"font-variant-numeric: normal; font-variant-east-asian: normal; line-height: 25.6px; font-family: \" microsoft=\"\" border:=\"\" 0px=\"\" white-space:=\"\" max-width:=\"\" widows:=\"\" font-stretch:=\"\"></section><section class=\"RankEditor active\" style=\"font-variant-numeric: normal; font-variant-east-asian: normal; line-height: normal; font-family: \" microsoft=\"\" border:=\"\" 0px=\"\" white-space:=\"\" position:=\"\" max-width:=\"\" widows:=\"\" font-stretch:=\"\"><section style=\"margin-top: 10px; margin-bottom: 10px; position: static; max-width: 100%;\"><section style=\"margin-top: 1.5em; margin-right: 0%; margin-left: 0%; padding-right: 0%; padding-left: 0%; width: 100%; max-width: 100%;\" data-width=\"100%\"><section style=\"margin: auto; padding-right: 0%; padding-left: 0%; width: 80%; text-align: right; position: static; max-width: 100%;\" data-width=\"80%\"><img style=\"margin-top: -0.5em; margin-left: -0.5em; width: 3.5em; vertical-align: middle; position: static;transform: rotateZ(-0.1deg);-webkit-transform: rotateZ(-0.1deg);-moz-transform: rotateZ(-0.1deg);-o-transform: rotateZ(-0.1deg);\" src=\"https://www.wkbar.cc/uploadFiles/uploadCatchImgs/0e25b7bd3c38483cb3ea34c2ae52602b/1541997220661095275.png\"><section style=\"margin-top: -1.8em; padding-bottom: 10px; text-align: center; max-width: 100%;\"><section style=\"padding-right: 1em; position: static; max-width: 100%;\"><img width=\"100%\" height=\"\" title=\"1453102656584060071.jpg\" style=\"margin-right: 0%; margin-left: 0%; padding-right: 0%; padding-left: 0%; border: 10px solid white; width: 100%; vertical-align: middle; box-shadow: rgb(102, 102, 102) 2px 2px 5px;\" alt=\"08f790529822720ea7d145b779cb0a46f21fab64.jpg\" src=\"https://www.wkbar.cc/uploadFiles/uploadCatchImgs/0e25b7bd3c38483cb3ea34c2ae52602b/1541997220663068350.jpg\" border=\"0\" data-width=\"100%\" mapurl=\"\" opacity=\"\"></section></section></section><section style=\"padding: 10px; max-width: 100%;\"><p style=\"margin-top: 5px; margin-bottom: 5px; text-align: center; max-width: 100%;\"><span class=\"active brush\" style=\"text-align: justify; color: #797979; line-height: 24px; font-family: \" microsoft=\"\" font-size:=\"\" max-width:=\"\">人世间有太多的情感与归宿不能把握，构成了命运的不确定与爱情的不可求..</span></p></section></section></section></section></section><section class=\"_wxbEditor\"><section style=\"border: 0px none;\"><section style=\"width: 100%;background-image: url(https://www.wkbar.cc/uploadFiles/uploadCatchImgs/0e25b7bd3c38483cb3ea34c2ae52602b/1542004942066096868.png);background-size: contain; display: flex;display: -webkit-flex;background-repeat: no-repeat;\"><section style=\"width: 70%;padding: 30px 15px 10px;font-size: 14px;color: #399dfb;line-height: 25px;\"><p>摩羯座是象征着冬天开始的星座。冬天把“绝对意识”毫无保留地奉献给了摩羯座出生的人。摩羯座的人，尤其当有几个行星同时处于你的星座时，你将是一个具有现实主义思想和有抱负的人.</p><p><span style=\"font-size: 16px\">幸运数字：</span>8 16 26 35</p></section><section style=\"width: 30%\"><section style=\"width: 90%; margin-right: auto; margin-left: auto;\"><img src=\"https://www.wkbar.cc/uploadFiles/uploadCatchImgs/0e25b7bd3c38483cb3ea34c2ae52602b/1542004942066069264.png\" style=\"width: 100%;display: block;\"></section><section style=\"width: 88%; border:1px dashed #399dfb;padding: 3px;margin-top: 50px\"><section style=\"background-color: rgb(57, 157, 251); padding-right: 7px; padding-left: 7px; text-align: center; color: rgb(254, 254, 254);\"><p>12月22日</p><p>~</p><p>1月19日</p></section></section></section></section></section></section><p><br></p>";
//
//        System.out.println(html.length());
//
//        List<String> picList = SrcUtils.ImgSrc(html);
//
//        for (int i = 0; i < picList.size(); i++) {
//            System.out.println(picList.get(i));
//        }

//        String token = "17_tKCTOgwPWJx8scI9owl610sECn5nhPqe_9dGm8lXmb83xvUvaJRQO5g5hb618mmjIPvJf2MKiSSF1X5Up3ARxFLEZloBYuwU2MzC4cbl4q5jQFe8torX7e9dDWi4jlIsQ13FZtCrIb7av1-QMQFfADDUYN";
//        String code = "397074710973";
//        String cardId = "plRqb1d-zy44nbJHExotsjyeFc90";
//        WechatCardUtil.queryCard(token, code, cardId);

//        String token = "18_9yHn_lMTC-So7O3VdTP3MLeWHa8jebE38GfMY__TvLtH_jhMJty6NyA4l4ZS4h9xj_-96anjjqPC4qhiF6YVixFBoJ9bXbUg3sG_Ay-pnG_D_LWPn8gxzhdEvGJFqAweUEPlF9Nq2EJIXmlNNIBeAHDHCV";
//        WechatMessageUtil.createTag(token, "测试");
        //"tag":{"id":100,"name":"测试"}

//        List<String> list = new ArrayList<>();
//        list.add("otlSPvysBUvymy5QFva9oIEgoH6k");
//        WechatMessageUtil.setTag(token, "100", list);


//        String str = "2019-4-6 星期六   5:30:48";
//        String[] arr = str.split(" ");
//        String up_time = arr[0] + " " + arr[arr.length - 1];
//
//        System.out.println(Tools.parseTime(str));

//        String str = "{keyword1:1,keyword2:2,keyword3:3}";
//        JSONObject keywordJSON = JSONObject.fromObject("");

//        String str = "{\"card_id\":\"330724199405075614\",\"id_card\":\"330724199405075614\",\"id_type\":1,\"is_create_bar\":0,\"member_level\":\"默认会员等级\",\"overage\":1.0,\"phone_number\":\"18222956710\",\"reward\":0.0,\"usable_integral\":1,\"user_name\":\"马越强\",\"user_password\":\"IwAAAN36xOnftQ==\"}";
//        SWUser swUser = (SWUser) JSONObject.toBean(JSONObject.fromObject(str), SWUser.class);
//        System.out.println(swUser.toString());

//        SWQuery query = new SWQuery();
//        query.setField_value("330724199405075614");
//        query.setField_type(1);
//        System.out.println(JSONObject.fromObject(query).toString());

//        PageData pdUser = new PageData();
//        pdUser.put("name", "2sdfasd");
//
//        JSONObject result = new JSONObject();
//        result.put("pdUser", pdUser);
//
//        PageData pdUser2 = (PageData) JSONObject.toBean(result.getJSONObject("pdUser"), PageData.class);
//
//        System.out.println(pdUser2.toString());

//        PageData pd = new PageData();
//        pd.put("recharge_money", 299);
//        pd.put("reward_money", 299);
//
//        if(pd.get("recharge_money")==Integer.valueOf("299") && pd.get("reward_money")==Integer.valueOf("299")){
//            System.out.println("11");
//        }
//        if(pd.get("recharge_money").toString().equals("299") && pd.get("reward_money").toString().equals("299")){
//            System.out.println("33");
//        }
//
//        System.out.println("22");


//        String str = "{\"card_id\":\"330724199405075614\",\"id_card\":\"330724199405075614\",\"id_type\":1,\"is_create_bar\":0,\"member_level\":\"默认会员等级\",\"overage\":1.0,\"phone_number\":\"18222956710\",\"reward\":0.0,\"usable_integral\":1,\"user_name\":\"马越强\",\"user_password\":\"IwAAAN36xOnftQ==\"}";
//
//        ShopInfo shopInfo = (ShopInfo) JSONObject.toBean(JSONObject.fromObject(str), ShopInfo.class);
//
//        System.out.println(shopInfo.toString());


//        String pay_actualbalance = "500.23";
//
//        int f = (int) (Float.parseFloat(pay_actualbalance) * 100);
//        System.out.println(String.valueOf(f));


//        String str = "{result=success, code=R0001, orderNo=wj15574719343761547, payMoney=10, payType=2, channelNo=8001155720190510150534102941, sign=5f43065ead018ea117baf4c60ce7d228b0dcc062, orderStatus=1, remark=揽客充值：充10元，送0.0元。, shopNo=1535559855663, shopOrderNo=4432201905101505096780562713, completeDate=2019-05-10 15:05:40}";
//        JSONObject.fromObject(str);

//        String str = "密码不正确，请重试！";
//        if(str.equals("您输入的卡号密码不正确") || str.contains("密码不正确")){
//            System.out.println("111111111111111");
//        }



//        String time = Tools.date2Str(Tools.sAddDays(new Date(), 365), "yyyy-MM-dd");
//
//        System.out.println(time);
//
//        int month = Math.round(Float.parseFloat("1"));
//
//        System.out.println(month);


//        LongStream ls = LongStream.rangeClosed(2L, 10L);
//        long[] lsA = ls.toArray();
//        for (long l : lsA) {
//            System.out.println(l);
//        }

        //获取授权方的信息
//        WeChatOpenUtil.getAuthorizerInfo("wx7d71780213f70683"
//                , "wxd85e827a431d379a"
//                ,"22_ff0Ajtod4ayKpdyI1rvLF0lDvszGY7CJcQyjIDPDWDYPoJFsnZwMQS_e-JHvyke0-2baOnFa8Etdv7NHUll5kmKoHgaRl1aGYhcbTaljK8fhpKJdwoARhsXOQXSvkQ7VdRMZkXr7DKujfKW8TJEbAIADTO"
//                );

        //获取授权的口令信息
//        JSONObject terrace = WeChatOpenUtil.getAuthorizeRefresh(
//                "wx7d71780213f70683"
//                , "wxd85e827a431d379a",
//                "22_jDeL8I6aReLoI_bt48m7USavEtept_olW8C3A36rshSrj7fng_41MCBimewBcpJJEv5ida1Rc14LNd1VAAnEL7gImycGganU79Xqt_iB85cXNCKBR46vUbbxq2Ondkh11GUj3VrR-bcTFRoQRTPfAAAJLH"
//                ,"refreshtoken@@@1b8GvIvl3RrkZk1xj0u2XrLXG4Bm9z5gWUgUQMZvW48");


    }


    public static Date getNextTime(int handsel_time, int handsel_times, Date date) {
        if(handsel_times == 0) {//天
            date = Tools.sAddDays(date, handsel_time);
        }else if(handsel_times == 1) {//周
            date = Tools.sAddDays(date, handsel_time * 7);
        }else if(handsel_times == 2) {//月
            date = Tools.sAddMonth(date, handsel_time);
        }else if(handsel_times == 3) {//年
            date = Tools.sAddYear(date, handsel_time);
        }
        return date;
    }

 	
 	
}
