<template>
  <div class="center">
    <h1>Special Section of Unit {{ currentUnit.index }}</h1>

    <WordCard
      class="card"
      v-for="(word, index) in currentUnit.specialSectionWords"
      :key="index"
      :word="word"
    ></WordCard>
  </div>
</template>

<script>
import WordCard from "@/components/WordCard.vue";
import { mapState } from "vuex";
export default {
  components: {
    WordCard,
  },
  computed: {
    ...mapState(["currentUnit"]),
  },
};
</script>

<style>
</style>