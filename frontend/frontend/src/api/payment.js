import { apiInstance } from "./index.js";

const api = apiInstance();

function getUser(success, fail) {
  api.get(`/payment/${userid}`).then(success).catch(fail);
}

export { getUser };
