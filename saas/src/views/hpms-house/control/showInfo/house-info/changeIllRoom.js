
import config from 'config';
import hpmsService from 'service/hpms-house';
import dictsService from 'service/common';
import tpl from './changeIllRoom.html';
import './house-info.scss';
import {
    mapMutations
} from 'vuex';

export default {
    name:"changeIllRoom",
    template: tpl,
    props:{
        id:String,
    },
    data () {
        return {
            form1:{
                illRoom:[]
            },
            labelWidth:"76px",
        }
    },
    mounted(){
    },
    
    methods: {
        ...mapMutations(['LOADING']),
        
        init(data){
            if(data.illRoom=='Y'){
                this.form1.illRoom.push('illRoom')
            }
        },
        submit(){
            this.$refs.form1.validate(valid => {
                if (valid) {
                    var params={
                        roomId:this.id,
                        illRoom:"N",
                    }
                    this.form1.illRoom.forEach(items=>{
                        if(items=='Y'){
                            params.illRoom="Y"
                        }
                        
                    })
                    this.$emit('saveChangeIllRoom',params);
                }
            })
        },
        unset(){
            this.$emit('hideChangeIllRoom');
        }
       
    },
    created(){
      
    }
}
