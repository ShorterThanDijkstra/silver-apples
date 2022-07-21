<template>
  <div class="submit-box">
    <form @submit.prevent="handleSubmit" class="form">

      <label class="label">Password :</label>
      <input
        type="password"
        v-model="password"
        class="submit-input"
        required
        placeholder=" at least 10 characters"
      />
      <div v-if="passwordError" class="error">{{ passwordError }}</div>

      <label class="label">Confirm Your Password :</label>
      <input
        type="password"
        class="submit-input"
        v-model="confirmPassword"
        required
      />
      <div v-if="confirmPasswordError" class="error">
        {{ confirmPasswordError }}
      </div>

      <br />
      <div>
        <button class="submit button" type="submit">Reset</button>
      </div>
    </form>
  </div>
</template>
<script>
import { ElMessage } from "element-plus";
import { mapMutations } from "vuex";
export default {
  data() {
    return {
      password: "",
      confirmPassword: "",
    };
  },

  computed: {
    passwordError() {
      return this.password.length >= 10
        ? ""
        : "Password should be at least 10 characters long!";
    },
  
    confirmPasswordError() {
      return this.password === this.confirmPassword
        ? ""
        : "Passwords must match";
    },
  },
  methods: {
    ...mapMutations(["clearUserToken"]),
    fail(msg) {
      ElMessage.error(msg);
    },
    success() {
      ElMessage({
        message: "修改密码成功",
        type: "success",
      });
      this.clearUserToken();
      this.$router.push({
        name: "Login",
      });
    },
    handleSubmit() {
      if (this.passwordError === "" && this.confirmPasswordError === "") {
        this.resetPassword();
      }
    },
    resetPassword() {
      const info = {
        password: this.password,
        confirmedPassword: this.confirmPassword,
        token: this.$route.query.token,
      };
      this.$http
        .post(this.$backend + "/user/change-password/complete", info)
        .then((response) => {
          if (response.data.code === 200) {
            this.success();
          } else {
            this.fail(response.data.data);
          }
        });
    },
  },
  mounted() {
    if (!this.$route.query.token) {
      this.fail("invalid request");
      this.$router.push({
        name: "ResetPasswordRequest",
      });
    }
  },
};
</script>