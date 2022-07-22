<template>
  <div>
    <div class="head flex">
      <h1 class="title">Special Section of Unit {{ $route.params.unit }}</h1>
      <button class="button button-right" type="button" @click="$router.back()">&#8592; back</button>
    </div>

    <div class="grid">
      <WordCard
          v-for="(word, index) in specialSectionOfCurrentUnit"
          :key="index"
          :word="word"
          :specialSection="true"
      ></WordCard>
    </div>

  </div>
</template>

<script>
import WordCard from "@/components/WordCard.vue";
import {mapGetters} from "vuex";

export default {
  components: {
    WordCard,
  },
  computed: {
    ...mapGetters(["specialSectionOfCurrentUnit"])
  },
};
</script>

<style scoped>
.button.button-right{
  margin-left: auto;
}
</style>