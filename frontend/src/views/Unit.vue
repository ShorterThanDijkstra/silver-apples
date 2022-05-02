<template>
  <div v-if="roots !== null">
    <div class="center">
      <h1>Unit {{ id }}</h1>
      <RootCard
        class="card"
        v-for="(root, index) in roots"
        :key="index"
        :root="root"
      ></RootCard>
    </div>
  </div>
</template>

<script>
import RootCard from "@/components/RootCard.vue";
export default {
  components: { RootCard },
  props: {
    id: {
      type: String,
      default: "1",
    },
  },
  data() {
    return { roots: null };
  },
  methods: {
    getData(id) {
      const url = this.$backend + "/roots/" + id;
      this.axios
        .get(url)
        .then((response) => (this.roots = response.data.data))
        .catch(function (error) {
          console.log(error);
        });
    },
  },
  mounted() {
    this.getData(this.id);
    this.$watch(
      () => this.$route.params,
      (toParams, _) => {
        this.getData(toParams.id);
      }
    );
  },
};
</script>

<style>
.card {
  width: 45%;
  display: inline-grid;
  margin: 10px;
  height: 300px;
  border: 1px solid #ccc !important;
  padding: 14px;
  border-radius: 16px !important;
}
.center {
  margin-left: 5%;
  font-size: 22px;
  /* margin-right: 5%; */
}
</style>