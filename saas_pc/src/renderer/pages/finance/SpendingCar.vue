<template>
  <div class="car-main">
    <div class="main-header">
      <div class="tab-wrap">
        <el-tabs v-model="carListParams.car_source" @tab-click="fixLinksType">
          <el-tab-pane label="自有车辆" name="1"></el-tab-pane>
          <el-tab-pane label="外援车队" name="2"></el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div class="main-content">
      <div class="handle-wrap">
        <div class="inp-wrap">
          <span class="iconfont icon-shujuchaxun iconfont-search"></span>
          <el-input key="inside" v-if="carListParams.car_source == 1" class="inp-search" v-model="keywords" placeholder="请输入车牌号"></el-input>
          <el-input key="outside" v-else class="inp-search" v-model="keywords" placeholder="请输入车队名称"></el-input>
        </div>
        <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>

      </div>
      <div class="table">
        <dataBlank v-if="(!tableData.length || !tableDataOut.length) && maxPage === 0" dataType="car" :pageType=carListParams.car_source></dataBlank>
        <el-table key="inside" :data="tableData" v-if="tableData.length && carListParams.car_source == 1" style="width: 100%">
          <el-table-column prop="car_number" label="车牌号">
          </el-table-column>
          <el-table-column prop="car_type" label="车辆型号">
          </el-table-column>
          <el-table-column prop="seat_num" label="座位数">
          </el-table-column>
          <el-table-column prop="driver_name" label="司机">
          </el-table-column>
          <el-table-column prop="driver_mobile" label="手机号" v-if="carListParams.car_source == 2">
          </el-table-column>
          <el-table-column prop="next_maintain_at" label="距下次保养" v-if="carListParams.car_source == 1">
          </el-table-column>
          <el-table-column prop="next_insurance_at" label="距下次保险" v-if="carListParams.car_source == 1 ">
          </el-table-column>
          <el-table-column prop="next_annual_audit_at" label="距下次年检" v-if="carListParams.car_source == 1">
          </el-table-column>
          <el-table-column prop="run_km" label="公里数" v-if="carListParams.car_source == 1">
          </el-table-column>
          <el-table-column prop="address" label="操作" width="150">
            <template slot-scope="scope">
              <div class="btns">
                <a href="javascript:void(0)" class="btn" @click="carHandler(scope.row)">选择</a>
              </div>
            </template>
          </el-table-column>
        </el-table>
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
                <router-link :to="{name: 'SpendingCarDetails', query: {customer_id: scope.row.customer_id,customerType: 2}}" class="btn">查看</router-link>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pages-wrap" v-if="controlPage">
        <el-pagination :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
export default {
  name: 'carOrigin',
  data () {
    return {
      carListParams: {
        car_source: '1',
        keywords: ''
      },
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
  computed: {
    controlPage () {
      let car_source = this.carListParams.car_source
      return ((car_source == 1 && this.tableData.length) || (car_source == 2 && this.tableDataOut.length))
    },
    ...mapState({
      spendingInfo: state => state.finance.spendingInfo
    })
  },
  methods: {
    // 监听router中的query.type
    watchIsType () {
      let type = this.$route.query.type
      if (!type) {
        type = 1
      }
      this.carListParams.car_source = type.toString()
      this.getCarList()
    },
    fixLinksType () {
      this.$router.push({ name: 'SpendingCar', query: { type: this.carListParams.car_source } })
    },
    resetPage () {
      this.page = 1
      this.tableData = []
    },
    ...mapActions([
      'refreshSpendingInfo',
    ]),
    carHandler (info) {
      let obj = { ...this.spendingInfo }
      Object.assign(obj, {saas_car_id: info.car_id.toString(), car_number: info.car_number })
      this.refreshSpendingInfo(obj)
      this.$router.go(-1)
    },
    searchFrom () {
      this.carListParams.keywords = this.keywords
    },
    getCarList () {
      let url, params
      let car_source = this.carListParams.car_source
      params = {
        page: this.page
      }
      if (car_source == 1) {
        url = this.$httpUrl.carList
      } else {
        url = this.$httpUrl.allfleetList
      }
      params = Object.assign({}, this.carListParams, params)
      this.$axios.get(url, { params: params }).then(res => {
        if (res != false) {
          this.maxPage = res.pages
          if (car_source == 1) {
            this.tableData = res.list
          } else {
            this.tableDataOut = res.list
          }

        }
      })
    },
    changePage (page) {
      this.page = page
      this.getCarList()
    },
    downloadCarList () {
      let params = {
        car_source: this.carListParams.car_source
      }
      this.$axios.get(this.$httpUrl.downloadCarList, { params: params }).then(res => {
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
    }
  }
}
</script>
<style lang ="less" scoped>
</style>
