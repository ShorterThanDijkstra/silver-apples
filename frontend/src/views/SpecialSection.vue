<template>
  <div class="center">
    <h1>Special Section of Unit {{ $route.params.unit }}</h1>

    <WordCard
      class="card"
      v-for="(word, index) in specialSectionOfCurrentUnit($route.params.unit)"
      :key="index"
      :word="word"
    ></WordCard>
  </div>
</template>

<script>
import WordCard from "@/components/WordCard.vue";
import { mapGetters } from "vuex";
export default {
  components: {
    WordCard,
  },
  computed: {
    ...mapGetters(["specialSectionOfCurrentUnit"])
  },
};
</script>

<style>
</style>