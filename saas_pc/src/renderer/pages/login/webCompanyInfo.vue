<template>
  <div>
    <div class="webCompanyInfo" :style="loginbg">
      <img :src="sloganSrc" class="slogan">
      <div class="login-box">
        <div class="login-top">
          <img :src="logoSrc" class="logo">
          <span class="logo-tt">车队助手</span>
        </div>
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
    </div>
  </div>
</template>
<script>
import slogan from '@/assets/images/slogan.png'
import logo from '@/assets/images/login-logo.png'
export default {
  name: 'webCompanyInfo',
  data () {
    return {
      loginbg: {
        backgroundImage: 'url(https://img.baochequan.cn/images/2018-09-18/e2d99aa3ed6a89de1c1f.png)',
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover'
      },
      sloganSrc: slogan,
      logoSrc: logo,
      infoForm: {
        name: null,
        company: null
      },
      infoRules: {
        name: [
          { required: true, message: '请输入真实姓名', trigger: ['blur', 'change'] }
        ],
        company: [
          { required: true, message: '请输入公司名称', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    // 登录
    rtnInfo (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let loading = this.$loading({ text: 'Loading' })
          this.$axios.post(this.$httpUrl.complete, this.infoForm).then(res => {
            loading.close()
            if (res) {
              this.$router.push({ name: 'arrange' })
            }
          })
        }
      })
    }
  }
}
</script>
<style lang="less" scoped>
.webCompanyInfo {
  & /deep/ .el-form-item__error {
    left: 28px;
    top: 110%;
  }
  & /deep/ .el-input__suffix {
    right: 20px;
  }
  & /deep/ .el-input__inner {
    border: none;
    border-radius: 40px;
    background-color: #f5f5f5;
    padding: 0 90px 0 8px;
    color: @color-grey;
  }
}

.webCompanyInfo {
  position: fixed;
  left: 0;
  top: 0;
  z-index: 99;
  min-height: 100vh;
  min-width: 100vw;
  .slogan {
    height: 52px;
    width: 667px;
    margin: 112px 0 0 830px;
  }
  .login-box {
    width: 480px;
    height: 400px;
    position: absolute;
    top: 0;
    bottom: 0;
    right: 300px;
    margin: auto; // margin: 96px 370px 0 auto;
    background-color: @bd-bg;
    border-radius: 8px;
    .login-top {
      text-align: center;
      padding-top: 40px;
      height: 50px;
      .logo {
        width: 50px;
        height: 50px;
        margin-right: 10px;
        margin-bottom: -10px;
      }
      .logo-tt {
        font-size: 34px;
      }
    }
    .login-after-box {
      width: 400px;
      margin: 0 auto;
      margin-top: 40px;
      .el-form-item {
        margin-bottom: 40px;
      }
      .inp-wrap {
        padding-left: 20px;
        position: relative;
        background-color: #f5f5f5;
        border-radius: 40px;
        .iconfont {
          position: absolute;
          left: 2px;
          font-size: @fontsize-large-x;
          color: @color-light-grey;
          &.iconfont-icon {
            font-size: 22px;
            left: 30px;
          }
        }
        &.inp-wrap-code {
          position: relative;
          .code {
            position: absolute;
            right: 30px;
            top: 2px;
            color: #ccc;
            z-index: 9;
            font-size: @fontsize-medium;
            cursor: pointer;
            &::before {
              content: "";
              position: absolute;
              left: -12px;
              top: 7px;
              width: 1px;
              height: 26px;
              background-color: @color-light-grey;
            }
          }
        }
      }

      .btn-wrap {
        .btn {
          height: 43px;
          line-height: 1;
          width: 100%;
          border-radius: 40px;
        }
      }
    }
  }
}
</style>
