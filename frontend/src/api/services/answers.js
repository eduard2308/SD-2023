import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/answers/", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  allAnswersByQuestion(question) {
    return HTTP.get(BASE_URL + "/answers/answersByQuestion/" + question.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  answerById(answer) {
    return HTTP.get(BASE_URL + "/answers/answerById/" + answer.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  create(answer) {
    return HTTP.post(BASE_URL + "/answers", answer, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  edit(answer) {
    return HTTP.patch(BASE_URL + "/answers", answer, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  remove(answer) {
    return HTTP.delete(BASE_URL + "/answers/" + answer.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  downVote(answer, iduser) {
    return HTTP.patch(
      BASE_URL + "/answers/" + answer.id + "/downvote",
      answer,
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
  upVote(answer, iduser) {
    return HTTP.patch(
      BASE_URL + "/answers/" + answer.id + "/upvote",
      answer,
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
  getUserScore(answer) {
    return HTTP.get(BASE_URL + "/answers/" + answer.id + "/userScore", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  }
};
