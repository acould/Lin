
import config from 'config';
import hpmsService from 'service/hpms-house';
import dictsService from 'service/common';
import tpl from './editNos.html';
import './editNos.scss';
import {
    mapMutations
} from 'vuex';

export default {
    name:"editNos",
    template: tpl,
    props:{
        id:String,
    },
    data () {
        return {
            form1:{
                waterNo:null,
                electricNo:null,
                gasNo:null,
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
            data.waterNo ? this.form1.waterNo=data.waterNo : ''
            data.electricNo ? this.form1.electricNo=data.electricNo : ''
            data.gasNo ? this.form1.gasNo=data.gasNo : ''
        },
        submit(){
            this.$refs.form1.validate(valid => {
                if (valid) {
                    var params={
                        roomId:this.id,
                        waterNo: this.form1.waterNo ? this.form1.waterNo : '',
                        electricNo: this.form1.electricNo ? this.form1.electricNo : '',
                        gasNo: this.form1.gasNo ? this.form1.gasNo : '',
                    }
                    
                    this.$emit('saveEditNos',params);
                }
            })
        },
        unset(){
            this.$emit('hideEditNos');
        }
       
    },
    created(){
      
    }
}
