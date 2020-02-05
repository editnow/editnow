<template>
    <b-card no-body class="mb-1">
        <b-card-header header-tag="header" class="p-1" role="tab">
            <b-button block href="#"
                      variant="info"
                      class="font-weight-bold"
                      v-b-toggle="accordionId">
                {{ accordionName }}
            </b-button>
        </b-card-header>
        <b-collapse v-bind:id="accordionId"
                    v-bind:visible="visible"
                    v-bind:accordion="accordion" role="tabpanel">
            <b-card-body>
                <button v-for="actionTool in actionTools"
                        :key="actionTool.name"
                        v-on:click="handleClickedActionTool(actionTool)"
                        :disabled="actionTool.disabled || !canUseActionTools"
                        style="width: 100%;"
                        class="btn btn-light m-1 font-weight-bold action-tool-btn">
                    {{ actionTool.readableName }}
                    <tool-card-parameters-modal v-if="actionTool.parameterInfoDtos && actionTool.parameterInfoDtos.length > 0"
                                                :modal-id="actionTool.name"
                                                :modal-title="actionTool.readableName + ' parameters input modal' "
                                                :parameter-info-dtos="actionTool.parameterInfoDtos"
                                                :callbackFunctionOk="emitActionTool"/>
                </button>
            </b-card-body>
        </b-collapse>
    </b-card>
</template>

<script>
  import constVars from '../mixins/const-variables';
  import ToolCardParametersModal from "./ToolCardParametersModal";

  export default {
    name: "ToolCard",
    components: {ToolCardParametersModal},
    data() {
      return {
        accordionId: this.accordion + '-' + this.accordionName,
        canUseActionTools: false
      }
    },
    props: {
      accordion: String,
      accordionName: {
        type: String,
        required: true
      },
      actionTools: Array,
      visible: {
        type: Boolean,
        default: function () {
          return false
        }
      }
    },
    methods: {
      handleClickedActionTool(actionTool) {
        if (actionTool.parameterInfoDtos && actionTool.parameterInfoDtos.length > 0) {
          this.getInputParameters(actionTool);
        } else {
          this.emitActionTool(actionTool.name, null);
        }
      },
      getInputParameters(actionTool) {
        this.$bvModal.show(actionTool.name);
      },
      emitActionTool(actionToolName, parameterDtos) {
        let emittedActionTool = {
          name: actionToolName,
          readableName: this.actionTools.find(actionTool => {
            return actionTool.name === actionToolName
          }).readableName
        };
        if (parameterDtos !== null && parameterDtos.length > 0) {
          emittedActionTool.parameters = [];
          parameterDtos.forEach(parameterDto => {
            emittedActionTool.parameters.push({
              name: parameterDto.name,
              parameterType: parameterDto.parameterType,
              value: parameterDto.value
            });
          });
        }
        this.$eventBus.$emit(constVars.EVENT_ACTION_TOOL_CLICK, emittedActionTool);
      }
    },
    created() {
      const context = this;
      this.$eventBus.$on(constVars.EVENT_IS_IMAGE, (isImage) => {
        context.canUseActionTools = isImage;
      })
    }
  }
</script>

<style scoped>
  .action-tool-btn {
      border: 1px outset;
  }
</style>