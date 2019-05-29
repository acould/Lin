<template>
  <div class='contacts-main increase'>
    <div class='main-header'>
      <div class='breadcrumb-wrap'>
        <div class='back' @click='$router.go(-1)'>
          <span class='iconfont icon-jiantou2'></span>
          返回
        </div>
        <el-breadcrumb separator-class='el-icon-arrow-right'>
          <el-breadcrumb-item :to="{ name: 'carOrigin',query: {type: carForm.car_source} }">车辆管理</el-breadcrumb-item>
          <el-breadcrumb-item>{{typeTxt}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class='main-content'>
      <el-form :model='carForm' :rules='carRules' ref='carForm' :label-width='labelWidth' class='contacts-form'>
        <el-form-item label='车辆品牌' prop='car_type'  class="carNumber">
          <el-input type='text' v-model='carForm.car_type' placeholder='请输入品牌信息' auto-complete='off'></el-input>
        </el-form-item>
        
        <el-form-item label='车牌号' prop='car_number' class="carNumber">
          <el-input type='text' v-model='carForm.car_number' placeholder='请输入车牌号' auto-complete='off'></el-input>
        </el-form-item>
        <el-form-item label='座位数' prop='seat_num'  class="carNumber">
          <el-input type='text' v-model='carForm.seat_num' placeholder='请输入车辆座位' auto-complete='off'></el-input>
        </el-form-item>
        <el-form-item label="类型"  class="carNumber" prop="car_source">
          <el-radio-group v-model="carForm.car_source">
            <el-radio label="1">自有车辆</el-radio>
            <el-radio label="2">外援车辆</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label='GPS设备号' prop='imei' v-if="carForm.car_source==1" >
          <el-input type='text' v-model='carForm.imei' placeholder='请输入GPS设备号(GPS)底部' auto-complete='off'></el-input>
        </el-form-item>
        <!-- <el-form-item label='GPS卡号' prop='gps_mobile'>
          <el-input type='text' v-model='carForm.gps_mobile' placeholder='请输入GPS卡号' auto-complete='off'></el-input>
        </el-form-item> -->
        <el-form-item label='车队名称' prop='out_company' v-if="carForm.car_source == 2"  class="carNumber">
          <el-autocomplete placeholder='请输入车队名称' v-model='carForm.out_company' :hide-loading="true" :fetch-suggestions='querySearchFleet' @select='fleetSelect'></el-autocomplete>
        </el-form-item>
        <el-form-item label='司机姓名' prop='driver_name'  class="carNumber">
          <el-autocomplete placeholder='请输入司机姓名' v-model='carForm.driver_name' :fetch-suggestions='querySearchDriver' @select='driverSelect'></el-autocomplete>
        </el-form-item>
        <el-form-item label='联系方式' prop='driver_mobile'  class="carNumber">
          <el-input type='text' v-model.trim='carForm.driver_mobile' placeholder='请输入手机号' auto-complete='off'></el-input>
        </el-form-item>
        <el-form-item label='保险时间' prop='next_insurance_at' class="carNumber" v-if="carForm.car_source == 1">
          <el-date-picker type='date'  v-model='carForm.next_insurance_at' placeholder='请输入保险时间' auto-complete='off' value-format="yyyy-MM-dd"></el-date-picker>
        </el-form-item>
        <el-form-item label='保养时间' prop='next_maintain_at' class="carNumber" v-if="carForm.car_source == 1">
          <el-date-picker type='date' v-model='carForm.next_maintain_at' placeholder='请输入保养时间' auto-complete='off' value-format="yyyy-MM-dd"></el-date-picker>
        </el-form-item>
        <el-form-item label='年审时间' prop='join_at' class="carNumber" v-if="carForm.car_source == 1">
          <el-date-picker type='date'  v-model='carForm.next_annual_audit_at' placeholder='请输入年审时间' auto-complete='off' value-format="yyyy-MM-dd"></el-date-picker>
        </el-form-item>
        <el-form-item label='公里数' prop='run_km' class="carNumber" v-if="carForm.car_source == 1">
          <el-input type='text' v-model='carForm.run_km' placeholder='请输入当前公里数' auto-complete='off'></el-input>
        </el-form-item>

          <div class="car-form">
            <p class="definedHeader"><span>自定义字段</span> <a href="javascript:void(0);" class="btn-tag" @click="addTag" >添加自定义字段</a></p>
              <user-defined-tag
                v-for="(field, index) in carForm.definable_fields"
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
        <el-form-item>
          <div class='btn-wrap'>
            <a href='javascript:void(0);' @click="submitForm('carForm')" class='btn'>保存</a>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import customMixin from '@/assets/mixins/custom-mixins'
export default {
  name: 'increaseCar',
   mixins: [customMixin],
  data () {
    var validateMobile = (rule, value, callback) => {
      let reg = /^[1]\d{10}$/
      if (!reg.test(value)) {
        callback(new Error('手机号格式错误'))
      } else {
        callback()
      }
    }
    var validateSeats = (rule, value, callback) => {
      let reg = /^[1-9]\d*$/
      if (!reg.test(value)) {
        callback(new Error('请输入正确数字'))
      } else {
        callback()
      }
    }
    return {
      labelWidth: '153px',
      typeTxt: null, // 默认添加司机,传type则为修改司机
      carForm: {
        car_type: '',
        car_number: '',
        seat_num: '',
        car_source: '1',
        imei: null,
        out_company: '',
        driver_name: '',
        driver_mobile: '',
        next_insurance_at: '',
        next_maintain_at: '',
        next_annual_audit_at: '',
        customer_id:'',
        car_id: 0,
        driver_id: 0,
        definable_fields:[]
      },
      carRules: {
        car_type: { required: true, message: '请输入车辆品牌', trigger: 'blur' },
        car_number: { required: true, message: '请输入车牌号', trigger: 'blur' },
        seat_num: [
          { required: true, message: '请输入车辆座位数', trigger: ['blur','change'] },
          { required: true, validator: validateSeats, trigger: ['blur','change'] }
        ],
        car_source: { required: true, message: '请选择车辆类型', trigger: 'change' },
        out_company: { required: true, message: '请输入外援车队名称', trigger: 'change' },
        driver_name: { required: true, message: '请输入司机姓名', trigger: 'change' },
        driver_mobile: { required: true, validator: validateMobile, trigger: 'blur' }
      }
    }
  },
  created () {
    this._initPage() 
    if(this.$route.query.car_id){
      this.getCarDetail()
    }else{
      this.getFieldList()
    }
    
  },
  methods: {
    ...mapActions([
      "getMessageCondition"
    ]),
    _initPage () {
      // 新增修改 文字
      let car_id = this.$route.query.car_id
      if (car_id) {
        this.typeTxt = '修改车辆'
      } else {
        this.typeTxt = '添加车辆'
      }
      // 车辆类型
      let car_source = this.$route.query.car_source
      if (car_source) {
        this.carForm.car_source = car_source
      }
    },
    querySearchFleet (queryString, cb) {
      let params = {
        keywords: queryString
      }
      this.$axios.get(this.$httpUrl.searchFleetList, { params: params }).then(res => {
        cb(res)

      })
    },
    addTag () {
      this.setCustomData(this.carForm)
      this.$router.push({ name: 'addCustom', query: { location_id: this.$locationId.CAR_ID } })
    },
    // 获取设置的自定义列表标签
    getFieldList () {
      let params = {
        location_id: this.$locationId.CAR_ID
      }
      let p = this.$axios.get(this.$httpUrl.customFiledList, { params: params }).then(res => {
        if (res != false) {
          res.list.map(ele => {
            ele.value = null
          })
          this.carForm.definable_fields = res.list
        }
      })
      return p
    },
    fleetSelect (item) {
      console.log(item)
      this.carForm.out_company = item.title
      this.carForm.customer_id = item.id
    },
    querySearchDriver (queryString, cb) {
      let params = {
        keywords: queryString
      }
      this.$axios.get(this.$httpUrl.searchDriverList, { params: params }).then(res => {
        console.log(res)
        cb(res)
      })
    },
    driverSelect (item) {
      this.carForm.driver_name = item.name
      this.carForm.driver_mobile = item.mobile
      this.carForm.driver_id = item.id
    },
    getCarDetail () {
      let car_id = this.$route.query.car_id
      if (car_id) {
        let params = {
          car_id: car_id
        }
        this.$axios.get(this.$httpUrl.carInfo, { params: params }).then(res => {
          if (res != false) {
            res.car_source = res.car_source.toString()
            this.carForm = res
          }
        })
      }
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.carForm)
          this.$axios.post(this.$httpUrl.carSave, this.carForm).then(res => {
            if (res != false) {
              this.$message({
                message: this.carForm.car_id > 0 ? '车辆修改成功' : '车辆添加成功',
                type: 'success'
              })
              this.getMessageCondition()
              this.$router.push({ name: 'carOrigin', query: { type: this.carForm.car_source } })
            }
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>
<style lang ='less' scoped>
@import '../contacts/increase.less';
.increase{
  .contacts-form{
    .carNumber{
      width: 49%;
      display: inline-block;
    }
  }
  
  .car-form{
    background: #FFFFFF;
   .userdefined-container{
     width:50%;
     display: inline-block;
   }
  }
  .definedHeader{
    line-height: 45px;
    border-bottom: 1px solid #E9E9E9;
    span{
      margin-left: 80px;
    }
    .btn-tag{
      float: right;
      margin-right: 31px;
      color: #0DB592;
      position: relative;
    }
  }
  
}

</style>