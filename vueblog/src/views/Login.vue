<template>
    <div class="ruleForm">
  <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="用户名" prop="username">
    <el-input v-model="ruleForm.username"></el-input>
  </el-form-item>
  <el-form-item label="密码" prop="password">
    <el-input type="password" v-model="ruleForm.password"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="submitForm('ruleForm')">login</el-button>
    <el-button @click="resetForm('ruleForm')">reset</el-button>
  </el-form-item>
</el-form>
<a href="/blogs">暂不登录</a>
    </div>
</template>

<script>
export default {
  name:"Login",
    data() {
      return {
        ruleForm: {
          username: 'markerhub',
          password: '111111'
        },
        rules: {
          username: [
            { required: true, message: '请输入密码', trigger: 'blur' },
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'change' }
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
                this.$axios.post("/login",this.ruleForm).then(res => {
                // 获取数据
                const jwt = res.headers["jwt"];
                const userInfo = res.data.data;
                //把数据存入Store中
                this.$store.commit("SET_TOKEN",jwt);//localStore中
                this.$store.commit("SET_USERINFO",userInfo);//sessionStore中
                // console.log(this.$store.getters.getUser)//查看数据

                this.$router.push("/blogs");//登录路由跳转

            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style scoped>
    .ruleForm{
        max-width: 400px;
        margin: 0 auto;
    }
</style>