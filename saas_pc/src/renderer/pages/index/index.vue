<template>
  <div class="index-container">
    <div class="main-content">
      <div class="swiper-container" id="swiper-container">
        <div class="swiper-wrapper">
          <div class="swiper-slide">
            <div class="banner">
              <div class="row">
                <p class="text">已经有超过10000家车队</p>
                <p class="text text-bold">选择车队助手管理车队</p>
                <a href="javascript:void(0)" class="banner-btn" @click="rtnLogin">立即体验</a>
                <span class="play" @click="slideVideo"></span>
              </div>
            </div>
          </div>
          <div class="swiper-slide">
            <div class="banner banner-item-2">
              <div class="row">
                <div class="video-wrap">
                  <span v-if="showPauseIcon" class="play" @click="playVideo"></span>
                  <video ref="video" controls controlslist="nodownload" width="1000px" height="550px">
                    <source src="https://img.baochequan.cn/Uploads/Video/propaganda.mp4" type="video/mp4">
                  </video>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 如果需要分页器 -->
        <div class="swiper-pagination swiper-pagination-white"></div>
        <!-- 如果需要导航按钮 -->
        <div class="swiper-button-prev swiper-button-white"></div>
        <div class="swiper-button-next swiper-button-white"></div>
      </div>

      <div class="show-wrap">
        <div class="section-line">
          <div class="row">
            <img :src="bg0Src" alt="">
            <div class="txt-wrap">
              <p class="txt-t">待办事项</p>
               <p class="txt-f">语音自动识别</p>
              <p class="txt-f">快速记事和日程管理</p>
              <a href="javascript:void(0);" class="section-line-btn" @click="rtnLogin">立即体验</a>
            </div>
          </div>
        </div>
        <div class="section-line">
          <div class="row">
            <img :src="bg1Src" alt="">
            <div class="txt-wrap">
              <p class="txt-t">电脑手机同步</p>
              <p class="txt-f">随时随地使用</p>
              <a href="javascript:void(0);" class="section-line-btn" @click="rtnLogin">立即体验</a>
            </div>
          </div>
        </div>
        <div class="section-line">
          <div class="row">
            <img :src="bg2Src" alt="">
            <div class="txt-wrap">
              <p class="txt-t">阿里云技术支撑</p>
              <p class="txt-f">数据永不丢失</p>
              <a href="javascript:void(0);" class="section-line-btn" @click="rtnLogin">立即体验</a>
            </div>
          </div>
        </div>
        <div class="section-line">
          <div class="row">
            <img :src="bg3Src" alt="">
            <div class="txt-wrap">
              <p class="txt-t">车队专属下单系统</p>
              <p class="txt-f">为每个车队打造下单首页</p>
              <a href="javascript:void(0);" class="section-line-btn" @click="rtnLogin">立即体验</a>
            </div>
          </div>
        </div>
        <div class="section-line">
          <div class="row">
            <img :src="bg4Src" alt="">
            <div class="txt-wrap">
              <p class="txt-t">成本运算系统</p>
              <p class="txt-f">外调、费用成本一目了然</p>
              <a href="javascript:void(0);" class="section-line-btn" @click="rtnLogin">立即体验</a>
            </div>
          </div>
        </div>
        <div class="section-line">
          <div class="row">
            <img :src="bg5Src" alt="">
            <div class="txt-wrap">
              <p class="txt-t">运力实时展现</p>
              <p class="txt-f">瞬间掌握空车数据</p>
              <a href="javascript:void(0);" class="section-line-btn" @click="rtnLogin">立即体验</a>
            </div>
          </div>
        </div>
        <div class="section-line">
          <div class="row">
            <img :src="bg6Src" alt="">
            <div class="txt-wrap">
              <p class="txt-t">全智能机器人通知</p>
              <p class="txt-f">免去司机和客户多个电话</p>
              <a href="javascript:void(0);" class="section-line-btn" @click="rtnLogin">立即体验</a>
            </div>
          </div>
        </div>
        <div class="section-line">
          <div class="row">
            <img :src="bg7Src" alt="">
            <div class="txt-wrap">
              <p class="txt-t">外援结算</p>
              <p class="txt-f">同行借车被借 轻松对账</p>
              <a href="javascript:void(0);" class="section-line-btn" @click="rtnLogin">立即体验</a>
            </div>
          </div>
        </div>
        <div class="section-line">
          <div class="row">
            <img :src="bg8Src" alt="">
            <div class="txt-wrap">
              <p class="txt-t">智慧账单</p>
              <p class="txt-f">对账一目了然</p>
              <a href="javascript:void(0);" class="section-line-btn" @click="rtnLogin">立即体验</a>
            </div>
          </div>
        </div>
        <div class="section-line">
          <div class="row">
            <img :src="bg9Src" alt="">
            <div class="txt-wrap">
              <p class="txt-t">车辆管理</p>
              <p class="txt-f">保养 保险 年审定时提醒</p>
              <a href="javascript:void(0);" class="section-line-btn" @click="rtnLogin">立即体验</a>
            </div>
          </div>
        </div>
        <div class="section-line">
          <div class="row">
            <div class="corage-wrap">
              <p class="section-tt">使用教程</p>
              <div class="video-wrap">
                <div class="video-txt">
                  <p :class="videoOption == 1 ? 'active': ''" @click="switchVideoOption(1)">客户和司机的添加、修改、删除</p>
                  <p :class="videoOption == 2 ? 'active': ''" @click="switchVideoOption(2)">车辆任务的添加、修改、删除</p>
                  <p :class="videoOption == 3 ? 'active': ''" @click="switchVideoOption(3)">调度</p>
                  <p :class="videoOption == 4 ? 'active': ''" @click="switchVideoOption(4)">如何对账单进行结款操作</p>
                </div>
              </div>
            </div>
            <div class="play-box">
              <div class="video-box">
                <video controls controlslist="nodownload" v-show="videoOption == 1 ">
                  <source :src="video1Src" type="video/mp4">
                </video>
                  <video controls controlslist="nodownload" v-show="videoOption == 2 ">
                    <source :src="video2Src" type="video/mp4">
                </video>
                    <video controls controlslist="nodownload" v-show="videoOption == 3">
                      <source :src="video3Src" type="video/mp4">
                </video>
                      <video controls controlslist="nodownload" v-show="videoOption == 4 ">
                        <source :src="video4Src" type="video/mp4">
                </video>
              </div>
            </div>
          </div>
        </div>
        <div class="section-line">
          <div class="row">
            <div class="dynamic-wrap">
              <p class="section-tt">公司动态</p>
              <ul class="dynamic-list">
                <router-link :to="{name: 'dynamicDetail',query:{article_id:article.article_id}}" v-for="(article, index) in  articleList" :key="index" tag="li">
                  <span class="info">{{article.title}}</span>
                  <span class="date">{{article.out_time}}</span>
                </router-link>
              </ul>
            </div>
          </div>
        </div>
        <div class="section-line">
          <div class="row">
            <div class="case-wrap">
              <p class="section-tt">合作案例</p>
              <ul class="caselist">
                <li v-for="(item, index) in 8" :key="index"></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="preload-image" style="display:none">
      <img src="https://img.baochequan.cn/images/2018-09-18/e2d99aa3ed6a89de1c1f.png" alt="">
    </div>
    </div>
</template>
<script>
import Swiper from 'swiper';
import 'swiper/dist/css/swiper.min.css';
import video from '@/assets/images/index/video.png'
export default {
  name: 'index',
  data () {
    return {
      bg0Src: "https://img.baochequan.cn/images/2018-10-08/962fb43e5caae8861cb2.png",
      bg1Src: "https://img.baochequan.cn/images/2018-07-27/dc0ab06a0c8e2ae54048.png",
      bg2Src: "https://img.baochequan.cn/images/2018-07-27/8d7e5eb8fa1fcf5408ba.png",
      bg3Src: "https://img.baochequan.cn/images/2018-07-27/2ee32bedab000630ae76.png",
      bg4Src: "https://img.baochequan.cn/images/2018-07-27/f77380718eeef470752d.png",
      bg5Src: "https://img.baochequan.cn/images/2018-07-27/063b4a670450f7f78abb.png",
      bg6Src: "https://img.baochequan.cn/images/2018-07-27/bae52a3d11295c323583.png",
      bg7Src: "https://img.baochequan.cn/images/2018-07-27/b9356ece24f641f47849.png",
      bg8Src: "https://img.baochequan.cn/images/2018-07-27/434ff344e6bbd4151925.png",
      bg9Src: "https://img.baochequan.cn/images/2018-07-27/63a3147d1af0c79b2d4b.png",
      video1Src: "https://img.baochequan.cn/Uploads/Video/add_people.mp4",
      video2Src: "https://img.baochequan.cn/Uploads/Video/add_task.mp4",
      video3Src: "https://img.baochequan.cn/Uploads/Video/dispatch.mp4",
      video4Src: "https://img.baochequan.cn/Uploads/Video/bill.mp4",
      videoOption: 1,
      articleList: [],
      showPauseIcon: true,
      swiper: null
    }
  },
  created () {
    this.getArticleList()
    this.repairBowCss()
  },
  mounted () {
    this.initSwiper()
    this.listeningVideoPlay()

  },
  methods: {
    initSwiper () {
      this.swiper = new Swiper('.swiper-container', {
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev',
        },
        pagination: {
          el: '.swiper-pagination',
        },
      })
    },
    slideVideo () {
      this.swiper.slideTo(1, 400, false)
    },
    getArticleList () {
      this.$axios.get(this.$httpUrl.articleList, { params: { type: 14 } }).then(res => {
        if (res) {
          this.articleList = res.list
        }
      })
    },
    switchVideoOption (num) {
      this.videoOption = num
    },
    rtnLogin () {
      this.$router.push({ name: 'schedule' })
    },
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
@bannerSrc: "https://img.baochequan.cn/images/2018-07-27/9403e74cf0cb2c927155.jpg";
@bannerSrc2: "https://img.baochequan.cn/images/2018-07-28/5d95a362f38f5f8655bc.jpg";
@rowWidth: 1000px;
.index-container {
  #swiper-container {
    min-width: 1200px;
    margin: 0 auto;
    height: 600px;
    .swiper-button-next {
      right: 110px;
    }
    .swiper-button-prev {
      left: 110px;
    }
  }
  .row {
    width: @rowWidth;
    margin: 0 auto;
  }
  .main-content {
    .banner {
      height: 600px;
      width: 100%;
      background: url(@bannerSrc) no-repeat left top / cover;
      position: relative;
      .play {
        position: absolute;
        top: 256px;
        right: 605px;
        z-index: 9;
        width: 89px;
        height: 89px;
        cursor: pointer;
        background: url("@{indexImgSrc}/play.png");
      }
      &.banner-item-2 {
        background: url(@bannerSrc2) no-repeat left top / cover;
        position: relative;
        .play {
          left: 0;
          top: 0;
          bottom: 0;
          right: 0;
          margin: auto;
        }
        .row {
          padding-top: 20px;
        }
        .video-wrap {
          width: 1000px;
          height: 550px;
          margin: 0 auto;
        }
      }
      .row {
        padding-top: 123px;
        .text {
          font-size: 48px;
          color: #fff;
          margin-bottom: 20px;
          &-bold {
            font-size: 53px;
            font-weight: bold;
          }
        }
        .banner-btn {
          display: block;
          width: 250px;
          height: 73px;
          line-height: 73px;
          margin-top: 89px;
          text-align: center;
          font-size: 26px;
          border: 1px solid #fff;
          color: #fff;
          border-radius: 6px;
        }
      }
    }
    .section-line {
      overflow: hidden;
      &:nth-child(2n) {
        background-color: #fff;
        img {
          right: 0
        }
        .txt-wrap {
          float: left;
        }
      }
      &:nth-child(2n + 1) {
        background-color: #fafafa;
        img {
           left: 0;
        }
        .txt-wrap {
          float: right;
        }
      }
      &:nth-child(1) {
        height: 750px;
        .row {
          margin-top: 64px;
        }
      }
      &:nth-child(2) {
        height: 540px;
        .row {
          margin-top: 64px;
        }
      }
      &:nth-child(3) {
        height: 548px;
        .row {
          margin-top: 34px;
        }
        .txt-t {
          padding-top: 60px;
        }
      }
      &:nth-child(4) {
        height: 587px;
        .row {
          margin-top: 44px;
        }
        .txt-t {
          padding-top: 60px;
        }
      }
      &:nth-child(5) {
        height: 643px;
        .row {
          margin-top: 34px;
        }
        .txt-t {
          padding-top: 26px;
        }
      }
      &:nth-child(6) {
        height: 575px;
        .row {
          margin-top: 34px;
        }
        .txt-t {
          padding-top: 66px;
        }
      }
      &:nth-child(7) {
        height: 642px;
        .row {
          margin-top: 34px;
        }
        .txt-t {
          padding-top: 26px;
        }
      }
      &:nth-child(8) {
        height: 640px;
        .row {
          margin-top: 70px;
        }
        .txt-t {
          padding-top: 30px;
        }
      }
      &:nth-child(9) {
        height: 585px;
        .row {
          margin-top: 34px;
        }
        .txt-t {
          padding-top: 40px;
        }
      }
      &:nth-child(10) {
        height: 643px;
        .row {
          margin-top: 57px;
        }
        .txt-t {
          padding-top: 20px;
        }
      }
      .row {
        position: relative;
      }
      img {
        position: absolute;
        top: 0;
      }
      .txt-t {
        font-size: 48px;
        color: #333;
        margin-bottom: 15px;
      }
      .txt-f {
        font-size: 36px;
        color: #555;
      }
      .section-line-btn {
        display: block;
        width: 180px;
        height: 50px;
        line-height: 50px;
        margin-top: 26px;
        text-align: center;
        font-size: 18px;
        color: @theme-color;
        border: 1px solid @theme-color;
        border-radius: 4px;
      }
      .section-tt {
        position: relative;
        color: #333;
        font-size: 26px;
        padding-top: 55px;
        padding-bottom: 17px;
        margin-bottom: 40px;
        display: inline-block;
        &::after {
          content: "";
          position: absolute;
          bottom: 0;
          left: 0;
          width: 87px;
          height: 4px;
          background-color: @theme-color;
          border-radius: 4px;
        }
      }
      .dynamic-list {
        overflow: hidden;
        padding-bottom: 75px;
        li {
          float: left;
          overflow: hidden;
          margin-bottom: 27px;
          font-size: 18px;
          cursor: pointer;
          &:nth-of-type(2n + 1) {
            margin-right: 60px;
          }
          .info {
            color: #333;
            float: left;
            width: 380px;
            white-space: nowrap;
            text-overflow: ellipsis;
            margin-right: 30px;
          }
          .date {
            float: left;
            color: #999;
          }
        }
      }
      .case-wrap {
        height: 460px;
        .caselist {
          overflow: hidden;
          li {
            float: left;
            width: 240px;
            height: 110px;
            box-sizing: border-box;
            margin: 0 12px 13px 0;
            background: url("@{indexImgSrc}/case1.png") no-repeat left top /
              240px 110px;
            &:nth-of-type(4n) {
              margin-right: 0;
            }
            &:nth-of-type(2) {
              background-image: url("@{indexImgSrc}/case2.png");
            }
            &:nth-of-type(3) {
              background-image: url("@{indexImgSrc}/case3.png");
            }
            &:nth-of-type(4) {
              background-image: url("@{indexImgSrc}/case4.png");
            }
            &:nth-of-type(5) {
              background-image: url("@{indexImgSrc}/case5.png");
            }
            &:nth-of-type(6) {
              background-image: url("@{indexImgSrc}/case6.png");
            }
            &:nth-of-type(7) {
              background-image: url("@{indexImgSrc}/case7.png");
            }
            &:nth-of-type(8) {
              background-image: url("@{indexImgSrc}/case8.png");
            }
          }
        }
      }
      .corage-wrap {
        height: 640px;
        float: left;
        .video-txt {
          p {
            width: 281px;
            height: 62px;
            line-height: 64px;
            box-sizing: border-box;
            background-color: #fff;
            border-bottom: 1px solid #e0e0e0;
            text-align: center;
            font-size: 16px;
            color: #333;
            cursor: pointer;
            &.active {
              color: #fff;
              border-bottom-color: @theme-color;
              background-color: @theme-color;
            }
          }
        }
      }
      .play-box {
        float: right;
        width: 527px;
        height: 425px;
        background-color: @theme-color;
        .video-box {
          width: 247px;
          height: 503px;
          margin: 66px auto 0;
          background: url("@{indexImgSrc}/videotemplate.png") no-repeat left top /
            247px 503px;
          position: relative;
          video {
            position: absolute;
            width: 100%;
            height: 100%;
            padding: 14px;
            box-sizing: border-box;
          }
        }
      }
    }
  }
}
</style>
