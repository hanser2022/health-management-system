<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >
      <div class="title-container">
        <img src="../../assets/logo.png" style="width: 40px;position: relative; top: 13px;right: 6px">
        <span style="color: #409EFF;font-size: 20px;font-size: 20px">个人健康管理系统</span>
      </div>

      <br><br>
      <el-form-item prop="username">
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon
            :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"
          />
        </span>
      </el-form-item>

      <el-button
        :loading="loading"
        type="primary"
        style="width: 100%; margin-bottom: 30px"
        @click.native.prevent="handleLogin"
      >登 陆</el-button>
      <div>
        <el-button
          type="primary"
          style="width: 100%; margin-bottom: 30px; margin-left: 0px"
          @click.native.prevent="handleRegister"
        >注 册</el-button>
      </div>
      <div v-if="showRegisterTip" class="register-tip">
        <i class="el-icon-info" /> 注册成功！登录后请先录入身体信息
      </div>
    </el-form>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'

export default {
  name: 'Login',
  data() {
    // 定义验证用户名函数
    const validateUsername = (rule, value, callback) => {
      // 调用 validUsername 函数判断用户名是否合法
      if (!validUsername(value)) {
        // 如果不合法则返回错误信息
        callback(new Error('请输入正确的用户名'))
      } else {
        // 合法则调用 callback() 函数返回验证成功信息
        callback()
      }
    }
    // 定义验证密码函数
    const validatePassword = (rule, value, callback) => {
      // 判断密码是否小于6位
      if (value.length < 6) {
        // 如果小于6位则返回错误信息
        callback(new Error('输入的密码不能少于6位'))
      } else {
        // 合法则调用 callback() 函数返回验证成功信息
        callback()
      }
    }
    return {
      // 定义表单数据对象
      loginForm: {
        username: '',
        password: ''
      },
      // 定义表单验证规则
      loginRules: {
        username: [
          { required: true, trigger: 'blur', validator: validateUsername }
        ],
        password: [
          { required: true, trigger: 'blur', validator: validatePassword }
        ]
      },
      // 定义 loading 状态
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      showRegisterTip: false
    }
  },

  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
        this.showRegisterTip = route.query && route.query.registered === 'true'
      },
      immediate: true
    }
  },

  methods: {

    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },

    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true // 显示 loading 状态圈
          this.$store
            .dispatch('user/login', this.loginForm)
            .then((res) => {
              localStorage.setItem('username', this.loginForm.username)
              // 登录成功，跳转到目标路由
              this.$router.push({ path: this.redirect || '/' })
              this.loading = false // 隐藏 loading 状态
            })
            .catch(() => {
              this.loading = false // 隐藏 loading 状态
            })
        } else {
          return false
        }
      })
    },

    handleRegister() {
      this.$router.push({ path: '/register' })
    }
  }
}
</script>

<style lang="scss">
$bg: #2d3a4b;
$dark_gray: #3472a6;
$light_gray: #eee;

.login-container {
  height: 100vh;
  width: 100%;
  background-color: $bg;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url("../../assets/loginBack.png");
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;

  .login-form {
    position: relative;
    width: 400px;
    max-width: 100%;
    opacity: 0.95;
    padding: 80px 35px 0;
    margin: 0 auto;
    overflow: hidden;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.3);
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;
    margin-left: 16%;

    .title {
      font-size: 26px;
      color: $dark_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }

  .el-form-item {
    margin-bottom: 20px;
  }

  .el-input {
    width: 100%;
    border: none;
    border-radius: 5px;
    background-color: #f7f7f7;

    input {
      padding: 15px;
      color: $dark_gray;

      &::placeholder {
        color: #ccc;
      }
    }
  }

  .el-button {
    height: 50px;
    font-size: 16px;
    background-color: $dark_gray;
    border: none;

    &:hover,
    &:focus {
      background-color: darken($dark_gray, 10%);
    }
  }

  .register-tip {
    text-align: center;
    color: #67C23A;
    font-size: 14px;
    margin-bottom: 20px;
    padding: 10px;
    background-color: #f0f9eb;
    border-radius: 4px;
  }
}
</style>
