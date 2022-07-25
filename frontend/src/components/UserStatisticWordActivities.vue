<template>
  <div class="calendar">
    <CalendarHeatmap
      :values="activeDays"
      :end-date="endDate"
      no-data-text=" "
      :round="2"
      :max="20"
      tooltip-unit="words"
      :range-color="['#ebedf0', '#9be9a8', '#40c463', '#30a14e', '#216e39']"
    ></CalendarHeatmap>
  </div>
</template>

<script>
import { CalendarHeatmap } from "vue3-calendar-heatmap";

export default {
  props: {
    username: String,
  },
  data() {
    return {
      activeDays: [],
      endDate: new Date().toISOString().split("T")[0],
    };
  },

  components: {
    CalendarHeatmap,
  },
  mounted() {
    const url =
      `${this.$backend}/user-statistic/word-activities/${this.username}` 
    this.$http.get(url).then((response) => {
      this.activeDays = response.data.data;
    });
  },
};
</script>

<style scoped>
.calendar {
  font-size: 0.7rem;
}
</style>