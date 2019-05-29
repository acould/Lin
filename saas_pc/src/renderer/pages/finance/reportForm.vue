<template>
  <div class="report-form">
    <div class="main-header" ref="main-header">
      <div class="breadcrumb-wrap">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>财务管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'reportForm'}">报表</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <financeOptions ref="financeOptions"  option="5"></financeOptions>
    <div class="main-content">
      <div class="conditions" ref="conditions">
        <div class="inline-wrap">
          <span class="lb">付款状态</span>
          <el-select v-model="tableParams.bill_status" placeholder="请选择">
            <el-option v-for="item in orderStatusList" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </div>
        <div class="inline-wrap">
          <span class="lb">日期</span>
          <el-date-picker v-model="tableParams.begin_at" placeholder="开始日期" prefix-icon="''" type="date" value-format="yyyy-MM-dd">
          </el-date-picker>
          <span class="lb-center">至</span>
          <el-date-picker v-model="tableParams.end_at" placeholder="结束日期" prefix-icon="''" type="date" value-format="yyyy-MM-dd">
          </el-date-picker>
        </div>
        <div class="search-wrap">
          <el-input type="text" class="keyword-inp" v-model="keywords" placeholder="请输入司机名称/车牌号/客户名称"></el-input>
          <a href="javascript:void(0);" class="btn btn-search btn-nobg" @click="searchKeywords">搜索</a>
        </div>
        <a href="javascript:void(0)" class="btn btn-import" @click="downloadBillList">导出表格</a>
      </div>
      <div class="table">
        <el-table :data="tableData" style="width: 100%" :height="tableHeight">
          <el-table-column  label="订单编号" width="90" >
            <template slot-scope="scope">
              <div @click="jumpDetails(scope.row.order_type,scope.row.order_id)" class="color-green cursor">
                {{scope.row.order_num}}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="order_type" label="订单类型" width="90">
              <template slot-scope="scope">
                <p v-if="scope.row.order_type==1">包车</p>
                <p v-if="scope.row.order_type==2">接送</p>
                <p v-if="scope.row.order_type==3">班车</p>
              </template>
          </el-table-column>
          <el-table-column prop="travel_time" label="日期" width="90">
            <template slot-scope="scope">
               <p><span :class="timeCompare(scope.row.travel_begin_at)">{{ scope.row.travel_time }}</span></p>
            </template>
          </el-table-column>
          <el-table-column label="行程详情" align="center">
            <el-table-column prop="customer_company" label="客户">
            </el-table-column>
            <el-table-column prop="travel_content" label="行程">
            </el-table-column>
            <el-table-column prop="car_number" label="车辆" width="109">
            </el-table-column>
            <el-table-column prop="driver" label="司机">
            </el-table-column>
          </el-table-column>
          <el-table-column label="收费情况" align="center">
            <el-table-column prop="money" label="应收" width="60">
            </el-table-column>
            <el-table-column prop="deposit" label="现收" width="60">
            </el-table-column>
            <el-table-column prop="receivable" label="未收" width="60">
            </el-table-column>
            <el-table-column prop="name" label="结算" width="60">
              <template slot-scope="scope">
                <span :class="scope.row.bill_status == '已结' ? 'color-green' : 'color-yellow'">{{scope.row.bill_status}}</span>
              </template>

            </el-table-column>
          </el-table-column>
          <el-table-column label="外援车辆" align="center">
            <el-table-column prop="out_company" label="车队" width="100">
            </el-table-column>
            <el-table-column prop="foreign_money" label="应付" width="60">
            </el-table-column>
            <el-table-column prop="collection_money" label="代收" width="60">
            </el-table-column>
            <el-table-column prop="foreign_receivable" label="应收" width="60">
            </el-table-column>
            <el-table-column prop="foreign_bill_status" label="结算" width="60">
            </el-table-column>
          </el-table-column>
          <el-table-column prop="total_money" label="支出" width="70">
            <template slot-scope="scope">
              {{scope.row.total_money}}
              <el-popover popper-class="report-tip" placement="left" width="439" trigger="click">
                <div class="line-wrap">
                  <div class="inline">
                    <p>停车费</p>
                    <p>{{scope.row.park_money}}</p>
                  </div>
                  <div class="inline">
                    <p>过路费</p>
                    <p>{{scope.row.toll_money}}</p>
                  </div>
                  <div class="inline">
                    <p>油费</p>
                    <p>{{scope.row.oil_money}}</p>
                  </div>
                  <div class="inline">
                    <p>餐费</p>
                    <p>{{scope.row.meal_money}}</p>
                  </div>
                  <div class="inline">
                    <p>住宿费</p>
                    <p>{{scope.row.hotel_money}}</p>
                  </div>
                  <div class="inline">
                    <p>水费</p>
                    <p>{{scope.row.water_money}}</p>
                  </div>
                  <div class="inline">
                    <p>保养费</p>
                    <p>{{scope.row.maintain_money}}</p>
                  </div>
                  <div class="inline">
                    <p>维修费</p>
                    <p>{{scope.row.repair_money}}</p>
                  </div>
                  <div class="inline">
                    <p>其他</p>
                    <p>{{scope.row.other_money}}</p>
                  </div>
                </div>
                <span slot="reference" class="iconfont icon-jiantou2 iconfont-arrow"></span>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column prop="profit" label="利润" width="57">
          </el-table-column>
          <el-table-column prop="date" label="操作">
            <template slot-scope="scope">
              <!-- :to="{name: 'accountDetails',query: {order_id:scope.row.order_id,source:'bill'}}" -->
              <div @click="jumpDetails(scope.row.order_type,scope.row.order_id)" class="btn btn-view">
                查看详情
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <div class="pages-wrap">
      <el-pagination :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
    </div>
  </div>
</template>
<script>
export default {
  name: 'reportForm',
  data () {
    return {
      orderStatusList: [
        {
          value: '0',
          label: '全部'
        },
        {
          value: '1',
          label: '未付'
        },
        {
          value: '2',
          label: '已付'
        }
      ],
      tableParams: {
        bill_status: '',
        keywords: '',
        begin_at: '',
        end_at: ''
      },
      page: 1,
      maxPage: null,
      tableData: [],
      keywords: null,
      tableHeight: null
    }
  },
  watch: {
    tableParams: {
      handler () {
        if (this.page != 1) {
          this.page = 1
        }
        this.getReportData()
      },
      deep: true
    }
  },
  created () {
    this.getReportData()
  },
  beforeDestroy () {
    window.onresize = null
  },
  mounted () {
    this.setTableHight()
    window.onresize = this.$util.debounce(this.setTableHight, 500)
  },
  methods: {
    setTableHight () {
      this.tableHeight = this.$root.$el.clientHeight - this.$refs['main-header'].clientHeight -
      this.$refs['financeOptions'].$el.clientHeight - this.$refs['conditions'].clientHeight - 54 - 30 + 'px'
      console.log(this.tableHeight)
    },
      // 样式函数
    timeCompare (timestamp) {
      let curDate = new Date(new Date().toLocaleDateString()).getTime()
      let missionDate = new Date(new Date(timestamp * 1000).toLocaleDateString()).getTime()
      if (missionDate >= curDate) {
        return 'color-red'
      }
      return ''
    },
    getReportData () {
      let params, url
      params = Object.assign({}, this.tableParams, { page: this.page })
      url = this.$httpUrl.statement
      this.$axios.get(url, { params: params }).then(res => {
        if (res) {
          this.tableData = res.list
          this.maxPage = res.pages
        }
      })
    },
    searchKeywords () {
      this.tableParams.keywords = this.keywords
    },
    changePage (page) {
      this.page = page
      this.getReportData()
    },
    // 全部导出账单
    downloadBillList () {
      let paramsWrap = {
        params: this.tableParams
      }
      this.$axios.get(this.$httpUrl.downloadALLBillList, paramsWrap).then(res => {
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
    jumpDetails(order_type,order_id){
      if(order_type==1){
        this.$router.push({name: 'accountDetails', query: {order_id:order_id,source:'bill'}})
      }else if(order_type==2){
        this.$router.push({name: 'TransportStationDetail', query: {order_id:order_id,source:'bill'}})
      }else if(order_type==3){
        this.$router.push({name: 'shuttleBusDetail', query: {order_id:order_id,source:'bill'}})
      }
    }
  }
}
</script>
<style lang="less" scope>
.report-form {
  .cursor{
    cursor: pointer;
  }
  height: 100%;
  .main-content {
    margin: 0;
    padding: 0 16px;
    .conditions {
      border-bottom: none;
      overflow: hidden;
      font-size: 16px;
      padding: 8px 0;
      color: #000;
      width: 100%;
      .inline-wrap {
        float: left;
        height: 34px;
        margin-right: 10px;
        .lb {
          margin-right: 8px;
        }
        .el-select {
          width: 90px;
        }
        .el-date-editor {
          width: 100px;
        }
        .lb-center {
          margin: 0 6px;
        }
      }
      .search-wrap {
        float: left;
        margin-right: 10px;
        .btn-search {
          width: 77px;
          margin: 0 10px;
          border-color: #ccc;
          color: #666;
          float: none;
          display: inline-block;
        }
        .keyword-inp {
          width: 255px;
        }
      }
      .btn {
        float: left;
        &.btn-import {
          width: 90px;
        }
      }
    }
    .iconfont-arrow {
      display: inline-block;
      transform: rotate(-90deg);
    }
    .table {
       ::-webkit-scrollbar {
      height: 12px;
    }
    }
    .btn-view {
      width: 101px;
    }
  }
}
</style>
<style lang="less">
.report-form {
  .el-input__inner {
    height: 34px;
    line-height: 34px;
  }
  .inline-wrap {
    .el-input--prefix .el-input__inner {
      padding: 0 10px;
    }
  }
}
.report-tip {
  .line-wrap {
    overflow: hidden;
    .inline {
      float: left;
      width: 11.11%;
      text-align: center;
    }
  }
}
</style>
