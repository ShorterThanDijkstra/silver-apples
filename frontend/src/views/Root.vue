<template>
  <div class="center">
    <h1>{{ currentRoot.name }}</h1>
    <WordCard
      class="card"
      v-for="(word, index) in currentRoot.words"
      :key="index"
      :word="word"
    ></WordCard>
  </div>
</template>

<script>
import WordCard from "@/components/WordCard.vue";
import { mapState } from "vuex";
export default {
  components: { WordCard },
  computed:{
      ...mapState(['currentRoot'])
  },
};
</script>

<style>
.card {
  width: 45%;
  display: inline-grid;
  margin: 10px;
  height: 300px;
}
</style>