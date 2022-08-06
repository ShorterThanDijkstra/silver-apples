<template>
  <div class="title">
    <h2>{{ spell }}</h2>
  </div>
  <div class="flex">
    <button class="button" @click="formVisible = true">Make a sentence</button>
    <button class="button button-right" type="button" @click="$router.back()">
      &#8592; back
    </button>
  </div>
  <el-dialog v-model="formVisible" :title="title">
    <el-input
      class="text"
      v-model="sentence"
      :autosize="{ minRows: 3 }"
      type="textarea"
      placeholder="Please input"
    />
    <button class="button" @click="submit">submit</button>
  </el-dialog>
  <hr />
  <div class="grid">
    <UserPracticeWordCard
      v-for="(practice, index) in practices"
      :key="index"
      :practice="practice"
    >
    </UserPracticeWordCard>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { ElMessage } from "element-plus";
import UserPracticeWordCard from "@/components/UserPracticeWordCard.vue";
export default {
  components: {
    UserPracticeWordCard,
  },

  props: ["spell"],
  data() {
    return { sentence: "", formVisible: true, practices: [] };
  },
  computed: {
    ...mapGetters(["findWordInCurrentRoot", "userCache"]),
    title() {
      const word = this.findWordInCurrentRoot(this.spell);
      return this.spell + ":\n" + word.explain;
    },
  },
  methods: {
    fail(msg) {
      ElMessage.error(msg);
    },
    success(practice) {
      ElMessage({
        message: "submit successfully",
        type: "success",
        duration: 1000,
      });
      this.sentence = "";
      this.formVisible = false;
      practice.user = this.userCache;
      practice.createTime = new Date().toISOString();
      this.practices.push(practice);
    },
    getPracticesByWordId(wordId) {
      const url = this.$backend + "/book/word-practice/practices/" + wordId;
      this.$http.get(url).then((response) => {
        this.practices = response.data.data;
      });
    },
    submit() {
      const word = this.findWordInCurrentRoot(this.spell);
      const practice = { word: word, sentence: this.sentence };
      const url = this.$backend + "/book/word-practice/practice";
      this.$http.post(url, practice).then((response) => {
        if (response.data.code === 200) {
          this.success(practice);
        } else {
          this.fail(response.data.data);
        }
      });
    },
  },
  mounted() {
    const word = this.findWordInCurrentRoot(this.spell);
    this.getPracticesByWordId(word.id);
  },
};
</script>

<style scoped>
.grid {
  display: grid;
  grid-template-columns: 1fr;
  grid-gap: 1em;
  width: 97%;
  margin: auto;
}

@media (min-width: 35em) {
  .grid {
    display: grid;
    grid-template-columns: 1fr;
    grid-gap: 1em 1em;
    /* width: 80%; */
    margin: auto;
  }
}
@media (min-width: 35em) {
  .grid {
    display: grid;
    grid-template-columns: 1fr;
    grid-gap: 1em 1em;
    width: 80%;
    /* margin-left: 0; */
    margin: auto;
  }
}
.text {
  margin-top: -1.5em;
}
.button.button-right{
  margin-left: auto;
}
</style>