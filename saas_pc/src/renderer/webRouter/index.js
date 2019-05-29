import Vue from 'vue'
import Router from 'vue-router'
import store from '../store'
// 首页
import indexOrigin from '@/pages/index/indexOrigin' // 官网入口页
import index from '@/pages/index/index' // 官网首页
import help from '@/pages/index/help' // 帮助中心
import aboutUs from '@/pages/index/aboutUs' // 关于我们
import dynamicList from '@/pages/index/dynamicList' // 动态列表
import dynamicDetail from '@/pages/index/dynamicDetail' // 动态详情
// 财务
import finance from '@/pages/finance/finance' // 财务管理入口页
import revenue from '@/pages/finance/revenue' // 营收
import revenueDetail from '@/pages/finance/revenueDetail'
import reportForm from '@/pages/finance/reportForm' // 报表
// import reportFormDetail from '@/pages/finance/reportFormDetail' // 报表详情
import profits from '@/pages/finance/profits' // 利润
import spending from '@/pages/finance/spending' // 支出
import AddSpending from '@/pages/finance/AddSpending' // 新增车辆支出
import AddOtherSpending from '@/pages/finance/AddOtherSpending' // 新增其他支出
import SpendingDetail from '@/pages/finance/SpendingDetail' // 支出详情
import OtherSpendingDetail from '@/pages/finance/OtherSpendingDetail' // 支出详情
import SpendingOrder from '@/pages/finance/SpendingOrder' // 新增支出选择订单
import SpendingCar from '@/pages/finance/SpendingCar' // 新增支出选择车辆列表
import SpendingCarDetails from '@/pages/finance/SpendingCarDetails' // 新增支出选择车辆列表
import outSettlement from '@/pages/finance/outSettlement' // 营收
import OutSettlementDetail from '@/pages/finance/OutSettlementDetail' // 营收详情
// 调度
import schedule from '@/pages/schedule/schedule' // 调度首页
import arrange from '@/pages/schedule/arrange' // 调度排班
import mission from '@/pages/schedule/mission' // 调度任务
import addMission from '@/pages/schedule/addMission' // 添加任务
import carArrange from '@/pages/schedule/carArrange' // 添加车辆排班
import arrangeDetails from '@/pages/schedule/arrangeDetails' // 车辆所有任务
import outcar from '@/pages/schedule/outcar' // 调度外援页面

// 通讯录
import contacts from '@/pages/contacts/contacts' // 通讯录首页
import customerOrigin from '@/pages/contacts/customerOrigin' // 通讯录客户中心
import customerDetails from '@/pages/contacts/customerDetails' // 通讯录客户详情
import imageDetails from '@/pages/contacts/imageDetails' //单个旅行社图片详情
import allImages from '@/pages/contacts/allImages' //全部图片
import addImage from '@/pages/contacts/addImage' //上传图片
import increaseCustomer from '@/pages/contacts/increaseCustomer' // 通讯录新增客户
import driverOrigin from '@/pages/contacts/driverOrigin' // 通讯录司机中心
import driverDetails from '@/pages/contacts/driverDetails' // 通讯录司机详情
import IncreaseOperator from '@/pages/contacts/IncreaseOperator' // 通讯录司机详情 
import increaseDriver from '@/pages/contacts/increaseDriver' // 通讯录新增计调
import IncreaseOutsideCar from '@/pages/contacts/IncreaseOutsideCar' // 通讯录新增外援车辆
import operatorOrigin from '@/pages/contacts/operatorOrigin' // 通讯录新增外援车辆
import modifyYardman from '@/pages/contacts/modifyYardman' // 增加车调
// 车辆管理
import carManage from '@/pages/carManage/carManage' // 车辆管理首页
import carOrigin from '@/pages/carManage/carOrigin' // 车辆管理中心
import increaseCar from '@/pages/carManage/increaseCar' // 车辆管理新增司机
import carDetails from '@/pages/carManage/carDetails'
import vehicleTrajectory from '@/pages/carManage/vehicleTrajectory'
import monitor from '@/pages/carManage/monitor'
// 账单
import account from '@/pages/account/account' // 账单
import accountOrigin from '@/pages/account/accountOrigin' // 账单首页
import companyAccount from '@/pages/account/companyAccount' // 公司账单
import accountDetails from '@/pages/account/accountDetails' // 账单详情
import shuttleBusDetail from '@/pages/account/shuttleBusDetail' // 包车账单详情
import TransportStationDetail from '@/pages/account/TransportStationDetail' // 接送站账单详情
// 接单助手
import fleet from '@/pages/fleet/fleet' // 车辆管理
import fleetManagement from '@/pages/fleet/fleetManagement' // 车辆管理
import FleetCarDetail from '@/pages/fleet/FleetCarDetail' // 车型详情
import fleetFinance from '@/pages/fleet/fleetFinance' // 财务
import fleetOrderList from '@/pages/fleet/fleetOrderList' // 订单列表
// 接单管理
import orderManagement from '@/pages/orderManagement/orderManagement' // 上传接单管理
import complaintDetails from '@/pages/orderManagement/complaintDetails' //投诉详情
// 车队信息
import motorcadeInfo from '@/pages/motorcade/motorcadeInfo' // 上传车队信息
// 备忘录 
import memo from '@/pages/memo/memo' //备忘录
//日志
import logOrigin from '@/pages/log/logOrigin' //日志初始页
import logList from '@/pages/log/logList' // 日志列表
import logDetail from '@/pages/log/logDetail' // 日志详情
// 业务员 
import salesmanOrigin from '@/pages/salesman/salesmanOrigin' // 业务员列表
import salesmanList from '@/pages/salesman/salesmanList' // 业务员列表
import addSalesman from '@/pages/salesman/addSalesman' // 增加业务员   
import salesmanOrder from '@/pages/salesman/salesmanOrder' // 业务员相关订单
// 设置
import set from '@/pages/set/set' // 设置
// 
import companyInfo from '@/pages/login/webCompanyInfo' // 填写公司信息
import login from '@/pages/login/webLogin' // web登录
import accountManagement from '@/pages/login/WebAccountManagement' // 账号注册 忘记密码
// 自定义设置
import customSet from '@/pages/customSet/customSet'
import addCustom from '@/pages/customSet/addCustom'
Vue.use(Router)
const router = new Router({
  routes: [{
      path: '/',
      redirect: {
        name: 'index'
      }
    },
    {
      path: '/salesman',
      component: salesmanOrigin,
      meta: {
        requiresAuth: true
      },
      children: [{
          path: '/',
          name: 'salesman',
          redirect: {
            name: 'salesmanList'
          }
        },
        {
          path: 'list',
          name: 'salesmanList',
          component: salesmanList
        },
        {
          path: 'add',
          name: 'addSalesman',
          component: addSalesman
        },
        {
          path: 'order',
          name: 'salesmanOrder',
          component: salesmanOrder
        }
      ]
    },
    {
      path: '/logOrigin',
      component: logOrigin,
      meta: {
        requiresAuth: true
      },
      children: [{
          path: '/',
          name: 'logOrigin',
          redirect: {
            name: 'logList'
          }
        },
        {
          path: 'logList',
          name: 'logList',
          component: logList
        },
        {
          path: 'logDetail',
          name: 'logDetail',
          component: logDetail
        }
      ]
    },
    {
      path: '/fleet',
      component: fleet,
      meta: {
        requiresAuth: true
      },
      children: [{
          path: '/',
          name: 'fleet',
          redirect: {
            name: 'fleetManagement'
          }
        },
        {
          path: 'management',
          name: 'fleetManagement',
          component: fleetManagement
        },
        {
          path: 'finance',
          name: 'fleetFinance',
          component: fleetFinance
        },
        {
          path: 'orderList',
          name: 'fleetOrderList',
          component: fleetOrderList
        },
        {
          path: 'carDetail',
          name: 'fleetCarDetail',
          component: FleetCarDetail
        }
      ]
    },
    {
      path: '/finance',
      component: finance,
      meta: {
        requiresAuth: true
      },
      children: [{
          path: '/',
          name: 'finance',
          redirect: {
            name: 'revenue'
          }
        },
        {
          path: 'revenue',
          name: 'revenue',
          component: revenue
        },
        {
          path: 'revenueDetail',
          name: 'revenueDetail',
          component: revenueDetail
        },
        {
          path: 'reportForm',
          name: 'reportForm',
          component: reportForm
        },
        {
          path: 'OtherSpendingDetail',
          name: 'OtherSpendingDetail',
          component: OtherSpendingDetail
        },
        {
          path: 'SpendingDetail',
          name: 'SpendingDetail',
          component: SpendingDetail
        },
        {
          path: 'profits',
          name: 'profits',
          component: profits
        },
        {
          path: 'spending',
          name: 'spending',
          component: spending
        },
        {
          path: 'AddOtherSpending',
          name: 'AddOtherSpending',
          component: AddOtherSpending
        },
        {
          path: 'AddSpending',
          name: 'AddSpending',
          component: AddSpending
        },
        {
          path: 'outSettlement',
          name: 'outSettlement',
          component: outSettlement
        },
        {
          path: 'SpendingCarDetails',
          name: 'SpendingCarDetails',
          component: SpendingCarDetails
        },
        {
          path: 'OutSettlementDetail',
          name: 'OutSettlementDetail',
          component: OutSettlementDetail
        }
      ]
    },
    {
      path: '/index',
      component: indexOrigin,
      children: [{
          path: '/',
          name: 'indexOrigin',
          redirect: {
            name: 'index'
          }
        },
        {
          path: 'index',
          name: 'index',
          component: index
        },
        {
          path: 'aboutUs',
          name: 'aboutUs',
          component: aboutUs
        },
        {
          path: 'help',
          name: 'help',
          component: help
        },
        {
          path: 'dynamicList',
          name: 'dynamicList',
          component: dynamicList
        },
        {
          path: 'dynamicDetail',
          name: 'dynamicDetail',
          component: dynamicDetail
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/accountManagement',
      name: 'accountManagement',
      component: accountManagement
    },
    {
      path: '/companyInfo',
      name: 'companyInfo',
      component: companyInfo,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/memo',
      name: 'memo',
      component: memo,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/motorcadeInfo',
      name: 'motorcadeInfo',
      component: motorcadeInfo,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/orderManagement',
      name: 'orderManagement',
      component: orderManagement,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/complaintDetails',
      name: 'complaintDetails',
      component: complaintDetails,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/schedule',
      component: schedule,
      meta: {
        requiresAuth: true
      },
      children: [{
          path: '/',
          name: 'schedule',
          redirect: {
            name: 'arrange'
          }
        },
        {
          path: 'outcar',
          name: 'outcar',
          component: outcar
        },
        {
          path: 'arrange',
          name: 'arrange',
          component: arrange
        },
        {
          path: 'mission',
          name: 'mission',
          component: mission
        },
        {
          path: 'SpendingOrder',
          name: 'SpendingOrder',
          component: SpendingOrder
        },
        {
          path: 'carArrange',
          name: 'carArrange',
          component: carArrange
        },
        {
          path: 'addMission',
          name: 'addMission',
          component: addMission
        },
        {
          path: 'arrangeDetails',
          name: 'arrangeDetails',
          component: arrangeDetails
        }
      ]
    },
    {
      path: '/contacts',
      component: contacts,
      meta: {
        requiresAuth: true
      },
      children: [{
          path: '/',
          name: 'contacts',
          redirect: {
            name: 'customerOrigin'
          }
        },
        {
          path: 'customerOrigin',
          name: 'customerOrigin',
          component: customerOrigin
        },
        {
          path: 'operatorOrigin',
          name: 'operatorOrigin',
          component: operatorOrigin
        },
        {
          path: 'imageDetails',
          name: 'imageDetails',
          component: imageDetails
        },
        {
          path: 'allImages',
          name: 'allImages',
          component: allImages
        },
        {
          path: 'addImage',
          name: 'addImage',
          component: addImage
        },
        {
          path: 'customerDetails',
          name: 'customerDetails',
          component: customerDetails
        },
        {
          path: 'increaseCustomer',
          name: 'increaseCustomer',
          component: increaseCustomer
        },
        {
          path: 'driverOrigin',
          name: 'driverOrigin',
          component: driverOrigin
        },
        {
          path: 'driverDetails',
          name: 'driverDetails',
          component: driverDetails
        },
        {
          path: 'increaseDriver',
          name: 'increaseDriver',
          component: increaseDriver
        },
        {
          path: 'IncreaseOperator',
          name: 'IncreaseOperator',
          component: IncreaseOperator
        },
        {
          path: 'IncreaseOutsideCar',
          name: 'IncreaseOutsideCar',
          component: IncreaseOutsideCar
        },
        {
          path: 'modifyYardman',
          name: 'modifyYardman',
          component: modifyYardman
        },
      ]
    },
    {
      path: '/carManage',
      component: carManage,
      meta: {
        requiresAuth: true
      },
      children: [{
          path: '/',
          name: 'carManage',
          redirect: {
            name: 'carOrigin',
            query: {
              type: 1
            }
          }
        },
        {
          path: 'carOrigin',
          name: 'carOrigin',
          component: carOrigin
        },
        {
          path: 'increaseCar',
          name: 'increaseCar',
          component: increaseCar
        },
        {
          path: 'SpendingCar',
          name: 'SpendingCar',
          component: SpendingCar
        },
        {
          path: 'carDetails',
          name: 'carDetails',
          component: carDetails
        },
        {
          path: 'vehicleTrajectory',
          name: 'vehicleTrajectory',
          component: vehicleTrajectory
        },
        {
          path: 'monitor',
          name: 'monitor',
          component: monitor
        }
      ]
    },
    {
      path: '/account',
      component: account,
      meta: {
        requiresAuth: true
      },
      children: [{
          path: '/',
          name: 'account',
          redirect: {
            name: 'accountOrigin'
          }
        },
        {
          path: 'accountOrigin',
          name: 'accountOrigin',
          component: accountOrigin
        },
        {
          path: 'companyAccount',
          name: 'companyAccount',
          component: companyAccount
        },
        {
          path: 'accountDetails',
          name: 'accountDetails',
          component: accountDetails
        },
        {
          path: 'TransportStationDetail',
          name: 'TransportStationDetail',
          component: TransportStationDetail
        },
        {
          path: 'shuttleBusDetail',
          name: 'shuttleBusDetail',
          component: shuttleBusDetail
        }
      ]
    },
    {
      path: '/set',
      component: set,
      meta: {
        requiresAuth: true
      },
      children: [{
        path: '/',
        name: 'set'
      }]
    },
    // 自定义设置
    {
      path: '/customSet',
      name: 'customSet',
      component: customSet,
      meta: {
        requiresAuth: true
      },
    },
    {
      path: '/addCustom',
      name: 'addCustom',
      component: addCustom,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '*',
      redirect: '/'
    }
  ],
  scrollBehavior(to, from, savedPosition) {
    return new Promise((resolve, reject) => {
      resolve({
        x: 0,
        y: 0
      })
    })
  }
})
// authIds: role_id 允许的role_id数组
// path: 访问的路径
const PAGEARR = [{
  path: 'finance',
  authIds: [0, 1, 3]
}]
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    let token = localStorage.getItem('token')
    if (!token) {
      next({
        name: 'login'
      })
      return
    } else {
      if (!store.state.user.token) store.commit('refreshToken', token)
      PAGEARR.forEach((ele, index) => {
        let toPath = to.path
        if (toPath.includes(ele.path)) {
          let auth = identityAuth(ele.authIds)
          if (auth) {
            next()
          } else {
            next({
              name: from.name
            })
          }
          return
        } else {
          if (index === PAGEARR.length - 1) {
            next()
          }
        }
      })
    }
  } else {
    next()
  }
})

function identityAuth(authIds) {
  let auth = false
  let role_id = store.state.user.user && store.state.user.user.role_id
  if (!role_id && role_id !== 0) {
    store.dispatch('getUserInfo').then(val => {
      if (authIds.includes(val)) {
        auth = true
      }
    })
  } else if (authIds.includes(role_id)) {
    auth = true
  }
  return auth
}
export default router