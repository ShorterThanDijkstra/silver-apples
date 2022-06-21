<script>
import TheSidebarMenu from "@/components/TheSidebarMenu.vue";

export default {
  components: {
    TheSidebarMenu: TheSidebarMenu,
  },
};
</script>

<template>
  <div class="container">
    <div class="sidebar">
      <TheSidebarMenu></TheSidebarMenu>
    </div>
    <main class="main">
      <h1>Main</h1>
      <router-view></router-view>
    </main>
  </div>
</template>