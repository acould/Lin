<template>
  <div class="account-main" id="IncreaseOutsideCar">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'customerOrigin' }">外援车队</el-breadcrumb-item>
          <el-breadcrumb-item
            :to="{ name: 'customerDetails',query: {customerType: 2,customer_id:carForm.customer_id} }"
          >{{carForm.companyName}}</el-breadcrumb-item>
          <el-breadcrumb-item>{{typeTxt}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <el-form
        :model="carForm"
        ref="customerForm"
        :rules="carRules"
        :label-width="labelWidth"
        class="customerForm"
      >
        <el-form-item label="单位名称" prop="companyName">
          <div class="inp-wrap">
            <el-input
              type="text"
              :disabled="true"
              v-model="carForm.companyName"
              placeholder="请输入单位名称"
              auto-complete="off"
            ></el-input>
          </div>
        </el-form-item>
        <el-form-item label="车牌号" prop="car_number">
          <div class="inp-wrap">
            <el-input
              type="text"
              v-model="carForm.car_number"
              placeholder="请输入车牌号"
              auto-complete="off"
            ></el-input>
          </div>
        </el-form-item>
        <el-form-item label="车型">
          <div class="inp-wrap">
            <el-input
              type="text"
              v-model="carForm.car_type"
              placeholder="请输入车型"
              auto-complete="off"
            ></el-input>
          </div>
        </el-form-item>
        <el-form-item label="座位数" prop="seat_num">
          <div class="inp-wrap">
            <el-input
              type="text"
              v-model="carForm.seat_num"
              placeholder="请输入座位数"
              auto-complete="off"
            ></el-input>
          </div>
        </el-form-item>
        <el-form-item label="司机姓名" prop="driver_name">
          <div class="inp-wrap">
            <el-autocomplete
              placeholder="请输入司机姓名"
              v-model="carForm.driver_name"
              :fetch-suggestions="querySearchDriver"
              @select="driverSelect"
            ></el-autocomplete>
          </div>
        </el-form-item>
        <el-form-item label="联系方式" prop="driver_mobile">
          <div class="inp-wrap">
            <el-input
              type="text"
              v-model="carForm.driver_mobile"
              placeholder="请输入联系方式"
              auto-complete="off"
            ></el-input>
          </div>
        </el-form-item>
      </el-form>
      <div class="btn-wrap">
        <a href="javascript:void(0);" class="btn" @click="submitForm('customerForm')">提交</a>
        <a href="javascript:void(0);" class="btn btn-nobg" @click="backUrl">取消</a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'IncreaseOperator',
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
      labelWidth: "104px",
      typeTxt: '',
      carForm: {
        car_id: '',
        companyName: '',
        driver_mobile: '',
        seat_num: '',
        car_type: '',
        car_number: '',
        driver_name: '',
        driver_id: '',
        car_source: 2
      },
      carRules: {
        driver_mobile: { required: true, validator: validateMobile, trigger: ['blur', 'change'] },
        driver_name: { required: true, message: '司机姓名', trigger: ['blur', 'change'] },
        seat_num: { required: true, message: '座位数不能为空', trigger: ['blur', 'change'] },
        car_number: { required: true, message: '车牌号不能为空', trigger: ['blur', 'change'] },
        companyName: { required: true, message: '单位名称不能为空', trigger: ['blur', 'change'] }
      },
      templateName: null
    }
  },
  watch: {
    'carForm.driver_name' (newVal) {
      if (newVal == this.templateName) {
        this.carForm.driver_id = null
      }
    }
  },
  created () {
    this.getQueryParams()
  },
  methods: {
    getQueryParams () {
      let query = this.$route.query
      let obj = {}
      let companyName = query.companyName
      if (companyName) obj.companyName = companyName
      let customer_id = query.customer_id
      if (customer_id) obj.customer_id = customer_id
      let car_id = query.car_id
      if (car_id) {
        this.typeTxt = '修改车辆'
        Object.assign(this.carForm, query)
      } else {
        this.typeTxt = '新增车辆'
        Object.assign(this.carForm, obj)
      }
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post(this.$httpUrl.carSave, this.carForm).then(res => {
            if (res != false) {
              this.$message({
                message: this.carForm.car_id > 0 ? '车辆修改成功' : '车辆添加成功',
                type: 'success'
              })
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
    querySearchDriver (queryString, cb) {
      let params = {
        keywords: queryString
      }
      this.$axios.get(this.$httpUrl.searchDriverList, { params: params }).then(res => {
        cb(res)
      })
    },
    driverSelect (item) {
      this.templateName = item.name
      this.carForm.driver_name = item.name
      this.carForm.driver_mobile = item.mobile
      this.carForm.driver_id = item.id
    }
  }
}
</script>
<style lang ="less" scoped>
.account-main {
  height: 100%;
  background-color: #f5f5f5;
}
.customerForm {
  margin: 10px 16px 0;
  padding: 20px 0 10px 0;
  background-color: #fff;
  overflow: hidden;
  .inp-wrap {
    width: 256px;
    overflow: hidden;
  }
  .el-checkbox-group {
    height: 40px;
  }
  & /deep/ .el-radio-group {
    display: inline-block;
    .el-radio + .el-radio {
      margin-left: 10px;
    }
    .el-radio .el-radio__input.is-checked .el-radio__inner {
      background-color: @theme-color;
      border-color: @theme-color;
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
</style>