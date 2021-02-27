<template>
  <div>
    <Header></Header>

    <div class="mblog">
      <h2> {{ blog.title }}</h2>
      <el-link icon="el-icon-edit" v-if="ownBlog">
        <router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}" >编辑</router-link>
      </el-link>
      <el-link icon="el-icon-edit" v-if="ownBlog">
      <span  @click="del"><a>删除</a></span> 
      </el-link>
      <div class="markdown-body" v-html="blog.content"></div>

    </div>

  </div>
</template>

<script>
  import 'github-markdown-css'
  import Header from "../components/Head";

  export default {
    name: "BlogDetail",
    components: {Header},
    data() {
      return {
        blog: {
          id: "",
          title: "",
          content: ""
        },
        ownBlog: false
      }
    },
    methods:{
         del(){
              if(confirm("确定是否删除")){
                   this.$axios.delete("/delete/"+this.blog.id , {headers:{"jwt":localStorage.getItem("token")}})
                   .then(resp=>{
                   this.$router.push("/blogs");
                    })
              }
         }
    },
    created() {
      const blogId = this.$route.params.blogId
      this.$axios.get('/blog/' + blogId).then(res => {
        const blog = res.data.data
        this.blog.id = blog.id
        this.blog.title = blog.title

        var MardownIt = require("markdown-it")
        var md = new MardownIt()

        var result = md.render(blog.content)
        this.blog.content = result
        this.ownBlog = (blog.userId == this.$store.getters.getUser.id)

      })
    }
  }
</script>

<style scoped>
  .mblog {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    width: 100%;
    min-height: 700px;
    padding: 20px 15px;
  }

</style>