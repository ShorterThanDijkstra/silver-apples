<template>
  <el-card class="word-card">
    <div class="card-header">
      <span>{{ word.spell }}</span>
    </div>
    <hr/>
    <div class="card-body">
      <div>
        <div>
          <div class="flex">
            <span>explain :</span>
            <button class="button  button-right" role="button" @click="hideOrShow('explain')">
              <span v-if="explainShow">hide</span>
              <span v-else>show</span>
            </button>
          </div>
        </div>
        <p v-show="explainShow">{{ word.explain }}</p>
        <hr/>
      </div>

      <div>
        <div>
          <div class="flex">
            <span>background : </span>
            <button
                class="button button-right"
                role="button"
                @click="hideOrShow('detail')"
            >
              <span v-if="detailShow">hide</span>
              <span v-else>show</span>
            </button>
          </div>
        </div>
        <p v-show="detailShow">{{ word.detail }}</p>
        <hr/>
      </div>

      <div>
        <div>
          <div class="flex">
            <span>sentences : </span
            >
            <button
                class="button button-right"
                role="button"
                @click="hideOrShow('sentences')"
            >
              <span v-if="sentencesShow">hide</span>
              <span v-else>show</span>
            </button>
          </div>
        </div>
        <div v-show="sentencesShow">
          <p v-for="(sentence, index) in word.sentences" :key="index">
            {{ sentence.text }}
          </p>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script lang="ts" setup>
import {ref} from "vue";

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
</script>
<style scoped>
.button.button-right {
  margin-left: auto;
  margin-top: 0;
}

</style>