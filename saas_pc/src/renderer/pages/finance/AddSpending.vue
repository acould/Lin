<template>
  <div>
    <div class='main-header'>
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>财务管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'spending'}">支出</el-breadcrumb-item>
          <el-breadcrumb-item>新增支出</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div id="main-content-addspending">
      <div class="form">
        <el-form :model="formParams" :rules="formRules" ref="releaseForm" :label-width="labelWidth" class="mission-form">
          <el-form-item label="关联订单" prop="dateArr" class="inline-item inline-item-iconfont">
            <div class="inp-wrap">
              <el-input v-model="formParams.order_num" :disabled="true" placeholder="请选择订单"></el-input>
              <span class="iconfont icon-tubiaolunkuo-" @click="selectOrder" v-if="!formParams.id"></span>
            </div>
          </el-form-item>
          <el-form-item label="使用车辆" prop="car_number" class="inline-item inline-item-iconfont">
            <div class="inp-wrap">
              <el-input v-model="formParams.car_number" :disabled="true" placeholder="请输入车牌号"></el-input>
              <span class="iconfont icon-che" v-if="!hasOrderId" @click="selectCar"></span>
            </div>
          </el-form-item>
          <el-form-item v-if="hasOrderId" label="使用日期" prop="begin_at" class="inline-item">
            <div class="inp-wrap">
              <el-input v-model="formParams.begin_at" :disabled="hasOrderId" placeholder="请输入车牌号"></el-input>
            </div>
          </el-form-item>
          <el-form-item v-if="!hasOrderId" label="使用日期" prop="begin_at" class="inline-item">
            <div class="inp-wrap">
              <!-- <el-date-picker :picker-options="pickerOptions1" v-model="formParams.begin_at" type="date" placeholder="使用日期" value-format="yyyy-MM-dd">
              </el-date-picker> -->
              <!-- 解除时间限制 -->
              <el-date-picker v-model="formParams.begin_at" type="date" placeholder="使用日期" value-format="yyyy-MM-dd">
              </el-date-picker>
            </div>
          </el-form-item>
          <el-form-item label="过路费" prop="toll_money" class="inline-item">
            <div class="inp-wrap">
              <el-input v-model.number="formParams.toll_money" placeholder="请输入过路费总价"></el-input>
            </div>
          </el-form-item>
          <el-form-item label="停车费" prop="park_money" class="inline-item">
            <div class="inp-wrap">
              <el-input v-model.number="formParams.park_money" placeholder="请输入停车总价"></el-input>
            </div>
          </el-form-item>
          <el-form-item label="餐费" prop="meal_money" class="inline-item">
            <div class="inp-wrap">
              <el-input v-model.number="formParams.meal_money" placeholder="请输入餐费总价"></el-input>
            </div>
          </el-form-item>
          <el-form-item label="油费" prop="oil_money" class="inline-item">
            <div class="inp-wrap">
              <el-input v-model.number="formParams.oil_money" placeholder="请输入油费总价"></el-input>
            </div>
          </el-form-item>
          <el-form-item label="水费" prop="water_money" class="inline-item">
            <div class="inp-wrap">
              <el-input v-model.number="formParams.water_money" placeholder="请输入水费总价"></el-input>
            </div>
          </el-form-item>
          <el-form-item label="住宿费" prop="hotel_money" class="inline-item">
            <div class="inp-wrap">
              <el-input v-model.number="formParams.hotel_money" placeholder="请输入住宿费总价"></el-input>
            </div>
          </el-form-item>
          <el-form-item label="维修" prop="repair_money" class="inline-item">
            <div class="inp-wrap">
              <el-input v-model.number="formParams.repair_money" placeholder="请输入维修总价"></el-input>
            </div>
          </el-form-item>
          <el-form-item label="保养" prop="maintain_money" class="inline-item">
            <div class="inp-wrap">
              <el-input v-model.number="formParams.maintain_money" placeholder="请输入保养费总价"></el-input>
            </div>
          </el-form-item>
          <el-form-item label="其他费用" prop="other_money" class="inline-item">
            <div class="inp-wrap">
              <el-input v-model.number="formParams.other_money" placeholder="请输入其他费用总价"></el-input>
            </div>
          </el-form-item>
          <el-form-item label="备注" prop="remark" class="inline-item">
            <div class="inp-wrap">
              <el-input v-model="formParams.remark"></el-input>
            </div>
          </el-form-item>
          <el-form-item label="合计" prop="dateArr" class="inline-item">
            <p>
              <span>{{totalMoney}}</span>
              元
            </p>
          </el-form-item>
        </el-form>
      </div>
      <div id="main-content-addspending">
        <el-form :label-width='definedWidth'>
          <div class="car-form">
            <p class="definedHeader"><span>自定义字段</span> <a href="javascript:void(0);" class="btn-tag" @click="addTag" >添加自定义字段</a></p>
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
          </div>
        </el-form>
      </div>
      <div class="image-box">
        <h3>费用凭据
          <span>最多上传10张</span>
        </h3>
        <div class="image-wrap">
          <div class="upload-wrap">
            <div class="upload-image" v-for="(src, index) in images" :key="index">
              <span class="iconfont icon-htmal5icon19 iconfont-close" @click="removeUploadImage(index)"></span>
              <img @click="checkImg(src)" :src="src" alt="">
            </div>
            <el-upload class="avatar-uploader" :http-request="uploadImage" :action="''" :multiple="true" :show-file-list="false">
              <div class="upload-btn">
                <span class="iconfont icon-close iconfont-close"></span>
                <span class="upload-sign">添加费用凭据</span>
              </div>
            </el-upload>
          </div>
        </div>
      </div>
      <div class='btn-wrap'>
        <a href='javascript:void(0);' class='btn' @click="submitForm('releaseForm')">提交</a>
        <a href="javascript:void(0);" class="btn btn-nobg" @click="backUrl">取消</a>
      </div>
    </div>
    <imgDialog ref="imgDialog" :imgShow.sync='imgShow' :imgs='imgs'></imgDialog> 
  </div>
</template>
<script>
import imgDialog from '@/components/imgDialog'
import { mapActions, mapState } from 'vuex'
import customMixin from '@/assets/mixins/custom-mixins'
export default {
  name: 'AddSpending',
  mixins: [customMixin],
  data () {
    var validateMobile = (rule, value, callback) => {
      let reg = /^[1]\d{10}$/
      if (!value) {
        callback(new Error('手机号不能为空'))
      } else if (!reg.test(value)) {
        callback(new Error('手机号格式错误'))
      } else {
        callback()
      }
    }
    return {
      labelWidth: '90px',
      definedWidth:'153px',
      formParams: {
        id: '',
        begin_at: '', //使用开始时间（关联订单时不传）
        order_num: '',
        saas_order_id: '', //saas订单id
        car_number: '', // 车牌号（关联订单时不传）
        saas_car_id: '', // 车辆id（关联订单时不传）
        park_money: null,
        toll_money: null,
        oil_money: null,
        meal_money: null,
        hotel_money: null,
        water_money: null,
        maintain_money: null,
        repair_money: null,
        other_money: null,
        current_km: '',
        remark: '',
        images: '',
        
      },
      definable_fields:[],
      formRules: {
        begin_at: { required: true, message: '使用日期不能为空', trigger: ['blur', 'change'] },
        car_number: { required: true, message: '使用车辆不能为空', trigger: ['blur', 'change'] },
      },
      images: [],
      imgLen: 0,
      isUploading: false, // 图片是否正在上传
       imgShow:false,
      imgs:''
      // pickerOptions1: {
      //   disabledDate (time) {
      //     return time.getTime() >= Date.now()
      //   }
      // }
    }
  },
  watch: {
    images (newVal) {
      this.formParams.images = newVal.join(",")
    }
  },
  beforeRouteLeave (to, from, next) {
    if (to.name != 'SpendingCar' && to.name != 'SpendingOrder') {
      this.refreshSpendingInfo(null)
    }
    next()
  },
  computed: {
    totalMoney () {
      if(!this.$route.query.useTime){
        let obj = this.formParams
        let money = 0
        for (var i in obj) {
          if (typeof obj[i] !== 'string' && obj[i] !== null) {
            money += obj[i]
          }
        }

        return money
      }
      
    },
    ...mapState({
      spendingInfo: state => state.finance.spendingInfo
    }),
    hasOrderId () {
      return this.formParams.saas_order_id || this.$route.query.useTime ? true : false
    }
  },
  created () {
    this.getQueryParams()
    if(this.formParams.id){
      this.getSpendingInfo()
    }else{
      this.getFieldList()
    }
    
    this.getGlobalData()
  },
  mounted () {
    if(this.$route.query.useTime){
      this.formParams.order_num = this.$route.query.order_num
      this.formParams.car_number = this.$route.query.carNumber
      this.formParams.begin_at =this.$route.query.useTime
      this.formParams.saas_order_id = this.$route.query.order_id
    }
  },
  methods: {
    ...mapActions([
      'setSpendingInfo',
      'refreshSpendingInfo'
    ]),
    selectOrder () {
      this.setSpendingInfo(this.formParams)
      this.$router.push({ name: 'SpendingOrder' })
    },
    addTag () {
      this.setCustomData(this.definable_fields)
      this.$router.push({ name: 'addCustom', query: { location_id: this.$locationId.SPENDING_ID } })
    },
     getFieldList () {
      let params = {
        location_id: this.$locationId.SPENDING_ID
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
    getSpendingInfo () {
      let params, url
      let id = this.formParams.id
      if (!id) return
      params = { id: this.formParams.id }
      url = this.$httpUrl.feeDetail
      this.$axios.post(url, params).then(res => {
        if (res) {
          let formParams = {
            id: res.fee_id.toString(),
            begin_at: '',
            order_num: res.order_num,
            saas_order_id: res.saas_order_id.toString(),
            car_number: res.car_number,
            saas_car_id: res.saas_car_id.toString(),
            park_money: Number.parseFloat(res.park_money),
            toll_money: Number.parseFloat(res.toll_money),
            oil_money: Number.parseFloat(res.oil_money),
            meal_money: Number.parseFloat(res.meal_money),
            hotel_money: Number.parseFloat(res.hotel_money),
            water_money: Number.parseFloat(res.water_money),
            maintain_money: Number.parseFloat(res.maintain_money),
            repair_money: Number.parseFloat(res.repair_money),
            other_money: Number.parseFloat(res.other_money),
            current_km: '',
            remark: res.audit_remark
          }
          this.definable_fields = res.definable_fields
          if (res.saas_order_id > 0) {
            formParams.begin_at = res.begin_at + '-' + res.end_at
          } else {
            formParams.begin_at = res.begin_at
          }
          let imageStr = res.images.join(",")
          this.formParams.images = imageStr
          this.images = res.images
          this.imgLen = res.images.length
          Object.assign(this.formParams, formParams)
        }
      })
    },
    getQueryParams () {
      let query = this.$route.query
      if (query.fee_id) this.formParams.id = query.fee_id
    },
    selectCar () {
      this.setSpendingInfo(this.formParams)
      this.$router.push({ name: 'SpendingCar' })
    },
    getGlobalData () {
      let spendingInfo = this.spendingInfo
      if (spendingInfo) {
        this.formParams = { ...this.spendingInfo }
        let images = spendingInfo.images
        if (images) {
          this.images = images.split(',')
        }
      }
    },
    // 上传图片，换取图片地址
    uploadImage (r) {
      if (this.imgLen >= 10) {
        this.$message({
          message: '上传凭据最多上传10张',
          type: 'error'
        })
        return
      }
      this.imgLen++
      this.isUploading = true
      let formData = new FormData()
      let client;
      if (process.env.IS_WEB) {
        client = 'saas_pc'
      } else {
        client = 'saas_windows'
      }
      formData.append('files', r.file)
      formData.append('client', client)
      this.$axios.defaults.headers['Content-Type'] = 'multipart/form-data'
      let loading = this.$loading({ text: 'Loading' })
      this.$axios.post(this.$httpUrl.uploadImage, formData).then(res => {
        loading.close()
        if (res) {
          this.isUploading = false
          this.images.push(res.files)
        }
      })
    },
    removeUploadImage (imgIndex) {
      this.images.splice(imgIndex, 1)
      this.imgLen--
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.isUploading) {
            this.$message({
              message: '图片正在上传中,请稍等',
              type: 'error'
            })
            return
          }
          this.formParams.definable_fields = this.definable_fields
          console.log(this.formParams)
          this.$axios.post(this.$httpUrl.feeSave, this.formParams).then(res => {
            if (res != false) {
              this.$message({
                message: this.formParams.id > 0 ? '支出修改成功' : '支出添加成功',
                type: 'success'
              })
              this.refreshSpendingInfo(null)
              this.backUrl()
            }
          })
        } else {
          return false
        }
      })
    },
    backUrl () {
      this.$router.go(-1)
    },
    checkImg(img){
    //  console.log(img)
     this.imgShow =true
     this.$nextTick(()=>{
       this.$refs.imgDialog.showToggle(img)
     })
   }
  },
  components: {
    imgDialog
  }
}
</script>
<style lang="less">
@imgWidth: 176px;
@imgHeight: 103px;
#main-content-addspending {
  .form {
    margin: 15px 16px 0 16px;
    background-color: #fff;
    overflow: hidden;
    padding-top: 24px;
    .inline-item {
      width: 50%;
      float: left;
      box-sizing: border-box;
      position: relative;
      .inp-wrap {
        margin-right: 20px;
        .el-date-editor {
          width: 100% !important;
        }
        .el-input {
          width: 100%;
        }
      }
      &.inline-item-iconfont {
        padding-right: 40px;
        .iconfont {
          position: absolute;
          top: 0;
          right: -22px;
          color: #666;
          font-size: 24px;
          z-index: 9;
          cursor: pointer;
        }
      }
    }
  }
  .image-box {
    margin: 18px 16px 0 16px;
    background-color: #fff;
    h3 {
      height: 54px;
      line-height: 54px;
      border-bottom: 1px solid #e9e9e9;
      box-sizing: border-box;
      font-weight: bold;
      padding-left: 30px;
      span {
        font-size: 12px;
        color: #333;
        margin-left: 10px;
      }
    }
    .image-wrap {
      margin: 25px 20px 0 20px;
      padding-bottom: 10px;
      overflow: hidden;
      .inp {
        width: 173px;
        & /deep/ .el-input__inner {
          height: 32px;
          line-height: 32px;
        }
      }
      .avatar-uploader {
        float: left;
        margin-right: 20px;
      }
      .upload-btn {
        width: @imgWidth;
        height: @imgHeight;
        line-height: @imgHeight;
        text-align: center;
        font-size: @fontsize-medium;
        color: @color-light-grey;
        background-color: #fff;
        border: 1px dashed #dadada;
        margin-bottom: 15px;
        .iconfont-close {
          transform: rotate(45deg);
          display: inline-block;
        }
      }
      .upload-image {
        float: left;
        position: relative;
        img {
          width: @imgWidth;
          height: @imgHeight;
          margin-right: 12px;
          margin-bottom: 15px;
        }
        .iconfont-close {
          position: absolute;
          top: -8px;
          right: 10px;
          font-size: 24px;
          color: #666;
          padding: 10px;
          cursor: pointer;
        }
      }
    }
  }
  .btn-wrap {
    margin-top: 55px;
    text-align: center;
    .btn {
      display: inline-block;
      width: 154px;
      height: 36px;
      line-height: 36px;
      margin-right: 30px;
      &:last-of-type {
        margin-right: 0;
      }
    }
  }
  .car-form{
    background: #ffffff;
    margin: 15px 16px 0 16px;
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
}
</style>