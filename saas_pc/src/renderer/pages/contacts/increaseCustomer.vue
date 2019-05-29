<template>
  <div class="contacts-main increase">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right" v-if="!customer_id">
          <el-breadcrumb-item :to="{ name: 'customerOrigin' }">通讯录</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'customerOrigin' }">客户</el-breadcrumb-item>
          <el-breadcrumb-item>新增客户</el-breadcrumb-item>
        </el-breadcrumb>
        <el-breadcrumb separator-class="el-icon-arrow-right" v-else>
          <el-breadcrumb-item
            :to="{ name: 'customerOrigin',query: queryParams}"
          >{{customerType == 1 ? '客户' : '外援车队'}}</el-breadcrumb-item>
          <el-breadcrumb-item
            :to="{ name: 'customerDetails',query: queryParams}"
          >{{customerForm.company}}</el-breadcrumb-item>
          <el-breadcrumb-item>修改信息</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <el-form
        :model="customerForm"
        ref="customerForm"
        :rules="customerRules"
        :label-width="labelWidth"
        class="customerForm"
      >
        <el-form-item label="单位名称" prop="company" class="inline">
          <div class="inp-wrap">
            <el-input
              type="text"
              v-model="customerForm.company"
              placeholder="请输入单位名称"
              auto-complete="off"
            ></el-input>
          </div>
        </el-form-item>
        <el-form-item label="发票抬头" prop="invoice_title" class="inline">
          <div class="inp-wrap">
            <el-input
              type="text"
              v-model="customerForm.invoice_title"
              placeholder="请输入发票抬头"
              auto-complete="off"
            ></el-input>
          </div>
        </el-form-item>
        <el-form-item label="计调姓名" prop="name" class="inline">
          <div class="inp-wrap">
            <el-input
              type="text"
              v-model="customerForm.name"
              placeholder="请输入计调姓名"
              auto-complete="off"
            ></el-input>
          </div>
        </el-form-item>
        <el-form-item label="联系方式" prop="mobile" class="inline">
          <div class="inp-wrap">
            <el-input
              type="text"
              v-model.trim="customerForm.mobile"
              placeholder="请输入联系方式"
              auto-complete="off"
            ></el-input>
          </div>
        </el-form-item>
        <el-form-item label="客户身份" prop="type" class="inline">
          <el-checkbox-group v-model="customerForm.type">
            <el-checkbox :label="1">客户</el-checkbox>
            <el-checkbox :label="2">外援车队</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="税号" prop="tax_number" class="inline">
          <div class="inp-wrap">
            <el-input
              type="text"
              v-model="customerForm.tax_number"
              placeholder="请输入税号"
              auto-complete="off"
            ></el-input>
          </div>
        </el-form-item>
        <el-form-item label="联系地址" prop="address" class="inline address">
          <div class="inp-wrap">
            <el-input
              type="textarea"
              v-model="customerForm.address"
              placeholder="请输入联系地址"
              auto-complete="off"
            ></el-input>
          </div>
        </el-form-item>
        <el-form-item
          v-if="customerForm.type.length && customerForm.type.includes(1)== 1"
          label="查看轨迹权限"
          prop="enable_see_history"
          class="inline"
        >
          <el-radio-group v-model="customerForm.enable_see_history">
            <el-radio :label="1">允许</el-radio>
            <el-radio :label="0">拒绝</el-radio>
          </el-radio-group>
        </el-form-item>
        <div class="custom-box">
          <h3 class="tt">自定义字段</h3>
          <a href="javascript:void(0);" class="btn-tag" @click="addTag" v-if="!customer_id">添加自定义字段</a>
          <user-defined-tag
            v-for="(field, index) in customerForm.definable_fields"
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
      <div class="btn-wrap">
        <a href="javascript:void(0);" class="btn" @click="submitForm('customerForm')">提交</a>
        <a href="javascript:void(0);" class="btn btn-nobg" @click="backUrl">取消</a>
      </div>
    </div>
  </div>
</template>
<script>
import customMixin from '@/assets/mixins/custom-mixins'
export default {
  name: 'increaseCustomer',
  mixins: [customMixin],
  data () {
    // else if (!reg.test(value)) {
    //     callback(new Error('手机号格式错误'))
    //   }
    var validateMobile = (rule, value, callback) => {
      let reg = /^[1]\d{10}$/
      if (!value) {
        callback(new Error('手机号不能为空'))
      } else {
        callback()
      }
    }
    return {
      typeTxt: null, // 默认添加客户,传type则为修改客户
      labelWidth: '100px',
      customerType: '',
      customer_id: null,
      customerForm: {
        customer_id: '',
        company: '',
        name: '',
        mobile: '',
        type: [],
        tax_number: '',
        invoice_title: '',
        address: '',
        enable_see_history: 0,
        definable_fields: []
      },
      customerRules: {
        company: { required: true, message: '单位名称不能为空', trigger: ['blur', 'change'] },
        mobile: { required: true, validator: validateMobile, trigger: ['blur', 'change'] },
        name: { required: true, message: '计调姓名不能为空', trigger: ['blur', 'change'] },
        type: { type: 'array', required: true, message: '至少选择一个客户身份', trigger: ['blur', 'change'] }
      }
    }
  },
  watch: {
    'customerForm.type' (newVal) {
      if (newVal.length == 0 || (newVal.length <= 1 && newVal.includes(2))) {
        this.customerForm.enable_see_history = 0
      }
    }
  },
  created () {
    this.setTypeTxt()
    if (this.$route.query.customer_id) {
      this.getCustomerDetail()
    } else {
      this.getFieldList().then(() => {
        this.getStorePageData()
      })
    }
  },
  computed: {
    queryParams () {
      return { customerType: this.customerType, customer_id: this.customer_id }
    }
  },
  methods: {
    // 获取store中页面数据
    getStorePageData () {
      let getCustomData = this.$store.getters.getCustomData
      if (getCustomData) {
        // 判断是否有新增加的自定义字段，若有，增加刚添加的自定义字段
        let pageCustomData = this.customerForm.definable_fields
        let definable_fields = [...getCustomData.definable_fields]
        pageCustomData.forEach(e => {
          definable_fields.forEach(j => {
            if (j.field_id == e.field_id) {
              e.value = j.value
              return
            }
          })
        })
        this.customerForm = Object.assign({}, this.customerForm, getCustomData,{'definable_fields':pageCustomData})
        this.clearCustomData()
      }
    },
    // 增加自定义标签
    addTag () {
      this.setCustomData({...this.customerForm})
      this.$router.push({ name: 'addCustom', query: { location_id: this.$locationId.CUSTOMER_ID } })
    },
    // 获取设置的自定义列表标签
    getFieldList () {
      let params = {
        location_id: this.$locationId.CUSTOMER_ID
      }
      let p = this.$axios.get(this.$httpUrl.customFiledList, { params: params }).then(res => {
        if (res != false) {
          res.list.map(ele => {
            ele.value = null
          })
          this.customerForm.definable_fields = res.list
        }
      })
      return p
    },
    setTypeTxt () {
      let query = this.$route.query
      let isChange = this.customer_id = query.customer_id
      if (isChange) {
        this.typeTxt = '修改客户'
      } else {
        this.typeTxt = '添加客户'
      }
      let customerType = this.customerType = query.customerType
      if (customerType) this.customerForm.type = [Number.parseInt(customerType)]
    },
    getCustomerDetail () {
      let customer_id = this.$route.query.customer_id
      if (customer_id) {
        let params = {
          customer_id: customer_id
        }
        this.$axios.get(this.$httpUrl.customerInfo, { params: params }).then(res => {
          if (res != false) {
            let type = res.type === 3 ? [1, 2] : [res.type]
            Object.assign(this.customerForm, res, { type })
          }
        })
      }
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let type = this.customerForm.type
          type = type.length === 2 ? 3 : type.join('')
          let params = {}
          Object.assign(params, this.customerForm, { type: type })
          this.$axios.post(this.$httpUrl.customerSave, params).then(res => {
            if (res != false) {
              this.$message({
                message: this.customerForm.customer_id > 0 ? '客户修改成功' : '客户添加成功',
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
      let customer_id = this.customerForm.customer_id
      let customerType = this.$route.query.customerType
      if (customer_id) {
        this.$router.go(-1)
      } else {
        this.$router.push({ name: 'customerOrigin', query: { customerType: customerType } })
      }
    }
  }
}
</script>
<style lang ="less" scoped>
@import "./increase.less";
.customerForm {
  margin: 10px 16px 0;
  padding: 20px 0 53px 0;
  background-color: #fff;
  overflow: hidden;
  .inline {
    float: left;
    width: 50%;
  }
  .inp-wrap {
    width: 256px;
    overflow: hidden;
  }
  .el-checkbox-group {
    height: 40px;
  }
  .custom-box {
    border-top: 10px solid #f5f5f5;
    clear: both;
    position: relative;
    .tt {
      padding-left: 33px;
      font-size: 14px;
      color: #333;
      height: 44px;
      line-height: 44px;
      border-bottom: 1px solid #f5f5f5;
      margin-bottom: 15px;
    }
    .btn-tag {
      position: absolute;
      right: 30px;
      top: 15px;
      font-size: 18px;
      color: @theme-color;
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
