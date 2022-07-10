<template>
  <el-menu
    class="el-menu-vertical"
    :collapse="isCollapse"
    :collapse-transition="true"
    :unique-opened="true"
    :default-active="router.currentRoute.value.fullPath"
    router
    @open="handleOpen"
    @close="handleClose"
  >
    <div>
      <button class="menu-toggle" @click="toggle">toggle-menu</button>
    </div>
    <el-sub-menu v-for="(group, i) in 6" :key="i" :index="`unit-group-${i}`">
      <template #title>
        <el-icon><IconMenu /></el-icon>
        <span>Unit {{ group * 5 - 4 }}-{{ group * 5 }}</span>
      </template>
      <el-menu-item
        v-for="rank in 5"
        :key="rank"
        :index="`/unit/${group * 5 + rank - 5}`"
      >
        unit {{ group * 5 + rank - 5 }}
      </el-menu-item>
    </el-sub-menu>

    <div class="bottom">
      <el-menu-item index="/">
        <el-icon><setting /></el-icon>
        <template #title>setting</template>
      </el-menu-item>
    </div>
  </el-menu>
</template>

<script  setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { Menu as IconMenu, Setting } from "@element-plus/icons-vue";

const router = useRouter();
const isCollapse = ref(true);
const paths = ref(router.currentRoute.value.fullPath)
const handleOpen = (key, keyPath) => {
};
const handleClose = (key, keyPath) => {
 

};
const showUnit = (unitIndex) => {
  router.push({
    name: "Unit",
    params: { unit: unitIndex },
  });
};
const toggle = () => {
  isCollapse.value = !isCollapse.value;
};
</script>

<style>
.el-menu-vertical {
  display: flex;
  flex-direction: column;
  /* font-size: 0.1rem; */
  width: auto;
  height: 100%;
}
.bottom {
  margin-top: auto;
}
.menu-toggle {
  right: 0.1em;
  border: 0;
  background-color: transparent;
  font-size: 2.5em;
  width: 1em;
  text-indent: 5em;
  white-space: nowrap;
  overflow: hidden;
}
.menu-toggle::after {
  position: absolute;
  top: 0.2em;
  left: 0.5em;
  display: block;
  content: "\2261";
  text-indent: 0;
}
.el-icon.el-sub-menu__icon-arrow{
  display: none;
}
</style>
