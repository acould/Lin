import config from 'config';

const NotFound = () => import('views/errors/Not_found');
const ServerError = () => import('views/errors/Server_error');

const Dashboard = () => import('views/Dashboard.vue');

const Layout = () => import('../layout/layout.vue');

const Login = () => import('views/login/login.vue');
const HpmsHouse = () => import('views/hpms-house/index');
const demo = () => import('views/demo/demoList.vue');
const findPassword = () => import('views/login/findPassword.vue');

//system
const dictList = () => import('views/system/dict/dictList.vue');
const ruleList = () => import('views/system/rule/ruleList.vue');
const rightList = () => import('views/system/right/rightList.vue');
const noticeList = () => import('views/system/notice/noticeList.vue');
const templateList = () => import('views/system/template/templateList.vue');

//house
const dailyRentalState = () => import('views/house/houseState/dailyRentalState.vue');

//contract
const contractMonthList = () => import('views/contract/monthList.vue');
const contractDayList = () => import('views/contract/dayList.vue');


let routes = [
    { path: '/', redirect: '/login'},
    { path: '/login', component: Login},
    {path: '/demo', component: demo},
    { path: '/find/password', component:findPassword },
    { path:'/portal', component:Layout,
        children:[
            {path:'/dashboard', component:Dashboard},
            {path:'/system/dict', component:dictList},
            {path:'/system/rule', component:ruleList},
            {path:'/system/right', component:rightList},
            {path:'/system/notice', component:noticeList},
            {path:'/system/template', component:templateList},
            {path: '/hpmshouse', component: HpmsHouse},
            
            {path:'/house/daily/state', component:dailyRentalState},
            {path:'/contract/month', component:contractMonthList},
            {path:'/contract/day', component:contractDayList},
            {path:'/404', component: NotFound },
        ] 
    }
    // { path: '/', redirect: `/${config.root}/dashboard` },
    // { path: `/${config.root}`,  component: Portal, 
    //   children:[
    //       { path: 'dashboard', component: Dashboard },
    //       { path: '404', component: NotFound },
    //       { path: '500', component: ServerError },
    //   ]
    // },
    // { path: '*', redirect: { path:`/${config.root}/404` } }
];

export default routes;
