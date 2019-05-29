<template>
  <div class="financeOptions">
    <router-link tag="div" :to="{name:'reportForm'}" class="block block5" :class="option == 5 ? 'block-selected' : ''">
      <img :src="icon_finance5" class="icon">
      <div class="right-side">
        <p class="lb">报表</p>
      </div>
    </router-link>
    <router-link tag="div" :to="{name:'revenue'}" class="block block1" :class="option == 1 ? 'block-selected' : ''">
      <img :src="icon_finance1" class="icon">
      <div class="right-side">
        <p class="lb">营收</p>
        <p class="txt">{{info.total_income_money}}元</p>
      </div>
    </router-link>
    <router-link tag="div" :to="{name:'outSettlement'}" class="block block2" :class="option == 2 ? 'block-selected' : ''">
      <img :src="icon_finance2" class="icon">
      <div class="right-side">
        <p class="lb">外援结算</p>
        <p class="txt">{{info.foreign_money}}元</p>
      </div>
    </router-link>
    <router-link tag="div" :to="{name:'spending'}" class="block block3" :class="option == 3 ? 'block-selected' : ''">
      <img :src="icon_finance3" class="icon">
      <div class="right-side">
        <p class="lb">支出</p>
        <p class="txt">{{info.total_pay_money}}元</p>
      </div>
    </router-link>
    <router-link tag="div" :to="{name:'profits'}" class="block block4" :class="option == 4 ? 'block-selected' : ''">
      <img :src="icon_finance4" class="icon">
      <div class="right-side">
        <p class="lb">利润</p>
        <p class="txt">{{info.profit}}元</p>
      </div>
    </router-link>
  </div>
</template>
<script>
import icon_finance1 from '@/assets/images/icon_finance1.png'
import icon_finance2 from '@/assets/images/icon_finance2.png'
import icon_finance3 from '@/assets/images/icon_finance3.png'
import icon_finance4 from '@/assets/images/icon_finance4.png'
import icon_finance5 from '@/assets/images/icon_finance5.png'
export default {
  name: 'financeOptions',
  props: {
    option: null
  },
  data () {
    return {
      icon_finance1: icon_finance1,
      icon_finance2: icon_finance2,
      icon_finance3: icon_finance3,
      icon_finance4: icon_finance4,
      icon_finance5: icon_finance5,
      info: {}
    }
  },
  created () {
    this.getFinanceData()
  },
  methods: {
    getFinanceData () {
      this.$axios.get(this.$httpUrl.moneyIndex).then(res => {
        if (res) {
          this.info = res
        }
      })
    }
  }
}
</script>
<style lang ="less" scoped>
.financeOptions {
  height: 100px;
  min-width: 847px;
  background-color: #f5f5f5;
  padding: 16px;
  box-sizing: border-box;
  overflow: hidden;
  .block {
    width: 154px;
    height: 71px;
    float: left;
    border-radius: 4px;
    background-color: #fff;
    padding: 14px 0 14px 14px;
    box-sizing: border-box;
    margin-right: 10px;
    cursor: pointer;
    &.block1 {
      color: #0db592;
    }
    &.block2 {
      color: #ff991f;
    }
    &.block3 {
      color: #ff991f;
    }
    &.block4 {
      color: #ff5656;
    }
    &.block5 {
      color: #439ff8;
      .right-side {
        line-height: 42px;
      }
    }

    &.block-selected {
      box-shadow: 0px 3px 6px 0px rgba(0, 0, 0, 0.2);
    }
    &:last-of-type {
      margin-right: 0;
    }
    .icon {
      width: 42px;
      height: 42px;
      float: left;
    }
    .right-side {
      margin-left: 55px;
      .lb {
        color: #000;
        font-size: 16px;
        margin-bottom: 6px;
        font-weight: bold;
      }
      .txt {
        font-size: 14px;
        color: #333;
      }
    }
  }
}
</style>
