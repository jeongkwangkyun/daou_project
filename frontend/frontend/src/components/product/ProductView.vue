<template>
  <div>
    <v-container class="bv-example-row mt-3">
      <v-row>
        <v-col>
          <v-alert show
            ><h3>{{ product.productName }} 주문하기</h3></v-alert
          >
        </v-col>
      </v-row>

      <v-row class="mb-1">
        <v-col class="text-left">
          <v-btn class="ma-1" color="grey" outlined @click="listProduct"
            >목록</v-btn
          >
        </v-col>
      </v-row>
      <v-simple-table>
        <thead>
          <tr>
            <th>상품번호</th>
            <th>상품명</th>
            <th>가격</th>
            <th>상품내용</th>
            <th>개수</th>
            <th>총 금액</th>
          </tr>
        </thead>
        <tbody>
          <tr class="colsize">
            <td>{{ product.productNo }}</td>
            <td>{{ product.productName }}</td>
            <td>{{ product.productPrice }}</td>
            <td>{{ product.productContent }}</td>
            <td>
              <input type="number" v-model="productCnt" />
            </td>
            <td>{{ totalPrice }}</td>
          </tr>
        </tbody>
      </v-simple-table>
    </v-container>
    <v-btn color="primary" variant="primary" @click="directPayment()">
      직접 결제방식
    </v-btn>
    <v-btn color="success" variant="primary" @click="autoPayment()"
      >자동 결제방식</v-btn
    >
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
const paymentStore = "paymentStore";
import { getProduct } from "@/api/product";
export default {
  data() {
    return {
      product: {},
      productNo: 0,
      productCnt: 0,
      totalPrice: 0,
      payment_list: [],
    };
  },
  computed: {
    ...mapGetters(paymentStore, ["payments"]),
  },
  created() {
    getProduct(
      this.$route.params.productNo,
      (response) => {
        this.product = response.data;
      },
      (error) => {
        console.log("삭제시 에러발생!!", error);
      }
    );
  },
  methods: {
    ...mapActions(paymentStore, ["regPayment"]),
    listProduct() {
      this.$router.push({ name: "ProductList" });
    },
    directPayment() {
      //직접결제;
      if (this.checkValue() == true) {
        this.$router.push({
          name: "Direct",
        });
      }
    },
    autoPayment() {
      if (this.checkValue() == true) {
        this.$router.push({
          name: "Auto",
        });
      }
    },
    checkValue() {
      if (this.productCnt == 0) {
        alert("1개 이상 선택하셔야 합니다.");
        return false;
      } else {
        this.payment_list.push({
          productNo: this.product.productNo,
          productName: this.product.productName,
          productCnt: this.productCnt * 1,
          totalPrice: this.totalPrice,
        });
        this.regPayment(this.payment_list);
        return true;
      }
    },
  },
  watch: {
    productCnt: function () {
      if (this.productCnt < 0) {
        alert("1개 이상 선택하셔야 합니다.");
        this.productCnt = 1;
      } else {
        this.totalPrice = this.productCnt * this.product.productPrice;
      }
    },
  },
};
</script>

<style></style>
