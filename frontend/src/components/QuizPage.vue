<template>
  <div class="text item">
    <div v-for="(content, j) in page.content.split(/\d+\.\s+/)" :key="j">
      <div v-if="j == 0" style="font-weight: bold">
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
          description="Fill in the answers to right(separate by commas if there are multiple options):"
          type="info"
          :closable="false"
          show-icon
          effect="dark"
        />
      </div>
      <div v-else>
        <p style="display: inline-block; margin-right: 2rem">
          {{ j }}. {{ " " + content }}
        </p>
        <input
          v-model="userInputs[j - 1]"
          style="
            font-weight: bold;
            font-size: 18px;
            width: 3rem;
            border: 1px solid #ccc !important;
            padding: 10px;
            border-radius: 16px !important;
          "
          type="text"
        />
      </div>
    </div>
    <button @click="checkAnswers" class="button">check answers</button>
    <!-- {{ page.answers }} -->
  </div>
</template>

<script>
import { ElNotification } from "element-plus";
import { ElMessage } from "element-plus";
export default {
  props: {
    page: {
      type: Object,
      required: true,
    },
  },
  data() {
    return { userInputs: this.initUserInputs() };
  },
  methods: {
    initUserInputs() {
      const inputs = [];
      for (let i = 0; i < this.page.answers.length; i++) {
        inputs.push("");
      }
      return inputs;
    },
    compare(actual, expect, index) {
      if (actual === "") {
        this.showError("your answer is empty", index + 1);
        return false;
      }
      const actualList = actual.split(/,|，|\s+/);
      const expectList = expect.split(/,|，|\s+/);
      if (actualList.length != expectList.length) {
        const msg = "your answer is " + actual + ", right answer is " + expect;
        this.showError(msg, index + 1);
        return false;
      }
      for (let i = 0; i < expectList.length; i++) {
        if (expectList[i] !== actualList[i]) {
          const msg =
            "your answer is " + actual + ", correct answer is " + expect;
          this.showError(msg, index + 1);
          return false;
        }
      }
      return true;
    },
    checkAnswers() {
      let allCorrect = true;
      for (let index = this.page.answers.length - 1; index >= 0; index--) {
        let compared = this.compare(
          this.userInputs[index].trim().toLowerCase(),
          this.page.answers[index].trim().toLowerCase(),
          index
        );
        allCorrect = allCorrect && compared;
      }
      if(allCorrect){
        this.showSuccess();
      }
    },
    showError(msg, question) {
      ElNotification({
        title: "question " + question + ":",
        message: msg,
        type: "error",
        duration: 0,
        offset: 100,
      });
    },
    showSuccess() {
      ElMessage({
        message: "Congrats, all your answers are right.",
        type: "success",
      });
    },
  },
};
</script>

<style scoped>
.el-alert {
  margin: 20px 0 0;
  font-size: large;
}
</style>