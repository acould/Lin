<template>
  <div class="help-container">
    <div class="banner">
      <p>把您的疑问都交给我们</p>
    </div>
    <div class="row">
      <div class="question-wrap">
        <div class="question">
          <p class="ask">1.什么是车队助手？</p>
          <div class="answer">
            车队助手是一款“智能、好用”的车队管理软件。其主要功能是帮助车队长更好的管理、调度车队的车辆和司机，更准确的记录车队账单，提高工作效率，减少错误。<br>
          </div>
        </div>
        <div class="question">
          <p class="ask">2.如何添加车辆？</p>
          <div class="answer">
            点击“车辆管理”界面右上方绿色加号，进入“添加车辆”填写车辆信息，（类型为自有车辆会添加在“自有车辆”下；若为外援车辆会添加在“外援车辆”下。司机若已添加入“司机列表”则可点击右方人物头像选择；若为新增司机，则直接填写，保存后新增司机会存入“司机列表”。）完成后保存。
          </div>
        </div>
        <div class="question">
          <p class="ask">3.如何修改、删除车辆？</p>
          <div class="answer">
            在“车辆管理”界面“自有车辆/外援车辆”中，找到要修改或删除的车辆，点击进入车辆详情中修改或删除车辆。
          </div>
        </div>
        <div class="question">
          <p class="ask">4.如何添加司机？</p>
          <div class="answer">
            点击左上方人物头像进入“个人中心”，点击“通讯录”，点击“司机”进入“司机列表”。点击右上方加号，进入“添加司机”界面填写司机信息后保存。
          </div>
        </div>
        <div class="question">
          <p class="ask">5.如何修改、删除司机？</p>
          <div class="answer">
            点击左上方人物头像进入“个人中心”，点击“通讯录”，点击“司机”进入“司机列表”。选择要修改或删除的司机，点击进入“司机详情”，“司机详情”中可选择修改或删除。
          </div>
        </div>
        <div class="question">
          <p class="ask">6.如何添加客户？</p>
          <div class="answer">
            点击左上方人物头像进入“个人中心”，点击“通讯录”，点击“客户”进入“客户列表”。点击右上方加号，进入“添加客户”界面填写客户信息后保存。
          </div>
        </div>
        <div class="question">
          <p class="ask">7.如何修改、删除客户？</p>
          <div class="answer">
            点击左上方人物头像进入“个人中心”，点击“通讯录”，点击“客户”进入“客户列表”。选择要修改或删除的客户，点击进入“客户详情”，“客户详情”中可选择修改或删除。
          </div>
        </div>
        <div class="question">
          <p class="ask">8.如何新增任务？</p>
          <div class="answer">
            在“调度”界面，点击左上方绿色加号，或点击车辆绿色空闲时间（点击黄色日期会出现车辆当日时间表，可酌情添加任务。）进入“新增任务”，填完信息（车辆可多选。司机、客户若已添加入“通讯录”，则可通过右方人物头像进入列表选择；若为新增司机、客户，则直接填写，保存后新司机、增客户会存入“通讯录”。）点击“发布”，选择发布方式后完成。新任务会加入“账单”列表下。
          </div>
        </div>
        <div class="question">
          <p class="ask">9.如何查看车辆任务调度？</p>
          <div class="answer">
            点击该车辆红色已排任务日期或点击“查看更多”，进入“查看更多”页面，可查看该车辆的任务安排
          </div>
        </div>
        <div class="question">
          <p class="ask">10.如何修改、删除任务？</p>
          <div class="answer">
            1.在“调度”下“排班”中找到任务车辆，点击“车牌号”进入该车任务列表，选择要修改或删除的任务，点击进入订单详情，进行修改或删除。<br> 2.在“调度”下“排班”中找到任务车辆，进入“查看更多”，选择任务执行时间，找到要修改或删除的任务，点击进入订单详情，进行修改或删除。
            <br> 3.在“调度”下“任务”中找到要修改或删除的任务，点击进入订单详情，进行修改或删除。
            <br> 4.在“账单”下找到用车单位，点击进入“账单详情”（“账单详情”中任务按时间顺序排序），找到要修改或删除的任务，点击进入订单详情，进行修改或删除
          </div>
        </div>
        <div class="question">
          <p class="ask">11.“账单”下“全部”、“已结”、“未结”是如何排序的？</p>
          <div class="answer">
            “全部”按用车单位已结加未结累计总金额，由多到少，从上往下排序；“已结”按用车单位已结累计总金额，由多到少，从上往下排序；“未结”按用车单位未结累计总金额，由多到少，从上往下排序。（“账单详情”中任务按时间顺序排序）
          </div>
        </div>
        <div class="question">
          <p class="ask">12.如何修改价格或确认结款？</p>
          <div class="answer">
            在“账单”下“全部”或“未结”列表中，找到要修改价格或确认结款的用车单位，点击进入“账单详情”（“账单详情”中任务按时间顺序排序）找到要修改价格或确认结款的用车单位，进行修改价格或确认结款（一旦确认结款，就无法在修改或删除任务）。
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>

export default {
  name: 'aboutUs',
  data () {
    return {

    }
  },
  created () {

  },
  methods: {

  }
}
</script>
<style lang ="less" scoped>
@bannerSrc: 'https://img.baochequan.cn/images/2018-07-28/efba1f9b218b9df3fe0b.jpg';
.help-container {
  .row {
    width: 1000px;
    margin: 0 auto;
  }
  .banner {
    height: 474px;
    line-height: 474px;
    text-align: center;
    font-size: 42px;
    color: #fff;
    max-width: 100%;
    background: url(@bannerSrc) no-repeat left top / cover;
  }
  .question-wrap {
    padding-top: 52px;
    padding-bottom: 40px;
    user-select: none;
    .question {
      margin-bottom: 42px;
      .ask {
        margin-bottom: 22px;
        font-size: 18px;
        color: #333;
      }
      .answer {
        padding: 10px 30px;
        line-height: 30px;
        box-sizing: border-box;
        background-color: #f8f8f8;
        font-size: 14px;
        color: #333;
        letter-spacing: 1px;
      }
    }
  }
}
</style>
