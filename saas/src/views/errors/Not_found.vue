<template>
    <div class="not-found">
        <div class="slogan">
            <img src="/static/404.svg" alt="">
        </div>
    </div>
</template>

<script>
export default {name: 'not-found'};
</script>

<style lang="scss">
.not-found {
   img{
       height: 300px;
   }
   .slogan{
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        text-align: center;
   }
}
</style>