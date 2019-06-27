<template>
    <div class="imgs-box">
        <i-img-list v-model="imgList"  :props="imgProp" :perfix="imgPerfix" :crop="crop" :delete-tip="true" @delete="imgDel"></i-img-list>
        <div class="upload-pc" @click="openUpImgBox">
			<i class="el-icon-plus"></i>
        </div>
		<i-dialog v-model="isShowImgBox"  size="large" title="上传图片" append-to-body >
			<imgBox ref="showImgList" 
				@closeUpImgBox="closeUpImgBox"
				@imgSubmit="imgSubmit"
			>
			</imgBox>
		</i-dialog>
    </div>
</template>



<script>
import config from 'config';
import hpmsService from 'service/hpms-house';
import imgBox from '../../update-form/imgbox';
import {
    mapState,mapMutations
} from 'vuex';


export default {
    props:{
        data:Array,
        roomId:String,
	},
	components:{
		'imgBox':imgBox,
	},
    data () {
        return {
            activeName:'houseInfo',
            imgList:[],
            imgProp:{url:'src'},
            crop:'?x-oss-process=style/w800',
			imgPerfix: config.baseImageUrl,
			isShowImgBox:false,
        }
    },
    mounted(){
    },
    
    methods: {
		...mapMutations(['LOADING']),
        init(){
            this.imgList=[];
            let imgData=this.data;
            for(  let index in imgData){
                for( let i in imgData[index].attachmentList){
                    let item=imgData[index].attachmentList[i];
                    this.imgList.push({
                        src:item.src,
                        imageId:item.imageId,
                        infos:{"attachmenType":item.attachmenType,"attachmentId":item.attachmentId}
                    })
				}
            }
            
        },
        imgDel(index,object){
            let id=object.imageId;
            hpmsService.imgDel(id).then(res=>{
                if (res.code === 0) {
                    // this.$message.success('删除成功');
                }else{
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
            })
        
        },
       
		closeUpImgBox(){
            this.isShowImgBox=false;
		},
		imgSubmit(data,index){
			let params={
				roomId:this.roomId,
				attachmentList:data
			}
			hpmsService.saveRoomInfoImgs(params).then(res=>{
				if (res.code === 0) {
					this.$message.success('保存成功');
					this.getHouseInfo(params);
					this.closeUpImgBox();
                }else{
                    this.$message.error( res && res.msg ? res.msg :'请求失败,请重试');
                }
			})
            //this.templateList[index].attachmentList=data;
           // this.closeUpImgBox();
		},
		openUpImgBox(index){
			this.isShowImgBox=true;
            this.$nextTick(() => {
                this.$refs['showImgList'].init(this.data)
            })
		},
		getHouseInfo(params){
			this.imgList=[];
			let imgData=params.attachmentList;
			console.log('re house inf img ')
			for(  let index in imgData){
				for( let i in imgData[index].attachmentList){
					let item=imgData[index].attachmentList[i];
					this.imgList.push({
						src:item.src,
						imageId:item.imageId,
						infos:{"attachmenType":item.attachmenType,"attachmentId":item.attachmentId}
					})
				}
			}
		}
    }
}
</script>
<style lang="scss" scoped>
.imgs-box{
    padding: 16px;
}
.upload-pc{
    width: 115px;
    height: 115px;
    line-height: 115px;
    text-align: center;
    border: 1px dashed #c0ccda;
    border-radius: 4px;
    float: left;
    // margin:10px 10px 30px 0px;
    .el-upload{
        width: 100%;
    }
}
</style>