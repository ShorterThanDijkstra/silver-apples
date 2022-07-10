<template>
  <div class="submit-box">
    <form @submit.prevent="handleSubmit" class="form">
        <label class="label">User Name :</label>
        <input
          type="text"
          v-model="username"
          class="submit-input"
          placeholder=" at least 5 characters, must be unique"
          required
        />
        <div v-if="usernameLengthError" class="error">
          {{ usernameLengthError }}
        </div>
        <div v-if="usernameUniqueError" class="error">
          {{ usernameUniqueError }}
        </div>

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
        <input type="password" class="submit-input"  v-model="confirmPassword" required />
        <div v-if="confirmPasswordError" class="error">
          {{ confirmPasswordError }}
        </div>

        <br />
        <div>
          <button class="submit button" type="submit">Sign up here</button>
        </div>
    </form>
  </div>
</template>
<script>
import { ElMessage } from "element-plus";
export default {
  data() {
    return {
      username: "",
      password: "",
      confirmPassword: "",
      usernameUniqueError: "",
    };
  },

  computed: {
    passwordError() {
      return this.password.length >= 10
        ? ""
        : "Password should be at least 10 characters long!";
    },
    usernameLengthError() {
      return this.username.length >= 5
        ? ""
        : "Username should be at least 5 characters";
    },
    confirmPasswordError() {
      return this.password === this.confirmPassword
        ? ""
        : "Passwords must match";
    },
  },
  methods: {
    fail(msg) {
      ElMessage.error(msg);
    },
    success() {
      ElMessage({
        message: "注册成功，请登录",
        type: "success",
      });
      this.$router.push({
        name: "Login",
      });
    },
    handleSubmit() {
      if (
        this.passwordError === "" &&
        this.usernameLengthError === "" &&
        this.confirmPasswordError === ""
      ) {
        this.$http
          .get(this.$backend + "/user/register/username-exist/" + this.username)
          .then((data) => {
            if (data.data.data) {
              this.fail("This username has already been taken");
            } else {
              this.register();
            }
          });
      }
    },
    register() {
      const info = {
        username: this.username,
        password: this.password,
        confirmedPassword: this.confirmPassword,
        token: this.$route.query.token,
      };
      this.$http
        .post(this.$backend + "/user/register/complete", info)
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
        name: "RegisterRequest",
      });
    }
  },
};
</script>