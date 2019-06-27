
import config from 'config';
import hpmsService from 'service/hpms-house';
import dictsService from 'service/common';
import tpl from './changeRentType.html';
import './house-info.scss';
import {
    mapState,mapMutations
} from 'vuex';

export default {
    name:"changeRentType",
    template: tpl,
    props:{
        id:String,
    },
    data () {
        return {
            form1:{

                rentType:[]
            },
            labelWidth:"76px",
        }
    },
    mounted(){
	},
	computed:{
        ...mapState(['dict'])
    },
    
    methods: {
        ...mapMutations(['LOADING']),
        _rentType(rule, value, callback){
            if(this.form1.rentType.length==0){
                callback(new Error('请选择出租方式'));
            }
            callback();
        },
        init(data){
            if(data.monthRent=='Y'){
                this.form1.rentType.push('MONTH_RENT')
            }
            if(data.dayRent=='Y'){
                this.form1.rentType.push('DAY_RENT')
            }
        },
        submit(){
            this.$refs.form1.validate(valid => {
                if (valid) {
                    var params={
                        roomId:this.id,
                        monthRent:"N",
                        dayRent:"N",
                    }
                    this.form1.rentType.forEach(items=>{
                        if(items=='MONTH_RENT'){
                            params.monthRent="Y"
                        }
                        if(items=='DAY_RENT'){
                            params.dayRent="Y"
                        }
                    })
                    this.$emit('saveChangeRentType',params);
                }
            })
        },
        unset(){
            this.$emit('hideChangeRentType');
        }
       
    },
    created(){
      
    }
}
