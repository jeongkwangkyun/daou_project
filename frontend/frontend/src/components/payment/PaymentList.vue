<template>
  <div>
    <user-data
      @change_couponState="change_couponState"
      @change_savemoneyState="change_savemoneyState"
      @change_pointState="change_pointState"
    ></user-data>
    <v-simple-table>
      <thead>
        <tr>
          <th>상품번호</th>
          <th>구입상품</th>
          <th>수량</th>
          <th>총 가격</th>
          <th>할인 쿠폰 사용</th>
          <th>포인트 사용</th>
          <th>적립금 사용</th>
          <th>결제 금액</th>
        </tr>
      </thead>
      <tbody>
        <direct-pay
          style="height: 100px; font-size: 30px; text-align: left"
          v-for="(payment, index) in payment_list"
          :key="index"
          v-bind="payment"
        />
      </tbody>
    </v-simple-table>
    <v-btn>결제하기</v-btn>
  </div>
</template>

<script>
import { mapState } from "vuex";

import DirectPay from "@/components/payment/child/DirectPay.vue";
import UserData from "@/components/payment/child/UserData.vue";
const paymentStore = "paymentStore";
export default {
  components: {
    UserData,
    DirectPay,
  },
  data() {
    return {
      couponData: [],
      savemoneyData: {},
      pointData: {},
      paymentData: [],
      couponA: 0,
      couponB: 0,
      couponC: 0,
    };
  },
  created() {
    for (var i = 0; i < this.payment_list.length; i++) {
      this.paymentData.push({
        coupon: 0,
        point: 0,
        savemoney: 0,
        payPrice: this.payment_list[i].payPrice,
      });
    }
  },
  computed: {
    ...mapState(paymentStore, ["payment_list"]),
  },
  methods: {
    change_couponState: function (data) {
      this.couponData = data;
      this.couponA = this.couponData[0].cnt;
      this.couponB = this.couponData[1].cnt;
      this.couponC = this.couponData[2].cnt;
    },
    change_savemoneyState: function (data) {
      this.savemoneyData = data;
    },
    change_pointState: function (data) {
      this.pointData = data;
    },
  },
  watch: {},
};
</script>

<style></style>
