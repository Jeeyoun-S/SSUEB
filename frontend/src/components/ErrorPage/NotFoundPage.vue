<template>
  <div class="main-page">
    <div class="main-center">
      <img
        class="errorImg"
        :src="require('@/assets/fail/universeNotFound.gif')"
      />
    </div>
  </div>
  <!-- <div class="errorImg">
    <img :src="require('@/assets/fail/universeNotFound.gif')" />
  </div> -->
</template>

<style scoped>
.errorImg {
  width: 120%;
  height: 120%;
  margin-left: 30%;
}
.main-page {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
}
.main-center {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;

  width: 730px;
  height: 654.4px;
  margin-right: 40px;
}
.main-center .main-center-item {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

  width: 730px;
  height: 200px;
}
.main-center .main-center-item img {
  width: 200px;
  height: 200px;

  border-radius: 10px;
  object-fit: cover;
}
.main-center .main-center-item .explain {
  width: 500px;
  height: 200px;

  border-radius: 10px;
  padding: 35px;
}
.main-center .main-center-item .explain h2 {
  font-size: 30px;
  font-family: "Patua One", cursive;
  font-weight: lighter;
}
.main-center .main-center-item .explain.one {
  background-color: #e8ebf6;
}
.main-center .main-center-item .explain.two {
  color: white;
  background-color: #3d6ec5;
}
.main-page .main-center .main-center-item .explain.three {
  background-color: #e3e3e3;
}
</style>
