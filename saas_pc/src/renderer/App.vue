<template>
  <div id="app" v-cloak>
    <sidebar v-if="token"></sidebar>
    <router-view class="view" :class="smallFontClass ? 'max-small': ''"></router-view>
    <updateBox :updateBoxShow.sync="updateBoxShow" :updateInfo=updateInfo></updateBox>
  </div>
</template>
<script>
import sidebar from '@/components/common/sidebar'
import updateBox from '@/components/updateBox'
import { mapActions, mapState } from 'vuex'
export default {
  name: 'App',
  components: {
    updateBox
  },
  data () {
    return {
      updateBoxShow: false,
      updateInfo: null,
      smallFontClass: true
    }
  },
  computed: {
    ...mapState({
      token: state => state.user.token
    }),
  },
  watch: {
    '$route': {
      handler (to, from) {
        if (to.name == 'accountDetails' || to.path.includes('index/')) {
          this.smallFontClass = false
        } else {
          this.smallFontClass = true
        }
      },
      immediate: true
    }
  },
  created () {
  
    if (this.token) this.getUserInfo()
    this.detectionUpdate()
  },
  methods: {
    ...mapActions([
      'getUserInfo',
    ]),
    detectionUpdate () {
      if (!process.env.IS_WEB) {
        import('electron').then(({ remote }) => {
          let token = localStorage.getItem('token')
          if (!token) return
          let versionCode = remote.getGlobal('sharedObject').versionCode
          let x = this.$util.getCPU()
          let type
          if (x == 'x64') {
            type = 1
          } else if (x == 'x86') {
            type = 2
          } else {
            type = null
          }
          this.$axios.get(this.$httpUrl.detectionUpdate, { params: { versionCode: versionCode, type: type } }).then(res => {
            if (res != false) {
              if (res.need_update) {
                this.updateBoxShow = true
                this.updateInfo = res
              }
            }
          })
        })
      }
    }
  }
}
</script>
<style lang ="less">
@import "./assets/fonts/iconfont.css";
@import "./assets/css/reset.css";
@import "./assets/css/comm.less";
@import "./assets/css/resetUI.less";

.max-small {
  div,
  span:not(.dot):not(.iconfont),
  applet,
  object,
  iframe,
  h1,
  h2,
  h3,
  h4,
  h5,
  h6,
  p,
  blockquote,
  pre,
  a,
  abbr,
  acronym,
  address,
  big,
  cite,
  code,
  del,
  dfn,
  em,
  img,
  ins,
  kbd,
  q,
  s,
  samp,
  small,
  strike,
  strong,
  sub,
  sup,
  tt,
  var,
  b,
  u,
  i,
  center,
  dl,
  dt,
  dd,
  ol,
  ul,
  li,
  fieldset,
  form,
  label,
  legend,
  table,
  caption,
  tbody,
  tfoot,
  thead,
  tr,
  th,
  td,
  article,
  aside,
  canvas,
  details,
  embed,
  figure,
  figcaption,
  footer,
  header,
  menu,
  nav,
  output,
  ruby,
  section,
  summary,
  time,
  mark,
  audio,
  video,
  input {
    font-size: 12px !important;
  }
}
</style>
