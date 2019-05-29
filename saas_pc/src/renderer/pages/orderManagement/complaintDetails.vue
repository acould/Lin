<template>
  <div class="complaintDetails">
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="back">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>{{orderInfo.company}}</el-breadcrumb-item>
          <!-- <el-breadcrumb-item :to="{ name: 'customerOrigin',query: {customerType:customerType} }">{{companyTypeTxt}}</el-breadcrumb-item> -->
          <el-breadcrumb-item>投诉详情</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="main-content">
      <div v-if='this.$route.query.order_id!=""' class="order-info">
        <div class="info-t">
          <div class="left-side">
            <span class="iconfont icon-dingdan"></span>
            <div @click="jumpDetails(orderInfo.order_type,orderInfo.saas_order_id)" class="order-num">
              订单编号：{{orderInfo.order_num}}
            </div>
          </div>
          <div class="right-side">
            <div  class="btns">
                <div  class="btn" @click="jumpDetails(orderInfo.order_type,orderInfo.saas_order_id)">
              查看详情
            </div>
            </div>
          </div>
        </div>
        <div class="info-b">
          <div class="info-list">
            <div class="col">
              <p class="line">行程日期：{{orderInfo.travel_begin_at}}至{{orderInfo.travel_end_at}}</p>
            </div>
            <div class="col">
              <p class="line">客户单位：{{orderInfo.company}}</p>
            </div>
          </div>
          <p v-show='orderInfo.drivers.length' v-for='(item,index) in orderInfo.drivers' :key='index' class="remark">司机:&nbsp;{{item.driver_name}}&nbsp;&nbsp;{{item.car_number}}</p>
          <p class="remark">行程：{{orderInfo.travel_content}}</p>
        </div>
      </div>
      <div class='answer'> 
        <el-input v-model="backmessage" placeholder="请输入回复信息"></el-input>
        <div @click ='replayAnswer()' class='btn'>回复</div>
      </div>
      <div v-for='(item,index) in complaintDetail' class='message' :key='index'>
        <p class='mes'>
          <span v-show='item.send_type==0'>{{item.company}}</span>
          <span v-show='item.send_type==1'>{{item.title}}</span>
          <span class='time'>{{item.created_at}}</span>
        </p>
        <p class='text'>{{item.content}}</p>
        <img v-show='item.image1' @click='lookImgs(item.image1)' :src='item.image1' alt='' />
        <img v-show='item.image2' @click='lookImgs(item.image1)' :src='item.image2' alt='' />
        <img v-show='item.image3' @click='lookImgs(item.image1)' :src='item.image3' alt='' />
      </div>
    </div>
    <div class='emptyBox'>

    </div>
    <div @click='refreshLish()' class='refresh'>
        <img src="../../assets/images/refresh.png" alt="">
      </div>
    <div class="pages-wrap" v-if="complaintDetail.length">
        <el-pagination @current-change="changePage" :page-count="maxPages" background layout="prev, pager, next"></el-pagination>
      </div>
    <imgDialog ref="imgDialog" :imgShow.sync='imgShow' :imgs='imgs'></imgDialog> 
  </div>
</template>
<script>
  import imgDialog from '@/components/imgDialog'

  export default{
    data(){
      return{
        orderInfo:{},
        backmessage:'',
        page:1,
        maxPages:null,
        complaintDetail:[],
        imgShow:false,
        imgs:'',
        id:'',
      }
    },
    created(){
      if(this.$route.query.complainant_id){
        this.getComplaitDetails()
      }
      
    },
  
    mounted () {
      
    },
    methods:{
      getComplaitDetails(){
        let params={
          complainant_id:this.$route.query.complainant_id,
          order_id: this.$route.query.order_id,
          page:this.page

        }
        this.$axios.get(this.$httpUrl.complaintDetails, { params: params }).then(res => {
          if (res) {
              this.orderInfo= res.order
              this.complaintDetail =res.list
              this.maxPages=res.pages
              this.id =res.list[0].id
              this.$message('数据更新完毕')
          }
        })
      },
      changePage(page){
        this.page= page 
        this.getComplaitDetails()
      },
      lookImgs(img){
         this.imgShow =true
          this.$nextTick(()=>{
            this.$refs.imgDialog.showToggle(img)
          })
      },
      replayAnswer(){
        let params = {
          content:this.backmessage,
          order_id:this.$route.query.order_id,
          complainant_id:this.$route.query.complainant_id,
          pid:this.complaintDetail[0].id
        }
        this.$axios.post(this.$httpUrl.replayComplaint,params).then(res=>{
          if(res){
            console.log(res)
            this.$message('回复成功')
            this.backmessage=''
            this.getComplaitDetails()
          }
        })
      },
      back(){
        this.$router.push({name:'orderManagement',query:{value:2}})
      },
      refreshLish(){
        if(this.$route.query.complainant_id){
          this.getComplaitDetails()
        }
      },
      jumpDetails(orderType,order_id){
        if(orderType==1){
          this.$router.push({name: 'accountDetails', query: {order_id:order_id}})
        }else if(orderType==2){
          this.$router.push({name: 'TransportStationDetail', query: {order_id:order_id}})
        }else if(orderType==3){
          this.$router.push({name: 'shuttleBusDetail', query: {order_id:order_id}})
        }
      }
    },
    components:{
      imgDialog
    }
  }
</script>
<style lang="less" scope>
.complaintDetails{
  .main-content {
    margin-top: 15px;
    background-color: #F5F5F5;
    min-width: 800px;
    overflow-x: hidden;
    min-height: calc(100% - 160px);
    .order-info {
      padding: 20px 30px 0 30px;
      background-color: #fff;
      height: 179px;
      .info-t {
        overflow: hidden;
      }
      .btn-bg {
        margin-right: 0;
      }
      .left-side {
        float: left;
        margin-bottom: 10px;
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
          cursor: pointer;
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
      }
    }
    .remark {
      margin-left: 42px;
      padding-bottom: 10px;
      word-break: break-all;
    }
    .log-container {
      margin: 10px 16px 0;
      background-color: #fff;
      padding: 0 10px;
      min-height: calc(100% - 150px);
      .handler {
        height: 32px;
        line-height: 32px;
        padding: 16px 0;
        min-width: 800px;
        .btn-search {
          display: inline-block;
          margin: 0 20px 0 10px;
          height: 32px;
          line-height: 30px;
          width: 60px;
          box-sizing: border-box;
          text-align: center;
          border-radius: 4px;
          color: @color-dark-grey;
          font-size: @fontsize-medium;
          border: 1px solid #999;
        }
      }
    }
    .answer{
      min-width:967px;
      height:54px;
      background-color:#fff;
      margin-top:10px;
      position:relative;
      .el-input{
        margin-top:8px; 
        .el-input__inner{
          border:none;
          outline:none;
        }
      }
      .btn{
        width:119px;
        height:32px;
        background-color:#0DB592;
        border-radius:3px;
        color:#fff;
        font-size:14px;
        line-height:32px;
        text-align:center;
        position:absolute;
        top:13px;
        right:10px;
      }
    }
    .message{
      min-width:967px;
      background-color:#fff;
      margin-top:10px;
      padding:16px 27px 18px 27px;
      .mes{
        font-size:14px!important;
        color:#333333;
        .time{
          margin-left:5px;
          color:#999999;
        }
      }
      .text{
        font-size:16px!important;
        color:#000000;
        word-break: break-all;
      }
      img{
        width:44px;
        height:43px;
        display:inline-block;
      }
    }
  }
  .emptyBox{
    width: 100%;
    height: 54px;
    background:#F5F5F5;
  }
  .refresh{
    position: fixed;
    bottom:100px;
    right:60px;
    cursor: pointer;
  }
}
</style>
