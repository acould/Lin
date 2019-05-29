<template>
  <div class="shuttleBus">
    <el-form-item v-for='(item,index) in shuuleBusData' :key='index'>
      <div class='out-box car-form'>
        <span id="shuttleData" @click="removeShuttleBus(index)" v-if="shuuleBusData.length>1" class="iconfont icon-guanbi1 icon-close"></span>
        <!-- 收起 -->
        <div v-if='!item.show'>
          <el-form-item  v-for='(dateTravel,dateIndex) in item.date_travels' :key='dateIndex'> 
            <el-form-item class="inline-item" label='开始时间'
            :rules="missionRules.begin_at_date">   
              <el-input class="date"
                v-model.trim="dateTravel.begin_at_time"
                @focus="choseBeginTime(1,index,dateIndex)"
                @input='changeValue(index,dateIndex,1)'
                maxlength="5"
                disabled="disabled"
                @blur="checkTime(index,dateIndex,1)"
              placeholder='请填写时间'>
              </el-input>
            </el-form-item>
            <el-form-item  class="inline-item" label='结束时间'
            :rules="missionRules.end_at_date">
              <el-input class="date"
              @focus="choseBeginTime(2,index,dateIndex)"
              v-model.trim="dateTravel.end_at_time"
              maxlength="5"
              @input='changeEndValue(index,dateIndex,2)'
              disabled="disabled"
              @blur="checkTime(index,dateIndex,2)"
              placeholder='请填写时间'>
              </el-input>
            </el-form-item>
            <div class="cycle">运行周期 </div>
          </el-form-item>
          <div class='shuttleDriver'>
            <span class="car">  出行车辆:{{shuuleBusData[index].driver_cars[0].car_number}} </span>
            <span class='name'>  司机姓名：{{shuuleBusData[index].driver_cars[0].driver_name}} </span>
            <span class="mobile">联系方式：{{shuuleBusData[index].driver_cars[0].driver_mobile}}</span>  
             <span class="show" @click='showShuttle(index)'>展开</span>
          </div>
         
        </div>
        <!--展开 -->
        <el-form-item v-if='item.show' v-for='(dateTravel,dateIndex) in item.date_travels' :key='dateIndex'> 
          <el-form-item class="inline-item" label='开始时间'
          :rules="missionRules.begin_at_date">   
            <el-input class="date"
              v-model.trim="dateTravel.begin_at_time"
              @focus="choseBeginTime(1,index,dateIndex)"
              @input='changeValue(index,dateIndex,1)'
              maxlength="5"
              @blur="checkTime(index,dateIndex,1)"
             placeholder='请填写时间'>
            </el-input>
          </el-form-item>
          <el-form-item class="inline-item" label='结束时间'
          :rules="missionRules.end_at_date">
            <el-input class="date"
            @focus="choseBeginTime(2,index,dateIndex)"
            v-model.trim="dateTravel.end_at_time"
            maxlength="5"
            @input='changeEndValue(index,dateIndex,2)'
            :disabled="!dateTravel.begin_at_time"
            @blur="checkTime(index,dateIndex,2)"
            placeholder='请填写时间'>
            </el-input>
          </el-form-item>
          <div class="cycle" @click="choseCycle(index,dateIndex,dateTravel.cycle_date_pc,dateTravel.cycle_date)">运行周期 </div>
          <el-form-item   :rules="missionRules.travel_from_place" class="btn-add-center" label='起点'>
            <el-input :id="'travel_from_place'+index+dateIndex" class="date" v-model="dateTravel.travels.travel_from_place"></el-input>
          </el-form-item>
           <el-form-item  :rules="missionRules.travel_to_place"  class="btn-add-center" label='终点'>
            <el-input :id="'travel_to_place'+index+dateIndex"  class="date" v-model="dateTravel.travels.travel_to_place"></el-input>
          </el-form-item>
          <div  class='addPasway' @click="addPasway(index,dateIndex)">添加途经点</div>
          <el-form-item label="行程"> 
              <el-input  v-model="dateTravel.travels.travel_content" class="date" placeholder="请输入行程"></el-input>
          </el-form-item>
          <el-form-item  class='passWayLine' label='途经点' v-for='(placeData,placeIndex) in dateTravel.travels.travel_pathway' :key='placeIndex'>
            <input :id="'travel_pass'+index+dateIndex+placeIndex" class='pathWay' v-model="placeData.travel_from_place"/>
            <span class="iconfont icon-guanbi1 icon-close" @click="deletePathway(index,dateIndex,placeIndex)"></span>   
          </el-form-item>
           <!--  -->
          <div class='deletePaw' v-if='shuuleBusData[index].date_travels.length>1' @click='deletePaw(index,dateIndex)'>删除</div>
        </el-form-item>
        <div v-if='item.show' class='addTrip' @click='addTrip(index)'>添加趟数</div>
        <div v-if='item.show' >
          <el-form-item v-for="(carData,carIndex) in item.driver_cars" :key="carIndex">
            <el-form-item  class='passWayLine addCarClass' label="出行车辆">
              <input class='pathWay' placeholder="请选择车辆" readonly v-model="carData.car_number" @click='edit(index,carIndex,3)'/>
              <span class="outcar" v-if="carData.car_source == 2">外援</span>
              <span @click="deleteCar(index,carIndex)" v-if="shuuleBusData[index].driver_cars.length>1"  class="iconfont icon-guanbi1 icon-close"></span>
            </el-form-item>         
            <el-form-item :rules="missionRules.driver_name" label="司机姓名">
                <el-autocomplete
                    value-key="name"
                    placeholder="请输入司机姓名"
                    v-model.trim="carData.driver_name"
                    class="longwidth"
                    :fetch-suggestions="querySearchDriver"
                    @select="handleSelectDriver"
                    @focus="getViceIndex(index,carIndex)"
                  ></el-autocomplete>
            </el-form-item>
            <el-form-item :rules="missionRules.driver_mobile" label="联系方式">
              <el-input  class="date" v-model="carData.driver_mobile"></el-input>
            </el-form-item>
            <div v-for="(viceDriver,viceIndex) in carData.vice_drivers" :key="viceIndex">
                <el-form-item label="司机姓名">
                  <el-autocomplete
                    value-key="name"
                    placeholder="请输入司机姓名"
                    v-model.trim="viceDriver.driver_name"
                    class="longwidth"
                    :fetch-suggestions="querySearchDriver"
                    @select="handleSelectAssistant"
                    @focus="getViceIndex(index,carIndex,viceIndex)"
                  ></el-autocomplete> 
                </el-form-item>
                <el-form-item label='联系方式'>
                  <el-input  class="date" v-model="viceDriver.driver_mobile"></el-input>
                </el-form-item>
                <span
                  class="iconfont icon-guanbi1 icon-close"
                  @click="deleteViceDriver(index, carIndex,viceIndex)"
                ></span>
            </div>
            <div v-if="carData.car_source != 2" class="addTrip addDriver" @click="addViceDriver(index,carIndex)">添加副班司机</div>
            
            <el-form-item v-if="carData.car_source == 1 || carData.car_source ==undefined" label="现收款">
              <el-input placeholder="请输入现收款"  class="date" v-model="carData.my_collection_money"></el-input>
            </el-form-item>
            
            <el-form-item v-if="carData.car_source == 2" class="inline-item" label="代收款">
              <el-input  class="date" placeholder="请输入代收款" v-model="carData.collection_money"></el-input>
            </el-form-item>
            <el-form-item v-if="carData.car_source == 2" class="inline-item" :rules="missionRules.begin_at_date" label="外援费用">
              <el-input  class="date" placeholder="请输入外援费用" v-model="missionRules.foreign_money"></el-input>
            </el-form-item>
            <el-form-item label="司机备注"  class="remark">
              <el-input placeholder="请输入司机备注" type="textarea" v-model="carData.remark_to_driver"></el-input>
            </el-form-item>
          </el-form-item>
          <div class="addTrip" @click='edit(index)'>添加车辆</div>    
        </div> 
        <el-form-item>
        </el-form-item>

      </div>
     </el-form-item>
    <calendar ref="calendar" @choseTime='choseTime' :cycleShow.sync="cycleShow"></calendar>
    <shuttleBusBox
      :car-style-box-show.sync="carStyleBoxShow"
      :carInfoArr="carInfoArr"
      :date-interval="dateInterval"
      @rtnSelectedList="rtnSelectedList"
      @rtnCarDaysInfo="rtnCarDaysInfo"
    ></shuttleBusBox>
    <shuttleBusSelect
      :selectedCarBoxShow.sync="selectedCarBoxShow"
      :dateInterval="dateInterval"
      :arrangeInfo="arrangeInfo"
      @rtnCarDaysInfo="rtnCarDaysInfo"
    ></shuttleBusSelect>
    <div id="mapContainer2">
      <el-amap ref="maps" :amap-manager="maps.amapManager" vid="amapDemo" :events="maps.events"></el-amap>
    </div>
    <div class="addTrip" @click="addTripContent">添加行程</div>

  </div>
</template>
<script>
import calendar from '@/components/calendar'
import shuttleBusBox from '@/components/shuttleBusBox'
import shuttleBusSelect from '@/components/shuttleBusSelect'
export default {
   props: {
    reqInfo: {
      type: Array,
      value: []
    }
  },
  data(){
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
    var validateDate = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请选择日期'))
      } else if (!this.reqParams[this.boxIndex][this.timeType]) {
        callback(new Error('请选择时间'))
      } else {
        callback()
      }
    }
    return{
      cycleShow:false,
      carStyleBoxShow:false,
      carInfoArr:null,
      dateInterval:null,
      arrangeInfo: null,
      orderType:null,
      selectedCarBoxShow: false,
      showMessage:false,
      shuuleBusData:[{
        show:true,
        driver_cars:[{
          car_number:'',
          driver_name:'',
          driver_mobile:'',
          remark_to_driver:'',
          my_collection_money:'',
          vice_drivers:[]
        }],  
        date_travels:[{
          cycle_date:[],
          cycle_dates_pc:[],
          begin_at_time:'',
          end_at_time:'',
          travels:{
            travel_from_place:'',
            travel_to_place:'',
            travel_content:'',
            travel_pathway:[]
          }
        }]
      }],
      timeType:'',//判断事件类型
      shuttleIndex:null,//班车索引
      shuttleDateTravelIndex:'',//班车趟数索引
      selectDriverName:null,//select选择框副班司机
      dataIndex:null,//行程index
      voiceIndex:null,//司机index
      assinDriverIndex:null,//副班司机index
      emptyTimeArr:[],
      timesArr:[],
      timeCheck:Boolean,
      timedArr:[],
      beginplaceArr:[],
      endPlaceArr:[],
      shuttleIndex:null,
      missionRules: {
        travel_from_place: { required: true, message: '请输入起点', trigger: ['blur', 'change'] },
        travel_to_place: { required: true, message: '请输入终点', trigger: ['blur', 'change'] },
        my_collection_money: { required: true, validator: validateMoney, trigger: ['blur', 'change'] },
        car_number: { required: true, message: '请输入出行车辆', trigger: ['blur', 'change'] },
        begin_at_date: { required: true, validator: validateDate, trigger: ['blur', 'change'] },
        end_at_date: { required: true, validator: validateDate, trigger: ['blur', 'change'] },
        driver_name: { required: true, message: '请输入司机姓名', trigger: ['blur', 'change'] },
        driver_mobile: { required: true, validator: validateMobile, trigger: ['blur', 'change'] },
      },
       maps:
       {
        amapManager: new this.$VueAMap.AMapManager(),
        events: {
          init: o => {
            for (var i = 0; i < this.shuuleBusData.length; i++) {
              for(var j=0;j<this.shuuleBusData[i].date_travels.length;j++){
                this.initAmap("travel_from_place"+i+j,i,j)
                this.initAmap("travel_to_place"+i+j,i,j)
                for(var h=0;h<this.shuuleBusData[i].date_travels[j].travels.travel_pathway.length;h++){
                  this.initAmap("travel_pass"+i+j+h,i,j,h)
                }
              }
            }
          }
        }
      },
    }
  },
  mounted () {
  },
  watch: {
    shuuleBusData:{
      handler(newValue){
         this.$emit('update:shuuleBusData', newValue)
      },
      deep:true
    },
    reqInfo: {
      handler (newVal) {
        this.shuuleBusData = newVal
      },
      deep: true,
      immediate: true
    }
  },
  methods:{
    removeShuttleBus(index){
      this.shuuleBusData.splice(index,1)
    },
    changeValue(index,dateIndex){
      let value = this.shuuleBusData[index].date_travels[dateIndex]['begin_at_time']
     let reg = /^(([0-1][0-9])|([2][0-3]))$/
      if (reg.test(value)) {
        this.shuuleBusData[this.shuttleIndex].date_travels[this.shuttleDateTravelIndex]['begin_at_time']= value + ':'
      }
    },
    changeEndValue(index,dateIndex){
      let value = this.shuuleBusData[index].date_travels[dateIndex]['end_at_time']
     let reg = /^(([0-1][0-9])|([2][0-3]))$/
      if (reg.test(value)) {
        this.shuuleBusData[this.shuttleIndex].date_travels[this.shuttleDateTravelIndex]['end_at_time']= value + ':'
      }
    },
    choseBeginTime(type,index,dateIndex){
      this.timeType= 'type==1'?'begin_at_time':'end_at_time'
      this.shuttleIndex=index
      this.shuttleDateTravelIndex = dateIndex
    },
    choseCycle(index,dateIndex,cycle_dates_pc,date){
      if(cycle_dates_pc==undefined){
        cycle_dates_pc=[]
      }
      this.cycleShow = true
      this.$nextTick(()=>{
        this.$refs.calendar.showToggle(index,dateIndex,cycle_dates_pc,date)
      })
    },
    // 失去焦点
    checkTime(index,dateIndex,type){
      let reg = /^(([0-1]?[0-9])|([2]?[0-3]))(:?)[0-5]([0-9])$/
      let shuttleObj = this.shuuleBusData[index].date_travels[dateIndex]
      var types 
      if(type==1){
          types = 'begin_at_time'
      }else{
        types = 'end_at_time'
      }
      if(!reg.test(shuttleObj[types])){
        this.shuuleBusData[index].date_travels[dateIndex][types]=''
      }
      if(type== 2){
        let date1 =  this.shuuleBusData[index].date_travels[dateIndex]['begin_at_time']
        let date2 = this.shuuleBusData[index].date_travels[dateIndex]['end_at_time'] 
        if(this.CompareDate(date1,date2)){
          this.$message('结束时间不能小于开始时间')
          this.shuuleBusData[index].date_travels[dateIndex]['end_at_time'] =''
        }
      }
    },
    // 选择完日期后点击确定
    choseTime(choseDay,shuttleIndex,dateIndex,checked){
      var timesArr=[]
      for(var i=0;i<choseDay.length;i++){
        for(var j=0;j<choseDay[i].list.length;j++){
          timesArr = timesArr.concat(choseDay[i].list[j].list)
        }
      }
      timesArr = this.unique(timesArr)
      if(checked){
        for(let a=0;a<this.shuuleBusData.length;a++){
          for(let b=0;b<this.shuuleBusData[a].date_travels.length;b++){
            this.shuuleBusData[a].date_travels[b].cycle_date= timesArr
            this.shuuleBusData[a].date_travels[b].cycle_date_pc = choseDay
          }
        }
      }else{
        this.shuuleBusData[shuttleIndex].date_travels[dateIndex].cycle_date= timesArr
      this.shuuleBusData[shuttleIndex].date_travels[dateIndex].cycle_date_pc = choseDay
      }
    },
    // 数组去重
    unique(a) {
      var res = a.filter(function(item, index, array) {
        return array.indexOf(item) === index;
      });
      return res;
    },
    getViceIndex (index,carIndex,viceIndex) {
      this.dataIndex = index
      this.viceIndex = carIndex
      this.assinDriverIndex= viceIndex
    },
    // 添加副班司机
    addViceDriver(index,carIndex){
      this.shuuleBusData[index].driver_cars[carIndex].vice_drivers.push({
        driver_id:'',driver_name:'',driver_mobile:''
      })
    },
    // 删除副班司机
    deleteViceDriver(index, carIndex,viceIndex){
       this.shuuleBusData[index].driver_cars[carIndex].vice_drivers.splice(viceIndex,1)
    },
    // 选择副班司机
    handleSelectAssistant (item) {
      let obj = {
        driver_id: item.id,
        driver_name:item.name,
        driver_mobile: item.mobile
      }
      let carObj = this.shuuleBusData[this.dataIndex].driver_cars[this.viceIndex].vice_drivers[this.assinDriverIndex]
      let newCarObj = Object.assign({}, carObj, obj)
      this.$set(this.shuuleBusData[this.dataIndex].driver_cars[this.viceIndex].vice_drivers, this.assinDriverIndex, newCarObj)
    },
    // 选择司机
    handleSelectDriver (item) {
      let obj = {
        driver_id: item.id,
        driver_mobile: item.mobile,
        driver_name:item.name
      }
      let carObj = this.shuuleBusData[this.dataIndex].driver_cars[this.viceIndex]
      let newCarObj = Object.assign({}, carObj, obj)
      this.$set(this.shuuleBusData[this.dataIndex].driver_cars, this.viceIndex, newCarObj)
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
    initAmap(ele,index,dateIndex,placeIndex){
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
          let place = null, lng = null, lat = null
            place = e.poi.name
            lng = e.poi.location.lng
            lat = e.poi.location.lat
          if (placeIndex=== undefined) {
              let obj={}
              let sign = ele.includes('from') ? 'from' : 'to'
                obj[`travel_${sign}_place`] = place
                obj[`travel_${sign}_lat`] = lat
                obj[`travel_${sign}_lng`] = lng
                if(that.shuuleBusData[index].date_travels[dateIndex].travels){
                  that.shuuleBusData[index].date_travels[dateIndex].travels = Object.assign({}, that.shuuleBusData[index].date_travels[dateIndex].travels, obj)
                }else{
                  that.shuuleBusData[index].date_travels.push(travel) 
                }
          }else{
              if(ele.includes('travel_pass')){
                let lineWay={}
                lineWay[`travel_from_place`] = place
                lineWay[`travel_from_lat`] = lat
                lineWay[`travel_from_lng`] = lng
                that.$set(that.shuuleBusData[index].date_travels[dateIndex].travels.travel_pathway,placeIndex,lineWay)
              }
          }
        })
      })
    },
    // 添加途经点
    addPasway(index,dateIndex){
        this.shuuleBusData[index].date_travels[dateIndex].travels.travel_pathway.push({

        })
        this.$nextTick(()=>{
          for(var g=0;g<this.shuuleBusData[index].date_travels[dateIndex].travels.travel_pathway.length;g++){
            this.initAmap('travel_pass'+index+dateIndex+g,index,dateIndex,g)
        }
        })
      
    },
    deletePathway(index,dateIndex,placeIndex){
      this.shuuleBusData[index].date_travels[dateIndex].travels.travel_pathway.splice(placeIndex,1)
    },
    // 添加趟数
    addTrip(index){
      this.shuuleBusData[index].date_travels.push({
          cycle_date:[],
          cycle_dates_pc:[],
          begin_at_time:'',
          end_at_time:'',
          travels:{
            travel_from_place:'',
            travel_to_place:'',
            travel_pathway:[],
            travel_content:''
          }
      })
      this.$nextTick(()=>{
        for (var k = 0; k < this.shuuleBusData.length; k++) {
              for(var z=0;z<this.shuuleBusData[k].date_travels.length;z++){
                this.initAmap("travel_from_place"+k+z,k,z)
                this.initAmap("travel_to_place"+k+z,k,z)
            }
        }
      })
    },
    // 删除趟数
    deletePaw(index,dateIndex){
      this.shuuleBusData[index].date_travels.splice(dateIndex,1)
    },
    addCar(index){
      this.shuuleBusData[index].driver_cars.push({})
    },
    // 删除车辆
    deleteCar(index,carIndex){
        this.shuuleBusData[index].driver_cars.splice(carIndex,1)
    },
    addTripContent(){
      // this.showMessage =true
      for(let j=0;j<this.shuuleBusData.length;j++){
        this.shuuleBusData[j].show =false
      }
      this.shuuleBusData.push({
        show:true,
        driver_cars:[
          {
          car_number:'',
          driver_name:'',
          driver_mobile:'',
          remark_to_driver:'',
          my_collection_money:'',
          vice_drivers:[{
            driver_name:'',
            driver_mobile:''
          }]
        }
        ],
        date_travels:[{
          cycle_date:[],
          cycle_dates_pc:[],
          begin_at_time:'',
          end_at_time:'',
          travels:{
            travel_from_place:'',
            travel_to_place:'',
            travel_pathway:[],
            travel_content:''
          }
        }]
      })
      this.$nextTick(()=>{
        for (var i = 0; i < this.shuuleBusData.length; i++) {
          for(var j=0;j<this.shuuleBusData[i].date_travels.length;j++){
              this.initAmap("travel_from_place"+i+j,i,j)
              this.initAmap("travel_to_place"+i+j,i,j)
            for(var h=0;h<this.shuuleBusData[i].date_travels[j].travels.travel_pathway.length;h++){
              this.initAmap("travel_pass"+i+j+h,i,j,h)
            }
          }
        }
      })
    },
    edit(index,carIndex,orderType){
        let shuttleObj =  this.shuuleBusData[index].date_travels
        this.shuttleIndex = index
        var emptyTimeArr=[]
        var beginplaceArr=[]
        var endPlaceArr=[]
        var beginTimeArr=[]
        var endTimeArr=[]
        for(let a=0;a<shuttleObj.length;a++){ 
          if(!shuttleObj[a].cycle_date.length){
            emptyTimeArr.push(a+1)
          }
          if(!shuttleObj[a].travels.travel_from_place){
            beginplaceArr.push(a+1)
          }
          if(!shuttleObj[a].travels.travel_to_place){
            endPlaceArr.push(a+1)
          }
          if(!shuttleObj[a].begin_at_time){
            beginTimeArr.push(a+1)
          }
          if(!shuttleObj[a].end_at_time){
            endTimeArr.push(a+1)
          }
        }
        if(beginTimeArr.length){
          this.$message({
              type:'error',
              message:'第'+beginTimeArr.join(',')+'趟开始时间为空,请填写开始时间',
              duration:2000
            })
        }else if(endTimeArr.length){
          this.$message({
              type:'error',
              message:'第'+endTimeArr.join(',')+'趟结束时间为空,请填写结束时间',
              duration:2000
            })
        }else
        if(emptyTimeArr.length){
          this.$message({
              type:'error',
              message:'第'+emptyTimeArr.join(',')+'趟运行周期为空,请选择运行周期',
              duration:2000
            })
        }else if(beginplaceArr.length){
          this.$message({
              type:'error',
              message:'第'+beginplaceArr.join(',')+'趟起点为空,请填写起点',
              duration:2000
            })
        }else if(endPlaceArr.length){
          this.$message({
              type:'error',
              message:'第'+endPlaceArr.join(',')+'趟终点为空,请填写终点',
              duration:2000
            })
          endPlaceArr=[]
        }else{
          for(var j=0;j<this.shuuleBusData.length;j++){
            for(var z=0;z<this.shuuleBusData[j].date_travels.length;z++){
              if(this.shuuleBusData[j].date_travels.length==1){
                  this.timedArr=this.shuuleBusData[j].date_travels[z].cycle_date
              }else{
                this.timedArr.push(this.shuuleBusData[j].date_travels[z].cycle_date)
              }         
            }
            
          }
          for(let k =0;k<this.timedArr.length;k++){
            if(this.timedArr[k]==null){
              this.timedArr.splice(k,1)
            }
          }
          this.timedArr =JSON.stringify(this.timedArr)
          this.dateInterval ={start_time: '', end_time: '',cycle_date:this.timedArr}
          let carsArr =JSON.stringify(this.shuuleBusData[index].driver_cars) 

          this.carInfoArr =JSON.parse(carsArr) 
          if(this.carInfoArr.length){
            for(let j=0;j<this.carInfoArr.length;j++){
              if(this.carInfoArr[j].driver_id==undefined){
                this.carInfoArr.splice(j,1)
              }
            }
          }else{
            this.carInfoArr=[]
          }
            
          this.carStyleBoxShow = true
          this.orderType=3
          this.timedArr=[]
        }
    },

    CompareDate(t1,t2){
      var date = new Date();
      var a = t1.split(":");
      var b = t2.split(":");
      return date.setHours(a[0],a[1]) > date.setHours(b[0],b[1]);
    },
     rtnSelectedList (obj) {
      this.selectedCarBoxShow = true
      this.arrangeInfo = obj
    },
    rtnCarDaysInfo (res) {
      // this.carInfo = res
      let arr = []
      res.forEach(ele => {
        if(ele.vice_drivers==undefined){
          ele.vice_drivers = []
        }
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
          vice_drivers: ele.vice_drivers
        }
        arr.push(obj)
      });
     
      this.shuuleBusData[this.shuttleIndex].driver_cars = arr
      this.carStyleBoxShow = false
      this.selectedCarBoxShow = false
    },
    showShuttle(index){
      this.shuuleBusData[index].show =true
      this.$nextTick(()=>{
        for (var i = 0; i < this.shuuleBusData.length; i++) {
          for(var j=0;j<this.shuuleBusData[i].date_travels.length;j++){
              this.initAmap("travel_from_place"+i+j,i,j)
              this.initAmap("travel_to_place"+i+j,i,j)
            for(var h=0;h<this.shuuleBusData[i].date_travels[j].travels.travel_pathway.length;h++){
              this.initAmap("travel_pass"+i+j+h,i,j,h)
            }
          }
        }
      })
    },
  },
  components:{
    calendar,
    shuttleBusBox,
    shuttleBusSelect,
  }
}
</script>
<style lang='less'>
.shuttleBus .el-form-item .car-form .date .el-input__inner{
    text-align:left;
    padding:0 10px;
  }
  .remark {
    .el-textarea {
      width: 482px;
      .el-textarea__inner {
        height: 49px;
      }
    }
  }
</style>
<style lang='less' scoped>
.shuttleBus{
  .shuttleDriver{
    margin-left:120px;
   
    .name{
      margin-left:45px;
    }
    .mobile{
      margin-left:37px;
    }
  }
   .show{
      width:68px;
      height: 32px;
      display: inline-block;
      margin-left:112px;
      line-height:32px;
      text-align:center;
      background:rgba(13,181,146,1);
      border-radius:4px;
      color:#fff;
      cursor: pointer;
    }
  #shuttleData{
    position: absolute;
    top:-18px;
    right:-5px;
  }
  .out-box {
    min-height:93px;
    width: 771px;
    text-align:left;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border: 1px solid #dcdfe6;
    border-radius: 4px;
    position: relative;
    padding-top: 14px;
    .el-form-item{
      display:inline-block;
      .date{
        width:195px;
      }
      .el-form-item__content{
        position: relative;
        left:0px;
      }
    }
   
  }
  .cycle{
    width:70px;
    height:32px;
    display:inline-block;
    margin-top:2px;
    position: absolute;
    background:rgba(13,181,146,1);
    border-radius:4px;
    text-align: center;
    line-height: 32px;
    color: #FFFFFF;
    cursor:pointer;
  }
  .addPasway{
    width:70px;
    height:33px;
    position: absolute;
    line-height:33px;
    text-align:center;
    border-radius:4px;
    background-color:#0db592;
    color:#FFFFFF;
    display:inline-block;
    cursor:pointer;
  }
  .passWayLine{
    width:400px;
    .pathWay{
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
  }
  
  .deletePaw{
    width:103px;
    height:33px;
    margin-left:90px;
    text-align:center;
    border:1px solid rgba(255,114,0,1);
    border-radius:4px;
    font-size:14px;
    color:#FF7200;
    line-height:33px;
    cursor:pointer;
  }
  .addTrip{
    width:103px;
    height:33px;
    margin-left:200px;
    margin-top:10px;
    background:rgba(13,181,146,1);
    border-radius:4px;
    text-align:center;
    line-height:33px;
    color:rgba(255,255,255,1);
    cursor:pointer;
  }
  .addDriver{
    margin-left:90px;
  }
  .outcar{
    padding: 4px 10px;
    line-height: 1;
    color: #fff;
    background-color: #ffb320;
    position: absolute;
    left:140px;
    top:10px;
    border-radius:4px;
  }
}

</style>

