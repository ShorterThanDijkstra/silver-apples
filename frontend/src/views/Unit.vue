<template>
    <div class="center">
      <h1 class="title">
        <span>Unit {{ currentUnit.index }}</span>
        <button
          v-if="currentUnit.index < 10"
          style="margin-left: 11.4rem"
          class="button"
          role="button"
          @click="specialSection"
        >
          special section
        </button>
        <button
          v-else
          style="margin-left: 10rem"
          class="button"
          role="button"
          @click="specialSection"
        >
          special section
        </button>

        <button
          style="margin-left: 16rem"
          @click="exercise"
          class="button"
          role="button"
        >
          quizzes
        </button>
      </h1>
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
}
.title {
  margin-left: 1rem;
}

.button {
  display: flex;
  justify-content: right;
  appearance: none;
  background-color: #2ea44f;
  border: 1px solid rgba(27, 31, 35, 0.15);
  border-radius: 6px;
  box-shadow: rgba(27, 31, 35, 0.1) 0 1px 0;
  box-sizing: border-box;
  color: #fff;
  cursor: pointer;
  display: inline-block;
  font-family: -apple-system, system-ui, "Segoe UI", Helvetica, Arial,
    sans-serif, "Apple Color Emoji", "Segoe UI Emoji";
  font-size: 14px;
  font-weight: 600;
  line-height: 20px;
  padding: 6px 16px;
  position: relative;
  text-align: center;
  text-decoration: none;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  vertical-align: middle;
  white-space: nowrap;
}

.button:focus:not(:focus-visible):not(.focus-visible) {
  box-shadow: none;
  outline: none;
}

.button:hover {
  background-color: #2c974b;
}

.button:focus {
  box-shadow: rgba(46, 164, 79, 0.4) 0 0 0 3px;
  outline: none;
}

.button:disabled {
  background-color: #94d3a2;
  border-color: rgba(27, 31, 35, 0.1);
  color: rgba(255, 255, 255, 0.8);
  cursor: default;
}

.button:active {
  background-color: #298e46;
  box-shadow: rgba(20, 70, 32, 0.2) 0 1px 0 inset;
}
</style>