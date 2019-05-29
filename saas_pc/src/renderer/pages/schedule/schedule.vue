<template>
  <div class="schedule-origin">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'schedule'
}
</script>
<style lang ="less" scoped>
.schedule-origin {
  width: 100%;
  min-height: 100%;
}
</style>