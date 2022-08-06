<template>
  <div class="flex">
    <button class="button" @click="formVisible = true">Add your word</button>
    <!-- <button class="button button-right" type="button" @click="$router.back()">
      &#8592; back
    </button> -->
  </div>
  <el-dialog v-model="formVisible" title="add custom words">
    <UserCustomWordForm
      :formVisible="formVisible"
      @formNotVisible="formNotVisible"
      @addCustomWord="addCustomWord"
    ></UserCustomWordForm>
  </el-dialog>
  <hr />
  <div class="grid">
    <UserCustomWordCard
      v-for="(word, index) in customWords"
      :key="index"
      :word="word"
    ></UserCustomWordCard>
  </div>
</template>

<script>
import UserCustomWordForm from "@/components/UserCustomWordForm.vue";
import UserCustomWordCard from "@/components/UserCustomWordCard.vue";
export default {
  components: { UserCustomWordForm, UserCustomWordCard },
  data() {
    return {
      formVisible: false,
      customWords: [],
    };
  },
  methods: {
    formNotVisible() {
      this.formVisible = false;
    },
    addCustomWord() {
      this.$router.go();
    },

    getCustomWords() {
      const url = this.$backend + "/user/custom-word/";
      this.$http.get(url).then((response) => {
        this.customWords = response.data.data;
      });
    },
  },
  mounted() {
    this.getCustomWords();
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
.button.button-right {
  margin-left: auto;
}
</style>