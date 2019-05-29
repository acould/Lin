// 获取当前时间
function formatTime(dt) {
  var date = dt ? new Date(dt) : new Date()
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()
  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()
  return {
    yM: [year, month].map(formatNumber).join('-'),
    date: [year, month, day].map(formatNumber).join('-'),
    time: [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, second].map(formatNumber).join(':'),
    carTime: [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute].map(formatNumber).join(':')
  }
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}


function compareDate2(d1, d2) {
  // 格式化日期  
  if (!d2) {
    d2 = new Date()
  } else {
    d2 = new Date(d2.replace(/-/g, '/'))
  }
  d1 = new Date(d1.replace(/-/g, '/'))
  if (d1.getTime() <= d2.getTime()) {
    return true
  } else {
    return false
  }
}

function compareDate(d1, d2) {
  // 格式化日期  
  if (!d2) {
    d2 = new Date()
    d2.setMinutes(0)
    d2.setHours(0)
    d2.setSeconds(0)
    d2.setMilliseconds(0)
  } else {
    d2 = new Date(d2.replace(/-/g, '/'))
  }
  d1 = new Date(d1.replace(/-/g, '/'))
  if (d1.getTime() < d2.getTime()) {
    return true
  } else {
    return false
  }
}

// 计算时间天数差
function calcDateDifference (d1, d2) {
  let msTime1 = +new Date(d1.replace(/-/g, '/'))
  let msTime2 = +new Date(d2.replace(/-/g, '/'))
  return (msTime2 - msTime1) / (1000 * 3600 * 24) + 1 
}


function generateArray(d1, d2) {
  let b = compareDate(d1, d2)
  if (!b) {
    return [d1]
  } else {
    let newDate1 = new Date(d1.replace(/-/g, '/')).getTime()
    let newDate2 = new Date(d2.replace(/-/g, '/')).getTime()
    let len = ((newDate2 - newDate1) / 1000 / 86400) - 1
    let arr = []
    for (var i = 1; i <= len; i++) {
      let m = formatTime((newDate1 + i * 86400 * 1000)).date
      arr.push(m)
    }
    let newArr = arr.concat([d1, d2]).sort()
    return newArr
  }
}

function getCPU() {
  var agent = navigator.userAgent.toLowerCase()
  console.log(agent)
  if (agent.indexOf("win64") >= 0 || agent.indexOf("wow64") >= 0) return "x64"; 
  else if (agent.indexOf("Linux") >= 0) return 'Linux'
  else if (agent.indexOf("mac") >= 0) return 'mac'       
  else return 'x86'
}

function hasClass(el, className) {
  let reg = new RegExp('(^|\\s)' + className + '(\\s|$)')
  return reg.test(el.className)
}

function addClass(el, className) {
  if (hasClass(el, className)) {
    return
  }

  let newClass = el.className.split(' ')
  newClass.push(className)
  el.className = newClass.join(' ')
}

function removeClass(el, className) {
  if (!hasClass(el, className)) {
    return
  }

  let reg = new RegExp('(^|\\s)' + className + '(\\s|$)', 'g')
  el.className = el.className.replace(reg, ' ')
}

/** 
 * @desc 函数防抖
 * @param {function} fn 执行的函数
 * @param {Number} wait 延迟执行的毫秒数
 * @param {Boolean} immediate 是否立即执行 
*/
function debounce (fn, wait = 1000, immediate) {
  let timeout 
  return function () {
    let args = arguments;
    let context = this
    if (timeout) clearTimeout(timeout)
    if (immediate) {
      let callNow = !timeout;
      timeout = setTimeout(() => {
          timeout = null;
      }, wait)
      if (callNow) func.apply(context, args)
    } else {
      timeout = setTimeout(() => {
        timeout = null
        fn.apply(context, args)
      }, wait);
    }
   
  }
}

export default {
  formatTime,
  compareDate,
  compareDate2,
  generateArray,
  calcDateDifference,
  getCPU,
  hasClass,
  addClass,
  removeClass,
  debounce
}