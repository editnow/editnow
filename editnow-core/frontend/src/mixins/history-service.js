import constVars from '../mixins/const-variables';

export default {
  emitActionRequestHistory(context, readableName, parameters) {
    const dataHistory = {
      readableName: readableName, type: 'INPUT'
    };
    if (parameters && parameters.length > 0) {
      dataHistory.parameters = [];
      parameters.forEach(parameter => dataHistory.parameters.push(parameter))
    }
    emitHistory(context, dataHistory);
  },
  emitResetHistory(context) {
    const dataHistory = {
      type: 'MESSAGE',
      message: 'Changes history reset',
      time: getCurrentTime()
    };
    context.$eventBus.$emit(constVars.EVENT_HISTORY_RESET, dataHistory);
  },
  emitErrorMessage(context, errorMessage) {
    const errorData = {
      type: 'ERROR',
      message: errorMessage,
      time: getCurrentTime()
    };
    context.$eventBus.$emit(constVars.EVENT_HISTORY_ERROR, errorData);
  }
}

function emitHistory(context, dataHistory) {
  dataHistory.time = getCurrentTime();
  context.$eventBus.$emit(constVars.EVENT_HISTORY_CHANGE, dataHistory);
}

function getCurrentTime() {
  const date = new Date();
  return date.toLocaleTimeString();
}