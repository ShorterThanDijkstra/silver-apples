<template>
  <el-card class="word-card">
    <div class="card-header flex">
      <el-avatar class="avatar" src="/favicon.png" />
      <span>
        <a class="user">
          {{ practice.user.username }}
        </a>
      </span>
      <span class="right">
        {{ new Date(practice.createTime).toLocaleString() }}
      </span>
    </div>
    <hr />
    <div class="card-body">
      {{ practice.sentence }}
    </div>
  </el-card>
</template>

<script lang="ts" setup>
const props = defineProps<{
  practice: Object;
}>();
</script>
<style scoped>
.right {
  margin-left: auto;
  margin-top: 0;
}
.avatar {
  margin-right: 1em;
}
.user {
  color: #555555;
}
</style>