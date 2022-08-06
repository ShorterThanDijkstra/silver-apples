<template>
  <div class="title">
    <h2>{{ `${username}' activities` }}</h2>
  </div>
  <div class="activities">
    <UserWordActivity :username="username"></UserWordActivity>
  </div>
  <div class="info">
    <el-alert
      description="Only counts words of the book"
      type="info"
      :closable="false"
      show-icon
      effect="dark"
    />
  </div>

  <div class="grid">
    <UserStatisticWordCard
      v-for="(practice, index) in practicesOfUser"
      :key="index"
      :practice="practice"
    >
    </UserStatisticWordCard>
  </div>
</template>

<script>
import UserWordActivity from "@/components/UserStatisticWordActivities.vue";
import UserStatisticWordCard from "@/components/UserStatisticWordCard.vue";

export default {
  props: {
    username: String,
  },
  data() {
    return { practicesOfUser: [] };
  },
  components: { UserWordActivity, UserStatisticWordCard },

  methods: {
    loadPracticesOfUser() {
      const url = `${this.$backend}/user-statistic/words/${this.username}`;
      this.$http.get(url).then((response) => {
        this.practicesOfUser = response.data.data;
      });
    },
  },
  mounted() {
    this.loadPracticesOfUser();
  },
  created() {
    this.$watch(
      () => this.$route.params,
      (toParams, previousParams) => {
        if (this.$route.name === "UserStatistic") {
          this.$router.go();
        }
      }
    );
  },
};
</script>

<style scoped>
.activities {
  margin: 1em 0 2em 0;
}
.info{
  margin-bottom: 0.5em;
}
</style>