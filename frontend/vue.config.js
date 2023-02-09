const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,

  pluginOptions: {
    vuetify: {
      // https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
    },
  },
  // # CORS 오류 해결을 위하여 proxy 추가
  devServer: {
    // port: 8080,
    // proxy: "http://localhost:8080/api",
    // proxy: process.env.VUE_APP_API_BASE_URL,
    proxy: {
      "/api": {
        target: "http://localhost:5000",
        changeOrigin: true,
        onProxyReq: function (request) {
          request.setHeader("origin", "http://localhost:5000");
        },
      },
    },
  },
  outputDir: "C:/Users/SSAFY/S08P12A801/backend/src/main/resources/static",
});
