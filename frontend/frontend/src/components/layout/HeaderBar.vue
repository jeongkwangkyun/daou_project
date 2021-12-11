<template>
  <div>
    <v-app-bar color="deep-purple" dark>
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

      <v-toolbar-title>Daou몰 </v-toolbar-title>
      <v-toolbar-items class="hidden-sm-and-down">
        <v-btn right color="deep-purple" v-if="userInfo" @click="moveMypage()"
          >{{ userInfo.userid }}님 안녕하세요</v-btn
        >
      </v-toolbar-items>
    </v-app-bar>

    <v-navigation-drawer v-model="drawer" absolute temporary>
      <v-list nav dense>
        <v-list-item-group
          v-model="group"
          active-class="deep-purple--text text--accent-4"
        >
          <v-list-item router-link :to="{ name: 'Home' }">
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Home</v-list-item-title>
          </v-list-item>

          <v-list-item router-link :to="{ name: 'Product' }">
            <v-list-item-icon>
              <v-icon>mdi-account-plus</v-icon>
            </v-list-item-icon>
            <v-list-item-title>상품주문</v-list-item-title>
          </v-list-item>

          <v-list-item router-link :to="{ name: 'Refund' }">
            <v-list-item-icon>
              <v-icon>mdi-account-circle</v-icon>
            </v-list-item-icon>
            <v-list-item-title>환불</v-list-item-title>
          </v-list-item>

          <!-- <v-list-item v-if="!userInfo" router-link :to="{ name: 'SignIn' }">
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>
            <v-list-item-title>로그인</v-list-item-title>
          </v-list-item> -->

          <!-- <v-list-item @click.prevent="onClickLogout">
            <v-list-item-icon>
              <v-icon>mdi-logout-variant</v-icon>
            </v-list-item-icon>
            <v-list-item-title>로그아웃</v-list-item-title>
          </v-list-item> -->
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  name: "HeaderBar",
  data: () => ({
    drawer: false,
    group: null,
  }),
};
</script>

<style></style>
