let API_ROOT
if (process.env.NODE_ENV === 'development') {
  API_ROOT = 'https://t.baochequan.cn/saas'
} else {
  // API_ROOT = 'http://newapi.baochequan.cn/saas' 
  API_ROOT = 'http://saaspc.baochequan.cn/saas'  
  // API_ROOT = 'https://t.baochequan.cn/saas'
}

const root = API_ROOT
const noSaasRoot =
  process.env.NODE_ENV === 'development'
    ? 'https://t.baochequan.cn':
     'http://saaspc.baochequan.cn'
    // 'https://t.baochequan.cn'

let httpUrl = {
  // 检测更新
  detectionUpdate: noSaasRoot + `/system/saas/windows/version`, // 获取手机验证码
  // 下载地址
  download: noSaasRoot + '/system/saas/app/download', // app下载地址
  uploadImage: noSaasRoot + `/upload/image`, // 调用图片上传接口（pc）
  // 登录
  code: noSaasRoot + `/auth/code`, // 获取手机验证码
  logout: noSaasRoot + `/auth/logout`, // 获取手机验证码
  login: root + `/login`, // 登录
  register: root + `/register`, // 注册
  resetPassword: noSaasRoot + `/user/save/password`, // 修改密码
  complete: root + `/complete`, // 登陆后填写信息
  userInfo: noSaasRoot + `/user/profile`,
  resetTel1: noSaasRoot + `/user/check/mobile`, // 修改手机号(1)
  resetTel2: noSaasRoot + `/user/update/mobile`, // 修改手机号(2)
  // 车辆管理
  carList: root + `/car/list`, // 车辆列表
  carSave: root + `/car/save`, // 新增/修改车辆
  allfleetList: root + `/car/fleet/list`, // 获取外援车队列表
  fleetCarList: root + `/fleet/car/list`, // 获取某一外援车队车辆列表
  carInfo: root + `/car/info`, // 车辆详情
  deleteCar: root + `/car/delete`, // 删除车辆
  searchFleetList: root + `/keywords/fleet`, // 关键词查询车队
  searchDriverList: root + `/keywords/driver`, // 关键词查询车队
  carCount: root + `/car/count`, // 获取车辆个数
  downloadCarList: root + `/car/list/download`, // 导出车辆列表
  postExcelCar: noSaasRoot +`/import/car`,//导入车辆excel表格
  // 通讯录
  customerList: root + `/customer/list/pc`, // 客户列表
  customerSave: root + `/customer/save`, // 新增/修改客户
  deleteCustomer: root + `/customer/delete`, // 删除客户
  customerInfo: root + `/customer/info`, // 客户详情
  customerTop: root + `/customer/top`, // 置顶客户
  downloadCustomerList: root + `/customer/list/download`, // 导出客户列表
  yardmenList: root + `/yardmen/list`, // 获取某一客户的计调列表
  yardmenListDownload: root + `/yardman/list/download`, // 导出客户计调列表
  yardmenSave: root + `/yardman/save`, // 修改计调信息
  yardmenUpdate: root + `/fleet/yardman/update`, // 计调信息修改
  yardmenDelete: root + `/yardman/delete`, // 删除计调
  driverList: root + `/driver/list/pc`, // 司机列表
  driverSave: root + `/driver/save`, // 新增/修改司机
  deleteDriver: root + `/driver/delete`, // 删除司机
  driverInfo: root + `/driver/info`, // 司机详情
  downloadDriverList: root + `/driver/list/download`, // 导出司机列表
  searchCarList: root + `/keywords/car`, // 关键词查询车辆
  // 帐单
  billList: root + `/customer/bill/list`, // 帐单列表
  billModifyMoney: root + `/customer/bill/modify/money`, // 帐单金额修改
  billModifyStatus: root + `/customer/bill/modify/status`, // 帐单结款
  billMonthMoney: root + `/customer/money/month`, // 月份帐单金额
  orderList: root + `/order/list`, // 帐单详情，订单列表
  downloadBillList: root + `/customer/bill/list/download`, // 导出帐单列表
  downloadALLBillList: root + `/customers/bill/list/download`, // 导出帐单列表

  // 排班 发单
  scheduleList: root + `/schedule/list`, // 调度
  shuttleBusList:root+'/schedule/bus/list',//班车发单选择车辆
  orderDetail: root + `/order/detail`, // 订单详情
  orderCancel: root + `/order/cancel`, // 取消订单
  orderFinish: root + `/order/finish`, // 结束订单
  scheduleDay: root + `/schedule/day`, // 获取车辆某天的排班信息
  scheduleMonth: root + `/schedule/month`, // 获取车辆月度排班表
  orderSave: root + `/order/save`, // 发布/修改订单任务
  carDays: root + `/schedule/split/car/days`, // 发布任务筛选车辆信息
  busyChange: root + `/busy/change`, // 手动标记忙闲状态

  // 文本框自动搜索查询
  driverListPC: root + `/driver/list/pc`, // 获取司机列表PC
  customerListPC: root + `/customer/list/pc`, // 获取客户列表PC
  keywordsYardman: root + `/keywords/yardman`, // 计调员关键词
  keywordsDriver: root + `/keywords/driver`, // 司机关键词
  // 接单管理 
  receiveList: root + `/receive/list`, // 帐单列表
  deleteReceiveOrder: root + `/receive/order/delete`, // 删除旅行社订单
  ordereReject: root + `/receive/order/reject`, // 拒绝接单
  smartSchedule: root + `/smart/schedule`, // 智能排班
  orderListDownload: root + `/receiver/order/list/download`, // 导出接单列表
  changeReadyOrder:root + `/receive/backlog`, //未排班的转为待办事项
  // 首页管理
  getMotorcadeInfo: root + `/info`, // 获取车队个人资料
  saveMotorcadeInfo: root + `/save/info`, // 保存预览车队信息
  // 财务
  moneyIndex: root + `/money/index`, //财务各类统计数据
  // 营收
  customerBillList: root + `/customer/bill/list`, // 按客户查看
  timeBillList: root + `/customer/bill/list/time`, // 按时间查看
  customerBill: root + `/customer/bill/list/customer`, // 某个客户的账单详情
  // 报表
  profitDay: root + `/profit/day`, // 日常报表
  statement: root + `/statement`, // 新报表
  
  profitPay: root + `/profit/pay`, // 支出明细
  profitPayDetail: root + `/profit/payDetail`, // 支出详情
  // 利润
  profitIndex: root + `/profit/index`, // 利润列表
  profitListDownload: root + `/profit/list/download`, // 利润导出账单
  // 外援结算
  foreignList: root + `/foreign/list`, // 外援结算列表
  foreignDetail: root + `/foreign/detail`, // 外援车队详情列表
  foreignBalance: root + `/foreign/balance`, // 结算
  foreignListDownload: root + `/foreign/list/download`, // 导出外援结算账单
  // 支出
  feeQuery: root + `/fee/query`, // 支出列表
  feeAudit: root + `/fee/audit`, // 费用审批
  feeDetail: root + `/fee/detail`, // 费用详情
  feeSave: root + `/fee/save`, // 新建/修改费用上报
  payListDownload: root + `/pay/list/download`, // 支出导出账单

  // web官网
  articleList: root + `/article/list`, // 获取文章列表
  articleDetail: root + `/article/detail`, // 获取文章列表
  // 待办事项
  backlogSave: root + `/backlog/save`, // 添加/修改待办事项
  backlogList: root + `/backlog/list`, // 获取文章列表
  backlogDelete: root + `/backlog/delete`, // 删除待办事项
  getcarInfo:root+'/car/type',//车辆信息
  readyDetails:root+'/backlog/detail',//代办事项详情
  // 车队计调
  fleetYardmanList: root + `/fleet/yardman/list`, // 计调列表
  deleteFleetYardman: root + `/fleet/yardman/delete`, // 删除计调
  addFleetYardman: root + `/fleet/yardman/add`, // 删除计调
  homeCount: root + `/home/count`, // 删除计调
  // 车队管理
  selfOrderList: root + `/self/order/list`, // 车队自主下单列表
  selfCarTitles: root + `/self/car/titles`, // 获取所有车型
  selfConfig: root + `/self/config`, // 获取配置
  selfSaveConfig: root + `/self/config/save`, // 获取配置
  selfShuttleConfig: root + `/self/shuttle/config`, // 获取获取接送机/站相关配置
  selfSaveShuttleConfig: root + `/self/shuttle/config/save`, // 获取获取接送机/站相关配置
  airportList: noSaasRoot + `/self/airport/list`, // 机场数据查询
  trainList: noSaasRoot + `/self/train/list`, // 机场数据查询
  selfShuttleStation: root + `/self/shuttle/station`, // 接送机配置选择站点时判断站点是否已配置
  selfShuttleConfigDelete: root + `/self/shuttle/config/delete`, // 接送机配置选择站点时判断站点是否已配置
  selfOrderStatus: root + `/self/order/status`, // 接送机配置选择站点时判断站点是否已配置
  bankList: noSaasRoot + `/bank/list`, // 银行列表
  withdrawBalance: root + `/withdraw/balance`, // 车队余额信息
  withdrawSave: root + `/withdraw/save`, // 申请提现
  // 计调操作记录
  logList: root + `/log/list`, // 订单列表记录
  logOrderList: root + `/log/order/list`, // 某一订单操作详情记录
  // 业务员
  salesmanList: root + `/salesman/list`, // 业务员列表
  salesmanSave: root + `/salesman/save`, // 增加/修改业务员
  salesmanDetail: root + `/salesman/detail`, // 业务员详情
  salesmanDelete: root + `/salesman/delete`, // 删除业务员
  salesmanDownload: root + `/salesman/download`, // 业务员列表导出
  salesmanOrderDownload: root + `/salesman/order/download`, // 业务员列表导出
  //通讯录-上传图片
  photoList: root + `/file/list`, // 图片列表
  uploadPhoto:root+ '/file/save/more',//提交图片
  deleteIma:root+ '/file/delete',//删除图片
  // 投诉列表管理
  getComplaintList:root+'/complaint/tour/list',//投诉列表
  complaintDetails:root+'/complaint/tour/detail',//投诉详情
  replayComplaint:root+'/complaint/reply',//投诉回复
  setComplaintRead:root+'/complaint/read',//设为已读
  //GPS
  gpshistory:root+'/gps/history', //行程轨迹
  gpsTracking:root+'/gps/tracking', //行程轨迹
  // 自定义字段
  addCustomFie:root+'/field/save', //添加修改自定义字段
  customFiledList:root+'/field/list', //自定义列表
  deleteCustom:root+'/field/delete',//删除自定义
  customDetails:root+'/field/detail',//自定义详情
}

export default httpUrl
