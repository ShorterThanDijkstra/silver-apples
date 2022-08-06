<template>
  <el-form
    ref="custom-word-form"
    :model="word"
    :rules="rules"
    label-width="120px"
    class="custom-word-form"
    :size="formSize"
    @submit.prevent
    label-position="left"
    style="max-width: 460px"
    status-icon
  >
    <el-form-item label="word" prop="spell">
      <el-input v-model="word.spell" />
    </el-form-item>
    <el-form-item label="explanation" prop="explanation">
      <el-input v-model="word.explanation" />
    </el-form-item>
    <el-form-item label="sentence" prop="sentence">
      <el-input v-model="word.sentence" />
    </el-form-item>
    <el-form-item label="note" prop="note">
      <el-input v-model="word.note" type="textarea" />
    </el-form-item>
    <el-form-item class="submit">
      <button class="button" @click="submitForm()">Add</button>
    </el-form-item>
  </el-form>
</template>

<script >
import { ElMessage } from "element-plus";
export default {
  props: ["formVisible"],
  data() {
    return {
      word: {
        spell: "",
        explanation: "",
        sentence: "",
        note: "",
      },
      formSize: "default",
      rules: {
        spell: [
          {
            required: true,
            message: "Please input word name",
            trigger: "blur",
          },
          {
            min: 3,
            message: "Length should be great than 2",
            trigger: "blur",
          },
        ],
        explanation: [
          {
            required: true,
            message: "Please input word explanation",
            trigger: "blur",
          },
        ],
        sentence: [
          {
            required: false,
            message: "Please input word sentence",
            trigger: "blur",
          },
        ],

        note: [
          {
            required: false,
            message: "Please input word note",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    fail(msg) {
      // window.alert(msg);
 
      ElMessage.error(msg);
    },
    success() {
      ElMessage({
        message: "submit successfully",
        type: "success",
        duration: 1000,
      });
      this.$emit("formNotVisible");
      this.$emit("addCustomWord")
      this.$refs["custom-word-form"].resetFields();

    },
    valid() {
      const spellCheck =
        this.word.spell != null && this.word.spell.trim().length > 2;
      const explanationCheck =
        this.word.explanation != null && this.word.explanation.trim() != "";
      return spellCheck && explanationCheck;
    },
    submitForm() {
      if (this.valid()) {
        const url = this.$backend + "/user/custom-word/";
        this.$http.post(url, this.word).then((response) => {
          if (response.data.code === 200) {
            this.success();
          } else {
            this.fail(response.data.data);
          }
        });
      } else {
        this.fail("error submit!!");
      }
    },
  },
};
</script>
<style scoped>
.submit {
  margin-top: -1em;
}
@media (min-width: 50em) {
  .custom-word-form {
    width: 40%;
    margin: auto;
  }
}
</style>