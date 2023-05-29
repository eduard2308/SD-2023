<template>
  <v-container>
    <v-layout row wrap>
      <v-flex xs12 sm6 offset-sm3>
        <v-card>
          <v-card-title>
            <span class="headline">Answers</span>
            <v-btn @click="editQuestion">Edit Question</v-btn>
            <v-btn @click="deleteQuestion">Delete Question</v-btn>
          </v-card-title>
          <h1>{{ this.question.title }}</h1>
          <h2>{{ this.question.text }}</h2>
          <v-img
            :src="'data:image/png;base64,' + this.question.image"
            height="300px"
          ></v-img>
          <v-card-text>
            <v-container>
              <v-layout row wrap>
                <v-flex xs12>
                  <v-text-field
                    v-model="search"
                    append-icon="search"
                    label="Search"
                    single-line
                    hide-details
                  ></v-text-field>
                </v-flex>
              </v-layout>
            </v-container>
            <v-data-table
              :headers="headers"
              :items="items"
              :search="search"
              @click:row="seeDetailedAnswer"
            ></v-data-table>
            <AnswerDialog
              :opened="dialogVisible"
              :answer="selectedAnswer"
              @refresh="refreshList"
            ></AnswerDialog>
            <QuestionDialogAdmin
              :opened="dialogQuestionVisible"
              :item="this.question"
              @refresh="refreshList"
            ></QuestionDialogAdmin>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import api from "../api";
import AnswerDialog from "../components/AnswerDialog.vue";
import QuestionDialogAdmin from "@/components/QuestionDialogAdmin";
import router from "@/router";
export default {
  name: "AnswerListAdmin",
  components: {
    AnswerDialog,
    QuestionDialogAdmin,
  },
  data() {
    return {
      question: null,
      items: [],
      search: "",
      headers: [
        {
          text: "Text",
          align: "start",
          sortable: false,
          value: "text",
        },
        { text: "Author", value: "user_id" },
        { text: "Date", value: "date" },
      ],
      dialogVisible: false,
      dialogQuestionVisible: false,
      selectedAnswer: {},
    };
  },
  methods: {
    seeDetailedAnswer(answer) {
      router.push({
        path: "/answers/admin/{{answer.id}}",
        query: { answer: answer },
      });
    },
    editQuestion() {
      this.dialogQuestionVisible = true;
    },
    async deleteQuestion() {
      await api.itemsAdmin.remove(this.question);
      router.push({ path: "/questions/admin" });
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedAnswer = {};
      this.items = await api.answersAdmin.allAnswersByQuestion(this.question);
    },
  },
  created() {
    this.question = this.$route.query.question;
    this.refreshList();
  },
};
</script>

<style scoped></style>
