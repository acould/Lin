<template>
  <div class="account-main" id="IncreaseOperator">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'customerOrigin' }">客户</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'customerDetails',query: {customerType: 1,customer_id:operatorForm.customer_id} }">{{operatorForm.companyName}}</el-breadcrumb-item>
          <el-breadcrumb-item>{{typeTxt}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <el-form :model='operatorForm' ref="customerForm" :rules='operatorRules' :label-width='labelWidth' class='customerForm'>
        <el-form-item label='单位名称' prop='companyName'>
          <div class="inp-wrap">
            <el-input type='text' :disabled="true" v-model='operatorForm.companyName' placeholder='请输入单位名称' auto-complete='off'></el-input>
          </div>
        </el-form-item>
        <el-form-item label='计调姓名' prop='name'>
          <div class="inp-wrap">
            <el-input type='text' :disabled="isModifyInfo" v-model='operatorForm.name' placeholder='请输入计调姓名' auto-complete='off'></el-input>
          </div>
        </el-form-item>
        <el-form-item label='联系方式' prop='mobile'>
          <div class="inp-wrap">
            <el-input type='text' :disabled="isModifyInfo"  v-model.trim='operatorForm.mobile'  placeholder='请输入联系方式' auto-complete='off'></el-input>
          </div>
        </el-form-item>
        <el-form-item label='下单权限' prop='enable_create_order'>
          <div class="inp-wrap">
            <el-radio-group v-model="operatorForm.enable_create_order">
              <el-radio :label="1">具有</el-radio>
              <el-radio :label="-1">不具有</el-radio>
            </el-radio-group>
          </div>
        </el-form-item>
      </el-form>
      <div class='btn-wrap'>
        <a href='javascript:void(0);' class='btn' @click="submitForm('customerForm')">提交</a>
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
      operatorForm: {
        customer_id: '',
        name: '',
        mobile: '',
        enable_create_order: 1,
        customer_yardman_id: ''
      },
      operatorRules: {
        mobile: { required: true, validator: validateMobile, trigger: ['blur', 'change'] },
        name: { required: true, message: '计调姓名不能为空', trigger: ['blur', 'change'] },
        companyName: { required: true, message: '单位名称不能为空', trigger: ['blur', 'change'] },
        enable_create_order: { required: true, message: '下单权限不能为空', trigger: ['blur', 'change'] },
      }
    }
  },
  computed: {
    isModifyInfo () {
      return this.operatorForm.customer_yardman_id ? true : false
    }    
  },
  watch: {

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
      let customer_yardman_id = query.customer_yardman_id
      if (customer_yardman_id) {
        this.typeTxt = '修改计调'
        Object.assign(this.operatorForm, query)
      } else {
        this.typeTxt = '添加计调'
        Object.assign(this.operatorForm, obj)
      }    
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post(this.$httpUrl.yardmenSave, this.operatorForm).then(res => {
            if (res != false) {
              this.$message({
                message: this.operatorForm.customer_id > 0 ? '计调修改成功' : '计调添加成功',
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