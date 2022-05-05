<template>
  <el-menu
    class="el-menu"
    mode="horizontal"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    @select="handleSelect"
  >
    <el-sub-menu v-for="(group, i) in 6" :key="i" :index="String(i)">
      <template #title>Unit {{ group * 5 - 4 }}-{{ group * 5 }}</template>
      <el-menu-item
        v-for="unit_index in 5"
        :key="unit_index"
        :index="String(group * 5 + unit_index - 5 )"
      >
        unit {{ group * 5 + unit_index - 5 }}
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
  
</template>
<script lang="ts" setup>
import { useRouter } from "vue-router";
const router = useRouter();
const handleSelect = (key: string, keyPath: string[]) => {
  router.push("/unit/" + key);
};
</script>