<template>
  <el-menu
    class="el-menu"
    mode="horizontal"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    @select="handleSelect"
  >
    <el-sub-menu index="1">
      <template #title>Reading</template>
      <el-menu-item index="1-1">Unit 1</el-menu-item>
      <el-menu-item index="1-2">Unit 2</el-menu-item>
      <el-menu-item index="1-3">Unit 3</el-menu-item>
    </el-sub-menu>
    <el-sub-menu index="2">
      <template #title>Volcabulary</template>
      <el-menu-item index="2-1">Unit 1</el-menu-item>
      <el-menu-item index="2-2">Unit 2</el-menu-item>
      <el-menu-item index="2-3">Unit 3</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>
<script lang="ts" setup>
const handleSelect = (key: string, keyPath: string[]) => {
  console.log("key: ", key, " keyPath: ", keyPath)
}
</script>