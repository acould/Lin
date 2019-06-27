
import tpl from './index.html';
// import './index.scss';

import monthList from './monthList';
import dayList from './dayList';
import '../index.scss';

import hpmsService from 'service/hpms-house';
import { mapState,mapMutations } from 'vuex';



export default {
    name: 'hpmsHouseBill',
    template: tpl,
    props:{
        id:String,
    },
   
    data () {
        return {
			// id:'',
            activeName:'monthList',
            onlyEffective:true,
            params:{
                roomId:'',
                onlyEffective:'Y',
            }
        }
    },
    components:{
        'monthList':monthList,
        'dayList':dayList,
    },
    mounted(){
        //this.init();
    },
    watch: {
        onlyEffective(val){
            this.cacheParams();
        },
        activeName(val){
            this.cacheParams();
        }

    },
    
    methods: {
        ...mapMutations(['LOADING']),
        init(){
			this.params.roomId=this.id;
			this.cacheParams();
			this.activeName='monthList'
        },
        cacheParams(){
            this.params.onlyEffective= this.onlyEffective ?'Y':'N';
            if(this.activeName=='monthList'){
                this.$refs['monthList'].init(this.params);
            }else{
                this.$refs['dayList'].init(this.params);
            }
        }
        
    }
}
