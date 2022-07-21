<template>
  <div class="submit-box">
    <form @submit.prevent="login" class="form">
      <label class="label">Email :</label>
      <input class="submit-input" type="email" v-model="email" required />
      <div v-if="emailError" class="error">
        {{ emailError }}
      </div>

      <label class="label">Password :</label>
      <input class="submit-input" type="password" v-model="password" required />
      <div v-if="passwordError" class="error">{{ passwordError }}</div>

      <div class="flex">
        <button class="submit button" type="submit">Sign in</button>
        <router-link
          class="button button-right"
          :to="{ name: 'ResetPasswordRequest' }"
        >
          Reset Password
        </router-link>
        <button class="button button-right" @click="newAccount" type="button">
          New
        </button>
      </div>
    </form>
  </div>
</template>
<script>
import { mapMutations } from "vuex";
import { ElMessage } from "element-plus";

export default {
  data() {
    return {
      email: "",
      password: "",
    };
  },

  computed: {
    passwordError() {
      return this.password.length >= 10
        ? ""
        : "Password should be at least 10 characters long!";
    },
    emailError() {
      return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.email)
        ? ""
        : "invalid email address";
    },
  },
  methods: {
    ...mapMutations(["setUserToken"]),
    fail(msg) {
      ElMessage.error(msg);
    },
    success(data) {
      ElMessage({
        message: "Congrats, you are successfully logged in.",
        type: "success",
      });
      this.setUserToken(data.token);
      if (this.$route.query.redirect) {
        this.$router.push(this.$route.query.redirect);
      } else {
        this.$router.push({
          name: "Home",
        });
      }
    },
    login() {
      if (this.passwordError === "" && this.emailError === "") {
        const login = {
          email: this.email,
          password: this.password,
        };
        this.$http
          .post(this.$backend + "/user/login", login)
          .then((response) => {
            if (response.data.code !== 200) {
              this.fail(response.data.data);
            } else {
              this.success(response.data.data);
            }
          });
      }
    },
    newAccount() {
      this.$router.push({
        name: "RegisterRequest",
      });
    },
  },
};
</script>
<style scoped>
</style>