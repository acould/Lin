<template>
    <div id="notice">
        <i-tabs v-model="active" type="card">
            <i-tab-pane label="未读消息" name="first">
                <i-table :data="tableData" stripe style="width:100%" :height="tableHeight">
                    <i-table-column type="selection" label="全选" width="60" align="center"></i-table-column>
                    <i-table-column prop="dictValue" label="分类" width="150" align="center"></i-table-column>
                    <i-table-column prop="itemDesc" label="主题" align="center"></i-table-column>
                    <i-table-column prop="itemStatusCn" label="时间" width="150" align="center"></i-table-column>
                </i-table>
            </i-tab-pane>
            <i-tab-pane label="全部消息" name="second">
                <i-table :data="tableData" stripe style="width:100%" :height="tableHeight">
                    <i-table-column type="selection" label="全选" width="60" align="center"></i-table-column>
                    <i-table-column prop="dictValue" label="分类" width="150" align="center"></i-table-column>
                    <i-table-column prop="itemDesc" label="主题" align="center"></i-table-column>
                    <i-table-column prop="itemStatusCn" label="时间" width="150" align="center"></i-table-column>
                </i-table>
            </i-tab-pane>
        </i-tabs>
    </div>
</template>
<script>
export default {
    data(){
        return {
            active:'first',
            tableHeight:'',
            tableData:null
        }
    },
    methods:{
        bindTableHeight(){
            this.tableHeight = document.documentElement.clientHeight - 48 - 32 - 40;
            window.addEventListener('resize', () => {
                this.tableHeight = document.documentElement.clientHeight - 48 - 32 - 40;
            });
        },
    },
    mounted(){
        this.bindTableHeight();
    }
}
</script>
<style lang="scss">
#notice{
    padding:16px;


    .el-tabs-card > .el-tabs-header .el-tabs-item.is-active{
        background: #f5f5f5;
        border:1px solid #DCDFE6;
        border-bottom-color: #f5f5f5;
    }

    .el-tabs-header{
        border-bottom-color: #DCDFE6;
        margin-bottom: 0;
    }
    
    .el-table{
        border-top:none;
    }
}
</style>
