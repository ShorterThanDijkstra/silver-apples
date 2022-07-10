<template>
  <div>
    <div v-for="(content, j) in page.content.split(/\d+\.\s+/)" :key="j">
      <div v-if="j === 0" class="quiz-head">
        <p>{{ content.split(":")[0] }}:</p>
        <div v-if="content.split(':').length > 1">
          <p
              v-for="(candi, k) in content.split(':')[1].split(/[a-zA-Z]\.\s/)"
              :key="k"
          >
            <span v-if="k !== 0"
            >{{ String.fromCharCode(96 + k) }}.{{ " " + candi }}</span
            >
          </p>
        </div>
        <el-alert
            description="Separate the answers by commas if there are multiple options:"
            type="info"
            :closable="false"
            show-icon
            effect="dark"
        />
      </div>
      <div v-else class="quiz-content">
        <p class="question">
          {{ j }}. {{ " " + content }}
        </p>
        <input class="answer"
               v-model="userInputs[j - 1]"
               type="text"
        />
      </div>
    </div>
    <button @click="checkAnswers" class="button">check answers</button>
<!--     {{ page.answers }}-->
  </div>
</template>

<script>
import {ElMessage, ElNotification} from "element-plus";

export default {
  props: {
    page: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {userInputs: this.initUserInputs()};
  },
  methods: {
    initUserInputs() {
      const inputs = [];
      for (let i = 0; i < this.page.answers.length; i++) {
        inputs.push("");
      }
      return inputs;
    },
    compare(actual, expect, index, showError) {
      if (actual === "") {
        showError("your answer is empty", index + 1);
        return false;
      }
      const actualList = actual.split(/,|，|\s+/);
      const expectList = expect.split(/,|，|\s+/);
      if (actualList.length !== expectList.length) {
        const msg = "your answer is " + actual + ", right answer is " + expect;
        showError(msg, index + 1);
        return false;
      }
      for (let i = 0; i < expectList.length; i++) {
        if (expectList[i] !== actualList[i]) {
          const msg =
              "your answer is " + actual + ", correct answer is " + expect;
          showError(msg, index + 1);
          return false;
        }
      }
      return true;
    },
    checkAnswers() {
      let allCorrect = true;
      const showError = this.showError()
      for (let index = 0; index < this.page.answers.length; index++) {
        let compared = this.compare(
            this.userInputs[index].trim().toLowerCase(),
            this.page.answers[index].trim().toLowerCase(),
            index,
            showError
        );
        allCorrect = allCorrect && compared;
      }
      if (allCorrect) {
        this.showSuccess();
      }
    },
    showError() {
      let offset = 10
      let duration = 2000
      return (msg, question) => {
        ElNotification({
          title: "question " + question + ":",
          message: msg,
          type: "error",
          duration: duration,
          offset: offset,
        });
        duration += 800
        offset += 100
      }
    },

    showSuccess() {
      ElMessage({
        message: "Congrats, all your answers are correct.",
        type: "success",
      });
    },
  },
};
</script>

<style scoped>
.question {
  flex: 10;
  margin-right: 1em;
}

.answer {
  flex: 1;
  margin-left: auto;
  margin-top: 1em;
  padding-left: 0;
  width: 4em;
  height: 2em;
  font-weight: bold;
  border: 1px solid #ccc;
  border-radius: 10px;
  text-align: center;
}

@media (min-width: 35em) {
  .answer {
    flex: 1;
    margin-left: auto;
    margin-top: 2em;
    padding-left: 0;
    width: 4em;
    height: 2em;
    font-weight: bold;
    border: 1px solid #ccc;
    border-radius: 10px;
    text-align: center;
  }
}

.quiz-head {
  font-weight: bold;
}

.quiz-content {
  display: flex;
}
</style>