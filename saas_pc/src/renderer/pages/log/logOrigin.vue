<template>
  <div class="log-origin">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'logOrigin'
}
</script>
<style lang ="less" scoped>
.log-origin {
  background-color: #f5f5f5;
  &>div {
    background-color: #f5f5f5;
    overflow-x: hidden;
  }
}
</style>
