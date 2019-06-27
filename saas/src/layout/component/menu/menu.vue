<template>
    <div class="hi-menu">
        <hi-menu-item v-for="(menu, index) in menus"
            class="hi-menu-first-item"
            :key="`menu-${index}`"
            :id="`menu-${index}`"
            :level="1"
            :label="menu.label"
            :url="menu.url"
            :icon="menu.icon"
            :children="menu.children"
            @activated="handleClick"></hi-menu-item>
    </div>
</template>
<script>
import HiMenuItem from './menu-item.vue';
export default {
    name:'hi-menu',
    components:{HiMenuItem},
    props:{
        menus:{
            required:true,
            type:Array
        }
    },
    methods:{
        handleClick(menuItem){
            console.log('menu click')
            this.$emit('activated', menuItem);
        }
    }
}
</script>
