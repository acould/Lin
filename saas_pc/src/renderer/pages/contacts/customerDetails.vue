<template>
  <div class="contacts-main" id="customerDetails">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'customerOrigin' }">通讯录</el-breadcrumb-item>
          <el-breadcrumb-item
            :to="{ name: 'customerOrigin',query: {customerType:customerType} }"
          >{{companyTypeTxt}}</el-breadcrumb-item>
          <el-breadcrumb-item>{{customerInfo.company}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <div class="order-info">
        <div class="info-t">
          <div class="left-side">
            <h3 class="order-num">基本信息</h3>
          </div>
          <div class="right-side">
            <div class="btns">
              <a href="javascript:void(0)" @click="lookImage()" class="btn btn-bg lookImg">查看合同</a>
              <a href="javascript:void(0)" class="btn btn-bg" @click="orderHandler(1)">修改</a>
              <a href="javascript:void(0)" class="btn btn-fffbg" @click="orderHandler(2)">删除</a>
            </div>
          </div>
        </div>
        <div class="info-b" v-if="customerType == 1">
          <div class="info-list">
            <div class="col">
              <p class="line">单位名称：{{customerInfo.company}}</p>
              <p class="line">客户身份：{{customerInfo.type_text}}</p>
              <p class="line">查看轨迹权限：{{customerInfo.enable_see_history ? '允许' : '不允许'}}</p>
            </div>
            <div class="col">
              <p class="line">发票抬头：{{customerInfo.invoice_title}}</p>
              <p class="line">税 号：{{customerInfo.tax_number}}</p>
              <p class="line">联系地址：{{customerInfo.address}}</p>
            </div>
            <div class="row" v-for="(field, index) in  definable_fields" :key="index">
              {{field.name}}：
              <span v-if="field.type_id!=6">
                {{field.value}}
              </span> 
              <span  v-if="field.type_id==6">
                <span  v-for="(itemImg,indexS) in field.values" :key="indexS+'img'">
                   <img class="images" @click="checkImg(itemImg)" :src="itemImg" alt="">
                </span>
               
              </span>
            </div>
          </div>
          <!-- <p class="remark">联系地址：{{customerInfo.address}}</p> -->
        </div>
        <div class="info-b" v-else>
          <div class="info-list">
            <div class="col">
              <p class="line">车队名称：{{customerInfo.company}}</p>
              <p class="line">计调姓名：{{customerInfo.name}}</p>
              <p class="line">客户身份：{{customerInfo.type_text}}</p>
              <p class="line">联系地址：{{customerInfo.address}}</p>
            </div>
            <div class="col">
              <p class="line">发票抬头：{{customerInfo.invoice_title}}</p>
              <p class="line">联系方式：{{customerInfo.mobile}}</p>
              <p class="line">税 号：{{customerInfo.tax_number}}</p>
            </div>
          </div>
        </div>
      </div>
      <div class="container travel-container" v-if="customerType == 1">
        <div class="handler">
          <div class="inp">
            <el-input
              class="inp-search"
              suffix-icon="iconfont icon-shujuchaxun"
              v-model="keywords"
              placeholder="请输入计调名称"
            ></el-input>
            <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
          </div>
          <a href="javascript:void(0);" class="btn btn-import" @click="downloadTableList">全部导出</a>
          <router-link :to="{name: 'IncreaseOperator',query: queryParams}" class="btn btn-add">添加计调</router-link>
        </div>
        <div class="table">
          <el-table key="car" v-if="tableData.length" :data="tableData" style="width: 100%">
            <el-table-column prop="name" label="计调姓名"></el-table-column>
            <el-table-column prop="mobile" label="联系方式"></el-table-column>
            <el-table-column prop="driver_name" label="自助下单权限">
              <template slot-scope="scope">{{scope.row.enable_create_order == 1 ? '是' : '否'}}</template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <div class="btns">
                  <a href="javascript:void(0);" class="btn" @click="handlerOperator(1,scope.row)">修改</a>
                  <a
                    href="javascript:void(0);"
                    class="btn btn-nobg"
                    @click="handlerOperator(2,scope.row)"
                  >删除</a>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="container car-container" v-else>
        <div class="handler">
          <el-radio-group v-model="carParams.seat_num">
            <el-radio label>全部</el-radio>
            <el-radio label="5,10">5-10座</el-radio>
            <el-radio label="11,25">11-25座</el-radio>
            <el-radio label="26,40">26-40座</el-radio>
            <el-radio label="41,70">41-70座</el-radio>
          </el-radio-group>
          <div class="inp">
            <el-input
              class="inp-search"
              v-model="keywords"
              placeholder="请输入车牌号"
            ></el-input>
            <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
          </div>
          <div class="date-warp">
            <el-date-picker
              ref="datePicker"
              v-model="dateArr"
              @change="getDate"
              type="daterange"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              class="seldate"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </div>
          <router-link
            :to="{name: 'IncreaseOutsideCar',query: queryParams}"
            class="btn add-car btn-add"
          >添加车辆</router-link>
        </div>
        <div class="table">
          <el-table key="car" v-if="tableData.length" :data="tableData" style="width: 100%">
            <el-table-column label="车牌号" width="110" fixed align="center">
              <template slot-scope="scope">
                <router-link
                  tag="p"
                  class="color-green car-number"
                  :to="{name: 'carDetails',query: {car_id:scope.row.car_id,type:2}}"
                >{{scope.row.car_number}}</router-link>
                <p>{{scope.row.car_type}}</p>
                <p>{{scope.row.seat_num}}座</p>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              :label="day.text_pc+ ''+ day.week"
              v-for="(day, index) in days"
              :key="index"
            >
              <template slot-scope="scope">
                <div class="curdateschedule">
                  <p
                    @click="toggleArrangeStatus((tableData[scope.$index].days)[index],tableData[scope.$index])"
                  >
                    <span
                      class="arrange-status arrange-status-free"
                      v-if="(tableData[scope.$index].days)[index].is_busy == 0"
                    >闲</span>
                    <span
                      class="arrange-status arrange-status-busy"
                      v-else-if="(tableData[scope.$index].days)[index].is_busy == 1"
                    >忙</span>
                    <span class="arrange-status arrange-status-halfbusy" v-else>忙</span>
                  </p>
                  <p
                    class="bottom-txt"
                    @click="taskRtn((tableData[scope.$index].days)[index].is_busy, (tableData[scope.$index].days)[index].text_pc,(tableData[scope.$index].days)[index].date,tableData[scope.$index])"
                  >
                    <span
                      v-if="(tableData[scope.$index].days)[index].is_busy == 0"
                      class="grey limitlen"
                    >添加任务</span>
                    <span
                      v-else
                      class="limitlen"
                    >{{(tableData[scope.$index].days)[index].customer_name}}</span>
                  </p>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="110" align="center">
              <template slot-scope="scope">
                <router-link
                  :to="{name: 'carArrange',query: {car_number: scope.row.car_number, car_id: scope.row.car_id}}"
                >查看更多</router-link>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="pages-wrap" v-if="tableData.length && customerType == 1">
        <el-pagination
          :current-page="page"
          @current-change="changePage"
          :page-count="maxPage"
          background
          layout="prev, pager, next"
        ></el-pagination>
      </div>
       <div class="pages-wrap" v-if="tableData.length && customerType == 2">
        <el-pagination
          :current-page="page"
          @current-change="changePage"
          :page-count="maxPage"
          background
          layout="prev, pager, next"
        ></el-pagination>
      </div>
      <dataBlank
        word="计调"
        :nobtn="false"
        v-if="!tableData.length && customerType == 1 && maxPage === 0"
        data-type="customer"
      ></dataBlank>
      <dataBlank
        :nobtn="false"
        v-if="!tableData.length && customerType == 2 && maxPage === 0"
        data-type="car"
      ></dataBlank>
    </div>
    <imgDialog ref="imgDialog" :imgShow.sync='imgShow' :imgs='imgs'></imgDialog> 
  </div>
</template>

<script>
import imgDialog from '@/components/imgDialog'
export default {
  name: 'customerDetails',
  data () {
    return {
      companyTypeTxt: '',
      customerType: null,
      customerInfo: {
        
      },
      definable_fields:[],
      page: 1,
      maxPage: null,
      customer_id: '',
      keywords: '',
      carParams: {
        keywords: '',
        start_time: '',
        end_time: '',
        seat_num: null,
        customer_id: '',
      },
      travelParams: {
        customer_id: '',
        keywords: ''
      },
      tableData: [],
      days: [],
      dateArr: [],
      imgShow:false,
      imgs:''
    }
  },
  watch: {
    carParams: {
      handler () {
        if (this.page != 1) {
          this.page = 1
        }
        this.getTableData()
      },
      deep: true
    },
    travelParams: {
      handler () {
        if (this.page != 1) {
          this.page = 1
        }
        this.getTableData()
      },
      deep: true
    }
  },
  computed: {
    queryParams () {
      return { customer_id: this.customer_id, companyName: this.customerInfo.company }
    }
  },
  created () {
    this.getQueryParams()
    
  },
  mounted () {
    this.getCustomerDetail()
  },
  methods: {
    getQueryParams () {
      let query = this.$route.query
      let customerType = query.customerType
      this.customerType = customerType
      this.customer_id = query.customer_id
      if (customerType == 1) {
        this.companyTypeTxt = '客户'
        this.travelParams.customer_id = query.customer_id
      } else {
        this.companyTypeTxt = '外援车队'
        this.carParams.customer_id = query.customer_id
      }
    },
    /* 
      处理订单的修改以及删除
      type 1 修改 2删除
    */
    orderHandler (type) {
      let customer_id = this.customer_id
      if (type == 1) {
        this.$router.push({ name: 'increaseCustomer', query: { customerType: this.customerType, customer_id: customer_id } })
      } else {
        this.$confirm('删除该车队时，与车队相关的车辆数据都将删除且无法恢复，确定删除吗？', {
          type: 'warning'
        }).then(() => {
          let params = { customer_id: customer_id }
          this.$axios.get(this.$httpUrl.deleteCustomer, { params: params }).then(res => {
            if (res != false) {
              this.$router.push({ name: 'customerOrigin', query: { customerType: this.customerType } })
            }
          })
        })
      }
    },
    handlerCar (type, info) {
      let car_id = info.car_id
      if (type == 1) {
        let params = {};
        Object.assign(params, this.queryParams, info)
        this.$router.push({ name: 'IncreaseOutsideCar', query: params })
      } else if (type == 2) {
        this.$confirm('是否确定删除该车辆', {
          type: 'warning'
        }).then(() => {
          let params = { car_id: car_id }
          this.$axios.get(this.$httpUrl.deleteCar, { params: params }).then(res => {
            if (res != false) {
              this.getTableData()
            }
          })
        })
      } else {
        let date = this.$util.formatTime().date
        let params = {
          driver_id: info.driver_id,
          driver_name: info.driver_name,
          driver_mobile: info.driver_mobile,
          car_number: info.car_number,
          car_id: info.car_id,
          date: date,
          car_source: 2
        }
        this.$router.push({ name: 'addMission', query: params })
      }
    },
    handlerOperator (type, info) {
      let customer_id = this.customer_id
      if (type == 1) {
        let params = {};
        Object.assign(params, this.queryParams, info, { customer_yardman_id: info.id })
        this.$router.push({ name: 'IncreaseOperator', query: params })
      } else {
        this.$confirm('是否确定删除该计调', {
          type: 'warning'
        }).then(() => {
          let params = { customer_yardman_id: info.id, customer_id: this.customer_id }
          this.$axios.get(this.$httpUrl.yardmenDelete, { params: params }).then(res => {
            if (res != false) {
              this.getTableData()
            }
          })
        })
      }
    },
    searchFrom () {
      let customerType = this.customerType
      let keywords = this.keywords
      if (customerType == 1) {
        Object.assign(this.travelParams, { keywords })
      } else {
        Object.assign(this.carParams, { keywords })
      }
    },
    getCustomerDetail () {
      let customer_id = this.$route.query.customer_id
      let params = {
        customer_id: customer_id
      }
      this.$axios.get(this.$httpUrl.customerInfo, { params: params }).then(res => {
        if (res != false) {
          this.customerInfo = res
          for(let J=0;J<res.definable_fields.length;J++){
            if(res.definable_fields[J].type_id==6){
              res.definable_fields[J].values = res.definable_fields[J].value.split(',')
            }
          }
          this.definable_fields = res.definable_fields
        }
      })
    },
    getTableData () {
      let params, url
      let customerType = this.customerType
      params = {}
      params.page = this.page
      if (customerType == 1) {
        Object.assign(params, this.travelParams)
        url = this.$httpUrl.yardmenList
      } else {
        Object.assign(params, this.carParams)
        url = this.$httpUrl.scheduleList
      }
      this.$axios.get(url, { params: params }).then(res => {
        if (res != false) {
          let list = res.list
          if (customerType == 1) {
            this.maxPage = res.pages
            this.tableData = list
          } else {
            let arr = []
            this.maxPage = res.pages
            if (list.length != 0) {
              list.forEach(ele => {
                let obj = {}
                Object.assign(obj, ele.car_info)
                obj.days = ele.days
                arr.push(obj)
              })
              this.tableData = arr
              this.days = arr[0].days
            } else {
              this.tableData = []
            }
          }
        }
      })
    },
    taskRtn (status, date, ymd, carInfo) {
      // 0=闲，1=忙，2=半忙
      if (status == 0) {
        this.$router.push({ name: 'addMission', query: { car_source: 2,car_number: carInfo.car_number, car_id: carInfo.car_id, date: ymd, driver_id: carInfo.driver_id, driver_mobile: carInfo.driver_mobile, driver_name: carInfo.driver_name } })
      } else if (status == 1) {
        this.$router.push({ name: 'arrangeDetails', query: { car_number: carInfo.car_number, car_id: carInfo.car_id, date: ymd } })
      } else {
        this.$router.push({ name: 'carArrange', query: { car_source:2,car_number: carInfo.car_number, car_id: carInfo.car_id, yMd: ymd } })
        // this.scheduleInfo = Object.assign({}, carInfo, { date: ymd, textDate: date, car_number: carInfo.car_number, car_id: carInfo.car_id, })
        // this.scheduleBoxShow = true
      }
    },
    // 切换忙闲状态
    toggleArrangeStatus (day, carInfo) {
      let status = day.is_busy
      if (status == 2) return
      let willStatusText = status == 0 ? '忙' : '闲'
      this.$confirm(`您是否将${carInfo.car_number}车辆${day.text_pc}改为${willStatusText}`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let params = {
          car_id: carInfo.car_id,
          date: day.date,
          type: status == 0 ? 1 : 2
        }
        this.$axios.get(this.$httpUrl.busyChange, { params: params }).then(res => {
          if (res) {
            this.$message.success('操作成功')
            this.getTableData()
          }
        })
      }).catch(() => {
        console.log('取消操作')
      });
    },
    changePage (page) {
      this.page = page
      this.getTableData()
    },
    downloadTableList () {
      let params = {
        customer_id: this.customer_id
      }
      Object.assign(params, this.travelParams)
      this.$axios.get(this.$httpUrl.yardmenListDownload, { params: params }).then(res => {
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
    lookImage () {
      // let customer_id =this.$router.query.customer_id
      if (this.$route.query.customerType == 1) {
        this.$router.push({ name: 'imageDetails', query: { customer_id: this.$route.query.customer_id, customerType: 1 } })
      } else if (this.$route.query.customerType == 2) {
        this.$router.push({ name: 'imageDetails', query: { customer_id: this.$route.query.customer_id, customerType: 2 } })
      }
    },
    getDate (val) {
      let start_time = null, end_time = null
      if (Array.isArray(val)) {
        start_time = val[0]
        end_time = val[1]
      }
      Object.assign(this.carParams, { start_time: start_time , end_time: end_time })
    },
    checkImg(img){
    //  console.log(img)
     this.imgShow =true
     this.$nextTick(()=>{
       this.$refs.imgDialog.showToggle(img)
     })
   }  
  },
  components: {
    imgDialog
  }
}
</script>
<style lang="less">
#customerDetails {
  .date-warp {
    display: inline-block;
    margin-right: 10px;
    .el-date-editor {
      width: 260px !important;
      .el-input__icon {
        line-height: 1;
      }
      .el-range-separator {
        line-height: 24px;
      }
      &.el-input__inner {
        height: 32px;
        line-height: 32px;
        padding-right: 0;
      }
    }
  }
}
</style>
<style lang ="less" scoped>
.contacts-main {
  height: 100%;
  background-color: #f5f5f5;
}
.main-content {
  padding-top: 20px;
  height: calc(100% - 80px);
  box-sizing: border-box;
  background-color: transparent;
  .order-info {
    padding: 20px 30px 0 30px;
    background-color: #fff;
    .info-t {
      overflow: hidden;
    }
    .btn-fffbg {
      margin-right: 0;
    }
    .left-side {
      float: left;
      margin-bottom: 20px;
      .icon-dingdan {
        color: @theme-color;
        font-size: 22px;
        vertical-align: -2px;
        margin-right: 14px;
      }
      .order-num {
        font-size: 20px;
        color: #000;
        font-weight: bold;
        display: inline-block;
      }
    }
    .right-side {
      float: right;
    }
    .info-b {
      position: relative;
      .info-list {
        padding-left: 42px;
        width: 66.67%;
        overflow: hidden;
      }
      .col {
        float: left;
        width: 50%;
        .line {
          margin-bottom: 10px;
        }
      }
      .row {
        float: left;
        width: 50%;
        margin-bottom: 10px;
      }
      .summary {
        position: absolute;
        top: 10px;
        right: 0;
        p {
          float: left;
          text-align: right;
          color: #333;
          margin-left: 30px;
          span {
            margin-top: 10px;
            display: block;
            color: #000;
            font-size: 20px;
          }
        }
      }
    }
  }
  #blank-wrap {
    top: 150px;
  }
  .container {
    margin: 10px 16px 0;
    background-color: #fff;
    padding: 0 10px;
    &.car-container {
      min-height: calc(100% - 180px);
      .handler {
        overflow: hidden;
        margin-left: 10px;
        & /deep/ .el-radio-group {
          display: inline-block;
          .el-radio + .el-radio {
            margin-left: 10px;
          }
          .el-radio .el-radio__input.is-checked .el-radio__inner {
            background-color: @theme-color;
            border-color: @theme-color;
          }
        }
        .inp {
          display: inline-block;
          margin-left: 10px;
          & /deep/ .inp-search {
            width: 160px;
            .el-input__inner {
              height: 32px;
              line-height: 32px;
            }
            .el-input__suffix {
              top: -4px;
            }
          }
          .btn-search {
            display: inline-block;
            margin-right: 10px;
            height: 32px;
            line-height: 30px;
            width: 60px;
            box-sizing: border-box;
            text-align: center;
            border-radius: 4px;
            color: @color-dark-grey;
            font-size: @fontsize-medium;
            border: 1px solid #999;
          }
        }
      }
      .arrange-status {
        display: inline-block;
        border-radius: 50%;
        color: @color-white;
        font-size: @fontsize-small;
        width: 18px;
        height: 18px;
        padding: 1px;
        text-align: center;
        line-height: 18px;
        &-busy {
          background-color: @red;
        }
        &-free {
          background: @theme-color;
        }
        &-halfbusy {
          background: @yellow;
        }
      }
      .curdateschedule,
      .car-number {
        cursor: pointer;
      }
    }
    &.travel-container {
      min-height: calc(100% - 150px);
      .handler {
        .inp {
          display: inline-block;
          margin-left: 10px;
          & /deep/ .inp-search {
            width: 272px;
            .el-input__inner {
              height: 32px;
              line-height: 32px;
            }
            .el-input__suffix {
              top: -4px;
            }
          }
          .btn-search {
            display: inline-block;
            margin: 0 20px 0 10px;
            height: 32px;
            line-height: 30px;
            width: 60px;
            box-sizing: border-box;
            text-align: center;
            border-radius: 4px;
            color: @color-dark-grey;
            font-size: @fontsize-medium;
            border: 1px solid #999;
          }
        }
        .btn-import {
          display: inline-block;
          width: 80px;
          margin: 0 30px 0 10px;
        }
      }
    }
    .handler {
      /* height: 32px; */
      line-height: 32px;
      padding: 16px 0;
      min-width: 800px;
      .btn-add {
        width: 110px;
        height: 32px;
        display: inline-block;
      }
    }
    .table {
      padding: 0 10px;
      & /deep/ .el-table {
        min-width: auto;
        .el-table__header {
          thead {
            th {
              background-color: #fafafa;
              .cell {
                font-weight: bold;
                span {
                  font-weight: bold;
                }
              }
            }
          }
        }
      }
    }
  }
  .remark {
    margin-left: 42px;
    padding-bottom: 16px;
    word-break: break-all;
  }
  .lookImg {
    width: 119px;
  }
  .images{
    width:40px;
    height: 40px;
    display: inline-block;
    margin-left:10px;
    vertical-align: middle;
  }
}
</style>
