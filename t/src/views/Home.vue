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
import { defineComponent } from 'vue';
import axios from 'axios';

export default defineComponent({
  name: 'Home',
  setup(){
      console.log("setup");
      // function (response) {}相当于(response)=> {}
      axios.get("http://localhost:8080/ebook/list?name=Spring").then((response)=> {
          console.log(response);
      })
   }
});
</script>
