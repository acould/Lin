<template>
    <div class="month-contract">
        <i-tabs v-model="activeName">
            <i-tab-pane label="合同信息" name="first" :disabled="true">
                <div class="info-box">
                    <h6 class="apartment-name">签约房源:{{form['apartmentName']}} {{form.roomName}}</h6>
                    <i-row>
                        <i-col :span="24">
                            <label style="font-size:12px;color:#5e6d82">地址:</label>
                            <span style="font-size:12px">{{form['apartmentAddress']}}</span>
                        </i-col>
                        <i-col :span="6">
                            <label style="font-size:12px;color:#5e6d82">合同状态:</label>
                            <span style="font-size:12px">{{form['orderStatusCn']}}</span>
                        </i-col>

						<i-col :span="6" v-show="state=='update'">
                            <label style="font-size:12px;color:#5e6d82">签约方式:</label>
                            <span style="font-size:12px">{{form['signTypeCn']}}</span>
                        </i-col>
                    </i-row>
                </div>
                <i-form :model="form" ref="form" label-width="100px">
                    <div class="divider">
                        <label>承租人</label>   
                        <i-form-item label-width="0px" prop="isCheckIn" class="checkbox-wrap" style="right:110px">
                            <i-checkbox v-model="form.isCheckIn">非本人入住</i-checkbox>
                        </i-form-item>
                        <i-form-item label-width="0px" prop="isSigned" class="checkbox-wrap" style="right:0">
                            <i-checkbox v-model="form.isSigned">非本人签约</i-checkbox>
                        </i-form-item> 
                    </div>
					<div class="divider">
						<label class="is-span-required">签约方式</label>   
						<i-form-item label-width="0px"  prop="signType" class="checkbox-wrap" style="right:0">
							<i-radio-group v-model="form.signType">
								<i-radio label="ELECTRONIC_SIGN" style="right:0">电签</i-radio>
								<i-radio label="PAPER_SIGN" style="right:0">纸签</i-radio>
							</i-radio-group>
						</i-form-item> 
                    </div>
					
                    <div class="info-box">
                        <i-row :gutter="10">
                            <div class="lessee">
                                <i-col :span="8">
                                    <i-form-item prop="lessee.customerName" label="承租人姓名:" :rules="[{required:true,message:'请输入承租人姓名',trigger:'blur'}]">
                                        <i-input v-model="form.lessee.customerName"></i-input>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="8">
                                    <i-form-item prop="lessee.cardType" label="证件类型:" :rules="[{required:true, message:'请选择证件类型', trigger:'change'}]">
                                        <i-select v-model="form.lessee.cardType">
                                            <i-option v-for="(option, index) in dictCertType" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                                        </i-select>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="8">
                                    <i-form-item prop="lessee.idCard" label="证件号码:" :rules="[{required:true, message:'请输入证件号码', trigger:'blur'}]">
                                        <i-input v-model="form.lessee.idCard"></i-input>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="8">
                                    <i-form-item prop="lessee.customerPhone" label="手机号码:" :rules="[{required:true, message:'请输入手机号码', trigger:'blur'}]">
                                        <i-input v-model="form.lessee.customerPhone"></i-input>                                
                                    </i-form-item>
                                </i-col>
								<i-col :span="8">
                                    <i-form-item prop="lessee.sourceType" label="来源渠道:" :rules="[{required:true, message:'请选择用户来源渠道', trigger:'change'}]">
                                        <i-select v-model="form.lessee.sourceType">
                                            <i-option v-for="(option, index) in dict['MonthCustSource']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                                        </i-select>
                                    </i-form-item>
                                </i-col>
								<i-col :span="8">
                                    <i-form-item prop="lessee.email" label="电子邮箱:" :rules="[{required:true, message:'请输入电子邮箱', trigger:'blur'},{type:'email',message:'请输入正确格式', trigger:'blur'}]">
                                        <i-input v-model="form.lessee.email"></i-input>                                
                                    </i-form-item>
                                </i-col>

                                <i-col :span="24">
                                    <i-form-item prop="lessee.cardAttachments" label="证件照片:">
                                        <i-img-list v-model="form['lessee']['cardAttachments']" :props="imgProp" :perfix="imgPerfix" :crop="crop" :show-del="false"></i-img-list>
                                        <i-uploader class="upload-pc"
                                            action="/gaodu-files/upload/image"
                                            :show-file-list="false"
                                            :on-success="handleLesseeSuccess"
                                            :multiple="true"
                                            :before-upload="handleLesseeValidate">
                                            <i class="el-icon-plus"></i>
                                        </i-uploader>
                                    </i-form-item>
                                </i-col>
                            </div>
                            <div class="agent" v-if="form.agent">
                                <i-col :span="8">
                                    <i-form-item prop="agent.customerName" label="代理人姓名:">
                                        <i-input v-model="form.agent.customerName"></i-input>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="8">
                                    <i-form-item prop="agent.cardType" label="证件类型:">
                                        <i-select v-model="form.agent.cardType">
                                            <i-option v-for="(option, index) in dict['CertType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                                        </i-select>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="8">
                                    <i-form-item prop="agent.idCard" label="证件号码:">
                                        <i-input v-model="form.agent.idCard"></i-input>
                                    </i-form-item>
                                </i-col>
                                <i-col :span="8">
                                    <i-form-item prop="agent.customerPhone" label="手机号码:">
                                        <i-input v-model="form.agent.customerPhone"></i-input>                                
                                    </i-form-item>
                                </i-col>
                                <i-col :span="24">
                                    <i-form-item prop="agent.cardAttachments" label="证件照片:">
                                        <i-img-list v-model="form['agent']['cardAttachments']" :props="imgProp" :perfix="imgPerfix" :crop="crop" :show-del="false"></i-img-list>
                                        <i-uploader class="upload-pc"
                                            action="/gaodu-files/upload/image"
                                            :show-file-list="false"
                                            :on-success="handleAgentSuccess"
                                            :multiple="true"
                                            :before-upload="handleAgentValidate">
                                            <i class="el-icon-plus"></i>
                                        </i-uploader>
                                    </i-form-item>
                                </i-col>
                            </div>
                        </i-row>
                    </div>
                    <div class="divider">合同内容</div>
                    <div class="info-box">
                        <i-row :gutter="10">
                            <i-col :span="8">
                                <i-form-item label="合同号:" prop="orderNum">
                                    <i-input v-model="form.orderNum"></i-input>
                                </i-form-item>
                            </i-col>
							<i-col :span="8">
                                <i-form-item label="签约日期:" prop="signDate" :rules="[{required:true, message:'请选择签约日期', trigger:'change',type:'date'}]">
                                    <i-date-picker v-model="form.signDate" type="date" style="width:100%"></i-date-picker>
                                </i-form-item>
                            </i-col>

                            <i-col :span="8">
                                <i-form-item label="合同期:" prop="date" :rules="[{required:true, message:'请选择合同期', type:'array', trigger:'change'}]">
                                    <i-date-picker v-model="form.date" type="daterange" style="width:100%"></i-date-picker>
                                </i-form-item>
                            </i-col>
                            <i-col :span="8">
                                <i-form-item label="付款方式:" prop="payPeriod" :rules="[{required:true, message:'请选择付款方式', trigger:'change'}]">
                                    <i-select v-model="form.payPeriod">
                                        <i-option v-for="(option, index) in dict['PayPeriod']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                                    </i-select>
                                </i-form-item>
                            </i-col>
                            <i-col :span="8">
                                <i-form-item label="月租金:" prop="price" :rules="[{required:true, message:'请输入月租金', type:'number'}]">
                                    <i-input v-model.number="form.price"></i-input>
                                </i-form-item>
                            </i-col>
							<i-col :span="8">
                                <i-form-item label="押金方式:" prop="depositPeriod" :rules="[{required:true, message:'请选择押金方式', trigger:'change'}]">
                                    <i-select v-model="form.depositPeriod">
                                        <i-option v-for="(option, index) in dict['DepositPeriod']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                                    </i-select>
                                </i-form-item>
                            </i-col>
                            <i-col :span="8">
                                <i-form-item label="押金:" prop="receiptsDeposit" :rules="[{required:true, message:'请输入押金', type:'number'}]">
                                    <i-input v-model.number="form.receiptsDeposit"></i-input>
                                </i-form-item>
                            </i-col>
                            <i-col :span="8">
                                <i-form-item label="收款日期类型:" prop="paymentType" :rules="[{required:true, message:'请选择收款日期类型'}]">
                                    <i-radio-group v-model="form.paymentType">
                                        <i-radio v-for="(option, index) in dict['PayDateType']" :key="index" :label="option.dictKey">{{option.dictValue}}</i-radio>
                                    </i-radio-group>
                                </i-form-item>
                            </i-col>
                            <i-col :span="8" v-if="form.paymentType == 'ADVANCE'" :rules="[{required:true, message:'请输入提前天数', type:'number'}]">
                                <i-form-item label="提前天数:" prop="paymentAdvanceDays" class="is-required">
                                    <i-input v-model.number="form.paymentAdvanceDays">
										<template slot="append">天</template>
									</i-input>
                                </i-form-item>
                            </i-col>
                            <i-col :span="8" v-if="form.paymentType == 'FIXED_DATE'" :rules="[{required:true, message:'请输入每月几号', type:'number'}]">
                                <i-form-item label="每月几号:" prop="paymentDays" class="is-required">
                                    <i-input v-model.number="form.paymentDays">
										<template slot="append">号</template>
									</i-input>
                                </i-form-item>
                            </i-col>
                            <i-col :span="24">
                                <i-form-item label="其他费用:">
                                    <div class="other-item" v-for="(item,itemIndex) in form.otherFees" :key="itemIndex">
                                        <i-col :span="7" style="padding-right:10px">
                                            <i-form-item label-width="0px">
                                                <i-select v-model="item.feeType" clearable>
                                                    <i-option v-for="(option, index) in dict['ChargeType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                                                </i-select>
                                            </i-form-item>
                                        </i-col>
                                        <i-col :span="7" style="padding-right:10px">
                                            <i-form-item label-width="0px">
                                                <i-select v-model="item.paymentType" clearable>
                                                    <i-option v-for="(option, index) in dict['OtherFeePayType']" :key="index" :label="option.dictValue" :value="option.dictKey"></i-option>                                    
                                                </i-select>
                                            </i-form-item>
                                        </i-col>
                                        <i-col :span="7" style="padding-right:10px">
                                            <i-form-item label-width="0px">
                                                <i-input v-model="item.money"></i-input>
                                            </i-form-item>
                                        </i-col>
                                        <i-col :span="3">
                                            <a class="btn add" v-if="itemIndex == 0" @click.stop="addOtherFee">
                                                <i class="el-icon-plus"></i>
                                            </a>
                                            <a class="btn delete" v-if="itemIndex != 0" @click.stop="deleteOtherFee(itemIndex)">
                                                <i class="el-icon-minus"></i>
                                            </a>
                                        </i-col>
                                    </div>
                                </i-form-item>
                            </i-col>
                            <i-col :span="24">
                                <i-form-item label="备注及补充约定:" prop="remark">
                                    <i-input type="textarea" v-model="form.remark"></i-input>
                                </i-form-item>
                            </i-col>
                            <i-col :span="24">
                                <i-form-item label="合同附件:" prop="attachments">
                                    <i-img-list v-model="form['attachments']" :props="imgProp" :perfix="imgPerfix" :crop="crop" :show-del="false"></i-img-list>
                                    <i-uploader class="upload-pc"
                                            action="/gaodu-files/upload/image"
                                            :show-file-list="false"
                                            :on-success="handleSuccess"
                                            :multiple="true"
                                            :before-upload="handleValidate">
                                            <i class="el-icon-plus"></i>
                                    </i-uploader>
                                </i-form-item>
                            </i-col>
                        </i-row>
                    </div>
                </i-form>
            </i-tab-pane>
            <i-tab-pane label="租期账单" name="second" :disabled="true">
                <div>
                    <div>
                        <i-button type="text" style="margin-top:8px" @click.stop="addReceipt">新增账期</i-button>
                    </div>
                    <i-table :data="form['bills']"  style="width:100%">
                        <i-table-column prop="chargeTypeCn" label="费用类型"  align="center"></i-table-column>
                        <i-table-column label="账期"  align="center" width="200" inline-template>
                            <div>{{new Date(row.startDate).Format('yyyy-MM-dd') + '~' + new Date(row.endDate).Format('yyyy-MM-dd')}}</div>
                        </i-table-column>
                        <i-table-column prop="billMoney" label="应收金额"  align="center"></i-table-column>
                        <i-table-column prop="billDate" label="应收日期"  align="center" inline-template>
                            <div>{{new Date(row.billDate).Format('yyyy-MM-dd')}}</div>
                        </i-table-column>
                        <i-table-column prop="remark" label="备注"  align="center"></i-table-column>
                        <i-table-column prop="billStatusCn" label="收款状态"  align="center"></i-table-column>
                        <i-table-column label="操作" inline-template>
                            <div>
                                <i-button type="text" class="btn-table-operate" @click.stop="edit(row, $index)">修改</i-button>
                                <i-button type="text" class="btn-table-operate" @click.stop="del(row, $index)">删除</i-button>
                            </div>
                        </i-table-column>
                    </i-table>
                </div>
            </i-tab-pane>
			<i-tab-pane label="预览合同" v-if="state=='add' && showPirview" name="previewPane" :disabled="true">
				<div style="min-height:300px" v-html="templateHtml"></div>
				<div>
					<i-dialog v-model="selTemp" title="选择合同模板" size="tiny" append-to-body :close-on-click-modal="false">
						<i-form  ref="passwordForm" label-width="100px">
							<i-form-item label="合同模板:" :rules="[{required:true, message:'请选择合同模板', trigger:'blur'}]">
								<i-select v-model="form.templateId" filterable>
									<i-option v-for="item in templateList"  :value="item.id" :label="item.templateName"  :key="item.id"></i-option>
								</i-select>
							</i-form-item>
						</i-form>
						<div>
							<div style="float: right;padding-bottom: 20px;">
								<i-button @click.stop="selTemp=false" v-if="form.templateId!=''">取消</i-button>
								<i-button @click.stop="printPreView()" type="primary">提交</i-button>
							</div>
						</div>
					</i-dialog> 
				</div>
			</i-tab-pane>
        </i-tabs>
        <i-dialog v-model="receiptDialogStatus" :title="receiptState == 'update' ? '修改应收' : '新增应收'" append-to-body>
            <receipt ref="receipt" @submit="submitReceipt"></receipt>
            <span slot="footer">
                <i-button @click.stop="receiptDialogStatus = false">取消</i-button>
                <i-button type="primary" @click.stop="saveReceipt">确定</i-button>
            </span>
        </i-dialog>

    </div>
</template>
<script>
import config from 'config';
import md5 from 'md5';
// var md5=require(md5);

import contractService from 'service/contract';
import financeService from 'service/hpms-finance';
import templateService from 'service/template';

import {mapState, mapMutations} from 'vuex';

import receipt from './receipt.vue';

export default {
	components:{receipt},
    data(){
        return {
            activeName:'first',
            form:{
				templateId:'',
                apartmentName:'',
                apartmentAddress:'',
                bills:[],
                orderNum:'',
                signDate:'',
                date:[],
                startDate:'',
                endDate:'',
                orderStatus:'',
                orderStatusCn:'',
                depositPeriod:'',
                payPeriod:'',
                price:'',
                receiptsDeposit:'',
                paymentType:'',
                paymentDays:'',
                paymentAdvanceDays:'',
                otherFees:[
                    {
                        feeType:'',
                        paymentType:'',
                        money:'',
                        feeId:''
                    }
                ],
                remark:'',
                attachments:[],
                isSigned: false,
				isCheckIn: false,
				signType:'ELECTRONIC_SIGN',
                lessee:{
                    customerName:'',
                    customerPhone:'',
                    cardTypeCn:'',
					cardType:'',
					sourceType:'',
                    idCard:'',
                    customerPhone:'',
                    email:'',
                    cardAttachments:[]
                },
                agent:{
                    customerName:'',
                    customerPhone:'',
                    cardTypeCn:'',
                    cardType:'',
                    idCard:'',
                    customerPhone:'',
                    email:'',
                    cardAttachments:[]
                }
            },
            imgProp:{url:'src'},
            crop:'?x-oss-process=style/w800',
            imgPerfix: config.baseImageUrl,
            receiptDialogStatus:false,
            previewStatus:true,
			selTemp:false,
			templateList:[],
			templateHtml:'',
            receiptState:'add',
			state:'add',
			showPirview:false,
			prireCacheHash:'',
        }
    },
    methods:{
		...mapMutations(['LOADING']),
		init(params){
			this.form.roomId=params.roomId;
			this.form.apartmentInfoId=params.apartmentInfoId;
			this.form.apartmentName=params.apartmentName;
			this.form.roomName=params.roomName;
			this.form.apartmentAddress=params.apartmentAddress;
			this.form.orderStatusCn=params.orderStatusCn;
		},
        getDetail(id){
            this.LOADING(true);
            contractService.getMonthContractDetail(id).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    res.data['date'] = [];
                    if (!res.data.otherFees) {
                        res.data.otherFees = [{
                            feeType:'',
                            paymentType:'',
                            money:'',
                            feeId:''
                        }];
                    }
                    this.$set(this, 'form', res.data);
                    this.form.isSigned = this.form.isSigned == 'Y' ? true : false;
                    this.form.isCheckIn = this.form.isCheckIn == 'Y' ? true : false;
                    this.form.date[0] = this.form.startDate;
                    this.form.date[1] = this.form.endDate;
                    this.state = 'update';
                }
            });
		},
		
		openReceiptList(){
			this.activeName='second'
		},
		openContractInfo(){
			this.activeName='first'
		},
        addOtherFee(){
            this.form.otherFees.push({
                        feeType:'',
                        paymentType:'',
                        money:'',
                        feeId:''
                    });
        },
        deleteOtherFee(index){
            this.form.otherFees.splice(index, 1);
        },
        handleLesseeSuccess(res){
            if (res.code === 0) {
                let item = {
                    src: res.data.src,
                    imageId:res.data.imageId
                }
                this.form.lessee.cardAttachments.push(item);
            }
        },
        handleLesseeValidate(file){
            var reg = new RegExp(/\.jpg$|\.jpeg$|\.gif$|\.png$|\.bmp$/i);

            if ( !reg.test(file.name) ) {
                this.$message.error('请上传图片格式!');
                return false;
            }
            if ( file.size > (20 * 1024 * 1024) ) {
                this.$message.error('图片最大不能超过20M!');
                return false;
            }
            return true;
        },
        handleAgentSuccess(res){
            if (res.code === 0) {
                let item = {
                    src: res.data.src,
                    imageId:res.data.imageId
                }
                this.form.agent.cardAttachments.push(item);
            }
        },
        handleAgentValidate(file){
            var reg = new RegExp(/\.jpg$|\.jpeg$|\.gif$|\.png$|\.bmp$/i);

            if ( !reg.test(file.name) ) {
                this.$message.error('请上传图片格式!');
                return false;
            }
            if ( file.size > (20 * 1024 * 1024) ) {
                this.$message.error('图片最大不能超过20M!');
                return false;
            }
            return true;
        },
        handleSuccess(res){
            if (res.code === 0) {
                let item = {
                    src: res.data.src,
                    imageId:res.data.imageId
                }
                this.form.attachments.push(item);
            }
        },
        handleValidate(file){
            var reg = new RegExp(/\.jpg$|\.jpeg$|\.gif$|\.png$|\.bmp$/i);

            if ( !reg.test(file.name) ) {
                this.$message.error('请上传图片格式!');
                return false;
            }
            if ( file.size > (20 * 1024 * 1024) ) {
                this.$message.error('图片最大不能超过20M!');
                return false;
            }
            return true;
        },
        saveReceipt(){
            this.$refs['receipt'].saveReceipt();
        },
        addReceipt(){
            this.receiptDialogStatus = true;

            this.$nextTick(() => {
                this.$refs['receipt'].addReceipt(this.form.orderId)
            });
        },
        edit(row, index){
            this.receiptDialogStatus = true;
            this.$nextTick(() => {
                this.$refs['receipt'].getDetail(row, index);
            });
        },
        del(row, index){
            this.form.bills.splice(index, 1);
		},
		
        submitReceipt(params, state, index){
            if (this.state == 'add') {  
                if (state == 'add') {
                    this.$message.success('操作成功');
                    this.form.bills.push(params);
                    this.receiptDialogStatus = false;
                } else {
                    this.$message.success('操作成功');
                    this.$set(this.form.bills, index, params);
                    this.receiptDialogStatus = false;
                }
            } else {
                if (state == 'add') {
                    this.$message.success('操作成功');
                    this.form.bills.push(params);
                    this.receiptDialogStatus = false;
                } else{
                    this.$message.success('操作成功');
                    this.$set(this.form.bills, index, params);
                    this.receiptDialogStatus = false;
                }
            }
		},
		//创建或显示
		showBill(){
			let prireCacheHash=md5(JSON.stringify(this.form));
					
			this.$refs['form'].validate(valid => {
                if (valid) {
					if(this.form.paymentType=='ADVANCE' && this.form.paymentAdvanceDays.length==0){
						this.$message.error('请输入提前天数');return ;
					}
					if(this.form.paymentType=='FIXED_DATE' && this.form.paymentDays.length==0){
						this.$message.error('请输入每月几号');return ;
					}
					
					if( typeof this.form.bills !=='undefined' && this.form.bills.length!=0  && this.prireCacheHash==prireCacheHash){
						this.openReceiptList();
						this.$emit('next');
						return false;
					}
					this.prireCacheHash=prireCacheHash;
					let otherFeeList=[];
					if(this.form.otherFees.length==1){
						if(this.form.otherFees[0].feeType=='' || this.form.otherFees[0].paymentType=='' || this.form.otherFees[0].money=='' ){
							otherFeeList=[];
							this.form.otherFees=[];
						}else{
							otherFeeList=this.form.otherFees;
						}
					}else{
						otherFeeList=this.form.otherFees;
					}
					
					let params={
						roomId:this.form.roomId,
						signDate:this.form.signDate,
						depositPeriod:this.form.depositPeriod,
						payPeriod:this.form.payPeriod,
						price:this.form.price,
						startDate:this.form.date[0],
						endDate:this.form.date[1],
						paymentType:this.form.paymentType,
						paymentDay:this.form.paymentDays,
						paymentAdvanceDays:this.form.paymentAdvanceDays,
						otherFeeList:otherFeeList,
					}
					contractService.createBill(params).then(res => {
						if (res.code === 0) {
							this.form.bills=res.data;
							this.openReceiptList();
							this.$emit('next');
						}
					});
				}
			})
		},
        saveContract(){
			// this.$emit('next');this.$emit('success');;return ;
            this.$refs['form'].validate(valid => {
                if (valid) {
					let params=this.getContractSaveParams();
                    if (this.state == 'add') {
                        contractService.addMonthContract(params).then(res => {
                            if (res.code === 0) {
                                this.$message.success('新增成功');
                                this.$emit('success');
                            }
                        });
                    } else {
                        contractService.updateMonthContract(params).then(res => {
                            if (res.code === 0) {
                                this.$message.success('保存成功');
                            }
                        }); 
                    }
                    
                }
            });
		},
		getContractSaveParams(){
			let params = JSON.parse(JSON.stringify(this.form));
			params.startDate=params.date[0];
			params.endDate=params.date[1];
			params.billBases=params.bills;
			params.isCheckIn=params.isCheckIn?'Y':'N';
			params.isSigned=params.isSigned?'Y':'N';
			if(this.state=='add'){
				// params.signType='ELECTRONIC_SIGN';
				params.contractType='NEW';
				// params.templateId=0;
			}
			return params;
		},
		getTemplateList(){
			// this.LOADING(true);
            templateService.getEffectivesTemplateList().then(res => {
                // this.LOADING(false);
                if (res.code === 0) {
					this.templateList=res.data;
				}
			});
		},
		openTempListPane(){
			// this.$emit('next');this.$emit('successMContract',true);return false;
			templateService.getEffectivesTemplateList().then(res => {
                if (res.code === 0) {
					this.templateList=res.data;
					let tempbody=this.templateList.find((item)=>{
						return item.id==this.form.templateId;
					});
					if(typeof tempbody !=='undefined'){
						this.$emit('successMContract',true);
						this.printPreViewHtml(tempbody.templateContent)
						// this.templateHtml=tempbody.templateContent;
						this.activeName='previewPane';
					}else{
						this.$emit('successMContract',false);
						this.selTemp=true;
					}
					
				}
			});
			
		},

		openTempListBox(){
			// this.$emit('next');this.$emit('successMContract',true);return false;
			templateService.getEffectivesTemplateList().then(res => {
                if (res.code === 0) {
					this.templateList=res.data;
					this.selTemp=true;
				}
			});
			
		},
		printPreView(){
			let tempbody=this.templateList.find((item)=>{
				return item.id==this.form.templateId;
			});
			if(typeof tempbody !=='undefined'){
				this.printPreViewHtml(tempbody.templateContent)
				// this.templateHtml=tempbody.templateContent;
			}
			this.activeName='previewPane'
			// this.templateHtml=tempbody.templateContent;
			this.selTemp=false;
			// this.printPreView=true;
		},
		openSelTemp(){
			this.selTemp=true;
			this.getTemplateList();
		},
		showPirviewTab(){
			this.showPirview=true;
		},
		hidePirviewTab(){
			this.showPirview=false;
		},
		printPreViewHtml(html){
			let text=html;
			let params=this.getContractSaveParams();
			this.LOADING(true);
            contractService.previeContract(params).then(res => {
				this.LOADING(false);
				if (res.code !== 0) {
					this.$message.error(res.msg ? res.msg:'服务器异常,请重试' );
					return;
				}
				for(let item in res.data){
					let req=new RegExp('\\'+item, "g");
					text=text.replace(req,res.data[item])
				}
				this.templateHtml=text;
			})
			
		}


	},
	watch:{
		'form.signType'(val){
			this.$emit('changeSignType',val);
		},
	},
    computed:{
		...mapState(['dict']),
		dictCertType(){
			let certType=JSON.parse(JSON.stringify(this.dict.CertType));
			if(this.form.signType=='ELECTRONIC_SIGN'){
				return certType;
			}else{
				let delItemIndex=certType.lastIndexOf(certType.find((item)=>{return item.dictKey=='OTHER'}))
				certType.splice(delItemIndex,1)
				return certType;
			}

		},
		
    },
    mounted(){
        //this.getDetail('2C9094E56B413442016B413442610000');
    }
}
</script>
<style lang="scss">
.month-contract{
    .apartment-name{
        font-size: 16px;
        font-weight: bold;
    }
    .info-box{
        position: relative;
        border:1px solid #ddd;
        padding:10px;
    }

    .divider{
        border-left:4px solid #329dff;
        padding-left:15px;
        margin:15px 0;
        font-weight: bold;
        position: relative;
    }

    .checkbox-wrap{
        position: absolute;
        top:-8px;

        .el-checkbox-label{
            font-weight: normal;
        }
    }

    .other-item{
        overflow: hidden;

        .el-form-item .el-form-item{
            margin-bottom: 22px;
        }
    }

    .btn{
        display: inline-block;
        width: 16px;
        height: 16px;
        line-height: 16px;
        border-radius: 50%;
        text-align: center;
        color: #fff;

        &.add{
            background: #20a0ff;
        }

        &.delete{
            background: #ff4949;
        }
	}
	.is-span-required{
		&:before{
			content: "*";
			color: #ff4949;
			margin-right: 4px;
    		margin-left: -10px;
		}
	}
}

</style>


