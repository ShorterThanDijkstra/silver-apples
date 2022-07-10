<template>
  <el-card>
    <div class="card-header">
      <span>{{ root.name }}</span>
      <button class="button" @click="wordsOfRoot" type="text">Words</button>
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
<style scoped>

</style>
