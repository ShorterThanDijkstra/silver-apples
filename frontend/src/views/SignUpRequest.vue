<template>
  <div class="box">
    <form @submit.prevent="handle">
      <div class="center">
        <label>Email :</label>
        <input type="email" v-model="email" required />
        <div v-if="emailPatternError" class="error">
          {{ emailPatternError }}
        </div>
        <br />
        <div class="button">
          <button class="submit" type="submit">Create New Account</button>
        </div>
        <div></div>
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
              this.fail("this email has aready been taken");
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