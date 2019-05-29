<template>
  <div class="transport-station">
    <el-form-item
      :label="index === 0 ? '接站': '送站'"
      class="inline-item"
      v-for="(item,index) in reqParams"
      :key="index"
    >
      <div class="out-box car-form">
        <el-form-item
          :prop="'total_order_cars.' + index + '.begin_at_date'"
          :rules="missionRules.begin_at_date"
          label="开始日期"
          class="inline-item"
        >
          <el-date-picker
            v-model="item.begin_at_date"
            type="date"
            prefix-icon="''"
            class="date longwidth"
            value-format="yyyy-MM-dd"
            placeholder="请选择日期"
            @focus="getTimeIndex(index,1)"
            :disabled="index === 1 && !reqParams[0].end_at_date "
            @click.native="verifyDateTip(1,index === 0 ? 1: 3,(index === 1 && reqParams[0].end_at_date))"
            :picker-options="index === 0 ? '': pickerOptions3"
          ></el-date-picker>
          <span class="lbl-name">时间</span>
        </el-form-item>
        <el-form-item
          :prop="'total_order_cars.' + index + '.begin_at_time'"
          :rules="missionRules.begin_at_time"
          class="inline-item time"
        >
          <el-input
            maxlength="5"
            class="time"
            v-model.trim="item.begin_at_time"
            @focus="getTimeIndex(index, 1)"
            @blur="formatClock(index)"
            @input="addColon(index, 1)"
            :disabled="index === 1 && (!reqParams[0].end_at_time || !reqParams[0].end_at_date)"
            @click.native="verifyDateTip(2, index === 0 ? 1: 3,(index === 1 && reqParams[0].end_at_time &&reqParams[0].end_at_date))"
            placeholder="请填写"
          ></el-input>
        </el-form-item>
        <el-form-item
          :prop="'total_order_cars.' + index + '.end_at_date'"
          :rules="missionRules.end_at_date"
          label="结束日期"
          class="inline-item"
        >
          <el-date-picker
            v-model="item.end_at_date"
            type="date"
            prefix-icon="''"
            class="date longwidth"
            value-format="yyyy-MM-dd"
            placeholder="请选择日期"
            :disabled="!item.begin_at_date"
            @focus="getTimeIndex(index,2)"
            @click.native="verifyDateTip(1,index === 0 ? 2: 4,item.begin_at_date)"
            :picker-options="index === 0 ? pickerOptions2: pickerOptions4"
          ></el-date-picker>
          <span class="lbl-name">时间</span>
        </el-form-item>
        <el-form-item
          :prop="'total_order_cars.' + index + '.end_at_time'"
          :rules="missionRules.end_at_time"
          class="inline-item time"
        >
          <el-input
            @focus="getTimeIndex(index, 2)"
            @input="addColon(index,2)"
            @blur="formatClock(index)"
            maxlength="5"
            class="time"
            :disabled="!item.begin_at_time || !item.begin_at_date"
            @click.native="verifyDateTip(2, index === 0 ? 2: 4,item.begin_at_time && item.begin_at_date)"
            v-model="item.end_at_time"
            placeholder="请填写"
          ></el-input>
        </el-form-item>
        <el-form-item
          :prop="'total_order_cars.'+index+'.travels.travel_from_place'"
          :rules="missionRules.travel_from_place"
          label="起点"
          class="inline-item"
        >
          <el-input
            :id="'travel_from_place'+index"
            type="text"
            @blur="formatPlace(1,index, null)"
            v-model="item.travels.travel_from_place"
          ></el-input>
          <a
            href="javascript:void(0);"
            class="btn btn-add-center"
            @click="addCenterAddress(index)"
          >添加途径地</a>
        </el-form-item>
        <el-form-item
          label="途径点"
          v-for="(childItem, childIndex) in item.travels.travel_pathway"
          :key="'center'+childIndex"
        >
          <input
            :id="'travel_center_place'+index+childIndex"
            class="place"
            type="text"
            @blur="formatPlace(null,index, childIndex)"
            v-model="childItem.travel_from_place"
          >
          <span
            class="iconfont icon-guanbi1 icon-close"
            @click="removeCenterAddress(index, childIndex)"
          ></span>
        </el-form-item>
        <el-form-item
          label="终点"
          :prop="'total_order_cars.'+index+'.travels.travel_to_place'"
          :rules="missionRules.travel_to_place"
        >
          <el-input
            :id="'travel_to_place'+index"
            @blur="formatPlace(2,index, null)"
            v-model="item.travels.travel_to_place"
          ></el-input>
        </el-form-item>
        <!-- 出行车辆 -->
        <div v-for="(driverItem, driverIndex) in item.driver_cars" :key="'driver'+driverIndex">
          <el-form-item
            label="出行车辆"
            class="car-form-item"
            :prop="'total_order_cars.'+index+'.driver_cars.'+driverIndex+'.car_number'"
            :rules="missionRules.car_number"
          >
            <el-input
              v-model="driverItem.car_number"
              readonly
              placeholder="请选择车辆"
              @click.native="edit(index)"
            ></el-input>
            <span class="outcar" v-if="driverItem.car_source == 2">外援</span>
          </el-form-item>
          <el-form-item
            label="司机姓名"
            class="inline-item"
            :prop="'total_order_cars.'+index+'.driver_cars.'+driverIndex+'.driver_name'"
            :rules="missionRules.driver_name"
          >
            <el-autocomplete
              value-key="name"
              placeholder="请输入司机姓名"
              v-model.trim="driverItem.driver_name"
              class="longwidth"
              :fetch-suggestions="querySearchDriver"
              @select="handleSelectDriver"
              @focus="getDriverIndex(index, driverIndex)"
            ></el-autocomplete>
          </el-form-item>
          <el-form-item
            label="联系方式"
            class="inline-item"
            :prop="'total_order_cars.'+index+'.driver_cars.'+driverIndex+'.driver_mobile'"
            :rules="missionRules.driver_mobile"
          >
            <el-input v-model="driverItem.driver_mobile" placeholder="请输入联系方式"></el-input>
          </el-form-item>
          <!-- 副班司机 -->
          <div
            v-for="(assistantItem, assistantIndex) in driverItem.vice_drivers"
            :key="assistantIndex"
          >
            <el-form-item label="司机姓名" class="inline-item">
              <el-autocomplete
                value-key="name"
                placeholder="请输入司机姓名"
                v-model.trim="assistantItem.driver_name"
                class="longwidth"
                :fetch-suggestions="querySearchDriver"
                @select="handleSelectAssistant"
                @focus="getDriverIndex(index, driverIndex,assistantIndex)"
              ></el-autocomplete>
            </el-form-item>
            <el-form-item label="联系方式" class="inline-item">
              <el-input v-model="assistantItem.driver_mobile" placeholder="请输入联系方式"></el-input>
            </el-form-item>
              <span
                  class="iconfont icon-guanbi1 icon-close"
                  @click="deleteDriver(index, driverIndex,assistantIndex)"
                ></span>
          </div>
          <!-- 副班司机 -->
          <el-form-item v-if="driverItem.car_source != 2">
            <a
              href="javascript:void(0);"
              class="btn btn-add-driver"
              @click="addAssistant(index,driverIndex)"
            >添加副班司机</a>
          </el-form-item>
          <el-form-item v-if="driverItem.car_source == 2" class="inline-item" label="外援费用">
            <el-input placeholder="请输入外援费用" v-model="driverItem.foreign_money"></el-input>
          </el-form-item>
          <el-form-item v-if="driverItem.car_source == 2" class="inline-item" label="代收款">
            <el-input placeholder="请输入代收款" v-model="driverItem.collection_money"></el-input>
          </el-form-item>
          <el-form-item
            label="现收款"
            class="inline-item deposit"
            :prop="'total_order_cars.'+index+'.driver_cars.'+driverIndex+'.my_collection_money'"
            :rules="missionRules.my_collection_money"
             v-if="driverItem.car_source == 1" 
          >
            <el-input v-model="driverItem.my_collection_money" placeholder="请输入现收款"></el-input>
          </el-form-item>
          <el-form-item label="司机备注" class="remark">
            <el-input
              type="textarea"
              maxlength="200"
              v-model="driverItem.remark_to_driver"
              placeholder="请输入司机备注"
            ></el-input>
          </el-form-item>
        </div>
        <!-- 出行车辆 -->
        <a href="javascript:void(0);" class="btn btn-add-car" @click="edit(index)">添加车辆</a>
      </div>
    </el-form-item>
    <div id="mapContainer2">
      <el-amap ref="maps" :amap-manager="maps.amapManager" vid="amapDemo" :events="maps.events"></el-amap>
    </div>
    <car-style-box
      :car-style-box-show.sync="carStyleBoxShow"
      :car-info="carInfo"
      :date-interval="dateInterval"
      @rtnSelectedList="rtnSelectedList"
      @rtnCarDaysInfo="rtnCarDaysInfo"
    ></car-style-box>
    <selected-car-box
      :selectedCarBoxShow.sync="selectedCarBoxShow"
      :dateInterval="dateInterval"
      :arrangeInfo="arrangeInfo"
      @rtnCarDaysInfo="rtnCarDaysInfo"
    ></selected-car-box>
  </div>
</template>
<script>
import carStyleBox from '@/components/carStyleBox'
import selectedCarBox from '@/components/selectedCarBox'
export default {
  name: 'TransportStation',
  components: {
    carStyleBox,
    selectedCarBox,
  },
  props: {
    reqInfo: {
      type: Array,
      value: []
    },
    orderType:{
      type: [String,Number]
    }
  },
  data () {
    var validateMobile = (rule, value, callback) => {
      let reg = /^[1]\d{10}$/
      if (!reg.test(value)) {
        callback(new Error('手机号格式错误'))
      } else {
        callback()
      }
    }
    var validateMoney = (rule, value, callback) => {
      let reg = /^([0]|([1-9]\d*))((\.\d*){0,1})$/g
      if (!reg.test(value) && value) {
        if (value < 0) {
          callback(new Error('金额不能小于0'))
        } else {
          callback(new Error('请输入正确的金额'))
        }
      } else {
        callback()
      }
    }
    return {
      reqParams: [{ shuttle_type: '1', travels: { travel_pathway: [] }, driver_cars: [{ vice_drivers: [] }] }, { shuttle_type: '2', travels: { travel_pathway: [] }, driver_cars: [{ vice_drivers: [] }] }],
      timeIndex: null,
      timeType: null,
      pickerOptions2: {
        disabledDate: (time) => {
          return time.getTime() <= Date.parse(this.reqParams[0].begin_at_date) - 3600 * 24 * 1000
        },
      },
      pickerOptions3: {
        disabledDate: (time) => {
          return time.getTime() <= Date.parse(this.reqParams[0].end_at_date) - 3600 * 24 * 1000
        },
      },
      pickerOptions4: {
        disabledDate: (time) => {
          return time.getTime() <= Date.parse(this.reqParams[1].begin_at_date) - 3600 * 24 * 1000
        },
      },
      maps: {
        amapManager: new this.$VueAMap.AMapManager(),
        events: {
          init: o => {
            let arr = ['travel_from_place', 'travel_to_place']
            arr.forEach(ele => {
              this.initAmap(ele + '0', 0, null)
              this.initAmap(ele + '1', 1, null)
            })
          }
        }
      },
      placeType: null,
      placeIndex: null,
      placeChildIndex: null,
      selected: false,
      carStyleBoxShow: false,
      selectedCarBoxShow: false,
      arrangeInfo: null,
      boxIndex: 1,
      dateInterval: null,
      carInfo: [{}],
      order_cars_index: null,
      assistantIndex: null,
      temp_driver_name: null,
      missionRules: {
        travel_from_place: { required: true, message: '请输入起点', trigger: ['blur', 'change'] },
        travel_to_place: { required: true, message: '请输入终点', trigger: ['blur', 'change'] },
        my_collection_money: { required: true, validator: validateMoney, trigger: ['blur', 'change'] },
        car_number: { required: true, message: '请输入出行车辆', trigger: ['blur', 'change'] },
        begin_at_date: { required: true, message: '请选择开始日期', trigger: ['blur', 'change'] },
        end_at_date: { required: true, message: '请选择结束日期', trigger: ['blur', 'change'] },
        begin_at_time: { required: true, message: '选择时间', trigger: ['blur', 'change'] },
        end_at_time: { required: true, message: '选择时间', trigger: ['blur', 'change'] },
        driver_name: { required: true, message: '请输入司机姓名', trigger: ['blur', 'change'] },
        driver_mobile: { required: true,  message: '请输入手机号', trigger: ['blur', 'change'] },
        // validator: validateMobile,
      }
    }
  },
  watch: {
    reqParams: {
      handler (newVal) {
        this.$emit('update:reqParams', newVal)
      },
      deep: true
    },
    reqInfo: {
      handler (newVal) {
        this.reqParams = newVal
        this.carInfo = newVal[this.boxIndex].driver_cars
      },
      deep: true
    },
    boxIndex (newVal) {
      this.carInfo = this.reqInfo[this.boxIndex].driver_cars
    }
  },
  methods: {
    getTimeIndex (ind, type) {
      this.timeIndex = ind
      this.timeType = type === 2 ? 'end_at_time' : 'begin_at_time'
    },
    // 验证时分格式
    formatClock (ind) {
      let reg2 = /^(([0-1]?[0-9])|([2]?[0-3]))(:?)[0-5]([0-9])$/
      let obj = this.reqParams[ind]
      let time = obj[this.timeType]
      if (!reg2.test(time)) {
        this.reqParams[ind][this.timeType] = null
        return
      }
      if (this.timeType === 'end_at_time') {
        let time2 = obj['begin_at_time']
        if (!obj['end_at_date']) return
        let date1 = obj['end_at_date'].replace('/') + " " + time + ":00"
        let date2 = obj['begin_at_date'].replace('/') + " " + time2 + ":00"
        if (Date.parse(date1) <= Date.parse(date2)) {
          this.reqParams[ind][this.timeType] = null
          let place = ind === 1 ? '送站' : '接站'
          this.$message.error(`${place}结束时间不得小于接站开始时间`)
        }
      } else {
        if (ind !== 0) {
          let obj2 = this.reqParams[ind - 1]
          if (!obj['begin_at_date']) return
          let time2 = obj2['end_at_time']
          let date1 = obj['begin_at_date'].replace('/') + " " + time + ":00"
          let date2 = obj2['end_at_date'].replace('/') + " " + time2 + ":00"
          if (Date.parse(date1) <= Date.parse(date2)) {
            this.reqParams[ind][this.timeType] = null
            if (ind === 1) this.$message.error('送站开始时间不得小于接站结束时间')
          }
        }
      }
    },
    // 增加冒号
    addColon (index, type) {
      let timeType = type === 2 ? 'end_at_time' : 'begin_at_time'
      let reg1 = /^(([0-1][0-9])|([2][0-3]))$/
      let newVal = this.reqParams[index][timeType]
      if (reg1.test(newVal)) {
        this.reqParams[index][timeType] = newVal + ':'
      }
    },
    // 点击处于disbaled的日期提示
    verifyDateTip (dataType, type, status) {
      if (status) return
      let dateMsg = dataType === 1 ? '日期' : '时间'
      if (type === 2) {
        this.$message(`请先选择接站开始${dateMsg}`)
      } else if (type === 3) {
        this.$message(`请先选择接站结束${dateMsg}`)
      } else if (type === 4) {
        this.$message(`请先选择送站开始${dateMsg}`)
      }
    },
    initAmap (ele, index, childIndex) {
      // travel_center_place0 1 0
      let that = this
      var map = new AMap.Map('mapContainer2')
      AMap.plugin(['AMap.Autocomplete', 'AMap.PlaceSearch'], function () {
        var autoOptions = {
          city: '杭州', // 城市，默认全国
          input: ele// 使用联想输入的input的id
        }
        var autocomplete = new AMap.Autocomplete(autoOptions)
        var placeSearch = new AMap.PlaceSearch({
          city: '杭州',
          map: map
        })
        AMap.event.addListener(autocomplete, 'select', function (e) {
          // that.selected = true
          let place = null, lng = null, lat = null
          if (e.poi.location && e.poi.location.lng) {
            place = e.poi.name
            lng = e.poi.location.lng
            lat = e.poi.location.lat
          } else {
            that.$message('当前输入的地址不准确，请重新输入')
            setTimeout(() => {
              this.input.value = ''
            }, 200)
          }
          let travels = that.reqParams[index].travels
          if (!childIndex && childIndex !== 0) {
            let obj = {}
            let sign = ele.includes('from') ? 'from' : 'to'
            obj[`travel_${sign}_place`] = place
            obj[`travel_${sign}_lat`] = lat
            obj[`travel_${sign}_lng`] = lng
            obj[`travel_${sign}_temp`] = place
            that.reqParams[index].travels = Object.assign({}, travels, obj)
          } else {
            let travel_pathway = travels.travel_pathway
            let obj = {
              travel_from_place: place,
              travel_from_lat: lat,
              travel_from_lng: lng,
              travel_from_temp: place,
            }
            that.$set(travels.travel_pathway, childIndex, obj)
          }
        })
      })
    },
    formatPlace (type, index, childIndex) {
      let hasError = false
      let travels = this.reqParams[index].travels
      if (!childIndex && childIndex !== 0) {
        let sign = type === 1 ? 'from' : 'to'
        let travels = this.reqParams[index].travels
        if (!travels[`travel_${sign}_place`]) return
        let lng = travels[`travel_${sign}_lng`]
        // 最新的地名跟选择栏的地名不符
        if (lng && travels[`travel_${sign}_temp`] !== travels[`travel_${sign}_place`]) {
          let obj = {}
          obj[`travel_${sign}_lat`] = null
          obj[`travel_${sign}_lng`] = null
          obj[`travel_${sign}_temp`] = null
          this.reqParams[index].travels = Object.assign({}, travels, obj)
        }
      } else {
        let pathwayObj = travels.travel_pathway[childIndex]
        if (!pathwayObj[`travel_from_place`]) return
        let lng = pathwayObj[`travel_from_lng`]
        if (lng && pathwayObj[`travel_from_temp`] !== pathwayObj[`travel_from_place`]) {
          let obj = {}
          obj[`travel_from_lat`] = null
          obj[`travel_from_lng`] = null
          obj[`travel_from_temp`] = null
          this.$set(travels.travel_pathway, childIndex, obj)
        }
      }
    },
    // 增加途径点
    addCenterAddress (index) {
      this.reqParams[index].travels.travel_pathway.push({})
      let travel_pathway = this.reqParams[index].travels.travel_pathway
      let childIndex = travel_pathway.length - 1
      let ele = 'travel_center_place'
      this.$nextTick(function () {
        this.initAmap(ele + index+childIndex, index, childIndex)
      })
    },
    // 删除途经点
    removeCenterAddress (index, childIndex) {
      this.reqParams[index].travels.travel_pathway.splice(childIndex, 1)
    },
    deleteDriver(index, driverIndex,assistantIndex){
      this.reqParams[index].driver_cars[driverIndex].vice_drivers.splice(assistantIndex, 1)
    },
    // 打开选择车辆框
    edit (index) {
      let obj = this.reqParams[index]
      this.boxIndex = index
      let begin_at_date = obj.begin_at_date
      let end_at_date = obj.end_at_date
      if (!begin_at_date || !end_at_date) {
        // 如果两个日期同时不存在，只提示开始日期
        let dateMsg = !begin_at_date ? '开始日期' : '结束日期'
        let place = index === 0 ? '接站' : '送站'
        this.$message.error(`请选择${place}${dateMsg}`)
        return
      }
      this.dateInterval = { start_time: begin_at_date, end_time: end_at_date ,order_type:this.orderType}
      this.carStyleBoxShow = true
    },
    rtnCarDaysInfo (res) {
      this.carInfo = res
      let arr = []
      res.forEach(ele => {
        let obj = {
          driver_id: ele.driver_id,
          driver_name: ele.driver_name,
          driver_mobile: ele.driver_mobile,
          remark_to_driver: null,
          car_source: ele.car_source,
          user_car_id: ele.user_car_id,
          foreign_money: ele.foreign_money,
          car_number: ele.car_number,
          collection_money: null,
          my_collection_money: null,
          vice_drivers: ele.vice_drivers,
          begin_at_date: ele.begin_at_date,
          end_at_date:  ele.end_at_date
        }

        arr.push(obj)
      });
      this.reqParams[this.boxIndex].driver_cars = arr
      this.carStyleBoxShow = false
      this.selectedCarBoxShow = false
    },
    rtnSelectedList (obj) {
      this.selectedCarBoxShow = true
      this.arrangeInfo = obj
    },
    querySearchDriver (queryString, cb) {
      let params = {
        keywords: queryString
      }
      this.$axios.get(this.$httpUrl.keywordsDriver, { params: params }).then(res => {
        if (res) {
          cb(res)
        }
      })
    },
    handleSelectDriver (item) {
      let obj = {
        driver_id: item.id,
        driver_mobile: item.mobile,
        driver_name: item.name
      }
      this.temp_driver_name = item.name
      let order_cars_index = this.order_cars_index
      let car = this.reqParams[this.boxIndex].driver_cars
      this.$set(car,order_cars_index,Object.assign({}, car[order_cars_index],obj))
    },
    handleSelectAssistant (item) {
      let obj = {
        driver_id: item.id,
        driver_mobile: item.mobile,
        driver_name: item.name
      }
      let order_cars_index = this.order_cars_index
      let vice_drivers = this.reqParams[this.boxIndex].driver_cars[order_cars_index].vice_drivers
      this.$set(vice_drivers,this.assistantIndex,Object.assign({},vice_drivers[this.assistantIndex],obj))
    },
    getDriverIndex (index, driverIndex, assistantIndex) {
      this.order_cars_index = driverIndex,
      this.boxIndex = index
      if (assistantIndex || assistantIndex == 0) {
        this.assistantIndex = assistantIndex
      }
    },
    addAssistant (index, driverIndex) {
      this.reqParams[index].driver_cars[driverIndex].vice_drivers.push({})
    }
  }
}
</script>
<style lang="less" scoped>
.transport-station {
  .out-box {
    min-height: 400px;
    width: 631px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border: 1px solid #dcdfe6;
    border-radius: 4px;
    position: relative;
    padding-top: 14px;
    .el-form-item {
      .date {
        width: 93px;
      }
      .time {
        width: 56px;
      }
    }

    .lbl-name {
      margin: 0 5px;
      color: #333;
    }
    .el-form-item {
      margin-bottom: 12px;
      &.inline-item {
        display: inline-block;
      }
      &.car-form-item {
        position: relative;
        .outcar {
          position: absolute;
          left: 140px;
          top: 9px;
          padding: 4px 10px;
          line-height: 1;
          color: #fff;
          background-color: #ffb320;
        }
      }
    }
    .icon-close {
      color: #666;
      padding: 10px;
      cursor: pointer;
    }
  }
  .place {
    width: 193px;
    height: 32px;
    line-height: 32px;
    outline: none;
    border: 1px solid #dcdfe6;
    color: #606266;
    padding: 0 15px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border-radius: 4px;
  }
  .btn-add-center,
  .btn-add-driver {
    display: inline-block;
    width: 103px;
    margin-left: 10px;
  }
  .btn-add-driver {
    margin-left: 0;
  }
  .btn-add-car {
    width: 154px;
    margin-left: 30px;
    margin-bottom: 20px;
  }
  #mapContainers2 {
    position: relative;
    width: 0;
    height: 0;
  }
}
</style>
<style lang="less" >
@input-width: 193px;
@input-height: 32px;
@longWidth: 195px;
@textarea-width: 630px;
@textarea-height: 98px;
.transport-station {
  .out-box {
    .el-input,
    .el-autocomplete {
      width: @input-width;
      &.longwidth {
        width: @longWidth;
      }
      .el-input__inner {
        height: @input-height;
        line-height: @input-height;
        border-color: #dcdfe6;
      }
    }
    .el-form-item {
      &.time {
        .el-form-item__label {
          width: 90px !important;
        }
        .el-form-item__content {
          margin-left: 0 !important;
        }
      }
    }
  }
  .remark {
    .el-textarea {
      width: 482px;
      .el-textarea__inner {
        height: 49px;
      }
    }
  }
}
</style>