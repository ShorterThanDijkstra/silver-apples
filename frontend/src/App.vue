<script>
import TheNavigation from "@/components/TheNavigation.vue"
import { mapActions } from "vuex"
export default {
  components: {
    'TheNavigation': TheNavigation
  },
  methods:{
    ...mapActions(["allRoots"])
  },
  mounted(){
    this.allRoots();
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
