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
          <td>
            <select name="count" v-model="coupon">
              <option value="0">선택안함</option>
              <option
                value="0.2"
                v-if="
                  payment_list[0].totalPrice >= 30000 && userInfo[0].couponA > 0
                "
              >
                20%
              </option>
              <option
                value="0.1"
                v-if="
                  payment_list[0].totalPrice >= 20000 && userInfo[0].couponB > 0
                "
              >
                10%
              </option>
              <option
                value="0.05"
                v-if="
                  payment_list[0].totalPrice >= 10000 && userInfo[0].couponC > 0
                "
              >
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
      </tbody>
    </v-simple-table>
    <v-btn @click="doPayment">결제하기</v-btn>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { registPayment } from "@/api/payment";
import UserData from "@/components/payment/child/UserData.vue";
const userInfoStore = "userInfoStore";
const paymentStore = "paymentStore";
export default {
  components: {
    UserData,
  },
  data() {
    return {
      couponData: [],
      savemoneyData: {},
      pointData: {},
      paymentData: [],
      couponType: 0,
      point: 0,
      savemoney: 0,
      price: 0,
      coupon: 0,
    };
  },
  created() {
    this.price = this.payment_list[0].totalPrice;
  },
  computed: {
    ...mapState(paymentStore, ["payment_list"]),
    ...mapState(userInfoStore, ["userInfo"]),
  },
  methods: {
    change_state() {
      this.price =
        this.payment_list[0].totalPrice -
        this.payment_list[0].totalPrice * this.coupon -
        this.point -
        this.savemoney;
    },
    doPayment() {
      if (this.coupon == 0) {
        this.couponType = 0;
      } else if (this.coupon == 0.2) {
        this.couponType = 20;
      } else if (this.coupon == 0.1) {
        this.couponType = 10;
      } else if (this.coupon == 0.01) {
        this.couponType = 5;
      }

      registPayment(
        {
          userId: "root",
          productNo: this.payment_list[0].productNo,
          productCnt: this.payment_list[0].productCnt,
          totalPrice: this.payment_list[0].totalPrice,
          payCoupon: this.couponType,
          payPoint: this.point,
          paySaveMoney: this.savemoney,
          userMoney: this.price,
        },
        ({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
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
      this.change_state();
    },
    point: function () {
      if (this.point < 0) {
        alert("-값 적용 x");
        this.point = 0;
      } else if (this.point > this.userInfo[0].point) {
        alert("최대" + this.userInfo[0].point + "까지 사용가능합니다.");
        this.point = this.userInfo[0].point;
      } else {
        this.change_state();
        if (this.price < 0) {
          alert("결제 금액 0이하로 포인트 사용 금지");
          this.point = 0;
        }
      }
    },
    savemoney: function () {
      if (this.savemoney < 0) {
        alert("-값 적용 x");
        this.savemoney = 0;
      } else if (this.savemoney > this.userInfo[0].saveMoney) {
        alert("최대" + this.userInfo[0].saveMoney + "까지 사용가능합니다.");
        this.savemoney = this.userInfo[0].saveMoney;
      } else {
        this.change_state();
        if (this.price < 0) {
          alert("결제 금액 0이하로 적립금 사용 금지");
          this.savemoney = 0;
        }
      }
    },
  },
};
</script>

<style></style>
