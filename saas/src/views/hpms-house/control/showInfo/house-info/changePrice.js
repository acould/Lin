
import config from 'config';
import hpmsService from 'service/hpms-house';
import dictsService from 'service/common';
import tpl from './changePrice.html';
import './house-info.scss';
import {
    mapMutations
} from 'vuex';

export default {
    name:"changePrice",
    template: tpl,
    props:{
        id:String,
    },
    data () {
        return {
            form1:{
                dayPrice:null,
                monthPrice:null,
            },
            formRules:{},
            labelWidth:"76px",
        }
    },
    mounted(){
    },
    
    methods: {
        ...mapMutations(['LOADING']),
        init(data){
            console.log('price-init',data);
            data.dayPrice ? this.form1.dayPrice=data.dayPrice : ''
            data.monthPrice ? this.form1.monthPrice=data.monthPrice : ''
            console.log('form1',this.form1);
        },
        submit(){
            this.$refs.form1.validate(valid => {
                if (valid) {
                    var params={
                        roomId:this.id,
                        dayPrice: this.form1.dayPrice ? parseFloat(this.form1.dayPrice) : '',
                        monthPrice:this.form1.monthPrice ?parseFloat(this.form1.monthPrice):'',
                    }
                    
                    this.$emit('saveChangePrice',params);
                }
            })
        },
        unset(){
            this.$emit('hideChangePrice');
        }
       
    },
    created(){
      
    }
}
