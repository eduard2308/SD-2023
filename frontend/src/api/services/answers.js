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
        return HTTP.get(BASE_URL + "/answers/answersByQuestion/" + question.id, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },

    edit(item) {
        return HTTP.patch(BASE_URL + "/answers/admin", item, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },
    remove(item) {
        return HTTP.delete(BASE_URL + "/answers/admin/" + item.id, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },
};