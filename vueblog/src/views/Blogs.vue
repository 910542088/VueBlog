<template>
    <div>
        <Head></Head>
    <div class="block">
      <el-timeline>

        <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs" :key="blog">
          <el-card>
            <h4>
              <router-link :to="{name: 'BlogDetail', params: {blogId: blog.id}}">
                {{blog.title}}
              </router-link>
            </h4>
            <p>{{blog.description}}</p>
          </el-card>
        </el-timeline-item>

      </el-timeline>

      <el-pagination class="mpage"
                     background
                     layout="prev, pager, next"
                     :current-page="currentPage"
                     :page-size="pageSize"
                     :total="total"
                     @current-change=page>
      </el-pagination>
    </div>
</div>
</template>

<script>
import Head from '../components/Head'
export default {
    name: "Blogs",
    components:{Head},
    data() {
        return{
            blogs: {},
            currentPage: 1,
            total: 0,
            pageSize: 5
        }
    },
    methods:{
        page(currentPage){
            this.$axios.get("/blogs?currentPage="+currentPage).then(res=>{
                this.blogs = res.data.data.records;
                this.currentPage = res.data.data.current;
                this.total = res.data.data.total;
                this.pageSize = res.data.data.size;
            })
        }
    },
    created(){
        this.page(1);
    }
        
}
</script>

<style>
    .mpage {
    margin: 0 auto;
    text-align: center;
  }
</style>