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
          <el-breadcrumb-item :to="{ name: 'reportForm'}">报表</el-breadcrumb-item>
          <el-breadcrumb-item>{{typeName}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <financeOptions option="5"></financeOptions>
    <div class="main-content">
      <div class="conditions detail">
        <p class="tt tt-report">{{typeName}}:{{totalMoney}}元</p>
        <div class="search-box">
          <div class="search-wrap">
            <span class="iconfont icon-shujuchaxun iconfont-search"></span>
            <el-input class="search-inp" v-model="keywords" placeholder="请输入订单编号"></el-input>
          </div>
          <a href="javascript:void(0)" class="btn-search" @click="searchKeywords">搜索</a>
        </div>
        <div class="date-box">
          <div class="date-wrap">
            <el-date-picker class="el-date" v-model="tableParams.time" type="month" value-format="yyyy-MM" placeholder="选择年月">
            </el-date-picker>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="table">
          <dataBlank :nobtn=false v-if="!tableData.length && maxPage === 0" dataType="account"></dataBlank>  
          <el-table class="timeTable" v-if="tableData.length" :data="tableData" style="width: 100%">
            <el-table-column label="订单编号">
              <template slot-scope="scope">
                <router-link  v-if="scope.row.order_num"  tag="div" :to="{name: 'accountDetails',query: {order_id:scope.row.saas_order_id,source:'bill'}}" class="order-id">
                  {{scope.row.order_num }}
                </router-link>
                <span v-else> {{scope.row.str}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="productId" label="时间">
              <template slot-scope="scope">
                <p class="line">开始:{{scope.row.begin_at}}</p>
                <p class="line" v-if="scope.row.end_at">结束:{{scope.row.end_at}}</p>
              </template>
            </el-table-column>
            <el-table-column label="司机">
              <template slot-scope="scope">
                <div class="driver" v-for="(car, index) in scope.row.cars" :key="index">
                  {{car.name}}
                  <div class="iconfont-wrap" v-if="car.name">
                    <el-popover popper-class="tel-tip" placement="top-start" width="150" trigger="hover">
                      {{car.mobile}}
                      <span slot="reference" class="iconfont icon-bohao iconfont-tel"></span>
                    </el-popover>
                  </div>
                  <p class="line">{{car.car_number}}</p>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="费用支出">
              <template slot-scope="scope">
                <span class="red">{{scope.row.money}}</span>元
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <div class="btn-wrap">
                  <!-- 跳转到支出详情 -->
                  <router-link key="foreign_money" v-if="tableParams.type =='foreign_money'"  :to="{name: 'accountDetails',query: {order_id:scope.row.saas_order_id,source:'bill'}}" class="btn btn-nobg">查看详情</router-link>
                  <router-link key="other" v-else :to="{name: 'SpendingDetail',query: {fee_id:scope.row.fee_id}}" class="btn btn-nobg">查看详情</router-link>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="pages-row">
          <div class="pages-wrap time" v-if="tableData.length">
            <el-pagination :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>

export default {
  name: 'reportFormDetail',
  watch: {

  },
  data () {
    return {
      typeName: null,
      keywords: null,
      tableParams: {
        type: '',
        keywords: '',
        time: null,
      },
      totalMoney: null,
      tableData: [],
      page: null,
      maxPage: null
    }
  },
  watch: {
    tableParams: {
      handler () {
        if (this.page != 1) {
          this.page = 1
        }
        this.getPayDetail()
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
      this.typeName = query.typeName      
      Object.assign(this.tableParams, { type: query.type })
    },
    getPayDetail () {
      let params, url
      params = Object.assign({}, this.tableParams, { page: this.page })
      url = this.$httpUrl.profitPayDetail
      this.$axios.get(url, { params: params }).then(res => {
        if (res) {
          this.tableData = res.list
          this.totalMoney = res.total
          this.maxPage = res.pages
        }
      })
    },
    searchKeywords () {
      Object.assign(this.tableParams, { keywords: this.keywords })
    },
    changePage (page) {
      this.page = page
      this.$nextTick(() => {
        this.getPayDetail()
      })
    }
  }
}
</script>
<style lang="less" scope>
</style>