<template>
  <div class="car-main">
    <div class="main-header">
      <div class="tab-wrap">
        <el-tabs v-model="carListParams.car_source" @tab-click="fixLinksType">
          <el-tab-pane label="自有车辆" name="1"></el-tab-pane>
          <el-tab-pane label="外援车队" name="2"></el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div class="main-content">
      <div class="handle-wrap">
        <div class="inp-wrap">
          <span class="iconfont icon-shujuchaxun iconfont-search"></span>
          <el-input
            key="inside"
            v-if="carListParams.car_source == 1"
            class="inp-search"
            v-model="keywords"
            placeholder="请输入车牌号/司机姓名"
          ></el-input>
          <el-input
            key="outside"
            v-else
            class="inp-search"
            v-model="keywords"
            placeholder="请输入车队名称"
          ></el-input>
        </div>
         <el-select v-model="carListParams.paginate" placeholder="请选择显示条数" class="seatsnum-select pageselect">
          <el-option
            v-for="(item,index) in pageOptions"
            :key="index"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
        <div class="btn-wrap" v-if="carListParams.car_source == 1">
          <a href="javascript:void(0)" @click="downloadCarList" class="btn btn-export">全部导出</a>
        </div>
        <!-- <div class="btn-wrap" v-if="carListParams.car_source == 1">
          <el-upload
            class="avatar-uploader"
            :http-request="uploadExcel"
            :action="''"
            :multiple="true"
            :show-file-list="false"
          >
            <div class="btn btn-export">导入车辆</div>
          </el-upload>
        </div> -->
        <div class="btn-wrap">
          <router-link
            v-if="carListParams.car_source == 1"
            :to="{name: 'increaseCar', query: {car_source: carListParams.car_source}}"
            class="btn add-driver"
          >添加车辆</router-link>
          <router-link
            v-else
            :to="{name: 'increaseCustomer',query: {customerType: 2}}"
            class="btn add-driver"
          >添加车队</router-link>
        </div>
      </div>
      <div class="table">
        <dataBlank
          v-if="(!tableData.length || !tableDataOut.length) && maxPage === 0"
          data-type="car"
          :pageType="carListParams.car_source"
        ></dataBlank>
        <el-table
          key="inside"
          :data="tableData"
          v-if="tableData.length && carListParams.car_source == 1"
          style="width: 100%"
        >
          <el-table-column prop="car_number" label="车牌号"></el-table-column>
          <el-table-column prop="car_type" label="车辆型号"></el-table-column>
          <el-table-column prop="seat_num" label="座位数"></el-table-column>
          <el-table-column prop="driver_name" label="司机"></el-table-column>
          <el-table-column prop="driver_mobile" label="手机号" v-if="carListParams.car_source == 2"></el-table-column>
          <el-table-column
            prop="next_maintain_at"
            label="距下次保养"
            v-if="carListParams.car_source == 1"
          ></el-table-column>
          <el-table-column
            prop="next_insurance_at"
            label="距下次保险"
            v-if="carListParams.car_source == 1 "
          ></el-table-column>
          <el-table-column
            prop="next_annual_audit_at"
            label="距下次年检"
            v-if="carListParams.car_source == 1"
          ></el-table-column>
          <el-table-column prop="run_km" label="公里数" v-if="carListParams.car_source == 1"></el-table-column>
          <el-table-column prop="address" label="操作" width="150">
            <template slot-scope="scope">
              <div class="btn-container">
                <div class="btn-select-wrap">
                  <span class="btn-txt">操作</span>
                  <el-select
                    v-model="btnOption"
                    placeholder="操作"
                    class="hid-select"
                    @focus="btnSelectBlur(scope.row,scope.$index)"
                  >
                    <el-option value="1" label="查看"></el-option>
                    <el-option value="2" label="修改"></el-option>
                    <el-option value="3" label="已保养"></el-option>
                    <el-option value="4" label="已保险"></el-option>
                    <el-option value="5" label="已年审"></el-option>
                  </el-select>
                </div>
                <div class="date-wrap">
                  <el-date-picker
                    class="car-handler-date"
                    v-model="handerDate"
                    popper-class="handler-date-popper"
                    :ref="'handerDate'+scope.$index"
                    value-format="yyyy-MM-dd"
                    @change="getHandlerDate"
                    type="date"
                  ></el-date-picker>
                </div>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <el-table
          key="outside"
          :data="tableDataOut"
          v-if="tableDataOut.length && carListParams.car_source == 2"
          style="width: 100%"
        >
          <el-table-column prop="out_company" label="车队名称"></el-table-column>
          <el-table-column prop="name" label="联系人"></el-table-column>
          <el-table-column prop="mobile" label="联系方式"></el-table-column>
          <el-table-column prop="driver_name" label="已添加车辆">
            <template slot-scope="scope">
              <span v-if="!scope.row.car_count">未添加</span>
              <span v-else>{{scope.row.car_count}}辆</span>
            </template>
          </el-table-column>
          <el-table-column prop="address" label="操作" width="150">
            <template slot-scope="scope">
              <div class="btns">
                <router-link
                  :to="{name: 'customerDetails', query: {customer_id: scope.row.customer_id,customerType: 2}}"
                  class="btn"
                >查看</router-link>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pages-wrap" v-if="controlPage">
        <el-pagination
          :current-page="page"
          @current-change="changePage"
          :page-count="maxPage"
          background
          layout="prev, pager, next"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'carOrigin',
  data () {
    return {
      carListParams: {
        car_source: '1',
        keywords: '',
        paginate:''
      },
      page: 1,
      maxPage: null,
      keywords: '',
      tableData: [],
      tableDataOut: [],
      btnOption: null,
      btnRowData: null,
      handerDate: null,
      dateHandlerType: null,
      pageOptions: [{
        value: '10',
        label: '10'
      }, {
        value: '20',
        label: '20'
      }, {
        value: '50',
        label: '50'
      }],
    }
  },
  created () {
    this.watchIsType()
  },
  watch: {
    carListParams: {
      handler (val) {
        if (this.page != 1) {
          this.resetPage()
        }
        this.getCarList()
      },
      deep: true
    },
    btnOption (newVal) {
      let info = this.btnRowData
      if (!newVal) {
        return
      } else if (newVal == 1) {
        this.$router.push({ name: 'carDetails', query: { car_id: info.car_id, type: this.carListParams.car_source } })
      } else if (newVal == 2) {
        this.$router.push({ name: 'increaseCar', query: { car_id: info.car_id } })
      } else {
        let msg;
        if (newVal == 3) msg = '请填写下次保养时间'
        else if (newVal == 4) msg = '请填写下次保险时间'
        else msg = '请填写下次年审时间'
        this.$message(msg)
        this.dateHandlerType = newVal
        this.$refs['handerDate' + info.index].focus()
      }
      this.btnOption = null
    },
  },
  computed: {
    controlPage () {
      let car_source = this.carListParams.car_source
      return ((car_source == 1 && this.tableData.length) || (car_source == 2 && this.tableDataOut.length))
    }
  },
  methods: {
    // 获取自有车辆表格选中行的数据
    btnSelectBlur (rowInfo, index) {
      this.btnRowData = Object.assign({}, rowInfo, { index })
    },
    modifyCarData (info, type, date) {
      let params = {
        car_id: info.car_id,
        car_number: info.car_number,
        car_type: info.car_type,
        seat_num: info.seat_num,
        car_source: '1',
        driver_name: info.driver_name,
        driver_mobile: info.driver_mobile,
        driver_id: info.driver_id,
        run_km: info.run_km
      }
      if (type == 3) {
        params.next_maintain_at = date
      } else if (type == 4) {
        params.next_insurance_at = date
      } else {
        params.next_annual_audit_at = date
      }
      this.$axios.post(this.$httpUrl.carSave, params).then(res => {
        if (res != false) {
          this.$message({
            message: '车辆修改成功',
            type: 'success'
          })
          this.getCarList()
        }
      })
    },
    // 获取按钮操作的数据
    getHandlerDate (date) {
      this.modifyCarData(this.btnRowData, this.dateHandlerType, date)
      this.handerDate = null
    },
    // 监听router中的query.type
    watchIsType () {
      let type = this.$route.query.type
      if (!type) {
        type = 1
      }
      this.carListParams.car_source = type.toString()
      this.getCarList()
    },
    fixLinksType () {
      this.$router.push({ name: 'carOrigin', query: { type: this.carListParams.car_source } })
      this.carListParams.paginate=''
    },
    resetPage () {
      this.page = 1
      this.tableData = []
    },
    searchFrom () {
      this.carListParams.keywords = this.keywords
    },
    getCarList () {
      let url, params
      let car_source = this.carListParams.car_source
      params = {
        page: this.page
      }
      if (car_source == 1) {
        url = this.$httpUrl.carList
      } else {
        url = this.$httpUrl.allfleetList
      }
      params = Object.assign({}, this.carListParams, params)
      this.$axios.get(url, { params: params }).then(res => {
        if (res != false) {
          this.maxPage = res.pages
          if (car_source == 1) {
            this.tableData = res.list
          } else {
            this.tableDataOut = res.list
          }

        }
      })
    },
    changePage (page) {
      this.page = page
      this.getCarList()
    },
    downloadCarList () {
      let params = {
        car_source: this.carListParams.car_source
      }
      this.$axios.get(this.$httpUrl.downloadCarList, { params: params }).then(res => {
        if (res != false) {
          if (res.url) {
            window.location.href = res.url
          } else {
            this.$message({
              message: '没有可以导出的数据',
              type: 'info'
            })
          }
        }
      })
    },
    // 导入车辆excel 表12-19
    uploadExcel (r) {
      let file = r.file
      let type = file.type
      const isText = type === 'application/vnd.ms-excel'
      const isTextComputer = type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      if (!isText && !isTextComputer) {
        this.$message.error('只能上传excel表格')
        return
      }
      if (file.size > 1024 * 1024) {
        this.$message.error('excel表格大小不能超过1MB')
        return
      }
      let formData = new FormData()
      formData.append('files', r.file)
      formData.append('client', 'saas_windows')
      formData.append('version', '1.3.0')
      this.$axios.defaults.headers['Content-Type'] = 'multipart/form-data'
      this.$axios.post(this.$httpUrl.postExcelCar, formData).then(res => {
        if (res) {
          this.$message('导入成功')
        }
      })
    }
  }
}
</script>
<style lang="less" scoped>
.btn-export {
  padding: 0px 20px;
}
</style>
<style lang ="less">
.car-main {
  .btn-container {
    position: relative;
    .date-wrap {
      position: absolute;
      left: -340px;
      bottom: 20px;
      opacity: 0;
      z-index: -2;
    }
  }
}
.pageselect{
  width: 130px;
  margin-left: 20px;
}
.handle-wrap{
  &/deep/.el-input__inner {
    height: 32px;
  }
}
</style>
