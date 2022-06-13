<template>
  <el-menu
    class="el-menu"
    mode="horizontal"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    @select="handleSelect"
  >
    <elMenuItem index="home" class="home">Home</elMenuItem>
    <elMenuItem index="intro" class="intro">Introduction</elMenuItem>
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

    <!-- <el-sub-menu index="user">
      <template #title>User</template>
      <el-menu-item index="sign_in">sign in</el-menu-item>
      <el-menu-item index="sign_up">sign up</el-menu-item>
    </el-sub-menu> -->
  </el-menu>
</template>
<script>
export default {
  methods: {
    handleSelect(key, keyPath) {
      if (key === "intro") {
        this.intro();
        return;
      }
      if (key === "home") {
        this.home();
        return;
      }
      this.unit(key);
    },
    home() {
      this.$router.push({
        name: "Home",
      });
    },
    unit(index) {
      this.$router.push({
        name: "Unit",
        params: { unit: index },
      });
    },
    intro() {
      this.$router.push({
        name: "Introduction",
      });
    },
  },
};
</script>
<style scoped>
.intro {
  /* background-color: #545c64;
  color: #fff;
  text-align: center;
  cursor: pointer; */
  margin-left: 5%;
}
.home {
  margin-left: 7rem;
  font-size: medium;
}
</style>