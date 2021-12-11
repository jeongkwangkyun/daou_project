<template>
  <tr class="colsize">
    <td>{{ prd_no }}</td>
    <td>{{ prd_name }}</td>
    <td>{{ prd_price }}</td>
    <td>{{ prd_cnt }}</td>
    <td>{{ total_price }}</td>
    <td><input type="checkbox" /></td>
  </tr>
</template>

<script>
export default {
  name: "ProductListRow",
  props: {
    prd_no: Number,
    prd_name: String,
    prd_price: Number,
  },
  data() {
    return {
      total_price: 0,
      prd_cnt: 0,
    };
  },
};
</script>

<style></style>
