<template>

  <a-button type="danger">Danger</a-button>
  <div class="home">
    <a-button type="danger">
      Danger
    </a-button>
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js + TypeScript App"/>
  </div>
</template>

<script lang="ts">
import { defineComponent ,onMounted,ref,reactive,toRef} from 'vue';
import axios from 'axios';

export default defineComponent({
  name: 'Home',
  setup(){
      console.log("setup");
      //ref()响应式数据
      const ebooks = ref();
      const ebook1= reactive({books:[]});
      onMounted(()=>{
          console.log("onMounted");
          // function (response) {}相当于(response)=> {}
          axios.get("http://localhost:8080/ebook/list?name=Spring").then((response)=> {
              const data = response.data;
              ebooks.value = data.content;
              ebook1.books =data.content;
              console.log(response);
          });
      });
      return {
          ebooks,
          ebook2:toRef(ebook1,"books")
      }
   }
});
</script>
