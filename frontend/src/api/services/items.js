import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/items", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(item) {
    return HTTP.post(BASE_URL + "/items", item, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(item) {
    return HTTP.patch(BASE_URL + "/items", item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  downVote(item, iduser) {
    return HTTP.patch(
      BASE_URL + "/items/" + item.id + "/downvote",
      {},
      {
        headers: authHeader(),
        params: {
          userId: iduser,
        },
      }
    ).then((response) => {
      return response.data;
    });
  },
  upVote(item, iduser) {
    return HTTP.patch(
      BASE_URL + "/items/" + item.id + "/upvote",
      {},
      {
        headers: authHeader(),
        params: {
          userId: iduser,
        },
      }
    ).then((response) => {
      return response.data;
    });
  },
  remove(item) {
    return HTTP.delete(BASE_URL + "/items/" + item.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  getUserScore(question) {
    return HTTP.get(BASE_URL + "/items/" + question.id+"/scoreUser", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  }
};
