<template>
  <div>
    <h3>
      <div class="container">
        <div class="masthead-subheading">결제목록</div>
      </div>
    </h3>
    <user-data></user-data>
    <v-container class="bv-example-row mt-3">
      <v-simple-table>
        <thead>
          <tr>
            <th>결제번호</th>
            <th>구입상품</th>
            <th>구입개수</th>
            <th>총가격</th>
            <th>사용쿠폰</th>
            <th>사용포인트</th>
            <th>사용적립금</th>
            <th>결제 금액</th>
            <th>결제날짜</th>
            <th>버튼</th>
          </tr>
        </thead>
        <tbody>
          <refund-list-row
            style="height: 100px; font-size: 30px; text-align: left"
            v-for="(refund, index) in refunds"
            :key="index"
            v-bind="refund"
          />
        </tbody>
      </v-simple-table>
    </v-container>
  </div>
</template>

<script>
import UserData from "@/components/payment/child/UserData.vue";
import RefundListRow from "@/components/refund/child/RefundListRow.vue";
import { getAllRefunds } from "@/api/refund.js";
export default {
  name: "RefundList",
  components: {
    RefundListRow,
    UserData,
  },
  data() {
    return {
      refunds: [],
      userNo: 2,
    };
  },
  created() {
    getAllRefunds(
      this.userNo,
      (response) => {
        this.refunds = response.data;
        console.log(this.refunds);
      },
      (error) => {
        console.log(error);
      }
    );
  },
  methods: {
    goRefundDone() {
      this.$router.push({ name: "RefundDone" });
    },
  },
};
</script>

<style></style>
