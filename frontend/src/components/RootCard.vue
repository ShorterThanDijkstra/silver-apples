<template>
    <el-card class="box-card">
      <div class="card-header">
        <span>{{ root.name }}</span>
        <el-button class="button" @click="wordsOfRoot" type="text"
          >Words</el-button
        >
      </div>
      <hr />
      <div class="card-body">
        <p>{{ root.description }}</p>
      </div>
    </el-card>
</template>
<script>
import { mapMutations } from "vuex";
export default {
  props: {
    root: {
      type: Object,
      required: true,
    },
    rootIndex: {
      type: Number,
      required: true,
    },
  },
  methods: {
    ...mapMutations(["setCurrentRootIndex"]),
    wordsOfRoot() {
      this.setCurrentRootIndex(this.rootIndex);
      this.$router.push({
        name: "Root",
        params: { name: this.root.name },
      });
    },
  },
};
</script>
<style>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
