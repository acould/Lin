<template>
  <div class="dynamicDetail">
    <div class="row">
      <h4 class="tt">{{info.title}}</h4>
      <p class="date">{{info.created_at}}</p>
      <div class="con" v-html="info.content" ondragstart="return false">

      </div>
    </div>
  </div>
</template>
<script>

export default {
  name: 'dynamicDetail',
  data () {
    return {
      info: {}
    }
  },
  created () {
    this.getDetialInfo()
  },
  methods: {
    getDetialInfo () {
      let article_id = this.$route.query.article_id
      if (!article_id) return
      this.$axios.get(this.$httpUrl.articleDetail, { params: { article_id: article_id } }).then(res => {
        if (res) {
          this.info = res 
        }
      })
    }
  }
}
</script>
<style lang ="less" scoped>
.dynamicDetail {
  margin-top: 50px;
  padding-bottom: 140px;
  .row {
    width: 1000px;
    margin: 0 auto;
    overflow: hidden;
    .tt {
      font-size: 28px;
      color: #333;
      line-height: 40px;
    }
    .date {
      font-size: 16px;
      color: #999;
      margin: 20px 0 46px 0;
    }
    .con {
      color: #333;
      font-size: 16px;
    }
  }
}
</style>
<style lang="less">
.dynamicDetail {
  .con {
    user-select: none;
    img {
      max-width: 100%;
    }
  }
}
</style>

