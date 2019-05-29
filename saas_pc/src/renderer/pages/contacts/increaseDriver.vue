<template>
  <div class="contacts-main increase">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'driverOrigin' }">通讯录</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'driverOrigin' }">司机</el-breadcrumb-item>
          <el-breadcrumb-item>{{typeTxt}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <el-form
        :model="contactsForm"
        ref="contactsForm"
        :rules="contactsRules"
        :label-width="labelWidth"
        class="contacts-form"
      >
        <el-form-item label="姓名" prop="name" class="inline">
          <el-input
            type="text"
            v-model="contactsForm.name"
            placeholder="请输入司机姓名"
            auto-complete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="mobile" class="inline">
          <el-input
            type="text"
            v-model.trim="contactsForm.mobile"
            placeholder="请输入联系方式"
            auto-complete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="驾驶车辆" prop="car_number" v-if="!isChange" class="inline">
          <el-autocomplete
            @blur="carInpBlur"
            placeholder="请输入车牌号选择车辆"
            v-model="contactsForm.car_number"
            :fetch-suggestions="querySearchAsync"
            @select="handleSelect"
          ></el-autocomplete>
        </el-form-item>
        <el-form-item label="驾驶证编号" prop="driving_licence" class="inline">
          <el-input
            type="text"
            v-model="contactsForm.driving_licence"
            placeholder="请输入驾驶证编号"
            auto-complete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="入职时间" prop="join_at" class="inline">
          <el-date-picker
            v-model="contactsForm.join_at"
            type="date"
            placeholder="选择日期"
            :picker-options="pickerOptions1"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="紧急联系人" prop="urgent_name" class="inline">
          <el-input
            type="text"
            v-model="contactsForm.urgent_name"
            placeholder="请输入紧急联系人姓名"
            auto-complete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="urgent_mobile" class="inline">
          <el-input
            type="text"
            v-model.trim="contactsForm.urgent_mobile"
            placeholder="请输入联系方式"
            auto-complete="off"
          ></el-input>
        </el-form-item>
        <div class="custom-box">
          <h3 class="tt">自定义字段</h3>
          <a href="javascript:void(0);" class="btn-tag" @click="addTag" v-if="!isChange">添加自定义字段</a>
          <user-defined-tag
            v-for="(field, index) in contactsForm.definable_fields"
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
        <a href="javascript:void(0);" class="btn" @click="submitForm('contactsForm')">保存</a>
      </div>
    </div>
  </div>
</template>
<script>
import customMixin from '@/assets/mixins/custom-mixins'
export default {
  name: 'increaseDriver',
  mixins: [customMixin],
  data () {
    return {
      labelWidth: '153px',
      typeTxt: null, // 默认添加司机,传type则为修改司机
      isChange: false,
      contactsForm: {
        name: null,
        mobile: null,
        car_number: null,
        driving_licence: null,
        join_at: null,
        urgent_name: null,
        urgent_mobile: null,
        driver_id: 0,
        definable_fields: []
      },
      contactsRules: {
        name: { required: true, message: '请输入司机姓名', trigger: 'blur' },
        mobile: { required: true, message: '请输入司机手机号', trigger: 'blur' },
        car_number: { required: true, message: '请输入驾驶车辆', trigger: 'change' }
      },
      pickerOptions1: {
        disabledDate (time) {
          return time.getTime() >= Date.now()
        }
      }
    }
  },
  created () {
    this.setTypeTxt()
    if (this.$route.query.driver_id) {
      this.getDriverDetail()
    } else {
      this.getFieldList().then(() => {
        this.getStorePageData()
      })
    }
    
  },
  methods: {
        // 获取store中页面数据
    getStorePageData () {
      let getCustomData = this.$store.getters.getCustomData
      if (getCustomData) {
        // 判断是否有新增加的自定义字段，若有，增加刚添加的自定义字段
        let pageCustomData = this.contactsForm.definable_fields
        let definable_fields = [...getCustomData.definable_fields]
        pageCustomData.forEach(e => {
          definable_fields.forEach(j => {
            if (j.field_id == e.field_id) {
              e.value = j.value
              return
            }
          })
        })
        this.contactsForm = Object.assign({}, this.contactsForm, getCustomData,{'definable_fields':pageCustomData})
        this.clearCustomData()
      }
    },
    // 增加自定义标签
    addTag () {
      this.setCustomData({...this.contactsForm})
      this.$router.push({ name: 'addCustom', query: { location_id: this.$locationId.DRIVER_ID } })
    },
    // 获取设置的自定义列表标签
    getFieldList () {
      let params = {
        location_id: this.$locationId.DRIVER_ID
      }
      let p = this.$axios.get(this.$httpUrl.customFiledList, { params: params }).then(res => {
        if (res != false) {
          res.list.map(ele => {
            ele.value = null
          })
          this.contactsForm.definable_fields = res.list
        }
      })
      return p
    },
    setTypeTxt () {
      let isChange = this.$route.query.driver_id
      if (isChange) {
        this.isChange = true
        this.typeTxt = '修改司机'
      } else {
        this.typeTxt = '添加司机'
      }
    },
    querySearchAsync (queryString, cb) {
      let params = {
        keywords: queryString
      }
      this.$axios.get(this.$httpUrl.searchCarList, { params: params }).then(res => {
        cb(res)
      })
    },
    handleSelect (item) {
      this.contactsForm.car_number = item.car_number
      this.contactsForm.car_id = item.id
    },
    getDriverDetail () {
      let driver_id = this.$route.query.driver_id
      if (driver_id) {
        let params = {
          driver_id: driver_id
        }
        this.$axios.get(this.$httpUrl.driverInfo, { params: params }).then(res => {
          if (res != false) {
            this.contactsForm = res
          }
        })
      }
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post(this.$httpUrl.driverSave, this.contactsForm).then(res => {
            if (res != false) {
              this.$message({
                message: this.contactsForm.driver_id > 0 ? '司机修改成功' : '司机添加成功',
                type: 'success'
              })
              this.$router.push({ name: 'driverOrigin' })
            }
          })
        } else {
          return false
        }
      })
    },
    carInpBlur () {
      let contactsForm = this.contactsForm
      if (!contactsForm.driver_id && contactsForm.car_number) {
        Object.assign(this.contactsForm, { car_number: '' })
        this.$message({
          message: "请选择车牌号",
          type: 'info'
        })
      }
    }
  }
}
</script>
<style lang ='less' scoped>
@import "./increase.less";
.contacts-form {
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