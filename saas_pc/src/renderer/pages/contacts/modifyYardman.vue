<template>
  <div class="account-main" id="IncreaseOperator">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'operatorOrigin' }">客户管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'operatorOrigin',query: {type: 'operatorOrigin'} }">同事/车调</el-breadcrumb-item>
          <el-breadcrumb-item>修改车调</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <el-form
        :model="operatorForm"
        ref="customerForm"
        :rules="operatorRules"
        :label-width="labelWidth"
        class="customerForm"
      >
        <el-form-item label="姓名" prop="name">
          <div class="inp-wrap">
            <el-input
              type="text"
              readonly
              v-model="operatorForm.name"
              placeholder="请输入姓名"
              auto-complete="off"
            ></el-input>
          </div>
        </el-form-item>
        <el-form-item label="联系方式" prop="mobile">
          <div class="inp-wrap">
            <el-input
              type="text"
              readonly
              v-model="operatorForm.mobile"
              placeholder="请输入联系方式"
              auto-complete="off"
            ></el-input>
          </div>
        </el-form-item>
        <el-form-item label="用户身份" prop="role_id">
          <div class="inp-wrap">
            <el-select v-model="operatorForm.role_id" placeholder="请选择">
              <el-option
                v-for="(item, index) in content"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
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
    return {
      labelWidth: "104px",
      typeTxt: '',
      operatorForm: {
        mobile: null,
        name: null,
        yardman_id: null,
        role_id: null,
      },
      name: '',
      mobile: '',
      operatorRules: {
        mobile: { required: true, message: '联系方式不能为空', trigger: ['blur', 'change'] },
        name: { required: true, message: '名字不能为空', trigger: ['blur', 'change'] },
        role_id: { required: true, message: '用户身份不能为空', trigger: ['blur', 'change'] },
      },
      content: [
        {
          label: '管理员',
          value: 1
        },
        {
          label: '车调',
          value: 2
        },
        {
          label: '财务',
          value: 3
        }
      ]
    }
  },
  created () {
    this.getQueryParams()
  },
  methods: {
    getQueryParams () {
      let query = this.$route.query
      if (query.name) this.operatorForm.name = query.name
      if (query.mobile) this.operatorForm.mobile = query.mobile
      if (query.yardman_id) this.operatorForm.yardman_id = query.yardman_id
      if(query.role_id) this.operatorForm.role_id = query.role_id
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post(this.$httpUrl.yardmenUpdate, this.operatorForm).then(res => {
            if (res != false) {
              this.$message({
                message: '车调修改成功',
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
  .el-select {
    width: 256px;
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