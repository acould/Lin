<template>
  <div class="contacts-main driverOrigin">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>全部订单</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <div class="handle-wrap">
        <el-select v-if='this.$route.query.salesmanId' v-model="selectVal" @change="selectSalesman" placeholder="请选择业务员" class="select-salesman">
          <el-option v-for="(item,index) in salesmanList" :key="item.salesman_id" :label="item.name" :value="item.salesman_id">
          </el-option>
        </el-select>
        <el-select v-if='this.$route.query.customer_id' v-model="company" @change="selectCustomer" placeholder="请选择单位名称" class="select-salesman">
          <el-option v-for="(item,index) in customerList" :key="item.customer_id" :label="item.company" :value="item.customer_id">
          </el-option>
        </el-select>
        <el-select v-if='this.$route.query.driver_id' v-model="driver" @change="selectDriver" placeholder="请选择司机姓名" class="select-salesman">
          <el-option v-for="(item,index) in DriverList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
        <div class="date-wrap">
          <el-date-picker class="el-date" v-model="tableParams.date" type="month" value-format="yyyy-MM" placeholder="选择年月">
          </el-date-picker>
        </div>
        <div class="btn-wrap">
          <a href="javascript:void(0)" class="btn btn-export" @click="downloadTableList">全部导出</a>
        </div>
      </div>
      <div class="table" v-if="tableData.length">
        <el-table :data="tableData">
          <el-table-column label="订单编号" width="130">
            <template slot-scope="scope">
              <span  @click="jumpDetails(scope.row.order_type,scope.row.order_id)" class="color-green">{{scope.row.order_num}}</span>
            </template>
          </el-table-column>
          <el-table-column label="订单类型">
            <template slot-scope="scope">
              <p v-if="scope.row.order_type==1">包车</p>
              <p v-if="scope.row.order_type==2">接送</p>
              <p v-if="scope.row.order_type==3">班车</p>
            </template>
          </el-table-column>
          <el-table-column label="营收" width="90">
            <template slot-scope="scope">
              <span class="color-red">{{scope.row.money}}</span>元
            </template>
          </el-table-column>
          <el-table-column label="时间" width="130">
            <template slot-scope="scope">
              <p>开始:{{scope.row.travel_begin}}</p>
              <p>结束:{{scope.row.travel_end}}</p>
            </template>
          </el-table-column>
          <el-table-column prop="travel_content" label="行程">
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
          <el-table-column label="司机" width="100">
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
          <el-table-column label="状态" width="90">
            <template slot-scope="scope">
              <p v-if="scope.row.bill_status == 2">
                <span class="dot dot-ok"></span>已结</p>
              <p v-else>
                <span class="dot dot-wait"></span>未结</p>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <div class="btns">
                <div @click="jumpDetails(scope.row.order_type,scope.row.order_id)" class="btn btn-nobg">查看详情</div>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pages-wrap" v-if="tableData.length">
        <el-pagination :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'salesmanOrder',
  data () {
    return {
      identityType: 'salesmanOrder',
      tableParams: {
        date: '',
        salesman_id: null,
        customer_id:null,
        driver_id:null,
        driver_mobile:null
      },
      page: 1,
      maxPage: null,
      tableData: [],
      salesmanList: [],
      selectVal: '',
      customerList:[],
      company:'',
      DriverList:[],
      driver:''
    }
  },
  watch: {
    tableParams: {
      handler (val) {
        if (this.page != 1) {
          this.resetPage()
        }
        this.getTableList()
      },
      deep: true
    },
  },
  created () {
    this.getQueryParams()
   
  },
  mounted(){
    if(this.$route.query.salesmanId){
      this.getSalesmanList()
    } 
    if(this.$route.query.customer_id){
      this.getCustomerList()
    }
    if(this.$route.query.driver_id){
      this.getDriverList()
    }
  },
  methods: {
    // 查询参数
    getQueryParams () {
      let query = this.$route.query
      if (query.salesmanId) this.tableParams.salesman_id = query.salesmanId
      if (query.salesmanName) this.selectVal = query.salesmanName
      if(query.customer_id) this.tableParams.customer_id =query.customer_id
      if(query.company) this.company =query.company
      if(query.driver_id) this.tableParams.driver_id =query.driver_id
      if(query.driver_mobile) this.tableParams.driver_mobile =query.driver_mobile
      if(query.driver_name) this.driver=query.driver_name
    },
    resetPage () {
      this.page = 1
      this.tableData = []
    },
    selectSalesman (val) {
      this.tableParams.salesman_id = val
    },
    selectCustomer(val){
      this.tableParams.customer_id = val
    },
    selectDriver(val){
      this.tableParams.driver_id = val
    },
    getSalesmanList () {
      let params = {
        paginate: 9999
      }
      this.$axios.get(this.$httpUrl.salesmanList, { params: params }).then(res => {
        if (res != false) {
          this.salesmanList = res.list
        }
      })
    },
    // 获取客户列表
    getCustomerList(){
      let params = {
        paginate: 9999,
        type:this.$route.query.type
      }
      this.$axios.get(this.$httpUrl.customerList, { params: params }).then(res => {
        if (res != false) {
         this.customerList =res.list
        }
      })
    },
    getDriverList () {
      let params = {
        keywords:'',
        paginate:9999
      }
      this.$axios.get(this.$httpUrl.keywordsDriver, { params: params }).then(res => {
        if (res != false) {
          this.DriverList = res
        }
      })
    },
    getTableList () {
      let params = {
        page: this.page
      }
      params = Object.assign({}, this.tableParams, params)
      this.$axios.get(this.$httpUrl.orderList, { params: params }).then(res => {
        if (res != false) {
          this.maxPage = res.pages
          this.tableData = res.list
        }
      })
    },
    downloadTableList () {
      this.$axios.get(this.$httpUrl.salesmanOrderDownload,{params: this.tableParams}).then(res => {
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
    changePage (page) {
      this.page = page
      this.getTableList()
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
<style lang ="less" scoped>
@import "../contacts/origin.less";
.driverOrigin {
  min-height: 100%;
  background-color: @bg-grey;
}
.main-content {
  margin: 10px 16px 0;
  .handle-wrap {
    background-color: #fff;
    .btn-wrap {
      &.download {
        width: 120px;
      }
    }
    .date-wrap {
      float: left;
      margin-left: 10px;
      .el-date {
        width: 120px;
      }
    }
  }
  .table {
    .money {
      color: #ff5656;
    }
    .car-source {
      padding: 1px 5px;
      &.out-car {
        background-color: #ff991f;
        color: #fff;
        border-radius: 2px;
      }
      &.pay {
        background-color: #26b9e7;
        color: #fff;
        border-radius: 2px;
      }
    }
    .dot {
      padding: 3px;
      border-radius: 50%;
      font-size: 0;
      vertical-align: middle;
      margin-right: 10px;
      &.dot-ok {
        background-color: #0db592;
      }
      &.dot-wait {
        background-color: #ff991f;
      }
      &.dot-fail {
        background-color: #ff5656;
      }
    }
    .iconfont-wrap {
      display: inline-block;
      .iconfont-tel {
        color: @theme-color;
        font-size: 24px;
        vertical-align: -4px;
        padding: 2px;
        cursor: pointer;
      }
      .iconfont-arrow {
        display: inline-block;
        transform: rotate(-90deg);
        cursor: pointer;
      }
    }
  }
}
</style>
<style lang="less">
.driverOrigin {
  .select-salesman {
    float: left;
    width: 138px;
    margin: 0 10px 0 20px;
    .el-input__inner {
      height: 32px;
      line-height: 32px;
    }
  }
  .handle-wrap {
    .el-date {
      .el-input__inner {
        height: 32px;
        line-height: 32px;
      }
      .el-input__prefix,
      .el-input__suffix {
        top: -3px;
      }
    }
  }
}
</style>
