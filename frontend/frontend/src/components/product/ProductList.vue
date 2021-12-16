<template>
  <div>
    <v-simple-table>
      <thead>
        <tr>
          <th>상품번호</th>
          <th>상품명</th>
          <th>가격</th>
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
  </div>
</template>

<script>
import ProductListRow from "@/components/product/child/ProductListRow.vue";
import { listProduct } from "@/api/product.js";
export default {
  name: "ProductList",
  components: {
    ProductListRow,
  },
  computed: {},
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

        console.log(this.products);
      },
      (error) => {
        console.log(error);
      }
    );
  },
  methods: {},
};
</script>

<style></style>
