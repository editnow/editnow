// vue.config.js
module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8098',
        ws: true,
        changeOrigin: true
      }
    }
  },
  outputDir: 'target/dist',
  assetsDir: 'static'
};
