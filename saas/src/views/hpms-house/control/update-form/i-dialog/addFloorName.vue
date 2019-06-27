<template>
    <i-dialog v-model="show" size="small" :title="this.title? this.title: '编辑楼层'"  append-to-body >
        <div class="sel-tmplist-box">
            <!-- <i-form-item label="模板名称" > -->
            <i-form :model="formData"  >
				<i-row :gutter="10" style="">
					<i-col :span="12">
						<i-form-item label="楼层:" :rules="[{required:true, message:'请输入楼层', trigger:'blur'}]">
							<i-input v-model.trim='formData.floorName'  placeholder="请输入楼层">
								<template slot="append">层</template>
							</i-input>
						</i-form-item>
					</i-col>
					<i-col :span="12">
						<i-form-item label="间数:" :rules="[{required:true, message:'请输入间数', trigger:'blur'}]">
							<i-input v-model.trim='formData.rooms' placeholder="请输入间数">
								<template slot="append">间</template>
							</i-input>
						</i-form-item>
					</i-col>
				</i-row>
				<i-row :gutter="10" style="margin: 6px 0;">
					<i-col :span="24">
						<span style="font-size:12px;">房号生成特殊规则:</span>
					</i-col>
				</i-row>
				<i-row :gutter="10" style="height: 36px;width:90%;margin:0 auto 20px;">
					<i-col :span="10">
						<i-checkbox-group id="checkbox-two" v-model="formData.hasRoomPrefix">
							<i-checkbox id="checkbox-span" label="Y">加前缀</i-checkbox>
						</i-checkbox-group>
						<i-input v-model.trim='formData.roomPrefix' class="room-input" style="display: inline-block;width:100px;"></i-input>
					</i-col>
					<i-col :span="6" style="display: inline-block;line-height: 2.4;">
						<i-checkbox-group id="checkbox-two"  v-model="formData.removeFour">
							<i-checkbox id="checkbox-span" label="Y">房间号去掉"4"</i-checkbox>
						</i-checkbox-group>
					</i-col>
					<i-col :span="8" style="display: inline-block;line-height: 2.4;">
						<i-checkbox-group id="checkbox-two"  v-model="formData.fillZero">
							<i-checkbox id="checkbox-span" label="Y">个位数楼层按2位生成</i-checkbox>
						</i-checkbox-group>
					</i-col>
				</i-row>
            </i-form>
            <div class="hpms-button-box">
                <div class="right-button-box" style="padding-bottom: 20px;">
                    <i-button @click.stop="unset()">取消</i-button>
                    <i-button @click.stop="submit()"  type="primary">提交</i-button>
                </div>
            </div>
        </div>
    </i-dialog>
</template>


<script>
export default {
    props:{
        title:String,
    },
    data(){
        return {
            show:false,
            labelWidth:'95px',
            formData:{
                floorId:'',
                floorName:'',
				index:'',
				hasRoomPrefix:[],
				roomPrefix:'',
				removeFour:[],
				fillZero:[],
			},
			keyNum:0,
            
        }
    },
    mounted(){
    },
    methods: {
        open(data,index){
			this.show=true;
			if(typeof data !=='undefined'){
				this.formData.floorId=data.floorId;
				this.formData.floorName=data.floorName;
				if(data.hasRoomPrefix=='Y'){
					this.formData.hasRoomPrefix=['Y'];
				}
				if(data.removeFour=='Y'){
					this.formData.removeFour=['Y'];
				}
				if(data.fillZero=='Y'){
					this.formData.fillZero=['Y'];
				}
			}
			
			if(index){
				this.formData.index=index;
			}

			
			console.log(this.formData);
            
        },
        close(){
            this.show=false;
        },
        unset(){
            this.close();
        },
        submit(){
			var ru={
                floorName:/^((-\d+)|(0+)|(\d+))$/, //正负整数包含0
                rooms:/^((0+)|(\d+))$/, //正负整数包含0
			};
            let formData=JSON.parse(JSON.stringify(this.formData));
            if( !ru.floorName.test(formData.floorName) || formData.floorName<-5 || formData.floorName>100 ){
                this.$message.error('楼层请输入-5到100之间的整数'); 
                return ;
			}

			if( !ru.rooms.test(formData.rooms) || formData.rooms<0 || formData.rooms>1000 ){
                    this.$message.error('楼层请输入0到1000之间的整数'); 
                    return ;
            }
			
			if(formData.hasRoomPrefix==''){
				formData.hasRoomPrefix='N';
			}else{
				formData.hasRoomPrefix='Y';
			}
			if(formData.removeFour==''){
				formData.removeFour='N';
			}else{
				formData.removeFour='Y';
			}
			if(formData.fillZero==''){
				formData.fillZero='N';
			}else{
				formData.fillZero='Y';
			}
            this.$emit('submit',formData);
        }
       
    }
}
</script>

