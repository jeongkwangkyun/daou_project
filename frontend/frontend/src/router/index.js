import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";

import Product from "@/views/Product.vue";
import ProductList from "@/components/product/ProductList.vue";
import Payment from "@/views/Payment.vue";
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
        name: ProductList,
        component: ProductList,
      },
    ],
  },
  {
    path: "/payment",
    name: "Payment",
    component: Payment,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
