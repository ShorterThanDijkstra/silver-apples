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

        <label>Nick Name :</label>
        <input type="text" v-model="nickname" required />
        <div v-if="nicknameError" class="error">
          {{ nicknameError }}
        </div>
        <label>Email :</label>
        <input type="text" v-model="email" required />
        <div v-if="emailError" class="error">
          {{ emailError }}
        </div>
        <div v-if="emailUniqueError" class="error">
          {{ emailUniqueError }}
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
import axios from "axios";
import { mapState } from "vuex";
import { ElMessageBox } from "element-plus";
export default {
  data() {
    return {
      email: "",
      username: "",
      nickname: "",
      password: "",
      confirmPassword: "",
      usernameUniqueError: "",
      emailUniqueError: "",
    };
  },

  computed: {
    ...mapState(["backend"]),
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
    nicknameError() {
      return this.nickname.trim() != "" ? "" : "nick name should not be empty";
    },
    emailError() {
      return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.email)
        ? ""
        : "invalid email address";
    },
  },
  methods: {
    checkEmail() {
      axios
        .get(this.backend + "/user/register/email/" + this.email)
        .then((data) => {
          if (data.data.data) {
            this.emailUniqueError = "This username has already been taken";
          } else {
            this.emailUniqueError = "";
          }
          this.checkUsername();
        });
    },
    checkUsername() {
      axios
        .get(this.backend + "/user/register/username/" + this.username)
        .then((data) => {
          if (data.data.data) {
            this.usernameUniqueError = "This username has already been taken";
          } else {
            this.usernameUniqueError = "";
          }
          this.register();
        });
    },
    register() {
      if (
        this.passwordError === "" &&
        this.usernameLengthError === "" &&
        this.confirmPasswordError === "" &&
        this.nicknameError === "" &&
        this.usernameUniqueError === "" &&
        this.emailUniqueError === ""
      ) {
        const user = {
          email: this.email,
          name: this.username,
          nickName: this.nickname,
          password: this.password,
          confirmPassword: this.confirmPassword,
        };
        axios
          .post(this.backend + "/user/register", user)
          .then((response) => this.finish(response.data));
      }
    },
    msg(msg, title, callback) {
      ElMessageBox.alert(msg, title, {
        confirmButtonText: "OK",
        callback: (action) => {
          callback();
        },
      });
    },
    finish(data) {
      if (data.code === 200) {
        this.msg(data.data, "success", () =>
          this.$router.push({ name: "Home" })
        );
      } else {
        this.msg(data.data, "error", () => this.$router.go());
      }
    },

    handleSubmit() {
      this.checkEmail();
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

.error {
  color: #ff0000;
  margin-top: 10px;
  font-size: 1em;
  font-weight: bold;
}
</style>