<template>
  <div>
    <el-dialog :visible.sync="imgboxShow" class="imgBox">
      <div  class="img">
        <img :src="imgds" alt="">
      </div>
      
      <a @click='downloadPic()'  class="download">
          下载图片
          <span class="iconfont icon-xiazai iconfont-xiazai"></span>
       </a>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'imgDialog',
  props: {
    imgShow:{
      type:Boolean
    },
    imgs:{
      type:String
    }
  },
  watch: {
  
  },
  data () {
    return {
     imgboxShow:false,
     imgds:''
    }
  },
  created(){

  },
  methods: {
    // 弹窗 接受值
    showToggle(id){
      this.imgboxShow =true
      this.imgds = id
    },
    // 下载图片
    downloadPic(){
      var imgSrc =this.imgds
      // 判断浏览器型号
       if (!!window.ActiveXObject || "ActiveXObject" in window){
	        this.createIframe(imgSrc)
	    }
	    else{
        var dowLoad =document.getElementsByClassName('download')[0]
        dowLoad.setAttribute('download',imgSrc)
        dowLoad.setAttribute('href',imgSrc)
	    }

    },
    // 动态创建iframe标签
    createIframe(imgSrc){
      if(document.getElementById('IframeReportImg')==0){
        var iframe  = document.createElement('iframe')
        iframe.setAttribute('id','IframeReportImg')
        iframe.setAttribute('name','IframeReportImg')
        iframe.setAttribute('onload','downloadImg()')
        iframe.setAttribute('src','about:blank')
        if(document.getElementById('IframeReportImg').getAttribute('IframeReportImg')!=imgSrc){
            iframe.setAttribute('src',imgSrc)
        }else{
          this.downloadImg();
        }
      }
     }
  
  }
}
</script>
<style lang="less" scope>
    .imgBox{
      .img{
        width: 100%;
        height: 500px;
        display: block;
       display: flex;
       align-items: center;
        img{
          width:100%;
        }
      }
      .el-dialog{
        width: 280px!important;
        height: 500px;
        .el-dialog__header{
          .el-dialog__headerbtn{
            font-size:20px;
            top:12px;
            right:12px;
            .el-icon-close:before{
              color:#333333;
            }
          }
        }
        .el-dialog__body{
            padding: 0;
        }
      }
      .download{
        width: 100%;
        height: 28px;
        background:RGBA(0, 0, 0, 0.6);
        line-height:28px;
        text-align: center;
        font-size:12px;
        color: #FFFFFF; 
        font-weight:400!important;
        text-decoration:none; 
        color:#FFFFFF;
        display:block; 
        position:absolute;
        bottom:0px;
        .iconfont{
          vertical-align: middle;
          display: inline-block;
        }
        
      }
    }
</style>

