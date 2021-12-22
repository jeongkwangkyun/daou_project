<template>
  <tr>
    <td>{{ productNo }}</td>
    <td>
      <router-link
        :to="{ name: 'ProductView', params: { productNo: this.productNo } }"
      >
        {{ productName }}
      </router-link>
    </td>
    <td>{{ productPrice }}</td>
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
    return {};
  },
};
</script>

<style></style>
