<template>
  <div>
    <h1 class="head">Unit {{ currentUnit.index }}</h1>
    <br>
    <QuizCard
      v-for="(quiz, index) in quizzesOfCurrentUnit()"
      :quiz="quiz"
      :key="index"
    >
    </QuizCard>
    
  </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import QuizCard from "@/components/QuizCard.vue";
export default {
  components: {
    QuizCard,
  },
  computed: {
    ...mapGetters(["quizzesOfCurrentUnit"]),
    ...mapState(["currentUnit"]),
  },
};
</script>

<style scoped>
.head{
    margin-left: 15%;
}
</style>