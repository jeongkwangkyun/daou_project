import { apiInstance } from "./index.js";

const api = apiInstance();

function listProduct(success, fail) {
  api.get("/product").then(success).catch(fail);
}
function getProduct(prdocutNo, success, fail) {
  api.get(`/product/${prdocutNo}`).then(success).catch(fail);
}

export { listProduct, getProduct };
