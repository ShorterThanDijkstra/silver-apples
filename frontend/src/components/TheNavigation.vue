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
    <el-sub-menu index="user">
      <template #title>User</template>
      <el-menu-item v-if="userToken === null" index="login">Sign in</el-menu-item>
      <el-menu-item v-else index="logout">Sign out</el-menu-item>
    </el-sub-menu>
    <span class="vl"></span>
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
import { mapGetters, mapMutations } from "vuex";
import { ElMessageBox } from "element-plus";

export default {
  computed: {
    ...mapGetters(["userToken"]),
  },
  methods: {
    ...mapMutations(["clearUserToken"]),
    handleSelect(key, keyPath) {
      if (key === "intro") {
        this.intro();
        return;
      }
      if (key === "login") {
        this.login();
        return;
      }
      if (key === "logout") {
        this.logout();
        return;
      }
      if (key === "home") {
        this.home();
        return;
      }
      this.unit(key);
    },
    login() {
      this.$router.push({
        name: "Login",
      });
    },
    logout() {
      ElMessageBox.confirm("Logout?", "", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "info",
      })
        .then(() => {
          this.clearUserToken();
          this.$router.go()
        })
        .catch(() => {});
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
  margin-left: 3rem;

}
.home {
  margin-left: 7rem;
}
.vl {
  border-left: 0.5px solid #fff;
  height: auto;
}
</style>