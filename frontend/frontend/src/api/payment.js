import { apiInstance } from "./index.js";

const api = apiInstance();

function getUser(user_no, success, fail) {
  api.get(`/payment/${user_no}`).then(success).catch(fail);
}

function registPayment(payment, success, fail) {
  api
    .post(`/payment/direct`, JSON.stringify(payment))
    .then(success)
    .catch(fail);
}
function getAutoPayment(orderDto, success, fail) {
  api.post(`/payment/auto`, JSON.stringify(orderDto)).then(success).catch(fail);
}

export { getUser, registPayment, getAutoPayment };