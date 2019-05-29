import Vue from 'vue'
import App from './App'
// import router from './router'
import store from './store'
import axios from './Axios/axios.js'
import httpUrl from './api/api.js'
import util from '@/assets/js/util.js'
import locationId from '@/assets/js/customConstants.js'
import camelCase from 'lodash/camelCase'
// element-ui
import {
  Loading,
  Pagination,
  MessageBox,
  Message,
  Notification,
  Icon,
  Button,
  Breadcrumb,
  BreadcrumbItem,
  Input,
  Radio,
  RadioGroup,
  RadioButton,
  Checkbox,
  CheckboxGroup,
  Rate,
  DatePicker,
  TimePicker,
  TimeSelect,
  Table,
  TableColumn,
  Dialog,
  Select,
  Option,
  Upload,
  Tabs,
  TabPane,
  Form,
  Autocomplete,
  Popover,
  Badge,
  FormItem,
  Dropdown,
  DropdownMenu,
  DropdownItem,

} from 'element-ui'
import VueAMap from "vue-amap";
Vue.use(VueAMap);
// 初始化vue-amap
VueAMap.initAMapApiLoader({
  // 申请的高德key
  key: "ef55c9edf22fd04ec2fa724fb07a18d2",
  // 插件集合
  plugin: [],
  v:'1.4.4',
  uiVersion: '1.0.11'
});
Vue.prototype.$VueAMap = VueAMap
Vue.prototype.$loading = Loading.service
Vue.prototype.$msgbox = MessageBox
Vue.prototype.$alert = MessageBox.alert
Vue.prototype.$confirm = MessageBox.confirm
Vue.prototype.$prompt = MessageBox.prompt
Vue.prototype.$notify = Notification
Vue.prototype.$message = Message
let pluginArr = [
  Icon,
  TimePicker,
  TimeSelect,
  Pagination,
  Button,
  Breadcrumb,
  BreadcrumbItem,
  Select,
  Option,
  Input,
  Radio,
  RadioButton,
  Checkbox,
  CheckboxGroup,
  Rate,
  DatePicker,
  Table,
  TableColumn,
  Dialog,
  Tabs,
  TabPane,
  RadioGroup,
  Upload,
  Autocomplete,
  Popover,
  Badge,
  Form,
  FormItem,
  Dropdown,
  DropdownMenu,
  DropdownItem,

]
pluginArr.forEach(function (item) {
  Vue.use(item)
})

Vue.prototype.$util = util
Vue.prototype.$httpUrl = httpUrl
Vue.prototype.$axios = axios
Vue.prototype.$locationId = locationId
Vue.config.productionTip = false

// 注册全局组件
const requireComponent = require.context(
  // 其组件目录的相对路径
  './components/common',
  // 是否查询其子目录
  false,
  // 匹配基础组件文件名的正则表达式
  /[A-Za-z]\w+\.(vue|js)$/
)

requireComponent.keys().forEach(fileName => {
  // 获取组件配置
  const componentConfig = requireComponent(fileName)
  const componentName = camelCase(
    // 剥去文件名开头的 `'./` 和结尾的扩展名
    fileName.replace(/^\.\/(.*)\.\w+$/, '$1')
  )
  // 全局注册组件
  Vue.component(
    componentName,
    // 如果这个组件选项是通过 `export default` 导出的，
    // 那么就会优先使用 `.default`，
    // 否则回退到使用模块的根。
    componentConfig.default || componentConfig
  )
})
let router, promise
if (!process.env.IS_WEB) {
  promise = new Promise((resolve, reject) => {
    import('./router').then(module => {
      router = module.default
      resolve()
    })
  })
  import('../main/ipcRenderer').then(module => {
    Vue.prototype.$windowUtil = module.default
  })
  Vue.use(require('vue-electron'))
} else {
  promise = new Promise((resolve, reject) => {
    import('./webRouter').then(module => {
      router = module.default
      resolve()
    })
  })
}

promise.then(() => {
  new Vue({
    components: {
      App
    },
    router,
    store,

    template: '<App/>'
  }).$mount('#app')
})

/* eslint-disable no-new */