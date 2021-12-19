import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";

import Product from "@/views/Product.vue";
import ProductList from "@/components/product/ProductList.vue";
import ProductView from "@/components/product/ProductView.vue";

import Direct from "@/views/Direct.vue";
import Auto from "@/views/Auto.vue";

import Refund from "@/views/Refund.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/product",
    name: "Product",
    component: Product,
    redirect: "/product/list",
    children: [
      {
        path: "list",
        name: "ProductList",
        component: ProductList,
      },
      {
        path: "detail/:productNo",
        name: "ProductView",
        component: ProductView,
      },
    ],
  },
  {
    path: "/payment/direct",
    name: "Direct",
    component: Direct,
  },
  {
    path: "/payment/auto",
    name: "Auto",
    component: Auto,
  },
  {
    path: "/refund",
    name: "Refund",
    component: Refund,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
