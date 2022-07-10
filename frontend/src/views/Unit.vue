<template>
  <div>
    <div class="head">
      <h1 class="title">Unit {{ currentUnit.index }}</h1>
      <div class="flex">

        <button class="button" role="button" @click="specialSection">
          special section
        </button>
        <button @click="exercise" class="button button-right" role="button">
          quizzes
        </button>

      </div>

      <hr>
    </div>
    <div class="grid">
      <RootCard
          v-for="(root, index) in currentUnit.roots"
          :key="index"
          :root="root"
          :rootIndex="index"
      ></RootCard>
    </div>
    <div class="flex">
      <button @click="previous" class="gray-button" role="button">
        &#8592; previous
      </button>
      <button @click="next" class="gray-button button-right" role="button">
        next &#8594;
      </button>
    </div>
  </div>
</template>

<script>
import RootCard from "@/components/RootCard.vue";
import {mapGetters, mapMutations} from "vuex";

export default {
  components: {RootCard},
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
    previous() {
      let unit = this.$route.params.unit
      unit = parseInt(unit) - 1
      this.go(unit)
    },
    next() {
      let unit = this.$route.params.unit
      unit = parseInt(unit) + 1
      this.go(unit)
    },
    go(unit) {
      if (unit >= 1 && unit <= 30) {
        this.$router.push({
          name: "Unit",
          params: {unit: unit},
        });
      }
    },
    exercise() {
      this.$router.push({
        name: "Exercise",
        params: {unit: this.$route.params.unit},
      });
    },
    specialSection() {
      this.$router.push({
        name: "SpecialSection",
        params: {unit: this.$route.params.unit},
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
.gray-button {
  margin-top: 1em;
  width: 8em;
  transition: all .5s ease;
  color: #999999;
  border: 3px solid white;
  font-family: 'Montserrat', sans-serif;
  text-transform: uppercase;
  text-align: center;
  line-height: 1;
  font-size: 17px;
  background-color: #555555;
  padding: 10px;
  outline: none;
  border-radius: 10px;
}

.button-right {
  margin-left: auto;
}



</style>