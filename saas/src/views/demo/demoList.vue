<template>
	<div>
		
		<i-tooltip class="item" effect="dark"   content="phone" placement="top-end">
			<i-button @click="demo">test</i-button>
		</i-tooltip>
		<el-table
			:data="tableData1"
			style="width: 100%">
			<el-table-column type="expand">
			<template scope="props">
				<span>{{ props.row.address }}</span>
			</template>
			</el-table-column>
			<el-table-column
			label="商品 ID"
			prop="id">
			</el-table-column>
			<el-table-column
			label="商品名称"
			prop="name">
			</el-table-column>
			<el-table-column
			label="描述"
			prop="desc">
			</el-table-column>
		</el-table>
		<div class="room-see-phone" :class="{'room-see-phone-show':stime}">13685789550</div>
	</div>
</template>

<style lang="scss" scoped>
	@keyframes showMsg { 0%{opacity:1} 80%,100%{opacity:0} };
	.demo-table-expand {
	/* font-size: 12; */
	}
	.demo-table-expand label {
	width: 90px;
	color: #99a9bf;
	}
	.demo-table-expand .el-form-item {
	margin-right: 0;
	margin-bottom: 0;
	width: 50%;
	}
	.room-see-phone-show{
		opacity: 1 !important;
	}
	.room-see-phone{
	position: relative;
	margin-top: -48px;
	background-color: #000;
	padding: 4px 10px;
	display: block;
	width: 110px;
	opacity: 0;
	// -webkit-animation: showMsg 3s linear 1;
	&:before{
		content: ' ';
		display: block;
		position: absolute;
		left: 67px;
		top: 24px;
		width: 0;
		height: 0;
		overflow: hidden;
		font-size: 0;
		line-height: 0;
		border: 10px;
		border-style: dashed dashed solid dashed;
		z-index: 9;
		border-color: #000 transparent transparent transparent;
	}
	
	
}
</style>

<script>
	import hpmsService from 'service/hpms-house';
	import Promise from 'es6-promise';
    export default {
		data() {
			return {
				tableData1: [
					{
						date: '2016-05-03',
						name: '王小虎',
						address: '上海市普陀区金沙江路 1518 弄',
						age: 17,
						tag: '家'
						}, {
						date: '2016-05-02',
						name: '王小虎',
						address: '上海市普陀区金沙江路 1518 弄',
						age: 18,
						tag: '公司'
					}, {
						date: '2016-05-04',
						name: '王小虎',
						address: '上海市普陀区金沙江路 1518 弄',
						age: 19,
						tag: '家'
					}, {
						date: '2016-05-01',
						name: '王小虎',
						address: '上海市普陀区金沙江路 1518 弄',
						age: 20,
						tag: '公司'
					}, {
						date: '2016-05-08',
						name: '王小虎',
						address: '上海市普陀区金沙江路 1518 弄',
						age: 21,
						tag: '其他'
					}, {
						date: '2016-05-06',
						name: '王小虎',
						address: '上海市普陀区金沙江路 1518 弄',
						age: 22,
						tag: '家'
					}, {
						date: '2016-05-07',
						name: '王小虎',
						address: '上海市普陀区金沙江路 1518 弄',
						age: 23,
						tag: '家'
					}
				],

				tableData5: [
					{
						id: '12987122',
						name: '好滋好味鸡蛋仔',
						category: '江浙小吃、小吃零食',
						desc: '荷兰优质淡奶，奶香浓而不腻',
						address: '上海市普陀区真北路',
						shop: '王小虎夫妻店',
						shopId: '10333'
					}, {
						id: '12987123',
						name: '好滋好味鸡蛋仔',
						category: '江浙小吃、小吃零食',
						desc: '荷兰优质淡奶，奶香浓而不腻',
						address: '上海市普陀区真北路',
						shop: '王小虎夫妻店',
						shopId: '10333'
					}, {
						id: '12987125',
						name: '好滋好味鸡蛋仔',
						category: '江浙小吃、小吃零食',
						desc: '荷兰优质淡奶，奶香浓而不腻',
						address: '上海市普陀区真北路',
						shop: '王小虎夫妻店',
						shopId: '10333'
					}, {
						id: '12987126',
						name: '好滋好味鸡蛋仔',
						category: '江浙小吃、小吃零食',
						desc: '荷兰优质淡奶，奶香浓而不腻',
						address: '上海市普陀区真北路',
						shop: '王小虎夫妻店',
						shopId: '10333'
					}
				],
				phone:13123131,
				stime:false,
			}
		},
		methods:{
			demo(){
				console.log('demo')
				this.stime=true;
				var interv=setInterval(()=>{
					console.log('321312');
					this.stime=false;
					clearInterval(interv)
				},3000)
				
				
				// this.$message.error({message:'请求失败,请重试',duration:1000,onClose:function(){
				// 	location.reload()
				// }});
			},
		}
		
    }
</script>