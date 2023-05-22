<template>
  <v-container>
    <v-layout row wrap>
      <v-flex xs12 sm6 offset-sm3>
        <v-card>
          <v-card-title>
            <span class="headline">Answers</span>
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
              :items="answers"
              :search="search"
              @click:row="editAnswer"
            ></v-data-table>
            <AnswerDialog
              :opened="dialogVisible"
              :answer="selectedAnswer"
              @refresh="refreshList"
            ></AnswerDialog>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import api from "../api";
import AnswerDialog from "../components/AnswerDialog.vue";
export default {
  name: "AnswerListAdmin",
  components: {
    AnswerDialog,
  },
  data() {
    return {
      question: null,
      answers: [],
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
      selectedAnswer: {},
    };
  },
  methods: {
    editAnswer(answer) {
      this.selectedAnswer = answer;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedAnswer = {};
      this.answers = await api.answersAdmin.allAnswersByQuestion(this.question);
    },
  },
  created() {
    this.question = this.$route.query.question;
    this.refreshList();
  },
};
</script>

<style scoped></style>
