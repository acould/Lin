<template>
  <div>
    <div class='main-header'>
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>财务管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'outSettlement'}">外援结算</el-breadcrumb-item>
          <el-breadcrumb-item>费用详情</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <financeOptions option="2"></financeOptions>
    <div class="main-content">
      <div class="conditions detail">
        <p class="tt tt-report">{{companyName}}</p>
        <div class="search-box">
          <div class="search-wrap">
            <span class="iconfont icon-shujuchaxun iconfont-search"></span>
            <el-input class="search-inp" v-model="keywords" placeholder="请输入订单编号"></el-input>
          </div>
          <a href="javascript:void(0)" class="btn-search" @click="searchKeywords">搜索</a>
        </div>
      </div>
      <div class="row">
        <div class="medium-line">
          <div class="checkboxs">
            <el-checkbox v-model="allChecked">全部</el-checkbox>
            <el-checkbox-group v-model="type1">
              <el-checkbox label="2">外援</el-checkbox>
              <el-checkbox label="3">被借</el-checkbox>
            </el-checkbox-group>
            <el-checkbox-group v-model="type2">
              <el-checkbox label="4">已结</el-checkbox>
              <el-checkbox label="5">未结</el-checkbox>
            </el-checkbox-group>
          </div>
          <a href="javascript:void(0);" class="import-bill btn" @click="downTableList">导出表格</a>
          <router-link tag="div" class="link" :to="{name:'addMission',query: routeLinksParams}">
            <span class="iconfont icon-close iconfont-close"></span>
            <span>新增账单</span>
          </router-link>
          
          <div class="date-box">
            <div class="date-wrap">
              <el-date-picker class="el-date" v-model="tableParams.time" type="month" value-format="yyyy-MM" placeholder="选择年月">
              </el-date-picker>
            </div>
          </div>
        </div>
        <div class="table">
          <!-- 时间筛选表格 -->
          <el-table ref="timeTable" class="timeTable" :class="type2[0] == '5' ? 'fix-bottom': ''" :data="tableData" @selection-change="handleSelectionChange" style="width: 100%">
            <el-table-column type="selection" key="selection" width="47" v-if="type2[0] == '5'">
            </el-table-column>
            <el-table-column label="" width="47" key="noselection" v-else>
            </el-table-column>
            <el-table-column label="订单编号" width="110">
              <template slot-scope="scope">
                <div @click="jumpDetails(scope.row.order_id,scope.row.order_type)" class="order-id">
                  {{scope.row.order_num}}
                  <p>
                    <span v-if="scope.row.type == 2" class="car-source out-car">外援</span>
                    <span v-if="scope.row.type == 1" class="car-source pay">被借</span>
                  </p>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="订单类型">
              <template slot-scope="scope">
                <span v-if="scope.row.order_type==1">包车</span>
                <span v-if="scope.row.order_type==2">接送</span>
                <span v-if="scope.row.order_type==3">班车</span>
              </template>
            </el-table-column>
            <el-table-column label="时间" min-width="140">
              <template slot-scope="scope">
                <p class="line">开始:{{scope.row.travel_begin_at}}</p>
                <p class="line">结束:{{scope.row.travel_end_at}}</p>
              </template>
            </el-table-column>
            <el-table-column label="司机" width="115">
              <template slot-scope="scope">
                {{scope.row.detail[0].driver_name}}
                <div class="iconfont-wrap">
                  <el-popover popper-class="tel-tip" placement="top-start" width="150" trigger="hover">
                    {{scope.row.detail[0].driver_mobile}}
                    <span slot="reference" class="iconfont icon-bohao iconfont-tel"></span>
                  </el-popover>
                </div>
                <div class="iconfont-wrap">
                  <el-popover popper-class="table-tip" placement="left" width="220" trigger="click">
                    <div class="line-wrap">
                      <div class="line" v-for="(info, index) in scope.row.detail" :key="index">
                        <span class="name">{{info.driver_name}}</span>
                        <span class="mobile">{{info.driver_mobile}}</span>
                        <span class="car-number">{{info.car_number}}</span>
                        <span v-if="info.out_company_id > 0 " class="car-source out-car">外援</span>
                      </div>
                    </div>
                    <span slot="reference" class="iconfont icon-jiantou2 iconfont-arrow"></span>
                  </el-popover>
                </div>
                <p class="line">{{scope.row.detail[0].car_number}}</p>
              </template>
            </el-table-column>
            <el-table-column label="行程" prop="travel_content" min-width="100">

            </el-table-column>
            <el-table-column label="收支" min-width="100">
              <template slot-scope="scope">
                <p>营收：{{scope.row.money}}元</p>
                <p>结算：{{scope.row.foreign_money}}元</p>
                <p>代收：{{scope.row.collection_money}}元</p>
                <p>利润：{{scope.row.profit}}元</p>
                <p :class="scope.row.receivable < 0 ? 'green': scope.row.receivable > 0 ? 'red' : ''">应收：{{scope.row.receivable}}元</p>
              </template>
            </el-table-column>
            <el-table-column label="状态" width="65">
              <template slot-scope="scope">
                <p v-if="scope.row.status == 2">
                  <span class="dot dot-ok"></span>已结</p>
                <p v-else>
                  <span class="dot dot-wait"></span>未结</p>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <div class="btn-wrap">
                  <router-link class="btn btn-nobg" v-if="scope.row.status == 2" :to="{name: 'accountDetails',query: {order_id:scope.row.order_id,source:'bill'}}">查看详情</router-link>
                  <a href="javascript:void(0);" v-else class="btn" @click="confirm(scope.row.order_id,scope.row.type)">结算</a>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="pay-footer" v-if="type2[0] == '5'">
          <el-checkbox @click.native="toggleCheck" v-model="allChecked2">全选</el-checkbox>
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
          <div class="pages-wrap time" v-if="tableData.length" :class="type2[0] == '5' ? 'fix-top': ''">
            <el-pagination :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>

export default {
  name: 'OutSettlementDetail',
  watch: {

  },
  data () {
    return {
      keywords: '',
      conmpanyName: '',
      page: 1,
      maxPage: null,
      allChecked: true,
      type1: [],
      type2: [],
      tableParams: {
        keywords: null,
        customer_id: null,
        type: "1",
        time: null
      },
      tableData: [],
      allChecked2: null,
      selectTableData: [],
      routeLinksParams: {}
    }
  },
  computed: {
    noChked () {
      return (!this.type1.length && !this.type2.length) === true ? true : false
    },
    allChk () {
      if (this.allChecked) return ["1"]
      let arr = this.type1.concat(this.type2)
      return arr
    }
  },
  watch: {
    type1 (newVal) {
      if (newVal.length) {
        this.allChecked = false
      }
      if (newVal.length > 1) {
        newVal.splice(0, 1)
      }
      this.type1 = newVal
    },
    type2 (newVal) {
      if (newVal.length) this.allChecked = false
      if (newVal.length > 1) {
        newVal.splice(0, 1)
      }
      this.type2 = newVal
    },
    noChked (newVal) {
      if (newVal) this.allChecked = true
    },
    allChecked (newVal) {
      if (newVal) this.type1 = this.type2 = []
      if (!newVal && !this.type1.length && !this.type2.length) this.allChecked = true
    },
    allChk (newVal) {
      this.tableParams.type = newVal.join(",")
    },
    tableParams: {
      handler () {
        if (this.page != 1) {
          this.page = 1
        }
        this.getTableData()
      },
      deep: true
    }
  },
  created () {
    this.getQueryParams()
  },
  methods: {
    getQueryParams () {
      let query = this.$route.query
      this.companyName = query.companyName
      this.routeLinksParams = Object.assign({}, query, { customer_company: query.companyName })
      Object.assign(this.tableParams, { customer_id: query.customer_id })
      if (query.type1) this.type1 = [query.type1]
      if (query.type2) this.type2 = [query.type2]
    },
    searchKeywords () {
      this.tableParams.keywords = this.keywords
    },
    getTableData () {
      let params, url
      params = Object.assign({}, this.tableParams, { page: this.page })
      url = this.$httpUrl.foreignDetail
      this.$axios.get(url, { params: params }).then(res => {
        if (res) {
          this.tableData = res.list
          this.maxPage = res.pages
        }
      })
    },
    changePage (page) {
      this.page = page
      this.getTableData()
    },
    // 未结款复选框操作操作
    handleSelectionChange (selectTableData) {
      let tableData = this.tableData
      if (selectTableData.length === tableData.length && tableData.length) {
        this.allChecked2 = true
      } else {
        if (this.allChecked) {
          this.allChecked2 = false
        }
      }
      this.selectTableData = selectTableData
    },
    // 切换选择
    toggleCheck () {
      this.$refs.timeTable.toggleAllSelection()
    },
    allConfirm () {
      var arr = []
      let confirmIds = this.selectTableData
      if (confirmIds.length) {
        confirmIds.forEach(ele => {
          arr.push({ order_id: ele.order_id, type: ele.type })
        })
        this.confirmPayed(arr)
      } else {
        this.$message({
          message: '请勾选账单',
          type: 'error'
        })
      }
    },
    confirm (order_id, type) {
      var arr = [{ order_id: order_id, type: type }]
      this.confirmPayed(arr)
    },
    modifyData (order_id, data) {
      for (var i in this.tableData) {
        let orderInfo = this.tableData[i]
        if (order_id == orderInfo.order_id) {
          Object.assign(orderInfo, data)
          this.tableData[i] = orderInfo
          break
        }
      }
    },
    // 确认结款
    confirmPayed (order_ids) {
      this.$confirm('是否确认结款', {
        type: 'warning'
      }).then(() => {
        let params = {
          'orders': order_ids,
          "customer_id": this.tableParams.customer_id
        }
        this.$axios.post(this.$httpUrl.foreignBalance, params).then(res => {
          if (res != false) {
            this.$message({
              message: '结款操作成功',
              type: 'success'
            })
            for (var i in order_ids) {
              let data = {
                'status': 2
              }
              this.modifyData(order_ids[i].order_id, data)
            }
          }
        })
      })
    },
    downTableList () {
      this.$axios.get(this.$httpUrl.foreignListDownload, { params: this.tableParams }).then(res => {
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
    jumpDetails(order_id,orderType){
      if(orderType==1){
        this.$router.push({name: 'accountDetails', query: {order_id:order_id,source:'bill'}})
      }else if(orderType==2){
        this.$router.push({name: 'TransportStationDetail', query: {order_id:order_id,source:'bill'}})
      }else if(orderType==3){
        this.$router.push({name: 'shuttleBusDetail', query: {order_id:order_id,source:'bill'}})
      }
    }
  }
}
</script>
<style lang="less" scope>

</style>