<template>
  <div>
    <div class="center">
      <h1>Unit {{currentUnit.index}}</h1>
      <RootCard
        class="card"
        v-for="(root, index) in currentUnit.roots"
        :key="index"
        :root="root"
      ></RootCard>
    </div>
  </div>
</template>

<script>
import RootCard from "@/components/RootCard.vue";
import { mapState } from "vuex";
export default {
  components: { RootCard },
  computed: {
    ...mapState(["currentUnit"]),
  },
};
</script>

<style>
.card {
  width: 45%;
  display: inline-grid;
  margin: 10px;
  height: 300px;
  border: 1px solid #ccc !important;
  padding: 14px;
  border-radius: 16px !important;
}
.center {
  margin-left: 5%;
  font-size: 22px;
  /* margin-right: 5%; */
}
</style>