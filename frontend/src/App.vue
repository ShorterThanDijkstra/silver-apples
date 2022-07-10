<script>
import TheTopNavigation from "./components/TheTopBarMenu.vue";
import {mapGetters} from "vuex";

export default {
  components: {
    TheTopNavigation: TheTopNavigation,
  },
  computed: {
    ...mapGetters(['userToken'])
  }
};
</script>

<template>
  <div class="container">
    <div v-show="userToken" class="menu">
      <TheTopNavigation></TheTopNavigation>
    </div>
    <main class="main">
      <router-view></router-view>
    </main>
  </div>
</template>