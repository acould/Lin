<template>
  <div class="fleet-main fleetManagement">
    <div class="main-header" ref="main-header">
      <div class="tab-wraps">
        <el-tabs v-model="pageType" @tab-click="toggleIdentityType">
          <el-tab-pane label="车辆管理" name="fleetManagement"></el-tab-pane>
          <el-tab-pane label="订单列表" name="fleetOrderList"></el-tab-pane>
          <el-tab-pane label="财务" name="fleetFinance"></el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div class="main-content">
      <div class="top-msg">
        <div class="inline-wrap">
          <span class="lb">车队名称</span>
          <el-input type='text' v-model='params.display_title' placeholder='请输入车队名称'></el-input>
        </div>
        <div class="inline-wrap">
          <span class="lb">客服电话</span>
          <el-input type='text' v-model='params.service_mobile' placeholder='请输入客服电话'></el-input>
        </div>
        <a href="javascript:void(0);" class="btn" @click="saveTableData">保存并预览</a>
        <div class="links-con" v-if="previewInfo.url">
          <div class="inp-wrap">
            <el-input type="text" ref="inpLinks" @focus="inpFocus" v-model="previewInfo.url" readonly></el-input>
          </div>
          <a href="javascript:void(0);" class="btn" @click="copyUrl">复制链接</a>
        </div>
      </div>
      <div class="table">
        <el-table fixed :data="tableData" header-row-class-name="tabletrClass" :height="tableHeight">
          <el-table-column label="车型" width="140">
            <template slot-scope="scope">
              <el-checkbox class="chk" @click.native="setCarTypes(scope.row)" v-model="scope.row.checked" :true-label="1" :false-label="0">{{scope.row.title}}</el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="单公里价格(元)" width="120">
            <template slot-scope="scope">
              <input type="number" class="inp" v-model="scope.row.km_price" @blur="verifyPriceTip(scope.column.label,scope.row.title,scope.row.km_price)">
            </template>
          </el-table-column>
          <el-table-column label="车辆每天利润(元)" width="130" align="center">
            <template slot-scope="scope">
              <input type="number" class="inp" v-model="scope.row.day_price" @blur="verifyPriceTip(scope.column.label,scope.row.title,scope.row.day_price)">
            </template>
          </el-table-column>
          <el-table-column label="超公里价格(元)" width="130">
            <template slot-scope="scope">
              <input type="number" class="inp" v-model="scope.row.over_km" @blur="verifyPriceTip(scope.column.label,scope.row.title,scope.row.over_km)">
            </template>
          </el-table-column>
          <el-table-column label="超时价格(元)" width="130">
            <template slot-scope="scope">
              <input type="number" class="inp" v-model="scope.row.over_hour" @blur="verifyPriceTip(scope.column.label,scope.row.title,scope.row.over_hour)">
            </template>
          </el-table-column>
          <el-table-column label="算法：公里数×每公里价格＋利润×(天数-1)">
            <template slot-scope="scope">
              <router-link :to="{name: 'fleetCarDetail',query:{id: scope.row.id,carTypeName:scope.row.title}}" class="btn btn-link">设置接送机/站 </router-link>
              <a href="javascript:void(0)" class="btn btn-nobg btn-nobg-theme btn-preview" @click="saveTableData">预览</a>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <fleet-preview-box :previewInfo="previewInfo" :fleet-preview-box-show.sync="fleetPreviewBoxShow" ></fleet-preview-box>
  </div>
</template>
<script>
import FleetPreviewBox from "@/components/FleetPreviewBox"
export default {
  name: 'fleetManagement',
  components: {
    FleetPreviewBox
  },
  data () {
    return {
      pageType: 'fleetManagement',
      demo: null,
      url: '',
      previewInfo: {
        url: '',
        image: ''
      },
      params: {
        display_title: '',
        service_mobile: '',
        car_types: []
      },
      clickChkCount: 0,
      tableData: [

      ],
      tableHeight: null,
      fleetPreviewBoxShow: false
    }
  },
  watch: {

  },
  created () {
    this.getTableList()
  },
  mounted () {
    this.setTableHight()
    this.throttle()
  },
  methods: {
    toggleIdentityType () {
      let routerName = this.pageType
      this.$router.push({ name: routerName })
    },
    setTableHight () {
      this.tableHeight = this.$root.$el.clientHeight - 150 + 'px'
    },
    throttle () {
      let wait = 10
      let lastDate = 0
      window.onresize = () => {
        let cur = +new Date()
        if (cur - lastDate > wait) {
          lastDate = cur
          this.setTableHight();
        }
      }
    },
    // 设置车型
    setCarTypes (info) {
      this.clickChkCount++
      let clickChkCount = this.clickChkCount
      if (clickChkCount == 1) return
      else this.clickChkCount = 0
      // 点击事件比值改变要早，判断条件相反
      if (info.checked) {
        let id = info.id
        let car_types = this.params.car_types
        let ind = car_types.findIndex(function (value, index, arr) {
          return value.id === id;
        })
        this.params.car_types.splice(ind, 1)
      } else {
        this.params.car_types.push(info)
      }
    },
    getTableList () {
      this.$axios.get(this.$httpUrl.selfConfig).then(res => {
        if (res != false) {
          let car_types = res.config.car_types
          this.tableData = res.config.car_types
          let newArr = []
          car_types.forEach(ele => {
            if (ele.checked) {
              newArr.push(ele)
            }
          })
          this.params = Object.assign({}, this.params, res.config, { car_types: newArr })
          this.previewInfo = { url: res.url, image: res.image }
        }
      })
    },
    saveTableData () {
      this.$axios.post(this.$httpUrl.selfSaveConfig, { config: this.params }).then(res => {
        if (res != false) {
          this.$message.success('保存成功')
          this.fleetPreviewBoxShow = true
          this.getTableList()
        }
      })
    },
    inpFocus () {
      this.$refs.inpLinks.select()
    },
    // 复制网址
    copyUrl () {
      let inp = this.$refs.inpLinks
      inp.focus()
      inp.select()
      document.execCommand('copy');
      this.$message({
        message: '复制成功',
        type: 'success'
      });
    },
    verifyPriceTip (lb, station, price) {
      lb = lb.replace('(元)','')
      if (price <= 0) {
        this.$message.error(`${station}${lb}必须大于0`)
      }
    }
  }
}
</script>

<style lang ="less" scoped>
.fleet-main {
  .main-content {
    padding: 0 10px;
    .top-msg {
      overflow: hidden;
      padding: 12px 0;
      .inline-wrap {
        float: left;
        .lb {
          display: inline-block;
          vertical-align: middle;
          width: 87px;
          text-align: center;
          font-size: 14px;
          color: #333;
        }
        .el-input {
          width: 153px;
        }
      }
      .btn {
        float: left;
        width: 98px;
        margin: 0 29px 0 20px;
      }
      .links-con {
        overflow: hidden;
        .inp-wrap {
          float: left;
          width: 210px;
        }
        .btn {
          float: left;
          width: 85px;
          border-radius: 0;
          margin: 0;
        }
      }
    }
    .table {
      .inp {
        width: 70px;
        height: 30px;
        font-size: 14px;
        color: #333;
        border: 1px solid #ccc;
        border-radius: 3px;
        box-sizing: border-box;
        outline: none;
      }
      .el-table {
        margin-bottom: 0;
      }
      .chk {
        margin-right: 8px;
      }
      .btn {
        float: left;
        margin-right: 24px;
        margin-bottom: 10px;
        font-size: 14px;
      }
      .btn-preview {
        width: 80px;
      }
      .btn-link {
        width: 108px;
      }
    }
  }
}
</style>
<style lang="less">
.fleetManagement {
  .top-msg {
    .el-input {
      .el-input__inner {
        height: 32px;
        line-height: 32px;
      }
    }
    .links-con {
      .el-input__inner {
        border-right: 0;
        border-radius: 0;
      }
    }
  }
  .table {
    .el-table .el-table__header-wrapper thead {
      font-size: 14px;
    }
  }
}
</style>



