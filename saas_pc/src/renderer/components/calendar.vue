<template>
  <div class="calendar">
    <el-dialog :visible.sync="calendarShow" class="imgBox">
      <div class="calendar-body">
        <div class="year">
          <span id="preYear" class="iconfont icon-jiantouarrowheads3" @click="changeYear(-1)"></span>
          <span id="preMonth" class="iconfont icon-jiantouzuo" @click="changeMonth(-1)"></span>
          <span>{{year+'年'}}{{month+"月"}}</span>
          <span id="nextYear" class="iconfont icon-jiantouarrowheads3" @click="changeYear(1)"></span>
          <span id="nextMonth" class="iconfont icon-jiantouzuo" @click="changeMonth(1)"></span>
        </div>
        <el-checkbox v-show='!showMask' v-model="checked">周期同步至其他趟数</el-checkbox>
        <ul class="week">
          <li v-for="(item,index) in everyWeekDay " :key="index">
            <span
              :class="{placeColor: item.realLen === item.len}"
              @click="choseWeek(item)"
            >{{item.content}}</span>
          </li>
        </ul>
        <!-- 日期 -->
        <ul class="dayLine">
          <li class="dayList" v-for="(item,dayIndex) in daysRowsData" :key="dayIndex">
            <span
              v-if="item.date"
              @click="choseDay(item)"
              :class="{placeColor:item.checked}"
            >{{item.day}}</span>
            <span v-if="!item.date"></span>
          </li>
        </ul>
        <span v-show='!showMask' class='cancel' @click='cancel()'>取消</span>  
        <span v-show='!showMask' @click='submit()' class='submit'>确定</span>
        <div v-show="showMask" class="mask"></div> 
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data () {
    return {
      checked: false, // 是否同步趟数
      calendarShow: false,
      year: null,
      month: null,
      day: '',
      allChoseDay: [],
      daysRowsData: [],
      everyWeekDayTemp: null,
      monthIndex: null, // 数据源索引
      yearIndex: null, // 数据源索引
      
      everyWeekDay: [{
        content: '日',
        value: 0,
        realLen: 0,
        len: 0,
      }, {
        content: '一',
        value: 1,
        realLen: 0,
        len: 0,
      }, {
        content: '二',
        value: 2,
        realLen: 0,
        len: 0,
      }, {
        content: '三',
        value: 3,
        realLen: 0,
        len: 0,
      }, {
        content: '四',
        value: 4,
        realLen: 0,
        len: 0,
      }, {
        content: '五',
        value: 5,
        realLen: 0,
        len: 0,
      }, {
        content: '六',
        value: 6,
        realLen: 0,
        len: 0,
      }],
      timeArr:[],
      shuttleIndex:null,
      dateIndex:null,
      dateArr:[],
      showMask:false
    }
  },
  methods: {
    showToggle (index,dateIndex,cycle_date,date) {
      this.shuttleIndex = index
      this.dateIndex = dateIndex
      this.allChoseDay =cycle_date
      this.calendarShow = true
      this.dateArr =date
      this.copyWeekData()
      let { year, month } = this.getCurrentDate()
      this.getRenderCalender(year, month) 
      if(this.allChoseDay.length){
         this.updateCalenderdata()
      }
      if(index==10000&&dateIndex==10000){
          this.showMask=true
      }
    },
    // 复制重置星期格式数据模板
    copyWeekData () {
      if (!this.everyWeekDayTemp) this.everyWeekDayTemp = JSON.parse(JSON.stringify(this.everyWeekDay)) 
      else this.everyWeekDay = JSON.parse(JSON.stringify(this.everyWeekDayTemp))
    },
    getCurrentDate () {
      let date = this.$util.formatTime().date
      let dateArr = date.split('-')
      let year = this.year = Number.parseInt(dateArr[0])
      let month = this.month = Number.parseInt(dateArr[1])
      return {
        year: year,
        month: month
      }
    },
    // 重置选择的周
    setWeek(){
      // for(let k=0;k<this.everyWeekDay.length;){
        // this.everyWeekDay[k].realLen=0
        // this.everyWeekDay[k].len=0
      // }
    },
    getRenderCalender (year, month) {
      let formatMonth = month - 1
      let formatNextMonth = month
      let lastPropetryObj = this.initDayPropetry(year, formatNextMonth, 0)
      let daysLen = lastPropetryObj.day
      let daysArr = []
      for (var i = 1; i < daysLen; i++) {
        var propetryObj = this.initDayPropetry(year, formatMonth, i, daysLen)
        daysArr.push(propetryObj)
      }
      let weekIndex = daysArr[0].weekIndex
      if (weekIndex) {
        let beforeArr = weekIndex && new Array(weekIndex).fill({})
        daysArr = beforeArr.concat(daysArr)
      }
      daysArr.push(lastPropetryObj)
      // 每月最后一日月数限制
      var months = (Number(daysArr[daysArr.length-1].date.split('-')[1])-1).toString()
      let years = daysArr[daysArr.length-1].date.split('-')[0]
      let days = daysArr[daysArr.length-1].date.split('-')[2]
      if(Number(daysArr[daysArr.length-1].date.split('-')[1])-1<10){
        daysArr[daysArr.length-1].date = years+'-'+'0'+months+'-'+days
      }else{
        daysArr[daysArr.length-1].date = years+'-'+(Number(daysArr[daysArr.length-1].date.split('-')[1])-1).toString()+'-'+days
      }     
        // if(this.dateArr.length){
        //   for(let d=0;d<this.dateArr.length;d++){
        //     for(let b=0;b<daysArr.length;b++){
        //       if(this.dateArr[d]==daysArr[b].date){
        //         daysArr[b].checked=true
        //       }
        //     }
        //   }
        //   this.daysRowsData = daysArr
        //   this.daysRowsData.forEach(ele => {
        //       if(ele.checked){
        //         this.everyWeekDay[ele.weekIndex].realLen++
        //       }
        //   })
        // }else{
          this.daysRowsData = daysArr
        // }
    },
    initDayPropetry (year, month, dayIndex, len) {
      let date = new Date(year, month, dayIndex)
      let weekIndex = date.getDay()  
      this.everyWeekDay[weekIndex].len++
      let day = date.getDate()
      return {
        date: [year, month + 1, day].map(this.formatNumber).join('-'),
        day: day,
        weekIndex: weekIndex,
        checked: false,
      }
    },
    formatNumber (n) {   
      n = n.toString()
      return n[1] ? n : '0' + n
    },
    changeYear (num) {
      let year = this.year
      let yearChanged = year + num
      if (2000 <= yearChanged && yearChanged <= 2050) {
        this.year = yearChanged
      }
      // this.setWeek()
      this.updateCalenderdata()
    },
    changeMonth (num) {
      let month = this.month
      let monthChanged = num + month
      if (monthChanged < 1) {
        this.year--
        this.month = 12
      } else if (monthChanged > 12) {
        this.year++
        this.month = 1
      } else {
        this.month = monthChanged
      }
      // this.setWeek()
      this.updateCalenderdata()
    },
    updateCalenderdata () {
      this.copyWeekData()
      this.yearIndex = null
      this.monthIndex = null
      let curYear = this.year
      let curMonth = this.month
      this.getRenderCalender(curYear, curMonth)
      let allChoseDay = this.allChoseDay
      if (allChoseDay.length) {      
        let daysArr = []
        allChoseDay.forEach((ele, index) => {
          if (ele.year === curYear) {
            this.yearIndex = index
            let monthList = ele.list
            monthList.forEach((item, monthIndex) => {
              if (item.month === curMonth) {
                this.monthIndex = monthIndex
                daysArr = item.list
              }
            })
          }
        })
        // 存储源中的日期匹配日历模板数据中的日期
        this.daysRowsData.forEach(ele => {
          if (daysArr.includes(ele.date)) {
            ele.checked = true
            this.everyWeekDay[ele.weekIndex].realLen++
          }
        })

      }
    },
    choseWeek (item) {
      let weekIndex = item.value //
      let allChoseDay = this.allChoseDay
      let yearIndex = this.yearIndex
      let monthIndex = this.monthIndex
      let weekIndexArr = []
      let filterArr = []
      let isWillChecked = !(item.len === item.realLen)
      this.daysRowsData.forEach((ele, index) => {
        if (ele.weekIndex === weekIndex) {
          ele.checked = isWillChecked
          weekIndexArr.push(ele.date)
        }
      })

      if (allChoseDay.length) {
        if (yearIndex === null) {
          this.yearIndex = allChoseDay.length
          allChoseDay.push({ year: this.year, list: [{ month: this.month, list: weekIndexArr }] })
          item.realLen = item.len
          return
        }
        if (monthIndex === null) {
          this.monthIndex = allChoseDay[yearIndex].list.length
          allChoseDay[yearIndex].list.push({ month: this.month, list: weekIndexArr })
          item.realLen = item.len
          return
        }
        let daysList = this.allChoseDay[yearIndex].list[monthIndex].list
        let newDaysList = []
        if (item.len === item.realLen) {
          newDaysList = daysList.filter((ele, index) => {
            return !weekIndexArr.includes(ele)
          })
          item.realLen = 0
          if (!newDaysList.length) {
            let yearList = this.allChoseDay
            let monthList = this.allChoseDay[yearIndex].list
            if (monthList.length === 1) {
              this.allChoseDay.splice(yearIndex, 1)
            } else {
              this.allChoseDay[yearIndex].list.splice(monthIndex, 1)
            }
            return
          }
        } else {
          newDaysList = [...new Set(daysList.concat(weekIndexArr))]
          item.realLen = item.len
        }
        this.allChoseDay[yearIndex].list[monthIndex].list = newDaysList
      } else {
        this.monthIndex = 0
        this.yearIndex = 0
        allChoseDay.push({ year: this.year, list: [{ month: this.month, list: weekIndexArr }] })
        item.realLen = item.len
      }
    },
    choseDay (item) {
      item.checked = !item.checked
      let allChoseDay = this.allChoseDay
      let yearIndex = this.yearIndex
      let monthIndex = this.monthIndex
      if (allChoseDay.length) {
        // 如果数据源中不存在这年
        if (yearIndex === null) {
          this.yearIndex = allChoseDay.length
          allChoseDay.push({ year: this.year, list: [{ month: this.month, list: [item.date] }] })
          this.everyWeekDay[item.weekIndex].realLen++
          return
        }
        // 如果数据源中不存在这月
        if (monthIndex === null) {
          this.monthIndex = allChoseDay[yearIndex].list.length
          allChoseDay[yearIndex].list.push({ month: this.month, list: [item.date] })
          this.everyWeekDay[item.weekIndex].realLen++
          return
        }
        let daysList = allChoseDay[yearIndex].list[monthIndex].list
        var dayIndex = daysList.findIndex(val => val === item.date)
        if (dayIndex === -1) {
          daysList.push(item.date)
          this.everyWeekDay[item.weekIndex].realLen++
        } else {
          let yearList = this.allChoseDay
          let monthList = this.allChoseDay[yearIndex].list
          let daysLen = daysList.length
          if (daysLen == 1) {
            if (monthList.length === 1) {
              this.allChoseDay.splice(yearIndex, 1)
            } else {
              this.allChoseDay[yearIndex].list.splice(monthIndex, 1)
            }
          } else {
            daysList.splice(dayIndex, 1)
          }
          this.everyWeekDay[item.weekIndex].realLen--
        }
      } else {
        this.monthIndex = 0
        this.yearIndex = 0
        this.allChoseDay = [{ year: this.year, list: [{ month: this.month, list: [item.date] }] }]
        this.everyWeekDay[item.weekIndex].realLen++
      }
    },
    submit(){
      this.$emit('choseTime',this.allChoseDay,this.shuttleIndex,this.dateIndex,this.checked)
      this.calendarShow =false
      this.allChoseDay =[]
      this.dateArr=[]
      this.checked=false
    },
    // 取消
    cancel(){
      this.calendarShow = false
      this.allChoseDay=[]
      this.updateCalenderdata()
       this.dateArr=[]
    }
  }
}
</script>
<style lang='less'>
.calendar {
  .imgBox{
    .el-dialog {
      width: 322px;
      height: 350px;
      .el-dialog__header{
        height: 30px;
        line-height: 30px;
        .el-dialog__close{
          vertical-align: middle;
        }
      }
    }
  }
  
  .el-checkbox {
    margin-left: 130px;
  }
}
</style>

<style lang='less' scoped>
.calendar {
  .imgBox {
    .calendar-body {
      .year {
        text-align: center;
        margin-bottom: 10px;
        #preYear {
          width: 30px;
          float: left;
          margin-right: 15px;
          cursor: pointer;
        }
        #preMonth {
          width: 30px;
          float: left;
          text-align: left;
          cursor: pointer;
        }
        #nextYear {
          width: 30px;
          float: right;
          text-align: left;
          cursor: pointer;
          transform: rotate(180deg);
        }
        #nextMonth {
          width: 30px;
          float: right;
          cursor: pointer;
          transform: rotate(180deg);
        }
      }
      .bgColor {
        width: 30px;
        height: 30px;
        display: inline-block;
        border-radius: 50%;
        text-align: center;
        background: #0db592;
        color: white;
      }
      .placeColor {
        width: 30px;
        height: 30px;
        line-height: 30px;
        display: inline-block;
        border-radius: 50%;
        text-align: center;
        background: #0db592;
        color: white;
      }
      .week {
        width: 280px;
        line-height: 30px;
        border-bottom: 1px solid #eeeeee;
        user-select: none;
        li {
          width: 38.3px;
          height: 30px;
          display: inline-block;
          text-align: center;
          cursor: pointer;
          span {
            width: 30px;
            height: 30px;
            display: inline-block;
          }
        }
      }
      .dayLine {
        width: 280px;
        user-select: none;
        li {
          width: 38px;
          height: 30px;
          line-height: 30px;
          display: inline-block;
          text-align: center;
          cursor: pointer;
        }
        span {
          width: 30px;
          height: 30px;
          display: inline-block;
        }
      }
    }
  }
  .cancel{
    margin-left:180px;
    color:black;
    cursor:pointer;
  }
  .submit{
    margin-left:15px;
    padding:5px;
    display:inline-block;
    border-radius:5px;
    background:#0db592;
    color:#FFFFFF;
    cursor:pointer;
  }
  .mask{
    width:300px;
    height: 240px;
    background:white;
    opacity: 0;
    position: absolute;
    top:53px;
    // z-index: 9999999;  
  }
}
</style>