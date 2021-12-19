import { apiInstance } from "./index.js";

const api = apiInstance();

function getAllRefunds(userNo, success, fail) {
  api.get(`/refund/${userNo}`).then(success).catch(fail);
}

function registerRefund(payNo, success, fail) {
  api.post(`/refund/${payNo}`).then(success).catch(fail);
}
export { getAllRefunds, registerRefund };
