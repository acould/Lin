<template>
    <div class="hi-menu-item" 
         @mouseenter="handleMouseenter"
         @mouseleave="handleMouseleave"
         @click="handleClick">
        <i class="hi-menu-item-icon" :class="icon" v-if="icon"></i>
        <span class="hi-menu-item-label">{{label}}</span>
        <div class="hi-menu-children" 
             v-if="children" 
             v-show="showChild">
            <hi-menu-item v-for="(child, index) in children" 
                :id="`${id}-${level + 1}-${index}`"
                :key="`${id}-${level + 1}-${index}`"
                :level="level+1"
                :label="child.label"
                :children="child.children"
                :url="child.url"
                @activated="handleChildClick"></hi-menu-item>
        </div>
    </div>
</template>
<script>
export default {
    name:'hi-menu-item',
    data(){
        return {
            showChild:false,
            timeout:null,

        }
    },
    props:{
        id: String | Number,
        label:String,
        url:String,
        icon:{
            type:String,
            default:''
        },
        level:{
            type:Number,
            default:1
        },
        children:Array
    },
    methods:{
        handleMouseenter(){
            clearTimeout(this.timeout);
            this.timeout = setTimeout(() => {
                this.showChild = true;
            }, 300);
        },
        handleMouseleave(){
            clearTimeout(this.timeout);
            this.timeout = setTimeout(() => {
                this.showChild = false;
            }, 300);
        },
        handleClick(){
            if (this.url) {
                if (!this.$router) {
                    console.error('缺少vue-router');
                    return;
                }
                this.$router.push(this.url);
                this.$emit('activated',{
                    id:this.id,
                    path: this.url,
                    label: this.label
                },false);
            }
        },
        handleChildClick(menuInfo){
            this.$emit('activated', menuInfo);
        }
    }
}
</script>
<style lang="scss">
    .hi-menu-first-item{
        position: relative;
        width: 100%;
        height: 70px;
        font-size:14px;
        text-align: center;
        color:#fff;
        padding-top:8px;
        margin-bottom: 16px;

        &:hover{
            cursor: pointer;
            background: #329dff;
        }
        .hi-menu-item-icon{
            display: block;
            width: 24px;
            height: 24px;
            margin: 0 20px 8px 20px;
            background-size: cover;
        }

        .hi-menu-children{
            position: absolute;
            margin-left:5px;
            top:0;
            left:100%;
            background: #001629;
            z-index: 9;

            .hi-menu-item{
                white-space: nowrap;
                line-height: 40px;
                padding: 0 20px;
                text-align: left;
                position: relative;

                &:hover{
                    background: #329dff;
                }
            }
        }
    }

</style>

