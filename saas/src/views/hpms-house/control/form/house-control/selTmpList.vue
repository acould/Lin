<template>
    <div class="sel-tmplist-box">
        <!-- <i-form-item label="模板名称" > -->
         <i-form :model="formData" ref="passwordForm" label-width="100px">
            <i-form-item label="模板名称:" :rules="[{required:true, message:'请选择模板', trigger:'blur'}]">
                <i-radio-group v-model="formData.index" size="small">
                    <i-radio v-for="item in tmpList" :key="item.value" :label="item.value" >{{item.label}}</i-radio>
                </i-radio-group>
            </i-form-item>
         </i-form>
        <div class="button-box">
            <div  style="float: right;height: 50px;">
                <i-button @click.stop="unset()">取消</i-button>
                <i-button @click.stop="submit()" type="primary" >提交</i-button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return {
            labelWidth:'95px',
            formData:{
                index:0,
            },
            tmpList:[],
            
        }
    },
    mounted(){
    },
    methods: {
        init(data,index){
            console.log('selt',data);
            this.tmpList=data
        },
        unset(){
            this.$emit('closeDialog');
        },
        submit(){
            this.$emit('submit',this.formData.index);
        }
       
    }
}
</script>

