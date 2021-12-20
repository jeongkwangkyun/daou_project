<template>
  <v-app>
    <div id="app">
      <header-bar />
      <router-view />
    </div>
  </v-app>
</template>
<script>
import HeaderBar from "./components/layout/HeaderBar.vue";

export default {
  name: "App",
  components: {
    HeaderBar,
  },
};
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
