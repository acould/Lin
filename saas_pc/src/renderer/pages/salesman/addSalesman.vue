<template>
  <div class='contacts-main increase'>
    <div class='main-header'>
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>{{typeTxt}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class='main-content'>
      <el-form :model='contactsForm' ref="contactsForm" :rules='contactsRules' :label-width='labelWidth' class='contacts-form'>
        <el-form-item label='业务员姓名' prop='name'>
          <el-input type='text' v-model='contactsForm.name' placeholder='请输入业务员姓名' auto-complete='off'></el-input>
        </el-form-item>
        <el-form-item label='联系方式' prop='mobile'>
          <el-input type='text' v-model.trim='contactsForm.mobile' placeholder='请输入联系方式' auto-complete='off'></el-input>
        </el-form-item>
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
        <el-form-item>
          <div class='btn-wrap'>
            <a href='javascript:void(0);' class='btn' @click="submitForm('contactsForm')">保存</a>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <imgDialog ref="imgDialog" :imgShow.sync='imgShow' :imgs='imgs'></imgDialog> 
  </div>
</template>

<script>
import imgDialog from '@/components/imgDialog'
import customMixin from '@/assets/mixins/custom-mixins'
export default {
  name: 'increaseDriver',
  mixins: [customMixin],
  data () {
    var validateMobile = (rule, value, callback) => {
      let  reg = /\d+/g
      if (!value) {
        callback(new Error('请输入联系方式'));
      } else if (!reg.test(value)) {
        callback(new Error('请输入数字'));
      } else {
        callback()
      }

    }
    return {
      definedWidth:'120px',
      labelWidth: '140px',
      imgShow:false,
      imgs:'',
      typeTxt: null,
      isChange: false,
      contactsForm: {
        name: null,
        mobile: null,
        salesman_id: null
      },
      contactsRules: {
        name: { required: true, message: '请输入业务员姓名', trigger: ['blur', 'change'] },
        mobile: { required: true, validator: validateMobile, trigger: ['blur', 'change'] },
      },
      definable_fields:[]
    }
  },
  created () {
    this.setTypeTxt()
    
    if(this.$route.query.salesmanId){
      this.getDriverDetail()
    }else{
      this.getFieldList()
    }
    
  },
  methods: {
    setTypeTxt () {
      let isChange = this.$route.query.salesmanId
      if (isChange) {
        this.isChange = true
        this.typeTxt = '修改业务员'
      } else {
        this.typeTxt = '添加业务员'
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
      let salesman_id = this.$route.query.salesmanId
      if (salesman_id) {
        let params = {
          salesman_id: salesman_id
        }
        this.$axios.get(this.$httpUrl.salesmanDetail, { params: params }).then(res => {
          if (res != false) {
            this.contactsForm = res
            this.definable_fields =res.definable_fields
          }
        })
      }
    },
    addTag () {
      this.setCustomData(this.definable_fields)
      this.$router.push({ name: 'addCustom', query: { location_id: this.$locationId.SALESMAN_ID } })
    },
    getFieldList(){
      let params = {
        location_id: this.$locationId.SALESMAN_ID
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
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.contactsForm.definable_fields = this.definable_fields
          this.$axios.post(this.$httpUrl.salesmanSave, this.contactsForm).then(res => {
            if (res != false) {
              this.$message({
                message: this.contactsForm.salesman_id > 0 ? '业务员修改成功' : '业务员添加成功',
                type: 'success'
              })
              this.$router.go(-1)
            }
          })
        } else {
          return false
        }
      })
    }
  },
  components: {
    imgDialog
  }
}
</script>
<style lang ='less'>
.increase{
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
    .el-input__inner{
      width: 220px
    }
  }
}
</style>

<style lang ='less' scoped>
@import "../contacts/increase.less";


</style>