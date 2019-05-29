<template>
  <div class="fleet-main">
    <div class="main-header" ref="main-header">
      <div class="tab-wraps">
        <el-tabs v-model="pageType" @tab-click="toggleIdentityType">
          <el-tab-pane label="车辆管理" name="fleetManagement"></el-tab-pane>
          <el-tab-pane label="订单列表" name="fleetOrderList"></el-tab-pane>
          <el-tab-pane label="财务" name="fleetFinance"></el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div class="main-content">
      <el-form :model='withdrawForm' :rules='withdrawRules' ref='withdrawForm' :label-width='labelWidth' class='withdraw-form'>
        <el-form-item label='当前余额'>
          <p class="balance color-red">{{balance}}</p>
        </el-form-item>
        <el-form-item label='提现金额' prop='money'>
          <el-input type='text' v-model='withdrawForm.money' placeholder='请输入提现金额' auto-complete='off'></el-input>
          <span class="tip">周一到周五9:00-18:00</span>
        </el-form-item>
        <el-form-item label="开户银行" prop="bank_name">
          <el-autocomplete placeholder="请选择开户银行" value-key="title" v-model="withdrawForm.bank_name" :fetch-suggestions='queryBankList' @select='handleSelectBank'>
          </el-autocomplete>
        </el-form-item>
        <el-form-item label='提现账号' prop='bank_number'>
          <el-input type='text' v-model='withdrawForm.bank_number' placeholder='请输入银行卡账号' auto-complete='off'></el-input>
        </el-form-item>
        <el-form-item label='输入密码' prop='password'>
          <el-input type='password' v-model='withdrawForm.password' placeholder='请输入登录密码' auto-complete='off'></el-input>
        </el-form-item>
        <el-form-item>
          <div class='btn-wrap'>
            <a href='javascript:void(0);' @click="submitForm('withdrawForm')" class='btn'>提现</a>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  name: 'fleetFinance',
  components: {

  },
  data () {
    var validateMoney = (rule, value, callback) => {
      let reg = /^[1-9]\d*$/g
      if (value == 0) {
        callback(new Error('金额必须大于0'))
      } else if (!reg.test(value)) {
        callback(new Error('金额必须为整数'))
      } else if (value > this.balanceFloat) {
        callback(new Error('金额不能大于当前余额'))
      } else {
        callback()
      }
    }
    var validateBankName1 = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请选择开户银行'))
      } else {
        callback()
      }
    }
    var validateBankName2 = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请选择开户银行'))
      } else if (!this.withdrawForm.bank_type) {
        this.withdrawForm.bank_name = ''
        callback(new Error('请选择开户银行'))
      } else {
        callback()
      }
    }
    return {
      pageType: 'fleetFinance',
      labelWidth: '110px',
      withdrawForm: {
        bank_number: null,
        bank_type: null,  // 银行卡类型 ID
        bank_name: null,
        money: null,
        password: null
      },
      withdrawRules: {
        money: [
          { required: true, message: '请填写提现金额', trigger: ['blur', 'change'] },
          { required: true, validator: validateMoney, trigger: ['blur', 'change'] },
        ],
        bank_number: { required: true, message: '请输入银行卡账号', trigger: ['blur', 'change'] },
        password: { required: true, message: '请输入密码', trigger: ['blur', 'change'] },
        bank_name: [
          { required: true, validator: validateBankName1, trigger: 'change' },
          { required: true, validator: validateBankName2, trigger: 'blur' },
        ],
      },
      balance: ''
    }
  },
  computed: {
    balanceFloat () {
      return Number.parseFloat(this.balance)
    },
    bank_name () {
      return this.withdrawForm.bank_name
    }
  },
  watch: {
    bank_name (newVal, oldVal) {
      if (!newVal || (newVal && oldVal)) {
        this.withdrawForm.bank_type = null
      }
    }
  },
  created () {
    this.getBalance()
  },
  methods: {
    toggleIdentityType () {
      let routerName = this.pageType
      this.$router.push({ name: routerName })
    },
    getBalance () {
      this.$axios.get(this.$httpUrl.withdrawBalance).then(res => {
        if (res) {
          this.balance = res.balance
        }
      })
    },
    queryBankList (queryString, cb) {
      let params = {
        keywords: queryString
      }
      this.$axios.get(this.$httpUrl.bankList, { params: params }).then(res => {
        if (res) {
          cb(res)
        }
      })
    },
    handleSelectBank (item) {
      this.withdrawForm = Object.assign({}, this.withdrawForm, { bank_name: item.title, bank_type: item.id })
    },
    submitForm () {
      this.$refs['withdrawForm'].validate((valid) => {
        if (valid) {
          let withdrawForm = this.withdrawForm
          let money = withdrawForm.money
          let bank_number = withdrawForm.bank_number
          this.$confirm(`您将提现${money}元至${bank_number}账户`, '温馨提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$axios.post(this.$httpUrl.withdrawSave, this.withdrawForm).then(res => {
              if (res) {
                this.$message.success('操作成功,正在提现中，请稍等')
              }
            })
          }).catch(() => {
            console.log('取消操作')
          });

        } else {
          this.$message({
            showClose: true,
            message: '请填写必填信息',
            type: 'error'
          })

        }
      })
    }
  }
}
</script>
<style lang ="less" scoped>
.withdraw-form {
  margin-top: 15px;
  .el-input,
  .el-autocomplete {
    width: 203px;
  }
  .balance {
  }
  .btn-wrap {
    width: 176px;
    margin-top: 17px;
  }
  .tip {
    color: @red;
    font-size: 12px;
    margin-left: 10px;
  }
}
</style>


