<template>
  <div class="log-main log-detail">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'logList'}">操作日志</el-breadcrumb-item>
          <el-breadcrumb-item>操作详情</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <div class="order-info">
        <div class="info-t">
          <div class="left-side">
            <span class="iconfont icon-dingdan"></span>
            <div  class="order-num" @click="jumpDetails(tableParams.order_type,tableParams.saas_order_id)">
              订单编号：
              <span class="color-green">{{orderInfo.order_num}}</span>
            </div>
          </div>
          <div class="right-side">
            <div class="btns">
                <div class="btn" @click="jumpDetails(tableParams.order_type,tableParams.saas_order_id)">
              查看详情
            </div>
            </div>
          </div>
        </div>
        <div class="info-b">
          <div class="info-list">
            <div class="col">
              <p class="line">开始时间：{{orderInfo.travel_begin_at}}</p>
            </div>
            <div class="col">
              <p class="line">预计结束时间：{{orderInfo.travel_end_at}}</p>
            </div>
          </div>
          <p class="remark">行程：{{orderInfo.travel_content}}</p>
        </div>
      </div>
      <div class="log-container container">
        <div class="handler">
          <div class="inp">
            <el-input class="inp-search" suffix-icon="iconfont icon-shujuchaxun" v-model="keywords" placeholder="请输入车调姓名"></el-input>
            <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
          </div>
        </div>
        <div class="table">
          <el-table key="car" :data="tableData" style="width: 100%">
            <el-table-column prop="user_name" width="150px" label="操作人员">
            </el-table-column>
            <el-table-column prop="created_at" width="180px" label="操作时间">
            </el-table-column>
            <el-table-column prop="content" label="操作记录">
            </el-table-column>
          </el-table>
        </div>
        <div class="pages-wrap" v-if="tableData.length">
          <el-pagination :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'logDetail',
  data () {
    return {
      keywords: '',
      tableData: [],
      tableParams: {
        saas_order_id: null,
        keywords: null,
        order_type:null
      },
      page: 1,
      maxPage: null,
      tableData: [],
      orderInfo: {}
    }
  },
  watch: {
    tableParams: {
      handler (newVal) {
        if (this.page != 1) {
          this.page = 1
        }
        this.getCarDetail()
      },
      deep: true
    },
  },
  created () {
    this.getQueryParams()
  },
  methods: {
    // 查询参数
    getQueryParams () {
      let query = this.$route.query
      if (query.orderId) this.tableParams.saas_order_id = query.orderId
      if (query.orderType) this.tableParams.order_type = query.orderType
    },
    // 获取表格数据
    getCarDetail () {
      let params = {
        page: this.page
      };
      params = Object.assign({}, this.tableParams, params);
      this.$axios.get(this.$httpUrl.logOrderList, { params: params }).then(res => {
        if (res != false) {
          this.tableData = res.list
          this.orderInfo = res.order
          this.maxPage = res.pages;
        }
      })
    },
    // 查询关键词
    searchFrom () {
      this.tableParams.keywords = this.keywords
    },
    changePage (page) {
      this.page = page;
      this.getCarDetail();
    },
    jumpDetails(order_type,order_id){

      if(this.$route.query.orderType==1){
        this.$router.push({name: 'accountDetails', query: {order_id:order_id,source:'bill'}})
      }else if(this.$route.query.orderType==2){
        this.$router.push({name: 'TransportStationDetail', query: {order_id:order_id,source:'bill'}})
      }else if(this.$route.query.orderType==3){
        this.$router.push({name: 'shuttleBusDetail', query: {order_id:order_id,source:'bill'}})
      }
    }
  }
}
</script>
<style lang ="less" scoped>
.main-content {
  margin-top: 15px;
  background-color: transparent;
  min-width: 800px;
  overflow-x: hidden;
  min-height: calc(100% - 160px);
  .order-info {
    padding: 20px 30px 0 30px;
    background-color: #fff;
    height: 179px;
    .info-t {
      overflow: hidden;
    }
    .btn-bg {
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
        cursor: pointer;
      }
    }
    .right-side {
      float: right;
    }
    .info-b {
      position: relative;
      .info-list {
        margin-left: 42px;
        overflow: hidden;
      }
      .col {
        float: left;
        width: 33.33%;
        .line {
          margin-bottom: 10px;
        }
      }
    }
  }
  .remark {
    margin-left: 42px;
    padding-bottom: 16px;
    word-break: break-all;
  }
  .log-container {
    margin: 10px 16px 0;
    background-color: #fff;
    padding: 0 10px;
    min-height: calc(100% - 150px);
    .handler {
      height: 32px;
      line-height: 32px;
      padding: 16px 0;
      min-width: 800px;
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
  }
}
</style>
<style lang="less">
.log-detail {
  .handler {
    .inp {
      display: inline-block;
      margin-left: 10px;
      .inp-search {
        width: 272px;
        .el-input__inner {
          height: 32px;
          line-height: 32px;
        }
        .el-input__suffix {
          top: -4px;
        }
      }
    }
  }
  .table {
    padding: 0 10px;
    .el-table {
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
</style>