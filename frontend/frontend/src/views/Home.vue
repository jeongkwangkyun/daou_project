<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/daou.png" />
    <info />
  </div>
</template>

<script>
import Info from "../components/home/Info.vue";

export default {
  name: "Home",
  components: { Info },
};
</script>
