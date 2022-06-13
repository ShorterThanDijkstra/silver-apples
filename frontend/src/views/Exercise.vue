<template>
  <div>
    <h1 class="head">Unit {{ $route.params.unit }}</h1>
    <br>
    <QuizCard
      v-for="(quiz, index) in quizzesOfCurrentUnit($route.params.unit)"
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
.head{
    margin-left: 15%;
}
</style>