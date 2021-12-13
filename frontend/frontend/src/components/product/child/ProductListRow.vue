<template>
  <tr class="colsize">
    <td><input type="checkbox" v-model="checked" /></td>
    <td>{{ prd_no }}</td>
    <td>{{ prd_name }}</td>
    <td>{{ prd_price }}</td>

    <td>
      <input type="number" v-model="prd_cnt" :disabled="checked == false" />
    </td>
    <td>{{ total_price }}</td>
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
  created() {},
  data() {
    return {
      total_price: 0,
      prd_cnt: 0,
      checked: false,
      product: {},
    };
  },
  watch: {
    prd_cnt: function () {
      if (this.prd_cnt < 0) {
        alert("1개 이상 선택하셔야 합니다.");
        this.prd_cnt = 1;
      } else {
        this.total_price = this.prd_cnt * this.prd_price;
        this.go_parent();
      }
    },
    checked: function () {
      if (this.checked == false) {
        this.prd_cnt = 0;
      } else {
        this.prd_cnt = 1;
      }
    },
  },
  methods: {
    go_parent() {
      this.product.checked = this.checked;
      this.product.prd_cnt = this.prd_cnt;
      this.product.prd_no = this.prd_no;
      this.product.total_price = this.total_price;
      this.$emit("change_state", this.product);
    },
  },
};
</script>

<style></style>
