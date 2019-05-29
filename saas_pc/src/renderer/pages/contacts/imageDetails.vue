<template>
  <div id='imageDetails'>
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ name: 'customerOrigin' }">通讯录</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'customerOrigin',query: {customerType:customerType} }">{{companyTypeTxt}}</el-breadcrumb-item>
          <el-breadcrumb-item>{{customerInfo.company}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <div class="order-info">
        <div class="info-t">
          <div class="left-side">
            <h3 class="order-num">基本信息</h3>
          </div>
          <div class="right-side">
            <div class="btns">
              <a href="javascript:void(0)" @click='lookImage()' class="btn btn-bg lookImg">管理合同</a>
            </div>
          </div>
        </div>
        <div class="info-b" v-if="customerType == 1">
          <div class="info-list">
            <div class="col">
              <p class="line">单位名称：{{customerInfo.company}}</p>
              <p class="line">客户身份：{{customerInfo.type_text}}</p>
            </div>
            <div class="col">
              <p class="line">发票抬头：{{customerInfo.invoice_title}}</p>
              <p class="line">税 号：{{customerInfo.tax_number}}</p>
            </div>
          </div>
          <p class="remark">联系地址：{{customerInfo.address}}</p>
        </div>
        <div class="info-b" v-else>
          <div class="info-list">
            <div class="col">
              <p class="line">车队名称：{{customerInfo.company}}</p>
              <p class="line">计调姓名：{{customerInfo.name}}</p>
              <p class="line">客户身份：{{customerInfo.type_text}}</p>
              <p class="line">联系地址：{{customerInfo.address}}</p>
            </div>
            <div class="col">
              <p class="line">发票抬头：{{customerInfo.invoice_title}}</p>
              <p class="line">联系方式：{{customerInfo.mobile}}</p>
              <p class="line">税 号：{{customerInfo.tax_number}}</p>
            </div>
          </div>
        </div>
      </div>
      <div class='main-box'>
        合同照片
      </div> 
      <div class='imageList'>
        <div v-for='(item,index) in this.photoList' :key='index' class='images'>
          <img @click='checkImage(item.url)' :src='item.url' alt=''/>
          <p class='text'>{{item.title}}</p>
          <p class='time'>{{item.created_at}}</p>
        </div>
      </div> 
    </div>
    <div class="pages-wrap" v-if="this.photoList.length">
        <el-pagination :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
      </div>
    <imgDialog ref="imgDialog" :imgShow.sync='imgShow' :imgs='imgs'></imgDialog> 
  </div>
</template>
<script>
import imgDialog from '@/components/imgDialog'
  export default{
    data (){
      return{
        companyTypeTxt:'',
        customerType:'',
        customerInfo:{},
        page:1,
        photoList:[],
        maxPage:null,
         imgShow:false,
          imgs:''
      }
    },
    created(){
      this.getQueryMessage()
      this.getCustomerDetail()
      this.getPhotoList()
    },
    mounted(){

    },
    methods:{
       getQueryMessage(){
          let query = this.$route.query
          this.customerType=query.customerType
          if (this.customerType == 1) {
            this.companyTypeTxt = '客户'
            // this.travelParams.customer_id = query.customer_id
          } else {
            this.companyTypeTxt = '外援车队'
            // this.carParams.customer_id = query.customer_id
          }
       },
        getCustomerDetail () {
          let customer_id = this.$route.query.customer_id
          let params = {
            customer_id: customer_id
          }
          this.$axios.get(this.$httpUrl.customerInfo, { params: params }).then(res => {
            if (res != false) {
              this.customerInfo = res
            }
          })
        },
      getPhotoList(){
        let customer_id = this.$route.query.customer_id
          let params = {
            customer_id: customer_id,
            page:this.page
          }
          this.$axios.get(this.$httpUrl.photoList, { params: params }).then(res => {
            if (res != false) {
              console.log(res)
              // this.customerInfo = res
             
              if(res.list.length==0){
                this.$message("暂无更多数据")
              }else{
                this.photoList=res.list
              }
              this.maxPage=res.pages
            }
          })
      },
      changePage(page){
        this.page = page
        this.getPhotoList()
      },
      lookImage(){
        this.$router.push({name:'addImage',query:{customer_id:this.$route.query.customer_id,customerType:this.$route.query.customerType}})
      },
      checkImage(img){
        
         this.imgShow =true
          this.$nextTick(()=>{
            this.$refs.imgDialog.showToggle(img)
          })
      }
    },
    components:{
      imgDialog
    }
  }
</script>
<style lang="less" scoped>
  #imageDetails{
      height: 100%;
      background-color: #f5f5f5;
    .main-content {
      padding-top: 20px;
      height: calc(100% - 80px);
      box-sizing: border-box;
      background-color: transparent;
      .order-info {
        padding: 20px 30px 0 30px;
        background-color: #fff;
        .info-t {
          overflow: hidden;
        }
        .btn-fffbg {
          margin-right: 0;
        }
        .left-side {
          float: left;
          margin-bottom: 20px;
          .icon-dingdan {
            color: @theme-color;
            font-size: 22px;
            vertical-align: -2px;
            margin-right: 14px;
          }
          .order-num {
            font-size: 20px;
            color: #000;
            font-weight: bold;
            display: inline-block;
          }
        }
        .right-side {
          float: right;
        }
        .info-b {
          position: relative;
          .info-list {
            margin-left: 42px;
            overflow: hidden;
          }
          .col {
            float: left;
            width: 33.33%;
            .line {
              margin-bottom: 10px;
            }
          }
          .summary {
            position: absolute;
            top: 10px;
            right: 0;
            p {
              float: left;
              text-align: right;
              color: #333;
              margin-left: 30px;
              span {
                margin-top: 10px;
                display: block;
                color: #000;
                font-size: 20px;
              }
            }
          }
        }
      }
    }
    .remark {
      margin-left: 42px;
      padding-bottom: 16px;
      word-break: break-all;
    }
    .main-box{
      padding-left:29px;
      width:98%;
      height:39px;
      margin:9px auto 0;
      background:#FFFFFF;
      line-height:39px;
      font-size:16px;
      font-weight:600;
      color:#000000;
    }
    .imageList{
      width:100%;
      margin-left:39px;
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
  }
</style>