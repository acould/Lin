<template>
  <div class="find-password">
    <div class="bg">
      <div class="higongyu"><img src="/static/imgs/login/higongyu.png" alt ></div>
      <div class="login-wraper">
        <div class="login-main">
          <div class="login-header">
            <span class="title">找回密码{{active}}/2</span>
            <a style="float:right;color:#329DFF" @click.stop="$router.push('/login')">返回登录</a>
          </div>
          <div>
            <i-steps :active="active">
              <i-step title="验证手机号" icon="el-icon-circle-check"></i-step>
              <i-step title="重置名" icon="el-icon-circle-check" status="wait"></i-step>
            </i-steps>
          </div>
          <div v-if="active == 1">
            <i-form :model="form" ref="form">
              <i-form-item
                prop="userPhone"
                :rules="[{required:true,message:'请输入手机号码',trigger:'blur'},{pattern:/^1\d{10}$/,message:'请输入正确格式'}]"
              >
                <i-input v-model="form.userPhone" placeholder="请输入手机号码"></i-input>
              </i-form-item>
              <i-form-item
                prop="code"
                :rules="[{required:true, message:'请输入验证码', triggger:'blur'}]"
              >
                <i-input v-model="form.code" placeholder="请输入验证码" class="code-input"></i-input>
                <i-button class="btn-code" @click.stop="getAuthCode">{{btnName}}</i-button>
              </i-form-item>
            </i-form>
            <i-button type="primary" class="btn-login" @click.stop="check">找回密码</i-button>
          </div>
          <div v-if="active == 2">
              <i-form :model="passwordForm" ref="passwordForm">
                  <i-form-item prop="password1" :rules="[{required:true,message:'请输入新密码，密码格式是8-20位的数字和字母', trigger:'blur'},{pattern:/^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,20}$/, message:'请输入新密码，密码格式是8-20位的数字和字母'}]">
                      <i-input v-model="passwordForm.password1" type="password" placeholder="请输入新密码，密码格式是8-20位的数字和字母"></i-input>
                  </i-form-item>
                  <i-form-item prop="password2" :rules="[{required:true,trigger:'blur', validator:_validPassword}]">
                      <i-input v-model="passwordForm.password2" type="password" placeholder="请输入确认新密码，密码格式是8-20位的数字和字母"></i-input>
                  </i-form-item>
              </i-form>
             <i-button type="primary" class="btn-login" @click.stop="update">确定</i-button>

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
import loginSerive from "service/login";
export default {
  data() {
    return {
      active: 1,
      form: {
        userPhone: "",
        code: ""
      },
      passwordForm:{
          userPhone:"",
          password1:"",
          password2:""
      },
      btnName: "获取验证码",
      timer: null,
      seconds: 60
    };
  },
  methods: {
    getAuthCode() {
      if (!this.form.userPhone) {
        this.$message.error("请先输入手机号");
        return;
      }
      if (this.timer) {
        return;
      }
      loginSerive.getForgetCode(this.form.userPhone).then(res => {
        if (res.code === 0) {
          this.timer = setInterval(() => {
            if (this.seconds == 0) {
              clearInterval(this.timer);
              this.btnName = "获取验证码";
              this.seconds = 60;
              return;
            }
            this.btnName = this.seconds + "s重新获取";
            this.seconds--;
          }, 1000);
        }
      });
    },
    check() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let params = JSON.parse(JSON.stringify(this.form));
          loginSerive.checkPhoneAndCode(params).then(res => {
            if (res.code == 0) {
              this.active = 2;
              this.passwordForm.userPhone = this.form.userPhone;

            }
          });
        }
      });
    },
    _validPassword(rule, value, callback){
      if (!value){
        return callback(new Error('请再次输入新密码'));
      }

      if (value != this.passwordForm.password1) {
        return callback(new Error('确认密码不一致'));
      }

      return callback();
    },
    update(){
        this.$refs['passwordForm'].validate(valid => {
            if (valid) {
                let params = JSON.parse(JSON.stringify(this.passwordForm));
                loginSerive.password(params).then(res => {
                    if (res.code === 0) {
                        this.$message('修改成功');
                    }
                });
            }
        });
    }
  }
};
</script>
<style lang="scss">
.find-password {
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
    .btn-code {
      width: 112px;
      height: 40px;
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
  .el-steps {
    margin-bottom: 40px;
  }

  .el-step-title {
    font-size: 12px;
    line-height: 18px;
  }
}
</style>

