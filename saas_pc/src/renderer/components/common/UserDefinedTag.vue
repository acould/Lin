<template>
  <div class="userdefined-container inline">
    <div class="userdefined-form inline">
      <!-- 文本 -->
      <el-form-item :rules="tagRules" :prop="propName" v-if="typeId === 1" :label="tagName">
        <el-input type="text" v-model.trim="tagValue" :placeholder="'请输入'+tagName "></el-input>
      </el-form-item>
      <!-- 数字 -->
      <el-form-item
        :rules="tagRules"
        :class="isRequired ? '' : 'cancel-remark'"
        :prop="propName"
        v-if="typeId === 2"
        :label="tagName"
      >
        <el-input type="text" v-model.trim="tagValue" :placeholder="'请输入'+tagName "></el-input>
      </el-form-item>
      <!-- 日期 -->
      <el-form-item :rules="tagRules" :prop="propName" v-if="typeId === 3" :label="tagName">
        <el-date-picker v-model="tagValue" type="date" placeholder="请选择日期"  value-format="yyyy-MM-dd"></el-date-picker>
      </el-form-item>
      <!-- 时分 -->
      <el-form-item :rules="tagRules" :prop="propName" v-if="typeId === 4" :label="tagName">
        <el-input
          maxlength="5"
          @input="addColon"
          @blur="formatClock"
          v-model="tagValue"
          placeholder="请输入时间"
        ></el-input>
      </el-form-item>
      <el-form-item :rules="tagRules" :prop="propName" v-if="typeId === 5" :label="tagName">
        <el-date-picker
          popper-class="user-defined-datetime"
          v-model="tagValue"
          type="datetime"
          placeholder="请选择日期"
          value-format="yyyy-MM-dd HH:mm"
        ></el-date-picker>
      </el-form-item>
      <!-- 图片 -->
      <el-form-item :rules="tagRules" :prop="propName" v-if="typeId === 6" :label="tagName">
        <div class="upload-wrap">
          <el-upload
            class="avatar-uploader"
            :http-request="uploadImage"
            :action="''"
            :multiple="true"
            :show-file-list="false"
          >
            <div class="upload-btn">
              <span class="iconfont icon-close iconfont-close"></span>
            </div>
          </el-upload>
          <div class="upload-image" v-for="(src, index) in tagValue" :key="index">
            <span class="iconfont icon-shanchu1 iconfont-close" @click="removeUploadImage(2,index)"></span>
            <img @click="look(src)" :src="src" alt>
          </div>
        </div>
      </el-form-item>
      <!-- 下拉框 -->
      <el-form-item :rules="tagRules" :prop="propName" v-if="typeId === 7" :label="tagName">
        <el-select v-model="tagValue" placeholder="请选择">
          <el-option v-for="(item, index) in content" :key="index" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <!-- 多选框 -->
      <el-form-item :rules="tagRules" :prop="propName" v-if="typeId === 8" :label="tagName">
        <el-checkbox-group v-model="tagValue">
          <el-checkbox v-for="item in content" :label="item" :key="item">{{item}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
    </div>
    <imgDialog ref="imgDialog" :imgShow.sync="imgShow"></imgDialog>
  </div>
</template>
<script>
import imgDialog from '@/components/imgDialog'
import tagRulesMethod from '@/assets/js/tag.js';
export default {
  props: {
    //标签类
    typeId: {
      type: Number,
      required: true,
      value: null
    },
    propName: {
      type: [String, Number],
      required: true,
      value: null
    },
    // 标签名字
    tagName: {
      type: String,
      // required: true,
      value: '测试'
    },
    // 是否必填
    isRequired: {
      type: Number,
      // required: true,
      value: 0
    },
    content: {
      type: [String, Array],
      value: []
    },
    propVal: {
      type: [String, Array, Number],
      value: null
    }
  },
  data () {
    return {
      tagValue: null,
      tagRules: null,
      isUploading: false,// 是否正在上传
      imageLen: 0,
      imgShow: false,
    }
  },
  components: {
    imgDialog
  },
  watch: {
    // 组件标签的值
    tagValue (newVal) {
      // 多选框的数组值转化为以逗号隔开的字符串
      if (this.typeId === 6 || this.typeId === 8) {
        if (Array.isArray(newVal)) {
          newVal = newVal.join(',')
        }
      }

      this.$emit('update:tagValue', newVal)
    },
    // 获取订单修改时传入组件的标签的值
    propVal: {
      handler (newVal) {
        let adjustVal = newVal
        if (adjustVal) {
          if (this.typeId === 6 || this.typeId === 8) {
            adjustVal = adjustVal ? adjustVal.split(',') : []
            if (this.typeId === 6) this.imageLen = adjustVal.length
          }
          this.tagValue = adjustVal
        }
      },
      immediate: true
    }
  },
  created () {

    // 获取相应类型的验证规则
    if (this.typeId === 6 || this.typeId === 8) {
      if (!this.tagValue)  this.tagValue = []
    }
    // 引入验证规则
    if (this.isRequired || this.typeId === 2) {
      this.tagRules = tagRulesMethod(this.typeId, this.tagName, this.isRequired)
    }

  },
  mounted () {

  },
  methods: {
    // 格式时间
    formatClock () {
      let reg = /^(([0-1]?[0-9])|([2]?[0-3]))(:?)[0-5]([0-9])$/
      if (!reg.test(this.tagValue)) {
        this.tagValue = null
      }
    },
    // 上传图片
    uploadImage (r) {
      if (this.imageLen >= 3) {
        this.$message({
          message: '图片最多上传3张',
          type: 'error'
        })
        return
      }
      this.imageLen++
      this.isUploading = true
      let formData = new FormData()
      let client;
      if (process.env.IS_WEB) {
        client = 'saas_pc'
      } else {
        client = 'saas_windows'
      }
      formData.append('files', r.file)
      this.$axios.defaults.headers['Content-Type'] = 'multipart/form-data'
      let loading = this.$loading({ text: 'Loading' })
      this.$axios.post(this.$httpUrl.uploadImage, formData).then(res => {
        loading.close()
        if (res) {
          this.isUploading = false
          this.tagValue.push(res.files)
        }
      })
    },
    // 删除上传图片
    removeUploadImage (type, imgIndex) {
      this.imageLen--
      this.tagValue.splice(imgIndex, 1)
    },
    // 预览
    look (img) {
      this.imgShow = true
      this.$nextTick(() => {
        this.$refs.imgDialog.showToggle(img)
      })
    },
    // 增加冒号
    addColon () {
      let newVal = this.tagValue
      let reg1 = /^(([0-1][0-9])|([2][0-3]))$/
      if (reg1.test(newVal)) {
        this.tagValue = newVal + ':'
      }
    }
  }
}
</script>

<style lang="less" scoped>
@imgWidth: 33px;
@imgHeight: 33px;
.userdefined-container {
  .inline {
    display: inline-block;
  }
  .userdefined-form {
    .el-form-item {
      margin-bottom: 12px;
    }
  }
  .upload-wrap {
    min-width: 175px;
    .upload-btn {
      width: @imgWidth;
      height: @imgHeight;
      line-height: @imgHeight;
      text-align: center;
      font-size: @fontsize-medium;
      color: @color-light-grey;
      background-color: #fff;
      border: 1px dashed #dadada;
      box-sizing: border-box;
      .iconfont-close {
        transform: rotate(45deg);
        display: inline-block;
      }
    }
    .avatar-uploader {
      height: @imgHeight;
      line-height: @imgHeight;
      margin-right: 12px;
      display: inline-block;
      vertical-align: middle;
    }
    .upload-image {
      display: inline-block;
      vertical-align: middle;
      height: @imgHeight;
      line-height: @imgHeight;
      position: relative;
      img {
        width: @imgWidth;
        height: @imgHeight;
        margin-right: 12px;
        cursor: pointer;
      }
      .iconfont-close {
        position: absolute;
        top: -23px;
        right: -2px;
        font-size: 16px;
        color: #ff7200;
        font-weight: bold;
        padding: 5px;
        cursor: pointer;
      }
    }
  }
}
</style>
<style lang="less">
.userdefined-container {
  .user-defined-datetime {
    .el-button--mini:not(.is-plain) {
      display: none;
    }
    .has-seconds {
      .el-time-spinner__wrapper {
        &:last-child {
          display: none;
        }
      }
    }
  }
  .cancel-remark {
    color: red;
    &.is-required {
      .el-form-item__label::before {
        content: "";
      }
    }
  }
}
</style>