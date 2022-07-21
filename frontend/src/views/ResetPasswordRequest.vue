<template>
  <div class="submit-box">
    <form @submit.prevent="resetPasswordRequest" class="form">
      <label class="label">Email :</label>
      <input class="submit-input" type="email" v-model="email" required />
      <div v-if="emailError" class="error">
        {{ emailError }}
      </div>
      <button class="submit button" type="submit">Reset Password</button>
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
    emailError() {
      return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.email)
        ? ""
        : "invalid email address";
    },
  },
  methods: {
    fail(msg) {
      ElMessage.error(msg);
    },
    success(data) {
      this.email = "";
      ElMessage.success(data);
    },
    resetPasswordRequest() {
      if (this.emailError === "") {
        this.$http
          .get(this.$backend + `/user/change-password/request/${this.email}`)
          .then((response) => {
            if (response.data.code !== 200) {
              this.fail(response.data.data);
            } else {
              this.success(response.data.data);
            }
          });
      }
    },
  },
};
</script>
<style scoped>
</style>