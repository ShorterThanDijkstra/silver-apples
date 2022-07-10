<template>
    <el-card class="article">
      <div
        v-for="(content, index) in intro.paragraphs"
        :key="index"
      >
        <p class="paragraph">
          {{ content }}
        </p>
      </div>
    </el-card>
</template>

<script>
import { mapMutations, mapState } from "vuex";
export default {
  data() {
    return {
      intro: "",
    };
  },
  computed: {
    ...mapState(["theIntro"]),
  },
  methods: {
    ...mapMutations(["setTheIntro"]),
  },
  mounted() {
    if (!this.theIntro) {
      const url = this.$backend + "/book/intro";
      this.$http.get(url).then((response) => {
        this.intro = response.data.data;
        this.setTheIntro(response.data.data);
      });
    } else {
      this.intro = this.theIntro;
    }
  },
};
</script>

<style scoped>
.article {
  height: auto;
  margin-top: 1em;
}

.paragraph {
  margin: 1em 0.1em;
  font-size: 1rem;
  line-height: 1.5em;
}
@media (min-width: 35em){
  .paragraph {
    margin: 1.5em;
    font-size: 1.5rem;
    line-height: 1.5em;
  }
}
</style>