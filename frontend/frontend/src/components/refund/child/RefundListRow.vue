<template>
  <tr class="colsize">
    <td>{{ payNo }}</td>
    <td>{{ productName }}</td>
    <td>{{ productCnt }}</td>
    <td>{{ totalPrice }}</td>
    <td>{{ couponType }}</td>
    <td>{{ usePoint }}</td>
    <td>{{ useSavemoney }}</td>
    <td>{{ userMoney }}</td>
    <td>{{ payDate }}</td>
    <td v-if="refundYn == 'n'">
      <v-btn @click="doRefund">환불하기</v-btn>
    </td>
    <td v-else>환불완료</td>
  </tr>
</template>

<script>
import { registerRefund } from "@/api/refund";
export default {
  name: "RefundListRow",
  props: {
    payNo: Number,
    productName: String,
    productCnt: Number,
    totalPrice: Number,
    couponType: Number,
    usePoint: Number,
    useSavemoney: Number,
    userMoney: Number,
    payDate: String,
    refundYn: String,
  },
  methods: {
    doRefund() {
      registerRefund(
        this.payNo,
        ({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data == "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          location.reload(true);
          this.movePage();
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
</script>

<style></style>
