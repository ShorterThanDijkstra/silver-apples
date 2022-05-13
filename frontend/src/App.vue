<script>
import TheNavigation from "@/components/TheNavigation.vue";
import { mapActions } from "vuex";

export default {
  components: {
    TheNavigation: TheNavigation,
  },
  methods: {
    ...mapActions(["getTheIntro"]),
  },
  mounted() {
    this.getTheIntro();
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
</style>
