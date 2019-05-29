<template>
  <div class="contacts-main driverOrigin">
    <div class="main-header">
      <div class="tab-wraps">
        <el-tabs v-model="identityType">
          <el-tab-pane
            label="客户"
            name="customerOrigin"
          ></el-tab-pane>
          <el-tab-pane
            label="司机"
            name="driverOrigin"
          ></el-tab-pane>
          <el-tab-pane
            label="同事/车调"
            name="operatorOrigin"
          ></el-tab-pane>
          <el-tab-pane
            label="业务员"
            name="salesmanList"
          ></el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div class="main-content">
      <div class="handle-wrap">
        <div class="date-wrap">
          <el-date-picker
            class="el-date"
            v-model="tableParams.time"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择年月"
          >
          </el-date-picker>
        </div>
        <div class="inp-wrap">
          <span class="iconfont icon-shujuchaxun iconfont-search"></span>
          <el-input
            class="inp-search"
            v-model="keywords"
            placeholder="请输入业务员姓名"
          ></el-input>
        </div>
        <a
          href="javascript:void(0)"
          class="btn-search"
          @click="searchFrom"
        >搜索</a>
        <div class="btn-wrap">
          <a
            href="javascript:void(0)"
            class="btn btn-export"
            @click="downloadTableList"
          >全部导出</a>
        </div>
        <div class="btn-wrap">
          <router-link
            :to="{name: 'addSalesman'}"
            class="btn add-driver"
          >添加业务员</router-link>
        </div>
      </div>
      <div
        class="table"
        v-if="tableData.length"
      >
        <el-table :data="tableData">
          <el-table-column
            prop="name"
            label="业务员"
            width="130"
          >
          </el-table-column>
          <el-table-column
            prop="mobile"
            label="联系电话"
            width="160"
          >
          </el-table-column>
          <el-table-column
            label="订单数"
            width="130"
          >
            <template slot-scope="scope">
              <router-link
                :to="{name: 'salesmanOrder', query: {salesmanName: scope.row.name,salesmanId: scope.row.salesman_id}}"
                class="color-green"
              >{{scope.row.order_count}}单</router-link>
            </template>
          </el-table-column>
          <el-table-column
            prop="address"
            label="操作"
          >
            <template slot-scope="scope">
              <div class="btn-select-wrap">
                <span class="btn-txt">操作</span>
                <el-select
                  v-model="btnOption"
                  placeholder="操作"
                  class="hid-select"
                  @focus="btnSelectBlur(scope.row)"
                >
                  <el-option
                    value="1"
                    label="修改"
                  >
                  </el-option>
                  <el-option
                    value="2"
                    label="删除"
                  >
                  </el-option>
                </el-select>
              </div>
           
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div
        class="pages-wrap"
        v-if="tableData.length"
      >
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
  name: 'salesmanList',
  data () {
    return {
      identityType: 'salesmanList',
      tableParams: {
        keywords: '',
        time: ''
      },
      page: 1,
      maxPage: null,
      keywords: '',
      tableData: [],
      btnOption: null,
      btnRowData: null,
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
      deep: true,
      immediate: true
    },
    identityType (newVal) {
      if (newVal != this.$router.name) {
        this.$router.push({ name: newVal })
      }
    },
    btnOption (newVal) {
      let info = this.btnRowData
      if (!newVal) {
        return
      } else if (newVal == 1) {
        this.$router.push({ name: 'addSalesman', query: { salesmanId: info.salesman_id } })
      } else if (newVal == 2) {
        this.$confirm('是否删除该业务员', {
          type: 'warning'
        }).then(() => {
        let params = {salesman_id:  info.salesman_id }
          this.$axios.get(this.$httpUrl.salesmanDelete, { params: params }).then(res => {
            if (res != false) {
              this.getTableList()
            }
          })
        }).catch(_ => console.log('取消操作'))
      }
      this.btnOption = null
    }
  },
  methods: {
    btnSelectBlur (rowInfo) {
      this.btnRowData = rowInfo
    },
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
      this.$axios.get(this.$httpUrl.salesmanList, { params: params }).then(res => {
        if (res != false) {
          this.maxPage = res.pages
          this.tableData = res.list
        }
      })
    },
    downloadTableList () {
      this.$axios.get(this.$httpUrl.salesmanDownload).then(res => {
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
