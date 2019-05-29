<template>
  <div class="contacts-main driverOrigin">
    <div class="main-header">
      <div class="tab-wraps">
        <el-tabs v-model="identityType">
          <el-tab-pane label="客户" name="customerOrigin"></el-tab-pane>
          <el-tab-pane label="司机" name="driverOrigin"></el-tab-pane>
          <el-tab-pane label="同事/车调" name="operatorOrigin"></el-tab-pane>
          <el-tab-pane label="业务员" name="salesmanList"></el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div class="main-content">
      <div class="handle-wrap">
        <div class="date-wrap">
          <el-date-picker class="el-date" v-model="tableParams.time" type="month" value-format="yyyy-MM" placeholder="选择年月">
          </el-date-picker>
        </div>
        <div class="inp-wrap">
          <span class="iconfont icon-shujuchaxun iconfont-search"></span>
          <el-input class="inp-search" v-model="keywords" placeholder="请输入司机姓名/联系方式"></el-input>
        </div>
        <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
        <div class="btn-wrap">
          <a href="javascript:void(0)" class="btn btn-export" @click="downloadTableList">全部导出</a>
        </div>
        <div class="btn-wrap">
          <router-link :to="{name: 'increaseDriver'}" class="btn add-driver">添加司机</router-link>
        </div>
        <div class="btn-wrap download">
          <a href="javascript:void(0)" class="btn" @click="openAppDownloadBox">司机APP下载</a>
        </div>
      </div>
      <dataBlank v-if="!tableData.length && maxPage === 0" dataType="driver"></dataBlank>
      <div class="table" v-if="tableData.length">
        <el-table :data="tableData">
          <el-table-column prop="driver_name" label="姓名" width="150">
          </el-table-column>
          <el-table-column prop="driver_mobile" label="联系方式" width="140">
          </el-table-column>
          <el-table-column prop="car_numbers" label="驾驶车辆">
          </el-table-column>
          <el-table-column prop="order_count" label="订单数">
            <template slot-scope="scope">
                <router-link class="color-green" :to="{name: 'salesmanOrder', query: {driver_id: scope.row.driver_id,driver_mobile:scope.row.driver_mobile,driver_name:scope.row.driver_name}}" >{{scope.row.order_count}}</router-link>
            </template>
          </el-table-column>
          <el-table-column prop="address" label="操作">
            <template slot-scope="scope">
              <div class="btns">
                <router-link :to="{name: 'driverDetails', query: {driver_id: scope.row.driver_id}}" class="btn">查看</router-link>
                <router-link :to="{path: 'increaseDriver', query: {driver_id: scope.row.driver_id}}" class="btn">修改</router-link>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pages-wrap" v-if="tableData.length">
        <el-pagination :current-page="page"  @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
      </div>
    </div>
    <add-driver-app-box :add-driver-app-box-show.sync="addDriverAppBoxShow"></add-driver-app-box>
  </div>
</template>
<script>
import AddDriverAppBox from '@/components/AddDriverAppBox'
export default {
  name: 'driverOrigin',
  data () {
    return {
      identityType: 'driverOrigin',
      tableParams: {
        keywords: '',
        time: ''
      },
      page: 1,
      maxPage: null,
      keywords: '',
      tableData: [],
      addDriverAppBoxShow: false
    }
  },
  components: {
    AddDriverAppBox
  },
  watch: {
    tableParams: {
      handler (val) {
        if (this.page != 1) {
          this.resetPage()
        }
        this.getTableList()
      },
      deep: true,
      immediate: true
    },
    identityType (newVal) {
      if (newVal != this.$router.name) {
        this.$router.push({ name: newVal })
      }
    }
  },
  methods: {
    resetPage () {
      this.page = 1
      this.tableData = []
    },
    searchFrom () {
      this.tableParams.keywords = this.keywords
    },
    getTableList () {
      let params = {
        page: this.page
      }
      params = Object.assign({}, this.tableParams, params)
      this.$axios.get(this.$httpUrl.driverList, { params: params }).then(res => {
        if (res != false) {
          this.maxPage = res.pages
          this.tableData = res.list
        }
      })
    },
    downloadTableList () {
      this.$axios.get(this.$httpUrl.downloadDriverList).then(res => {
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
    //打开司机App下载框
    openAppDownloadBox () {
      this.addDriverAppBoxShow = true
    }
  }
}
</script>
<style lang ="less" scoped>
@import "../contacts/origin.less";
.handle-wrap {
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
</style>
<style lang="less">
.driverOrigin {
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
