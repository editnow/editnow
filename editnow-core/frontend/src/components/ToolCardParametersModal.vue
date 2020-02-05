<template>
    <b-modal v-bind:id="modalId"
             :title="modalTitle"
             hide-footer>
        <div v-for="parameterInfo in parameterInfoDtos"
             :key="parameterInfo.name" style="margin-top: 5px;">
            <b-input-group :prepend="parameterInfo.name">
                <b-form-input v-if="parameterInfo.parameterType === 'INT'"
                              type="number"
                              v-model="parameterInfo.value"></b-form-input>
                <b-form-select v-if="parameterInfo.parameterType === 'OPTION'"
                               v-model="parameterInfo.value"
                               :options="parameterInfo.parameterInfoOptionDtos"></b-form-select>
                <span class="font-italic" style="margin-left: 5px;">{{parameterInfo.tips}}</span>
            </b-input-group>
        </div>
        <div class="footer">
            <button type="button"
                    class="btn btn-secondary footer-button"
                    @click="closeModal">
                Cancel
            </button>
            <button type="button"
                    class="btn btn-success footer-button"
                    @click="handleSendParameters"
            >
                Send parameters
            </button>
        </div>
    </b-modal>
</template>

<script>
  export default {
    name: "ToolCardParametersModal",
    props: [
      'modalId', 'modalTitle', 'parameterInfoDtos', 'callbackFunctionOk'
    ],
    methods: {
      handleSendParameters() {
        this.callbackFunctionOk(this.modalId, this.parameterInfoDtos);
        this.closeModal();
      },
      closeModal() {
        this.$bvModal.hide(this.modalId);
      }
    }
  }
</script>

<style scoped>
    .footer {
        display: flex;
        -ms-flex-align: center;
        -webkit-box-align: center;
        align-items: center;
        -ms-flex-pack: end;
        -webkit-box-pack: end;
        justify-content: flex-end;
        border-top: 1px solid #dee2e6;
        margin: 15px 5px 15px 5px;
    }
    .footer-button {
        margin: 1rem .5rem 0 0;
    }

</style>