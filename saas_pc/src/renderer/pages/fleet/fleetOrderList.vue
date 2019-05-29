<template>
  <div class="fleet-main">
    <div class="main-header" ref="main-header">
      <div class="tab-wraps">
        <el-tabs v-model="pageType" @tab-click="toggleIdentityType">
          <el-tab-pane label="车辆管理" name="fleetManagement"></el-tab-pane>
          <el-tab-pane label="订单列表" name="fleetOrderList"></el-tab-pane>
          <el-tab-pane label="财务" name="fleetFinance"></el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div class="takespace"></div>
    <div class="main-content">
      <div class="top-box">
        <el-radio-group class="radio-wrap" v-model="tableParams.type">
          <el-radio-button label="0">全部</el-radio-button>
          <el-radio-button label="1">待接单</el-radio-button>
          <el-radio-button label="2">退款订单</el-radio-button>
          <el-radio-button label="3">已排班</el-radio-button>
        </el-radio-group>
        <div class="select-wrap">
          订单类型
          <el-select v-model="tableParams.order_type" placeholder="请选择">
            <el-option v-for="item in orderTypeList" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </div>
        <div class="select-wrap">
          车型
          <el-select v-model="tableParams.car_type" placeholder="请选择">
            <el-option v-for="item in carTypeList" :key="item.id" :label="item.title" :value="item.id">
            </el-option>
          </el-select>
        </div>

      </div>
      <div class="table">
        <el-table :data="tableData" header-row-class-name="tabletrClass">
          <el-table-column label="类型" width="90" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.order_type === 1">包车</span>
              <span v-else-if="scope.row.order_type === 2 && scope.row.shuttle_type == 1">接机</span>
              <span v-else-if="scope.row.order_type === 2 && scope.row.shuttle_type == 2">送机</span>
              <span v-else-if="scope.row.order_type === 3 && scope.row.shuttle_type == 1">接站</span>
              <span v-else>送站</span>
            </template>
          </el-table-column>
          <el-table-column label="时间" width="140">
            <template slot-scope="scope">
              <p>{{scope.row.travel_at}}</p>
              <p>{{scope.row.end_at}}</p>
            </template>
          </el-table-column>
          <el-table-column label="备注" width="80" align="center" prop="remark">
          </el-table-column>
          <el-table-column label="行程" prop="travel_content">
            <template slot-scope="scope">
              <span v-if="scope.row.flight_num">航班号：{{scope.row.flight_num}}</span>
              {{scope.row.travel_content}}
            </template>
          </el-table-column>
          <el-table-column label="公里数" align="center" min-width="90">
            <template slot-scope="scope">
              {{scope.row.travel_total_distance}}公里
            </template>
          </el-table-column>
          <el-table-column label="车型">
            <template slot-scope="scope">
              {{scope.row.car_infos}}
            </template>
          </el-table-column>
          <el-table-column label="价格" prop="money" width="60">
          </el-table-column>
          <el-table-column label="发票" width="70">
            <template slot-scope="scope">
              <span>{{scope.row.need_invoice > 0 ? '需要' : '不需要'}}</span>
              <el-popover v-if="scope.row.need_invoice > 0 " popper-class="tel-tip" placement="top-start" width="150" trigger="hover">
                {{scope.row.invoice_title}}
                <span slot="reference" class="iconfont icon-jiantou2 iconfont-arrow"></span>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="下单人" width="110" prop="contact_mobile">
          </el-table-column>
          <el-table-column label="状态">
            <template slot-scope="scope">
              <span v-if="scope.row.order_status == 2" class="color-orange" @click="orderHandler">待接单</span>
              <span v-if="scope.row.order_status == 3" class="color-green">已排班</span>
              <span v-if="scope.row.order_status == 4" class="color-red">取消行程</span>
              <span v-if="scope.row.order_status == 6" class="color-black">已完成</span>
              <span v-if="scope.row.order_status == 5" class="color-black">订单取消</span>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <div class="btn-select-wrap">
                <span class="btn-txt">操作</span>
                <el-select v-model="btnOption" placeholder="操作" class="hid-select" @focus="btnSelectBlur(scope.row)">
                  <el-option value="1" label="排班" v-if="scope.row.order_status == 2">
                  </el-option>
                  <el-option value="2" label="拒绝排班" v-if="scope.row.order_status == 2">
                  </el-option>
                  <el-option value="3" label="全额退款" v-if="scope.row.order_status == 4">
                  </el-option>
                  <el-option value="4" label="其他金额" v-if="scope.row.order_status == 4">
                  </el-option>
                  <el-option value="5" label="结束任务" v-if="scope.row.order_status == 3">
                  </el-option>
                  <el-option value="6" label="查看详情" v-if="scope.row.order_status == 3 || scope.row.order_status == 6">
                  </el-option>
                  <el-option value="7" label="删除记录" v-if="scope.row.order_status == 5 || scope.row.order_status == 6">
                  </el-option>
                </el-select>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pages-wrap" v-if="tableData.length">
        <el-pagination :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
      </div>
    </div>
    <modifyPriceBox :modifyPriceBoxShow.sync="modifyPriceBoxShow" @getNewMoney="getNewMoney" :is-fleet-price="true"></modifyPriceBox>
  </div>
</template>
<script>
import modifyPriceBox from '@/components/modifyPriceBox'
export default {
  name: 'fleetOrderList',
  components: {
    modifyPriceBox
  },
  data () {
    return {
      pageType: 'fleetOrderList',
      plan_type: 0,
      orderType: null,
      tableParams: {
        type: 0,
        order_type: '',
        car_type: null,
      },
      page: 1,
      maxPage: null,
      carTypeList: [],
      tableData: [],
      orderTypeList: [
        {
          value: '0',
          label: '全部'
        },
        {
          value: '1',
          label: '包车'
        },
        {
          value: '2',
          label: '接送机'
        },
        {
          value: '3',
          label: '接送站'
        }
      ],
      btnOption: null,
      btnRowData: null,
      modifyPriceBoxShow: false
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
    btnOption (newVal) {
      let info = this.btnRowData
      if (!newVal) {
        return
      } else if (newVal == 1) {
        let queryParams = {
          travel_content: info.travel_content,
          travel_begin_at: info.begin_at_date,
          travel_end_at: info.end_at_date || info.begin_at_date,
          begin_at_date: info.begin_at_date,
          begin_at_time: info.begin_at_time,
          end_at_date: info.end_at_date,
          end_at_time: info.end_at_time,
          deposit: info.money,
          money: info.money,
          tour_guide_mobile: info.contact_mobile,
          self_order_id: info.self_order_id
        }
        if (!info.end_at_date) {
          queryParams.date = info.begin_at_date
        }
        this.$router.push({ name: 'addMission', query: queryParams })
      } else if (newVal == 2) {
        this.$confirm('是否拒绝排班', {
          type: 'warning'
        }).then(() => {
          this.orderHandler(1, info.self_order_id)
        }).catch(() => console.log('拒绝排班'))

      } else if (newVal == 3) {
        this.$confirm('是否全额退款', {
          type: 'warning'
        }).then(() => {
          this.orderHandler(2, info.self_order_id)
        }).catch(() => console.log('全额退款'))
      } else if (newVal == 4) {
        this.modifyPriceBoxShow = true
      } else if (newVal == 5) {
        this.orderHandler(3, info.self_order_id)
      } else if (newVal == 6) {
        this.$router.push({ name: 'accountDetails', query: { order_id: info.saas_order_id, source: 'bill' } })
      } else if (newVal == 7) {
        this.orderHandler(4, info.self_order_id)
      }
      this.btnOption = null
    }
  },
  created () {
    this.getCarTypeList()
    this.getTableList()
  },
  methods: {
    // 获取自有车辆表格选中行的数据
    btnSelectBlur (rowInfo) {
      this.btnRowData = rowInfo
    },
    getNewMoney (price) {
      let info = this.btnRowData
      this.orderHandler(2, info.self_order_id, price)
    },
    getCarTypeList () {
      this.$axios.get(this.$httpUrl.selfCarTitles).then(res => {
        if (res != false) {
          this.carTypeList = res
        }
      })
    },
    orderHandler (status, self_order_id, money) {
      // status 1 拒绝接单 2 退款 3 结束任务 4 删除记录
      let params = {
        self_order_id: self_order_id,
        status: status
      }
      if (money) {
        params.money = money
      }
      this.$axios.post(this.$httpUrl.selfOrderStatus, params).then(res => {
        if (res != false) {
          this.getTableList()
          this.$message({
            message: '操作成功',
            type: 'success'
          })
        }
      })
    },
    getTableList () {
      let params = {
        page: this.page
      };
      params = Object.assign({}, this.tableParams, params);
      this.$axios.get(this.$httpUrl.selfOrderList, { params: params }).then(res => {
        if (res != false) {
          this.tableData = res.list
          this.maxPage = res.pages;
        }
      })
    },
    toggleIdentityType () {
      let routerName = this.pageType
      this.$router.push({ name: routerName })
    },
    changePage (page) {
      this.page = page;
      this.getTableList();
    },
  }
}
</script>
<style lang ="less" scoped>
.fleet-main {
  .top-box {
    margin: 20px 0;
  }
  .main-content {
    padding: 0 10px;
  }
  .select-wrap {
    display: inline-block;
    margin-left: 20px;
    font-size: 14px;
    color: #666;
    .el-select {
      margin-left: 10px;
      width: 160px;
    }
  }
  .iconfont-arrow {
    display: inline-block;
    transform: rotate(-90deg);
  }
}
</style>
<style lang="less">
.fleet-main {
  .tabletrClass {
    th {
      background-color: #fafafa;
    }
  }
}
</style>



