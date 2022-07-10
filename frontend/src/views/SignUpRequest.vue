<template>
  <div class="submit-box">
    <form @submit.prevent="handle" class="form">
        <label class="label">Email :</label>
        <input class="submit-input" type="email" v-model="email" required />
        <div v-if="emailPatternError" class="error">
          {{ emailPatternError }}
        </div>
        <br />
        <div>
          <button class="submit button" type="submit">Create New Account</button>
        </div>
    </form>
  </div>
</template>
<script>
import { ElMessage } from "element-plus";
export default {
  data() {
    return {
      email: "",
    };
  },

  computed: {
    emailPatternError() {
      return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.email)
        ? ""
        : "invalid email address";
    },
  },
  methods: {
    fail(msg) {
      ElMessage.error(msg);
    },
    success() {
      ElMessage({
        message: "请前往邮箱查收邮件。",
        type: "success",
      });
      this.email = "";
    },
    handle() {
      if (this.emailPatternError === "") {
        this.$http
          .get(this.$backend + "/user/register/email-exist/" + this.email)
          .then((response) => {
            if (!response.data.data) {
              this.registerRequest();
            } else {
              this.fail("this email has already been taken");
            }
          });
      }
    },
    registerRequest() {
      const url = this.$backend + "/user/register/request/" + this.email;
      this.$http.get(url).then((response) => {
        if (response.data.code !== 200) {
          this.fail(response.data.data);
        } else {
          this.success();
        }
      });
    },
  },
};
</script>
<style scoped>
</style>