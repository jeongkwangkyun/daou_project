<template>
  <div>
    <user-data></user-data>
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
        <tr class="colsize">
          <td>{{ payment_list[0].productNo }}</td>
          <td>{{ payment_list[0].productName }}</td>
          <td>{{ payment_list[0].productCnt }}</td>
          <td>{{ payment_list[0].totalPrice }}</td>
          <td>{{ this.coupon }} %</td>
          <td>{{ this.point }}</td>
          <td>{{ this.savemoney }}</td>
          <td>{{ this.usermoney }}</td>
        </tr>
      </tbody>
    </v-simple-table>
    <v-btn @click="doPayment">결제하기</v-btn>
  </div>
</template>

<script>
import { mapState } from "vuex";
import UserData from "@/components/payment/child/UserData.vue";
import { getAutoPayment, registPayment } from "@/api/payment";
const paymentStore = "paymentStore";
export default {
  components: {
    UserData,
  },
  data() {
    return {
      paymentData: [],
      userNo: 2,
      coupon: 0,
      point: 0,
      savemoney: 0,
      usermoney: 0,
    };
  },
  created() {
    getAutoPayment(
      {
        userNo: this.userNo,
        productNo: this.payment_list[0].productNo,
        productCnt: this.payment_list[0].productCnt,
        totalPrice: this.payment_list[0].totalPrice,
        payCoupon: 0,
        payPoint: 0,
        paySaveMoney: 0,
        userMoney: 0,
      },
      (response) => {
        this.paymentData = response.data;

        this.coupon = this.paymentData.payCoupon;
        this.point = this.paymentData.payPoint;
        this.savemoney = this.paymentData.paySaveMoney;
        this.usermoney = this.paymentData.userMoney;
      }
    ),
      (error) => {
        console.log(error);
      };
  },
  computed: {
    ...mapState(paymentStore, ["payment_list"]),
  },
  methods: {
    doPayment() {
      registPayment(
        this.paymentData,
        ({ data }) => {
          let msg = "결제 처리시 문제가 발생했습니다.";
          if (data == "success") {
            msg = "결제가 완료되었습니다.";
          }
          alert(msg);
          this.movePage();
        },
        (error) => {
          console.log(error);
        }
      );
    },
    movePage() {
      this.$router.push({ name: "ProductList" });
    },
  },
  watch: {
    coupon: function () {
      this.coupon = this.paymentData.payCoupon;
    },
    point: function () {
      this.point = this.paymentData.payPoint;
    },
    savemoney: function () {
      this.savemoney = this.paymentData.paySaveMoney;
    },
    usermoney: function () {
      this.usermoney = this.paymentData.userMoney;
    },
  },
};
</script>

<style></style>
