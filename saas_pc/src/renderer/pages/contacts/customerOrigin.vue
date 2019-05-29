<template>
  <div class="contacts-main customerOrigin">
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
      <div class="table-top">
        <el-radio-group
          class="radio-wrap"
          @change="changeRadio"
          v-model="type"
          style="margin-bottom: 30px;"
        >
          <el-radio-button label="1">客户</el-radio-button>
          <el-radio-button label="2">外援车队</el-radio-button>
        </el-radio-group>
        <div class="inp-wrap" key="travelAgency" v-if="type == 1">
          <el-input
            class="inp-search"
            suffix-icon="iconfont icon-shujuchaxun"
            v-model="keywords"
            placeholder="请输入客户名称"
          ></el-input>
        </div>
        <div class="inp-wrap" key="carTeam" v-else>
          <el-input
            class="inp-search"
            suffix-icon="iconfont icon-shujuchaxun"
            v-model="keywords"
            placeholder="请输入车队名称"
          ></el-input>
        </div>
        <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
        <div class="btn-wrap">
          <a href="javascript:void(0)" class="btn btn-export" @click="downloadTableList">全部导出</a>
        </div>
        <div class="btn-wrap">
          <router-link
            v-show="type == 1"
            :to="{name: 'increaseCustomer',query: {customerType: 1}}"
            class="btn add-driver"
          >添加客户</router-link>
          <router-link
            v-show="type == 2"
            :to="{name: 'increaseCustomer',query: {customerType: 2}}"
            class="btn add-driver"
          >添加车队</router-link>
        </div>
        <div class="btn-wrap">
          <a href="javascript:void(0)" class="btn btn-export" @click="lookImage">查看合同</a>
        </div>
      </div>
      <dataBlank
        :nobtn="false"
        v-if="(!tableDataCar.length || !tableDataTravel.length) && maxPage === 0"
        data-type="customer"
      ></dataBlank>
      <div class="table">
        <el-table
          key="travel"
          :data="tableDataTravel"
          style="width: 100%"
          v-if="type == 1&& tableDataTravel.length"
        >
          <el-table-column prop="company" label="单位名称"></el-table-column>
          <el-table-column prop="count" label="计调人数"></el-table-column>
          <el-table-column prop="order_count" label="订单数">
            <template slot-scope="scope">
              <router-link
                :to="{name: 'salesmanOrder', query: {customer_id: scope.row.customer_id,type:'1',company: scope.row.company}}"
                class="color-green"
              >{{scope.row.order_count}}单</router-link>
            </template>
          </el-table-column>
          <el-table-column prop="address" label="联系地址"></el-table-column>
          <el-table-column prop="address" label="操作">
            <template slot-scope="scope">
              <div class="btns">
                <router-link
                  :to="{name: 'customerDetails', query: {customer_id: scope.row.customer_id,customerType: 1}}"
                  class="btn btn-nobg"
                >查看详情</router-link>
                <a
                  href="javascript:void(0)"
                  class="btn btn-top"
                  @click="stick(scope.row.customer_id)"
                >置顶</a>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <el-table
          key="car"
          :data="tableDataCar"
          style="width: 100%"
          v-if="type == 2&& tableDataCar.length"
        >
          <el-table-column prop="company" label="单位名称"></el-table-column>
          <el-table-column prop="order_count" label="订单数">
            <template slot-scope="scope">
              <router-link
                 :to="{name: 'salesmanOrder', query: {customer_id: scope.row.customer_id,type:'2',company: scope.row.company}}"
                class="color-green"
              >{{scope.row.order_count}}单</router-link>
            </template>
          </el-table-column>
          <el-table-column prop="customer_name" label="联系人"></el-table-column>
          <el-table-column prop="customer_mobile" label="联系电话"></el-table-column>
          <el-table-column prop="address" label="联系地址"></el-table-column>
          <el-table-column prop="address" label="操作">
            <template slot-scope="scope">
              <div class="btns">
                <router-link
                  :to="{name: 'customerDetails', query: {customer_id: scope.row.customer_id,customerType: 2}}"
                  class="btn btn-nobg"
                >查看详情</router-link>
                <a
                  href="javascript:void(0)"
                  class="btn btn-top"
                  @click="stick(scope.row.customer_id)"
                >置顶</a>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pages-wrap" v-if="tableDataTravel.length && type == 1">
        <el-pagination
          :current-page="page"
          @current-change="changePage"
          :page-count="maxPage"
          background
          layout="prev, pager, next"
        ></el-pagination>
      </div>
      <div class="pages-wrap" v-if="tableDataCar.length && type == 2">
        <el-pagination
          :current-page="page"
          @current-change="changePage"
          :page-count="maxPage"
          background
          layout="prev, pager, next"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'customerOrigin',
  data () {
    return {
      identityType: 'customerOrigin',
      type: "1",
      tableParams: {
        keywords: '',
        type: "1"
      },
      page: 1,
      maxPage: null,
      keywords: '',
      tableDataTravel: [],
      tableDataCar: []
    }
  },
  created () {
    this.getQueryParams()
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
    stick (id) {
      this.$axios.post(this.$httpUrl.customerTop, { customer_id: id }).then(res => {
        if (res != false) {
          this.$message.success('置顶成功')
          this.getTableList()
        }
      })
      
    },
    // 返回到跳转到客户详情之前的页面
    getQueryParams () {
      let query = this.$route.query
      let customerType = query.customerType
      if (customerType) {
        this.type = customerType
        Object.assign(this.tableParams, { type: customerType })
      }
    },
    resetPage () {
      this.page = 1
    },
    searchFrom () {
      Object.assign(this.tableParams, { keywords: this.keywords })
    },
    getTableList () {
      let params = {
        page: this.page
      }
      params = Object.assign({}, this.tableParams, params)
      this.$axios.get(this.$httpUrl.customerList, { params: params }).then(res => {
        if (res != false) {
          this.maxPage = res.pages
          if (params.type === "1") {
            this.tableDataTravel = res.list
          } else {
            this.tableDataCar = res.list
          }
        }
      })
    },
    downloadTableList () {
      let params = {
      }
      this.$axios.get(this.$httpUrl.downloadCustomerList, { params: params }).then(res => {
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
    changeRadio (o) {
      // 重置关键词      
      let keywords = ""
      this.keywords = keywords
      Object.assign(this.tableParams, { keywords, type: this.type })
    },
    changePage (page) {
      this.page = page
      this.getTableList()
    },
    //查看合同
    lookImage () {
      this.$router.push({ name: 'allImages' })
    }
  }
}
</script>
<style lang ="less" scoped>
@import "../contacts/origin.less";
.btn {
  &.add {
    background-color: transparent;
    color: #333;
    border: 1px solid #999;
  }
}
.customerOrigin {
  .main-content {
    .btns {
      .btn-top {
        background-color: @theme-color;
        color: #fff;
        border: none;
      }
    }
  }
}
</style>
<style lang="less">
.customerOrigin {
  min-height: 100%;
  background-color: @bg-grey;
  .main-content {
    margin: 10px 16px 0;
    .table-top {
      height: 70px;
      background-color: #fff;
      padding: 15px 20px;
      .inp-wrap {
        min-width: 242px;
        .el-input__inner {
          padding-left: 12px;
        }
        .el-input__suffix {
          top: -4px;
        }
      }
      .radio-wrap {
        float: left;
        .el-radio-button__inner {
          padding: 8px 24px;
        }
      }
    }
    .table {
      padding-bottom: 100px;
      .el-table {
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
