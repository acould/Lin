<template>
  <div>
    <div class="web-login" :style="loginbg">
      <div class="row">
        <img :src="sloganSrc" class="slogan">
      </div>
        <div class="login-box">
          <div class="login-top">
            <img :src="logoSrc" class="logo">
            <span class="logo-tt">登录车队助手</span>
          </div>
          <div class="login-main">
            <div class="tabs-wrap">
              <a href="javascript:void(0);" class="btn-tab" :class="loginType == 1 ? 'selected': ''" @click="toggleLoginType(1)">账号密码登录</a>
              <a href="javascript:void(0);" class="btn-tab" :class="loginType == 2 ? 'selected': ''" @click="toggleLoginType(2)">短信快捷登录</a>
            </div>
            <div v-show="loginType == 2">
              <el-form :model="loginForm" status-icon :rules="loginRules" ref="loginForm">
                <el-form-item prop="mobile" :rules="loginRules.mobile">
                  <div class="inp-wrap">
                    <span class="iconfont icon-shouji iconfont-mobile iconfont-icon"></span>
                    <el-input v-model="loginForm.mobile" maxlength="11" placeholder="请输入手机号" @keyup.enter.native="login('loginForm')"></el-input>
                  </div>
                </el-form-item>
                <el-form-item prop="code" :rules="loginRules.code">
                  <div class="inp-wrap inp-wrap-code">
                    <span class="iconfont icon-yanzhengma iconfont-icon"></span>
                    <el-input v-model="loginForm.code" placeholder="请输入验证码" maxlength="4" @keyup.enter.native="login('loginForm')"></el-input>
                    <span class="code" @click="sendCode('loginForm')">{{verifyCodeVal}}</span>
                  </div>
                </el-form-item>
                <el-form-item class="agreement-wrap-item" prop="agreement" :rules="loginRules.agreement">
                  <div class="agreement-wrap">
                    <el-checkbox v-model="loginForm.agreement">我已阅读并同意</el-checkbox>
                    <a href="javascript:void(0);" class="protocol" @click="openUrl">《车队助手用户注册协议》</a>
                  </div>
                </el-form-item>
                <el-form-item class="btn-wrap-item">
                  <div class="btn-wrap">
                    <el-button type="primary" class="btn" @click="login('loginForm')">登录</el-button>
                  </div>
                </el-form-item>
              </el-form>
            </div>
            <div v-show="loginType == 1">
              <el-form :model="loginFormPwd" status-icon :rules="loginRules" ref="loginFormPwd">
                <el-form-item prop="mobile" :rules="loginRules.mobile">
                  <div class="inp-wrap">
                    <span class="iconfont icon-shouji iconfont-mobile iconfont-icon"></span>
                    <el-input v-model="loginFormPwd.mobile" maxlength="11" placeholder="请输入手机号" @keyup.enter.native="login('loginFormPwd')"></el-input>
                  </div>
                </el-form-item>
                <el-form-item prop="pwd" :rules="loginRules.pwd">
                  <div class="inp-wrap inp-wrap-code">
                    <span class="iconfont icon-mima  iconfont-icon"></span>
                    <el-input type="password" v-model.trim ="loginFormPwd.pwd" placeholder="请输入密码" maxlength="15" @keyup.enter.native="login('loginFormPwd')"></el-input>
                  </div>
                </el-form-item>
                <el-form-item class="agreement-wrap-item" prop="agreement" :rules="loginRules.agreement">
                  <div class="agreement-wrap">
                    <el-checkbox v-model="loginFormPwd.agreement">我已阅读并同意</el-checkbox>
                    <a href="javascript:void(0);" class="protocol" @click="openUrl">《车队助手用户注册协议》</a>
                  </div>
                </el-form-item>
                <el-form-item class="btn-wrap-item">
                  <div class="btn-wrap">
                    <el-button type="primary" class="btn" @click="login('loginFormPwd')">登录</el-button>
                  </div>
                </el-form-item>
                <el-form-item class="btn-wrap-item">
                  <div class="account-handler">
                    <router-link :to="{name: 'accountManagement',query:{accountHandlerType: 1}}" class="a-btn">立即注册</router-link>
                    <router-link :to="{name: 'accountManagement',query:{accountHandlerType: 2}}" class="a-btn">忘记密码</router-link>
                  </div>
                </el-form-item>
              </el-form>
            </div>
            <div class="download-warp">
              <a href="javascript:void(0);" class="client" @click="pcDownload">PC客户端下载</a>
              <a href="javascript:void(0);" class="app" @click="appdownload">手机App下载</a>
            </div>
          </div>
        </div>
      </div>
      <select-app-download :select-app-box-show.sync="selectAppBoxShow" />
    </div>
</template>
<script>
import slogan from '@/assets/images/slogan.png'
import logo from '@/assets/images/login-logo.png'
import { mapActions } from 'vuex'
export default {
  name: 'weblogin',
  data () {
    var validateCode = (rule, value, callback) => {
      let reg = /[0-9]{4}/
      if (!reg.test(value)) {
        callback(new Error('验证码错误'))
      } else {
        callback()
      }
    }
    var validateMobile = (rule, value, callback) => {
      let reg = /^[1]\d{10}$/
      if (!reg.test(value)) {
        callback(new Error('手机号格式错误'))
      } else {
        callback()
      }
    }
    const validateAgreement = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请勾选注册协议'))
      } else {
        callback()
      }
    }
    const validatePwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error('密码不能为空'))
      } else {
        callback()
      }
    }
    return {
      loginType: 1, //1密码登录， 2验证码登录
      selectAppBoxShow: false,
      loginbg: {
        backgroundImage: 'url(https://img.baochequan.cn/images/2018-09-18/e2d99aa3ed6a89de1c1f.png)',
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover'
      },
      sloganSrc: slogan,
      logoSrc: logo,
      verifydisabled: false,
      verifyCodeVal: '获取验证码',
      loginForm: {
        mobile: null,
        code: null,
        agreement: false
      },
      loginFormPwd: {
        mobile: null,
        code: null,
        pwd: null,
        agreement: false
      },
      loginRules: {
        mobile: [
          { validator: validateMobile, trigger: ['blur', 'change'] }
        ],
        code: [
          { validator: validateCode, trigger: ['blur', 'change'] }
        ],
        agreement: [
          { validator: validateAgreement, trigger: ['blur', 'change'] }
        ],
        pwd: [
          { validator: validatePwd, trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  computed: {
    pwdCode () {
      return this.loginFormPwd.agreement
    },
    loginCode () {
      return this.loginForm.agreement
    },
    pwd () {
      return this.loginFormPwd.pwd
    }
  },
  watch: {
    pwdCode (newVal) {
      this.loginForm.agreement = newVal
    },
    loginCode (newVal) {
      this.loginFormPwd.agreement = newVal
    },
    pwd (newVal) {
      this.loginFormPwd.code = newVal
    }
  },
  created () {
    this.getDataCache()
  },
  methods: {
    toggleLoginType (type) {
      this.loginType = type
    },
    getDataCache () {
      let isAgree = localStorage.getItem('isAgree')
      let mobile = localStorage.getItem('mobile')
      if (isAgree) {
        this.loginForm.agreement = true
        this.loginFormPwd.agreement = true
      }
      if (mobile) {
        this.loginForm.mobile = mobile
        this.loginFormPwd.mobile = mobile
      }
    },
    openUrl () {
      window.open('https://mp.baochequan.cn/saas/agreement/registrationprotocol.html')
    },
    //登录
    login (formName) {
          let params = this[formName]
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (formName == 'loginFormPwd') {
            if (params.code.length < 6) {
              this.$message({
                message: '密码错误',
                type: 'error'
              })
            }
          }
          let loading = this.$loading({ text: 'Loading' })
          this.$axios.post(this.$httpUrl.login, params).then(res => {
            loading.close()
            if (res) {
              this.logined(res)
              localStorage.setItem('isAgree', 'true')
              localStorage.setItem('mobile', this.loginForm.mobile)
              if (res.user.saas_company_id > 0) {
                this.$router.push({ name: 'arrange' })
              } else {
                this.$router.push({ name: 'companyInfo' })
              }
            }
          })
        }
      })
    },
    ...mapActions(['logined']),
    //倒计时
    countdown (s) {
      if (s === 0) {
        this.verifydisabled = false
        this.verifyCodeVal = '重新发送'
      } else {
        this.verifydisabled = true
        this.verifyCodeVal = '已发送(' + s + ')s'
        this.timer = setTimeout(
          function () {
            this.countdown(--s)
          }.bind(this),
          1000
        )
      }
    },
    //发送验证码
    sendCode (formName) {
      this.$refs[formName].validateField('mobile', valid => {
        if (!valid) {
          if (this.verifydisabled) return
          let loading = this.$loading({ text: 'Loading' })
          let params = { mobile: this.loginForm.mobile, type: 'login' }
          this.$axios.post(this.$httpUrl.code, params).then(res => {
            loading.close()
            if (res) {
              this.countdown(60)
            }
          })
        }
      })
    },
    pcDownload () {
      this.$axios.get(this.$httpUrl.download).then(res => {
        if (res) {
          let x = this.$util.getCPU()
          if (x == 'x64') {
            window.location.href = res.saas_windows_version_url_x64
          } else if (x == 'x86') {
            window.location.href = res.saas_windows_version_url_x86
          } else {
            this.$message({
              showClose: true,
              message: '暂无该系统安装包',
              type: 'error'
            })
          }
        }
      })
    },
    appdownload () {
      this.selectAppBoxShow = true
    }
  }
}
</script>
<style lang="less" scoped>
@import "./webAccount.less";
</style>
