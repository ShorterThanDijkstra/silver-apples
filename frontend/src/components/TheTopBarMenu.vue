<template>
  <el-menu
    class="el-menu"
    mode="horizontal"
    background-color="#999"
    text-color="#333"
    active-text-color="#fff"
    :ellipsis="false"
    @select="handleSelect"
  >
    <el-sub-menu index="book">
      <template #title>Book</template>
      <elMenuItem index="intro" class="sub-menu intro">
        <template #title>Introduction</template>
      </elMenuItem>
      <el-sub-menu
          class="sub-menu"
          v-for="(group, i) in 3"
          :key="i"
          :index="String(i)"
      >
        <template #title>Unit {{ group * 10 - 9 }}-{{ group * 10 }}</template>
        <el-menu-item
            v-for="unit_index in 10"
            :key="unit_index"
            :index="String(group * 10 + unit_index - 10)"
        >
          unit {{ group * 10 + unit_index - 10 }}
        </el-menu-item>
      </el-sub-menu>

    </el-sub-menu>

    <div class="flex-grow"></div>
    <el-sub-menu index="user">
      <template #title>User</template>
      <el-menu-item v-if="userToken === null" index="login"
        >Sign in</el-menu-item
      >
      <el-menu-item v-else index="logout">Sign out</el-menu-item>
    </el-sub-menu>

    <elMenuItem index="home">
      <template #title>about</template>
    </elMenuItem>
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
          this.$router.go();
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
<style>
.el-menu {
  flex-wrap: wrap;
  border: none;
}
.flex-grow{
  flex-grow: 2;
}
/*.el-menu--horizontal>.el-menu-item.is-active{*/
/*  background-color: darkgrey;*/
/*}*/
</style>