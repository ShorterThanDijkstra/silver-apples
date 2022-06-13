<template>
  <div class="center">
    <div style="margin-top: 1rem">
      <h2 style="display: inline; margin-left: 0.6rem">
        {{ currentRootCache.name }}
      </h2>

      <button @click="previous" style="margin-left: 5.9rem" class="button">
        previous
      </button>
      <button @click="next" style="margin-left: 6.5rem" class="button">
        next
      </button>

      <br />
      <br />
    </div>
    <div>
      <WordCard
        class="card"
        v-for="(word, index) in currentRootCache.words"
        :key="index"
        :word="word"
      ></WordCard>
    </div>
  </div>
</template>

<script>
import WordCard from "@/components/WordCard.vue";
import { mapGetters, mapMutations } from "vuex";
export default {
  props: ["name"],
  components: { WordCard },
  computed: {
    ...mapGetters(["currentRootCache"]),
  },
  methods: {
    ...mapMutations(["incCurrentRootIndex", "decCurrentRootIndex"]),
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
        params: { name: this.currentRootCache.name },
      });
    },
  },
};
</script>

<style>
.card {
  width: 45%;
  display: inline-grid;
  margin: 10px;
  height: 300px;
}
</style>