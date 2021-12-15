import { apiInstance } from "./index.js";

const api = apiInstance();

function getUser(user_id, success, fail) {
  api.get(`/payment/${user_id}`).then(success).catch(fail);
}

export { getUser };
