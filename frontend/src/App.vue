<script>
import TheNavigation from "@/components/TheNavigation.vue";

export default {
  components: {
    TheNavigation: TheNavigation,
  },
};
</script>

<template>
  <TheNavigation></TheNavigation>
  <div class="container">
    <router-view></router-view>
  </div>
</template>

<style>
/* body {
    width: 90%;
    margin-left: auto;
    margin-right: auto;
    padding: 0;
} */
</style>
