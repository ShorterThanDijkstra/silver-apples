<template>
  <div class="center">
    <el-card class="article">
      <div
        v-for="(content, index) in theIntro.paragraphs"
        :key="index"
        class="text"
      >
        <p class="paragraph">
          {{ content }}
        </p>
      </div>
    </el-card>
  </div>
  <div></div>
</template>

<script>
import { mapMutations, mapState } from "vuex";
export default {
  computed: {
    ...mapState(["theIntro"]),
  },
  methods:{
    ...mapMutations(["setTheIntro"])
  },
  mounted() {
    if (!this.theIntro) {
      const url = this.$backend + "/book/intro";
      this.$http.get(url).then((response) => {
        this.setTheIntro(response.data.data);
      });
    }
  },
};
</script>

<style scoped>
.text {
  font-size: 22px;
}

.article {
  width: 60%;
  margin-left: 19%;
  margin-top: 2rem;
  height: auto;
}
.paragraph {
  margin-left: 4rem;
  margin-right: 4rem;
  margin-bottom: 3rem;
}
</style>