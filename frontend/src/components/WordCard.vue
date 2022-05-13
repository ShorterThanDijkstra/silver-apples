<template>
  <el-card class="box-card" style="height: 45%">
    <div class="card-header">
      <span>{{ word.spell }}</span>
    </div>
    <hr />
    <div class="card-body">
      <div>
        <div @click="hideOrShow('explain')">
          <div v-if="explainShow">
            <span>explain:</span>
            <button style="margin-left: 5rem" class="button" role="button">
              hide
            </button>
          </div>
          <div v-else>
            <span>explain:</span>
            <button style="margin-left: 5rem" class="button" role="button">
              show
            </button>
          </div>
        </div>
        <p v-show="explainShow">{{ word.explain }}</p>
        <hr />
      </div>

      <div>
        <div @click="hideOrShow('detail')">
          <div v-if="detailShow">
            <span>more: </span
            ><button style="margin-left:5.8rem" class="button" role="button">hide</button>
          </div>
          <div v-else>
            <span>more: </span
            ><button  style="margin-left:5.8rem" class="button" role="button">show</button>
          </div>
        </div>
        <div></div>
        <p v-show="detailShow">{{ word.detail }}</p>
        <hr />
      </div>

      <div>
        <div @click="hideOrShow('sentences')">
          <div v-if="sentencesShow">
            <span>sentences: </span
            ><button style="margin-left:3.4rem" class="button" role="button">hide</button>
          </div>
          <div v-else>
            <span>sentences: </span>
            <button style="margin-left:3.4rem" class="button" role="button">show</button>
          </div>
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
</script>
<style scoped>
</style>