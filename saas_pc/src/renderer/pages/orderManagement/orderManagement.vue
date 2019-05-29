<template>
  <div class="orderManagement">
    <div class="main-header" ref="main-header">
      <div class="tab-wrap">
        <el-tabs v-model="value" @tab-click="tabList">
          <el-tab-pane label="接单管理" name="1"></el-tab-pane>
          <el-tab-pane label="投诉管理" name="2"></el-tab-pane>
          <el-tab-pane label="下单设置" name="3"></el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div class="tip-wrap" v-if="!hasMotorcadeInfo">
      <p class="tip-txt">
        <span class="icon-xiangqing iconfont iconfont-tip"></span>
        您尚未设置车队首页，请前往首页管理页面。
      </p>
      <router-link :to="{name: 'motorcadeInfo'}" class="btn-set">立即设置</router-link>
    </div>
    <span class="dot dot-red" v-if="isAllArranged > 0 ? true : false ">{{isAllArranged}}</span>
    <span class="dots dot-red" v-if="comlaintNum > 0 ? true : false ">{{comlaintNum}}</span>
    <div v-show="this.value==1" class="main-content ">
      <!-- 12-17 -->
      <div class="top-box" ref="top-box">
        <el-radio-group class="radio-wrap" v-model="plan_type">
          <el-radio-button label="0">全部</el-radio-button>
          <el-radio-button label="1">未排班</el-radio-button>
          <el-radio-button label="2">已排班</el-radio-button>
          <el-radio-button label="3">已拒绝</el-radio-button>
        </el-radio-group>
        <div class="c-sort-wrap">
          <div class="sort-line" :class="{'selected': tableParams.order_by_time === 1}" @click="sortToggle(1)">下单时间排序
            <div class="arrow-list">
              <span class="iconfont icon-jiantouarrow486"></span>
              <span class="iconfont icon-jiantouarrow486"></span>
            </div>
          </div>
          <div class="sort-line" :class="{'selected': tableParams.order_by_time === 2}" @click="sortToggle(2)">出发时间排序
            <div class="arrow-list">
              <span class="iconfont icon-jiantouarrow486"></span>
              <span class="iconfont icon-jiantouarrow486"></span>
            </div>
          </div>
        </div>
      </div>
      <div class="handle-wrap" ref="handle-wrap">
              <div class="date-warp">
          <el-date-picker
            v-model="tableParams.start_time"
            type="date"
            placeholder="请选择时间"
            class="seldate"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
          <el-date-picker
            :disabled="tableParams.start_time ? false : true"
            v-model="tableParams.end_time"
            type="date"
            placeholder="请选择时间"
            class="seldate"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </div>
        <div class="inp-wrap">
          <span class="iconfont icon-shujuchaxun iconfont-search"></span>
          <el-input class="inp-search" v-model="keywords" placeholder="请输入客户姓名/联系电话"></el-input>
        </div>
        <a href="javascript:void(0)" class="btn-search" @click="searchFrom">搜索</a>
        <div class="btn-wrap">
          <a href="javascript:void(0)" class="btn btn-export" @click="downloadTableList">全部导出</a>
        </div>
      </div>
      <dataBlank v-if="!tableData.length && maxPage === 0" data-type="order"></dataBlank>
      <div class="table order-table" v-if="tableData.length">
        <el-table ref="multipleTable" :data="tableData" :height="tableHeight" tooltip-effect="dark">
          <el-table-column label="订单编号" width="100">
            <template slot-scope="scope">
              <div
                v-if="scope.row.saas_order_id >0"
                class="links color-green"
                @click="jumpDetails(scope.row.order_type,scope.row.saas_order_id)"
              >{{scope.row.order_num}}</div>
            </template>
          </el-table-column>
          <el-table-column label="订单类型" prop="order_type">
            <template slot-scope="scope">
              <p v-if="scope.row.order_type==1">包车</p>
              <p v-if="scope.row.order_type==2">接送</p>
              <p v-if="scope.row.order_type==3">班车</p>
            </template>
          </el-table-column>
          <el-table-column label="客户" width="130" prop="travel_time">
            <template slot-scope="scope">
              <router-link
                :to="{name: 'customerDetails',query:{customer_id: scope.row.customer_id}}"
              >
                <p>
                  {{ scope.row.name}}
                  <span class="icon-xiangqing iconfont iconfont-tip"></span>
                </p>
                <p>{{ scope.row.mobile}}</p>
                <p>{{ scope.row.company}}</p>
              </router-link>
            </template>
          </el-table-column>
          <el-table-column label="乘客姓名" width="130" prop="travel_time">
            <template slot-scope="scope">
              <p>{{ scope.row.contact_name}}</p>
              <p>{{ scope.row.contact_mobile}}</p>
            </template>
          </el-table-column>
          <el-table-column label="日期" width="170" prop="travel_time">
            <template slot-scope="scope">
              <p>开始日期：<span :class="timeCompare(scope.row.travel_begin_at_value)">{{ scope.row.travel_begin_at }}</span></p>
              <p>结束日期：{{ scope.row.travel_end_at }}</p>
            </template>
          </el-table-column>
          <el-table-column label="集合时间" width="100" prop="travel_from_time">
            <template slot-scope="scope">
              <p>{{ scope.row.travel_from_time}}</p>
            </template>
          </el-table-column>
          <el-table-column label="行程">
            <template slot-scope="scope">
              <p>{{scope.row.travel_content}}</p>
              <p v-if="scope.row.need_return==0">单程</p>
              <p v-if="scope.row.need_return==1">往返</p>
            </template>
          </el-table-column>
          <!--2019-1-4 新增集合地点 -->
          <el-table-column prop="travel_from_place" label="地点"></el-table-column>
          <el-table-column prop="car_remark" width="100" label="备注"></el-table-column>
          <el-table-column prop="team_num" width="60" label="团号"></el-table-column>
          <el-table-column label="车辆" width="100">
            <template slot-scope="scope">
              <div v-for="(car, index) in scope.row.car_info" :key="index" class="car">
                <p>{{car.seat_num}}座*{{car.car_num}}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="money" label="价格" width="100"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <div class="btns" v-if="scope.row.saas_order_id == 0">
                <span class="hadChange" v-if="scope.row.backlog_id!=0">已转为待办事项</span>
                <a href="javascript:void(0);" class="btn" @click="arrangeHandler(1,scope.row)">手动排班</a>
                <a href="javascript:void(0);" class="btn" @click="arrangeHandler(2,scope.row)">智能排班</a>
                <a
                  href="javascript:void(0);"
                  class="btn btn-refuse"
                  @click="arrangeHandler(3,scope.row)"
                >拒绝接单</a>
                <a
                  v-if="scope.row.backlog_id==0"
                  @click="changeReady(scope.row.tour_order_id)"
                  href="javascript:void(0);"
                  class="deleteBTN"
                >转为待办事项</a>
              </div>
              <div class="arrange-status" v-if="scope.row.saas_order_id < 0">
                <span class="iconfont icon-htmal5icon19 iconfont-fail"></span>
                <span>{{scope.row.status_text}}</span>
              </div>
              <div class="arrange-status" v-if="scope.row.saas_order_id > 0">
                <span class="iconfont icon-xuanzhonggou iconfont-ok"></span>
                <span>{{scope.row.status_text}}，</span>
                <div
                  class="links"
                  @click="jumpDetails(scope.row.order_type,scope.row.saas_order_id)"
                >查看详情</div>
              </div>
              <a
                href="javascript:void(0);"
                class="deleteBTN"
                @click="arrangeHandler(4,scope.row)"
              >删除订单</a>
            </template>
          </el-table-column>
          <!-- 12.6转为待办事项 -->
          <!-- <el-table-column label="一键转为代办事项">
            <template slot-scope="scope">
              
            </template>
          </el-table-column>-->
        </el-table>
      </div>
      <div class="pages-wrap" v-if="tableData.length">
        <el-pagination
          @current-change="changePage"
          :page-count="maxPage"
          background
          layout="prev, pager, next"
        ></el-pagination>
      </div>
    </div>
    <div v-show="this.value==2" class="main-content">
      <div class="handle-wrap">
        <div class="inp-wrap">
          <span class="iconfont icon-shujuchaxun iconfont-search"></span>
          <el-input class="inp-search" v-model="complaintList.keywords" placeholder="请输入客户单位名称"></el-input>
        </div>
        <el-date-picker
          v-model="complaintList.time"
          value-format="yyyy-MM"
          type="month"
          placeholder="选择年月"
        ></el-date-picker>
        <a href="javascript:void(0)" class="btn-search" @click="search()">搜索</a>
      </div>
      <dataBlank v-if="!complaintData.length" data-type="complaint"></dataBlank>
      <div class="table" v-if="complaintData.length>0" >
        <el-table ref="multipleTable" :data="complaintData"  tooltip-effect="dark">
          <el-table-column label="订单编号" width="100">
            <template slot-scope="scope">
              <span
                v-if="scope.row.order_num !=null"
                @click="handleClick(scope.row.complainant_id,scope.row.order_id,scope.row.not_read)"
                class="links color-green"
              >{{scope.row.order_num}}</span>
            </template>
          </el-table-column>
          <el-table-column label="单位名称" min-width="100">
            <template slot-scope="scope">
              <span>{{scope.row.company}}</span>
              <span v-show="scope.row.not_read>0" class="doted"></span>
            </template>
          </el-table-column>
          <el-table-column label="行程时间" min-width="150">
            <template slot-scope="scope">
              <p v-if="scope.row.travel_begin_at">开始：{{scope.row.travel_begin_at}}</p>
              <p v-if="scope.row.travel_end_at">结束：{{scope.row.travel_end_at}}</p>
            </template>
          </el-table-column>
          <el-table-column prop="travel_content" label="行程内容" min-width="230"></el-table-column>
          <el-table-column label="司机" min-width="100">
            <template slot-scope="scope">
              <div class="boxed" v-if="scope.row.drivers.length">
                {{scope.row.drivers[0].driver_name}}
                <div class="iconfont-wrap">
                  <el-popover
                    popper-class="tel-tip"
                    placement="top-start"
                    width="150"
                    trigger="hover"
                  >
                    {{scope.row.drivers[0].driver_mobile}}
                    <span
                      v-show="scope.row.drivers[0].driver_name"
                      slot="reference"
                      class="iconfont icon-bohao iconfont-tel"
                    ></span>
                  </el-popover>
                </div>
                <div class="iconfont-wrap">
                  <el-popover
                    popper-class="table-tip"
                    placement="right"
                    width="320"
                    trigger="click"
                  >
                    <div class="line-wrap">
                      <div class="line" v-for="(info, index) in scope.row.drivers" :key="index">
                        <span class="name">{{info.driver_name}}</span>
                        <span class="mobile">{{info.driver_mobile}}</span>
                        <span class="car-number">{{info.car_number}}</span>
                        <span v-if="info.car_source == 1" class="car-source out-car">外援</span>
                      </div>
                    </div>
                    <span
                      v-show="scope.row.drivers[0].driver_name"
                      slot="reference"
                      class="iconfont icon-jiantou2 iconfont-arrow"
                    ></span>
                  </el-popover>
                </div>
                <p class="line">{{scope.row.drivers[0].car_number}}</p>
                <span v-if="scope.row.drivers[0].car_source == 1" class="car-source out-car">外援</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="content" label="投诉内容" min-width="250"></el-table-column>
          <el-table-column prop="created_at" label="投诉时间" min-width="100"></el-table-column>
          <el-table-column label="操作" min-width="100">
            <template slot-scope="scope">
              <el-button
                class="lookBtn"
                @click="handleClick(scope.row.complainant_id,scope.row.order_id,scope.row.not_read)"
                type="text"
                size="small"
              >查看详情</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="pages-wrap" v-if="complaintData.length">
        <el-pagination
          @current-change="changeComPage"
          :page-count="maxPages"
          background
          layout="prev, pager, next"
        ></el-pagination>
      </div>
    </div>
    <div v-show="this.value==3" class="main-content">
      <el-form :label-width="labelWidth" class="mission-form customForm">
        <el-form-item :label="'客户下单必填项'">
          <div class="car-form">
            <el-form-item label="开始时间">
              <el-input :disabled="true" class="longwidth"></el-input>
            </el-form-item>
            <el-form-item label="预计结束时间">
              <el-input :disabled="true" class="longwidth"></el-input>
            </el-form-item>
            <el-form-item label="行程">
              <el-input :disabled="true" class="longwidth"></el-input>
            </el-form-item>
            <el-form-item label="车辆">
              <el-input :disabled="true" class="longwidth"></el-input>
            </el-form-item>
          </div>
        </el-form-item>
        <el-form-item :label="'选填项'">
          <div class="car-form">
            <el-form-item label="价格">
              <el-input :disabled="true" class="longwidth"></el-input>
            </el-form-item>
            <el-form-item label="团号">
              <el-input :disabled="true" class="longwidth"></el-input>
            </el-form-item>
          </div>
        </el-form-item>
        <el-form-item :label="'自定义字段'">
          <div class="car-form">
            <user-defined-tag
              v-for="(field, index) in definable_fields"
              :typeId="field.type_id"
              :tagValue.sync="field.value"
              :isRequired="field.is_required"
              :propName="'definable_fields.'+index+'.value'"
              :propVal="field.value"
              :content="field.content.split(',')"
              :tagName="field.name"
              :key="'definedTag'+index"
            ></user-defined-tag>
            <a href="javascript:void(0);" class="btn-tag" @click="addTag">添加自定义字段</a>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <setMotorcadeTipBox :setMotorcadeTipBoxShow.sync="setMotorcadeTipBoxShow"></setMotorcadeTipBox>
  </div>
</template>
<script>
import setMotorcadeTipBox from "@/components/setMotorcadeTipBox"
import { mapActions, mapState } from 'vuex'
import customMixin from '@/assets/mixins/custom-mixins'
export default {
  name: 'orderManagement',
  mixins: [customMixin],
  data () {
    return {
      setMotorcadeTipBoxShow: false,
      tableData: [],
      tableParams: {
        keywords: null,
        // date: null,
        order_by_time: 1,
        order_by_sort: 1,
        start_time: null,
        end_time: null
      },
      page: 1,
      maxPage: null,
      keywords: '',
      hasMotorcadeInfo: true,
      value: '1',
      complaintList: {
        keywords: '',
        time: '',
      },
      plan_type: '0',
      complaintData: [],
      pages: 1,
      maxPages: null,
      labelWidth: '173px',
      definable_fields: [],
      tableHeight: null,
    }
  },
  components: {
    setMotorcadeTipBox
  },
  computed: {
    ...mapState({
      isAllArranged: state => state.message.isAllArranged,
      comlaintNum: state => state.message.comlaintNum
    })
  },
  watch: {
    plan_type () {
      if (this.page !== 1) {
        this.page = 1
      }
      this.$nextTick(() => {
        this.getTableList()
      })
    },
    tableParams: {
      handler (val) {
        if (this.page != 1) {
          this.resetPage()
        }
        this.getTableList()
      },
      deep: true,
      immediate: true
    },
    '$route': {
      handler (to, next, from) {
      },
      immediate: true
    }
  },
  created () {
    this.getMessageCondition()
    this.getInfo()
    this.getFieldList()
  },
  beforeDestroy () {
    window.onresize = null
  },
  mounted () {
    if (this.$route.query.value == 2) {
      this.value = '2'
      this.getcomplaintList()
    } else if (this.$route.query.value == 1) {
      this.value = '1'
      this.getMessageCondition()
      this.getInfo()
    }
    this.setTableHight()
    window.onresize = this.$util.debounce(this.setTableHight, 500)
  },
  methods: {
    setTableHight () {
      this.tableHeight = this.$root.$el.clientHeight - this.$refs['main-header'].clientHeight -
        this.$refs['handle-wrap'].clientHeight - this.$refs['top-box'].clientHeight -54 -30 + 'px'
    },
    ...mapActions([
      "getMessageCondition"
    ]),
    // 样式函数
    timeCompare (timestamp) {
      let curDate = new Date(new Date().toLocaleDateString()).getTime()
      let missionDate = new Date(new Date(timestamp * 1000).toLocaleDateString()).getTime()
      if (missionDate >= curDate) {
        return 'color-red'
      }
      return ''
    },
    // 获取车队信息 
    getInfo () {
      this.$axios.get(this.$httpUrl.getMotorcadeInfo).then(res => {
        if (res) {
          if (res.car_images.length) {
            this.hasMotorcadeInfo = true
          } else {
            this.setMotorcadeTipBoxShow = true
            this.hasMotorcadeInfo = false
          }
        } else {
          this.hasMotorcadeInfo = false
        }
      })
    },
    sortToggle (sortNum) {
      let sort = this.tableParams.order_by_sort
      let time = this.tableParams.order_by_time
      time === sortNum ?
        (this.tableParams.order_by_sort = sort === 1 ? 2 : 1) :
        (this.tableParams.order_by_time = time === 1 ? 2 : 1)
    },
    // 查询表单
    searchFrom () {
      this.tableParams.keywords = this.keywords
    },
    // 获取表格数据
    getTableList () {
      let params = {
        page: this.page,
        type: this.plan_type
      }
      params = Object.assign({}, this.tableParams, params)
      this.$axios.get(this.$httpUrl.receiveList, { params: params }).then(res => {
        if (res) {
          this.maxPage = res.pages
          this.tableData = res.list
        }
      })
    },
    // 导出表格数据
    downloadTableList () {
      let params = {
        date: this.tableParams.date,
        keywords: this.tableParams.keywords
      }
      this.$axios.get(this.$httpUrl.orderListDownload, { params: params }).then(res => {
        if (res) {
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
    arrangeHandler (type, info) {
      /*
        type: 1:手动发单，2:智能发单, 3:拒绝接单
      */
      if (type === 3 || type === 4) {
        let tipTxt = type === 3 ? '是否确认拒绝接单' : '是否确认删除订单'
        this.$confirm(tipTxt, {
          type: 'warning'
        }).then(() => {
          let order_id = info.tour_order_id
          let url = type === 3 ? this.$httpUrl.ordereReject : this.$httpUrl.deleteReceiveOrder
          this.$axios.get(url, { params: { order_id: order_id } }).then(res => {
            if (res) {
              this.$message({
                message: '操作成功',
                type: 'info'
              })
              if (type == 3) this.getMessageCondition()
              this.getTableList()
            }
          })
        })
      } else {
        let queryParams = {
          customer_company: info.company,
          customer_mobile: info.mobile,
          customer_name: info.name,
          tour_guide_mobile: info.contact_mobile,
          tour_guide_name: info.contact_name,
          customer_id: info.customer_id,
          travel_content: info.travel_content,
          money: info.money,
          remark: info.car_remark,
          showDateScope: 1, // 1:只填充行程时间 2，填充车辆和行程时间
          travel_begin_at: info.begin_at.split(' ')[0],
          travel_end_at: info.end_at.split(' ')[0],
          tour_order_id: info.tour_order_id,
          team_num: info.team_num,
          travel_from_place: info.travel_from_place,
          travel_from_lng: info.travel_from_lng,
          travel_from_lat: info.travel_from_lat
        }
        // this.$message('提交成功')
        if (type == 1) {
          this.$router.push({ name: 'addMission', query: queryParams })
          // this.$router.push({ name: 'addMission', query: {backlog_id:info.backlog_id} })
        } else {
          let order_id = info.tour_order_id
          let loading = this.$loading({ text: '正在智能排班中，请稍等' })
          this.$axios.get(this.$httpUrl.smartSchedule, { params: { order_id: order_id } }).then(res => {
            loading.close()
            if (res) {
              if (res.message) {
                this.$message({
                  message: res.message,
                  type: 'error'
                })
              }
              if (res.length) {
                queryParams.order_cars_str = JSON.stringify(res)
                this.$router.push({ name: 'addMission', query: queryParams })
              }
            }
          })
        }
      }
    },
    // 转为待办事项
    changeReady (id) {
      this.$axios.get(this.$httpUrl.changeReadyOrder, { params: { order_id: id } }).then(res => {
        if (res) {
          this.$message('操作成功')
          this.getTableList()
        }
      })
    },
    // 翻页
    changePage (page) {
      this.page = page
      this.getTableList()
    },
    tabList () {
      this.$router.push({ name: 'orderManagement', query: { value: this.value } })
      if (this.value == 2) {
        this.getcomplaintList()
      }
    },
    // 查询投诉列表
    search () {
      this.getcomplaintList()
    },
    //获取投诉列表
    getcomplaintList () {
      let params = {
        page: this.pages
      }
      params = Object.assign({}, this.complaintList, params)
      this.$axios.get(this.$httpUrl.getComplaintList, { params: params }).then(res => {
        if (res) {
          this.maxPages = res.pages
          this.complaintData = res.list
        }
      })
    },
    handleClick (complainant_id, order_id, not_read) {
      //  complainant_id 投诉者id   order_id 订单id
      // console.log(complainant_id)
      // console.log(order_id)
      if (not_read > 0) {
        let params = {
          complainant_id: complainant_id,
          order_id: order_id
        }
        this.$axios.get(this.$httpUrl.setComplaintRead, { params: params }).then(res => {
          if (res) {
            console.log(res)
          }
        })
      }
      this.$router.push({ name: 'complaintDetails', query: { complainant_id: complainant_id, order_id: order_id } })
    },
    changeComPage (page) {
      this.pages = page
      this.getcomplaintList()
    },
    jumpDetails (orderType, order_id) {
      if (orderType == 1) {
        this.$router.push({ name: 'accountDetails', query: { order_id: order_id, source: 'mission' } })
      } else if (orderType == 2) {
        this.$router.push({ name: 'TransportStationDetail', query: { order_id: order_id, source: 'mission' } })
      } else if (orderType == 3) {
        this.$router.push({ name: 'shuttleBusDetail', query: { order_id: order_id, source: 'mission' } })
      }
    },
    addTag () {
      this.setCustomData(this.missionForm)
      this.$router.push({ name: 'addCustom', query: { location_id: this.$locationId.MiNIPROGRAM_ID } })
    },
    // 获取设置的自定义列表标签
    getFieldList () {
      let params = {
        location_id: this.$locationId.MiNIPROGRAM_ID
      }
      let p = this.$axios.get(this.$httpUrl.customFiledList, { params: params }).then(res => {
        if (res != false) {
          res.list.map(ele => {
            ele.value = null
          })
          this.definable_fields = res.list
        }
      })
      return p
    },
  }
}
</script>
<style lang ="less" scoped>
.orderManagement {
  position: relative;
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
  }
  .deleteBTN {
    width: 80px;
    text-align: center;
    line-height: 30px;
    border: 1px solid #9a9a9a;
    border-radius: 4px;
    display: inline-block;
  }
  .dot {
    position: absolute;
    left: 86px;
    top: 16px;
    padding: 1px 3px;
    background-color: #ff5656;
    border-radius: 12px;
    line-height: 1;
    font-size: 12px;
    color: #fff;
  }
  .dots {
    position: absolute;
    left: 182px;
    top: 16px;
    padding: 1px 3px;
    background-color: #ff5656;
    border-radius: 12px;
    line-height: 1;
    font-size: 12px;
    color: #fff;
  }
  .tip-wrap {
    height: 31px;
    line-height: 31px;
    border: 1px solid #e7d3ac;
    background-color: #fffbdd;
    .tip-txt {
      font-size: 14px;
      color: #f27800;
      padding-left: 32px;
      display: inline-block;
      .iconfont-tip {
        color: #f27800;
        font-size: 16px;
        vertical-align: -1px;
        margin-right: 5px;
      }
    }
    .btn-set {
      float: right;
      font-size: 14px;
      padding: 0 10px;
      color: @theme-color;
    }
  }
  .table {
    .iconfont-tip {
      color: #0db592;
      font-size: 16px;
      margin-left: 5px;
    }
    .doted {
      width: 5px;
      height: 5px;
      display: inline-block;
      border-radius: 50%;
      background: #ff5656;
      position: relative;
      top: -5px;
    }
    .btn-refuse {
      color: #999;
      border: 1px solid #ebebeb;
      background-color: #fff;
    }
  }
  .handle-wrap {
    .el-select {
      float: left;
      width: 120px;
      height: 32px;
      margin-left: 20px;
      & /deep/ .el-input__inner {
        height: 32px;
        line-height: 32px;
      }
    }
    & /deep/ .el-date-editor {
      float: left;
      margin-left: 20px;
      .el-input__inner {
        height: 32px;
        line-height: 32px;
      }
      .el-input__prefix,
      .el-input__suffix {
        top: -4px;
      }
    }
  }
  .arrange-status {
    color: @color-dark-grey;
    font-size: @fontsize-medium;
    display: inline-block;
    .iconfont {
      font-size: 16px;
      vertical-align: middle;
      margin-right: 5px;
    }
    .iconfont-ok {
      color: @theme-color;
    }
    .iconfont-fail {
      color: #e44a4a;
      font-size: 20px;
      vertical-align: -2px;
    }
    .links {
      color: @theme-color;
    }
    .btn-delete {
      margin-top: 10px;
      width: 50px;
      text-align: center;
    }
  }
  .lookBtn {
    width: 80px;
    height: 32px;
    border-radius: 4px;
    border: 1px solid #979797;
    background: #fff;
    text-align: center;
    font-size: 14px;
    color: #333333;
  }
  .iconfont-wrap {
    display: inline-block;
    line-height: 10px !important;
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
  .line {
    line-height: 15px !important;
  }
  .boxed {
    line-height: 25px;
  }
  .main-content {
    .mission-form {
      margin-top: 15px;
      .car-form {
        width: 650px;
        border: 1px solid rgba(207, 207, 207, 1);
        border-radius: 4px;
        padding-bottom: 10px;
        .el-form-item {
          margin-top: 5px;

          .longwidth {
            width: 195px;
          }
        }
      }
    }
  }
  .order-table {
    ::-webkit-scrollbar {
      height: 12px;
    }
  }
  .btn-tag {
    color: #0db592;
    margin-left: 102px;
  }
}
</style>
<style lang="less">
.orderManagement {
  .el-input__inner {
    height: 32px;
  }
  .links {
    cursor: pointer;
  }
  .el-form-item__label {
    width: 100px !important;
  }
  .el-form-item__content {
    margin-left: 100px !important;
  }
  .customForm {
    margin-left: 28px;
  }
  .hadChange {
    margin-right: 10px;
    line-height: 30px;
    display: inline-block;
    float: left;
  }
}
</style>
