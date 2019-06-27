<template>
    <div class="imgs-box">
        
        <div class="img-li">
            <div class="head is-required-img">卧室</div>
            <div class="body">
                <i-img-list v-model="imgList.BEDROOM"  :props="imgProp" :perfix="imgPerfix" :crop="crop" :delete-tip="true" ></i-img-list>
                <i-uploader class="upload-pc"
                            action="/gaodu-files/upload/image" 
                            :show-file-list="false"
                            :on-success="handleSuccessA"
                            :multiple="false"
                            :before-upload="handleValidate">
                            <i class="el-icon-plus"></i>
                </i-uploader>
            </div>
        </div>

        <div class="img-li">
            <div class="head is-required-img">卫生间</div>
            <div class="body">
                <i-img-list v-model="imgList.TOILET"  :props="imgProp" :perfix="imgPerfix" :crop="crop" :delete-tip="true" ></i-img-list>
                <i-uploader class="upload-pc"
                            action="/gaodu-files/upload/image" 
                            :show-file-list="false"
                            :on-success="handleSuccessB"
                            :multiple="false"
                            :before-upload="handleValidate">
                            <i class="el-icon-plus"></i>
                </i-uploader>
            </div>
        </div>

        <div class="img-li">
            <div class="head">客餐厅</div>
            <div class="body">
                <i-img-list v-model="imgList.HALL"  :props="imgProp" :perfix="imgPerfix" :crop="crop" :delete-tip="true" ></i-img-list>
                <i-uploader class="upload-pc"
                            action="/gaodu-files/upload/image" 
                            :show-file-list="false"
                            :on-success="handleSuccessC"
                            :multiple="false"
                            :before-upload="handleValidate">
                            <i class="el-icon-plus"></i>
                </i-uploader>
            </div>
        </div>

        <div class="img-li">
            <div class="head">阳台</div>
            <div class="body">
                <i-img-list v-model="imgList.BALCONY"  :props="imgProp" :perfix="imgPerfix" :crop="crop" :delete-tip="true" ></i-img-list>
                <i-uploader class="upload-pc"
                            action="/gaodu-files/upload/image" 
                            :show-file-list="false"
                            :on-success="handleSuccessD"
                            :multiple="false"
                            :before-upload="handleValidate">
                            <i class="el-icon-plus"></i>
                </i-uploader>
            </div>
        </div>
        <div class="img-li">
            <div class="head">厨房</div>
            <div class="body">
                <i-img-list v-model="imgList.KITCHEN"  :props="imgProp" :perfix="imgPerfix" :crop="crop" :delete-tip="true" ></i-img-list>
                <i-uploader class="upload-pc"
                            action="/gaodu-files/upload/image" 
                            :show-file-list="false"
                            :on-success="handleSuccessE"
                            :multiple="false"
                            :before-upload="handleValidate">
                            <i class="el-icon-plus"></i>
                </i-uploader>
            </div>
        </div>

        <div class="hpms-button-box">
            <div class="right-button-box" style="padding-bottom: 20px;">
                <i-button @click.stop="unset()">取消</i-button>
                <i-button @click.stop="submit()" type="primary">提交</i-button>
            </div>
        </div>

    </div>
</template>



<script>
import config from 'config';
import hpmsService from 'service/hpms-house';


export default {
    props:{
        // data:Array,
        // roomId:String,
    },
    data () {
        return {
            imgList:{
                BEDROOM:[],
                TOILET:[],
                HALL:[],
                BALCONY:[],
                KITCHEN:[],
            },
            index:0,
            imgProp:{url:'src'},
            crop:'?x-oss-process=style/w800',
            imgPerfix: config.baseImageUrl,
        }
    },
    mounted(){
    },
    
    methods: {
        init(imglist,index=0){
            this.index=index;
            if(typeof imglist !=='undefined' && imglist!=null && imglist.length>0){
                imglist.forEach((val,i)=>{
                    if(val.attachmenType=='BEDROOM'){
                        this.imgList.BEDROOM=val.attachmentList;
                    }else if(val.attachmenType=='TOILET'){
                        this.imgList.TOILET=val.attachmentList;
                    }else if(val.attachmenType=='HALL'){
                        this.imgList.HALL=val.attachmentList;
                    }else if(val.attachmenType=='BALCONY'){
                        this.imgList.BALCONY=val.attachmentList;
                    }else if(val.attachmenType=='KITCHEN'){
                        this.imgList.KITCHEN=val.attachmentList;
                    }
                })
            }else{
                this.imgList={
                    BEDROOM:[],
                    TOILET:[],
                    HALL:[],
                    BALCONY:[],
                    KITCHEN:[],
                }
            }
            console.log(this.imgList);
        },
        unset(){
            console.log('unset');
            this.$emit('closeUpImgBox');
        },
       
        handleSuccessA(res){
            this.handleSuccess(res,'a');
        },
        handleSuccessB(res){
            this.handleSuccess(res,'b');
        },
        handleSuccessC(res){
            this.handleSuccess(res,'c');
        },
        handleSuccessD(res){
            this.handleSuccess(res,'d');
        },
        handleSuccessE(res){
            this.handleSuccess(res,'e');
        },
        handleSuccess(res,type){
            if (res.code === 0) {
                let params = {
                    imageId: res.data.imageId,
                    src:res.data.src
                }

                if(type=='a'){
                    this.imgList.BEDROOM.push(params)
                }else if(type=='b'){
                    this.imgList.TOILET.push(params)
                }else if(type=='c'){
                    this.imgList.HALL.push(params)
                }else if(type=='d'){
                    this.imgList.BALCONY.push(params)
                }else if(type=='e'){
                    this.imgList.KITCHEN.push(params)
                }
                
                
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
        verification(){
            if(typeof this.imgList.BEDROOM=='undefined' || this.imgList.BEDROOM.length==0){
                this.$message.error('请添加卧室图片')
                return ;
            }
            if(typeof this.imgList.TOILET=='undefined' || this.imgList.TOILET.length==0){
                this.$message.error('请添加卫生间图片')
                return ;
            }
            return true;
        },
        submit(){
            var attachmentList=[];
            var ver={
                BEDROOM:false,
                TOILET:false
            }

            if(this.verification()){
                for( let key in this.imgList){
                    if(key=='BEDROOM'){
                        if(this.imgList[key].length>0){
                            ver.BEDROOM=true;
                        }
                    }
                    if(key=='TOILET'){
                        if(this.imgList[key].length>0){
                            ver.TOILET=true;
                        }
                    }

                    attachmentList.push(
                        {
                            attachmenType:key,
                            attachmentList:this.imgList[key]
                        }
                    )
                }

               
                this.$emit('imgSubmit',attachmentList,this.index);
            }
            
        }
    }
}
</script>
<style lang="scss" scoped>
.imgs-box{
    padding: 16px;
    .img-li{
        width: 100%;
        display: inline-block;
        .head{
            margin-bottom: 12px;
        }
        .body{

        }
        .is-required-img{
            &:before{
                content: "*";
                color: #ff4949;
                margin-right: 4px;
            }
        }

        
    }
    .button-box{
       border-top: 1px solid #ccc;  
       height: 40px;  
       margin-top: 32px;
       padding-top: 18px;
       .right{
           float: right;
       }
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
    margin-bottom: 30px;
    .el-upload{
        width: 100%;
    }
}
</style>