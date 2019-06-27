<template>
    <div id="template-list">
        <div class="container-wrap">
            <div class="container-header">
                <i-button icon="plus" type="primary" @click.stop="add">新增</i-button>
            </div>
            <div class="container-body">
                <i-table :data="tableData" style="width:100%" :height="tableHeight">
                    <i-table-column prop="templateNum" label="编号" width="150" align="center"></i-table-column>
                    <i-table-column prop="templateName" label="合同模板名称" width="150" align="center"></i-table-column>
                    <i-table-column prop="templateDescribe" label="使用场景描述"  align="center"></i-table-column>
                    <i-table-column prop="createTime" label="创建日期" width="150" align="center"></i-table-column>
                    <i-table-column prop="templateStatusCn" label="状态" width="150" align="center"></i-table-column>
                    <i-table-column label="操作" width="250" align="center" inline-template>
                        <div>
                            <i-button type="text" class="btn-table-operate" @click.stop="preview(row)">预览</i-button>
                            <i-button type="text" class="btn-table-operate" style="color:#FA5555" @click.stop="stop(row)" v-if="row.templateStatus != 'DEFAULT' && row.templateStatus != 'INVALID'">停用</i-button>
                            <i-button type="text" class="btn-table-operate" @click.stop="enable(row)" v-if="row.templateStatus != 'DEFAULT' && row.templateStatus != 'EFFECTIVE'">启用</i-button>
                            <i-button type="text" class="btn-table-operate" @click.stop="download(row)">下载</i-button>
                            <i-button type="text" class="btn-table-operate" v-if="row.templateStatus != 'DEFAULT' && row.templateStatus != 'INVALID'" @click.stop="setDefault(row)">设为默认模板</i-button>
                        </div>
                    </i-table-column>
                </i-table>
                <div class="pagination-wrap">
                    <i-pagination ref="pagination" 
                          :page-size="searchForm.size" 
                          :current-page="searchForm.current" 
                          :total="total" 
                          @current-change="handleCurrentChange"
                          layout="prev,pager,next,total"></i-pagination>
                </div>
                
            </div>
        </div>
        <i-dialog v-model="previewStatus" size="full">
            <div v-html="template"></div>
        </i-dialog>
        <i-dialog v-model="addStatus" size="small" title="新增模板">
            <i-form :model="form" ref="form" label-width="120px">
                <i-form-item label="合同模板名称:" prop="templateName" :rules="[{required:true, message:'请输入合同模板名称',trigger:'blur'},{max:20, message:'最多不能超过20个汉字长度'}]">
                    <i-input v-model="form.templateName" placeholder="请输入合同模板名称:"></i-input>
                </i-form-item>
                <i-form-item label="使用场景描述:" prop="templateDescribe" :rules="[{required:true, message:'请输入适用场景描述',trigger:'blur'},{max:200, message:'最多不能超过200个汉字长度'}]">
                    <i-input v-model="form.templateDescribe" type="textarea" placeholder="请输入适用场景描述"></i-input>
                </i-form-item>
                <i-form-item label="资产出租人:" prop="assetsOwner">
                    <i-input v-model="form.assetsOwner" :disabled="true"></i-input>
                </i-form-item>
                <i-form-item label="联系地址:" prop="address" :rules="[{required:true, message:'请输入联系地址',trigger:'blur'},{max:50, message:'最多不能超过50个汉字长度'}]">
                    <i-input v-model="form.address" placeholder="请输入联系地址"></i-input>
                </i-form-item>
                <i-form-item label="企业电子合同章:">
                    <img :src="baseUrl + form.src + crop" alt="" style="width:120px;height:120px">
                </i-form-item>
            </i-form>
            <span slot="footer">
                <i-button @click.stop="addStatus = false">取消</i-button>
                <i-button type="primary" @click.stop="nextStep">下一步</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="addTempStatus" size="normal"  :close-on-click-modal="false" title="新增合同模板">
            <div>
                <quill-editor 
                    v-model="templateContentNameHeader" :options="editorOptionReadOnly" :disabled='editorDisabled'>
                </quill-editor>
            </div>
             <i-form :model="form" ref="contract_form">
                <i-form-item prop="contractName" :rules="[{required:true, message:'请输入合同名称',trigger:'blur'},{max:20, message:'最多不能超过20个汉字长度'}]">
                    <i-input v-model="form.contractName" placeholder="请输入合同名称" class="contract-input"></i-input>
                </i-form-item>
             </i-form>
            <div>
                <quill-editor 
                    v-model="templateContentHeader" :options="editorOptionReadOnly" :disabled='editorDisabled'>
                </quill-editor>
            </div>
            <div style="height:350px">
                <quill-editor 
                    v-model="form.editableContent" :options="editorOption" style="height:300px">
                </quill-editor>
            </div>
            <div>
                <quill-editor 
                    v-model="templateContentFooter" :options="editorOptionReadOnly" :disabled='editorDisabled'>
                </quill-editor>
            </div>
            <span slot="footer">
                <i-button @click.stop="previewTemp">预览模板</i-button>
                <i-button type="primary" @click.stop="save">创建模板</i-button>
            </span>
        </i-dialog>
    </div>
</template>
<script>
import {mapState, mapMutations} from 'vuex';
import templateService from 'service/template';
import config from 'config';
import EventBus from 'utils/eventBus';
import { quillEditor } from "vue-quill-editor"; //调用编辑器
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';

export default {
    components: {
        quillEditor
    },

    data(){
        return {
            tableData:null,
            tableHeight:'',
            searchForm:{
                size:100,
                current:1
            },
            total:0,
            previewStatus:false,
            addStatus:false,
            addTempStatus:false,
            template:'',
            templateContentNameHeader:'',
            templateContentName:'',
            templateContentHeader:'',
            templateContentFooter:'',
            editorDisabled:true,
            _templateContent:'',
            form:{
                templateName:'',
                contractName:'',
                templateDescribe:'',
                templateContent:'',
                editableContent:'',
                assetsOwner: '',
                address:'',
                src:''
            },
            baseUrl:config.baseImageUrl,
            crop:'?x-oss-process=style/w800',
            editorOption:{
                placeholder:'此处可以输入自定义条款',
                modules: {
                    toolbar: [
                    ['bold', 'italic', 'underline', 'strike'],
                    [{ 'header': 1 }, { 'header': 2 }],
                    //[{ 'list': 'ordered' }, { 'list': 'bullet' }],
                    //[{ 'script': 'sub' }, { 'script': 'super' }],
                    //[{ 'indent': '-1' }, { 'indent': '+1' }],
                    //[{ 'direction': 'rtl' }],
                    //[{ 'size': ['small', false, 'large', 'huge'] }],
                    [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
                    //[{ 'font': [] }],
                    [{ 'color': [] }, { 'background': [] }],
                    //[{ 'align': [] }],
                    ['clean']
                    ]
                }
            },        
            editorOptionContract:{
                placeholder:'请输入合同名称',
                theme: 'bubble',
                modules: {
                    toolbar: []
                }
            },
            editorOptionReadOnly:{
                placeholder:'',
                theme: 'bubble',
                modules: {
                    toolbar: []
                }
            }
        }
    },
    watch:{
        addStatus(val){
            if (val) {
                this.$nextTick(() => {
                    this.$refs['form'].resetFields();
                });
            }
        }
    },
    methods:{
        ...mapMutations(['LOADING']),
        bindTableHeight(){
            this.tableHeight = document.documentElement.clientHeight - 48 - 16 - 48 - 40;
            window.addEventListener('resize', () => {
                this.tableHeight = document.documentElement.clientHeight - 48 - 16 - 48 - 40;
            });
        },
        getGrid(){
            this.LOADING(true)
            console.log(this.searchForm)
            templateService.getTemplateList(this.searchForm).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    this.total = res.total;
                    this.$set(this, 'tableData', res.data)
                }
            });
        },
        handleCurrentChange(val){
            this.searchForm.current = val;
            this.getGrid();
        },
        stop(row){
            this.$confirm('确定要停用'+ row.templateNum +'这个合同模板吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'error'
            }).then(() => {
                this.LOADING(true);
                templateService.stopTemplate(row.id).then(res => {
                    this.LOADING(false);
                    if (res.code === 0) {
                        this.$message.success('操作成功');
                        this.getGrid();
                    }
                });
            }).catch(() => {});
        },
        enable(row){
            this.$confirm('确定要启用'+ row.templateNum +'这个合同模板吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'error'
            }).then(() => {
                this.LOADING(true);
                templateService.enableTemplate(row.id).then(res => {
                    this.LOADING(false);
                    if (res.code === 0) {
                        this.$message.success('操作成功');
                        this.getGrid();
                    }
                });
            }).catch(() => {});
        },
        setDefault(row){
            this.$confirm('确定启用' + row.templateNum +'这个作为默认使用的模板吗?一个账号只能有一个默认使用模板.', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'error'
            }).then(() => {
                this.LOADING(true);
                templateService.setDefaultTemplate(row.id).then(res => {
                    this.LOADING(false);
                    if (res.code === 0) {
                        this.$message.success('操作成功');
                        this.getGrid();
                    }
                });
            }).catch(() => {

            });
        },
        preview(row){
            this.template = row.templateContent.replace("${contractName}",row.contractName).replace("${editableContent}",row.editableContent);
            this.previewStatus = true;
        },
        add(){
            if (this.account.electronicSeal.length < 1) {
                this.$confirm('您的账号还未上传企业电子合同章', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type:'error'
                }).then(() => {
                    EventBus.$emit('show-account');
                }).catch(() => {

                });
            } else {
                this.form.assetsOwner = this.account.companyName;
                this.form.src = this.account.electronicSeal[0].src;
                this.addStatus = true;
            }
        },
        nextStep(){
            this.$refs['form'].validate(valid => {
                if (valid) {
                    templateService.getDefaultTemplate().then(res => {
                        if (res.code === 0) {
                            this.form.templateContent = res.data.templateContent;
                            this.form.editableContent = res.data.editableContent;
                            this._templateContent  = this.form.templateContent;
                            if(this.form.templateContent.split("${contractName}").length>1){
                                this.templateContentNameHeader = this.form.templateContent.split("${contractName}")[0];
                                this._templateContent = this.form.templateContent.split("${contractName}")[1];
                            }
                            if(this._templateContent.split("${editableContent}").length>1){
                                this.templateContentHeader = this._templateContent.split("${editableContent}")[0];
                                this.templateContentFooter = this._templateContent.split("${editableContent}")[1];
                            }else{
                                this.templateContentHeader = this._templateContent
                            }
                            this.templateContentHeader.replace("${contractName}",this.form.contractName);
                            this.addTempStatus=true;
                        }
                    });
                }
            });
        },
        previewTemp(){
            this.template = this.form.templateContent.replace("${contractName}",this.form.contractName==''?'合同名称未录入':this.form.contractName).replace("${editableContent}",this.form.editableContent);
            this.previewStatus = true;
        },
        save(){
            this.$refs['contract_form'].validate();
            this.LOADING(true);
            let params = JSON.parse(JSON.stringify(this.form));
            templateService.addTemplate(params).then(res => {
                if (res.code === 0) {
                    this.addStatus = false;
                    this.addTempStatus = false;
                    this.$message.success('创建成功');
                    this.getGrid();

                }
            });
            this.LOADING(false);
        },
        download(row){
            templateService.getTemplateUrl(row.id).then(res => {
                if (res.code === 0) {
                    let a = document.createElement('a');
                    a.href = res.data;
                    a.target = '_blank';
                    a.click();
                    a.remove();
                }
            });
        }
    },
    computed:{
        ...mapState(['dict','account'])
    },
    mounted(){
        this.getGrid();
        this.bindTableHeight();
    }
}
</script>
<style lang="scss">
#template-list{
    .container-wrap{
        height: 100%;

        .container-header{
            height: 48px;
            padding:8px 10px;
            border:1px solid #e9e9e9;

            border-bottom: none;

            .el-button{
                padding:8px 15px;
            }
        }

        .pagination-wrap{
            margin-top: 10px;
        }
    }
    .contract-input{
        .el-input-inner {
            text-align: center;
            font-size: 20px;
            color:black;
            font-weight: 700!important
        }
    }
}
</style>


