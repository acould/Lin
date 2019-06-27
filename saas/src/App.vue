<template>
    <div class="app">
        <router-view></router-view>
    </div>
</template>

<script>
export default {name: 'app'};
</script>

<style>
.app {
    width: 100%;
    height: 100%;
}
.el-col label{
    color:#5e6d82;
    min-width: 60px;
    display: inline-block;
}
.el-col span{
    color:#475669;
    font-weight: 450;
}
</style>