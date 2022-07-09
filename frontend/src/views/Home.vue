<template>
  <div class="home">
    <div class="content"></div>
    <div class="warning-msg">
      <p>
        本网站内容仅供学习使用，如有侵权，请联系<span
          >buildvocabulary@163.com</span
        >
      </p>
    </div>
  </div>
</template>
<style scoped>
.home {
  display: flex;
  flex-direction: column;
  height: 90%;
}
.content {
  flex-basis: 15em;
}
.warning-msg {
  margin-top: auto;
  padding: 0.5em;
  border-radius: 3px 3px 3px 3px;
  color: #9f6000;
  font-size: 1rem;
}
</style>
