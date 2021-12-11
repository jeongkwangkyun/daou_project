<template>
  <v-simple-table>
    <template v-slot:default>
      <thead>
        <tr>
          <th>상품번호</th>
          <th>상품명</th>
          <th>가격</th>
          <th>개수</th>
          <th>총 금액</th>
          <th>체크</th>
        </tr>
      </thead>
      <tbody>
        <product-list-row
          style="height: 100px; font-size: 30px; text-align: left"
          v-for="(product, index) in products"
          :key="index"
          v-bind="product"
        />
      </tbody>
    </template>
  </v-simple-table>
</template>

<script>
import ProductListRow from "@/components/product/child/ProductListRow.vue";
import { listProduct } from "@/api/product.js";
export default {
  name: "ProductList",
  components: {
    ProductListRow,
  },
  data() {
    return {
      products: [],
    };
  },
  created() {
    listProduct(
      (response) => {
        this.products = response.data;
        console.log(response.data);
      },
      (error) => {
        console.log(error);
      }
    );
  },
};
</script>

<style></style>
