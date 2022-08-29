<template>
  <el-card class="word-card">
    <div class="card-header flex">
      <div>
        {{ practice.word.spell }}
      </div>
      <WordAudio class="audio" :word="practice.word"></WordAudio>
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
        <button v-else class="button" @click="showExplain=true">explanation</button>
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
import WordAudio from "./WordAudio.vue";

const props = defineProps<{
  practice: Object;
}>();
const showExplain = ref(false)
</script>
<style scoped>
.audio{
  margin-left: -1.5em;
}
</style>