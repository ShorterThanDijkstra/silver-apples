<template>
  <el-card class="quiz-card">
    <div v-for="(page, i) in quiz.quizPages" :key="i">
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
.text {
  font-size: 20px;
}

.item {
  padding: 18px 0;
}

.quiz-card {
  width: 70%;
  display: inline-grid;
  margin-left: 15%;
  margin-bottom: 5rem;
  height: auto;
}
</style>
