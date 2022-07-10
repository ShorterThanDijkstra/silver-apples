<template>
  <el-card class="quiz-card">
    <div  class="quiz-page" v-for="(page, i) in quiz.quizPages" :key="i">
      <QuizPage :page="page"></QuizPage>
      <hr v-if="i < quiz.quizPages.length - 1" />
    </div>
  </el-card>
</template>
<script>
import QuizPage from "./QuizPage.vue";
export default {
  components: {
    QuizPage,
  },
  props: {
    quiz: { type: Object, required: true },
  },
};
</script>
<style scoped>

.quiz-card {
  margin-bottom: 2em;
}
</style>
