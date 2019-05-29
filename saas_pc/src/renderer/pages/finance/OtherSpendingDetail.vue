<template>
  <div>
    <div class='main-header'>
      <div class="breadcrumb-wrap">
        <div class="back" @click="$router.go(-1)">
          <span class="iconfont icon-jiantou2"></span>
          返回
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>财务管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ name: 'spending'}">支出</el-breadcrumb-item>
          <el-breadcrumb-item>其他支出</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div id="main-content-addspending">
      <div class="form">
        <el-form :model="formParams" ref="releaseForm" :label-width="labelWidth" class="mission-form">
          <el-form-item label="使用日期" prop="begin_at" class="inline-item inline-item-iconfont block">
            <div class="inp-wrap">
              {{formParams.begin_at}}
            </div>
          </el-form-item>
          <el-form-item label="支出内容" class="inline-item inline-item-iconfont block">
            {{formParams.remark}}
          </el-form-item>
          <el-form-item label="价格明细" class="inline-item inline-item-iconfont block">
            {{formParams.other_money}}元
          </el-form-item>
        </el-form>
      </div>
      <div class="image-box">
        <h3>费用凭据
          <span>最多上传10张</span>
        </h3>
        <div class="image-wrap">
          <div class="upload-wrap">
            <div class="upload-image" v-for="(src, index) in images" :key="index">
              <img @click="checkImg(src)" :src="src" alt="">
            </div>
          </div>
        </div>
      </div>
      <div class='btn-wrap'>
        <router-link :to="{name: 'AddOtherSpending',query:{fee_id: formParams.id }}" class='btn'>修改</router-link>
        <a href="javascript:void(0);" class="btn btn-nobg" @click="deleteSpendingInfo">删除</a>
      </div>
    </div>
    <imgDialog ref="imgDialog" :imgShow.sync='imgShow' :imgs='imgs'></imgDialog> 
  </div>
</template>
<script>
import imgDialog from '@/components/imgDialog'
export default {
  name: 'OtherSpendingDetail',
  
  data () {
    return {
      labelWidth: '90px',
      formParams: {
        id: '',
        begin_at: '', //使用开始时间（关联订单时不传）
        other_money: null,
        remark: '',
        images: ''
      },
      images: [],
      imgLen: 0,
      isUploading: false, // 图片是否正在上传
      imgShow:false,
      imgs:''
    }
  },
  watch: {
    images (newVal) {
      this.formParams.images = newVal.join(",")
    }
  },
  created () {
    this.getQueryParams()
    this.getSpendingInfo()
  },
  methods: {
    getSpendingInfo () {
      let params, url
      let id = this.formParams.id
      if (!id) return
      params = { id: this.formParams.id }
      url = this.$httpUrl.feeDetail
      this.$axios.post(url, params).then(res => {
        if (res) {
          let formParams = {
            id: res.fee_id.toString(),
            other_money: Number.parseFloat(res.other_money),
            remark: res.remark,
            begin_at: res.begin_at
          }
          let imageStr = res.images.join(",")
          formParams.images = imageStr
          this.images = res.images
          this.imgLen = res.images.length
          this.formParams = Object.assign({}, formParams)
        }
      })
    },
    getQueryParams () {
      let query = this.$route.query
      if (query.fee_id) this.formParams.id = query.fee_id
    },
    backUrl () {
      this.$router.go(-1)
    },
    deleteSpendingInfo () {
      this.$confirm('是否删除', {
        type: 'warning'
      }).then(() => {
        let params = {
          id: this.formParams.id,
          type: -2
        }
        this.auditOrder(params)
      })
    },
    auditOrder (params) {
      let url
      url = this.$httpUrl.feeAudit
      this.$axios.post(url, params).then(res => {
        if (res) {
          this.$message({
            message: res.msg,
            type: 'success'
          });
          if (params.type == -2) {
            this.$router.go(-1)
          } 
        }
      })
    },
    checkImg(img){
    //  console.log(img)
     this.imgShow =true
     this.$nextTick(()=>{
       this.$refs.imgDialog.showToggle(img)
     })
   }
  },
  components: {
    imgDialog
  }
}
</script>
<style lang="less">
@imgWidth: 176px;
@imgHeight: 103px;
#main-content-addspending {
  .form {
    margin: 15px 16px 0 16px;
    background-color: #fff;
    overflow: hidden;
    padding-top: 24px;
    .inline-item {
      width: 50%;
      float: left;
      box-sizing: border-box;
      position: relative;
      &.block {
        float: none;
      }
      .inp-wrap {
        margin-right: 20px;
        .el-date-editor {
          width: 100% !important;
        }
        .el-input {
          width: 100%;
        }
      }
      &.inline-item-iconfont {
        padding-right: 40px;
        .iconfont {
          position: absolute;
          top: 0;
          right: -22px;
          color: #666;
          font-size: 24px;
          z-index: 9;
          cursor: pointer;
        }
      }
    }
  }
  .image-box {
    margin: 18px 16px 0 16px;
    background-color: #fff;
    h3 {
      height: 54px;
      line-height: 54px;
      border-bottom: 1px solid #e9e9e9;
      box-sizing: border-box;
      font-weight: bold;
      padding-left: 30px;
      span {
        font-size: 12px;
        color: #333;
        margin-left: 10px;
      }
    }
    .image-wrap {
      margin: 25px 20px 0 20px;
      padding-bottom: 10px;
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
          margin-right: 12px;
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
  }
  .btn-wrap {
    margin-top: 55px;
    text-align: center;
    .btn {
      display: inline-block;
      width: 154px;
      height: 36px;
      line-height: 36px;
      margin-right: 30px;
      &:last-of-type {
        margin-right: 0;
      }
    }
  }
}
</style>