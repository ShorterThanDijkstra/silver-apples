<template>
  <div class="center">
    <h1>{{ name }}</h1>
    <WordCard
      class="card"
      v-for="(word, index) in words"
      :key="index"
      :word="word"
    ></WordCard>
  </div>
</template>

<script>
import WordCard from "@/components/WordCard.vue";
export default {
  components: { WordCard },
  props: {
    id: {
      type: String,
      required: true,
    },
    name: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      words: null,
    };
  },
  methods: {
    getData(id) {
      const url = this.$backend + "/words/" + id;
      this.axios
        .get(url)
        .then((response) => (this.words = response.data.data))
        .catch(function (error) {
          console.log(error);
        });
    },
  },
  mounted() {
    this.getData(this.id);
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