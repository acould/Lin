<template>
  <div class="addCustom">
    <div class='main-header'>
      <div class='breadcrumb-wrap'>
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <!-- <el-breadcrumb-item>南山</el-breadcrumb-item> -->
          <el-breadcrumb-item>添加自定义字段</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class='main-content'>
      <div class='takespace'></div>
      <div class='addContent'>
        <div class='line name'>
          <span>字段名称</span>
          <span class='redspot'>*</span>  
          <el-input v-model="name" placeholder="请输入名称 例：航班号/客户姓名"></el-input>
        </div>
        <div class='line class'>
          <span>字段类型</span>
          <span class='redspot'>*</span>  
          <el-select v-model="type_id" @change='changeSel' placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.type_id"
              :label="item.label"
              :value="item.type_id">
            </el-option>
          </el-select>
        </div>
        <div v-show='this.type_id==7' class='line listvalue'>
          <span>列表值</span>
          <el-input class='listV' @keyup.enter.native='keyDowns' v-model="content" placeholder='请输入列表值，多个字段用英文逗号隔开'></el-input>
        </div>
        <div v-show='this.type_id==8' class='line choseValue'>
          <span>选项1</span>
          <el-input class='listV' v-model="content1" placeholder='请输入列表值，多个字段用英文逗号隔开'></el-input>
        </div>
        <div v-show='this.type_id==8' class='line choseValue'>
          <span>选项2</span>
          <el-input class='listV' v-model="content2" placeholder='请输入列表值，多个字段用英文逗号隔开'></el-input>
        </div>
        <div v-show='this.type_id==8' class='line choseValue'>
          <span>选项3</span>
          <el-input class='listV' v-model="content3" placeholder='请输入列表值，多个字段用英文逗号隔开'></el-input>
        </div>
        <div class='line place'>
          <span>添加位置</span>
          <span class='redspot'>*</span>  
          <el-select v-model="location_id" placeholder="请选择添加位置">
            <el-option
              v-for="item in locationSel"
              :key="item.location_id"
              :label="item.label"
              :value="item.location_id">
            </el-option>
          </el-select>
        </div>
        <div class='line mustFill'>
          <span>是否为必填项</span>
          <el-radio-group v-model="is_required">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </div>
        <div class='line driverShow'>
          <span>司机端是否显示</span>
          <el-radio-group v-model="driver_see">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </div>
        <div class='line customerShow'>
          <span>客户是否显示</span>
          <el-radio-group v-model="customer_see">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </div>
        <div class='line carShow'>
          <span>车调是否可改</span>
          <el-radio-group v-model="saas_edit">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </div>
      </div>
    </div>
    <div class='footer-btn'>
      <span class='submit' @click='submit()'>保存</span>
      <span class='cancel'>取消</span> 
    </div>
  </div>
</template>
<script>
export default {
  data(){
    return{
      name:'',
      type_id:'',
      location_id:'',
      content:'',
      is_required:'',
      driver_see:'',
      customer_see:'',
      saas_edit:'',
      field_id:'',//修改时必传
      content1:'',
      content2:'',
      content3:'',
      options:[{
        type_id: 1,
        label: '文本'
      }, {
        type_id: 2,
        label: '数字'
      }, {
        type_id: 3,
        label: '日期'
      }, {
        type_id: 4,
        label: '时间'
      }, {
        type_id: 5,
        label: '日期和日期'
      }, {
        type_id: 6,
        label: '图片'
      }, {
        type_id: 7,
        label: '列表'
      }, {
        type_id: 8,
        label: '多选'
      }],
      locationSel:[{
        location_id: 1,
        label: '小程序下单'
      }, {
        location_id: 2,
        label: '发布任务'
      }, {
        location_id: 3,
        label: '添加客户'
      }, {
        location_id: 4,
        label: '添加司机'
      }, {
        location_id: 5,
        label: '添加车辆'
      },
      //  {
      //   location_id: 6,
      //   label: '待办事项'
      // }, {
      //   location_id: 7,
      //   label: '添加车调'
      // },
       {
        location_id: 8,
        label: '上报支出'
      }, {
        location_id: 9,
        label: '上报行程'
      }, {
        location_id: 10,
        label: '添加业务员'
      }]
    }
  },
  created(){
    let location_id = this.$route.query.location_id
    if (location_id) this.location_id = location_id
  },
  mounted () {
    if(this.$route.query.field_id){
      this.getCustomDetails(this.$route.query.field_id)
    }
  },
  methods: {
    getCustomDetails(field_id){
      let params={
        field_id:field_id
      }
      this.$axios.get(this.$httpUrl.customDetails,{params:params}).then(res=>{
        if(res){
          this.name =res.name
          this.type_id=res.type_id
          this.location_id=res.location_id
          this.is_required=res.is_required
          this.driver_see=res.driver_see	
          this.customer_see=res.customer_see
          this.saas_edit=res.saas_edit
          this.content=res.content
          if(res.type_id==8){
            let choseValue =this.content.split(',')
            this.content1=choseValue[0]
            this.content2=choseValue[1]
            this.content3=choseValue[2]
          }
        }
      })
    },
    changeSel(){
      this.content=''
    },
    keyDowns(e){
      var key  =window.event.keyCode
      if(key=='13'){
        this.content = this.content+','
      }     
    },
    submit(){
      if(this.name==''){
        this.$message('请填写字段名称')
        return
      }
      if(this.type_id==''){
        this.$message('请选择您要添加的字段类型')
      }else{
        if(this.type_id==7){
          if(this.content==''){
            this.$message('请填写列表值')
          }  
        }else if(this.type_id==8){
          this.content=[]
          let arr = []
          arr.push(this.content1)
          arr.push(this.content2)
          arr.push(this.content3)
          for(var i=0;i<arr.length;i++){
            if(arr[i]){
                this.content=this.content+arr[i]+','
            }
          }
          this.content = this.content.substr(0,this.content.length-1);
          if(this.content==''){
            this.$message('请填写列表值')
          }  
        }
      } 
      if(this.location_id===''){
        this.$message('请选择您要添加的位置')
        return
      }
      if(this.is_required===''){
        this.$message('请选择是否为必填选项')
        return
      }
      if(this.driver_see===''){
        this.$message('请选择司机端是否显示')
        return
      }
      if(this.customer_see===''){
        this.$message('请选择客户端是否显示')
        return
      }
      if(this.saas_edit===''){
        this.$message('请选择车调是否课改')
        return
      }
      this.content=this.content.replace(/，/ig,',');
      let params={
        name:this.name,
        location_id:this.location_id,
        type_id:this.type_id,
        content:this.content,
        is_required:this.is_required,
        driver_see:this.driver_see,
        customer_see:this.customer_see,
        saas_edit:this.saas_edit,
      }
      if(this.$route.query.field_id){
        params.field_id=this.$route.query.field_id
      }
      this.$axios.post(this.$httpUrl.addCustomFie,params).then(res => {
        if (res) {
          this.$message('添加成功')
          this.$router.go(-1)
        }
      })
    }
  },
  
}
</script>
<style  lang='less'>
.addCustom{
  .el-input__inner{
  height:32px!important;
  }
}

</style>
<style lang='less' scoped>
  .addCustom{
    .addCustomBody{

    }
    .main-content{
      .addContent{
        .line{
          margin-top:14px;
          .el-input{
            width:274px;
            height:32px;
          }
          
          .el-select{
            width:274px;
            height:32px;
          }
          
        }
        .name{
          margin-left:69px;
        }
        .class{
           margin-left:69px;
        }
        .listvalue{
          margin-left:83px;
          .listV{
            margin-left:7px;
          }
        }
        .choseValue{
          margin-left:83px;
          .listV{
            margin-left:12px;
          }
        }
        .place{
          margin-left:69px;
        }
        .mustFill{
          margin-left:42px;
        }
        .customerShow{
          margin-left:42px;
        }
        .driverShow{
          margin-left:30px;
        }
        .carShow{
          margin-left:42px;
        }
      } 
    }
    .redspot{
      color:red;
    }
    .el-radio-group{
      margin-left:10px;
    }
    .footer-btn{
      margin-top:29px;
      margin-left:142px;
      span{
        width:126px;
        height:32px;
        text-align:center;
        line-height:32px;
        border-radius:4px;
        font-size:14px;
        display:inline-block;
        cursor:pointer;
      }
      .submit{
        background:#0DB592;
        color:#FFFFFF;
      }
      .cancel{
        margin-left:20px;
        border:1px solid rgba(172,172,172,1);
        color:#333333;
        background:#fff;
      }
    }
  }
</style>
