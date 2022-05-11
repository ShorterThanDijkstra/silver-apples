<template>
  <el-card class="box-card" style="height: 45%">
    <div class="card-header">
      <span>{{ word.spell }}</span>
    </div>
    <hr />
    <div class="card-body">
      <div>
        <div @click="hideOrShow('explain')">
          <span v-if="explainShow">explain: (hide)</span>
          <span v-else>explain: (show)</span>
        </div>
        <p v-show="explainShow">{{ word.explain }}</p>
        <hr />
      </div>

      <div>
        <div @click="hideOrShow('detail')">
          <span v-if="detailShow">more: (hide)</span>
          <span v-else>more: (show)</span>
        </div>
        <p v-show="detailShow">{{ word.detail }}</p>
        <hr />
      </div>

      <div>
        <div @click="hideOrShow('sentences')">
          <span v-if="sentencesShow">sentences: (hide)</span>
          <span v-else>sentences: (show)</span>
        </div>
        <div v-show="sentencesShow">
          <p v-for="(sentence, index) in word.sentences" :key="index">
            {{ sentence }}
          </p>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script lang="ts" setup>
import { ref } from "vue";

const props = defineProps<{
  word: Object;
}>();

let explainShow = ref(false);
let sentencesShow = ref(false);
let detailShow = ref(false);
const hideOrShow = (item: String) => {
  if (item === "detail") {
    detailShow.value = !detailShow.value;
  } else if (item === "sentences") {
    sentencesShow.value = !sentencesShow.value;
  } else if (item === "explain") {
    explainShow.value = !explainShow.value;
  }
};

const handleChange = (val: string[]) => {
  console.log(val);
};
</script>
<style>
.card {
  width: 45%;
  display: inline-grid;
  margin: 10px;
  height: 300px;
  border: 1px solid #ccc !important;
  padding: 14px;
  border-radius: 16px !important;
}
.center {
  /* text-align: center; */
  margin-left: 5%;
  font-size: 22px;
  /* margin-right: 5%; */
}
</style>