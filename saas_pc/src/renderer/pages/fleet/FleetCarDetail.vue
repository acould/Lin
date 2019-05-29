<template>
  <div class="fleet-main FleetCarDetail">
    <div class="main-header" ref="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'fleetManagement' }">车队管理</el-breadcrumb-item>
          <el-breadcrumb-item>{{carTypeName}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="takespace"></div>
    <div class="main-content">
      <div class="handle-wrap">
        <p class="tt">{{carTypeName}}</p>
        <div class="inp-wrap">
          <el-input class="inp-search" suffix-icon="iconfont icon-shujuchaxun" v-model="keywords" placeholder="请输入已添加的机场/车站"></el-input>
        </div>
        <a href="javascript:void(0)" class="btn-search" @click="searchKeywords">搜索</a>
        <a href="javascript:void(0)" class="btn btn-nobg  btn-nobg-theme btn-station" @click="setStation(2)" @mousemove="setStationType(2)">添加机场</a>
        <a href="javascript:void(0)" class="btn btn-nobg  btn-nobg-theme btn-station" @click="setStation(1)" @mousemove="setStationType(1)">添加火车站</a>
        <a href="javascript:void(0)" class="btn btn-save" @click="saveConfig">保存</a>
      </div>
      <div class="table">
        <el-table :data="tableData" header-row-class-name="tabletrClass">
          <el-table-column label="当前地区" width="200">
            <template slot-scope="scope">
              <span class="iconfont icon-shanchu" @click="deleteStationConfig(scope.row.id,scope.$index)"></span>
              {{scope.row.station}}
            </template>
          </el-table-column>
          <el-table-column label="一口价" width="120">
            <template slot-scope="scope">
              <input type="number" class="inp" v-model="scope.row.day_price" @blur="verifyPriceTip(scope.column.label,scope.row.station,scope.row.day_price)">
            </template>
          </el-table-column>
          <el-table-column label="一口价包含公里" width="140">
            <template slot-scope="scope">
              <input type="number" class="inp" v-model="scope.row.km" @blur="verifyPriceTip(scope.column.label,scope.row.station,scope.row.km)">
            </template>
          </el-table-column>
          <el-table-column label="超公里价格" width="130">
            <template slot-scope="scope">
              <input type="number" class="inp" v-model="scope.row.over_km" @blur="verifyPriceTip(scope.column.label,scope.row.station,scope.row.over_km)">
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <a href="javascript:void(0)" class="btn btn-save" @click="saveConfig">保存</a>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <popup-station-box @rtnConfigList="rtnConfigList" :popup-station-box-show.sync="popupStationBoxShow" :station-type="stationType" :car-type="tableparams.car_type"></popup-station-box>
  </div>
</template>
<script>
import PopupStationBox from "@/components/PopupStationBox"
export default {
  name: 'FleetCarDetail',
  components: {
    PopupStationBox
  },
  data () {
    return {
      keywords: '',
      carTypeName: '',
      tableparams: {
        car_type: null,
        keywords: ''
      },
      tableData: [],
      configParams: {
        car_type: null,
        config: [],
      },
      popupStationBoxShow: false,
      stationType: null,
    }
  },
  watch: {
    tableparams: {
      handler (newVal) {
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
      if (query.carTypeName) this.carTypeName = query.carTypeName
      if (query.id) {
        let id = query.id.toString()
        this.configParams.car_type = id
        this.tableparams.car_type = id
      }
    },
    // 获取表格数据
    getCarDetail () {
      this.$axios.get(this.$httpUrl.selfShuttleConfig, { params: this.tableparams }).then(res => {
        if (res != false) {
          this.tableData = res.list
          this.configParams.config = res.list
        }
      })
    },
    searchKeywords () {
      this.tableparams.keywords = this.keywords
    },
    // 保存表格配置
    saveConfig () {
      this.$axios.post(this.$httpUrl.selfSaveShuttleConfig, this.configParams).then(res => {
        if (res != false) {
          this.$message.success('保存成功')
          this.getCarDetail()
        }
      })
    },
    // 设置接送机站配置
    setStation (type) {
      this.popupStationBoxShow = true
    },
    // 设置接送机 和接送站类型
    setStationType (type) {
      this.stationType = type
    },
    verifyPriceTip (lb, station, price) {
      lb = lb.replace('(元)', '')
      if (price <= 0) {
        this.$message.error(`${station}${lb}必须大于0`)
      }
    },
    rtnConfigList (list) {
      let tableData = this.tableData
      for (var i in list) {
        for (var j in tableData) {
          if (tableData[j].station === list[i].station) {
            this.$message.error(`${list[i].station}接送服务已设置`)
            return
          }
        }
      }
      let arr = [...this.tableData, ...list,]
      this.configParams.config = arr
      this.tableData = arr
    },
    deleteStationConfig (id, ind) {
      this.$confirm('是否删除该配置', {
        type: 'warning'
      }).then(() => {
        // 如果id为0,数据为静态增加不调用接口
        if (id) {
          let params = { shuttle_config_id: id }
          this.$axios.get(this.$httpUrl.selfShuttleConfigDelete, { params: params }).then(res => {
            if (res != false) {
              this.message.success('删除成功')
              this.tableData.splice(ind, 1)
            }
          })
        } else {
          this.tableData.splice(ind, 1)
        }
      }).catch(() => console.log('取消删除操作'))
    }
  }
}
</script>
<style lang ="less" scoped>
.FleetCarDetail {
  .handle-wrap {
    height: 70px;
    background-color: #fff;
    padding: 15px 20px;
    .tt {
      float: left;
      font-size: 18px;
      color: #000;
      margin-top: 4px;
      font-weight: bold;
    }
    .inp-wrap {
      width: 272px;
    }
    .btn {
      float: left;
      margin-left: 15px;
    }
    .btn-station {
      width: 98px;
    }
    .btn-save {
      width: 85px;
    }
  }
  .table {
    .btn {
      width: 85px;
    }
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
    .iconfont {
      color: #000;
      font-size: 18px;
      vertical-align: -2px;
      padding-right: 5px;
      cursor: pointer;
    }
  }
}
</style>
<style lang="less">
.FleetCarDetail {
  .handle-wrap {
    .inp-wrap {
      min-width: 242px;
      .el-input__inner {
        padding-left: 12px;
      }
      .el-input__suffix {
        top: -4px;
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




