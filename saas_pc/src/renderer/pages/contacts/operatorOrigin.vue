<template>
  <div class="contacts-main">
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
        <div class="inp-wrap">
          <span class="iconfont icon-shujuchaxun iconfont-search"></span>
          <el-input class="inp-search" v-model="keywords" placeholder="请输入单位名称/联系人"></el-input>
        </div>
        <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
        <div class="btn-wrap">
          <a href="javascript:void(0);" class="btn" @click="addOperator">添加车调</a>
        </div>
      </div>
      <div class="table">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="name" label="姓名"></el-table-column>
          <el-table-column prop="mobile" label="联系方式"></el-table-column>
          <el-table-column prop label="用户身份">
            <template slot-scope="scope">
              <span v-if="scope.row.role_id === 0">主管理员</span>
              <span v-else-if="scope.row.role_id === 1">管理员</span>
              <span v-else-if="scope.row.role_id === 2">车调</span>
              <span v-else>财务</span>
            </template>
          </el-table-column>
          <el-table-column prop="address" label="操作">
            <template slot-scope="scope">
              <div class="btns">
                <router-link
                  v-if="scope.row.can_delete == 1"
                  class="btn"
                  :to="{name: 'modifyYardman',query:{mobile:scope.row.mobile,yardman_id:scope.row.yardman_id,name: scope.row.name,role_id: scope.row.role_id}}"
                >修改</router-link>
                <a
                  href="javascript:void(0);"
                  v-if="scope.row.can_delete == 1"
                  class="btn btn-nobg"
                  @click="hanlderData(scope.row.yardman_id)"
                >删除</a>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <add-operator-box :add-operator-box-show.sync="addOperatorBoxShow"></add-operator-box>
  </div>
</template>
<script>
import AddOperatorBox from '@/components/AddOperatorBox'
export default {
  data () {
    return {
      identityType: 'operatorOrigin',
      keywords: '',
      tableData: [],
      tableParams: {
        keywords: ''
      },
      addOperatorBoxShow: false
    }
  },
  components: {
    AddOperatorBox
  },
  watch: {
    identityType (newVal) {
      if (newVal != this.$router.name) {
        this.$router.push({ name: newVal })
      }
    },
    tableParams: {
      handler () {
        this.getTableList()
      },
      deep: true
    }
  },
  created () {
    this.getQueryParams()
    this.getTableList()
  },
  methods: {
    getQueryParams () {
      let query = this.$route.query
      if (query.type && query.type === 'operatorOrigin') {
        this.identityType = 'operatorOrigin'
      }
    },
    getTableList () {
      let params = {
        page: this.page
      }
      params = Object.assign({}, this.tableParams, params)
      this.$axios.get(this.$httpUrl.fleetYardmanList, { params: params }).then(res => {
        if (res != false) {
          this.maxPage = res.pages
          this.tableData = res.list
        }
      })
    },
    hanlderData (id) {
      this.$confirm('是否确定删除', {
        type: 'warning'
      }).then(() => {
        let params = { yardman_id: id }
        this.$axios.get(this.$httpUrl.deleteFleetYardman, { params: params }).then(res => {
          if (res != false) {
            this.$message({
              message: '删除成功',
              type: 'success'
            });
            this.getTableList()
          }
        })
      }).catch(() => console.log('取消删除'))
    },
    searchFrom () {
      this.tableParams.keywords = this.keywords
    },
    addOperator () {
      this.addOperatorBoxShow = true
    }
  }
}
</script>
