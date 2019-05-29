<template>
  <div class="log-main log-list">
    <div class='main-header'>
      <h3 class="single-tt">日志记录</h3>
    </div>
    <div class="main-content">
      <div class="handle-wrap">
        <div class="inp-wrap">
          <span class="iconfont icon-shujuchaxun iconfont-search"></span>
          <el-input class="inp-search" v-model="keywords" placeholder="请输入车调姓名/订单编号"></el-input>
        </div>
        <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
        <el-date-picker v-model="tableParams.time" value-format="yyyy-MM-dd" type="date" placeholder="选择年月">
        </el-date-picker>
      </div>
      <div class="table">
        <el-table key="travel" :data="tableData">
          <el-table-column prop="created_at" label="操作时间">
          </el-table-column>
          <el-table-column width="100px" prop="count" label="订单编号">
            <template slot-scope="scope">
              <div class="order-num color-green" @click="jumpDetails(scope.row.order_type,scope.row.order_id)">
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
          <el-table-column prop="address" label="时间">
            <template slot-scope="scope">
              <p>开始:{{scope.row.travel_begin_at}}</p>
              <p>结束:{{scope.row.travel_end_at}}</p>
            </template>
          </el-table-column>
          <el-table-column prop="travel_content" label="行程">
          </el-table-column>
          <el-table-column prop="address" label="操作记录">
            <template slot-scope="scope">
              <p>{{scope.row.count}}条</p>
            </template>
          </el-table-column>
          <el-table-column prop="address" label="操作">
            <template slot-scope="scope">
              <div class="btns">
                <router-link :to="{name: 'logDetail', query: {orderId: scope.row.order_id,orderType:scope.row.order_type}}" class="btn">查看详情</router-link>
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
  name: 'logList',
  data () {
    return {
      keywords: '',
      tableParams: {
        time: '',
        keywords: ''
      },
      page: 1,
      maxPage: null,
      tableData: []
    }
  },
  watch: {
    tableParams: {
      handler (val) {
        if (this.page != 1) {
          this.page = 1
        }
        this.getTableList();
      },
      deep: true
    },
  },
  created () {
    this.getTableList()
  },
  methods: {
    getTableList () {
      let params = {
        page: this.page
      };
      params = Object.assign({}, this.tableParams, params);
      this.$axios.get(this.$httpUrl.logList, { params: params }).then(res => {
        if (res != false) {
          this.tableData = res.list
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
      this.getTableList();
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
<style lang ="less">
.log-list {
  min-height: 100%;
  background-color: @bg-grey;
  .main-content {
    margin: 10px 16px 0;
    .handle-wrap {
      height: 70px;
      background-color: #fff;
      padding: 15px 20px;
      min-width: auto;
      .inp-wrap {
        &:first-of-type {
          margin-left: 0;
        }
      }
      & /deep/ .el-date-editor {
        float: left;
        margin-left: 20px;
        width: 140px;
        .el-input__inner {
          height: 32px;
          line-height: 32px;
        }
        .el-input__prefix,
        .el-input__suffix {
          top: -4px;
        }
      }
    }
    .table {
      padding-bottom: 100px;
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
}
</style>