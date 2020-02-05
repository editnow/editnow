<template>
  <div id="app">
    <b-container id="app-container">
      <b-row class="justify-content-center container-header" style="height: 10%;">
        <logo-component></logo-component>
      </b-row>
      <b-row class="align-items-center justify-content-center" style="height: 75%;">
        <router-view></router-view>
      </b-row>
      <b-row class="justify-content-center align-items-end"  style="height: 15%;">
        <span class="m-3 font-italic" style="font-weight: bold;">{{ appBuildInfo }}</span>
      </b-row>
    </b-container>
    <div id="tools-menu">
      <tool-cards-accordion id="tool-cards-accordion"></tool-cards-accordion>
    </div>
    <div id="console-menu">
      <console-menu
              console-menu-title="Changes history"
      ></console-menu>
    </div>
  </div>
</template>

<script>

import ToolCardsAccordion from "./view/ToolCardsMenuView";
import ConsoleMenu from "./view/HistoryMenuView";
import appInfoApi from './components/api/app-info-api.js';
import LogoComponent from "./components/LogoComponent";

export default {
  name: 'app',
  components: {LogoComponent, ConsoleMenu, ToolCardsAccordion},
  data () {
    return {
      appBuildInfo: "local"
    }
  },
  methods: {
  },
  created() {
    appInfoApi.getAppBuildInfo()
            .then(response => {
              this.appBuildInfo = response.data;
            })
            .catch(error => {
              console.log(error)
            });
  },
  beforeCreate() {
    this.$store.dispatch('loadUUIDFromCookie')
            .catch((state) => {
              this.$store.dispatch('generateUUID');
            })
  }
}
</script>

<style lang="scss">
  html, body, #app, #app-container, #app-row {
    height: 100%;
  }
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  #tools-menu {
    position: fixed;
    top: 10%;
    left: -2px;
    height: 75%;
    width: 200px;
  }

  #tool-cards-accordion {
    overflow-y: auto;
    max-height: 100%;
  }

  #console-menu {
    position: fixed;
    top: 10%;
    right: -2px;
    height: 75%;
    width: 300px;
  }
</style>
