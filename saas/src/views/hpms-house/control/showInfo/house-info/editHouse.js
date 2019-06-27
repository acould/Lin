
import config from 'config';
import hpmsService from 'service/hpms-house';
import dictsService from 'service/common';
import tpl from './editHouse.html';
import './house-info.scss';
import {
    mapState,mapMutations
} from 'vuex';

export default {
    name:"editHouse",
    template: tpl,
    props:{
        data:Object,
        roomId:String,
        // data:object,
	},
	computed:{
        ...mapState(['dict'])
    },
    data () {
        return {
           form1:{},
           labelWidth:"76px",
           select:{
               decoration:[],
               orientation:[]
           },
           formRules:{
            decoration:[
                { required:true, message:'请选择装修情况', trigger:'change' }
            ],
            orientation:[
                { required:true, message:'请选择朝向', trigger:'change' }
            ],
           }
           
        }
    },
    mounted(){
    },
    
    methods: {
        ...mapMutations(['LOADING']),
        _rlfs(rule, value, callback){
            if(typeof this.form1.rooms=='undefined' || typeof this.form1.halls=='undefined' || typeof this.form1.kitchens=='undefined' ||  typeof this.form1.toilets=='undefined'){
                callback(new Error('请输入室厅厨卫数量'));
            }else if(this.form1.rooms==0){
                callback(new Error('室的数量不能为0'));
            }else if(
                this.form1.rooms>9 || this.form1.halls>9 || this.form1.kitchens>9 || this.form1.toilets>9 ||
                this.form1.rooms< 0 || this.form1.halls<0 || this.form1.kitchens<0 || this.form1.toilets<0){
                    callback(new Error('请输入0-9之间的数字'));
            }
            // callback(new Error('请输入室厅厨卫数量'));
            callback();
        },
        submit(res){
           this.$refs.form1.validate(valid => {
                if (valid) {
                     this.$emit('saveEditHouse',this.form1);
                }
            });
        },
        unset(){
            this.$emit('hideEditHouse');
        },
        init(data){
            this.form1={
                roomId:data.roomId,
                roomName:data.roomName,
                rooms:data.rooms,
                halls:data.halls,
                kitchens:data.kitchens,
                toilets:data.toilets,
                area:data.area,
                orientation:data.orientation,
                decoration:data.decoration,
            }
        }
    },
    created(){
       
    }
}
