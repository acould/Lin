<template>
  <div>
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>财务管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'profits'}">利润</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <financeOptions option="4"></financeOptions>
    <div class="main-content">
      <div class="profit-block-wrap">
        <div class="block">
          <p class="lb">订单营收</p>
          <p class="txt">{{info.get}}
            <span>元</span>
          </p>
        </div>
        <div class="block">
          <p class="lb">订单支出</p>
          <p class="txt">{{info.pay}}
            <span>元</span>
          </p>
        </div>
        <div class="block">
          <p class="lb">日常支出</p>
          <p class="txt">{{info.day_pay}}
            <span>元</span>
          </p>
        </div>
      </div>
      <div class="takespace"></div>
      <div class="conditions profit">
        <div class="search-box">
          <div class="search-wrap">
            <span class="iconfont icon-shujuchaxun iconfont-search"></span>
            <el-input class="search-inp" v-model="keywords" placeholder="请输入公司名称/车牌号/司机等"></el-input>
          </div>
          <a href="javascript:void(0)" class="btn-search" @click="searchKeywords">搜索</a>
        </div>
        <!-- <a href="javascript:void(0);" class="import-bill btn" @click="downTableList">导出表格</a> -->
        <div class="date-box">
          <div class="date-wrap">
            <el-date-picker class="el-date" v-model="tableParams.time" type="month" value-format="yyyy-MM" placeholder="选择年月">
            </el-date-picker>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="table profit">
          <!-- <dataBlank :nobtn=false v-if="!tableData.length && maxPage === 0" dataType="account"></dataBlank> -->
          <el-table class="timeTable" :data="tableData" style="width: 100%">
            <el-table-column label="订单编号" min-width="100">
              <template slot-scope="scope">
                <div  @click="jumpDetails(scope.row.order_id,scope.row.order_type)" class="order-id">
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
            <el-table-column label="客户" width="180">
              <template slot-scope="scope">
                {{scope.row.customer_company}}
                <div class="iconfont-wrap">
                  <el-popover popper-class="tel-tip" placement="top-start" width="150" trigger="hover">
                    {{scope.row.customer_mobile}}
                    <span slot="reference" class="iconfont icon-bohao iconfont-tel"></span>
                  </el-popover>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="时间" width="150">
              <template slot-scope="scope">
                <p class="line">开始:{{scope.row.travel_begin_at}}</p>
                <p class="line">结束:{{scope.row.travel_end_at}}</p>
              </template>
            </el-table-column>
            <el-table-column label="司机" min-width="100">
              <template slot-scope="scope">
                {{scope.row.detail[0].name}}
                <div class="iconfont-wrap">
                  <el-popover popper-class="tel-tip" placement="top-start" width="150" trigger="hover">
                    {{scope.row.detail[0].mobile}}
                    <span slot="reference" class="iconfont icon-bohao iconfont-tel"></span>
                  </el-popover>
                </div>
                <div class="iconfont-wrap">
                  <el-popover popper-class="table-tip" placement="left" width="320" trigger="click">
                    <div class="line-wrap">
                      <div class="line" v-for="(info, index) in scope.row.detail" :key="index">
                        <span class="name">{{info.name}}</span>
                        <span class="mobile">{{info.mobile}}</span>
                        <span class="car-number">{{info.car_number}}</span>
                        <span v-if="info.out_company_id > 0 " class="car-source out-car">外援</span>
                      </div>
                    </div>
                    <span slot="reference" class="iconfont icon-jiantou2 iconfont-arrow"></span>
                  </el-popover>
                </div>
                <p class="line">{{scope.row.detail[0].car_number}}</p>
                <span v-if="scope.row.detail[0].out_company_id > 0" class="car-source out-car">外援</span>
              </template>
            </el-table-column>
            <el-table-column label="行程" prop="travel_content">
            </el-table-column>
            <el-table-column label="营收" width="80">
              <template slot-scope="scope">
                <span>{{scope.row.get_money}}</span>元
              </template>
            </el-table-column>
            <el-table-column label="支出" width="80">
              <template slot-scope="scope">
                <span>{{scope.row.pay_money}}</span>元
              </template>
            </el-table-column>
            <el-table-column label="利润" width="80">
              <template slot-scope="scope">
                <span class="red">{{scope.row.profit_money}}</span>元
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <div class="btn-wrap">
                  <div   @click="jumpDetails(scope.row.order_id,scope.row.order_type)" class="btn">
                    查看详情
                  </div>
                </div>
              </template>
            </el-table-column>
          </el-table>
          <div class="pages-row">
            <div class="pages-wrap time" v-if="tableData.length">
              <el-pagination :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>

export default {
  name: 'profits',
  watch: {

  },
  data () {
    return {
      keywords: '',
      page: 1,
      maxPage: null,
      tableParams: {
        keywords: null,
        time: null
      },
      tableData: [],
      info: {}
    }
  },
  watch: {
    tableParams: {
      handler () {
        if (this.page != 1) {
          this.page = 1
        }
        this.getProfitData()
      },
      deep: true
    }
  },
  created () {
    this.getQueryParams()
  },
  methods: {
    searchKeywords () {
      this.tableParams.keywords = this.keywords
    },
    getQueryParams () {
      let query = this.$route.query
      let dateType = query.dateType

      Object.assign(this.tableParams, { time: query.date })
    },
    getProfitData () {
      let params, url
      params = Object.assign({}, this.tableParams, { page: this.page })
      url = this.$httpUrl.profitIndex
      this.$axios.get(url, { params: params }).then(res => {
        if (res) {
          this.tableData = res.list
          this.info = Object.assign({}, { get: res.get, pay: res.pay, day_pay: res.day_pay })
          this.maxPage = res.pages
        }
      })
    },
    changePage (page) {
      this.page = page
      this.getProfitData()
    },
    downTableList () {
      this.$axios.get(this.$httpUrl.profitListDownload, { params: this.tableParams }).then(res => {
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
.main-content {
  .conditions {
    .import-bill {
      margin: 10px 10px 0 0;
    }
  }
}
</style>