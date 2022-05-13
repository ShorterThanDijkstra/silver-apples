<template>
  <el-menu
    class="el-menu"
    mode="horizontal"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    @select="handleSelect"
  >
    <p @click="intro" class="intro">Introduction</p>
    <el-sub-menu v-for="(group, i) in 6" :key="i" :index="String(i)">
      <template #title>Unit {{ group * 5 - 4 }}-{{ group * 5 }}</template>
      <el-menu-item
        v-for="unit_index in 5"
        :key="unit_index"
        :index="String(group * 5 + unit_index - 5)"
      >
        unit {{ group * 5 + unit_index - 5 }}
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>
<script>
import { mapActions } from "vuex";
export default {
  methods: {
    ...mapActions(["getUnitByIndex"]),
    handleSelect(key, keyPath) {
      this.getUnitByIndex(key);
      this.$router.push({
        name: "Unit",
      });
    },
    intro(){
      
      this.$router.push({
        name: "Introduction",
      });
    }
  },
};
</script>
<style scoped>
.intro {
  background-color: #545c64;
  color: #fff;
  text-align: center;
  cursor: pointer;
  margin-left: 7rem;
}
</style>