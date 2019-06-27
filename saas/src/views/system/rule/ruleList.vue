<template>
    <div id="rule">
        <div class="left-wrap">
            <div class="left-wrap-header">
                <span>业务模块</span>
            </div>
             <ul class="dict-list">
                <li class="dict-item" :class="{'active': current == item.dictKey}" v-for="item in dict['BusinessMoudle']" :key="item.dictKey" @click.stop="getBusinessRule(item)">{{item.dictValue}}</li>
            </ul>
        </div> 
        <div class="right-wrap">
            <div class="right-wrap-header">
                <span>房态管理</span>
            </div>
            <div class="right-wrap-body">
                <ul>
                    <li class="rule-item" v-for="(item,index) in ruleList" :key="index" >
                        <label class="label">{{item.ruleTypeCn}}</label>
                        <div class="content" v-if="item.inputType == 'RADIO'">
                            <i-radio-group v-model="item.ruleValue">
                                <i-radio v-for="(dictItem, dictIndex) in dict[item.ruleDict]" :key="dictIndex" :label="dictItem.dictKey">{{dictItem.dictValue}}</i-radio>
                            </i-radio-group>
                        </div>
                        <div class="content" v-if="item.inputType == 'INPUT'">
                            <i-input v-model="item.ruleValue"></i-input>
                            <span>{{item.unit}}</span>
                        </div>
                        <div class="content" v-if="item.inputType == 'CHECKBOX'">
                            <i-checkbox-group v-model="item.ruleValue">
                                <i-checkbox v-for="(dictItem, dictIndex) in dict[item.ruleDict]" :key="dictIndex" :label="dictItem.dictKey">{{dictItem.dictValue}}</i-checkbox>
                            </i-checkbox-group>
                        </div>
                    </li>
                </ul>

                <div class="operate-bar" v-if="ruleList.length > 0">
                    <i-button type="primary" @click.stop="save">保存</i-button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import {mapState, mapMutations} from 'vuex';
import ruleService from 'service/rule';

export default {
    data(){
        return {
            current:'',
            ruleList:[]
        }
    },
    methods:{
        ...mapMutations(['LOADING']),
        getBusinessRule(item){
            this.LOADING(true);
            this.current = item.dictKey;
            ruleService.getBusinessRule(item.dictKey).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    res.data.forEach(item => {
                        if (item.inputType == 'CHECKBOX') {
                            item.ruleValue = item.ruleValue.split(',');
                        }
                    });
                    this.$set(this, 'ruleList', res.data);
                }
            });
        },
        save(){
            this.LOADING(true);
            let params = JSON.parse(JSON.stringify(this.ruleList));

            params.forEach(item => {
                if (item.inputType == 'CHECKBOX') {
                    item.ruleValue = item.ruleValue.join(',');
                }
            });

            ruleService.saveBusinessRule({ruleList:params}).then(res => {
                this.LOADING(false);
                if (res.code === 0) {
                    this.$message.success('保存成功');
                }
            });
        }
    },
    computed:{
        ...mapState(['dict'])
    }
}
</script>
<style lang="scss">
#rule{
    padding:8px;

    .left-wrap{
        width: 260px;
        height: 100%;
        border: 1px solid #e9e9e9;
        
        .left-wrap-header{
            line-height: 48px;
            border-bottom: 1px solid #e9e9e9;
            padding:0 16px;
            font-size: 16px;
            font-weight: bold;
        }

        .dict-list{
            padding: 16px;
        }

        .dict-item{
            height: 40px;
            width: 100%;
            line-height: 40px;
            text-align: center;
            color:#1f2d3d;
            border:1px solid #eee;
            border-radius: 4px;
            margin-bottom: 16px;
            cursor: pointer;

            &.active{
                border-color:#a3d0fd;
                background: #e6f1fc;
                color:#329dff;
            }
        }
    }

    .right-wrap{
        position: absolute;
        right:8px;
        top:8px;
        width: calc(100% - 284px);
        height: calc(100% - 16px);
        border: 1px solid #e9e9e9;

        .right-wrap-header{
            line-height: 48px;
            border-bottom: 1px solid #e9e9e9;
            padding:0 16px;
            font-size: 16px;
            font-weight: bold;
        }

        .right-wrap-body{
            padding:20px;
        }

        .rule-item{
            overflow: hidden;
            margin-bottom:20px;
            line-height: 40px;
            .label {
                float: left;
                margin-right: 10px;
                color:#333;
                font-weight: 500;
            }

            .content{
                float: left;
                color:#000;
                font-weight: 500;
                .el-input{
                    float: left;
                    margin-right:5px;
                }
            }
        }
        
        .operate-bar{
            width: 100%;
            position: absolute;
            left: 0;
            bottom: 0;
            text-align: right;
            padding:10px;
        }
    }
}
</style>
