<template>
  <div class="box">
    <form @submit.prevent="login">
      <div class="center">
        <label>Email :</label>
        <input type="email" v-model="email" required />
        <div v-if="emailError" class="error">
          {{ emailError }}
        </div>

        <label>Password :</label>
        <input type="password" v-model="password" required />
        <div v-if="passwordError" class="error">{{ passwordError }}</div>

        <br />
        <div class="button">
          <button class="submit" type="submit">Sign in</button>
          <button class="new" @click="newAccount" type="button">
            create a new account
          </button>
        </div>
        <div></div>
      </div>
    </form>
  </div>
</template>
<script>
import axios from "axios";
import { mapState } from "vuex";
import { ElMessage } from "element-plus";
export default {
  data() {
    return {
      email: "",
      password: "",
    };
  },

  computed: {
    ...mapState(["backend"]),
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
    fail(msg) {
      ElMessage.error(msg);
    },
    success() {
      ElMessage({
        message: "Congrats, you are successfully logged in.",
        type: "success",
      });
      this.$router.push({
        name: "Home",
      });
    },
    login() {
      if (this.passwordError === "" && this.emailError === "") {
        const login = {
          email: this.email,
          password: this.password,
        };
        axios.post(this.backend + "/user/login", login).then((response) => {
          if (response.data.code !== 200) {
            this.fail(response.data.data);
          } else {
            this.success();
          }
        });
      }
    },
    newAccount() {
      this.$router.push({
        name: "Register",
      });
    },
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
.new {
  margin-left: 48%;
}
.error {
  color: #ff0000;
  margin-top: 10px;
  font-size: 1em;
  font-weight: bold;
}
</style>