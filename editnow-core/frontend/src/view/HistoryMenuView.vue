<template>
    <div class="consoleMenu">
        <h4 class="p-2 console-menu-title">
            {{ consoleMenuTitle }}
        </h4>
        <div class="m-2 historyDiv">
            <div v-for="history in histories"
                 :key="history.time"
                 class="m-1"
                 >
                <div>
                    {{ '[' + history.time + ']' }}
                </div>
                <div>
                    <span v-if="history.type === 'INPUT'">
                        <span class="font-weight-bold"> {{ history.readableName + ' '}}</span>
                        action called
                        <span v-if="history.parameters && history.parameters.length > 0">
                            {{ ' with parameters ' }}
                            <span v-for="(parameter, index) in history.parameters"
                                  :key="parameter.name">
                                <span class="font-weight-bold">
                                    {{ parameter.name + '=' + parameter.value }}
                                </span>
                                <span v-if="index !== (history.parameters.length - 1)">
                                    {{ ', ' }}
                                </span>
                            </span>
                        </span>
                    </span>
                    <span v-else-if="history.type === 'OUTPUT'">
                        Processed image for <span class="font-weight-bold"> {{ history.readableName }}</span> was received
                    </span>
                    <span v-else-if="history.type === 'ERROR'">
                        Returned error: <span class="font-weight-bold errorText"> {{ history.message }}</span>
                    </span>
                    <span v-else-if="history.type === 'MESSAGE'">
                        {{ history.message }}
                    </span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
  import constVars from '../mixins/const-variables';

  export default {
    name: "ConsoleMenu",
    props: [ 'consoleMenuTitle' ],
    created() {
      this.$eventBus.$on(constVars.EVENT_HISTORY_CHANGE, historyData => {
        this.histories.push(historyData);
      });
      this.$eventBus.$on(constVars.EVENT_HISTORY_RESET, resetHistoryData => {
        this.histories = [ resetHistoryData ];
      });
      this.$eventBus.$on(constVars.EVENT_HISTORY_ERROR, errorHistoryData => {
        this.histories.push(errorHistoryData);
      });
    },
    data() {
      return {
        histories: []
      }
    }
  }
</script>

<style scoped>
    .consoleMenu {
        background-color: #e9ecef;
        height: 100%;
        border-radius: 0.3rem;
        -webkit-box-shadow: 1px 1px 4px 2px rgba(0,0,0,0.30);
        -moz-box-shadow: 1px 1px 4px 2px rgba(0,0,0,0.30);
        box-shadow: 1px 1px 4px 2px rgba(0,0,0,0.30);
        overflow: auto;
    }
    .historyDiv {
        text-align: left;
    }
    .errorText {
        color: #b60000;
    }
    .console-menu-title {
        font-weight: bold;
    }
</style>