<template>
  <div class="account-main addmission">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'arrange' }">调度</el-breadcrumb-item>
          <el-breadcrumb-item
            :to="{ name: 'mission',query: {customerId:customer_id,companyName:companyName} }"
          >{{companyName}}任务</el-breadcrumb-item>
          <el-breadcrumb-item>添加任务</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="section-container">
      <el-form
        :model="missionForm"
        :rules="missionRules"
        ref="releaseForm"
        :label-width="labelWidth"
        class="mission-form"
      >
        <el-form-item label="订单类型">
          <el-radio :disabled="changeRadio" v-model="missionForm.order_type" label="1">包车</el-radio>
          <el-radio :disabled="changeRadio" v-model="missionForm.order_type" label="2">接送</el-radio>
          <el-radio :disabled="changeRadio" v-model="missionForm.order_type" label="3">班车</el-radio>
        </el-form-item>
        <el-form-item
          label="用车时间"
          class="inline-item"
          prop="travel_begin_at"
          v-if="missionForm.order_type == 1"
        >
          <el-date-picker
            v-model="missionForm.travel_begin_at"
            type="date"
            placeholder="请选择时间"
            class="seldate"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
        <el-form-item
          label="预计回程时间"
          class="inline-item"
          prop="travel_end_at"
          v-if="missionForm.order_type == 1"
        >
          <el-date-picker
            v-model="missionForm.travel_end_at"
            type="date"
            placeholder="请选择时间"
            class="seldate"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="集合地点" v-show="missionForm.order_type == 1">
          <input
            id="place"
            type="text"
            v-model="missionForm.travel_from_place"
            placeholder="等候客人通知"
          >
        </el-form-item>
        <el-form-item label="行程" v-if="missionForm.order_type == 1">
          <el-input
            type="textarea"
            placeholder="请输入时间、地点、人数等相关信息"
            v-model="missionForm.travel_content"
          ></el-input>
        </el-form-item>
        <!-- 包车 -->
        <el-form-item
          v-if="missionForm.order_type == 1"
          :label="'车辆和司机信息'"
          v-for="(order, index) in missionForm.order_cars"
          :key="index"
        >
          <div class="car-form" @click="getBoxIndex(index)">
            <span class="iconfont icon-close iconfont-close" @click="deleteCarDaysInfo(index)"></span>
            <el-form-item
              class="car-form-item"
              label="出行车辆"
              :prop="'order_cars.' + index + '.car_number'"
              :rules="missionRules.car_number"
            >
              <el-input
                placeholder="请输入车牌号选择车辆"
                readonly
                v-model="order.car_number"
                class="longwidth"
                @click.native="edit"
              ></el-input>
              <span class="outcar" v-if="order.car_source == 2">外援</span>
            </el-form-item>
            <el-form-item label="可用时间" v-if="order.begin_at_date && !order_id && isSelect">
              <p class="mark">
                <span>{{order.begin_at_date.substring(5).split('-')[0]+ '月' + order.begin_at_date.substring(5).split('-')[1]+ '日'}} {{order.begin_at_time}}</span> ——
                <span>{{order.end_at_date.substring(5).split('-')[0]+ '月' + order.end_at_date.substring(5).split('-')[1]+ '日'}} {{order.end_at_time}}</span>
              </p>
            </el-form-item>
            <el-form-item
              label="开始日期"
              class="inline-item"
              :prop="'order_cars.' + index + '.begin_at_date'"
              :rules="missionRules.begin_at_date"
            >
              <el-input
                placeholder="请选择日期"
                readonly
                v-model="order.begin_at_date"
                class="date"
                @click.native="edit"
              ></el-input>
              <span class="lbl-name">时间</span>
            </el-form-item>
            <el-form-item
              class="inline-item time"
              :prop="'order_cars.' + index + '.begin_at_time'"
              :rules="missionRules.begin_at_time"
            >
              <el-input
                maxlength="5"
                class="time"
                @blur="formatClock(index, 1)"
                @input="addColon(index, 1)"
                v-model.trim="order.begin_at_time"
                placeholder="请填写"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="结束日期"
              class="inline-item"
              :prop="'order_cars.' + index + '.end_at_date'"
              :rules="missionRules.end_at_date"
            >
              <el-input
                placeholder="请选择日期"
                readonly
                v-model="order.end_at_date"
                class="date"
                @click.native="edit"
              ></el-input>
              <span class="lbl-name">时间</span>
            </el-form-item>
            <el-form-item
              class="inline-item time"
              :prop="'order_cars.' + index + '.end_at_time'"
              :rules="missionRules.end_at_time"
            >
              <el-input
                maxlength="5"
                class="time"
                @blur="formatClock(index, 2)"
                @input="addColon(index, 2)"
                v-model.trim="order.end_at_time"
                placeholder="请填写"
              ></el-input>
            </el-form-item>
            <el-form-item
              v-if="order.car_source == 2"
              class="inline-item"
              label="外援费用"
              :prop="'order_cars.' + index + '.foreign_money'"
              :rules="missionRules.foreign_money"
            >
              <el-input placeholder="请输入外援费用" v-model="order.foreign_money" class="longwidth"></el-input>
            </el-form-item>
            <el-form-item
              v-if="order.car_source == 2"
              class="inline-item"
              label="代收款"
              :prop="'order_cars.' + index + '.collection_money'"
              :rules="missionRules.collection_money"
            >
              <el-input placeholder="请输入代收款" v-model="order.collection_money" class="longwidth"></el-input>
            </el-form-item>
            <el-form-item
              label="司机姓名"
              class="inline-item"
              :prop="'order_cars.' + index + '.driver_name'"
              :rules="missionRules.driver_name"
              
            >
              <el-autocomplete
                value-key="name"
                placeholder="请输入司机姓名"
                v-model.trim="order.driver_name"
                class="longwidth"
                :fetch-suggestions="querySearchDriver"
                @select="handleSelectDriver"
                @focus="getIndex(index)"
                tabindex="-1"
              ></el-autocomplete>
            </el-form-item>
            <el-form-item
              label="联系方式"
              class="inline-item"
              :prop="'order_cars.' + index + '.driver_mobile'"
            >
              <!-- :rules="missionRules.driver_mobile" -->
              <el-input placeholder="请输入联系方式" v-model.trim="order.driver_mobile" tabindex="-1" class="longwidth"></el-input>
            </el-form-item>
            <div v-for="(driver, driverIndex) in order.vice_drivers" :key="'assistant'+driverIndex">
              <el-form-item label="司机姓名" class="inline-item">
                <el-autocomplete
                  value-key="name"
                  placeholder="请输入司机姓名"
                  v-model.trim="driver.driver_name"
                  class="longwidth"
                  :fetch-suggestions="querySearchDriver"
                  @select="handleSelectAssistant"
                  @focus="getAssistantIndex(index, driverIndex)"
                ></el-autocomplete>
              </el-form-item>
              <el-form-item label="联系方式" class="inline-item">
                <el-input
                  placeholder="请输入联系方式"
                  v-model.trim="driver.driver_mobile"
                  class="longwidth"
                ></el-input>
              </el-form-item>
              <span
                class="iconfont icon-close driver-close"
                @click="removeAssistant(index, driverIndex)"
              ></span>
            </div>
            <el-form-item v-if="order.car_source != 2">
              <a
                href="javascript:void(0);"
                class="btn btn-assistant"
                @click="addAssistant(index)"
              >添加副班司机</a>
            </el-form-item>
            <el-form-item
              v-if="order.car_source != 2"
              label="现收款"
              prop="my_collection_money"
              class="inline-item"
            >
              <el-input
                type="number"
                placeholder="请输入现收款"
                v-model="order.my_collection_money"
                class="longwidth"
              ></el-input>
            </el-form-item>
            <el-form-item label="司机备注" prop="remark_to_driver" class="inline-item changeDriver">
              <el-input
                type="textarea"
                placeholder="请输入司机备注"
                v-model="order.remark_to_driver"
                class="longwidth addWidth"
              ></el-input>
            </el-form-item>
          </div>
        </el-form-item>
        <el-form-item v-if="missionForm.order_type == 1">
          <div class="edit btn-wrap">
            <a href="javascript:void(0);" class="btn" @click="edit">添加车辆</a>
          </div>
        </el-form-item>
        <!-- 包车 -->
        <!-- 班车 -->
        <shuttleBus
          ref="shuttleBus"
          v-if="missionForm.order_type == 3"
          :req-info="missionForm.bus_order_cars"
          :shuuleBusData.sync="missionForm.bus_order_cars"
        ></shuttleBus>
        <!-- 接送站 -->
        <transport-station
          v-if="missionForm.order_type == 2"
          :orderType="missionForm.order_type"
          :reqParams.sync="missionForm.total_order_cars"
          :req-info="missionForm.total_order_cars"
        ></transport-station>
        <!-- 接送站 -->
        <!-- 自定义标签组 -->
        <el-form-item :label="'自定义信息'" v-if="missionForm.definable_fields.length">
          <div class="car-form">
            <a href="javascript:void(0);" class="btn-tag" @click="addTag" v-if="!order_id">添加自定义字段</a>
            <user-defined-tag
              v-for="(field, index) in missionForm.definable_fields"
              :typeId="field.type_id"
              :tagValue.sync="field.value"
              :isRequired="field.is_required"
              :propName="'definable_fields.'+index+'.value'"
              :propVal="field.value"
              :content="field.content.split(',')"
              :tagName="field.name"
              :key="'definedTag'+index"
            ></user-defined-tag>
          </div>
        </el-form-item>
        <!-- 自定义标签组 -->
        <el-form-item label="单位名称" prop="customer_company" :rules="missionRules.customer_company">
          <el-autocomplete
            type="text"
            value-key="company"
            v-model.trim="missionForm.customer_company"
            :fetch-suggestions="querySearchComapny"
            @select="handleSelectComapny"
            placeholder="请输入用车单位"
            auto-complete="off"
          ></el-autocomplete>
        </el-form-item>
        <el-form-item
          label="联系人"
          class="inline-item"
          prop="customer_name"
          :rules="missionRules.customer_name"
        >
          <el-autocomplete
            v-if="customer_id"
            type="text"
            v-model="missionForm.customer_name"
            placeholder="请输入联系人姓名"
            :fetch-suggestions="querySearchName"
            @select="handleSelectName"
            auto-complete="off"
          ></el-autocomplete>
          <el-input
            v-else
            type="text"
            v-model.trim="missionForm.customer_name"
            placeholder="请输入联系人姓名"
            auto-complete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="customer_mobile" class="inline-item">
          <!-- :rules="missionRules.customer_mobile" -->
          <el-input
            type="text"
            v-model.trim="missionForm.customer_mobile"
            placeholder="请输入联系人手机号"
            auto-complete="off"
          ></el-input>
          <el-checkbox
            v-model="missionForm.customer_driver_see"
            :true-label="1"
            :false-label="0"
          >号码显示给司机</el-checkbox>
        </el-form-item>
        <div></div>
        <el-form-item label="乘客姓名" class="inline-item">
          <el-input
            type="text"
            v-model="missionForm.tour_guide_name"
            placeholder="请输入乘客姓名"
            auto-complete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="联系方式" class="inline-item">
          <el-input
            type="text"
            v-model.trim="missionForm.tour_guide_mobile"
            maxlength="11"
            placeholder="请输入乘客手机号"
            auto-complete="off"
          ></el-input>
          <el-checkbox
            v-model="missionForm.tour_driver_see"
            :true-label="1"
            :false-label="0"
          >号码显示给司机</el-checkbox>
        </el-form-item>
        <el-form-item label="团号">
          <el-input
            type="text"
            v-model="missionForm.team_num"
            placeholder="请输入团号"
            auto-complete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="money" :rules="missionRules.money" class="inline-item">
          <el-input v-model="missionForm.money" placeholder="请输入订单总价" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="已收定金"
          class="inline-item deposit"
          prop="deposit"
          :rules="missionRules.deposit"
        >
          <el-input v-model="missionForm.deposit" placeholder="请输入已收定金(选填)" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="备用金"
          class="inline-item deposit"
          prop="imprest"
          :rules="missionRules.imprest"
        >
          <el-input v-model="missionForm.imprest" placeholder="请输入备用金(选填)" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注" class="inline-item changeDriver">
          <el-input
            type="textarea"
            v-model="missionForm.remark"
            placeholder="请输入备注(选填)"
            auto-complete="off"
            class="longwidth addWidth"
          ></el-input>
        </el-form-item>
        <el-form-item label="是否开票" class="inline-item">
          <el-radio-group v-model="missionForm.need_invoice" class="el-input">
            <el-radio :label="1">需要</el-radio>
            <el-radio :label="0">不需要</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          label="开票金额"
          class="inline-item deposit"
          prop="invoice_money"
          :rules="missionRules.invoice_money"
        >
          <el-input v-model="missionForm.invoice_money" placeholder="请输入实际开票金额" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="业务员" class="inline-item">
          <el-autocomplete
            value-key="name"
            placeholder="请选择订单业务员"
            v-model="missionForm.salesman_name"
            :fetch-suggestions="querySearchSalesman"
            @select="handleSelectSalesman"
            @blur="verifySalesman"
          ></el-autocomplete>
        </el-form-item>
        <el-form-item
          label="客户返佣"
          class="inline-item deposit"
          prop="rebate"
          :rules="missionRules.rebate"
        >
          <el-input placeholder="请输入返佣" v-model="missionForm.rebate"></el-input>
        </el-form-item>
        <el-form-item label="业务员返佣" class="inline-item deposit" prop="salesman_rebate">
          <el-input placeholder="请输入返佣" v-model="missionForm.salesman_rebate"></el-input>
        </el-form-item>
        <el-form-item label="支付方式">
          <el-radio v-model="missionForm.pay_type" label="2">挂账</el-radio>
          <el-radio v-model="missionForm.pay_type" label="1">现结</el-radio>
        </el-form-item>
        <el-form-item v-if="missionForm.order_type!=3" label="通知方式">
          <el-checkbox v-model="missionForm.notify_type" true-label="4">都不通知</el-checkbox>
        </el-form-item>
        <el-form-item>
          <div class="btn-wrap">
            <a
              href="javascript:void(0);"
              class="btn"
              @click="release"
            >{{isPastDate ? '保存' : order_id ? '修改' : '发布'}}</a>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <notifyStyleBox :notifyStyleBoxShow.sync="notifyStyleBoxShow" @comfirmNotify="comfirmNotify"></notifyStyleBox>
    <carStyleBox
      v-if="missionForm.order_type == '1'"
      :carStyleBoxShow.sync="carStyleBoxShow"
      :carInfo="carInfo"
      :dateInterval="dateInterval"
      @rtnSelectedList="rtnSelectedList"
      @rtnCarDaysInfo="rtnCarDaysInfo"
    ></carStyleBox>
    <selectedCarBox
      v-if="missionForm.order_type == '1'"
      :selectedCarBoxShow.sync="selectedCarBoxShow"
      :dateInterval="dateInterval"
      :arrangeInfo="arrangeInfo"
      @rtnCarDaysInfo="rtnCarDaysInfo"
    ></selectedCarBox>
    <div id="mapContainer" style="width:0;height:0;" v-if="missionForm.order_type == '1'">
      <el-amap ref="maps" :amap-manager="maps.amapManager" vid="amapDemo" :events="maps.events"></el-amap>
    </div>
  </div>
</template>
<script>
import notifyStyleBox from '@/components/notifyStyleBox'
import carStyleBox from '@/components/carStyleBox'
import selectedCarBox from '@/components/selectedCarBox'
import TransportStation from '@/components/TransportStation'
import shuttleBus from '@/components/shuttleBus'
import customMixin from '@/assets/mixins/custom-mixins'
import { mapActions } from 'vuex'
export default {
  name: 'addMission',
  mixins: [customMixin],
  components: {
    notifyStyleBox,
    carStyleBox,
    selectedCarBox,
    TransportStation,
    shuttleBus
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
      notifyStyleBoxShow: false,
      carStyleBoxShow: false,
      selectedCarBoxShow: false,
      arrangeInfo: null, // 排班信息 
      carInfo: null,
      order_id: null,
      companyName: null,
      labelWidth: '173px',
      dateInterval: null,
      customer_id: null,
      order_cars_index: null,
      isSelect: false,
      changeRadio: false,
      missionForm: {
        order_type: '1', // 订单类型 1包车，2，接送站，3班车
        tour_order_id: null, // 旅行社订单ID 旅行社订单排班时必传
        dateArr: [],
        travel_begin_at: null, // 行程开始时间
        travel_end_at: null, // 行程结束时间
        travel_from_lng: null,
        travel_from_lat: null,
        travel_from_place: null, // 出发集合地点
        travel_content: null, // 行程信息
        travel: '',
        customer_company: null, // 客户公司
        customer_name: null, // 计调姓名
        customer_mobile: null, // 计调电话
        customer_id: null,
        customer_driver_see: 1, // 司机是否可看客户信息 0不可 1可
        team_num: null, // 团号
        tour_guide_name: null, // 乘客联系人名
        tour_guide_mobile: null, // 乘客联系人电话
        tour_driver_see: 1, //司机是否可看乘客联系人信息 0不可 1可
        order_id: null, // 修改订单时必传 订单ID
        notify_type: null, // 通知类型，1=自动，2=仅司机，3=仅乘客，4=都不通知
        money: null, // 订单金额
        deposit: null, //定金
        imprest: null, // 备用金
        need_invoice: 0, //是否开具发票
        invoice_money: null, //发票金额
        pay_type: '2', // 支付方式 1现结 2挂账
        remark: null, //备注
        self_order_id: null,
        salesman_id: null,  //业务员ID
        salesman_name: null,  //业务员ID
        rebate: null, // 客户返佣
        salesman_rebate: null,//业务员返佣 
        order_cars: [
          {
            // driver_id: null, // 司机ID
            // driver_name: null, // 司机姓名
            // driver_mobile: null, // 司机电话
            // remark_to_driver: null, // 给司机的备注
            // user_car_id: null, // 车辆id
            // car_number: null,
            // foreign_money: null, // 外援车辆费用 （外援车辆时填写）
            // collection_money: null, // 代收款（外援车辆时填写）
            // begin_at_date: null, // 出发日期
            // begin_at_time: null, // 出发时间
            // end_at_date: null, // 结束日期
            // end_at_time: null, // 结束时间,
            vice_drivers: [] //副班司机
          }
        ],
        send_order_cars: {},
        pick_order_cars: {},
        total_order_cars: [{ shuttle_type: '1', travels: { travel_pathway: [] }, driver_cars: [{ vice_drivers: [] }] }, { shuttle_type: '2', travels: { travel_pathway: [] }, driver_cars: [{ vice_drivers: [] }] }],
        // 班车信息
        bus_order_cars: [{ show: true, driver_cars: [{ vice_drivers: [] }], date_travels: [{ cycle_date: [], travels: { travel_from_place: '', travel_to_place: '', travel_pathway: [] } }] }],
        definable_fields: []
      },
      outTableParams: { // 上一页面传进的司机数据
        driver_id: null,
        driver_name: null,
        driver_mobile: null,
      },
      minDate: null,
      pickerOptions2: {
        start: '00:00',
        step: '00:10',
        end: '23:50'
      },
      missionRules: {
        travel_begin_at: { required: true, message: '请选择用车时间', trigger: ['blur', 'change'] },
        travel_end_at: { required: true, message: '请选择预计回程时间', trigger: ['blur', 'change'] },
        dateArr: { required: true, message: '请选择行程时间', trigger: 'blur' },
        customer_company: { required: true, message: '请输入用车单位', trigger: ['blur', 'change'] },
        customer_name: { required: true, message: '请输入联系人姓名', trigger: ['blur', 'change'] },
        customer_mobile: { required: true, message: '请输入手机号', trigger: ['blur', 'change'] },
        // validator: validateMobile,
        money: [
          { required: true, message: '可填写预估价格，行程结束后再修改', trigger: ['blur', 'change'] },
          { required: true, validator: validateMoney, trigger: ['blur', 'change'] },
        ],
        car_number: { required: true, message: '请输入出行车辆', trigger: ['blur', 'change'] },
        begin_at_date: { required: true, message: '请选择开始日期', trigger: ['blur', 'change'] },
        end_at_date: { required: true, message: '请选择结束日期', trigger: ['blur', 'change'] },
        begin_at_time: { required: true, message: '选择时间', trigger: ['blur', 'change'] },
        end_at_time: { required: true, message: '选择时间', trigger: ['blur', 'change'] },
        driver_name: { required: true, message: '请输入司机姓名', trigger: ['blur', 'change'] },
        driver_mobile: { required: true, validator: validateMobile, trigger: ['blur', 'change'] },
        foreign_money: { required: true, message: '外援车辆费用不能为空', trigger: ['blur', 'change'] },
        deposit: { required: true, validator: validateMoney, trigger: ['blur', 'change'] },
        imprest: { required: true, validator: validateMoney, trigger: ['blur', 'change'] },
        invoice_money: { required: true, validator: validateMoney, trigger: ['blur', 'change'] },
        rebate: { required: true, validator: validateMoney, trigger: ['blur', 'change'] },
        // travel_from_place: {required: true, message: '请输入集合地点', trigger: ['blur', 'change']}
      },
      isPastDate: false,
      temp_driver_name: null, //选择框选择的司机的名字
      temp_assistant_name: null, //选择框选择副班司机的
      assistantIndex: null,
      temp_salesman_name: null,
      timeIndex: 0, //车辆信息的时分数组索引
      timeType: null,
      travel_from_place_temp: null, // 集合地点值模板 
      maps: {
        amapManager: new this.$VueAMap.AMapManager(),
        events: {
          init: o => {
            this.firstInitAmap()
          }
        }
      },
      // fieldsList: []
    }
  },
  created () {
    if (this.$route.query.order_id) {
      this.getOrderInfo()
    } else {
      this.getFieldList().then(() => {
        this.getStorePageData()
      })
    }
    this.getQueryParams()
    //获取订单详情
    this.getOrderDetails()
  },
  watch: {
    carStyleBoxShow (newVal) {
      this.carInfo = [...this.missionForm.order_cars]
    },
    'missionForm.travel_begin_at' (newVal) {
      if (newVal) {
        this.isPastDate = this.$util.compareDate(this.missionForm.travel_begin_at)
      }
    },
    dateArr (newVal) {
      this.missionForm.dateArr = newVal
    },
    driver_name (newVal, oldVal) {
      if (this.temp_driver_name != newVal) {
        this.missionForm.order_cars[this.order_cars_index].driver_id = null
      }
    },
    assistant_name (newVal, oldVal) {
      if (this.temp_assistant_name != newVal && this.assistantIndex >=0) {
        this.missionForm.order_cars[this.order_cars_index].vice_drivers[this.assistantIndex].driver_id = null
      }
    },
    'missionForm.salesman_name' (newVal) {
      if (this.temp_salesman_name != newVal) {
        this.missionForm.salesman_id = null
      }
    },
    'missionForm.invoice_money' (newVal) {
      let reg = /^([0]|([1-9]\d*))((\.\d*){0,1})$/g
      if (reg.test(newVal) && newVal != 0) {
        this.missionForm.need_invoice = 1
      } else {
        this.missionForm.need_invoice = 0
      }
    },
    'missionForm.travel_from_place' (newVal) {
      let travel_from_place_temp = this.travel_from_place_temp
      if (travel_from_place_temp && newVal != travel_from_place_temp) {
        this.travelPlaceInpHandler(1)
      }
    },
    'missionForm.total_order_cars' (newVal) {
      if (newVal.length === 2) {
        this.missionForm.pick_order_cars = newVal[0]
        this.missionForm.send_order_cars = newVal[1]
      }
    },
    'missionForm.bus_order_cars' (newVal) {
      if (newVal.length) {
        this.missionForm.bus_order_cars = newVal
      }
    }
  },
  computed: {
    dateArr () {
      return [this.missionForm.travel_begin_at, this.missionForm.travel_end_at]
    },
    driver_name () {
      let order_cars_index = this.order_cars_index
      if (order_cars_index == 0 || order_cars_index) {
        return this.missionForm.order_cars[this.order_cars_index].driver_name
      }
      return ''
    },
    assistant_name () {
      let assistantIndex = this.assistantIndex
      if (assistantIndex == 0 || assistantIndex) {
        let obj = this.missionForm.order_cars[this.order_cars_index].vice_drivers[assistantIndex]
        if (obj) {
          return obj.driver_name
        } else {
          return ' '
        }
      }
      return ''
    }
  },
  methods: {
    // 获取store中页面数据
    getStorePageData () {
      let getCustomData = this.$store.getters.getCustomData
      if (getCustomData) {
        // 判断是否有新增加的自定义字段，若有，增加刚添加的自定义字段
        let pageCustomData = this.missionForm.definable_fields
        let definable_fields = [...getCustomData.definable_fields]
        pageCustomData.forEach(e => {
          definable_fields.forEach(j => {
            if (j.field_id == e.field_id) {
              e.value = j.value
              return
            }
          })
        })
        this.missionForm = Object.assign({}, this.missionForm, getCustomData,{'definable_fields':pageCustomData})
        this.clearCustomData()
      }  
    },
    // 增加自定义标签
    addTag () {
      this.setCustomData(this.missionForm)
      this.$router.push({ name: 'addCustom', query: { location_id: this.$locationId.MISSION_ID } })
    },
    // 获取设置的自定义列表标签
    getFieldList () {
      let params = {
        location_id: this.$locationId.MISSION_ID
      }
      let p = this.$axios.get(this.$httpUrl.customFiledList, { params: params }).then(res => {
        if (res != false) {
          res.list.map(ele => {
            ele.value = null
          })
          this.missionForm.definable_fields = res.list
        }
      })
      return p
    },
    getBoxIndex (index) {
      this.boxIndex = index
    },
    verifySalesman () {
      if (!this.missionForm.salesman_id && this.missionForm.salesman_name) {
        this.missionForm.salesman_name = null
        this.$message.info('请从搜索栏选择')
      }
    },
    // 首次初始化高德地图搜索
    firstInitAmap () {
      let that = this
      let ele = 'place'
      var map = new AMap.Map('mapContainer', {

      })
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
          that.missionForm.travel_from_lat = lat
          that.missionForm.travel_from_lng = lng
          that.missionForm.travel_from_place = place
          that.travel_from_place_temp = place
        })
      })
    },
    //限制只能根据搜索栏搜索
    travelPlaceInpHandler (type) {
      let lng = this.missionForm.travel_from_lng
      let place = this.missionForm.travel_from_place
      if ((type === 1 && lng)) {
        this.missionForm.travel_from_lng = null
        this.missionForm.travel_from_lat = null
      }
    },
    addColon (ind, type) {
      let timeType = type === 2 ? 'end_at_time' : 'begin_at_time'
      let reg1 = /^(([0-1][0-9])|([2][0-3]))$/
      let newVal = this.missionForm.order_cars[ind][timeType]
      if (reg1.test(newVal)) {
        this.missionForm.order_cars[ind][timeType] = newVal + ':'
      }
    },

    // 补充格式车辆信息的时分选择
    formatClock (ind, type) {
      let timeType = type === 2 ? 'end_at_time' : 'begin_at_time'
      let reg2 = /^(([0-1]?[0-9])|([2]?[0-3]))(:?)[0-5]([0-9])$/
      let val = this.missionForm.order_cars[ind][timeType]
      if (!reg2.test(val)) {
        this.missionForm.order_cars[ind][timeType] = null
      }
    },
    getQueryParams () {
      let queryObj = this.$route.query
      let neworder_cars_0 = this.missionForm.order_cars[0]
      let missionForm = this.missionForm
      let fixObj = {}
      // 订单类型
      let order_type = queryObj.order_type
      if (order_type) fixObj.order_type = order_type

      // 车牌号       
      let car_number = queryObj.car_number
      if (car_number) neworder_cars_0.car_number = car_number
      // 车辆id
      let car_id = queryObj.car_id
      if (car_id) {
        neworder_cars_0.car_id = car_id
        neworder_cars_0.user_car_id = car_id
      }
      // 自主下单id
      let self_order_id = queryObj.self_order_id
      if (self_order_id) {
        fixObj.self_order_id = queryObj.self_order_id
      }
      // 乘客联系方式
      if (queryObj.tour_guide_mobile) fixObj.tour_guide_mobile = queryObj.tour_guide_mobile
      // 日期
      let date = queryObj.date
      if (date) {
        fixObj.travel_begin_at = date
        fixObj.travel_end_at = date
        fixObj.dateArr = [date, date]
        neworder_cars_0.begin_at_date = date
        neworder_cars_0.end_at_date = date
      }
      let begin_at_time = queryObj.begin_at_time
      if (begin_at_time) {
        neworder_cars_0.begin_at_time = begin_at_time
      }
      let end_at_time = queryObj.end_at_time
      if (end_at_time) {
        neworder_cars_0.end_at_time = end_at_time

      }
      if (queryObj.begin_at_date) neworder_cars_0.begin_at_date = queryObj.begin_at_date
      if (queryObj.end_at_date) neworder_cars_0.end_at_date = queryObj.end_at_date
      // 选择车辆来源 
      let car_source = queryObj.car_source
      if (car_source) neworder_cars_0.car_source = car_source
      // 司机ID
      let driver_id = queryObj.driver_id
      if (driver_id) neworder_cars_0.driver_id = driver_id
      // 司机联系方式
      let driver_mobile = queryObj.driver_mobile
      if (driver_mobile) neworder_cars_0.driver_mobile = driver_mobile
      // 司机名字
      let driver_name = queryObj.driver_name
      if (driver_name) neworder_cars_0.driver_name = driver_name

      // 获取传进来的车辆来源数据
      if (car_source && car_source == 2) {
        this.outTableParams = Object.assign({}, {
          car_id: car_id,
          user_car_id: car_id,
          car_number: car_number,
          car_source: car_source
        })
      }
      let remark = queryObj.remark
      if (remark) fixObj.remark = queryObj.remark
      let team_num = queryObj.team_num
      if (team_num) fixObj.team_num = team_num
      if (queryObj.tour_guide_name) fixObj.tour_guide_name = queryObj.tour_guide_name
      if (queryObj.tour_guide_mobile) fixObj.tour_guide_mobile = queryObj.tour_guide_mobile
      // 单位
      let customer_company = queryObj.customer_company
      if (customer_company) fixObj.customer_company = customer_company
      // 客户联系方式
      let customer_mobile = queryObj.customer_mobile
      if (customer_mobile) fixObj.customer_mobile = customer_mobile
      // 客户名字
      let customer_name = queryObj.customer_name
      if (customer_name) fixObj.customer_name = customer_name
      // 客户ID
      let customer_id = queryObj.customer_id
      if (customer_id) {
        this.customer_id = customer_id
        fixObj.customer_id = customer_id
      }
      // 行程内容
      let travel_content = queryObj.travel_content
      if (travel_content) fixObj.travel_content = travel_content
      // 价格
      let money = queryObj.money
      if (money) fixObj.money = money
      let deposit = queryObj.deposit

      if (deposit) fixObj.deposit = deposit
      // 旅行社Id
      let tour_order_id = queryObj.tour_order_id
      if (tour_order_id) fixObj.tour_order_id = tour_order_id
      // 行程时间
      let travel_begin_at = queryObj.travel_begin_at
      let travel_end_at = queryObj.travel_end_at
      if (travel_begin_at) {
        fixObj.travel_begin_at = travel_begin_at
        fixObj.travel_end_at = travel_end_at
        fixObj.dateArr = [travel_begin_at, travel_end_at]
        if (queryObj.showDateScope === 2) {
          neworder_cars_0.begin_at_date = travel_begin_at
          neworder_cars_0.end_at_date = travel_end_at
        }
      }

      let order_cars_str = queryObj.order_cars_str
      if (order_cars_str) {
        fixObj.order_cars = JSON.parse(order_cars_str)
      }
      let travel_from_place = queryObj.travel_from_place
      if (travel_from_place) {
        fixObj.travel_from_place = travel_from_place
      }
      let travel_from_lat = queryObj.travel_from_lat
      if (travel_from_lat) {
        fixObj.travel_from_lat = travel_from_lat
      }
      let travel_from_lng = queryObj.travel_from_lng
      if (travel_from_lng) {
        fixObj.travel_from_lng = travel_from_lng
      }
      this.$set(missionForm.order_cars, 0, neworder_cars_0)
      this.missionForm = Object.assign({}, missionForm, fixObj)
    },
    //时间联动选择回调函数
    getDate (val) {
      if (Array.isArray(val)) {
        this.minDate = null
        Object.assign(this.missionForm, { travel_begin_at: val[0], travel_end_at: val[1] })
      }
      else {
        this.minDate = null
        Object.assign(this.missionForm, { travel_begin_at: null, travel_end_at: null })
      }
    },
    getOrderInfo () {
      let order_id = this.order_id = this.$route.query.order_id
      if (!order_id) return
      var p = this.$axios.get(this.$httpUrl.orderDetail, { params: { order_id: order_id } }).then(res => {
        if (res != false) {
          this.changeRadio = true
          res.pay_type = res.pay_type.toString()
          res.order_type = res.order_type.toString()
          res.notify_type = null
          let obj = {
            dateArr: [res.travel_begin_at, res.travel_end_at],
            total_order_cars: [res.pick_order_cars, res.send_order_cars]
          }
          this.temp_salesman_name = res.salesman_name
          this.missionForm = Object.assign(this.missionForm, res, obj)
        }
      })
      return p

    },
    rtnCarDaysInfo (res) {
      this.missionForm.order_cars = res
      this.isSelect = true
      this.carStyleBoxShow = false
      this.selectedCarBoxShow = false
    },
    deleteCarDaysInfo (index) {
      if (this.missionForm.order_cars.length == 1) {
        this.$set(this.missionForm.order_cars, index, {})
      } else {
        this.missionForm.order_cars.splice(index, 1)
      }
    },
    edit () {
      let a = false, b = false
      this.$refs["releaseForm"].validateField('travel_begin_at', (valid) => {
        if (!valid) {
          a = true
        }
      })
      this.$refs["releaseForm"].validateField('travel_end_at', (valid) => {
        if (!valid) {
          b = true
        }
      })
      if (a && b) {
        let missionForm = this.missionForm
        let lastTime = missionForm.dateArr[1] // 行程结束时间
        let firstTime = missionForm.dateArr[0]
        if (!this.$util.compareDate2(firstTime, lastTime)) {
          this.$message({
            showClose: true,
            message: '筛选的时间区间有误',
            type: 'error'
          })
          return
        }
        else {
          this.filterCarList()
          this.dateInterval = { start_time: missionForm.travel_begin_at, end_time: missionForm.travel_end_at, order_type: this.missionForm.order_type }
          this.carStyleBoxShow = true
        }
      } else if (!a) {
        this.$message({
          showClose: true,
          message: '请选择行程时间',
          type: 'error'
        })
      } else {
        this.$message({
          showClose: true,
          message: '请选择行程时间',
          type: 'error'
        })
      }

    },
    filterCarList () {
      let missionForm = this.missionForm
      let lastTime = missionForm.dateArr[1] // 行程结束时间
      let firstTime = missionForm.dateArr[0]
      let order_cars = [...missionForm.order_cars]
      let compareFun = this.$util.compareDate2
      let arr = []
      if (order_cars[0].begin_at_date) {
        order_cars.forEach((ele, index) => {
          if (compareFun(firstTime, ele.begin_at_date) && compareFun(ele.end_at_date, lastTime)) {
            arr.push(ele)
          }
        })
        if (!arr.length) {
          let outTableParams = this.outTableParams
          let carArr = [{
            driver_id: null,
            driver_name: null,
            driver_mobile: null,
            remark_to_driver: null,
            user_car_id: null,
            car_number: null,
            foreign_money: null,
            collection_money: null,
            begin_at_date: null,
            begin_at_time: null,
            end_at_date: null,
            end_at_time: null
          }]
          if (outTableParams && outTableParams.car_id) {
            carArr[0] = Object.assign({}, carArr[0], outTableParams)
            // 清空outTableParams数据
            this.outTableParams = {}
          }
          this.missionForm = Object.assign({}, this.missionForm, { order_cars: carArr })
        } else {
          missionForm.order_cars = arr
        }
      }
    },
    querySearchComapny (queryString, cb) {
      let params = {
        keywords: queryString
      }
      this.$axios.get(this.$httpUrl.customerListPC, { params: params }).then(res => {
        if (res) {
          cb(res.list)
        }
      })
    },
    handleSelectComapny (item, i) {
      this.missionForm.customer_company = item.company
      this.customer_id = item.customer_id
      this.missionForm.customer_id = item.customer_id
      this.missionForm.customer_name = item.customer_name
      this.missionForm.customer_mobile = item.customer_mobile
    },
    querySearchName (queryString, cb) {
      let params = {
        keywords: queryString,
        customer_id: this.customer_id
      }
      this.$axios.get(this.$httpUrl.keywordsYardman, { params: params }).then(res => {
        if (res) {
          cb(res)
        }
      })
    },
    handleSelectName (item) {
      this.missionForm.customer_name = item.name
      this.missionForm.customer_mobile = item.mobile
    },
    querySearchSalesman (queryString, cb) {
      let params = {
        keywords: queryString
      }
      this.$axios.get(this.$httpUrl.salesmanList, { params: params }).then(res => {
        if (res) {
          cb(res.list)
        }
      })
    },
    handleSelectSalesman (item) {
      this.missionForm.salesman_id = item.salesman_id
      this.temp_salesman_name = item.name
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
    getIndex (index) {
      this.temp_driver_name  = this.missionForm.order_cars[index].driver_name
      this.order_cars_index = index
    },
    getAssistantIndex (index, assistantIndex) {
      this.temp_driver_name = this.missionForm.order_cars[index].driver_name
      this.temp_assistant_name = this.missionForm.order_cars[index].vice_drivers[assistantIndex].driver_name
      this.order_cars_index = index
      this.assistantIndex = assistantIndex
    },
    handleSelectAssistant (item) {
      let obj = {
        driver_id: item.id,
        driver_mobile: item.mobile
      }
      this.temp_assistant_name = item.name
      let order_cars_index = this.order_cars_index
      let assistantIndex = this.assistantIndex
      let carObj = this.missionForm.order_cars[order_cars_index].vice_drivers[assistantIndex]
      let newCarObj = Object.assign({}, carObj, obj)
      this.$set(this.missionForm.order_cars[order_cars_index].vice_drivers, this.assistantIndex, newCarObj)
    },
    handleSelectDriver (item) {
      let obj = {
        driver_id: item.id,
        driver_mobile: item.mobile
      }
      this.temp_driver_name = item.name
      let carObj = this.missionForm.order_cars[this.order_cars_index]
      let newCarObj = Object.assign({}, carObj, obj)
      this.$set(this.missionForm.order_cars, this.order_cars_index, newCarObj)
    },
    rtnSelectedList (obj) {
      this.selectedCarBoxShow = true
      this.arrangeInfo = obj
    },
    ...mapActions([
      "getMessageCondition"
    ]),
    callReleaseAPI (params) {
      if (this.$route.query.backlog_id) {
        params.backlog_id = this.$route.query.backlog_id
      }
      // 根据订单类型过滤相关请求参数
      let order_type = this.missionForm.order_type
      delete params.total_order_cars
      // console.log(this.$refs.shuttleBus.shuuleBusData)
      if (order_type == '2' || order_type == '3') {
        delete params.travel_begin_at
        delete params.travel_end_at
        delete params.travel_begin_at
        delete params.travel_from_lng
        delete params.travel_from_lat
        delete params.travel_from_place
        delete params.travel_content
        delete params.order_cars
      } else {
        delete params.send_order_cars
        delete params.pick_order_cars
        delete params.total_order_cars
      }
      if (order_type == '3') {
        this.missionForm.bus_order_cars = this.$refs.shuttleBus.shuuleBusData
        for (let k = 0; k < this.missionForm.bus_order_cars.length; k++) {
          for (let z = 0; z < this.missionForm.bus_order_cars[k].date_travels.length; z++) {
            if (this.missionForm.bus_order_cars[k].date_travels[z].cycle_dates) {
              this.missionForm.bus_order_cars[k].date_travels[z].cycle_date = this.missionForm.bus_order_cars[k].date_travels[z].cycle_dates
            }
          }
        }

      }
      let order_id = this.order_id
      if (order_id) params.order_id = order_id
      this.$axios.post(this.$httpUrl.orderSave, params).then(res => {
        if (res != false) {
          this.getMessageCondition()
          if (this.$route.query.backlog_id) {
            this.$router.push({ name: 'memo' })
          } else {
            this.$router.go(-1)
          }

        }
      })
    },
    release () {
      this.$refs['releaseForm'].validate((valid) => {
        if (valid) {
          let params = { ...this.missionForm }
          let order_type = this.missionForm.order_type
          if (order_type == 1 || order_type == 2) {
            let travel_begin_at = order_type == 1 ? this.missionForm.travel_begin_at : this.missionForm.pick_order_cars.begin_at_date
            let isNotify = this.$util.compareDate(travel_begin_at)
            if (!isNotify && !this.missionForm.notify_type) {
              this.notifyStyleBoxShow = true
            } else {
              params.notify_type = 4
              this.callReleaseAPI(params)
            }
          } else if (order_type == 3) {
            var busOrderCar = []
            var cyCleTime = []
            var beginTime = []
            var endTime = []
            var travelFrom = []
            var travelTo = []
            console.log(this.missionForm.bus_order_cars)
            for (var i = 0; i < this.missionForm.bus_order_cars.length; i++) {
              if (this.missionForm.bus_order_cars[i].driver_cars.length != 0) {
                for (var j = 0; j < this.missionForm.bus_order_cars[i].driver_cars.length; j++) {
                  if (this.missionForm.bus_order_cars[i].driver_cars[j].car_number == undefined || this.missionForm.bus_order_cars[i].driver_cars[j].car_number == '') {
                    busOrderCar.push(i + 1)
                  }
                  // if(this.missionForm.bus_order_cars[i].driver_cars[j].car_source==2){
                  //   if(this.missionForm.bus_order_cars[i].driver_cars[j].foreign_money==0){
                  //     this.$message('请输入外援费用')
                  //     return
                  //   }
                  // }
                }
              }
              if (this.missionForm.bus_order_cars[i].date_travels.length != 0) {
                for (var g = 0; g < this.missionForm.bus_order_cars[i].date_travels.length; g++) {
                  if (this.missionForm.bus_order_cars[i].date_travels[g].cycle_date.length == 0) {
                    cyCleTime.push(g + 1)
                  }
                  if (this.missionForm.bus_order_cars[i].date_travels[g].begin_at_time == '' || this.missionForm.bus_order_cars[i].date_travels[g].begin_at_time == undefined) {
                    beginTime.push(g + 1)
                  }
                  if (this.missionForm.bus_order_cars[i].date_travels[g].end_at_time == '' || this.missionForm.bus_order_cars[i].date_travels[g].end_at_time == undefined) {
                    endTime.push(g + 1)
                  }
                  if (this.missionForm.bus_order_cars[i].date_travels[g].travels.travel_from_place == '' || this.missionForm.bus_order_cars[i].date_travels[g].travels.travel_from_place == undefined) {
                    travelFrom.push(g + 1)
                  }
                  if (this.missionForm.bus_order_cars[i].date_travels[g].travels.travel_to_place == '' || this.missionForm.bus_order_cars[i].date_travels[g].travels.travel_to_place == undefined) {
                    travelTo.push(g + 1)
                  }
                }
              }
            }
            if (busOrderCar.length) {
              var message = '第' + busOrderCar.join(',') + '行程中车辆为空请选择车辆'
              this.$message(message)
            } else if (beginTime.length) {
              var message = '第' + beginTime.join(',') + '趟开始时间为空'
              this.$message(message)
            } else if (endTime.length) {
              var message = '第' + endTime.join(',') + '趟结束时间为空'
              this.$message(message)
            } else if (cyCleTime.length) {
              var message = '第' + cyCleTime.join(',') + '趟运行周期为空'
              this.$message(message)
            } else if (travelFrom.length) {
              var message = '第' + travelFrom.join(',') + '趟起点为空'
              this.$message(message)
            } else if (travelTo.length) {
              var message = '第' + travelTo.join(',') + '趟终点为空'
              this.$message(message)
            } else{
              params.notify_type = 4
              this.callReleaseAPI(params)
            }
          }
        } else {
          this.$message({
            showClose: true,
            message: '请填写必填信息',
            type: 'error'
          })
        }
      })
    },
    comfirmNotify (val) {
      this.missionForm.notify_type = val
      let params = { ...this.missionForm }
      this.callReleaseAPI(params)
    },
    getOrderDetails () {

      if (this.$route.query.backlog_id) {
        let params = {
          backlog_id: this.$route.query.backlog_id
        }
        this.$axios.get(this.$httpUrl.readyDetails, { params: params }).then(res => {
          if (res != false) {

            let obj = {
              travel_begin_at: res.travel_begin_at,
              travel_end_at: res.travel_begin_at,
              travel_content: res.travel_content,
              money: res.money,
              customer_company: res.customer,
              customer_name: res.customer_yardman_name,
              customer_mobile: res.customer_yardman_mobile,
              remark: res.remark,
              team_num: res.team_num,
              tour_guide_name: res.tour_guide_name,
              tour_guide_mobile: res.tour_guide_mobile,
              travel_from_place: res.travel_from_place,
              travel_from_lng: res.travel_from_lng,
              travel_from_lat: res.travel_from_lat
            }
            this.temp_salesman_name = res.salesman_name || null
            this.missionForm = Object.assign({}, this.missionForm, obj)
          }
        })
      }
    },
    addAssistant (ind) {
      let obj = {
        driver_id: null,
        driver_name: null,
        driver_mobile: null
      }
      this.missionForm.order_cars[ind].vice_drivers.push(obj)
    },
    removeAssistant (ind, driverInd) {
      this.missionForm.order_cars[ind].vice_drivers.splice(driverInd, 1)
    }
  }
}
</script>
<style lang="less" scoped>
@import "./addMission.less";
</style>
<style lang="less">
.addmission {
  .deposit {
    &.is-required {
      .el-form-item__label:before {
        content: "";
      }
    }
  }
  .changeDriver {
    display: block !important;
    .addWidth {
      width: 482px !important;
      .el-textarea__inner {
        height: 49px;
      }
    }
  }
}
</style>

