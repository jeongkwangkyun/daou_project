<template></template>

<script>
import { userData } from "@/api/payment";
export default {
  name: "UserData",
  data() {
    return {
      userdata: [],
    };
  },
  created() {
    userData(
      "root",
      (response) => {
        this.userdata = response.data;
        console.log(this.userdata);
        // console.log(data + "회원가입 데이터");
      },
      (error) => {
        console.log(error);
      }
    );
  },
};
</script>

<style></style>
