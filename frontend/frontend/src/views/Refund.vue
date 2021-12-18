<template>
  <v-container>
    <h3>
      <div class="container">
        <div class="masthead-subheading">환불신청</div>
      </div>
    </h3>
    <refund-list></refund-list>
  </v-container>
</template>

<script>
import RefundList from "@/components/refund/RefundList.vue";
export default {
  name: "Refund",
  components: {
    RefundList,
  },
};
</script>

<style></style>
