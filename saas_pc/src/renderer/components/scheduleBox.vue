<template>
  <div>
    <el-dialog :visible.sync="boxShow" class="scheduleBox">
      <div class="dialog-header" slot="title"> 
        <p class="title">{{scheduleInfo.textDate}}<span class="mark">(红色为繁忙时间)</span></p>
      </div>
      <div class="dialog-bd">
        <div class="hour-wrap">
          <span class="hour-status" :class="time.is_busy == 1 ? 'hour-status-busy' : time.is_busy == 2 ? 'hour-status-halfbusy' : ''"  v-for="(time, index) in times" :key="index">
            {{time.text}}
          </span>      
        </div>
      </div>
      <div slot="footer" class="dialog-foot">   
        <a href="javascript:void(0);" class="btn" @click="submitForm">添加任务</a>
      </div>
    </el-dialog>
  </div>

</template>
<script>
export default {
  name: 'scheduleBoxShow',
  props: {
    scheduleBoxShow: {
      type: Boolean,
      default: false
    },
    scheduleInfo: {
      type: Object,
      default: null
    }
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:scheduleBoxShow', newVal)
      this.times = []
    },
    scheduleBoxShow (newVal) {
      this.boxShow = newVal
    },
    scheduleInfo (newVal) {     
      this.getData()
    }
  },
  data () {
    return {
      boxShow: this.scheduleBoxShow,
      totalprice: '',
      times: []
    }
  },
  methods: { 
    submitForm () {     
      let scheduleInfo = this.scheduleInfo
      this.$router.push({ name: 'addMission',query: { car_number: scheduleInfo.car_number, car_id: scheduleInfo.car_id, date: scheduleInfo.date,driver_id:scheduleInfo.driver_id,driver_mobile:scheduleInfo.driver_mobile,driver_name:scheduleInfo.driver_name }})
    },
    getData () {     
      let scheduleInfo = this.scheduleInfo
      let paramsWrap = { params: { car_id: scheduleInfo.car_id, type: 0, date: scheduleInfo.date } }
      this.$axios.get(this.$httpUrl.scheduleDay, paramsWrap).then(res => {
        if (res != false) {         
          this.times = res.times       
        }
      })
    }
  }
}
</script>
<style lang="less" scope>
.scheduleBox {
  & /deep/ .el-dialog {
    width: 441px;
    height: 260px;
    .el-dialog__body {
      padding: 0px;
      padding-top: 20px;
    }
    .el-dialog__footer {
      padding: 0;
    }
    .dialog-bd {
      .el-input__inner {
        width: 220px;
        height: 36px;
        background-color: #f5f5f5;
        margin-left: 67px;
      }
    }
  }  
  .mark {
    color: red;
  }
  .hour-wrap {
    width: 400px;
    margin: 0 auto;
    overflow: hidden;
    .hour-status {
      float: left;
      width: 50px;
      height: 20px;
      line-height: 20px;
      margin-bottom: 20px;
      text-align: center;
      color: #555;
      &-busy {
        background-color: @red;
        color: @color-white;
      }
      &-halfbusy {
        background-color: @yellow;
        color: @color-white;
      }
    }
  }
  .dialog-foot {
    text-align: center;   
    width: 330px;
    margin: 10px 55px 0;
    .btn {
      width: 150px;
      height: 36px;
      line-height: 36px;
      margin: 0 auto;
    }   
  }
}
</style>

