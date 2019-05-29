<template>
  <div class="set-origin">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>行车轨迹</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="amap-page-container" id="container">
      <el-amap
        ref="maps"
        :amap-manager="maps.amapManager"
        vid="amapDemo"
        :plugin="maps.plugin"
        :events="maps.events"
      ></el-amap>
    </div>
    <div class="panel-container" v-if="maps.order_car.start_address">
      <p class="line" v-if="maps.order_car.end_address">起点：{{maps.order_car.start_address}}</p>
      <p class="line" v-if="maps.order_car.end_address">终点：{{maps.order_car.end_address}}</p>
      <p class="line">
        <span v-if="maps.order_car.end_address">实际行驶公里数：</span>
        <span v-else>当前行驶公里数：</span>
        {{maps.order_car.totalmilestat}}
      </p>
      <p class="line">
        <span v-if="maps.order_car.end_address">实际已行驶时间：</span>
        <span v-else>当前行驶时间：</span>
        {{maps.order_car.totaltime}}
      </p>
      <p class="line" v-if="!maps.order_car.end_address">当前时速：{{maps.order_car.speed}}</p>
    </div>
  </div>
</template>
<script>
import car from "@/assets/images/icon_mapcar.png";
export default {
  name: "vehicleTrajectory",
  data () {
    return {
      map: null,
      maps: {
        tracking: {},
        center: null,
        carSrc: car,
        order_car: {
          start_lnglat: null, //开始地点经纬度
          start_address: null, //开始地点
          end_lnglat: null, //终点经纬度
          end_address: null, //终点地址
          totalmilestat: null, //总里程
          totaltime: null, //总时间
          car_number: null, //车牌号
          speed: null, //当前速度
          wait_time: null
        },
        pathLineStyle: {
          strokeStyle: "#FF991F",
          lineWidth: 6,
          borderStyle: "#ffffff",
          borderWidth: 0,
          dirArrowStyle: true
        },
        startPointStyle: {
          radius: 6,
          fillStyle: "#0DB592",
          strokeStyle: "#ffffff",
          lineWidth: 0
        },
        endPointStyle: {
          radius: 6,
          fillStyle: "#EF5A48",
          strokeStyle: "#ffffff",
          lineWidth: 0
        },
        keyPointStyle: {
          radius: 0,
          fillStyle: "#4A90E2",
          strokeStyle: "#ffffff",
          lineWidth: 0
        },
        pathSimplifierIns: null,
        PathSimplifier: null,
        PointSimplifier: null,
        pointSimplifierIns: null,
        navg1: null,
        path: [],
        remark: [],
        amapManager: new this.$VueAMap.AMapManager(),
        events: {
          init: o => {
            var map = this.map = new AMap.Map("container", {
              zoom: 13,
              center: [119.982571, 30.276524]
              // center:  this.maps.center
            });
            let p = new Promise((reslove, reject) => {
              this.getMapInfo(reslove);
            });
            p.then((isStart) => {
              if (isStart) {
                this.initStartPoint()
              } else {
                this.setPoint();
                this.loadAMapUI();
                this.initTimePoint()
              }
            });
          },
          moveend: () => { },
          zoomchange: () => { },
          click: e => {
          }
        },
        plugin: []
      },
      ind: 0,
      marker: null
    };
  },
  created () { },
  methods: {
    getMapInfo (reslove, isExpand) {
      let order_car_id = this.$route.query.order_car_id;
      if (!order_car_id) return;
      let params = { order_car_id: order_car_id };
      this.$axios
        .get(this.$httpUrl.gpshistory, { params: params })
        .then(res => {
          if (res) {
            if (res.history) {
              // 如果行程开始，则销毁起点小车
              if (this.maps.tracking) {
                this.clearMarker()
                this.maps.tracking = {}
              }
              let list = res.history.list
              // 判断路径是否存在
              if (list.length) {
                this.maps.order_car = res.order_car
                this.maps.path = list
                this.maps.remark = res.history.remark
                this.map.setCenter(list[list.length - 1].lnglat)
                // 判断地图是否初始结束
                if (reslove) reslove();
                // 判断是否开始执行扩展轨迹
                if (isExpand) {
                  this.expandPoint()
                  this.expandPath()
                  if (!res.order_car.end_address) {
                    let seconds = res.order_car.refresh_seconds || 10
                    setTimeout(this.getMapInfo, seconds * 1000, null, true)
                  }
                }
                // 当到达终点
                if (res.order_car.end_address) {
                  this.setPoint();
                }
              } else {
                this.$message.info('行程暂无数据')
              }
            } else if (!Array.isArray(res.tracking)) {
              if (!this.maps.tracking) {
                this.maps.tracking = res.tracking
                reslove(true)
              }  else {
                this.maps.tracking = res.tracking
                this.initStartPoint()
              }
              let seconds = res.order_car.refresh_seconds || 10
              setTimeout(this.getMapInfo, seconds * 1000, null, true)
            } else {
              this.$message.info(res.message)
            }
          }
        });
    },
    clearMarker () {
      let marker = this.marker
      if (marker) {
        marker.setMap(null);
        marker = null;
      }
    },
    initStartPoint () {
      let tracking = this.maps.tracking
      var m =  this.marker = new AMap.Marker({
        position: [tracking.lng, tracking.lat],
        icon: this.maps.carSrc,
      })
      m.setAngle(tracking.course)
      this.map.add([m])
      this.map.setFitView([m], null, [5, 5, 5, 5], null)
    },
    expandPoint () {
      this.maps.pointSimplifierIns.setData(this.maps.remark);
    },
    expandPath (res) {
      var maps = this.maps;
      if (maps.order_car.end_address) return
      var pathSimplifierIns = maps.pathSimplifierIns;
      var data = [maps.path];
      var navg1 = pathSimplifierIns.createPathNavigator(
        0,
        {
          loop: false,
          pathNavigatorStyle: {
            width: 12,
            height: 24,
            //使用图片
            content: this.maps.PathSimplifier.Render.Canvas.getImageContent(
              this.maps.carSrc,
              this.carOnload,
              this.carOnerror
            ),
          }
        }
      );
      var endstep = navg1.getPathEndIdx();
      navg1.moveToPoint(endstep)
    },
    carOnload () {
      this.maps.pathSimplifierIns.renderLater();
    },
    carOnerror () {
      alert("图片加载失败！");
    },
    // 初始轨迹线
    loadAMapUI () {
      let map = this.map;
      let maps = this.maps;
      let that = this;
      AMapUI.load(["ui/misc/PathSimplifier"], PathSimplifier => {
        this.maps.PathSimplifier = PathSimplifier;
        var pathSimplifierIns = (this.maps.pathSimplifierIns = new PathSimplifier(
          {
            zIndex: 100,
            map: map, //所属的地图实例
            // autoSetFitView: false,
            getPath: function (pathData, pathIndex) {
              var points = pathData;
              var lnglatList = [];
              for (var i = 0, len = points.length; i < len; i++) {
                lnglatList.push(points[i].lnglat);
              }
              return lnglatList;
            },
            getHoverTitle: function (pathData, pathIndex, pointIndex) {
              if (pointIndex == 0) return "起点：" + maps.order_car.start_address;
              let len = pathData.length
              if (pointIndex < len - 2) return
              let childData = pathData[pointIndex]
              let isEqualLng = pathData[len - 1].lng
              let isEqualLat = pathData[len - 1].lat
              // 避免最后两条数据经纬度重复，导致目的地不能显示出来
              if ((pointIndex == (len - 1) || (isEqualLng && isEqualLat)) && maps.order_car && maps.order_car.end_address) {
                return "终点：" + maps.order_car.end_address;
              }
              return;
            },
            renderOptions: {
              //轨迹线的样式
              startPointStyle: maps.startPointStyle,
              pathLineStyle: maps.pathLineStyle,
              // 轨迹节点的绘制样式
              keyPointStyle: maps.keyPointStyle,
              keyPointOnSelectedPathLineStyle: null,
              keyPointHoverStyle: null,
              // 选中轨迹线的样式
              pathLineSelectedStyle: maps.pathLineStyle,
              pathLineHoverStyle: maps.pathLineStyle,

              endPointStyle: maps.endPointStyle,
              renderAllPointsIfNumberBelow: 800 //绘制路线节点，如不需要可设置为-1,
            }
          }
        ));
        //设置数据
        pathSimplifierIns.setData([this.maps.path]);
        //对第一条线路（即索引 0）创建一个巡航器
        if (maps.order_car.end_address) return
        var navg1 = this.maps.navg1 = pathSimplifierIns.createPathNavigator(
          0,
          {
            loop: false,
            pathNavigatorStyle: {
              width: 12,
              height: 24,
              //使用图片
              content: PathSimplifier.Render.Canvas.getImageContent(
                this.maps.carSrc,
                this.carOnload,
                this.carOnerror
              ),
            }
          }
        );
        let m = navg1.getPathEndIdx();
        navg1.moveToPoint(m)
        this.getMapInfo(null, true)
      });
    },
    // 初始等候点
    initTimePoint () {
      AMapUI.loadUI(['misc/PointSimplifier'], PointSimplifier => {
        var pointSimplifierIns = this.maps.pointSimplifierIns = new PointSimplifier({
          map: this.map, //关联的map
          compareDataItem: function (a, b, aIndex, bIndex) {
            //数据源中靠后的元素优先，index大的排到前面去
            return aIndex > bIndex ? -1 : 1;
          },
          // getPosition: function (dataItem) {
          //   //返回数据项的经纬度，AMap.LngLat实例或者经纬度数组
          //   return dataItem.position;
          // },
          getPosition: function (pathData) {
            return pathData.lnglat;
          },
          getHoverTitle: function (pathData, pointIndex) {
            return "等候" + pathData.wait_time;
          },
          renderOptions: {
            pointStyle: this.maps.keyPointStyle
          }
        });
        //设置数据源，data需要是一个数组
        pointSimplifierIns.setData(this.maps.remark);
      });

    },
    setPoint () {
      let map = this.map;
      let order_car = this.maps.order_car;
      if (!order_car.start_lnglat) return;
      let startArr = order_car.start_lnglat.split(",");
      // 创建地图实例
      var startIcon = new AMap.Icon({
        // 图标尺寸
        size: new AMap.Size(25, 34),
        // 图标的取图地址
        image:
          "http://a.amap.com/jsapi_demos/static/demo-center/icons/dir-marker.png",
        // 图标所用图片大小
        imageSize: new AMap.Size(135, 40),
        // 图标取图偏移量
        imageOffset: new AMap.Pixel(-7, -3)
      });

      // 将 icon 传入 marker
      var startMarker = new AMap.Marker({
        position: new AMap.LngLat(startArr[0], startArr[1]),
        icon: startIcon,
        offset: new AMap.Pixel(-12, -25)
      });

      if (!order_car.end_lnglat) {
        map.add([startMarker]);
        return
      }
      let endArr = order_car.end_lnglat.split(",");
      // 创建一个 icon
      var endIcon = new AMap.Icon({
        size: new AMap.Size(25, 34),
        image:
          "http://a.amap.com/jsapi_demos/static/demo-center/icons/dir-marker.png",
        imageSize: new AMap.Size(135, 40),
        imageOffset: new AMap.Pixel(-95, -3)
      });

      // 将 icon 传入 marker
      var endMarker = new AMap.Marker({
        position: new AMap.LngLat(endArr[0], endArr[1]),
        icon: endIcon,
        offset: new AMap.Pixel(-13, -30)
      });

      // 将 markers 添加到地图
      map.add([startMarker, endMarker]);
    }
  }
};
</script>
<style lang ="less" scoped>
.amap-page-container {
  width: 100%;
  height: 100%;
  position: absolute;
  left: 0;
  top: calc(~"100%-63px");
  cursor: pointer;
}
.panel-container {
  position: absolute;
  top: 70px;
  left: 10px;
  width: 348px;
  z-index: 99;
  box-shadow: 1px 2px 3px 0px rgba(0, 0, 0, 0.35);
  border-radius: 4px;
  .line {
    background-color: #fff;
    font-size: 12px !important;
    line-height: 16px;
    padding: 6px;
    color: #333;
    span {
      font-size: 12px !important;
    }
  }
}
</style>