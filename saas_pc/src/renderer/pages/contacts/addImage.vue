<template>
  <div class='addImage'>
    <div class="main-header">
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item v-if='this.customerType!=""' :to="{ name: 'customerOrigin',query: {customerType:customerType} }">{{companyTypeTxt}}</el-breadcrumb-item>
          <el-breadcrumb-item  v-if='this.customerType!=""'>{{customerInfo.company}}</el-breadcrumb-item>
          <el-breadcrumb-item>管理图片</el-breadcrumb-item>
        </el-breadcrumb>
        
      </div>
    </div>
    <div class='imageList'>
      <div class='add'  v-show='this.page == 1'   @click='uploadImg()'>
        <el-upload   class="avatar-uploader" :http-request="uploadImage" :action="''" :multiple="true" :show-file-list="false">
          <div class="upload-btn">
            <span class="iconfont icon-close iconfont-close"></span>
            <span class="upload-sign">添加合同照片</span>
          </div>
        </el-upload>
        <el-input style="border:none; " class='img' v-model="value" placeholder="请输入图片标题"></el-input>
      </div>
      <div v-show='newPhotoList.length>0' v-for='(item,z) in newPhotoList'  class='images'>
        <span @click='reduce(z)' class="iconfont icon-close iconfont-close"></span>
        <img @click='lookPhotos(item.url)' :src='item.url' alt=''/>
        <p class='text'>{{item.title}}</p>
        <p class='time'>{{item.created_at}}</p>
      </div>
      <div v-for='(item,index) in this.photoList' :key='index' class='images'>
        <span @click='reduceImage(item.file_id,index)' class="iconfont icon-close iconfont-close"></span>
        <img @click='lookPhotos(item.url)' :src='item.url' alt=''/>
        <p class='text'>{{item.title}}</p>
        <p class='time'>{{item.created_at}}</p>
      </div>  
    </div>
    <div class='foot'>
      <div class='submit' @click='submit()'>提交</div>
      <div class='cancel' @click='cancel()'>取消</div>
    </div>
    <div class="pages-wrap">
        <el-pagination  :current-page="page" @current-change="changePage" :page-count="maxPage" background layout="prev, pager, next"></el-pagination>
      </div>
     <imgDialog ref="imgDialog" :imgShow.sync='imgShow' :imgs='imgs'></imgDialog> 
  </div>
</template>
<script>
import imgDialog from '@/components/imgDialog'
  export default{
      data(){
        return{
          photoList:[],
          page:1,
          maxPage:null,
          value:'',
          newPhotoList:[],
          file_ids:[],
          customerType:'',
          customer_id:'',
          companyTypeTxt:'',
          customerInfo:{},
          imgShow:false,
          imgs:'',
          ims:[]
        }
      },
      created(){
          this.getPhotoList()
          if(this.$route.query.customer_id){
              this.customer_id=this.$route.query.customer_id
          }
          if(this.$route.query.customerType){
            this.customerType=this.$route.query.customerType
            if (this.customerType == 1) {
              this.companyTypeTxt = '客户'
              // this.travelParams.customer_id = query.customer_id
            } else {
              this.companyTypeTxt = '外援车队'
              // this.carParams.customer_id = query.customer_id
            }
             this.getCustomerDetail()
          }else{
            this.customerType=''
          }
         
      },
      mounted () {
        
      },
      methods:{
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
          if(this.$route.query.customer_id){
            let customer_id = this.$route.query.customer_id
            var params={
              customer_id: customer_id,
              page:this.page,
              paginate:20
            }
          }else{
            var params={
              page:this.page,
              paginate:20
            }
          }
          
          this.$axios.get(this.$httpUrl.photoList, { params: params }).then(res => {
            if (res != false) {
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
          this.page=page
          this.getPhotoList()
        },
        // 上传图片，换取图片地址
        uploadImg(){
          if(this.value==''){
            this.$message('请先填写标题')
          }
        },
        uploadImage (r) { 
          // this.isUploading = true
          console.log(r)
          let formData = new FormData()
          let client;
          if (process.env.IS_WEB) {
            client = 'saas_pc'
          } else {
            client = 'saas_windows'
          }
          let nowDate =  this.$util.formatTime().date
          let obj={
            url:'',
            title:this.value,
            created_at:nowDate
          }

          formData.append('files', r.file)
          formData.append('client', client)
          this.$axios.defaults.headers['Content-Type'] = 'multipart/form-data'
            this.$axios.post(this.$httpUrl.uploadImage, formData).then(res => {
              if (res) {
                obj.url=res.files
                obj.customer_id= this.$route.query.customer_id
                this.ims.push(obj)
                var reserve = []
                for( var j=this.ims.length-1;j>=0;j--){
                    reserve.push(this.ims[j])
                }
                this.newPhotoList= reserve
                this.value=''
              }
            })  
        },
        submit(){
          if(this.newPhotoList!=''&&this.file_ids==''){
              let params = {
                contents:this.newPhotoList
              }
              this.$axios.post(this.$httpUrl.uploadPhoto, params).then(res => {
                if (res != false) {
                  this.$message({
                    message: '提交成功',
                    type: 'success'
                  })
                  this.newPhotoList=[]
                  this.ims=[]
                  this.getPhotoList()
                }
              })
          }else if(this.newPhotoList==''&&this.file_ids!=''){
              var param={
                file_ids:this.file_ids
               }
              this.$axios.get(this.$httpUrl.deleteIma, { params: param }).then(res => {
                if (res != false) {
                  this.$message({
                    message: '删除成功',
                    type: 'success'
                  })
                  this.file_ids=[]
                  this.getPhotoList()
                }
              }) 
          }else{
            // alert(1)
            // console.log()
            let params = {
                contents:this.newPhotoList
              }
              if(this.newPhotoList.length){
                this.$axios.post(this.$httpUrl.uploadPhoto, params).then(res => {
                  if (res != false) {
                    this.$message({
                      message: '提交成功',
                      type: 'success'
                    })
                  }
                })
              }
                
              var param={
                file_ids:this.file_ids
               }
               if(this.file_ids.length){
                this.$axios.get(this.$httpUrl.deleteIma, { params: param }).then(res => {
                  if (res != false) {
                    this.$message({
                      message: '删除成功',
                      type: 'success'
                    }) 
                    this.getPhotoList()
                  }
                }) 
               }
                
          }
            
          
        },
        cancel(){
          this.ims=[]
           this.newPhotoList=[]
          this.getPhotoList()
        },
        reduce(e){
          this.newPhotoList.splice(e,1)
          this.ims.splice(e,1)
        },
        reduceImage(id,g){
          // console.log(id)
          this.file_ids.push(id)
          // console.log(this.file_ids)
          this.photoList.splice(g,1)

        },
        lookPhotos(img){
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
<style lang="less">
  .addImage{
    .el-input__inner{
      border:none;
     border-radius:0px;
      border-bottom:1px solid  rgba(0,0,0,0.3);
      margin-top: 2px;
    }
    .imageList{
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
         position:relative;
        img{
          width:159px;
          height:133px;
        }
        .iconfont{
          position:absolute;
          right:0;
          top:0;
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
      .add{
        width:159px;
        height:178px;
        margin-top:10px;
        margin-right: 10px;
        float:left;
        // border:1px solid #EBEBEB;
        .avatar-uploader {
          float: left;
          margin-right: 20px;
        }
        .upload-btn {
          width: 159px;
          height: 133px;
          line-height: 133px;
          text-align: center;
          font-size:14px;
          color: #EBEBEB;
          background-color: #fff;
          border: 1px solid #dadada;
          // margin-bottom: 15px;
          .iconfont-close {
            transform: rotate(45deg);
            display: inline-block;
          }
        }
        .img{
          border:none;
        }
      }
    }
    .imageList:after{
      display:block;
      content:'';
      clear:both;
       visibility: hidden;
    }
    .foot{
      width:337px;
      display:flex;
      margin:33px auto 54px;

      .submit{
        width:154px;
        height:36px;
        background:#0DB592;
        border-radius:4px;
        font-size:14px;
        line-height:36px;
        text-align:center;
        color:#ffffff;
        margin-right:29px;
        cursor:pointer;
      }
      .cancel{
        width:154px;
        height:36px;
        background:#FFFFFF;
        border-radius:4px;
        border:1px solid #D9D9D9;
        border-radius:4px;
        font-size:14px;
        line-height:36px;
        text-align:center;
        color:#000000;
        cursor:pointer;
      }
    }
      
  }
</style>  