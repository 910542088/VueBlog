<template>
  <div class="head">
    <h3>欢迎光临</h3>
    <div>
      <el-avatar :size="50" :src="user.avatar"></el-avatar>
    </div>
    <div>{{user.username}}</div>
    <div class="option">
      <span><el-link type="primary" href="/blogs">主页</el-link></span>
      <el-divider direction="vertical"></el-divider>
      <span><el-link type="success" href="/blog/add">发表</el-link>
      <el-divider direction="vertical"></el-divider></span>
      <span v-show="!hasLogin"><el-link type="info" href="/login">登录</el-link></span>
      <span v-show="hasLogin"><el-link type="danger" @click="logout">退出</el-link></span>

    </div>
  </div>
</template>

<script>
export default {
  name: 'Head',
  data() {
    return {
      user:{
        username:'请登录',
        avatar:''
      },
      hasLogin: false,
    }
  },
  created() {
    if(this.$store.getters.getUser.username){
      this.user.username = this.$store.getters.getUser.username;
      this.user.avatar = this.$store.getters.getUser.avatar;
      this.hasLogin = true;
    }
  },
  methods: {
    logout(){
      this.$axios.get("/logout",{headers:{"jwt":localStorage.getItem("token")}}).then(res=>{
        this.$store.commit("REMOVE_INFO");
        this.$router.push("/login");
      })
    }
  }
  
}
</script>

<style scoped>
  .head{
    margin: 0 auto;
    text-align: center;
  }
  .option{
    margin: 10px 0;
  }
</style>
