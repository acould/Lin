
import config from 'config';
import hpmsService from 'service/hpms-house';
import dictsService from 'service/common';
import tpl from './changeRemark.html';
import './house-info.scss';
import {
    mapMutations
} from 'vuex';

export default {
    name:"changeRemark",
    template: tpl,
    props:{
        id:String,
    },
    data () {
        return {
            form1:{
                remark:''
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
            data.remark ? this.form1.remark=data.remark : ''
        },
        submit(){
            this.$refs.form1.validate(valid => {
                if (valid) {
                    var params={
                        roomId:this.id,
                        remark: this.form1.remark ? this.form1.remark : '',
                    }
                    this.$emit('saveChangeRemark',params);
                }
            })
        },
        unset(){
            this.$emit('hideChangeRemark');
        }
       
    },
    created(){
      
    }
}
