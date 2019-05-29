<template>
  <div class="dynamicList">
    <router-link tag="div" :to="{name: 'dynamicDetail',query: {article_id: article.article_id}}" class="dynamic" v-for="(article, index) in articleList" :key="index">
      <p class="tt">{{article.title}}</p>
      <p class="date">{{article.inside_time}}</p>
      <div class="con">
        {{article.content}}
      </div>
    </router-link>
    <div class="loadmore" @click="addPages" v-if="maxPage && page < maxPage">
      点击加载更多
    </div>
  </div>
</template>
<script>

export default {
  name: 'dynamicList',
  data () {
    return {
      articleList: [],
      maxPage: 1,
      page: 1
    }
  },
  watch: {
    page () {
      this.getArticleList()
    }
  },
  created () {
    this.getArticleList()
  },
  methods: {
    addPages () {
      this.page++
    },
    getArticleList () {
      let page = this.page
      if (page > this.maxPage) return
      this.$axios.get(this.$httpUrl.articleList, { params: { type: 14, page: this.page } }).then(res => {
        if (res) {
          this.articleList = this.articleList.concat(res.list)
          this.maxPage = res.pages
        }
      })
    }
  }
}
</script>
<style lang ="less" scoped>
.dynamicList {
  width: 1000px;
  margin: 0 auto;
  padding-bottom: 80px;
  .dynamic {
    border-bottom: 1px solid #efefef;
    cursor: pointer;
    .tt {
      color: #333;
      font-size: 18px;
      padding: 36px 0 18px 0;
    }
    .date {
      font-size: 14px;
      color: #bbb;
    }
    .con {
      height: 72px;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
      margin: 35px 0 20px 0;
      font-size: 14px;
      color: #999;
      letter-spacing: 1px;
      line-height: 24px;
      overflow: hidden;
    }
  }
  .loadmore {
    margin-top: 50px;
    height: 56px;
    line-height: 56px;
    border: 1px solid #efefef;
    text-align: center;
    color: #bbb;
    font-size: 16px;
    cursor: pointer;
  }
}
</style>
