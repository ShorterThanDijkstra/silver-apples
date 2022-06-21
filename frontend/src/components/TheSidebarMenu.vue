<template>
  <el-menu
    class="sidemenu"
    :collapse="isCollapse"
    @open="handleOpen"
    @close="handleClose"
  >
    <el-sub-menu
      :index="`unit-group-${unitGroup}`"
      v-for="unitGroup in 5"
      :key="unitGroup"
    >
      <template #title>
        <el-icon><IconMenu></IconMenu></el-icon>
        <span
          >unit{{ unitGroupStart(unitGroup) }}-{{
            unitGroupEnd(unitGroup)
          }}</span
        >
      </template>
      <el-menu-item v-for="unitIndex in unitsInGroup(unitGroup)" :key="unitIndex" @click="showUnit(unitIndex)" index="1-1"
          >{{unitIndex}}</el-menu-item
        >
    </el-sub-menu>

    <el-sub-menu index="unit-1">
      <template #title>
        <el-icon><IconMenu></IconMenu></el-icon>
        <span>Unit 1</span>
      </template>
      <el-menu-item @click="showUnit" index="1-1">item one</el-menu-item>
      <el-menu-item index="1-2">item two</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script setup>
import { ref } from "vue";
import { Menu as IconMenu } from "@element-plus/icons-vue";

const isCollapse = ref(true);
const handleOpen = (key, keyPath) => {
//   isCollapse.value = false;
};
const handleClose = (key, keyPath) => {
//   isCollapse.value = true;
};
const showUnit = (unitIndex) => {
  console.log(unitIndex);
};
// const sidemenubar=()
const unitGroupStart = (groupIndex) => groupIndex * 5 - 4;
const unitGroupEnd = (groupIndex) => groupIndex * 5;
const unitsInGroup = (groupIndex) =>
  Array.from({ length: 6 }, (_, i) => unitGroupStart(groupIndex) + i);
</script>

<style >
.sidemenu {
  display: flex;
  flex-direction: column;
  font-size: 0.1rem;
  width: auto;
}
.el-sub-menu.is-opened {
}
</style>
