<template>
  <el-card class="box-card">
    <div class="card-header">
      <span>{{ root.name }}</span>
      <el-button class="button" @click="words_of_root" type="text"
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
export default {
  props: {
    root: {
      required: true,
    },
  },
  methods: {
    words_of_root() {
      this.$router.push("/root/" + this.root.id + "/" + this.root.name);
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
