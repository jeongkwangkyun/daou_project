<template>
  <div>
    <v-simple-table>
      <thead>
        <tr>
          <th>체크</th>
          <th>상품번호</th>
          <th>상품명</th>
          <th>가격</th>
          <th>개수</th>
          <th>총 금액</th>
        </tr>
      </thead>
      <tbody>
        <product-list-row
          style="height: 100px; font-size: 30px; text-align: left"
          v-for="(product, index) in products"
          :key="index"
          v-bind="product"
          @change_state="change_state"
        />
      </tbody>
    </v-simple-table>
    <v-btn color="primary" variant="primary" @click="payment(1)">
      직접 결제방식
    </v-btn>
    <v-btn color="success" variant="primary" @click="payment()"
      >자동 결제방식</v-btn
    >
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
const paymentStore = "paymentStore";
import ProductListRow from "@/components/product/child/ProductListRow.vue";
import { listProduct } from "@/api/product.js";
export default {
  name: "ProductList",
  components: {
    ProductListRow,
  },
  computed: {
    ...mapGetters(paymentStore, ["payments"]),
  },
  data() {
    return {
      products: [],
      payment_list: [],
    };
  },
  created() {
    listProduct(
      (response) => {
        this.products = response.data;
        for (var i = 0; i < this.products.length; i++) {
          this.products[i].checked = false;
          this.products[i].productCnt = 0;
          this.products[i].totalPrice = 0;
        }
        console.log(this.products);
      },
      (error) => {
        console.log(error);
      }
    );
  },
  methods: {
    ...mapActions(paymentStore, ["regPayment"]),
    change_state: function (product) {
      this.products[product.productNo - 1].checked = product.checked;
      this.products[product.productNo - 1].totalPrice = product.totalPrice;
      this.products[product.productNo - 1].productCnt = product.productCnt;
      // console.log(this.products);
    },
    payment(pay_type) {
      //직접결제;
      // console.log(this.products);
      for (var i = 0; i < this.products.length; i++) {
        if (this.products[i].checked == true) {
          this.payment_list.push({
            productNo: this.products[i].productNo,
            productCnt: this.products[i].productCnt,
            productName: this.products[i].productName,
            totalPrice: this.products[i].totalPrice,
            payPrice: this.products[i].totalPrice,
          });
        }
      }
      this.regPayment(this.payment_list);
      if (pay_type == 1) {
        // console.log(this.payment_list);
        if (this.payment_list.length == 0) {
          alert("구입하실 상품을 선택해주세요");
        } else {
          this.$router.push({
            name: "Payment",
          });
        }
      }
      //자동결제
      else if (pay_type == 2) {
        console.log(this.payment_list);
      }
    },
  },
};
</script>

<style></style>
