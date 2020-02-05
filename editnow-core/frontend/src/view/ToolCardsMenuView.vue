<template>
    <div role="tablist" class="toolCards">
        <h4 class="m-1 font-weight-bold">
            {{ toolCardsTitle }}
        </h4>
        <tool-card
            v-for="toolCard in toolCards"
            :key="toolCard.accordionName"
            v-bind:accordion="toolCard.accordion"
            v-bind:accordion-name="toolCard.accordionName"
            v-bind:action-tools="toolCard.actionTools"
            v-bind:visible="toolCard.visible"></tool-card>
    </div>
</template>

<script>
    import ToolCard from "../components/ToolCard";
    import configurationApi from '../components/api/configuration-api';

    export default {
      name: "ToolCardsAccordion",
      components: {ToolCard},
      data () {
        const toolCardsAccordion = 'Tools';
        return {
          toolCardsTitle: toolCardsAccordion,
          toolCards: []
        }
      },
      methods: {
        getActionToolsConfiguration() {
          const context = this;

          configurationApi.getActionTools()
            .then(response => {
              const FIRST_ACTION_TOOL_CATEGORY = 0;
              const actionToolsInCategory = response.data;

              actionToolsInCategory.forEach( (actionToolInCategory, index) => {
                const categoryName = actionToolInCategory.categoryName;
                context.toolCards.push({
                  accordion: context.toolCardsTitle,
                  accordionName: categoryName,
                  actionTools: actionToolInCategory.actionTools,
                  visible: index === FIRST_ACTION_TOOL_CATEGORY
                });
              })

            })
            .catch(error => {
              console.log(error)
            })
        }
      },
      created() {
        this.getActionToolsConfiguration();
      },
    }
</script>

<style scoped>
    .toolCards {
        background-color: #e9ecef;
        border-radius: 0.3rem;
        -webkit-box-shadow: 1px 1px 4px 2px rgba(0,0,0,0.30);
        -moz-box-shadow: 1px 1px 4px 2px rgba(0,0,0,0.30);
        box-shadow: 1px 1px 4px 2px rgba(0,0,0,0.30);
    }
</style>