<template>
  <div class="content">
    <div class="head">
      <h1 class="title">
        {{ currentRootCache.name }}
      </h1>
      <div class="flex">
        <button @click="previous" class="button">previous</button>
        <button @click="next" class="button">next</button>
        <button class="button button-right" type="button" @click="unit()">&#8592; back</button>

      </div>
    </div>
    <div class="grid">
      <WordCard
          v-for="(word, index) in currentRootCache.words"
          :key="index"
          :word="word"
      ></WordCard>
    </div>
  </div>
</template>

<script>
import WordCard from "@/components/WordCard.vue";
import {mapGetters, mapMutations, mapState} from "vuex";

export default {
  props: ["name"],
  components: {WordCard},
  computed: {
    ...mapGetters(["currentRootCache"]),
    ...mapState(["currentUnitIndex"])
  },
  methods: {
    ...mapMutations(["incCurrentRootIndex", "decCurrentRootIndex"]),
    unit() {
      this.$router.push({
        name: "Unit",
        params: {unit: this.currentUnitIndex + 1},
      });
    },

    previous() {
      this.decCurrentRootIndex();
      this.refresh();
    },
    next() {
      this.incCurrentRootIndex();
      this.refresh();
    },
    refresh() {
      this.$router.push({
        name: "Root",
        params: {name: this.currentRootCache.name},
      });
    },
  },
};
</script>

<style scoped>
.head {
  font-size: 0.5em;
  margin-bottom: 3em;
}

.button.button-right {
  margin-left: auto;
}

</style>