let name = null
export default function tagRulesMethod(typeId, tagName, isRequired) {
  let rule = null
  name = tagName
  // 类型   
  // 1 文本
  // 2 数字
  // 3 日期
  // 4 时间
  // 5 日期和时间
  // 6 图片
  // 7 列表
  // 8 多选
  switch (typeId) {
    case 1:
      rule = [{
        required: true,
        message: `${tagName}不能为空`,
        trigger: ['blur', 'change']
      }]
      break;
    case 2:
      rule = [{
        required: true,
        validator: verifyNumber,
        trigger: ['blur', 'change']
      }]
      if (isRequired){
        rule.unshift({
          required: true,
          validator: verifyNull,
          trigger: ['blur', 'change']
        })
      }
      
      break;
    case 3:
      rule = [{
        required: true,
        message: `${tagName}不能为空`,
        trigger: ['blur', 'change']
      }]
      break;
    case 4:
      rule = [{
        required: true,
        message: `${tagName}不能为空`,
        trigger: ['blur', 'change']
      }]
      break;
    case 5:
      rule = [{
        required: true,
        message: `${tagName}不能为空`,
        trigger: ['blur', 'change']
      }]
      break;
    case 6:
      rule = [{
        required: true,
        message: `请上传图片`,
        trigger: ['blur', 'change']
      }]
      break;
    case 7:
      rule = [{
        required: true,
        // validator: verifyNull,
        message: `${tagName}不能为空`,
        trigger: ['blur', 'change']
      }]
      break;
    case 8:
      rule = [{
        required: true,
        message: `${tagName}不能为空`,
        trigger: ['blur', 'change']
      }]
      break;
  }
  return rule
}

function verifyNull(rule, value, callback) {
  if (!value) {
    callback(new Error(`${name}不能为空`))
  } else {
    callback()
  }
}

function verifyNumber(rule, value, callback) {
  let reg = /([a-zA-Z]+)|([\u4e00-\u9fa5]+)/g
  if (reg.test(value) && value) {
    callback(new Error('请输入数字'))
  } else {
    callback()
  }
}