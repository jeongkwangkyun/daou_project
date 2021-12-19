import { apiInstance } from "./index.js";

const api = apiInstance();

function listProduct(success, fail) {
  api.get("/product").then(success).catch(fail);
}
function getProduct(productNo, success, fail) {
  api.get(`/product/${productNo}`).then(success).catch(fail);
}

export { listProduct, getProduct };
