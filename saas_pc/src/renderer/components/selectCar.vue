<template>
  <div id="selectCar">
    <el-dialog :visible.sync="carsShow"   :close-on-press-escape='true' :close-on-click-modal="false" :show-close="false" top='9vh'>
        <div class="carTitle" slot="title">
          选择车辆
          <span class="iconfont icon-close iconfont-close" @click="closeBox"></span>
        </div>
        <div class='classCar'>
              <div  class='seatNumber'  v-for="(item, index) in carnumber" :key="index">
                <span class='seatCar'>{{item.seat_num}}座</span> 
                <span class='addNumber' v-show='item.car_num>0'  >
                  <em class='reduce' @click='reduce(index)' :disabled='item.car_num===0'>-</em>
                  <input class='inputNumber' disabled="disable" type="number" v-model.number="item.car_num" />
                  <em class='increase'  @click='increase(index)'>+</em>
                </span>
             </div>
        </div>
      <div class='bottm_btn'>
        <div class='choseCar'>已选择：<span>{{carClass}}</span>
        <!-- <span style="color:#0DB592;">{{num}}</span>辆 -->
        </div>
        <div class='cancelBtn' @click='cancelChose()'>清空 </div>
        <div class='sureBtn' @click='sureChose()'>确定 </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  export default{
    name:'selectCar',
    props:{
      selectedCarShow:{
        type:Boolean,
        default: false
      },
      carInfo:{
        type:Array
      },
      carIndex:{
        type:Number
      },
      carContent:{
        type:String
      }
    },
    data (){
      return{
         carsShow: this.selectedCarShow,
         cars:0,
         carss:'5',
         carnumber:[],
         content:'',
         btnShow:true,
         carClass:this.carContent,
         newArr:[],
         oldNumber:this.carInfo,
         carIndexs:this.carIndex,
         num:0
      }
    },
    watch:{
      carInfo(newVal){
        this.oldNumber=newVal
      },
      selectedCarShow(newVal){
        this.carsShow = newVal
        if(this.carsShow){
          this.getCarInfo()
          this.carClass=this.carContent
        }
      },
      carsShow(newVal){
        this.$emit('update:selectedCarShow', newVal)
      },
      carIndex(newVal){
         this.carIndexs =newVal
      },
      carContent(newVal){
         this.carClass =newVal
      },
    },
    created(){
      // 防止点击多次出现蓝色阴影
      document.ondragstart = document.onselectstart = function(){return false;};
    },
    mounted () {
       this.carClass=''
       this.num=0
    },
    methods:{
       closeCar(){
         this.carsShow=false
       },
       closeBox(){
         this.carsShow=!this.carsShow
       },
       //获取车辆信息
       getCarInfo(){
            let params={
              type:0
            }
            this.$axios.get(this.$httpUrl.getcarInfo, { params:params }).then(res => {
                if (res != false) {
                    for(var i=0;i<res.length;i++){
                        if(!res[i].car_num){
                            res[i].car_num=0
                        }
                    }
                    this.carnumber=res
                    if(this.oldNumber.length>0){
                      for(var z=0;z<this.oldNumber.length;z++){
                            this.num = this.num+this.oldNumber[z].car_num
                        }
                      for(var i=0;i<this.carnumber.length;i++){
                          for(var j=0;j<this.oldNumber.length;j++){
                            if(this.carnumber[i].seat_num==this.oldNumber[j].seat_num){
                                this.carnumber[i].car_num=this.oldNumber[j].car_num
                            }
                        }
                      }
                    }
                }
              })
              this.getCarMessage()
       },
       //增加减少车辆
       reduce(index){
        if(this.carnumber[index].car_num==0) {
          reuturn
        }
        var num = this.carnumber[index].car_num
        this.carnumber[index].car_num= num-1
        this.getCarMessage()
       },
       increase(index){
         this.carnumber[index].car_num++
         this.getCarMessage()
       },
       // 清空
       cancelChose(){
            let params={
              type:0
            }
            this.$axios.get(this.$httpUrl.getcarInfo, { params:params }).then(res => {
                if (res != false) {
                    for(var i=0;i<res.length;i++){
                        if(!res[i].car_num){
                            res[i].car_num=0
                        }
                    }
                    this.carnumber=res
                }
              }) 
              this.carClass=''
              this.newArr=[]
       },
      //  确认选择
       sureChose(){
         let carsArr=this.newArr
         let carText = this.carClass
         let carIndexs = this.carIndexs
         this.$emit('closeCar',carsArr,carText,carIndexs)
         this.carsShow =false
       },
      //  联动
       getCarMessage(){
         this.carClass=''
         this.num =0
          var newarr = []
         for(var i=0;i<this.carnumber.length;i++){
           if(this.carnumber[i].car_num>0){
              newarr.push(this.carnumber[i])
           }
         }
         this.newArr =newarr
         for(var j=0;j<this.newArr.length;j++){
            this.carClass= this.newArr[j].seat_num+'座*'+this.newArr[j].car_num+' '+this.carClass
            this.num = this.newArr[j].car_num+this.num
         }
       }
    },
    computed:{
    }
  }
</script>
<style lang='less' scoped>
    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
        -webkit-appearance: none;
    }
    input[type="number"]{
        -moz-appearance: textfield;
    }
    body{
      -moz-user-select: none; /*火狐*/
      -webkit-user-select: none; /*webkit浏览器*/
      -ms-user-select: none; /*IE10*/
      -khtml-user-select: none; /*早期浏览器*/
      user-select: none;
    }
#selectCar{
   .el-dialog{
    width: 661px;
    .carTitle{
      text-align: center;
      line-height: 40px;
      .iconfont-close{
        display: inline-block;
        float: right;
        margin-right: 21px;
      }
    }
    .classCar{
      width:100%;
      height:373px;
      border-bottom:1px solid #E7E7E7;
      Column-count:3;
      // column-gap:30px;
      // overflow: auto;
      .borderClass{
         border:1px solid #0DB592;
      }
      .seatNumber{
        // width:114px;
        height:31px;
        // border-radius:22px;
       cursor:pointer;
        position:relative;
        margin-bottom:11px;
        overflow: auto;
        &:hover .addNumber{
          display: inline-block !important;
        }
        em{
          font-style:normal;
          font-size:18px;
          cursor:pointer;
        }
        .seatCar{
          display:inline-block;
          margin-top:7px;
          margin-left:14px;
          
        }
        .addNumber{
          position:absolute;
          top:3px;
          left:80px;
          line-height: 25px;
          height: 25px;
          width: 80px;
          text-align: center;
          border-radius: 15px;
          border:1px solid #0DB592 ;
          .inputNumber{
            width:25px;
            text-align:center;
            background:#ffffff;
          }
        }  
      }
    }
    .bottm_btn{
        width:100%;
        height:41px;
        position:relative;
        .choseCar{
          margin-top:17px;
          width:383px;
          height:20px;
        }
        .cancelBtn{
            width:52px;
            font-size:16px;
            color:#000000;
            position:absolute;
            top:8px;
            right:132px;
            cursor:pointer;
        }
        .sureBtn{
          width:114px;
          height:35px;
          line-height:35px;
          text-align:center;
          border-radius:24px;
          background:#0DB592;
          border:1px solid #0DB592;
          font-size:16px;
          color:#FFFFFF;
          position:absolute;
          top:0;
          right:0;
           cursor:pointer;
        }
      }
  }
}
</style>
