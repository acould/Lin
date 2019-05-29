<template>
  <div class="revenue-container">
    <div class='main-header'>
      <div class="breadcrumb-wrap">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>财务管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'revenue'}">营收</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <financeOptions option="1" :isRefreshData.sync="isRefreshData"></financeOptions>
    <div class="main-content">
      <div class="conditions">
        <div class="tabs-wrap">
          <el-tabs v-model="activeName">
            <el-tab-pane label="按客户查看" name="1"></el-tab-pane>
            <el-tab-pane label="按时间查看" name="2"></el-tab-pane>
          </el-tabs>
        </div>
        <div class="search-box" v-show="activeName === '1'">
          <div class="search-wrap">
            <span class="iconfont icon-shujuchaxun iconfont-search"></span>
            <el-input class="search-inp" v-model="keywords1" placeholder="请输入用车单位名称"></el-input>
          </div>
          <a href="javascript:void(0)" class="btn-search" @click="searchKeywords">搜索</a>
        </div>
        <div class="search-box" v-show="activeName === '2'">
          <div class="search-wrap">
            <span class="iconfont icon-shujuchaxun iconfont-search"></span>
            <el-input class="search-inp" v-model="keywords2" placeholder="请输入车牌号/用车单位/司机等"></el-input>
          </div>
          <a href="javascript:void(0)" class="btn-search" @click="searchKeywords">搜索</a>
        </div>
        <div class="date-box" v-show="activeName === '2'">
          <div class="date-wrap">
            <el-date-picker class="el-date" v-model="timeParams.date" type="month" value-format="yyyy-MM" placeholder="选择年月">
            </el-date-picker>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="medium-line">
          <div class="tabs">
            <el-tabs v-model="customerParams.type" type="card" v-show="activeName === '1'">
              <el-tab-pane label="全部" name="0"></el-tab-pane>
              <el-tab-pane label="已结款" name="2"></el-tab-pane>
              <el-tab-pane label="未结款" name="1"></el-tab-pane>
            </el-tabs>
            <el-tabs v-model="timeParams.bill_status" type="card" v-show="activeName === '2'">
              <el-tab-pane label="全部" name="0"></el-tab-pane>
              <el-tab-pane label="已结款" name="2"></el-tab-pane>
              <el-tab-pane label="未结款" name="1"></el-tab-pane>
            </el-tabs>
          </div>
          <div class="money-wrap" v-show="activeName === '1' && customerMoney.total_order_money">
            <span class="money-lb">{{customerParams.type == 0 ? '全部：' : customerParams.type == 2 ? '已结款：': '未结款：'}}</span>
            <span class="money-txt all" v-show="customerParams.type == 0">{{customerMoney.total_order_money}}元</span>
            <span class="money-txt ok" v-show="customerParams.type == 2">{{customerMoney.has_pay_money}}元</span>
            <span class="money-txt fail" v-show="customerParams.type == 1">{{customerMoney.not_pay_money}}元</span>
          </div>
          <div class="money-wrap" v-show="activeName === '2' && timeMoney.total_order_money">
            <span class="money-lb">{{timeParams.bill_status == 0 ? '全部：' : timeParams.bill_status == 2 ? '已结款：': '未结款：'}}</span>
            <span class="money-txt all" v-show="timeParams.bill_status == 0">{{timeMoney.total_order_money}}元</span>
            <span class="money-txt ok" v-show="timeParams.bill_status == 2">{{timeMoney.has_pay_money}}元</span>
            <span class="money-txt fail" v-show="timeParams.bill_status == 1">{{timeMoney.not_pay_money}}元</span>
          </div>
          <router-link tag="div" class="link" :to="{name:'addMission'}">
            <span class="iconfont icon-close iconfont-close"></span>
            <span>新增账单</span>
          </router-link>
          <div class="link import" @click="downloadBillList" v-show="activeName === '2'">
            <span>全部导出</span>
          </div>
        </div>
        <!-- 客户筛选表格 -->
        <div class="table">
          <dataBlank v-if="activeName === '1' && !customerTableData.length && maxPageCustomer === 0" dataType="account"></dataBlank>
          <el-table v-if="customerTableData.length" :data="customerTableData" style="width: 100%" v-show="activeName === '1'">
            <el-table-column label="公司名称" width="220">
              <template slot-scope="scope">
                {{scope.row.company}}
                <div class="iconfont-wrap">
                  <el-popover popper-class="tel-tip" placement="top-start" width="150" trigger="hover">
                    {{scope.row.mobile}}
                    <span slot="reference" class="iconfont icon-bohao iconfont-tel"></span>
                  </el-popover>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="状态" width="110">
              <template slot-scope="scope">
                <p class="line">
                  <span class="dot dot-ok"></span>已结</p>
                <p class="line">
                  <span class="dot dot-wait"></span>未结</p>
              </template>
            </el-table-column>
            <el-table-column label="金额" width="120">
              <template slot-scope="scope">
                <p class="line">{{scope.row.has_pay_money}}元</p>
                <p class="line">{{scope.row.not_pay_money}}元</p>
              </template>
            </el-table-column>
            <el-table-column label="订单数" width="100">
              <template slot-scope="scope">
                <p class="line">{{scope.row.has_pay_order_num}}单</p>
                <p class="line">{{scope.row.not_pay_order_num}}单</p>
              </template>
            </el-table-column>
            <el-table-column label="月份" width="90">
              <template slot-scope="scope">
                <p class="line">{{scope.row.payed_month}}</p>
                <p class="line">{{scope.row.not_pay_month}}</p>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <div class="btn-wrap">
                  <router-link class="btn btn-nobg" :to="{name: 'revenueDetail',query:{companyName:scope.row.company,customer_id:scope.row.customer_id}}">查看详情</router-link>
                </div>
              </template>
            </el-table-column>
          </el-table>
          <!-- 时间筛选表格 -->
          <dataBlank v-if="activeName === '2' && !timeTableData.length && maxPageTime === 0" dataType="account"></dataBlank>
          <el-table v-if="timeTableData.length" ref="timeTable" class="timeTable" :class="timeParams.bill_status == 1 ? 'fix-bottom': ''" :data="timeTableData" @selection-change="handleSelectionChange" style="width: 100%" v-show="activeName === '2'">
            <el-table-column type="selection" key="selection" width="47" v-if="timeParams.bill_status == 1">
            </el-table-column>
            <el-table-column label="" width="47" key="noselection" v-else>
            </el-table-column>
            <el-table-column label="订单编号" width="110">
              <template slot-scope="scope">
                <div @click="jumpDetails(scope.row.order_type,scope.row.company,scope.row.customer_id,scope.row.order_id)"  class="order-id">
                  {{scope.row.order_num}}
                </div>
              </template>
            </el-table-column>
            <el-table-column label="营收" width="140">
              <template slot-scope="scope">
                <p>营收:<span>{{scope.row.money}}</span>元</p>
                
                <p>应收:<span :class="scope.row.receivable > 0  ? 'red' : scope.row.receivable < 0 ? 'green' : ''">{{scope.row.receivable}}</span:>元</p>
              </template>
            </el-table-column>
            <el-table-column prop="productId" label="时间" width="160">
              <template slot-scope="scope">
                <p class="line">开始:{{scope.row.travel_begin}}</p>
                <p class="line">结束:{{scope.row.travel_end}}</p>
              </template>
            </el-table-column>
            <el-table-column  label="行程" prop="travel_content">
            </el-table-column>
            <el-table-column label="计调姓名" width="100">
              <template slot-scope="scope">
                {{scope.row.customer_yardman_name}}
                <div class="iconfont-wrap">
                  <el-popover popper-class="tel-tip" placement="top-start" width="150" trigger="hover">
                    {{scope.row.customer_yardman_mobile}}
                    <span slot="reference" class="iconfont icon-bohao iconfont-tel"></span>
                  </el-popover>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="司机" width="115">
              <template slot-scope="scope">
                {{scope.row.car_info[0].driver_name}}
                <div class="iconfont-wrap">
                  <el-popover popper-class="tel-tip" placement="top-start" width="150" trigger="hover">
                    {{scope.row.car_info[0].driver_mobile}}
                    <span slot="reference" class="iconfont icon-bohao iconfont-tel"></span>
                  </el-popover>
                </div>
                <div class="iconfont-wrap">
                  <el-popover popper-class="table-tip" placement="left" width="320" trigger="click">
                    <div class="line-wrap">
                      <div class="line" v-for="(info, index) in scope.row.car_info" :key="index">
                        <span class="name">{{info.driver_name}}</span>
                        <span class="mobile">{{info.driver_mobile}}</span>
                        <span class="car-number">{{info.car_number}}</span>
                        <span v-if="info.car_source == 2" class="car-source out-car">外援</span>
                      </div>
                    </div>
                    <span slot="reference" class="iconfont icon-jiantou2 iconfont-arrow"></span>
                  </el-popover>
                </div>
                <p class="line">{{scope.row.car_info[0].car_number}}</p>
                <span v-if="scope.row.car_info[0].car_source == 2" class="car-source out-car">外援</span>
              </template>
            </el-table-column>
            <el-table-column label="状态" width="65">
              <template slot-scope="scope">
                <p v-if="scope.row.bill_status == 2">
                  <span class="dot dot-ok"></span>已结</p>
                <p v-else>
                  <span class="dot dot-wait"></span>未结</p>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <div class="btn-wrap">
                  <div v-if="scope.row.bill_status == 2"  @click="jumpDetails(scope.row.order_type,scope.row.company,scope.row.customer_id,scope.row.order_id)" class="btn btn-nobg">查看详情</div>
                  <a href="javascript:void(0);" v-if="scope.row.bill_status == 1" class="btn" @click="confirmPayed(scope.row.order_id)">确认结款</a>
                  <a href="javascript:void(0);" v-if="scope.row.bill_status == 1" class="btn btn-nobg" @click="popupModifyPriceBox(scope.row.order_id)">修改价格</a>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="pay-footer" v-if="activeName === '2' && timeParams.bill_status == 1">
          <el-checkbox @click.native="toggleCheck" v-model="allChecked">全选</el-checkbox>
          <div class="btn-wrap">
            <a href="javascript:void(0);" class="btn" @click="allConfirm">确认</a>
          </div>
          <div class="info">
            <span class="iconfont icon-xingzhuang iconfont-info"></span>
            已选择
            <span class="num">{{selectTableData.length}}</span>
            项
          </div>
        </div>
        <div class="pages-row">
          <div class="pages-wrap customer" v-if="activeName === '1' && customerTableData.length">
            <el-pagination :current-page="pageCustomer" @current-change="changePageCustomer" :page-count="maxPageCustomer" background layout="prev, pager, next"></el-pagination>
          </div>
          <div class="pages-wrap time" v-if="activeName === '2' && timeTableData.length" :class="timeParams.bill_status == 1 ? 'fix-top': ''">
            <el-pagination :current-page="pageTime" @current-change="changePageTime" :page-count="maxPageTime" background layout="prev, pager, next"></el-pagination>
          </div>
        </div>
      </div>
    </div>
    <modifyPriceBox :modifyPriceBoxShow.sync="modifyPriceBoxShow" :orderId="modifyMoneyOrderId" @getNewMoney="getNewMoney"></modifyPriceBox>
  </div>
</template>
<script>
import modifyPriceBox from '@/components/modifyPriceBox'
export default {
  name: 'revenue',
  components: {
    modifyPriceBox
  },
  data () {
    return {
      modifyPriceBoxShow: false,
      modifyMoneyOrderId: null,
      allChecked: null,
      isRefreshData: false,
      activeName: "1", // 默认为1,1：按客户查看,2：按时间查看 
      keywords1: null,
      keywords2: null,
      customerParams: {
        type: "0", // 账单类型 0全部 1未结 2 已结
        sort: 0, // 0 从高到低 1从低到高 
        keywords: null
      },
      timeParams: {
        bill_status: "0", // 结款状态 0全部 1未结 2已结
        date: null, // 所选年月份 例如2018-05
        keywords: null
      },
      maxPageCustomer: null,
      pageCustomer: 1, //客户表格页数
      pageTime: 1, // 时间表格页数
      maxPageTime: null,
      selectTableData: [], // 选中的数据
      customerMoney: {},
      timeMoney: {},
      customerTableData: [],
      timeTableData: []
    }
  },
  computed: {
  },
  watch: {
    activeName () {
      // 切换客户/时间 更新表格数据
      this.resetParams()
      this.getRevenueInfo()
    },
    customerParams: {
      handler () {
        if (this.pageCustomer != 1) {
          this.resetPage()
        }
        this.getRevenueInfo()
      },
      deep: true
    },
    timeParams: {
      handler () {
        if (this.pageTime != 1) {
          this.resetPage()
        }
        this.getRevenueInfo()
      },
      deep: true
    }
  },
  created () {
    this.getRevenueInfo()
  },
  methods: {
    // 全部导出账单
    downloadBillList () {
      this.$axios.get(this.$httpUrl.downloadALLBillList).then(res => {
        if (res != false) {
          if (res.url) {
            console.log(res)
            window.location.href = res.url
          } else {
            this.$message({
              message: '没有可以导出的数据',
              type: 'info'
            })
          }
        }
      })
    },
    // 未结款复选框操作操作
    handleSelectionChange (selectTableData) {
      let timeTableData = this.timeTableData
      if (selectTableData.length === timeTableData.length && timeTableData.length) {
        this.allChecked = true
      } else {
        if (this.allChecked) {
          this.allChecked = false
        }
      }
      this.selectTableData = selectTableData
    },
    // 切换选择
    toggleCheck () {
      this.$refs.timeTable.toggleAllSelection()
    },
    // 客户表格翻页
    changePageCustomer (page) {
      this.pageCustomer = page
      this.$nextTick(() => {
        this.getRevenueInfo()
      })
    },
    // 时间表格翻页
    changePageTime (page) {
      this.pageTime = page
      this.$nextTick(() => {
        this.getRevenueInfo()
      })
    },
    // 查询关键词
    searchKeywords () {
      let activeName = this.activeName
      if (activeName == 1) {
        Object.assign(this.customerParams, { keywords: this.keywords1 })
      } else {
        Object.assign(this.timeParams, { keywords: this.keywords2 })
      }
    },
    // 重置参数 
    resetParams () {
      let activeName = this.activeName
      if (activeName == 1) {
        Object.assign(this.timeParams, { bill_status: "0", date: null, keywords: null })
        this.keyWords2 = null
      } else {
        Object.assign(this.customerParams, { type: "0", sort: 0, keywords: null })
        this.keywords1 = null
      }
    },
    resetPage () {
      let activeName = this.activeName
      if (activeName == 1) {
        this.pageCustomer = 1
        this.customerTableData = []
        this.customerMoney = {}
      } else {
        this.pageTime = 1
        this.timeTableData = []
        this.timeMoney = {}
      }
    },
    getRevenueInfo () {
      let activeName = this.activeName
      let params, url
      if (activeName == 1) {
        params = Object.assign({}, this.customerParams, { page: this.pageCustomer })
        url = this.$httpUrl.customerBillList
      } else {
        params = Object.assign({}, this.timeParams, { page: this.pageTime })
        url = this.$httpUrl.timeBillList
      }
      this.$axios.get(url, { params: params }).then(res => {
        if (res) {
          if (activeName == 1) {
            this.customerTableData = res.list
            this.customerMoney = res.info
            this.maxPageCustomer = res.pages
          } else {
            this.timeTableData = res.list
            this.timeMoney = res.info
            this.maxPageTime = res.pages
          }
        }
      })
    },
    // 弹出价格框
    popupModifyPriceBox (order_id) {
      this.modifyPriceBoxShow = true
      this.modifyMoneyOrderId = order_id
    },
    modifyData (order_id, data) {
      for (var i in this.timeTableData) {
        let orderInfo = this.timeTableData[i]
        if (order_id == orderInfo.order_id) {
          Object.assign(orderInfo, data)
          this.timeTableData[i] = orderInfo
          break
        }
      }
    },
    getNewMoney (money) {
      let data = {
        'money': money
      }
      this.modifyData(this.modifyMoneyOrderId, data)
    },
    // 全选支付未结款
    allConfirm () {
      var arr = []
      let confirmIds = this.selectTableData
      if (confirmIds.length) {
        confirmIds.forEach(ele => {
          arr.push(ele.order_id)
        })
        let order_ids = arr.join(',')
        this.confirmPayed(order_ids)
      } else {
        this.$message({
          message: '请勾选账单',
          type: 'error'
        })
      }
    },
    // 确认结款
    confirmPayed (order_ids) {
      this.$confirm('是否确认结款', {
        type: 'warning'
      }).then(() => {
        let params = {
          'order_id': order_ids
        }
        this.$axios.post(this.$httpUrl.billModifyStatus, params).then(res => {
          if (res != false) {
            this.$message({
              message: '结款操作成功',
              type: 'success'
            })
            order_ids = order_ids.toString().split(',')
            for (var i in order_ids) {
              let data = {
                'bill_status': 2
              }
              this.modifyData(order_ids[i], data)
            }
          }
        })
      })
    },
    jumpDetails(orderType,company,customer_id,order_id){
      if(orderType==1){
        this.$router.push({name: 'accountDetails', query: {companyName: company,customerId:customer_id,order_id:order_id,source:'bill'}})
      }else if(orderType==2){
        this.$router.push({name: 'TransportStationDetail', query: {companyName: company,customerId:customer_id,order_id:order_id,source:'bill'}})
      }else if(orderType==3){
        this.$router.push({name: 'shuttleBusDetail', query: {companyName: company,customerId:customer_id,order_id:order_id,source:'bill'}})
      }
    }
  }
}
</script>
<style lang="less">
.table-tip {
  .line {
    font-size: 14px;
    padding: 0 15px;
    .name {
      display: inline-block;
      width: 56px;
      margin-right: 8px;
    }
    .mobile {
      margin-right: 8px;
    }
    .car_number {
      margin-right: 8px;
    }
    .car-source {
      margin-left: 6px;
      padding: 1px 5px;
      &.out-car {
        background-color: #ff991f;
        color: #fff;
        border-radius: 2px;
      }
    }
  }
}
</style>

