<template>
    <div class="customSet">
      <div class="main-header">
        <h3 class="single-tt">添加自定义字段</h3>
      </div>
      <div class="customSet-body">
        <div class="addCustom" @click="jumpAdd()">
          添加自定义字段
        </div>
        <div class="table">
          <el-table ref="multipleTable" :data='tableData' style="width:100%;" :header-cell-style="bgColor" tooltip-effect="dark">
            <el-table-column  prop="name" label="字段名" min-width="100">
            </el-table-column> 
            <el-table-column  prop="type_name" label="字段类型" min-width="150">
            </el-table-column>
            <el-table-column  prop="location_name" label="所在位置" min-width="150">
            </el-table-column>
            <el-table-column  label="是否必填" min-width="100">
              <template slot-scope="scope">
                <span v-show='scope.row.is_required==0'>否</span> 
                <span v-show='scope.row.is_required==1'>是</span> 
              </template>
            </el-table-column>
            <el-table-column  label="司机端是否显示" min-width="100">
              <template slot-scope="scope">
                <span v-show='scope.row.driver_see==0'>否</span> 
                <span v-show='scope.row.driver_see==1'>是</span> 
              </template>
            </el-table-column>
            <el-table-column label="客户是否展示" min-width="100">
              <template slot-scope="scope">
                <span v-show='scope.row.customer_see==0'>否</span> 
                <span v-show='scope.row.customer_see==1'>是</span> 
              </template>
            </el-table-column>
            <el-table-column  prop="change" label="车调是否可改" min-width="100">
              <template slot-scope="scope">
                <span v-show='scope.row.saas_edit==0'>否</span> 
                <span v-show='scope.row.saas_edit==1'>是</span> 
              </template>
            </el-table-column>
            <el-table-column  label="操作" min-width="100">
              <template slot-scope="scope">
                <div class='btn-container'>
                  <div class="btn-select-wrap">
                    <span class="btn-txt">操作</span>
                    <el-select
                      v-model="btnOption"
                      placeholder="操作"
                      class="hid-select"
                      @change="btnSelectBlur(scope.row,scope.$index)"
                    >
                      <el-option value="1" label="编辑"></el-option>
                      <el-option value="2" label="删除"></el-option>
                    </el-select>
                  </div>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="pages-wrap" v-if="tableData.length">
        <el-pagination @current-change="changeComPage" :page-count="maxPages" background layout="prev, pager, next"></el-pagination>
      </div>
    </div>
</template>
<script>
export default {
  data(){
    return{
      tableData:[],
      bgColor:{
       'background-color':'#F2F2F2',
       'font-size':'16px',
      },
      pages:1,
      maxPages:null,
      btnOption:'',
      obj:{}
    }
  },
  created(){

  },
  watch:{
    btnOption(newVal){ 
      if(!newVal){  
        return
      }else if(newVal==1){
        this.$router.push({name:'addCustom',query:{field_id:this.obj.field_id}})
      }else if(newVal==2){
        this.deleteCustom(this.obj)
      }
       this.btnOption = null
    }
   
  },
  mounted(){
    this.getCustomFieList()
  },
  methods:{
    jumpAdd(){
      this.$router.push({name:'addCustom'})
    },
    getCustomFieList(){
      let params = {
        keywords:'',
        location_id:'',
        page:this.pages
      }
      this.$axios.get(this.$httpUrl.customFiledList,{params:params}).then(res=>{
        if(res){
          this.tableData=res.list
          this.maxPages=res.pages
        }
      })
    },
    btnSelectBlur(field,index){
      this.obj= Object.assign({}, field, { index })
    },
    // 删除自定义
    deleteCustom(custonObj){
      if(custonObj.saas_edit==0){
        this.$message('您暂无权限，请联系管理员')
      }else{
        let params={
          field_id:custonObj.field_id
        }
        this.$axios.get(this.$httpUrl.deleteCustom,{params:params}).then(res=>{
          if(res){
            this.$message('删除成功!')
            this.getCustomFieList()
          }
        })
      }
      
    },
    
    changeComPage(page){
      this.pages=page
      this.getCustomFieList()
    }
  }
}
</script>
<style lang='less' scoped>
.customSet{
  .addCustom{
    width:146px;
    height:35px;
    background:rgba(46,182,149,1);
    border-radius:4px;
    text-align: center;
    line-height: 35px;
    font-size: 16px;
    color: #FFFFFF;
    margin-top: 11px;
    margin-left: 34px;
    cursor: pointer;
  }
  .table{
    margin-top: 13px;
  }
  
}
</style>
