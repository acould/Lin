<template>
  <div class="login-after-container">
    <span class="iconfont icon-close" @click="closeLoginWindow"></span>
    <img :src="logoSrc" class="logo">
    <p class="logo-tt">车队助手</p>
    <div class="login-after-box">
      <el-form :model="infoForm" status-icon :rules="infoRules" ref="infoForm">
        <el-form-item prop="company" :rules="infoRules.company">
          <div class="inp-wrap">
            <el-input v-model="infoForm.company" placeholder="请输入公司名称"></el-input>
          </div>
        </el-form-item>
        <el-form-item prop="name" :rules="infoRules.name">
          <div class="inp-wrap inp-wrap-code">
            <el-input v-model="infoForm.name" placeholder="请输入真实姓名"></el-input>
          </div>
        </el-form-item>   
        <el-form-item class="btn-wrap-item">
          <div class="btn-wrap">
            <el-button type="primary" class="btn" @click="rtnInfo('infoForm')">确定</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import logo from '@/assets/images/login-logo.png'
export default {
  name: 'companyInfo',
  data () {
    return {
      logoSrc: logo,
      infoForm: {
        name: null,
        company: null
      },
      infoRules: {
        name: [
          { required: true, message: '请输入真实姓名', trigger: 'blur' }
        ],
        company: [
          { required: true, message: '请输入公司名称', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    closeLoginWindow () {
      this.$windowUtil.closeWindow()
    },
    // 登录
    rtnInfo (formName) {  
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let loading = this.$loading({ text: 'Loading' })
          this.$axios.post(this.$httpUrl.complete, this.infoForm).then(res => {
            loading.close()
            if (res) {              
              this.$windowUtil.loginSuccess()  
            }
          })
        }
      })
    }  
  }
}
</script>
<style lang ="less" scoped>
/* 拖拽 */
body {
  .login-after-container {
    -webkit-app-region: drag;
    cursor: pointer;
  }
  .login-after-box,
  .icon-close {
    -webkit-app-region: no-drag;
    cursor: default
  }
}

.login-after-container {
  width: 100vw;
  height: 100vh;
  position: absolute;
  left: 0;
  top: 0;
  background-color: #fefefe;
  text-align: center;
  .icon-close {
    position: absolute;
    right: 0;
    top: -10px;
    font-size: 20px;
    color: #666;
    padding: 20px;
    cursor: pointer;
  }
  .logo {
    display: block;
    width: 83px;
    height: 83px;
    margin: 25px auto 13px;
  }
  .logo-tt {
    font-size: 21px;
    color: @color-dark-grey;
    margin-bottom: 20px;
    font-weight: bold;
  }
  .login-after-box {
    width: 300px;
    margin: 0 auto;
    .el-form-item {
      margin-bottom: 18px;
    }
    .btn-wrap-item {
      margin-bottom: 0;
    }    
    .inp-wrap {    
      position: relative;
      .iconfont {
        position: absolute;
        left: 2px;
        top: 4px;
        font-size: @fontsize-large-x;
        color: @color-light-grey;
        &.iconfont-mobile {
          font-size: 22px;
          left: 0px;
        }
      }
      .el-input {
        & /deep/ .el-input__inner {
          border: none;
          border-radius: 0;
          border-bottom: 1px solid #cacaca;
          padding: 0 90px 0 8px;
          color: @color-grey;
        }
      }

      &.inp-wrap-code {
        position: relative;
        .code {
          position: absolute;
          right: 10px;
          top: 2px;
          color: @theme-color;
          font-size: @fontsize-medium;
          cursor: pointer;
          &::before {
            content: '';
            position: absolute;
            left: -12px;
            top: 6px;
            width: 1px;
            height: 26px;
            background-color: @color-light-grey;
          }
        }
      }
    }
    .btn-wrap {
      margin-top: 40px;
      .btn {
        height: 36px;
        line-height: 1;
        width: 100%;
      }
    }
  }
}
</style>