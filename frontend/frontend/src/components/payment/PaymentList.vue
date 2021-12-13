<template>
  <div>
    <user-data></user-data>
  </div>
</template>

<script>
import { mapState } from "vuex";
import UserData from "@/components/payment/userdata/UserData.vue";

const paymentStore = "paymentStore";
export default {
  components: {
    UserData,
  },
  data() {
    return {};
  },
  computed: {
    ...mapState(paymentStore, ["payment_list"]),
  },
};
</script>

<style></style>
