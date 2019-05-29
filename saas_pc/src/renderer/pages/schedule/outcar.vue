<template>
  <div class="car-main">
    <div class="main-header">
      <div class="tab-wraps">
        <el-tabs v-model="identityType" @tab-click="toggleIdentityType">
          <el-tab-pane label="自有" name="arrange"></el-tab-pane>
          <el-tab-pane label="外援" name="outcar"></el-tab-pane>
          <el-tab-pane label="任务" name="mission"></el-tab-pane>
          <el-tab-pane label="监控" name="monitor"></el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div class="main-content">
      <div class="handle-wrap">
        <div class="inp-wrap">
          <span class="iconfont icon-shujuchaxun iconfont-search"></span>
          <el-input key="outside" class="inp-search" v-model="keywords" placeholder="请输入车队名称"></el-input>
        </div>
        <el-select v-model="carListParams.paginate" placeholder="请选择显示条数" class="seatsnum-select pageselect">
          <el-option
            v-for="(item,index) in pageOptions"
            :key="index"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
        <div class="btn-wrap">
          <router-link :to="{name: 'increaseCustomer',query: {customerType: 2}}" class="btn add-driver">添加车队</router-link>
        </div>
      </div>
      <div class="table">
        <dataBlank v-if="(!tableDataOut.length) && maxPage === 0" dataType="car" :pageType=carListParams.car_source></dataBlank>
        <el-table key="outside" :data="tableDataOut" v-if="tableDataOut.length && carListParams.car_source == 2" style="width: 100%">
          <el-table-column prop="out_company" label="车队名称">
          </el-table-column>
          <el-table-column prop="name" label="联系人">
          </el-table-column>
          <el-table-column prop="mobile" label="联系方式">
          </el-table-column>
          <el-table-column prop="driver_name" label="已添加车辆">
            <template slot-scope="scope">
              <span v-if="!scope.row.car_count">未添加</span>
              <span v-else>{{scope.row.car_count}}辆</span>
            </template>
          </el-table-column>
          <el-table-column prop="address" label="操作" width="150">
            <template slot-scope="scope">
              <div class="btns">
                <router-link :to="{name: 'customerDetails', query: {customer_id: scope.row.customer_id,customerType: 2}}" class="btn">查看</router-link>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pages-wrap">
        <el-pagination :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'carOrigin',
  data () {
    return {
      identityType: 'outcar',
      carListParams: {
        car_source: '2',
        keywords: '',
        paginate:''
      },
      pageOptions: [{
        value: '10',
        label: '10'
      }, {
        value: '20',
        label: '20'
      }, {
        value: '50',
        label: '50'
      }],
      page: 1,
      maxPage: null,
      keywords: '',
      tableData: [],
      tableDataOut: []
    }
  },
  created () {
    this.watchIsType()
  },
  watch: {
    carListParams: {
      handler (val) {
        if (this.page != 1) {
          this.resetPage()
        }
        this.getCarList()
      },
      deep: true
    }
  },
  methods: {
    // 监听router中的query.type
    watchIsType () {
      let type = this.$route.query.type
      if (!type) {
        type = 2
      }
      this.carListParams.car_source = type.toString()
      this.getCarList()
    },
    resetPage () {
      this.page = 1
      this.tableData = []
    },
    searchFrom () {
      this.carListParams.keywords = this.keywords
    },
    getCarList () {
      let url, params
      params = {
        page: this.page
      }
      url = this.$httpUrl.allfleetList
      params = Object.assign({}, this.carListParams, params)
      this.$axios.get(url, { params: params }).then(res => {
        if (res != false) {
          this.maxPage = res.pages
          this.tableDataOut = res.list

        }
      })
    },
    changePage (page) {
      this.page = page
      this.getCarList()
    },
    toggleIdentityType () {
      let identityType = this.identityType
      this.$router.push({ name: identityType });
    },
  }
}
</script>
<style lang ="less" scoped>
.pageselect{
  width: 130px;
  margin-left: 20px;
}
.handle-wrap{
  &/deep/.el-input__inner {
    height: 32px;
  }
}

</style>
