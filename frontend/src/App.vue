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
      <router-view></router-view>
    </main>
  </div>
</template>