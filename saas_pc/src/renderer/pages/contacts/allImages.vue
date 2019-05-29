<template>
  <div class='allImage'> 
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>合同图片</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class='imageList'>
      <div class='inp-wrap'>
          <el-input  suffix-icon="iconfont icon-shujuchaxun" v-model="keywords" placeholder="请输入客户名称/单位"></el-input>
      </div>
      <div @click='searchBtn' class='search'>
        搜索
      </div> 
      <div class='selected'>
        <el-date-picker
          v-model="startTime"
          type="month"
          @change='changeTime()'
          value-format="yyyy-MM"
          placeholder="选择年月">
        </el-date-picker>
      </div>
      <div class='addOrder' @click='addImage()'>
        管理合同
      </div>
      <!-- <div class='manage'>
        管理合同
      </div>  -->
    </div>
    <div class='titleH'>
          合同图片
    </div>
    <div class='imagesList'>
        <div v-for='(item,index) in this.photoList' :key='index' class='images'>
          <img @click='lookPhoto(item.url)' :src='item.url' alt=''/>
          <p class='text'>{{item.title}}</p>
          <p class='time'>{{item.created_at}}</p>
        </div>
      </div> 
      <dataBlank v-show='this.photoList.length<=0'  dataType="photo"></dataBlank>
    <div class="pages-wrap" v-if="this.photoList.length">
        <el-pagination  :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
      </div>
      <imgDialog ref="imgDialog" :imgShow.sync='imgShow' :imgs='imgs'></imgDialog> 
  </div>
  
</template>  
<script>
  import dataBlank from '@/components/common/dataBlank.vue'
  import imgDialog from '@/components/imgDialog'
  export default{
    data(){
      return{
        page:1,
        keywords:'',
        startTime:'',
        photoList:[],
        maxPage:null,
        imgShow:false,
        imgs:''
      }
    },
   
    created(){
      this.getPhotoList()
    },
    mounted(){

    },
    methods:{
      getPhotoList(){
        let customer_id = this.$route.query.customer_id
          let params = {
            customer_id: customer_id,
            page:this.page,
            keywords:this.keywords,
            time:this.startTime
          }
          this.$axios.get(this.$httpUrl.photoList, { params: params }).then(res => {
            if (res != false) {
              // console.log(res)
              // this.customerInfo = res
              if(res.list.length==0){
                this.$message("暂无更多数据")
                this.photoList=res.list
              }else{
                this.photoList=res.list
              }
              this.maxPage=res.pages
            }
          })
      },
      changePage(page){
        // console.log(page)
        this.page = page
      this.getPhotoList()
      },
      changeTime(){
        let params = {
          time:this.startTime,
        }
        this.$axios.get(this.$httpUrl.photoList, { params: params }).then(res => {
            if (res != false) {
              if(res.list.length==0){
                this.$message("暂无更多数据")
                 this.photoList=res.list
              }else{
                 this.photoList=res.list
              }
              this.maxPage=res.pages
            }
          })
      },
      searchBtn(){
        // console.log(this.keywords)
        let params={
          keywords:this.keywords,
          page:this.page
        }
        this.$axios.get(this.$httpUrl.photoList, { params: params }).then(res => {
            if (res != false) {
              if(res.list.length==0){
                this.$message("暂无更多数据")
                 this.photoList=res.list
              }else{
                 this.photoList=res.list
              }
              this.maxPage=res.pages
            }
        })
      },
      addImage(){
        this.$router.push({name:'addImage'})
      },
      lookPhoto(img){
          this.imgShow =true
        this.$nextTick(()=>{
          this.$refs.imgDialog.showToggle(img)
        })
      }
    },
    components:{
      dataBlank,
      imgDialog
    }
  }
</script>
<style lang="less" scope>
  .allImage{
    min-height:100%;
    background:#F5F5F5;
    .imageList{
      width:100%;
      padding-top:13px;
      background:#F5F5F5;
      display:flex;
      .inp-wrap {
        width: 242px;
        height:32px;
        margin-left:27px;
        .el-input--suffix{
          .el-input__inner {
            padding-left: 12px;
            height:32px!important;
          }
        }
        .el-input__suffix {
          top: -4px;
        }
      }
      .selected{
        .el-input__inner{
          width:129px;
          height:32px;
          line-height:32px;
          margin-left:26px;
        }
        .el-input__prefix{
          display:none;
        }
      }
      .el-date-editor--month{
         width:155px;
         line-height:32px;
      }
      .el-input__prefix{
        line-height:32px;
      }
      .addOrder{
        width:115px;
        height:32px;
        line-height:32px;
        text-align:center;
        background:#0DB592;
        font-size:14px!important;
        color:#FFFFFF;
        font-weight:400;
        cursor:pointer;
        border-radius:3px;
        margin-left:28px;
      }
      .manage{
        width:113px;
        height:30px;
        margin-left:20px;
        line-height:30px;
        text-align:center;
        background:#F5F5F5;
        font-size:14px!important;
        color:#0DB592;
        font-weight:400;
        cursor:pointer;
        border:1px solid #0DB592;
        border-radius:3px;
      }
    }
    .titleH{
      width:96%;
      height:39px;
      padding-left:20px;
      background:#FFFFFF;
      line-height:39px;
      margin:15px auto;
    }
    .imagesList{
      // width:100%;
      margin-left:27px;
      margin-top:10px;
      .images{
        width:159px;
        height:178px;
        border-radius:3px;
        background:#FFFFFF;
       box-shadow:1px 1px 3px 0px rgba(0,0,0,0.3);
        margin-right:10px;
         margin-top:10px;
         float:left;
        img{
          width:159px;
          height:133px;
        }
        .text{
          width:154px;
          margin-left:5px;
          margin-top:5px;
          font-size:14px!important;
          font-weight:400;
          color:#000000; 
        }
        .time{
          margin-left:5px;
          font-size:12px!important;
          color:#888888;
        }
      }
    }
    .search{
      height: 32px;
      line-height: 32px;
      width: 60px;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      text-align: center;
      border-radius: 4px;
      color: #333;
      font-size: 14px;
      border: 1px solid #999;
      margin-left:12px;
      cursor:pointer;
    }
  }
</style>