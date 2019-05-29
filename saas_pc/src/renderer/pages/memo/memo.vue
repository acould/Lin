<template>
  <div class="memo-container">
    <div class='main-header'>
      <h3 class="single-tt">待办事项</h3>
    </div>
    <div class="main-content">
      <div class="top-box">
        <el-radio-group class="radio-wrap" v-model="plan_type">
          <el-radio-button label="0">全部</el-radio-button>
          <el-radio-button label="1">未安排</el-radio-button>
          <el-radio-button label="2">已安排</el-radio-button>
        </el-radio-group>
        <!-- <router-link class="btn" :to="{name: 'addMission' }">添加任务</router-link> -->
        <a href="javascript:void(0);" class="btn-voice" @click="openVoiceBox">
          <span class="iconfont icon-yuyin"></span>
          语音输入
        </a>
      </div>
      <div class='mark-form'>
        <table style="width:100%;">
          <tr class="head_title">
            <th class="date">
              日期
            </th>
            <th class="user">
              客户
            </th>
            <th class="cars">
              车辆
            </th>
            <th class="carNumber">
              车牌号
            </th> 
            <th class="beginTime">
              出发日期
              <i class="icon-time  el-icon-caret-bottom" v-show="listorder == 1" @click="sortList(1)"></i>
              <i class="icon-time  el-icon-caret-top" v-show="listorder == 2" @click="sortList(2)"></i>
            </th>
            <th class="travel">
              行程
            </th>
            <th class="travel">
              集合地点
            </th>
            <th class="price">
              价格
            </th>
            <th class="remark">
              备注
            </th>
            <th class="arrange">
              安排车调
            </th>
            <th class="handle">操作
              <span class="addBtn" @click="showBtn">
                <span class="iconfont icon-close iconfont-close"></span>
              </span>
            </th>
          </tr>
          <tr v-show='showForm'>
            <th class="date">{{sameDay}}</th>
            <th class="user">
              <el-autocomplete v-model="customer" value-key="company" :fetch-suggestions="querySearchAsync" placeholder="请输入内容" @select="handleSelect"></el-autocomplete>
            </th>
            <th class="cars carsborder" v-model='car_infos' @click='choseCar(999999)'>
              {{cars}}
            </th>
            <!-- 车牌号 -->
            <th class="carNumber">
            
            </th>
            <th class="beginTime">
              <el-date-picker v-model="travel_begin_at" type="date" placeholder="选择日期" :picker-options="pickerOptions1" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd">
              </el-date-picker>
            </th>
            <th class="travel">
              <el-input type='text' placeholder="请填写行程信息" v-model="travel_content"> </el-input>
            </th>
             <th class="price"></th> 
            <th class="price">
              <el-input type='number' placeholder="请填写价格" v-model.number="money"> </el-input>
            </th>
            <th class="remark">
              <el-input type='text' placeholder="请填写备注" v-model="remark"> </el-input>
            </th>
            <th  class="arrange">
               <el-autocomplete v-model="arrangeCar" value-key="name" :fetch-suggestions="arrangeCb"  placeholder="请输入内容" @select="choseSelect"></el-autocomplete>
            </th>
            <th>
              <div class="submitBtn" @click='submitTable()'>保存</div>
            </th>
          </tr>
          <tr class='tableList' v-for="(item,index) in recordList" :key="index">
            <td class='firstDate'>
              <span v-show='item.remind_not_plan==1' class='redDot'></span>
              <span>{{item.created_at}}</span>
            </td>
            <td>
              <span v-show='!(index == editIndex)'>{{item.customer}}</span>
              <el-autocomplete v-model="item.customer" v-show='index == editIndex' value-key="company" :fetch-suggestions="querySearchAsync" @select="handleTableSelect" placeholder="请输入内容"></el-autocomplete>
              <!-- <el-input v-show='index == editIndex'  type='text' v-model="item.customer" > </el-input> -->
            </td>
            <td>
              <span class="cars" v-show='!(index == editIndex)'>{{item.carInfos}}</span>
              <span class='carsBlock' v-show='index == editIndex' @click="choseCar(index)">{{item.carInfos}}</span>
              <!-- <el-input  v-show='index == editIndex'  @click="choseCar(index)" type='text' v-model="item.carInfos" > </el-input> -->
            </td>
            <!-- 车牌号 -->
            <td>
              <div class='boxed'>
              {{item.car_info[0].driver_name}}
              <div class="iconfont-wrap">
                  <el-popover popper-class="tel-tip" placement="top-start" width="150" trigger="hover">
                    {{item.car_info[0].driver_mobile}}
                    <span  v-show='item.car_info[0].driver_name!=""' slot="reference" class="iconfont icon-bohao iconfont-tel"></span>
                  </el-popover>
              </div>
              <div class="iconfont-wrap">
                  <el-popover popper-class="table-tip" placement="right" width="320" trigger="click">
                    <div class="line-wrap">
                      <div class="line" v-for="(info, index) in item.car_info" :key="index">
                        <span class="name">{{info.driver_name}}</span>
                        <span class="mobile">{{info.driver_mobile}}</span>
                        <span class="car-number">{{info.car_number}}</span>
                        <span v-if="info.car_source == 1" class="car-source out-car">自有</span>
                        <span v-if="info.car_source == 2" class="car-source out-car">外援</span>
                      </div>
                    </div>  
                    <span v-show='item.car_info[0].driver_name!=""' slot="reference" class="iconfont icon-jiantou2 iconfont-arrow"></span>
                  </el-popover>
                </div>
                <p class="line">{{item.car_info[0].car_number}}</p>
                <span v-if="item.car_info[0].car_source == 1" class="car-source out-car">自有</span>
                 <span v-if="item.car_info[0].car_source == 2" class="car-source out-car">外援</span>
              </div>
            </td>
            <td>
              <span v-show='!(index == editIndex)'>{{item.travel_begin_at}}</span>
              <el-date-picker v-show='index == editIndex' v-model="item.travel_begin_at" type="date" :picker-options="pickerOptions1" placeholder="选择日期">
              </el-date-picker>
            </td>
            <td class='audios'>
              <span v-show='!(index == editIndex)'>{{item.travel_content}}</span>
              <div class="audio-wrap" :key="item.backlog_id" @click="togglePlay(index)" v-show='!(index == editIndex)&&item.voice_url'>
                <img :ref="'play'" :src="audioPlaySrc">
                <img class="hidden" :ref="'pause'" :src="audioPauseSrc">
                <audio controls :ref="'audio'" class="audio">
                  <source :src="item.voice_url">
                </audio>
              </div>
              <el-input v-show='index == editIndex' type='text' v-model="item.travel_content"> </el-input>
            </td>
            <td>
             <span>{{item.travel_from_place}}</span> 
            </td>
            <td>
              <span v-show='!(index == editIndex)'>{{item.money}}</span>
              <el-input v-show='index == editIndex' type='number' v-model="item.money"> </el-input>
            </td>
            <td>
              <span v-show='!(index == editIndex)'>{{item.remark}}</span>
              <el-input v-show='index == editIndex' type='text' v-model="item.remark"> </el-input>
            </td>
            <td>
              <span v-show='!(index == editIndex)'>{{item.saas_yardman_name}}</span>
               <el-autocomplete v-show='index == editIndex' v-model="item.saas_yardman_name" value-key="name" :fetch-suggestions="arrangeCb" placeholder="请安排计调" @select="choseSelected"></el-autocomplete>
            </td> 
            <td>
              <div class='readyBtn'>
                <span v-show='!(index==editIndex)'>
                  <span v-show='item.plan_type==1' class="el-dropdown-link" @click='arrenge(index)'>
                    待安排
                  </span>
                </span>
                <span v-show='index==editIndex' class="el-dropdown-link" @click='submit(item.backlog_id)'>
                  保存
                </span>
                <div class='dropFa' v-show='btnListShow'>
                  <div class='drop' v-show='index==key'>
                    <span>
                      <span>定时提醒</span>
                      <el-date-picker class="redwarm" popper-class="redwarm-select" v-model="item.remind_time_value" value-format="yyyy-MM-dd HH:mm" @change='setTime(item.backlog_id,index)' :picker-options="pickerOptions1" type="datetime">
                      </el-date-picker>
                    </span>
                    <span @click='arrangeClass(item.backlog_id)'>排班</span>
                    <span @click='change(index)'>修改</span>
                    <span @click='deleteD(item.backlog_id)'>删除</span>
                  </div>
                </div>
              </div>
              <div v-show='item.plan_type==2'>
                <span class="iconfont icon-gou5"></span>
                <span class='ready' @click='jumpDetaisl(item.saas_order_id)'>已安排</span>
                <span class='change' @click='changeArr(index)'>修改</span>
                <div class="changeShow" v-show='showFalse' style='position:relative;'>
                  <div class='dropDown' v-show='index == readyKey'>
                    <span @click='changeReady(item.saas_order_id)'>修改</span>
                    <span @click='deleteD(item.backlog_id)'>删除</span>
                  </div>
                </div>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <el-dialog :visible.sync="dateShow" title='请选择日期' center width='30%'>
      <el-date-picker style='margin:auto; display:block; width:300px;' v-model="dateValue" type="datetime" :clearable='true' value-format="yyyy-MM-dd HH:mm" :picker-options="pickerOptions1" placeholder="选择日期时间">
      </el-date-picker>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dateShow = false">取 消</el-button>
        <el-button type="primary" @click="dateShow = false">确 定</el-button>
      </span>
    </el-dialog>
    <div class="pages-wrap">
      <el-pagination :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
    </div>
    <selectCar ref='selectCar' :selectedCarShow.sync="selectedCarShow" @closeCar='closeCar' :carContent='carContent' :carIndex='carIndex' :carInfo="carInfo"></selectCar>
    <audio-box :audio-box-show.sync="audioBoxShow" />
    <BacklogDateBox :backlog-date-box-show.sync="backlogDateBoxShow" @rtnBacklogSetOption="rtnBacklogSetOption" />
  </div>
</template>
<script>

import audioPlaySrc from '@/assets/images/voice.png'
import audioPauseSrc from '@/assets/images/voiceOpen.png'
import AudioBox from '@/components/AudioBox'
import selectCar from '@/components/selectCar'
import BacklogDateBox from '@/components/BacklogDateBox'
import { mapActions, mapState} from 'vuex'
export default {
  name: 'memo',
  data () {
    return {
      audioBoxShow: false,
      modifyBacklogShow: false,
      editIndex: null, //'选择备忘录列表的索引'
      plan_type: '0',
      previousCon: '', //记事本之前填写的信息
      page: 1,
      maxPage: null,
      content: '',
      audioPlaySrc: audioPlaySrc,
      audioPauseSrc: audioPauseSrc,
      recordList: [{
        car_info:[{}]
      }],
      cars: '请选择车辆',//车辆文本信息
      dataTime: '',
      customer: '',
      customer_id: '',
      tableCustromerId: '', // 表格中修改的customerId
      tableModifyRowIndex: null,
      car_infos: '',
      travel_begin_at: '',
      travel_content: '',
      remark: '',
      money: '',
      sameDay: '',//当前时间
      pickerOptions1: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7;
        }
      },
      showForm: false,
      carInfo: null,//选择车辆信息,
      carIndex: null,
      selectedCarShow: false,
      key: 9999999999999,
      edit: false,
      editIndex: 999999999999999,
      carContent: '',
      btnListShow: false,
      showFalse: false,
      readyKey: 888888,
      value1: '',
      dateShow: false,
      dateValue: '',
      backlogDateBoxShow: false, // 控制保存时设置定时提醒的弹出框的显示状态
      listorder: 1, // 默认按照发单时间 //1:降序 2:升序
      arrangeCar:'',
      arrangeCar_id:'',
      arrangeCarId:'',
      carsArr:[]
    }
  },
  components: {
    AudioBox,
    selectCar,
    BacklogDateBox
  },
  watch: {
    plan_type () {
      if (this.page !== 1) {
        this.page = 1
      }
      this.$nextTick(() => {
        this.getRecordList()
      })
    },
    arrangeCar(newVal,oldVal){
      if (!newVal || (oldVal && newVal)) {
        this.arrangeCarId = null
      }
    },
    recordList () {
      this.watchPlayProcess()
    },
    customer (newVal, oldVal) {
      if (!newVal || (oldVal && newVal)) {
        this.customer_id = null
      }
    },
    tableCustromerVal (newVal, oldVal) {
      if (!newVal || (oldVal && newVal)) {
        this.tableCustromerId = null
      }
    },
    tableCompoent(newVal,oldVal){
      if (!newVal || (oldVal && newVal)) {
        this.arrangeCarId=null
      }
    }
  },
  computed: {
    tableCustromerVal () {
      let tableModifyRowIndex = this.tableModifyRowIndex
      if (tableModifyRowIndex || tableModifyRowIndex == 0) {
        return this.recordList[tableModifyRowIndex].customer
      }
      return ''
    },
    tableCompoent () {
      let tableModifyRowIndex = this.tableModifyRowIndex
      if (tableModifyRowIndex || tableModifyRowIndex == 0) {
        return this.recordList[tableModifyRowIndex].saas_yardman_name
      }
      return ''
    }
  },
  created () {
    this.getRecordList()
    this.getDay();
    // this.getTableList()
  },
  mounted () {
    this.getMessageCondition()
  },
  methods: {
    ...mapActions([
      "getMessageCondition"
    ]),
    querySearchAsync (queryString, cb) {
      let params = {
        keywords: queryString
      }
      this.$axios.get(this.$httpUrl.customerListPC, { params: params }).then(res => {
        if (res) {
          cb(res.list)
        }
      })
    },
    
    handleSelect (item, i) {
      this.customer = item.company
      this.customer_id = item.customer_id
      // this.missionForm.customer_name = item.customer_name
      // this.missionForm.customer_mobile = item.customer_mobile
    },
    handleTableSelect (item, i) {
      this.tableCustromerId = item.customer_id
    },
    // 获取当前时间
    getDay () {
      let myDate = new Date();
      this.sameDay = myDate.getFullYear() + '年' + (myDate.getMonth() + 1) + '月' + myDate.getDate() + '日' + myDate.getHours() + ':' + myDate.getMinutes()
    },
    //选择车辆
    choseCar (index) {
      this.selectedCarShow = !this.selectedCarShow
      this.carIndex = index
      if (index != '999999') {
        this.carInfo = this.recordList[index].carArr
        this.carContent = this.recordList[index].carInfos
      } else {
        this.carInfo = this.carsArr
        if(this.cars=='请选择车辆'){
          this.carContent=''
        }else{
          this.carContent = this.cars 
        }
        
      }
    },
    closeCar (carsArr, carText, carIndexs) {
      // console.log(carsArr)
      // console.log(carText)
      // console.log(carIndexs)
      this.selectedCarShow = false
      this.cars = carText
      this.carsArr=carsArr
      for (var i = 0; i < carsArr.length; i++) {
        if (carsArr[i].image) {
          delete (carsArr[i].image)
        }
      }
      this.car_infos = JSON.stringify(carsArr)
      if (carIndexs != 999999) {
        this.recordList[carIndexs].carInfos = this.cars
        this.recordList[carIndexs].car_infos = this.car_infos
      }

    },
    //增加待办事项信息
    submitTable () {
      if (this.travel_begin_at || this.travel_content || this.remark || this.money || this.car_infos || this.customer) {
        this.backlogDateBoxShow = true
      } else {
        this.$message.error('待办事项未填写内容，请确认后提交')
      }
    },
    rtnBacklogSetOption (optionObj) {
      let params = {
        travel_begin_at: this.travel_begin_at,
        customer: this.customer,
        car_infos: this.car_infos,
        travel_content: this.travel_content,
        remark: this.remark,
        money: this.money,
        content: '',
        customer_id: this.customer_id,
        saas_yardman_id:this.arrangeCar_id,
        saas_yardman_name:this.arrangeCar
      }
      if(params.saas_yardman_name==''){
        params.saas_yardman_id=''
      }
      if (optionObj.type === 2) {
        params.remind_time = optionObj.date
      }
      this.$axios.post(this.$httpUrl.backlogSave, params).then(res => {
        if (res) {
          this.getRecordList()
          this.showForm = false
          this.travel_begin_at = ''
          this.customer = ''
          this.car_infos = ''
          this.customer_id = null
          this.travel_content = ''
          this.remark = ''
          this.money = ''
          this.cars = '请选择车辆'
          this.arrangeCar_id=''
          this.arrangeCar=''
          this.$message({
            message: '保存成功,订单出发前48小时提醒！',
            type: 'success'
          });
        }
      })
    },
    openVoiceBox () {
      this.audioBoxShow = true
    },
    showBtn () {
      this.showForm = !this.showForm
      this.btnListShow = false
      this.showFalse = false

    },
    sortList (type) {
      this.listorder = type === 1 ? 2 : 1
      this.getRecordList(type)
    },
    getRecordList (sort) {
      let params = {
        page: this.page,
        plan_type: this.plan_type
      }
      if (sort) params.sort = sort
      this.$axios.get(this.$httpUrl.backlogList, { params: params }).then(res => {
        if (res) {
         
          this.maxPage = res.pages
          this.recordList = res.list
          for(var z=0;z<this.recordList.length;z++){
            if(this.recordList[z].car_info==''){
              let obj={
                driver_name:'',driver_mobile:'',car_number:'',car_source:'',car_type:''
              }
                this.recordList[z].car_info.push(obj)
            }
          }
          for (var i = 0; i < this.recordList.length; i++) {
            if (this.recordList[i].remind_time_value) {
              this.recordList[i].remind_time = this.recordList[i].remind_time_value
            }
            if (this.recordList[i].car_infos) {
              var carArr = JSON.parse(this.recordList[i].car_infos)
              this.recordList[i].carArr = carArr
              var car = ''
              for (var j = 0; j < carArr.length; j++) {
                car = carArr[j].seat_num + '座*' + carArr[j].car_num + ' ' + car

              }
              this.recordList[i].carInfos = car
            } else {
              this.recordList[i].carInfos = ''
            }
          }
          //  console.log(res)
        }
      })
    },
    arrenge (index) {
      this.showForm = false
      this.key = index
      this.btnListShow = !this.btnListShow
      this.showFalse = false
    },
    change (index) {
      this.tableModifyRowIndex = index // 表格列表索引
      this.tableCustromerId = this.recordList[index].customer_id
      this.key = index
      this.editIndex = index
      this.btnListShow = false
      this.arrangeCarId=this.recordList[index].saas_yardman_id
    },
    //修改表单
    submit (id) {
      this.btnListShow = false
      let idObj = { backlog_id: id }
      delete (this.recordList[this.key].plan_type)
      this.recordList[this.key].customer_id = this.tableCustromerId
      if (this.recordList[this.key].remind_time == "定时提醒") {
        this.recordList[this.key].remind_time = ''
      }
      this.recordList[this.key].saas_yardman_id = this.arrangeCarId
      let params = Object.assign(this.recordList[this.key], idObj)
      this.$axios.post(this.$httpUrl.backlogSave, params).then(res => {
        if (res) {
          this.editIndex = 999999999999999
          this.getRecordList()
          this.$message(res.message)
          this.cars = '请选择车辆'
        }
      })
    },
    setTime (id, index) {
      if (this.recordList[index].remind_time_value != '') {
        let params = {
          backlog_id: id,
          remind_time: this.recordList[index].remind_time_value
        }
        this.$axios.post(this.$httpUrl.backlogSave, params).then(res => {
          if (res) {
            this.$message('定时提醒设置成功')
            this.btnListShow = false
            this.getRecordList()
            this.getMessageCondition()
          }
        })
      }

    },
    //删除代办事项
    deleteD (id) {
      this.btnListShow = false
      let params = {
        backlog_id: id
      }
      this.$axios.get(this.$httpUrl.backlogDelete, { params: params }).then(res => {
        if (res) {
          this.$message(res.message);
          this.getRecordList()
        }
      })
    },
    //排班
    arrangeClass (id) {
      this.$router.push({ name: 'addMission', query: { backlog_id: id } })
    },
    jumpDetaisl (id) {
      this.$router.push({ name: 'accountDetails', query: { saas_order_id: id } })
    },
    changeArr (index) {
      this.readyKey = index
      this.showFalse = !this.showFalse
    },
    changeReady (id) {
      this.$router.push({ name: 'addMission', query: { order_id: id } })
    },
    togglePlay (index) {
      let pauseDom = this.$refs.pause[index]  //播放图片
      let playDom = this.$refs.play[index]   //暂停图片
      let audioDom = this.$refs.audio[index] // 音频dom
      let audioDomArr = this.$refs.audio
      const util = this.$util
      let lastIndex = this.lastIndex
      if ((lastIndex === 0 || lastIndex) && lastIndex != index) {
        this.stopPlay(lastIndex)
      }
      // 如果存在hidden,则还未开始播放
      if (util.hasClass(pauseDom, 'hidden')) {
        util.addClass(playDom, 'hidden')
        util.removeClass(pauseDom, 'hidden')
        audioDom.play()
      } else {
        util.addClass(pauseDom, 'hidden')
        util.removeClass(playDom, 'hidden')
        audioDom.pause()
      }
      this.lastIndex = index
    },
    stopPlay () {
      let lastIndex = this.lastIndex
      let pauseDom = this.$refs.pause[lastIndex]
      let playDom = this.$refs.play[lastIndex]
      let audioDom = this.$refs.audio[lastIndex]
      this.$util.addClass(pauseDom, 'hidden')
      this.$util.removeClass(playDom, 'hidden')
      audioDom.currentTime = 0;
      audioDom.pause()
    },
    watchPlayProcess () {
      this.$nextTick(() => {
        let audioDomArr = this.$refs.audio
        audioDomArr.forEach((ele, index) => {
          ele.addEventListener("ended", () => {
            let pauseDom = this.$refs.pause[index]
            let playDom = this.$refs.play[index]
            this.$util.addClass(pauseDom, 'hidden')
            this.$util.removeClass(playDom, 'hidden')
            this.lastIndex = null
          })
        })
      })
    },
    toggleArrange (info) {
      info.plan_type = info.plan_type === 1 ? 2 : 1
      this.hanlderBacklog(info, 1)
    },
    //处理备忘录，请求接口 isArrageHanlder 1 安排 2 内容 3  时间
    hanlderBacklog (info, hanlderStatus) {
      let params;
      if (info.backlog_id) {
        params = Object.assign({}, info)
        this.$delete(params, 'created_at')
        if (hanlderStatus == 1 || hanlderStatus == 2) {
          // 处理控制是否已安排
          this.$delete(params, 'remind_time')
          this.$delete(params, 'is_remind')
        } else {
          // 处理提醒时间
          this.$delete(params, 'plan_type')
          this.$delete(params, 'remind_time_value')
        }
      } else {
        params = { content: this.content }
      }
      this.$axios.post(this.$httpUrl.backlogSave, params).then(res => {
        if (res) {
          //修改备忘录
          let tipText = ''
          if (info.backlog_id) {
            // if (hanlderStatus) {
            //   this.getRecordList()
            // } else {
            //   // info.remind_time = '定时提醒'
            //   this.getRecordList()
            //   // this.hanlderBeforeRefreshData(info)
            // }
            this.getRecordList()
            tipText = '修改成功'
          } else {
            //增加备忘录
            tipText = '增加成功'
            this.page = 1
            this.content = ''
            this.getRecordList()
          }
          this.$message({
            message: tipText,
            type: 'success'
          })
        }
      })
    },
    clearEditBacklog () {
      this.content = ''
    },
    addBacklog () {
      if (!this.content) {
        this.$message({
          message: '内容不能为空',
          type: 'error'
        })
        return
      }
      this.hanlderBacklog(this.content)
    },
    changeDate (val) {
      if (this.$util.compareDate2(val)) {
        this.$message({
          message: '定时提醒时间不能小于当前时间',
          type: 'error'
        })
        return
      }
      let info = Object.assign({}, this.recordList[this.timeIndex])
      info.remind_time = val
      this.hanlderBacklog(info)
    },
    selectTimeIndex (i) {
      this.timeIndex = i
    },
    deleteBacklog (id) {
      this.$confirm('是否确认删除', {
        type: 'warning'
      }).then(() => {
        let paramsWrap = { params: { backlog_id: id } }
        this.$axios.get(this.$httpUrl.backlogDelete, paramsWrap).then(res => {
          if (res) {
            this.getRecordList()
          }
        })
      }).catch(() => {

      })
    },
    // 列表翻页
    changePage (page) {
      this.page = page
      this.getRecordList()
    },
    //计调列表
    //安排计调
    choseSelect(item,i){
      this.arrangeCar = item.name
      this.arrangeCar_id = item.yardman_id
    },
    arrangeCb(queryString, cb){
      let params = {
        keywords:queryString,
        // paginate: 10000
      }
      this.$axios.get(this.$httpUrl.fleetYardmanList, { params: params }).then(res => {
        if (res != false) {
          cb(res.list)
        }
      })
    },
     choseSelected (item, i) {
      this.arrangeCarId = item.yardman_id
    },
  },
}
</script>
<style lang ="less">
/*input number去掉框上下箭头*/
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none !important;
}
input[type="number"] {
  -moz-appearance: textfield;
}
/*时间日期选择器去掉此刻按钮*/

.redwarm-select,
.redwarm-select2{
  .has-seconds {
    .el-time-spinner__wrapper {
      &:last-child {
        display: none;
      }
    }
  }
   .el-picker-panel__footer {
    .el-button--text {
      span {
        display: none !important;
      }
    }
  }
}
.carsborder{
  border:1px solid #dcdfe6;
}
.memo-container {
  .audios {
    position: relative;
    .audio-wrap {
      cursor: pointer;
      display: inline-block;
      vertical-align: middle;
      /* position: absolute; */
      /* width: 100%;
        left: 0;
        bottom:0px; */
      img {
        display: inline-block;
        width: 18px;
        height: 18px;
        margin: 0 auto;
        &.hidden {
          display: none;
        }
      }
      .audio {
        position: absolute;
        left: 0;
        top: 0;
        width: 0;
        height: 0;
        opacity: 0;
      }
    }
  }

  min-height: 100%;
  background-color: #f5f5f5;
  .top-box {
    height: 54px;
    padding: 11px 74px 11px 34px;
    box-sizing: border-box;
    background-color: #fff;
    margin: 12px 0;
    overflow: hidden;
    .radio-wrap {
      float: left;
      height: 32px;
      .el-radio-button__inner {
        padding: 8px 24px;
      }
    }
    .btn-voice {
      float: right;
      padding: 4px 12px 2px 12px;
      border: 1px solid @theme-color;
      border-radius: 20px;
      color: @theme-color;
      .iconfont {
        font-size: 20px;
        vertical-align: -2px;
      }
    }
    .btn {
      width: 100px;
      float: left;
      margin-left: 20px;
    }
  }
  .main-content {
    background-color: #fff;
    min-width: 830px;
    margin: 0 9px;
    /* table{
      width: 100%; */
    .mark-form {
      min-width: 806px;
      border-radius: 4px 4px 0px 0px;
      margin: 0 12px;
      .head_title {
        height: 53px;
        min-width: 806px;
        background: #fafafa;
        text-align: left;
        line-height: 53px;
        font-size: 16px;
        color: #333333;
        .date {
          min-width: 72px;
        }
        .user {
          min-width: 100px;
        }
        .cars {
          min-width: 49px;
          display: block;
          height: 53px;
        }
        
        /* 车牌号 */
        .carNumber{
          min-width: 49px;
          /* display: block; */
          height: 53px;
        }
        .beginTime {
          min-width: 72px;
        }
        .travel {
          min-width: 227px;
        }
        .price {
          min-width: 58px;
        }
        .remark {
          min-width: 76px;
        }
        .handle {
          min-width: 127px;
          .addBtn {
            display: inline-block;
            width: 22px;
            height: 22px;
            border-radius: 50%;
            background: #0db592;
            margin-left: 30px;
            /* position: relative;
             top: px; */
            text-align: center;
            line-height: 22px;
            .iconfont-close {
              transform: rotate(45deg);
              display: inline-block;
              font-size: 14px;
              color: white;
              cursor: pointer;
            }
          }
        }
      }
      .carsBlock {
        min-width: 49px;
        display: inline-block;
        vertical-align: middle;
        height: 37px;
        cursor: pointer;
        border: 1px solid #dcdfe6;
      }
      .tableList {
        line-height: 45px;
        border-bottom: 1px solid #eeeeee;
        .firstDate {
          padding-left: 10px;
        }
        .el-dropdown-link {
          cursor: pointer;
          width: 82px;
          height: 30px;
          display: block;
          background: #0db592;
          border-radius: 3px;
          font-size: 14px;
          color: #ffffff;
          text-align: center;
          line-height: 30px;
          margin-left: 14px;
        }
        /* .readyBtn:hover .dropFa{
          display: block!important;
        } */

        .dropFa {
          position: relative;
          .drop {
            width: 81px;
            height: 160px;
            background: #ffffff;
            box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.42);
            border-radius: 3px;
            border: 1px solid #eeeeee;
            cursor: pointer;
            position: absolute;
            left: 14px;
            .redwarm {
              width: 120px;
              opacity: 0;
              position: absolute;
              top: -5px;
              left: -10px;
              cursor: pointer;
            }
            span {
              cursor: pointer;
              display: block;
              /* width:81px; */
              height: 40px;
              text-align: center;
              border-bottom: 1px solid #eeeeee;
            }
          }
        }

        .dropDown {
          width: 81px;
          height: 80px;
          background: #ffffff;
          box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.42);
          border-radius: 3px;
          border: 1px solid #eeeeee;
          cursor: pointer;
          position: absolute;
          left: 14px;
          span {
            display: block;
            width: 81px;
            height: 40px;
            text-align: center;
            border-bottom: 1px solid #eeeeee;
          }
        }
        .icon-gou5 {
          width: 20px;
          height: 20px;
          vertical-align: middle;
          border-radius: 50%;
          background: #0db592;
          color: #ffffff;
        }
        .ready {
          height: 20px;
          cursor: pointer;
          line-height: 20px;
          font-size: 14px;
          color: #333333;
        }
        .change {
          height: 20px;
          cursor: pointer;
          line-height: 20px;
          font-size: 14px;
          color: #0db592;
        }
      }
      .submitBtn {
        display: inline-block;
        width: 82px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        background: #0db592;
        border-radius: 3px;
        font-size: 14px;
        color: #ffffff;
        margin-left: -32px;
        cursor: pointer;
      }
    }
  }
  .icon-time {
    cursor: pointer;
    /* padding: 5px; */
    font-size: 18px;
  }
}
.memo-container ul li {
  float: left;
  list-style: none;
}
.memo-container .none {
  position: relative;
  top: 0;
  left: 0;
  opacity: 0;
}
.memo-container .el-input__inner {
  padding: none;
}
.memo-container{
  .iconfont-wrap {
    display: inline-block;
    line-height:10px!important;
      .iconfont-tel {
        color: @theme-color;
        font-size: 24px;
        vertical-align: -4px;
        padding: 2px;
        cursor: pointer;
      }
    .iconfont-arrow {
      display: inline-block;
      transform: rotate(-90deg);
      cursor: pointer;
    }
  }
  .line{
      line-height:15px!important;
    }
  .boxed{
    line-height:25px;
  }
  
}
.redDot{
    width: 4px;
    height: 4px;
    border-radius: 50%;
    background: #ff5656;
    display: inline-block;
    vertical-align: middle;
    margin-right: 3px;
  }
</style>