<template>
  <el-card class="word-card">
    <div class="card-header flex">
      <span>{{ word.spell }}</span>
      <WordAudio :word="word"></WordAudio>
      <button
        v-show="!specialSection"
        class="button button-right"
        @click="makeSentences"
      >
        make sentences
      </button>
    </div>
    <hr />
    <div class="card-body">
      <div>
        <div>
          <div class="flex">
            <span>explanation :</span>
            <button
              class="button button-right"
              role="button"
              @click="hideOrShow('explain')"
            >
              <span v-if="explainShow">hide</span>
              <span v-else>show</span>
            </button>
          </div>
        </div>
        <p v-show="explainShow" class="content">{{ word.explain }}</p>
        <hr />
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
        <p v-show="detailShow" class="content">{{ word.detail }}</p>
        <hr />
      </div>

      <div>
        <div>
          <div class="flex">
            <span>sentences : </span>
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
        <div v-show="sentencesShow" class="content">
          <p v-for="(sentence, index) in word.sentences" :key="index">
            {{ sentence.text }}
          </p>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { mapGetters } from "vuex";
import WordAudio from "./WordAudio.vue";
const router = useRouter();
const props = defineProps<{
  word: Object;
  specialSection: boolean;
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
const makeSentences = () => {
  if (!props.specialSection.valueOf()) {
    router.push({
      name: "MakeSentences",
      params: {
        spell: props.word.spell,
      },
    });
  }
};
</script>
<style scoped>
.button.button-right {
  margin-left: auto;
  margin-top: 0;
}
.word-card {
  font-weight: bold;
  font-size: 1.3rem;
}
.content {
  font-weight: normal;
  font-size: 1rem;
  line-height: 1.5em;
}
</style>