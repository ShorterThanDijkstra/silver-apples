<template>
  <el-card class="word-card">
    <div class="card-header flex">
      <div>
        {{ practice.word.spell }}
      </div>
      <span>
        {{ new Date(practice.createTime).toLocaleString() }}
      </span>
    </div>
    <hr />
    <div class="card-body">
      <div>
        <span v-if="showExplain" @click="showExplain=false">
          {{ practice.word.explain }}
        </span>
        <button v-else class="button" @click="showExplain=true">show explanation</button>
      </div>
      <hr />
      <span>
        {{ practice.sentence }}
      </span>
    </div>
  </el-card>
</template>

<script lang="ts" setup>
import { ref } from "@vue/reactivity";

const props = defineProps<{
  practice: Object;
}>();
const showExplain = ref(false)
</script>
<style scoped>
</style>