<template>
  <div class="login-container">
    <span class="iconfont icon-jiantou2 top-icon iconfont-back" @click="$router.go(-1)"></span>
    <span class="iconfont icon-close top-icon" @click="closeLoginWindow"></span>
    <img :src="logoSrc" class="logo">
    <p class="logo-tt">车队助手</p>
    <div class="login-box">
      <p class="pagetypetxt">{{accountHandlerText}}</p>
      <div>
        <el-form :model="accountForm" status-icon :rules="accountRules" ref="accountForm">
          <el-form-item prop="mobile" :rules="accountRules.mobile">
            <div class="inp-wrap">
              <span class="iconfont icon-shouji iconfont-mobile"></span>
              <el-input v-model="accountForm.mobile" maxlength="11" placeholder="请输入手机号"></el-input>
            </div>
          </el-form-item>
          <el-form-item prop="code" :rules="accountRules.code">
            <div class="inp-wrap inp-wrap-code">
              <span class="iconfont icon-yanzhengma"></span>
              <el-input v-model="accountForm.code" placeholder="请输入验证码" maxlength="4"></el-input>
              <span class="code" @click="sendCode('accountForm')">{{verifyCodeVal}}</span>
            </div>
          </el-form-item>
          <el-form-item prop="password" :rules="accountRules.password">
            <div class="inp-wrap inp-wrap-code">
              <span class="iconfont icon-mima"></span>
              <el-input type="text" v-model="accountForm.password" placeholder="密码长度不小于6位" maxlength="15" @keyup.enter.native="commitAccountInfo('accountForm')"></el-input>
            </div>
          </el-form-item>
          <el-form-item v-if="accountHandlerType == 1" class="agreement-wrap-item" prop="agreement" :rules="accountRules.agreement">
            <div class="agreement-wrap">
              <el-checkbox v-model="accountForm.agreement">我已阅读并同意</el-checkbox>
              <a href="javascript:void(0);" class="protocol" @click="openUrl">《车队助手用户注册协议》</a>
            </div>
          </el-form-item>
          <el-form-item class="btn-wrap-item">
            <div class="btn-wrap">
              <el-button type="primary" class="btn" @click="commitAccountInfo('accountForm')">提交</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
      <router-link class="links" :to="{name:'login'}">已有账号 立即登录</router-link>
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
      let reg = /\s/g
      if (!value) {
        callback(new Error('密码不能为空'))
      } else if (reg.test(value)) {
        callback(new Error('密码不能包含空格'))
      } else if (value.length < 6) {
        callback(new Error('密码的长度不能小于6位'))
      } else {
        callback()
      }
    }
    return {
      accountHandlerType: 1,
      accountHandlerText: '',
      logoSrc: logo,
      verifydisabled: false,
      verifyCodeVal: '获取验证码',
      accountForm: {
        mobile: null,
        code: null,
        password: null,
        agreement: null
      },
      accountRules: {
        mobile: [
          { validator: validateMobile, trigger: ['blur', 'change'] }
        ],
        code: [
          { validator: validateCode, trigger: ['blur', 'change'] }
        ],
        password: [
          { validator: validatePwd, trigger: ['blur', 'change'] }
        ],
        agreement: [
          { validator: validateAgreement, trigger: ['blur', 'change'] }
        ],
      }
    }
  },
  created () {
    this.getQueryParams()
  },
  methods: {
    getQueryParams () {
      let accountHandlerType = this.$route.query.accountHandlerType
      if (accountHandlerType) {
        this.accountHandlerType = accountHandlerType
        this.accountHandlerText = accountHandlerType == 1 ? '立即注册' : '忘记密码'
      }
    },
    closeLoginWindow () {
      this.$windowUtil.closeWindow()
    },
    openUrl () {
      window.open('https://mp.baochequan.cn/saas/agreement/registrationprotocol.html')
    },
    commitAccountInfo (formName) {
      let params = this[formName]
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let loading = this.$loading({ text: 'Loading' })
          let type = this.accountHandlerType
          let url = type == 1 ? this.$httpUrl.register : this.$httpUrl.resetPassword
          this.$axios.post(url, params).then(res => {
            loading.close()
            if (res) {
              if (type == 1) {
                this.login(params)
              } else {
                this.$router.push({ name: 'login' })
              }
            }
          })
        }
      })
    },
    login (params) {
      this.$axios.post(this.$httpUrl.login, params).then(res => {
        if (res) {
          this.logined(res).then(() => {
            localStorage.setItem('mobile', params.mobile)
            if (res.user.saas_company_id > 0) {
              this.$windowUtil.loginSuccess()
            } else {
              this.$router.push({ name: 'companyInfo' })
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
          let params = { mobile: this[formName].mobile, type: 'login' }
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
<style lang ="less" scoped>
@import "./account.less";
.pagetypetxt {
  margin: 30px auto 20px;
  font-size: 14px;
  color: #333;
}
.links {
  display: block;
  text-align: center;
  margin-top: 14px;
  font-size: 14px;
  color: @theme-color;
}
</style>