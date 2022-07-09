<template>
  <div class="head">
    <h1>Unit {{ currentUnit.index }}</h1>
    <div class="flex">
      <button class="button" role="button" @click="specialSection">
        special section
      </button>
      <button @click="exercise" class="button button-right" role="button">quizzes</button>
    </div>
  </div>
  <div class="grid">
    <RootCard
      class="card"
      v-for="(root, index) in currentUnit.roots"
      :key="index"
      :root="root"
      :rootIndex="index"
    ></RootCard>
  </div>
</template>

<script>
import RootCard from "@/components/RootCard.vue";
import { mapGetters, mapMutations } from "vuex";
export default {
  components: { RootCard },
  data() {
    return {
      currentUnit: {},
    };
  },
  computed: {
    ...mapGetters(["currentUnitCache"]),
  },
  methods: {
    ...mapMutations(["cacheCurrentUnit", "setCurrentUnitIndex"]),
    exercise() {
      this.$router.push({
        name: "Exercise",
        params: { unit: this.$route.params.unit },
      });
    },
    specialSection() {
      this.$router.push({
        name: "SpecialSection",
        params: { unit: this.$route.params.unit },
      });
    },
    loadContent(unitIndex) {
      this.setCurrentUnitIndex(unitIndex);
      if (!this.currentUnitCache) {
        const url = this.$backend + "/book/units/" + unitIndex;
        this.$http.get(url).then((response) => {
          this.currentUnit = response.data.data;
          this.cacheCurrentUnit(response.data.data);
        });
      } else {
        this.currentUnit = this.currentUnitCache;
      }
    },
  },
  created() {
    this.$watch(
      () => this.$route.params,
      (toParams, previousParams) => {
        if (this.$route.name === "Unit") {
          this.loadContent(toParams.unit);
        }
      }
    );
    this.loadContent(this.$route.params.unit);
  },
};
</script>

<style scoped>
.head {
  font-size: 0.8rem;
}
.flex{
  display: flex;
}
.button-right{
  margin-left: auto;
}
</style>