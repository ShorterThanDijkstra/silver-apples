<script>
import TheSidebarMenu from "@/components/TheSidebarMenu.vue";
import TheTopNavigation from "./components/TheTopNavigation.vue";
export default {
  components: {
    TheTopNavigation: TheTopNavigation,
  },
};
</script>

<template>
  <div class="container">
    <div class="menu">
      <TheTopNavigation></TheTopNavigation>
    </div>
    <main class="main">
      <router-view></router-view>
    </main>
  </div>
</template>