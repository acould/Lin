<template>
  <div>
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>财务管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'outSettlement'}">外援结算</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <financeOptions option="2"></financeOptions>
    <div class="main-content">
      <div class="conditions outSettlement">
        <div class="search-box">
          <div class="search-wrap">
            <span class="iconfont icon-shujuchaxun iconfont-search"></span>
            <el-input class="search-inp" v-model="keywords" placeholder="请输入用车单位名称"></el-input>
          </div>
          <a href="javascript:void(0)" class="btn-search" @click="searchKeywords">搜索</a>
        </div>
        <router-link tag="div" class="link" :to="{name:'addMission'}">
          <span class="iconfont icon-close iconfont-close"></span>
          <span>新增账单</span>
        </router-link>
      </div>
      <div class="row">
        <div class="table">
          <dataBlank v-if="!tableData.length && maxPage === 0" dataType="account" word="外援记录"></dataBlank>
          <el-table v-if="tableData.length" class="timeTable" :data="tableData" style="width: 100%">
            <el-table-column label="公司名称" min-width="100">
              <template slot-scope="scope">
                <router-link tag="div" :to="{name: 'OutSettlementDetail',query: {companyName: scope.row.company,customer_id:scope.row.customer_id,customer_mobile: scope.row.mobile,customer_name:scope.row.name}}" class="order-id">
                  {{scope.row.company}}
                  <div class="iconfont-wrap">
                    <el-popover popper-class="tel-tip" placement="top-start" width="150" trigger="hover">
                      {{scope.row.mobile}}
                      <span slot="reference" class="iconfont icon-bohao iconfont-tel"></span>
                    </el-popover>
                  </div>
                </router-link>
              </template>
            </el-table-column>
            <el-table-column label="状态">
              <template slot-scope="scope">
                <p class="line">
                  <span class="dot dot-ok"></span>已结</p>
                <p class="line">
                  <span class="dot dot-wait"></span>未结</p>
              </template>
            </el-table-column>
            <el-table-column label="外援金额">
              <template slot-scope="scope">
                <p class="line">{{scope.row.yes_get_money}}元</p>
                <p class="line">{{scope.row.not_get_money}}元</p>
              </template>
            </el-table-column>
            <el-table-column label="外援单数">
              <template slot-scope="scope">
                <router-link class="line green" :to="{name: 'OutSettlementDetail',query:{type2: '4',type1: '2',companyName: scope.row.company,customer_id:scope.row.customer_id,customer_mobile: scope.row.mobile,customer_name:scope.row.name}}" tag="p">
                  {{scope.row.yes_get_count}}单
                  <span class="iconfont icon-jiantou2"></span>
                </router-link>
                <router-link class="line green" :to="{name: 'OutSettlementDetail',query:{type2: '5',type1: '2',companyName: scope.row.company,customer_id:scope.row.customer_id,customer_mobile: scope.row.mobile,customer_name:scope.row.name}}" tag="p">
                  {{scope.row.not_get_count}}单
                  <span class="iconfont icon-jiantou2"></span>
                </router-link>
              </template>
            </el-table-column>
            <el-table-column label="被借金额">
              <template slot-scope="scope">
                <p class="line">{{scope.row.yes_pay_money}}元</p>
                <p class="line">{{scope.row.not_pay_money}}元</p>
              </template>
            </el-table-column>
            <el-table-column label="被借单数">
              <template slot-scope="scope">
                <router-link class="line green" :to="{name: 'OutSettlementDetail',query:{type2: '4',type1: '3',companyName: scope.row.company,customer_id:scope.row.customer_id,customer_mobile: scope.row.mobile,customer_name:scope.row.name}}" tag="p">
                  {{scope.row.yes_pay_count}}单
                  <span class="iconfont icon-jiantou2"></span>
                </router-link>
                <router-link class="line green" :to="{name: 'OutSettlementDetail',query:{type2: '5',type1: '3',companyName: scope.row.company,customer_id:scope.row.customer_id,customer_mobile: scope.row.mobile,customer_name:scope.row.name}}" tag="p">
                  {{scope.row.not_pay_count}}单
                  <span class="iconfont icon-jiantou2"></span>
                </router-link>

              </template>
            </el-table-column>
            <el-table-column label="对冲金额">
              <template slot-scope="scope">
                <p class="line">
                  <span>{{scope.row.yes_hedging}}</span>
                  元
                </p>
                <p class="line">
                  <span :class="scope.row.not_hedging >= 0 ? 'red' : 'green' "> {{scope.row.not_hedging}}</span>
                  元
                </p>
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
  name: 'outSettlement',
  watch: {

  },
  data () {
    return {
      keywords: '',
      page: 1,
      maxPage: null,
      tableParams: {
        keywords: null
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
        this.getTableData()
      },
      deep: true
    }
  },
  created () {
    this.getTableData()
  },
  methods: {
    searchKeywords () {
      this.tableParams.keywords = this.keywords
    },
    getTableData () {
      let params, url
      params = Object.assign({}, this.tableParams, { page: this.page })
      url = this.$httpUrl.foreignList
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
    }
  }
}
</script>
<style lang="less" scoped>
.table .green {
  cursor: pointer;
}
.icon-jiantou2 {
  display: inline-block;
  transform: rotate(180deg);
  color: #999;
}
</style>