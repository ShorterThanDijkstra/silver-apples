<script>
import TheNavigation from "@/components/TheNavigation.vue"
export default {
  components: {
    TheNavigation: TheNavigation
  }
}
</script>

<template>
  <TheNavigation></TheNavigation>
  <div class="container">
    <router-view></router-view>
  </div>
</template>

<style>
</style>
