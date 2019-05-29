<template>
  <div class="set-origin">
    <div class="main-header">
      <h3 class="single-tt">设置</h3>
    </div>
    <div class="main-content">
      <div class="takespace"></div>
      <div class="set-con">
        <div class="line">
          <span class="label">公司名称</span>
          <span class="inp">{{company}}</span>
        </div>
        <div class="line">
          <span class="label">真实姓名</span>
          <span class="inp">{{name}}</span>
        </div>
        <div class="line">
          <span class="label">修改手机号</span>
          <a href="javascript:void(0);" class="change-tel" @click="popupChangeTelBox">更换手机</a>
        </div>
        <div class="line">
          <span class="label">修改密码</span>
          <a href="javascript:void(0);" class="change-tel" @click="popupResetTelBox">修改密码</a>
        </div>
        <div class="btn-wrap">
          <a href="javascript:void(0);" class="btn" @click="logout">退出登录</a>
        </div>
      </div>
    </div>
    <changeTelBox :changeTelBoxShow.sync="changeTelBoxShow"></changeTelBox>
    <reset-pwd-box :reset-pwd-box-show.sync="resetPwdBoxShow"></reset-pwd-box>
  </div>
</template>
<script>
import changeTelBox from '@/components/changeTelBox'
import ResetPwdBox from '@/components/ResetPwdBox'
import { mapActions } from 'vuex'
export default {
  name: 'set',
  components: {
    changeTelBox,
    ResetPwdBox
  },
  data () {
    return {
      changeTelBoxShow: false,
      resetPwdBoxShow: false,
      name: null,
      company: null,
      boxShow: true,
    }
  },
  created () {
    this.getUserInfo()
  },
  methods: {
    ...mapActions([
      'refreshUserInfo',
    ]),
    ...mapActions({
      storeLogout: 'logout'
    }),
    getUserInfo () {
      this.$axios.get(this.$httpUrl.userInfo).then(res => {
        if (res) {
          let user = res.user
          this.name = user.name
          this.company = user.saas_company_name
          this.refreshUserInfo(res)
        }
      })
    },
    popupChangeTelBox () {
      this.changeTelBoxShow = true
    },
    logout () {
      this.$confirm('是否退出登录', {
        type: 'warning'
      }).then(() => {
        if (!process.env.IS_WEB) {
          this.storeLogout().then(() => {
            this.$windowUtil.logout()
          })
        }
        else this.storeLogout()
      })
    },
    popupResetTelBox () {
      this.resetPwdBoxShow = true
    }
  }
}
</script>
<style lang ="less" scoped>
@line-height: 32px;
.set-con {
  margin-top: 22px;
  .line {
    overflow: hidden;
    height: @line-height;
    color: @color-dark-grey;
    font-size: @fontsize-medium;
    margin-bottom: 10px;
    .label {
      float: left;
      width: 125px;
      text-align: right;
      margin-right: 40px;
    }
    .inp,
    .label {
      height: @line-height;
      line-height: @line-height;
    }
    .change-tel {
      float: left;
      width: 133px;
      height: @line-height;
      line-height: @line-height;
      text-align: center;
      box-sizing: border-box;
      color: @color-dark-grey;
      border: 1px solid @color-light-grey;
      border-radius: 4px;
    }
  }
  .btn-wrap {
    margin-left: 53px;
    width: 246px;
    margin-top: 20px;
    .btn {
      height: 36px;
      line-height: 36px;
      font-size: @fontsize-medium;
    }
  }
}
</style>