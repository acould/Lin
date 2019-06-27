<template>
    <div id="dict" contentedittable="true">
        <div class="left-wrap" @keyup.enter="getDictList">
            <i-input v-model="dictName" icon="search" @click.stop="getDictList"></i-input>
            <ul class="dict-list">
                <li class="dict-item" :class="{'active': current == item.dictId}" v-for="item in dictList" :key="item.dictId" @click.stop="getDictItemList(item)">{{item.dictName}}</li>
            </ul>
        </div>
        <div class="right-wrap">
            <div class="right-wrap-header">
                <i-button icon="plus" type="primary" @click.stop="add" :disabled="!current">添加选项</i-button>
            </div>
            <div class="right-wrap-container">
                <i-table :data="dictItemList"  style="width:100%" :height="tableHeight">
                    <i-table-column type="index" label="序号" width="60" align="center"></i-table-column>
                    <i-table-column prop="dictValue" label="选项名称" width="150" align="center"></i-table-column>
                    <i-table-column prop="itemDesc" label="字段描述" align="center"></i-table-column>
                    <i-table-column prop="itemStatusCn" label="状态" width="150" align="center"></i-table-column>
                    <i-table-column label="操作" width="150" align="center" inline-template>
                        <div>
                            <i-button type="text" class="btn-table-operate" @click.stop="edit(row)">编辑</i-button>
                            <i-button type="text" class="btn-table-operate" style="color:#FA5555" v-if="row.itemStatus == 'ENABLED'" @click.stop="disabled(row)">禁用</i-button>
                            <i-button type="text" class="btn-table-operate" v-if="row.itemStatus == 'DISABLED'" @click.stop="enable(row)">启用</i-button>
                        </div>
                    </i-table-column>
                </i-table>
            </div>
        </div>
        <i-dialog v-model="dialogStatus" size="small">
            <i-form :model="form" ref="form" label-width="100px">
                <i-form-item label="选项名称:" prop="dictValue" :rules="[{required:true, message:'请输入选项名称', trigger:'blur'},{max:10,message:'最多不能超过10个汉字'}]">
                    <i-input v-model="form.dictValue" placeholder="请输入字段名称"></i-input>
                </i-form-item>
                <i-form-item label="排序:" prop="dictSort" :rules="[{required:true, message:'请选择排序', trigger:'change', type:'number'}]">
                    <i-input-number v-model.number="form.dictSort" style="width:100%"></i-input-number>
                </i-form-item>
                <i-form-item label="字典描述:" prop="dictDesc" :rules="[{required:true, message:'请输入字典描述', trigger:'blur'}]">
                    <i-input type="textarea" v-model="form.dictDesc"></i-input>
                </i-form-item>
            </i-form>
            <span slot="footer">
                <i-button @click.stop="dialogStatus = false">取消</i-button>
                <i-button type="primary" @click.stop="save">确定</i-button>
            </span>
        </i-dialog>
    </div>
</template>
<script>
import dictService from 'service/dict';
import {mapMutations} from 'vuex';
export default {
    data(){
        return {
            dictName:'',
            dictList:[],
            dictItemList:[],
            current:'',
            tableHeight:'',
            dialogStatus:false,
            form:{
                dictId:'',
                dictItemId:'',
                dictValue:'',
                dictSort:'',
                dictDesc:''
            },
            stateType:'add'
        }
    },
    watch:{
        dialogStatus(val){
            if (val) {
                this.$nextTick(() => {
                    if (this.stateType == 'add') {
                        this.$refs['form'].resetFields();
                    }
                });
            }
        }
    },
    methods:{
        ...mapMutations(['LOADING']),
        bindTableHeight(){
            this.tableHeight = document.documentElement.clientHeight - 48 - 16 - 48;
            window.addEventListener('resize', () => {
                this.tableHeight = document.documentElement.clientHeight - 48 - 16 - 48;
            });
        },
        getDictList(){
            this.LOADING(true);
            this.current = '';
            this.$set(this, 'dictItemList', []);
            dictService.getDictList(this.dictName).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    this.$set(this, 'dictList', res.data);
                }
            });
        },
        getDictItemList(dict){
            this.LOADING(true);
            this.current = dict.dictId;
            dictService.getDictItemList(dict.dictId).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    this.$set(this, 'dictItemList', res.data);
                }
            });
        },
        add(){
            this.stateType = 'add';
            this.dialogStatus = true;
            this.form.dictId = this.current;
        },
        edit(row){
            row = JSON.parse(JSON.stringify(row));
            this.stateType = 'edit';
           
            this.dialogStatus = true;   
            this.$nextTick(() => {
                this.$refs['form'].resetFields();
                this.form.dictItemId = row.dictItemId;
                this.form.dictValue = row.dictValue;
                this.form.dictSort = row.dictSort;
                this.form.dictDesc = row.dictDesc;
            });       
        },
        save(){
            this.$refs['form'].validate(valid => {
                if (valid) {
                    let params = JSON.parse(JSON.stringify(this.form));
                    if (this.stateType == 'add') {
                        dictService.addDictItem(params).then(res => {
                            if (res.code === 0) {
                                this.$message.success('操作成功');
                                this.getDictItemList({dictId:this.current});
                                this.dialogStatus = false;
                            }
                        });
                    } else {
                        dictService.updateDictItem(params).then(res => {
                            if (res.code === 0) {
                                this.$message.success('操作成功');
                                this.getDictItemList({dictId:this.current});
                                this.dialogStatus = false;
                            }
                        });
                    }
                }
            });
        },
        disabled(row){
            this.$confirm('确定要禁用这个选项吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'error'
            }).then(() => {
                dictService.disableDictItem(row.dictItemId).then(res => {
                    if (res.code === 0) {
                        this.$message.success('操作成功');
                        this.getDictItemList({dictId:this.current});
                    }
                });
            }).catch(() => {});
        },
        enable(row){
            this.$confirm('确定要启用这个选项吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'error'
            }).then(() => {
                dictService.enableDictItem(row.dictItemId).then(res => {
                    if (res.code === 0) {
                        this.$message.success('操作成功');
                        this.getDictItemList({dictId:this.current});
                    }
                });
            }).catch(() => {});
        }
    },
    mounted(){
        this.LOADING(true);
        this.bindTableHeight();
        this.getDictList();
    }
}
</script>
<style lang="scss">
#dict{
    padding:8px;

    .left-wrap{
        width: 260px;
        height: 100%;
        border: 1px solid #e9e9e9;
        padding:16px;

        .dict-list{
            margin-top: 48px;
        }

        .dict-item{
            height: 40px;
            width: 100%;
            line-height: 40px;
            text-align: center;
            color:#1f2d3d;
            border:1px solid #eee;
            border-radius: 4px;
            margin-bottom: 16px;
            cursor: pointer;

            &.active{
                border-color:#a3d0fd;
                background: #e6f1fc;
                color:#329dff;
            }
        }
    }

    .right-wrap{
        position: absolute;
        right:8px;
        top:8px;
        width: calc(100% - 284px);
        height: calc(100% - 16px);

        .right-wrap-header{
            height: 48px;
            padding:8px 10px;
            border:1px solid #e9e9e9;
            border-bottom: none;

            .el-button{
                padding:8px 15px;
            }
        }
    }

    .el-input-number{
        .el-input{
            width: 100%;
        }
    }
}
</style>


