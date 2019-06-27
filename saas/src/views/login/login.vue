<template>
  <div id="login">
    <div class="bg">
      <div class="higongyu"><img src="/static/imgs/login/higongyu.png" alt ></div>
      <div class="login-wraper">
        <div class="login-main">
          <div class="login-header">
            <span class="title">用户登录</span>
            <img src="/static/imgs/login/logo.png" alt class="logo">
          </div>
          <i-form :model="form" ref="form" @keyup.enter.native="login">
            <i-form-item prop="userPhone" :rules="[{required:true,message:'请输入手机号码',trigger:'blur'},{pattern:/^1\d{10}$/,message:'请输入正确格式'}]">
              <i-input v-model="form.userPhone" placeholder="请输入手机号码"></i-input>
            </i-form-item>
            <i-form-item prop="userPassword" :rules="[{required:true, message:'请输入密码', trigger:'blur'}]">
              <i-input v-model="form.userPassword" type="password" placeholder="请输入密码"></i-input>
            </i-form-item>
            <i-form-item prop="code" :rules="[{required:true, message:'请输入验证码', triggger:'blur'}]">
              <i-input v-model="form.code" placeholder="请输入验证码" class="code-input"></i-input>
              <img class="code-img" :src="codeImg" @click.stop="getVerCodeImg" alt>
            </i-form-item>
          </i-form>
          <i-button type="primary" class="btn-login" @click.stop="login">登录</i-button>
          <div class="forget-wrap">
            <a @click.stop="findPassword">忘记密码</a>
          </div>
        </div>
        <p class="copyright">
          ©2018-2019 Shanghai Gaodu Technology Co.,Ltd All Rights Reserved. <br>
          上海高度信息科技有限公司 备案号：沪ICP备15016401号</p>
      </div>
    </div>
  </div>
</template>
<script>
import loginService from 'service/login';

export default {
  data() {
    return {
      form: {
        userPhone: "",
        userPassword: "",
        code: ""
      },
      codeImg:""
    };
  },
  methods:{
      login(){
          this.$refs['form'].validate(valid => {
              if (valid) {
                  let params = JSON.parse(JSON.stringify(this.form));
                  loginService.login(params).then(res => {
                      if (res.code === 0) {
                          this.$message.success('登录成功');
                          sessionStorage.clear();
                          this.$router.push('/dashboard');
                      } else {
                        this.getVerCodeImg();
                      }
                  });
              }
          });
      },
      findPassword(){
          this.$router.push('/find/password');
      },
      getVerCodeImg(){
        loginService.getVerCodeImg().then(res => {
          if (res.code == 0) {
              this.codeImg = res.data;
          }
        });
      }
  },
  mounted(){
      this.getVerCodeImg();
  }
};
</script>
<style lang="scss">
#login {
  width: 100%;
  height: 100%;
  background: linear-gradient(
    136deg,
    rgba(47, 136, 255, 1) 0%,
    rgba(47, 2, 145, 1) 100%
  );

  .higongyu{
    position: absolute;
    top:30%;
    left:30%;
    img{
      width:200px;
    }
  }
  .bg {
    position: relative;
    min-width: 1280px;
    height: 100%;
    background-image: url("/static/imgs/login/bg.png");
    background-size: cover;
    margin: 0 auto;
  }

  .login-wraper{
    width: 480px;
    height: 100%;
    position: absolute;
    right: 123px;
  }

  .login-main {
    width: 400px;
    height: 480px;
    padding: 40px;
    background: #fff;
    border-radius: 4px;
    position: absolute;
    bottom: 20%;
    .login-header {
      height: 48px;
      width: 100%;
      line-height: 48px;
      margin-bottom: 40px;

      .title {
        font-size: 24px;
        font-weight: bold;
        color: #333;
      }

      .logo {
        width: 48px;
        height: 48px;
        float: right;
      }
    }

    .el-input-inner {
      height: 40px;
    }

    .code-input {
      float: left;
      width: 192px;
      margin-right: 16px;
    }

    .code-img {
      width: 112px;
      height: 40px;
      cursor: pointer;
    }

    .btn-login {
      width: 100%;
      margin-top: 16px;
      padding: 14px 15px;
      font-size: 18px;
      margin-bottom: 24px;
    }

    .forget-wrap {
      text-align: right;
    }
  }

  .copyright{
      position: absolute;
      bottom:10%;
      left:-40px;
      font-size: 14px;
      color:#fff;
      text-align: center;
      width: 100%;
  }
}
</style>


