<template>
  <el-card class="word-card">
    <div class="card-header flex">
      <div>
        {{ word.spell }}
      </div>
      <span class="date">
        {{ new Date(word.createTime).toLocaleString() }}
      </span>
      <el-button class="right" type="danger" @click="deleteWord()"
        >delete</el-button
      >
    </div>
    <hr />
    <div class="card-body">
      <div>
        <span>explanation:</span>
        <p class="content">
          {{ word.explanation }}
        </p>
        <hr />
      </div>
      <div>
        <span>sentence:</span>
        <p class="content">
          {{ word.sentence }}
        </p>
        <hr />
      </div>
      <div>
        <span>note:</span>
        <p class="content">{{ word.note }}</p>
        <hr />
      </div>
    </div>
  </el-card>
</template>

<script >
import { ElMessage, ElMessageBox } from "element-plus";
export default {
  props: {
    word: Object,
  },
  methods: {
    fail(msg) {
      ElMessage.error(msg);
    },
    success() {
      ElMessage({
        message: "delete word successfully",
        type: "success",
        duration: 1000,
      });
      this.$router.go();
    },
    doDelete() {
      const url = this.$backend + "/user/custom-word/" + this.word.id;
      this.$http.del(url).then((response) => {
        if (response.data.code === 200) {
          this.success();
        } else {
          this.fail(response.data.data);
        }
      });
    },

    deleteWord() {
      ElMessageBox.confirm(
        "word '" + this.word.spell + "' will permanently be deleted. Continue?",
        "Warning",
        {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      )
        .then(() => {
          this.doDelete();
        })
        .catch(() => {});
    },
  },
};
</script>
<style scoped>
.word-card {
  font-size: 1.3rem;
  color: #555;
}
.content {
  font-size: 1rem;
  margin-top: 0.5em;
  color: #222;
}
.right {
  margin-left: auto;
}
.date {
  margin-left: 1em;
  color: #888;
}
</style>