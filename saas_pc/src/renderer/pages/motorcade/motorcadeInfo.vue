<template>
  <div class="motorcade">
    <div class="main-header">
      <h3 class="single-tt">首页管理</h3>
    </div>
    <div class="sec-container">
      <div class="sec-line">
        <p class="lb">
          <span class="mark">*</span>车队名称
        </p>
        <div class="sec-con">
          <el-input v-model="infoParams.display_title" placeholder="请输入车队名称" class="inp"></el-input>
        </div>
      </div>
      <div class="sec-line">
        <p class="lb">
          <span class="mark">*</span>联系方式
        </p>
        <div class="sec-con">
          <el-input v-model.trim="infoParams.contact_phone" placeholder="请输入联系方式" class="inp"></el-input>
        </div>
      </div>
      <div class="sec-line">
        <p class="lb">
          <span class="mark">*</span>车辆展示
        </p>
        <div class="sec-con">
          <div class="upload-wrap">
            <el-upload
              class="avatar-uploader"
              :http-request="uploadImage"
              :action="''"
              :multiple="true"
              :show-file-list="false"
            >
              <div class="upload-btn" @click="seluploadType(1)">
                <span class="iconfont icon-close iconfont-close"></span>
                <span class="upload-sign">添加车辆照片</span>
              </div>
            </el-upload>
            <div class="upload-image" v-for="(src, index) in infoParams.car_images" :key="index">
              <span
                class="iconfont icon-htmal5icon19 iconfont-close"
                @click="removeUploadImage(1,index)"
              ></span>
              <img @click="look(src)" :src="src" alt>
            </div>
          </div>
        </div>
      </div>
      <div class="sec-line">
        <p class="lb">
          <span class="mark">*</span>资质证照
        </p>
        <div class="sec-con">
          <div class="upload-wrap">
            <el-upload
              class="avatar-uploader"
              :http-request="uploadImage"
              :action="''"
              :multiple="true"
              :show-file-list="false"
            >
              <div class="upload-btn" @click="seluploadType(2)">
                <span class="iconfont icon-close iconfont-close"></span>
                <span class="upload-sign">添加证件照片</span>
              </div>
            </el-upload>
            <div
              class="upload-image"
              v-for="(src, index) in infoParams.qualification_images"
              :key="index"
            >
              <span
                class="iconfont icon-htmal5icon19 iconfont-close"
                @click="removeUploadImage(2,index)"
              ></span>
              <img @click="look(src)" :src="src" alt>
            </div>
          </div>
        </div>
      </div>
      <div class="sec-line">
        <p class="lb"></p>
        <div class="sec-con">
          <div class="btn-wrap">
            <a href="javascript:void(0);" class="btn btn-save" @click="Infohandler('save')">保存</a>
            <a href="javascript:void(0);" class="btn btn-preview" @click="Infohandler('preview')">预览</a>
          </div>
        </div>
      </div>
    </div>
    <prevewInfoBox :prevewInfoBoxShow.sync="prevewInfoBoxShow" :previewInfo="previewInfo"></prevewInfoBox>
    <imgDialog ref="imgDialog" :imgShow.sync="imgShow" :imgs="imgs"></imgDialog>
  </div>
</template>
<script>
import prevewInfoBox from "@/components/prevewInfoBox"
import imgDialog from '@/components/imgDialog'
export default {
  name: 'motorcadeInfo',
  data () {
    return {
      previewInfo: {},
      prevewInfoBoxShow: false,
      infoParams: {
        display_title: null,
        contact_phone: null,
        car_images: [],
        qualification_images: []
      },
      uploadType: null, //上传类型 1: 车辆照片 2：资质证照    
      qualificationLen: 0,
      carLen: 0,
      isUploading: false, // 图片是否正在上传
      imgShow: false,
      imgs: ''
    }
  },
  components: {
    prevewInfoBox,
    imgDialog
  },
  created () {
    this.getInfo()
  },
  methods: {
    // 获取车队信息 
    getInfo () {
      this.$axios.get(this.$httpUrl.getMotorcadeInfo).then(res => {
        if (res) {
          let obj = { display_title: res.display_title, contact_phone: res.contact_phone, car_images: res.car_images, qualification_images: res.qualification_images }
          Object.assign(this.infoParams, obj)
          this.qualificationLen = res.qualification_images.length

          this.carLen = res.car_images.length
        }
      })
    },
    // 验证信息
    verifyInfo () {
      let infoParams = this.infoParams
      let varifyStatus = false
      if (!infoParams.display_title) {
        this.$message({
          message: '请输入车队名称',
          type: 'error'
        });
        return varifyStatus
      }
      if (!infoParams.contact_phone) {
        this.$message({
          message: '请输入联系电话',
          type: 'error'
        });
        return varifyStatus
      } else {
        let regTel = /^(0\d{2}-)(\d{7,8})$|^[1]\d{10}$/
        if (!regTel.test(infoParams.contact_phone)) {
          this.$message({
            message: '请输入正确的联系电话',
            type: 'error'
          });
          return varifyStatus
        }
      }
      if (!infoParams.car_images.length) {
        this.$message({
          message: '请添加车辆照片',
          type: 'error'
        });
        return varifyStatus
      }
      if (!infoParams.qualification_images.length) {
        this.$message({
          message: '请添加证件照片',
          type: 'error'
        });
        return varifyStatus
      }
      if (this.isUploading) {
        this.$message({
          message: '请稍等，图片正在上传中',
          type: 'error'
        });
        return varifyStatus
      }
      return true
    },
    Infohandler (type) {
      // type 'save' 保存 'preview' 预览
      let varifyStatus = this.verifyInfo()
      if (!varifyStatus) return
      let loading = this.$loading({ text: 'Loading' })
      this.$axios.post(this.$httpUrl.saveMotorcadeInfo, this.infoParams).then(res => {
        loading.close()
        if (res) {
          if (type === 'save') {
            this.$message({
              message: '保存成功',
              type: 'success'
            });
          } else {
            this.previewInfo = res
            this.prevewInfoBoxShow = true
          }
        }
      })
    },
    seluploadType (type) {
      this.uploadType = type
    },
    removeUploadImage (type, imgIndex) {
      /*
        type:  1: 车辆照片 2：资质证照
        imgIndex: 照片数组中的索引
      */
      if (type === 1) {
        this.infoParams.car_images.splice(imgIndex, 1)
        this.carLen--
      } else {
        this.infoParams.qualification_images.splice(imgIndex, 1)
        this.qualificationLen--
      }

    },
    // 上传图片，换取图片地址
    uploadImage (r) {
      let uploadType = this.uploadType
      if (uploadType == 1) {
        if (this.carLen >= 10) {
          this.$message({
            message: '车辆照片最多上传10张',
            type: 'error'
          })
          return
        }
        this.carLen++
      } else {
        if (this.qualificationLen >= 4) {
          this.$message({
            message: '资质照片凭据最多上传4张',
            type: 'error'
          })
          return
        }
        this.qualificationLen++
      }
      this.isUploading = true
      let formData = new FormData()
      let client;
      if (process.env.IS_WEB) {
        client = 'saas_pc'
      } else {
        client = 'saas_windows'
      }
      formData.append('files', r.file)
      formData.append('client', client)
      this.$axios.defaults.headers['Content-Type'] = 'multipart/form-data'
      let loading = this.$loading({ text: 'Loading' })
      this.$axios.post(this.$httpUrl.uploadImage, formData).then(res => {
        loading.close()
        if (res) {
          this.isUploading = false
          if (uploadType == 1) {
            this.infoParams.car_images.push(res.files)
          } else {
            this.infoParams.qualification_images.push(res.files)
          }
        }
      })
    },
    look (img) {
      this.imgShow = true
      this.$nextTick(() => {
        this.$refs.imgDialog.showToggle(img)
      })
    }
  }
}
</script>
<style lang ="less" scoped>
@imgWidth: 208px;
@imgHeight: 117px;
.motorcade {
  background-color: @bg-grey;
  .sec-container {
    background-color: @bg-grey;
  }
  .sec-line {
    overflow: hidden;
    padding: 10px 0;
    .lb {
      float: left;
      height: 32px;
      line-height: 32px;
      width: 147px;
      text-align: right;
      font-size: @fontsize-medium;
      color: @color-dark-grey;
      box-sizing: border-box;
      padding-right: 10px;
      .mark {
        color: #f56c6c;
        font-size: 16px;
        vertical-align: middle;
        margin: 0 5px;
      }
    }
    .sec-con {
      margin-left: 147px;
      overflow: hidden;
      .inp {
        width: 173px;
        & /deep/ .el-input__inner {
          height: 32px;
          line-height: 32px;
        }
      }
      .avatar-uploader {
        float: left;
        margin-right: 20px;
      }
      .upload-btn {
        width: @imgWidth;
        height: @imgHeight;
        line-height: @imgHeight;
        text-align: center;
        font-size: @fontsize-medium;
        color: @color-light-grey;
        background-color: #fff;
        border: 1px dashed #dadada;
        margin-bottom: 15px;
        .iconfont-close {
          transform: rotate(45deg);
          display: inline-block;
        }
      }
      .upload-image {
        float: left;
        position: relative;
        img {
          width: @imgWidth;
          height: @imgHeight;
          margin-right: 20px;
          margin-bottom: 15px;
        }
        .iconfont-close {
          position: absolute;
          top: -8px;
          right: 10px;
          font-size: 24px;
          color: #666;
          padding: 10px;
          cursor: pointer;
        }
      }
    }
    .btn-wrap {
      overflow: hidden;
      .btn {
        width: 154px;
        height: 36px;
        line-height: 36px;
        float: left;
        margin-right: 12px;
        &.btn-preview {
          background-color: #fff;
          color: @color-dark-grey;
        }
      }
    }
  }
}
</style>