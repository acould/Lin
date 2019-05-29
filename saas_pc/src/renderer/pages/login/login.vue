<template>
  <div class="login-container">
    <span class="iconfont icon-close top-icon" @click="closeLoginWindow"></span>
    <img :src="logoSrc" class="logo">
    <p class="logo-tt">车队助手</p>
    <div class="login-box">
      <div class="tabs-wrap">
        <a href="javascript:void(0);" class="btn-tab" :class="loginType == 1 ? 'selected': ''" @click="toggleLoginType(1)">账号密码登录</a>
        <a href="javascript:void(0);" class="btn-tab" :class="loginType == 2 ? 'selected': ''" @click="toggleLoginType(2)">短信快捷登录</a>
      </div>
      <div v-show="loginType == 2">
        <el-form :model="loginForm" status-icon :rules="loginRules" ref="loginForm">
          <el-form-item prop="mobile" :rules="loginRules.mobile">
            <div class="inp-wrap">
              <span class="iconfont icon-shouji iconfont-mobile"></span>
              <el-input v-model="loginForm.mobile" maxlength="11" placeholder="请输入手机号" @keyup.enter.native="login('loginForm')"></el-input>
            </div>
          </el-form-item>
          <el-form-item prop="code" :rules="loginRules.code">
            <div class="inp-wrap inp-wrap-code">
              <span class="iconfont icon-yanzhengma"></span>
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
              <span class="iconfont icon-shouji iconfont-mobile"></span>
              <el-input v-model="loginFormPwd.mobile" maxlength="11" placeholder="请输入手机号" @keyup.enter.native="login('loginFormPwd')"></el-input>
            </div>
          </el-form-item>
          <el-form-item prop="pwd" :rules="loginRules.pwd">
            <div class="inp-wrap inp-wrap-code">
              <span class="iconfont icon-mima"></span>
              <el-input type="password" v-model.trim="loginFormPwd.pwd" placeholder="请输入密码" maxlength="15" @keyup.enter.native="login('loginFormPwd')"></el-input>
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
        </el-form>
        <div class="account-handler">
          <router-link :to="{name: 'accountManagement',query:{accountHandlerType: 1}}" class="a-btn">立即注册</router-link>
          <router-link :to="{name: 'accountManagement',query:{accountHandlerType: 2}}" class="a-btn">忘记密码</router-link>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
import logo from '@/assets/images/login-logo.png'
export default {
  name: 'login',
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
    openUrl (link) {
      this.$electron.shell.openExternal('https://mp.baochequan.cn/saas/agreement/registrationprotocol.html')
    },
    closeLoginWindow () {
      this.$windowUtil.closeWindow()
    },
    // 登录
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
              this.logined(res).then(() => {
                localStorage.setItem('isAgree', 'true')
                localStorage.setItem('mobile', params.mobile)
                if (res.user.saas_company_id > 0) {
                  this.$windowUtil.loginSuccess()
                } else {
                  this.$router.push({ name: 'companyInfo' })
                }
              })
            }
          })
        }
      })
    },
    ...mapActions([
      'logined'
    ]),
    // 倒计时
    countdown (s) {
      if (s === 0) {
        this.verifydisabled = false
        this.verifyCodeVal = '重新发送'
      } else {
        this.verifydisabled = true
        this.verifyCodeVal = '已发送(' + s + ')s'
        this.timer = setTimeout(function () {
          this.countdown(--s)
        }.bind(this), 1000)
      }
    },
    // 发送验证码
    sendCode (formName) {
      this.$refs[formName].validateField('mobile', (valid) => {
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
    }
  }
}
</script>
<style lang="less" scoped>
@import "./account.less";
</style>