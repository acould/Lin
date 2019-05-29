<template>
  <div class="contacts-origin">  
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'contacts'
}
</script>
<style lang ="less" scoped>
.contacts-origin {
  width: 100%;
  min-height: 100%;
}
</style>
