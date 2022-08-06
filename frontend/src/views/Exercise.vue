<template>
  <div>
    <div class="head flex">
      <h1 class="title">Unit {{ $route.params.unit }}</h1>
      <button class="button button-right" type="button" @click="$router.back()">
        &#8592; back
      </button>
    </div>
    <br />
    <div class="quiz-cards"></div>
    <QuizCard
      v-for="(quiz, index) in quizzesOfCurrentUnit"
      :quiz="quiz"
      :key="index"
    >
    </QuizCard>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import QuizCard from "@/components/QuizCard.vue";
export default {
  components: {
    QuizCard,
  },
  computed: {
    ...mapGetters(["quizzesOfCurrentUnit"]),
  },
};
</script>

<style scoped>
.button.button-right {
  margin-left: auto;
}
.quiz-cards {
  margin-top: -1em;
}
</style>