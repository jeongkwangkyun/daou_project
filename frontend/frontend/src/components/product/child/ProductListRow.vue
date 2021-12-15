<template>
  <tr class="colsize">
    <td><input type="checkbox" v-model="checked" /></td>
    <td>{{ productNo }}</td>
    <td>{{ productName }}</td>
    <td>{{ productPrice }}</td>

    <td>
      <input type="number" v-model="productCnt" :disabled="checked == false" />
    </td>
    <td>{{ totalPrice }}</td>
  </tr>
</template>

<script>
export default {
  name: "ProductListRow",
  props: {
    productNo: Number,
    productName: String,
    productPrice: Number,
  },
  created() {},
  data() {
    return {
      totalPrice: 0,
      productCnt: 0,
      checked: false,
      product: {},
    };
  },
  watch: {
    productCnt: function () {
      if (this.productCnt < 0) {
        alert("1개 이상 선택하셔야 합니다.");
        this.productCnt = 1;
      } else {
        this.totalPrice = this.productCnt * this.productPrice;
        this.go_parent();
      }
    },
    checked: function () {
      if (this.checked == false) {
        this.productCnt = 0;
      } else {
        this.productCnt = 1;
      }
    },
  },
  methods: {
    go_parent() {
      this.product.checked = this.checked;
      this.product.productCnt = this.productCnt;
      this.product.productNo = this.productNo;
      this.product.totalPrice = this.totalPrice;
      this.$emit("change_state", this.product);
    },
  },
};
</script>

<style></style>
