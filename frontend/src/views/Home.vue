<template>
  <div class="home">
    <div class="content">
      <div class="about-book">
        <h3>about the book</h3>
        <img src="/book.webp" alt="book-cover" />
        <p>
          The ideal book for people who want to increase their word power.
          Thorough coverage of 1,200 words and 240 roots while introducing 2,300
          words. The Vocabulary Builder is organized by Greek and Latin roots
          for effective study with nearly 250 new words and roots. Includes
          quizzes after each root discussion to test progress. A great study aid
          for students preparing to take standardized tests.
        </p>
      </div>
      <div class="donate">
        <h3>donate</h3>
        <div class="methods">
          <div>
            <img src="/alipay.jpg" alt="alipay" />
            <span>Alipay</span>
          </div>
        </div>
      </div>
      <div class="source-code">
        <h3>source code</h3>
        <a class="button" href="https://github.com/maerd-zinbieL/silver-apples">
          github
        </a>
      </div>
    </div>
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
.about-book {
  font-size: 1.3rem;
}
.source-code .button {
  width: 6em;
}
.about-book > img {
  max-width: 9em;
  float: right;
  margin-left: 1em;
  height: auto;
}
@media (min-width: 50em) {
  .about-book > img {
    max-width: 7em;
    float: right;
    margin-right: 1em;
    height: 60%;
  }
}
/*
@media (min-width: 60em) {
  .about-book > img {
    max-width: 20%;
    margin-left: auto;
    height: auto;
  }
} */
.methods {
  display: flex;
  align-items: center;
}
.methods img {
  max-width: 9em;
  height: auto;
}
.methods span {
  display: block;
  align-self: middle;
  font-size: 0.8em;
  text-align: center;
}
.warning-msg {
  /* position: absolute; */
  /* bottom: 0; */
  margin-top: 2em;
  padding: 0.5em;
  border-radius: 3px 3px 3px 3px;
  color: #9f6000;
  font-size: 1rem;
}
</style>
