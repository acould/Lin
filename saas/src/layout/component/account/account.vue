<template>
    <div class="account">
        <i-card>
            <div slot="header" class="clearfix">
                <span>商业信息</span>
            </div>
            <div>
                <div class="info-row">
                    <label class="label">企业名称:</label>
                    <span class="text">{{form.companyName}}</span>
                </div>
                <div class="info-row">
                    <label class="label">统一社会信用代码:</label>
                    <span class="text">{{form.creditCode}}</span>
                </div>
                <div class="info-row">
                    <label class="label">经营城市:</label>
                    <span class="text">{{form.cityNames.join(',')}}</span>
                </div>
                <div class="info-row">
                    <label class="label">营业执照:</label>
                    <span class="text">
                        <i-img-list v-model="form.businessLicense" :props="imgProp" :perfix="imgPerfix" :crop="crop" :show-del="false"></i-img-list>
                    </span>
                </div>
                <div class="info-row">
                    <label class="label">企业电子合同章:</label>
                    <span class="text">
                        <i-img-list v-model="form.electronicSeal" :props="imgProp" :perfix="imgPerfix" :crop="crop" @delete="handleElectronDelete"></i-img-list>
                        <i-uploader class="upload-pc"
                            v-if="form.electronicSeal.length < 1"
                            action="/gaodu-files/upload/image" 
                            :show-file-list="false"
                            :on-success="handleSuccess"
                            :data="{minWidth:220,minHeight:220,ratio:'1:1'}"
                            :multiple="false"
                            :before-upload="handleValidate">
                            <i class="el-icon-plus"></i>
                        </i-uploader>
                    </span>
                </div>
            </div>
        </i-card>
        <i-card>
            <div slot="header" class="clearfix">
                <span>法人资料</span>
            </div>
            <div>
                <div class="info-row">
                    <label class="label">法人姓名:</label>
                    <span class="text">{{form.legalPerson}}</span>
                </div>
                <div class="info-row">
                    <label class="label">证件类型:</label>
                    <span class="text">{{form.certTypeCn}}</span>
                </div>
                <div class="info-row">
                    <label class="label">证件号码:</label>
                    <span class="text">{{form.certNo}}</span>
                </div>
                <div class="info-row">
                    <label class="label">证件照片:</label>
                    <span class="text">
                        <i-img-list v-model="form.certPhoto" :props="imgProp" :perfix="imgPerfix" :crop="crop" :show-del="false"></i-img-list>                        
                    </span>
                </div>
            </div>
        </i-card>
        <i-card>
            <div slot="header" class="clearfix">
                <span>账号信息</span>
                <div style="float:right">
                    <i-button size="small" type="text" @click.stop="updateAccountStatus = true">修改登录账号</i-button>
                    <i-button size="small" type="text" @click.stop="updatePasswordStatus = true">修改登录密码</i-button>
                </div>
            </div>
            <div>
                <div class="info-row">
                    <label class="label">登录账号:</label>
                    <span class="text">{{form.userPhone}}</span>
                </div>
                <div class="info-row">
                    <label class="label">账户名称:</label>
                    <span class="text">{{form.userName}}</span>
                </div>
                <div class="info-row">
                    <label class="label">登录密码:</label>
                    <span class="text">{{form.password}}</span>
                </div>
                <div class="info-row">
                    <label class="label">角色:</label>
                    <span class="text">{{form.userTypeCn}}</span>
                </div>
            </div>
        </i-card>
        <i-dialog v-model="updateAccountStatus" size="small" title="修改登录账号" append-to-body>
            <i-form :model="accountForm" ref="accountForm" label-width="120px">
                <i-form-item label="密码:" prop="userPassword" :rules="[{required:true, message:'请输入密码', trigger:'blur'}]">
                    <i-input v-model="accountForm.userPassword" type="password" placeholder="请输入登录密码"></i-input>
                </i-form-item>
                <i-form-item label="新手机号码:" prop="userPhone" :rules="[{required:true, message:'请输入新手机号码', trigger:'blur'},{pattern:/^1\d{10}$/,message:'请输入正确格式'}]">
                    <i-input v-model="accountForm.userPhone" placeholder="请输入11位手机号码"></i-input>
                </i-form-item>
                <i-form-item label="验证码:" prop="code" :rules="[{required:true, message:'请输入验证码', trigger:'blur'}]">
                    <i-input v-model="accountForm.code" placeholder="请输入验证码">
                        <i-button slot="append" @click.stop="getAuthCode">{{btnName}}</i-button>
                    </i-input>
                </i-form-item>
            </i-form>
            <span slot="footer">
                <i-button @click.stop="updateAccountStatus = false">取消</i-button>
                <i-button @click.stop="updateAccount" type="primary">确定</i-button>
            </span>
        </i-dialog>
        <i-dialog v-model="updatePasswordStatus" size="small" title="修改登录密码" append-to-body>
            <i-form :model="passwordForm" ref="passwordForm" label-width="100px">
                <i-form-item label="原密码:" prop="oldPassword" :rules="[{required:true, message:'请输入原密码', trigger:'blur'}]">
                    <i-input v-model="passwordForm.oldPassword" type="password" placeholder="请输入原密码"></i-input>
                </i-form-item>
                <i-form-item label="新密码:" prop="password1" :rules="[{required:true, message:'请输入新密码，密码格式是8-20位的数字和字母', trigger:'blur'},{pattern:/^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,20}$/, message:'请输入正确的密码格式，密码格式是8-20位的数字和字母'}]">
                    <i-input v-model="passwordForm.password1" type="password" placeholder="请输入新密码，密码格式是8-20位的数字和字母"></i-input>
                </i-form-item>
                <i-form-item label="确认新密码:" prop="password2" :rules="[{required:true,trigger:'blur', validator:_validPassword}]">
                      <i-input v-model="passwordForm.password2" type="password" placeholder="请输入确认新密码，密码格式是8-20位的数字和字母"></i-input>
                  </i-form-item>
            </i-form>
            <span slot="footer"> 
                <i-button @click.stop="updatePasswordStatus = false">取消</i-button>
                <i-button @click.stop="updatePassword" type="primary">确定</i-button>
            </span>
        </i-dialog>
    </div>
</template>
<script>
import {mapMutations} from 'vuex';
import loginService from 'service/login';
import config from 'config';

export default {
    data(){
        return {
            form:{
                companyId:'',
                companyName:'',
                creditCode:'',
                cityNames:[],
                businessLicense:[],
                electronicSeal:[],
                legalPerson:'',
                certType:'',
                certTypeCn:'',
                certNo:'',
                certPhoto:[],
                companyType:'',
                companyTypeCn:'',
                password:'',
                userPhone:'',
                userType:'',
                userTypeCn:'',
            },
            imgProp:{url:'src'},
            crop:'?x-oss-process=style/w800',
            imgPerfix: config.baseImageUrl,
            updateAccountStatus:false,
            accountForm:{
                userPhone:'',
                userPassword:'',
                code:''
            },
            btnName:'获取验证码',
            seconds:60,
            timer:null,
            updatePasswordStatus:false,
            passwordForm:{
                oldPassword:'',
                password1:'',
                password2:''
            }
        }
    },
    watch:{
        updateAccountStatus(val){
            if (val) {
                this.$nextTick(() => {
                    this.$refs['accountForm'].resetFields();
                });
            }
        },
        updatePasswordStatus(val){
            if (val) {
                this.$nextTick(() => {
                    this.$refs['passwordForm'].resetFields();
                });
            }
        }
    },
    methods:{
        ...mapMutations(['setAccount','LOADING']),
        _validPassword(rule, value, callback){
            if (!value){
                return callback(new Error('请再次输入新密码'));
            }

            if (value != this.passwordForm.password1) {
                return callback(new Error('确认密码不一致'));
            }
            
            return callback();
        },
        getAccount(){
            this.LOADING(true);
            loginService.getAccount().then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    if (!res.data.electronicSeal) {
                        res.data.electronicSeal = []
                    } else {
                        res.data.electronicSeal = [res.data.electronicSeal]
                    }
                    this.setAccount(res.data);
                    this.$set(this, 'form', res.data);
                }
            });
        },
        handleSuccess(res){
            if (res.code === 0) {
                let params = {
                    companyId: this.form.companyId,
                    imageId: res.data.imageId
                }

                loginService.addElectron(params).then(result => {
                    if (result.code === 0) {
                        this.$message.success('新增成功');
                        this.$set(this.form, 'electronicSeal', [{
                            src:res.data.src,
                            imageId:res.data.imageId
                        }]);
                    }
                });
                
            }
        },
        handleValidate(file){
            console.log(file)
        },
        handleElectronDelete(index, item){
            loginService.deleteElectron(item.attachmentId).then(res => {
                if (res.code === 0) {
                    this.$message.success('删除成功');
                }
            });
        },
        getAuthCode(){
            if (!this.accountForm.userPhone) {
                this.$message.error('请先输入手机号!');
                return;
            }

            if (this.timer) {
                return;
            }

            loginService.getAuthCodeByPhone(this.accountForm.userPhone).then(res => {
                if (res.code === 0) {
                    this.$message.success('已发送成功');
                    this.timer = setInterval(() => {
                        if (this.seconds == 0) {
                            clearInterval(this.timer);
                            this.btnName = "获取验证码";
                            this.seconds = 60;
                            return;
                        }
                        this.btnName = this.seconds + "s后重新获取";
                        this.seconds--;
                    }, 1000);
                }
            });
        },
        updateAccount(){
            this.$refs['accountForm'].validate(valid => {
                if (valid) {
                    let params = JSON.parse(JSON.stringify(this.accountForm));

                    loginService.updateAccount(params).then(res => {
                        if (res.code === 0) {
                            this.form.userPhone = params.userPhone;
                            this.setAccount(this.form);
                            this.updateAccountStatus = false;
                            this.$message.success('修改成功');
                        }
                    });
                }
            });
        },
        updatePassword(){
            this.$refs['passwordForm'].validate(valid => {
                if (valid) {
                    let params = JSON.parse(JSON.stringify(this.passwordForm));

                    loginService.updatePassword(params).then(res => {
                        if (res.code === 0) {
                            this.updatePasswordStatus = false;
                            this.$message.success('修改成功');
                        }
                    });
                }
            });
        }
    },
    created(){
        this.getAccount();
    },
   
}
</script>
<style lang="scss">
.account{
    .el-card{
        border-color: #e9e9e9;
        border-radius: 0;
        box-shadow: none;
        margin-bottom: 8px;
    }

    .el-card-header{
        padding:0 20px;
        line-height: 40px;
        border-bottom-color: #e9e9e9;
        color:#000;
        font-weight: bold;
    }

    .el-card-body{
        padding: 24px;
    }

    .info-row{
        line-height: 20px;
        margin-bottom: 15px;
        overflow: hidden;

        .label{
            float: left;
            color:#aaa;
            font-weight: 500;
            margin-right: 8px;
        }

        .text{
            color: #000;
            font-weight: 500;
        }
        
    }

    .upload-pc{
        width: 120px;
        height: 120px;
        line-height: 120px;
        text-align: center;
        border: 1px dashed #c0ccda;
        border-radius: 4px;
        float: left;
        .el-upload{
            width: 100%;
        }
    }

}
</style>

