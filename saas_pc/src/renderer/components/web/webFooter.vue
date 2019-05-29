<template>
  <div class="footer">
    <div class="row">
      <ul class="links">
        <ul class="nav">
          <router-link tag="li" :to="{name: 'index'}">
            首页
          </router-link>
          <router-link tag="li" :to="{name: 'aboutUs'}">
            关于我们
          </router-link>
          <router-link tag="li" :to="{name: 'help'}">
            帮助中心
          </router-link>
          <router-link tag="li" :to="{name: 'dynamicList'}">
            动态列表
          </router-link>
        </ul>
      </ul>
      <div class="info">
        <div class="row">
          <div class="left-side">
            <p class="tel">400-688-1711</p>
            <p class="time">（周一至周六9:00 — 18:00）</p>
            <p class="address">地址：浙江省杭州市余杭区龙潭路天时科创园3号楼801</p>
          </div>
          <div class="right-side">
            <a href="javascript:void(0);" class="btn links-download" @click="downloadRtn">APP下载</a>
            <a href="javascript:void(0);" class="btn links-download" @click="pcDownload">PC客户端下载</a>
          </div>
        </div>
      </div>
      <div class="copyright">
        copyright © 2017-2018 杭州立刻网络科技有限公司浙ICP 备17060403号-2
      </div>
    </div>
    <select-app-download  :select-app-box-show.sync="selectAppBoxShow"/>
  </div>
</template>
<script>

export default {
  name: 'webFooter',
  data () {
    return {
      selectAppBoxShow: false
    }
  },
  created () {

  },
  methods: {
    downloadRtn () {
      this.selectAppBoxShow = true
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
    }
  }
}
</script>
<style lang ="less" scoped>
@rowWidth: 1000px;
.footer {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: @footerHeight;
  background-color: #333;
  .row {
    width: @rowWidth;
    margin: 0 auto;
  }
  .links {
    overflow: hidden;
    margin-left: -20px;
    padding-top: 13px;
    li {
      float: left;
      width: 80px;
      text-align: center;
      color: #fff;
      font-size: 16px;
      padding: 20px 0;
      margin-right: 50px;
      cursor: pointer;
    }
  }
  .info {
    color: #cacaca;
    .row {
      padding-bottom: 20px;
      border-bottom: 1px solid #535353;
      overflow: hidden;
    }
    .tel {
      font-size: 35px;
      margin-bottom: 14px;
    }
    .time {
      margin-bottom: 19px;
    }
    .time,
    .address {
      font-size: 16px;
    }
    .left-side {
      float: left;
    }
    .right-side {
      float: right;
      .links-download {
        width: 161px;
        height: 45px;
        line-height: 45px;
        margin-bottom: 20px;
        &:last-of-type {
          margin-bottom: 0;
        }
      }
    }
  }
  .copyright {
    margin-top: 20px;
    font-size: 16px;
    color: #cbcbcb;
    text-align: center;
  }
}
</style>
