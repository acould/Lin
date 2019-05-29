<template>
  <div>
    <el-dialog :visible.sync="boxShow" class="popupStationBox" :close-on-click-modal=false>
      <div class="dialog-header" slot="title">
        <div class="inp-wrap">
          <el-input placeholder="请输入接送机" v-if="stationType ==2" prefix-icon="el-icon-search" v-model="keywords">
          </el-input>
          <el-input placeholder="请输入接送站" v-if="stationType ==1" prefix-icon="el-icon-search" v-model="keywords">
          </el-input>
          <a href="javascript:void(0)" class="btn-search btn" @click="searchKeywords">搜索</a>
        </div>
      </div>
      <div class="main">
        <div class="letter-wrap">
          <span class="letter" @click="selectLetter(letter)" :class="selectedLetter == letter ? 'selected' : ''" v-for="(letter, index) in letterList" :key="index">{{letter}}</span>
        </div>
        <div class="area-list">
          <span class="area" :class="selectedClass(item.name)" v-for="(item,index) in letterDataList" :key="index" @click="selectArea(item.name)">
            {{item.name}}
          </span>
        </div>
        <div class="btn-footer">
          <div class="left-side">
            <span class="lb">已选择:</span>
            <div class="selected-list">
              <span v-for="(item, index) in selectedList" :key="index">
                <span v-if="index !== 0">、</span>
                {{item}}
              </span>
            </div>
          </div>
          <a href="javascript:void(0);" class="btn btn-confirm" @click="cfmSelectArea">确定</a>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'popupStationBox',
  props: {
    popupStationBoxShow: {
      type: Boolean,
      default: false
    },
    stationType: {
      type: Number,
      default: 0
    },
    carType: {
      type: String,
      default: ''
    }
  },
  watch: {
    boxShow (newVal) {
      if (!newVal) {
        this.resetParams()
      }
      this.$emit('update:popupStationBoxShow', newVal)
    },
    popupStationBoxShow (newVal) {
      this.boxShow = newVal
    },
    stationType () {
      this.getList()
    },
    selectedLetter () {
      this.filterLetterList()
    }
  },
  data () {
    return {
      boxShow: this.popupStationBoxShow,
      keywords: '',
      params: {
        keywords: '',
        type: 0
      },
      listObj: {},
      letterDataList: [],
      letterList: [],
      selectedLetter: null,
      selectedList: []
    }
  },
  methods: {
    getList () {
      let stationType = this.stationType
      let url = stationType == 2 ? this.$httpUrl.airportList : this.$httpUrl.trainList
      this.$axios.get(url, { params: this.params }).then(res => {
        if (res != false) {
          this.$delete(res, 'message')
          this.listObj = res
          let letterList = Object.keys(res)
          this.letterList = letterList
          this.selectedLetter = letterList[0]
          this.letterDataList = res[letterList[0]]
        } else {
          this.listObj = {}
          this.letterList = []
          this.selectedLetter = null
          this.letterDataList = []
          this.$message({
              message: '暂无信息',
              type: 'info'
          })
        }
      })
    },
    searchKeywords () {
      this.params.keywords = this.keywords
      this.getList()
    },
    filterLetterList () {
      this.letterDataList = this.listObj[this.selectedLetter]
    },
    selectLetter (letter) {
      this.selectedLetter = letter
    },
    selectedClass (name) {
      let b = this.selectedList.includes(name)
      if (b) return 'color-green'
    },
    selectArea (name) {
      let selectedList = this.selectedList
      let b = selectedList.includes(name)
      if (!b) this.selectedList.push(name)
      else {
        let ind = selectedList.findIndex(function (value, index, arr) {
          return value === name;
        })
        this.selectedList.splice(ind, 1)
      }
    },
    resetParams () {
      this.keywords = '',
      this.params.keywords = ''
      this.selectedList = []
      this.getList()
    },
    cfmSelectArea () {
      let selectedList = this.selectedList
      let car_type = this.carType
      let params = {
        car_type: car_type,
        station: selectedList
      }
      this.$axios.get(this.$httpUrl.selfShuttleStation, { params: params }).then(res => {
        if (res != false) {
          let shuttle_type = this.stationType
          var arr = selectedList.map(ele => {
            return {
              id: 0,
              station: ele,
              shuttle_type: shuttle_type,
              day_price: 0,
              km: 0,
              over_km: 0,
              car_type: car_type
            }
          })
          this.$emit('rtnConfigList', [...arr])
          this.resetParams()
          this.boxShow = false
        }
      })
    }
  }
}
</script>
<style lang="less" scoped>
.popupStationBox {
  & /deep/ .el-dialog {
    width: 703px;
    height: 404px;
    background-color: #f5f5f5;
    border-radius: 4px;
    .el-dialog__body {
      padding: 0px;
    }
    .el-dialog__footer {
      text-align: center;
      padding: 0;
    }
  }
  .dialog-header {
    padding: 0 15px;
    .inp-wrap {
      padding-top: 11px;
      margin-bottom: 11px;
      .el-input {
        width: 355px;
      }
      .btn-search {
        width: 70px;
        margin-left: 20px;
        display: inline-block;
      }
    }
  }
  .main {
    background-color: #f5f5f5;
    padding: 0 15px;
    position: relative;
    height: 404px;

    .letter-wrap {
      padding-bottom: 10px;
      border-bottom: 1px solid #ccc;
      .letter {
        padding-right: 5px;
        margin-right: 15px;
        cursor: pointer;
        &.selected {
          color: @theme-color;
        }
      }
    }
    .area-list {
      // overflow: hidden;
      height: 316px;
      overflow-y: scroll;
      .area {
        float: left;
        width: 25%;
        margin-top: 20px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        cursor: pointer;
      }
    }
    .btn-footer {
      position: absolute;
      bottom: 0;
      left: 0;
      width: 100%;
      height: 50px;
      padding-right: 132px;
      box-sizing: border-box;
      background-color: #fff;
      overflow: hidden;
      .selected-list {
        width: 100%;
        // height: 40px;
        // overflow: hidden;
        position: relative;
        top: -1px;
        line-height: 20px;
        padding-left: 60px;
        box-sizing: border-box;
        word-break: break-all;
      }
      .btn-confirm {
        position: absolute;
        right: 0;
        bottom: 0;
        width: 132px;
        height: 100%;
        line-height: 50px;
        border-radius: 0;
      }
      .left-side {
        padding: 5px 0;
        .lb {
          float: left;
          margin-left: 10px;
          font-size: 14px;
          color: #333;
        }
      }
    }
  }
}
</style>

