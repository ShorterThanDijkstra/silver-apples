<template>
  <el-card class="quiz-card">
    <div v-for="(page, i) in quiz.quizPages" :key="i">
      <div class="text item">
        <div v-for="(content, j) in page.content.split(/\d+\.\s+/)" :key="j">
          <div v-if="j == 0" style="font-weight: bold">
            <p>{{ content.split(":")[0] }}:</p>
            <div v-if="content.split(':').length > 1">
              <p
                v-for="(candi, k) in content
                  .split(':')[1]
                  .split(/[a-zA-Z]\.\s/)"
                :key="k"
              >
                <span v-if="k !== 0"
                  >{{ String.fromCharCode(96 + k) }}.{{ " " + candi }}</span
                >
              </p>
            </div>
          </div>
          <div v-else>
            <p>{{ j }}. {{ " " + content }}</p>
          </div>
        </div>
      </div>
      <div class="text item">{{ page.answers }}</div>
      <hr v-if="i < quiz.quizPages.length - 1" />
    </div>
  </el-card>
</template>
<script>
export default {
  props: {
    quiz: { type: Object, required: true },
  },
};
</script>
<style scoped>
.text {
  font-size: 25px;
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
