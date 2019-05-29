<template>
  <div c>
    <div class='main-header'>
      <div class="breadcrumb-wrap">
        <div
          class="back"
          @click="$router.go(-1)"
        >
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>财务管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'revenue'}">营收</el-breadcrumb-item>
          <el-breadcrumb-item>{{companyName}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <financeOptions option="1"></financeOptions>
    <div class="main-content">
      <div class="conditions detail">
        <p class="company-name">{{companyName}}</p>
        <div class="search-box">
          <div class="search-wrap">
            <span class="iconfont icon-shujuchaxun iconfont-search"></span>
            <el-input
              class="search-inp"
              v-model="keywords2"
              placeholder="请输入账单编号／计调姓名等"
            ></el-input>
          </div>
          <a
            href="javascript:void(0)"
            class="btn-search"
            @click="searchKeywords"
          >搜索</a>
        </div>
        <div class="date-box">
          <el-date-picker
            class="el-date"
            v-model="timeParams.date"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择年月"
          >
          </el-date-picker>
        </div>
      </div>
      <div class="row">
        <div class="medium-line">
          <div class="tabs">
            <el-tabs
              v-model="timeParams.bill_status"
              type="card"
            >
              <el-tab-pane
                label="全部"
                name="0"
              ></el-tab-pane>
              <el-tab-pane
                label="已结款"
                name="2"
              ></el-tab-pane>
              <el-tab-pane
                label="未结款"
                name="1"
              ></el-tab-pane>
            </el-tabs>
          </div>
          <div
            class="money-wrap"
            v-show="timeMoney.total_order_money"
          >
            <span class="money-lb">{{timeParams.bill_status == 0 ? '全部：' : timeParams.bill_status == 2 ? '已结款：': '未结款：'}}</span>
            <span
              class="money-txt all"
              v-show="timeParams.bill_status == 0"
            >{{timeMoney.total_order_money}}元</span>
            <span
              class="money-txt ok"
              v-show="timeParams.bill_status == 2"
            >{{timeMoney.has_pay_money}}元</span>
            <span
              class="money-txt fail"
              v-show="timeParams.bill_status == 1"
            >{{timeMoney.not_pay_money}}元</span>
          </div>
          <router-link
            tag="div"
            class="link"
            :to="{name:'addMission',query:missionQuery}"
          >
            <span class="iconfont icon-close iconfont-close"></span>
            <span>新增账单</span>
          </router-link>
          <div
            class="link import"
            @click="downloadBillList"
          >
            <span>全部导出</span>
          </div>
        </div>

        <div class="table">
          <dataBlank
            :missionQuery="missionQuery"
            v-if="!timeTableData.length && maxPageTime === 0"
            dataType="account"
          ></dataBlank>
          <!-- 时间筛选表格 -->
          <el-table
            v-if="timeTableData.length"
            ref="timeTable"
            class="timeTable"
            :class="timeParams.bill_status == 1 ? 'fix-bottom': ''"
            :data="timeTableData"
            @selection-change="handleSelectionChange"
            style="width: 100%"
          >
            <el-table-column
              type="selection"
              key="selection"
              width="47"
              v-if="timeParams.bill_status == 1"
            >
            </el-table-column>
            <el-table-column
              label=""
              width="47"
              key="noselection"
              v-else
            >
            </el-table-column>
            <el-table-column
              label="订单编号"
              width="110"
            >
              <template slot-scope="scope">
                <!-- <router-link
                  tag="div"
                  :to="{name: 'accountDetails',query: {companyName: scope.row.company,customerId:scope.row.customer_id,order_id:scope.row.order_id,source:'bill'}}"
                  class="order-id"
                >
                  {{scope.row.order_num}}
                </router-link> -->
                <div class="order-id" @click="jumpDetails(scope.row.company,scope.row.customer_id,scope.row.order_id,scope.row.order_type)">
                  {{scope.row.order_num}}
                </div>    
              </template>
            </el-table-column>
            <el-table-column label="订单类型">
              <template slot-scope="scope">
                <p v-if="scope.row.order_type==1">包车</p>
                <p v-if="scope.row.order_type==2">接送</p>
                <p v-if="scope.row.order_type==3">班车</p>
              </template>
            </el-table-column>
            <el-table-column
              label="营收"
              width="140"
            >
              <template slot-scope="scope">
                <p>营收:<span>{{scope.row.money}}</span>元</p>
                <p>已收:<span>{{scope.row.deposit}}</span>元</p>
                <p>应收:<span :class="scope.row.receivable > 0  ? 'red' : scope.row.receivable < 0 ? 'green' : ''">{{scope.row.receivable}}</span:>元</p>
              </template>
            </el-table-column>
            <el-table-column
              prop="productId"
              label="时间"
              width="160"
            >
              <template slot-scope="scope">
                <p class="line">开始:{{scope.row.travel_begin}}</p>
                <p class="line">结束:{{scope.row.travel_end}}</p>
              </template>
            </el-table-column>
            <el-table-column
              label="行程"
              prop="travel_content"
            >
            </el-table-column>
            <el-table-column
              label="计调姓名"
              width="100"
            >
              <template slot-scope="scope">
                {{scope.row.customer_yardman_name}}
                <div class="iconfont-wrap">
                  <el-popover
                    popper-class="tel-tip"
                    placement="top-start"
                    width="150"
                    trigger="hover"
                  >
                    {{scope.row.customer_yardman_mobile}}
                    <span
                      slot="reference"
                      class="iconfont icon-bohao iconfont-tel"
                    ></span>
                  </el-popover>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              label="司机"
              width="115"
            >
              <template slot-scope="scope">
                {{scope.row.car_info[0].driver_name}}
                <div class="iconfont-wrap">
                  <el-popover
                    popper-class="tel-tip"
                    placement="top-start"
                    width="150"
                    trigger="hover"
                  >
                    {{scope.row.car_info[0].driver_mobile}}
                    <span
                      slot="reference"
                      class="iconfont icon-bohao iconfont-tel"
                    ></span>
                  </el-popover>
                </div>
                <div class="iconfont-wrap">
                  <el-popover
                    popper-class="table-tip"
                    placement="left"
                    width="320"
                    trigger="click"
                  >
                    <div class="line-wrap">
                      <div
                        class="line"
                        v-for="(info, index) in scope.row.car_info"
                        :key="index"
                      >
                        <span class="name">{{info.driver_name}}</span>
                        <span class="mobile">{{info.driver_mobile}}</span>
                        <span class="car-number">{{info.car_number}}</span>
                        <span
                          v-if="info.car_source == 2"
                          class="car-source out-car"
                        >外援</span>
                      </div>
                    </div>
                    <span
                      slot="reference"
                      class="iconfont icon-jiantou2 iconfont-arrow"
                    ></span>
                  </el-popover>
                </div>
                <p class="line">{{scope.row.car_info[0].car_number}}</p>
                <span
                  v-if="scope.row.car_info[0].car_source == 2"
                  class="car-source out-car"
                >外援</span>
              </template>
            </el-table-column>
            <el-table-column
              label="状态"
              width="65"
            >
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
                  <div
                    v-if="scope.row.bill_status == 2"
                    @click="jumpDetails(scope.row.company,scope.row.customer_id,scope.row.order_id,scope.row.order_type)"
                    class="btn btn-nobg"
                  >查看详情</div>
                  <a
                    href="javascript:void(0);"
                    v-if="scope.row.bill_status == 1"
                    class="btn"
                    @click="confirmPayed(scope.row.order_id)"
                  >确认结款</a>
                  <a
                    href="javascript:void(0);"
                    v-if="scope.row.bill_status == 1"
                    class="btn btn-nobg"
                    @click="popupModifyPriceBox(scope.row.order_id)"
                  >修改价格</a>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div
          class="pay-footer"
          v-if="timeParams.bill_status == 1"
        >
          <el-checkbox
            @click.native="toggleCheck"
            v-model="allChecked"
          >全选</el-checkbox>
          <div class="btn-wrap">
            <a
              href="javascript:void(0);"
              class="btn"
              @click="allConfirm"
            >确认</a>
          </div>
          <div class="info">
            <span class="iconfont icon-xingzhuang iconfont-info"></span>
            已选择
            <span class="num">{{selectTableData.length}}</span>
            项
          </div>
        </div>
        <div class="pages-row">
          <div
            class="pages-wrap time"
            v-if="timeTableData.length"
            :class="timeParams.bill_status == 1 ? 'fix-top': ''"
          >
            <el-pagination
              :current-page="pageTime"
              @current-change="changePageTime"
              :page-count="maxPageTime"
              background
              layout="prev, pager, next"
            ></el-pagination>
          </div>
        </div>
      </div>
    </div>
    <modifyPriceBox
      :modifyPriceBoxShow.sync="modifyPriceBoxShow"
      :orderId="modifyMoneyOrderId"
      @getNewMoney="getNewMoney"
    ></modifyPriceBox>
  </div>
</template>
<script>
import modifyPriceBox from '@/components/modifyPriceBox'
export default {
  name: 'revenueDetail',
  components: {
    modifyPriceBox
  },
  data () {
    return {
      modifyPriceBoxShow: false,
      modifyMoneyOrderId: null,
      allChecked: null,
      missionQuery: null,
      companyName: '',
      keywords2: '',
      info: {},
      timeParams: {
        bill_status: '0',
        customer_id: null,
        date: null,
        keywords: null
      },
      pageTime: 1, // 时间表格页数
      maxPageTime: null,
      timeTableData: [],
      selectTableData: [], // 选中的数据 
      timeMoney: {}
    }
  },
  watch: {
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
    this.getQueryParams()
  },
  methods: {
    // 下载账单
    downloadBillList () {
      let params = this.timeParams
      this.$axios.get(this.$httpUrl.downloadBillList, { params: params }).then(res => {
        if (res != false) {
          if (res.url) {
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
    // 筛选时间
    changeDate (date) {
      this.timeParams.date = date
    },
    // 时间表格翻页
    changePageTime (page) {
      this.pageTime = page
      this.$nextTick(() => {
        this.getRevenueInfo()
      })
    },
    getQueryParams () {
      let query = this.$route.query
      this.companyName = query.companyName
      // let date = this.$util.formatTime().yM
      this.timeParams = Object.assign({}, this.timeParams, { customer_id: query.customer_id })
      this.missionQuery = { customer_id: query.customer_id, customer_company: query.companyName }
    },
    resetPage () {
      this.pageTime = 1
      this.timeTableData = []
      this.timeMoney = {}
    },
    searchKeywords () {
      Object.assign(this.timeParams, { keywords: this.keywords2 })
    },
    getRevenueInfo () {
      let params, url
      params = Object.assign({}, this.timeParams, { page: this.pageTime })
      url = this.$httpUrl.customerBill
      this.$axios.get(url, { params: params }).then(res => {
        if (res) {
          this.timeTableData = res.list
          this.timeMoney = res.info
          this.maxPageTime = res.pages
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
        console.log(order_ids)
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
            console.log(order_ids)
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
    // companyName: scope.row.company,customerId:scope.row.customer_id,order_id:scope.row.order_id,source:'bill'
    jumpDetails(companyName,customerId,order_id,order_type){
      // console.log(order_type)
      if(order_type==1){
        this.$router.push({name:'accountDetails',query:{companyName:companyName,customerId:customerId,order_id:order_id,source:'bill'}})
      }else if(order_type==2){
        this.$router.push({name: 'TransportStationDetail',query:{companyName:companyName,customerId:customerId,order_id:order_id,source:'bill'}})
      }else if(order_type==3){
        this.$router.push({name: 'shuttleBusDetail',query:{companyName:companyName,customerId:customerId,order_id:order_id,source:'bill'}})
      }
    }
  }
}
</script>
<style lang="less" scope>
</style>