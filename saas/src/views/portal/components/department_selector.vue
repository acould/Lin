<template>
    <i-cascader
        ref="cascader"
        :options="options"
        class="department_selector"
        :class="{'active': cascaderActive}"
        :is-basic="false"
        placeholder="选择城市与公司"
        clearable
        @change="handleChangeDepartment">
    </i-cascader>
</template>

<script>
export default {
    name: 'department-selector',

    props: {options: Array},

    data(){
        return {cascaderActive: false};
    },

    methods: {
        handleChangeDepartment(val){
            this.$emit('change', val);
        }
    },

    mounted(){
        // 实现城市部门级联选择器点击或悬浮效果
        this.$refs.cascader.$el.addEventListener('click', (e) => {
            if (!this.cascaderActive) {
                if (this.$refs.cascader.$el.children[0].children[2] === e.target) {
                    this.cascaderActive = false;
                    return;
                }
            }
            this.cascaderActive = !this.cascaderActive;
        }, true);
        document.addEventListener('click', (e) => {
            this.cascaderActive = false;
        });
    }
};
</script>

<style lang="scss">
.department_selector {
    &.active {
        .cascader {
            border: 1px solid #14161A;
            background: #14161A;
            &:hover {
                border: 1px solid #14161A;
                background: #14161A;
            }
        }
    }
    &.cascader-container .cascader {
        width:120px;
        height:24px;
        vertical-align: middle;
        background: transparent;
        border-radius: 2px;
        border:none;
        font-size:12px;
        color:#fff;
        border: 1px solid #364357;

        &:hover {
            border: 1px solid #364357;
            background: #313D4F;
        }
        &.focus {
            border: 1px solid #14161A;
            background: #14161A;
        }
        .result-label{
            line-height:24px;
            width: calc(100% - 35px);
        }
        input {
            padding-right:25px;
        }
        .icon-arrow{
            font-size: 9px;
            line-height:24px;
            width:30px;
            transform: scale(0.75);
            &.is-reverse {
                transform: scale(0.75) rotateZ(-180deg);
            }
        }
        .close-btn{
            font-size: 9px;
            line-height:24px;
            transform: scale(0.75);
            width:30px;
        }
        input::-webkit-input-placeholder,
        textarea::-webkit-input-placeholder {
            color:#fff;
            opacity:1;
        }
    　　input:-moz-placeholder,
        textarea:-moz-placeholder {
            color:#fff;
            opacity:1;
        }
    　　input::-moz-placeholder,
        textarea::-moz-placeholder {
            color:#fff;
            opacity:1;
        }
        input:-ms-input-placeholder,
        textarea:-ms-input-placeholder {
            color:#fff;
            opacity:1;
        }
    }
    &.cascader-container .cascader-menu-container,
    .filterable-menu {
        top:29px;
        height: 150px;
        border: 1px solid #CFDBE6;
        box-shadow: 0 6px 15px 0 rgba(0,0,0,0.30);
    }
    .cascader-menu-container .cascader-menu,
    .filterable-menu {
        min-width:80px;
        height:150px;
        padding:0;
    }
    .cascader-menu-container .cascader-menu {
        border-right: 1px solid #CFDBE6;
        &:last-child {
            border:0;
        }
    }
    .cascader-menu-container .cascader-menu .cascader-menu-item {
        color: #475669;
        font-size:12px;
        height:26px;
        line-height:26px;
        padding-top:0;
        padding-bottom:0;
        margin-bottom:5px;

        &:last-child {
            margin-bottom:0;
        }
        &.active {
            color:#fff;
            background: #00A1FF;
            .icon-extensible{
                color:#fff;
            }
        }
        &:hover {
            background: #008ADB;
            color:#fff;
            .icon-extensible{
                color:#fff;
            }
        }
        .icon-extensible {
            font-size: 9px;
            transform: scale(0.75) rotateZ(90deg);
            top:7px;
            color:#D3DCE6;
        }
    }
}
</style>
