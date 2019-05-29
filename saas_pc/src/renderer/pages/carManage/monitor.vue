<template>
  <div class="monitor">
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
      <div class="amap-page-container" id="container">
        <el-amap ref="maps" :amap-manager="maps.amapManager" vid="amapDemo" :events="maps.events"></el-amap>
      </div>
      <div class="panel-container">
        <el-input
          class="inp-search"
          prefix-icon="el-icon-search"
          v-model="keywords"
          placeholder="请输入车牌号/司机姓名/司机手机号"
        ></el-input>
        <a href="javascript:void(0)" class="btn btn-search" @click="searchFrom">搜索</a>
      </div>
    </div>
  </div>
</template>

<script>
import car from "@/assets/images/icon_mapcar.png";
export default {
  data () {
    return {
      identityType: 'monitor',
      maps: {
        amapManager: new this.$VueAMap.AMapManager(),
        carSrc: car,
        events: {
          init: o => {
            var map = this.map = new AMap.Map("container", {
              zoom: [3, 18],
            })
            this.getMapInfo()
          },
          moveend: () => { },
          zoomchange: () => { },
          click: e => {
          }
        },
        list: []
      },
      keywords: null
    }
  },
  methods: {
    getMapInfo () {
      let params = { keywords: this.keywords };
      this.$axios
        .get(this.$httpUrl.gpsTracking, { params: params })
        .then(res => {
          if (res) {
            this.list = res.list
            let seconds = res.refresh_seconds || 10
            this.setCarPoint()
            setTimeout(this.getMapInfo, seconds* 1000)
          }
        });
    },
    setCarPoint () {
      let arr = []
      this.list.forEach(ele => {
        var m = new AMap.Marker({
          position: [ele.lng, ele.lat],
          icon: this.maps.carSrc,
          angle: ele.course,
        });
        m.setAngle(ele.course)
        m.setLabel({
          offset: new AMap.Pixel(-10, 50),
          content: `<div class='info' style='transform:rotate(-${ele.course}deg)'>${ele.car_number}</div>`
        });
        arr.push(m)
      });
      this.map.add(arr)
      this.map.setFitView(arr, null, [5, 5, 5, 5], null)
    },
    toggleIdentityType () {
      let identityType = this.identityType
      this.$router.push({ name: identityType })
    },
    searchFrom () {
      this.getMapInfo()
    }
  },
}
</script>
<style lang="less"  scoped>
.amap-page-container {
  width: 100%;
  height: 100%;
  position: absolute;
  left: 0;
  top: calc(~"100%-60px");
  cursor: pointer;
}
.panel-container {
  position: absolute;
  top: 70px;
  left: 10px;
  width: 348px;
  z-index: 99;
  border-radius: 4px;
  background-color: transparent;
  .inp-search {
    width: 273px;
  }
  .btn-search {
    display: inline-block;
    width: 60px;
    height: 32px;
    line-height: 32px;
    background-color: #fff;
    color: #333;
    box-sizing: border-box;
    border: 1px solid #ddd;
  }
}
</style>
<style lang="less">
.monitor {
  .inp-search {
    .el-input__inner {
      height: 32px;
      line-height: 32px;
    }
  }
  .amap-marker-label {
    border-color: #fff;
    background-color: transparent;
  }
  .info {
     background-color: #fff;
     padding: 5px 10px;
     position: absolute;
     top: -5px;
     left: -10px;
  }
}
</style>