<template>
  <div class="aboutus-container">
    <div class="banner">
      <p>智能 · 好用的车队管理系统</p>
    </div>
    <div class="main-content">
      <div class="row">
        <div class="video-box">
          <span v-if="showPauseIcon" class="play" @click="playVideo"></span>
          <video ref="video" controls controlslist="nodownload" width="1000px" height="612px">
            <source src="https://img.baochequan.cn/Uploads/Video/interview.mp4" type="video/mp4">
          </video>
        </div>
        <div class="info">
          <p class="title">关于车队助手</p>
          <div class="con">
            我们从事包车行业十余年
            <br>我们深知最为一个车队长的工作繁重
            <br> 我们希望以现代化的技术，减轻工作、提高效率、并且节省成本
            <br> 所以车队助手来了！
            <br> 成立之初
            <br> 我们就获得杭州市蒲公英基金和浙江最大的风投机构浙江天堂硅谷的战略投资
            <br> 我们信心满满，能为包车行业做点贡献。
            <br> 我相信车队助手是全中国车队最好用并且最实惠的车队管理软件
            <br> 我们覆盖云端、电脑端、PC端、IOS端、安卓端
            <br> 我们希望更多的车队长能从繁重的车务管理中解脱出来
            <br> 如果需要我们，请致电：400-688-1711
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'aboutUs',
  data () {
    return {
      video1Src: "https://img.baochequan.cn/Uploads/Video/add_people.mp4",
      videoImg: "https://img.baochequan.cn/images/2018-07-30/40303e13774339117502.jpg",
      showPauseIcon: true
    }
  },
  created () {
    this.repairBowCss()
  },
  mounted () {
    this.listeningVideoPlay()
  },
  methods: {
    playVideo () {
      this.$refs.video.play()
      this.showPauseIcon = false
    },
    listeningVideoPlay () {
      this.$refs.video.addEventListener("ended", () => {
        this.showPauseIcon = true
      })
    },
    repairBowCss () {
      console.log(navigator)
      if (navigator.userAgent.toLowerCase().indexOf("chrome") != -1) {
        this.showPauseIcon = false
      }
    }
  }
}
</script>
<style lang ="less" scoped>
@bannerSrc: 'https://img.baochequan.cn/images/2018-07-28/5d95a362f38f5f8655bc.jpg';
.aboutus-container {
  height: auto;
  .row {
    width: 870px;
    margin: 0 auto;
  }
  .banner {
    height: 474px;
    text-align: center;
    font-size: 42px;
    color: #fff;
    max-width: 100%;
    background: url(@bannerSrc) no-repeat left top / cover;
    p {
      padding-top: 167px;
    }
  }
  .video-box {
    width: 1000px;
    height: 612px;
    margin: -120px auto 0;
    cursor: pointer;
    position: relative;
    .play {
      position: absolute;
      left: 0;
      top: 0;
      bottom: 0;
      right: 0;
      margin: auto;
      z-index: 9;
      width: 89px;
      height: 89px;
      background: url('@{indexImgSrc}/play.png');
      cursor: pointer;
    }
  }
  .info {
    user-select: none;
    .title {
      font-size: 24px;
      color: #333;
      padding: 42px 0;
      margin-top: 98px;
      position: relative;
      &::before {
        content: '';
        position: absolute;
        left: 0;
        top: 0;
        width: 69px;
        height: 2px;
        background-color: @theme-color;
      }
    }
    .con {
      font-size: 14px;
      color: #000;
      line-height: 30px;
      margin-bottom: 126px;
      letter-spacing: 2px;
    }
  }
}
</style>
