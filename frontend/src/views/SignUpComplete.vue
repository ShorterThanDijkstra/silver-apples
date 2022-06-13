<template>
  <div class="box">
    <form @submit.prevent="handleSubmit">
      <div class="center">
        <label>User Name :</label>
        <input
          type="text"
          v-model="username"
          placeholder=" at least 5 characters, must be unique"
          required
        />
        <div v-if="usernameLengthError" class="error">
          {{ usernameLengthError }}
        </div>
        <div v-if="usernameUniqueError" class="error">
          {{ usernameUniqueError }}
        </div>

        <label>Password :</label>
        <input
          type="password"
          v-model="password"
          required
          placeholder=" at least 10 characters"
        />
        <div v-if="passwordError" class="error">{{ passwordError }}</div>

        <label>Confirm Your Password :</label>
        <input type="password" v-model="confirmPassword" required />
        <div v-if="confirmPasswordError" class="error">
          {{ confirmPasswordError }}
        </div>

        <br />
        <div class="button">
          <button class="submit" type="submit">Sign up here</button>
        </div>
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
<style scoped>
form {
  max-width: 600px;
  margin: 30px auto;
  background: #fff;
  text-align: left;
  padding: 20px;
  border-radius: 10px;
}

label {
  color: black;
  display: inline-block;
  margin: 25px 0 15px;
}
.box {
  margin-top: 5rem;
  font-size: larger;
}
.center {
  width: 80%;
  margin-left: 10%;
}
input,
select {
  display: block;
  padding: 10px 0px;
  width: 100%;
  box-sizing: bordre-box;
  border: 2px solid;
  border-radius: 10px;
  font-size: larger;
  /* border-bottom: 1px solid #ddd; */
  color: #555;
}

button {
  background: rgb(7, 24, 7);
  border: 0;
  padding: 10px 20px;
  color: white;
  border-radius: 20px;
}

.submit {
  text-align: center;
}

.error {
  color: #ff0000;
  margin-top: 10px;
  font-size: 1em;
  font-weight: bold;
}
</style>