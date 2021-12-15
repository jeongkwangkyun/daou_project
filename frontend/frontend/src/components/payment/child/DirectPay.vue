<template>
  <tr class="colsize">
    <td>{{ productNo }}</td>
    <td>{{ productName }}</td>
    <td>{{ productCnt }}</td>
    <td>{{ totalPrice }}</td>
    <td>
      <select name="count" v-model="coupon">
        <option value="0">선택안함</option>
        <option value="0.2" v-if="totalPrice >= 30000 && userInfo[0].couponA">
          20%
        </option>
        <option value="0.1" v-if="totalPrice >= 20000 && userInfo[0].couponB">
          10%
        </option>
        <option value="0.05" v-if="totalPrice >= 10000 && userInfo[0].couponC">
          5%
        </option>
      </select>
    </td>
    <td>
      <input type="number" v-model="point" />
    </td>
    <td>
      <input type="number" v-model="savemoney" />
    </td>
    <td>{{ price }}</td>
  </tr>
</template>

<script>
import { mapGetters, mapState } from "vuex";
const userInfoStore = "userInfoStore";
export default {
  name: "DirectPay",
  props: {
    productCnt: Number,
    productNo: Number,
    productName: String,
    productPrice: Number,
    totalPrice: Number,
    payPoint: Number,
    payPrice: Number,
    paySavemoney: Number,
  },
  data() {
    return {
      coupon: 0,
      point: 0,
      savemoney: 0,
      price: 0,
    };
  },
  computed: {
    ...mapState(userInfoStore, ["userInfo"]),
    ...mapGetters(userInfoStore, ["userInfos"]),
  },
  created() {
    this.price = this.payPrice;
  },
  watch: {
    coupon: function () {
      this.price =
        this.totalPrice -
        this.totalPrice * this.coupon -
        this.point -
        this.savemoney;
    },
    point: function () {
      if (this.point < 0) {
        alert("-값 적용 x");
        this.point = 0;
      } else {
        this.price =
          this.totalPrice -
          this.totalPrice * this.coupon -
          this.point -
          this.savemoney;
      }
    },
    savemoney: function () {
      if (this.savemoney < 0) {
        alert("-값 적용 x");
        this.savemoney = 0;
      } else {
        this.price =
          this.totalPrice -
          this.totalPrice * this.coupon -
          this.point -
          this.savemoney;
      }
    },
  },
};
</script>

<style></style>
