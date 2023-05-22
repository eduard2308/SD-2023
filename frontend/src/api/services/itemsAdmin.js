import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/items/admin/", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(item) {
    return HTTP.patch(BASE_URL + "/items/admin", item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  remove(item) {
    return HTTP.delete(BASE_URL + "/items/admin/" + item.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
