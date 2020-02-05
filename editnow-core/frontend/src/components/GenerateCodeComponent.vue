<template>
    <span>
        <input type="button"
               class="btn btn-success font-weight-bold"
               value="Generate code"
               :disabled="disableGenerateCodeBtn"
               @click="generateCode()" />
        <b-modal :id="modalId"
                 title="Generated code"
                 hide-footer>
            <div>
                <pre>
                    <code>
                        {{code}}
                    </code>
                </pre>
            </div>
        </b-modal>
    </span>
</template>

<script>
  import constVars from '../mixins/const-variables';
  import actionApi from './api/action-api.js';

  export default {
    name: "GenerateCodeComponent",
    props: [ ],
    data() {
      return {
        disableGenerateCodeBtn: true,
        modalId: 'generatedCodeModal',
        code: ''
      }
    },
    created() {
      this.$eventBus.$on(constVars.EVENT_DISABLE_GENERATE_CODE_BTN, (disableGenerateCodeBtn) => {
        this.disableGenerateCodeBtn = disableGenerateCodeBtn;
      });
    },
    methods: {
      generateCode() {
        actionApi.generateCode().then(response => {
          this.code = "\n" + response.data;
          this.$bvModal.show(this.modalId);
        }).catch(error => {
          console.log(error)
        });
      }
    }
  }
</script>

<style scoped>

</style>