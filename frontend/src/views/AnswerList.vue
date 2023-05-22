<template>
  <v-container>
    <v-layout row wrap>
      <v-flex xs12 sm6 offset-sm3>
        <v-card>
          <v-card-title>
            <span class="headline">Answers</span>
            <v-btn @click="addAnswer">Add Answer</v-btn>
            <v-btn v-if="this.question.author.id === this.$store.getters['auth/getId']" @click="editQuestion">Edit Question</v-btn>
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
            <AddAnswerDialog
              :opened="dialogVisible"
              :answer="selectedAnswer"
              :question="this.question"
              @refresh="refreshList"
            ></AddAnswerDialog>
            <QuestionDialog
              :opened="dialogQuestionVisible"
              :item="this.question"
              @refresh="refreshList"
            ></QuestionDialog>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
import api from "../api";
import router from "@/router";
import AddAnswerDialog from "../components/AddAnswerDialog.vue";
import QuestionDialog from "../components/QuestionDialog.vue";
export default {
  name: "AnswerList",
  components: {AddAnswerDialog, QuestionDialog},
  data(){
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
        { text: "Date", value: "date"},
      ],
      dialogVisible: false,
      dialogQuestionVisible: false,
      selectedAnswer: {},
    };
  },
  created() {
    this.question = this.$route.query.question;
    this.refreshList();
  },
  methods: {
    seeDetailedAnswer(answer) {
      router.push({
        path: "/answers/{{ answer.id }}",
        query: { answer: answer },
      });
    },
    addAnswer() {
      this.dialogVisible = true;
    },
    editQuestion() {
      this.dialogQuestionVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.dialogQuestionVisible = false;
      this.selectedAnswer = {};
      this.items = await api.answers.allAnswersByQuestion(this.question);
    },
  },
};
</script>

<style scoped></style>
