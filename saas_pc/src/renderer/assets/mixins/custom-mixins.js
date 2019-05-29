// 用于跳转自定义页面，页面数据的存储与清空
import { mapActions, mapGetters } from 'vuex'
export default {
    computed: {
        ...mapGetters(['getCustomData'])
    },
    methods: {
      ...mapActions(['setCustomData','clearCustomData'])
    }
  }