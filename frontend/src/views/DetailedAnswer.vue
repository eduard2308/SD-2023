<template>
  <v-container>
    <v-layout row wrap>
      <v-flex xs12 sm6 offset-sm3>
        <v-card>
          <v-card-title>
            <span class="headline">Detailed Answer</span>
            <v-btn v-if="this.answer.user_id === this.$store.getters['auth/getId'] || this.$store.getters['auth/getRoles'] == 'ADMIN'" @click="editAnswer">Edit Answer</v-btn>
            <v-btn v-if="this.answer.user_id === this.$store.getters['auth/getId'] || this.$store.getters['auth/getRoles'] == 'ADMIN'" @click="deleteAnswer">Delete Answer</v-btn>
            <v-btn v-if="this.answer.user_id !== this.$store.getters['auth/getId'] && this.$store.getters['auth/getRoles'] == 'CUSTOMER'" @click="upVoteAnswer">Upvote Answer</v-btn>
            <v-btn v-if="this.answer.user_id !== this.$store.getters['auth/getId'] && this.$store.getters['auth/getRoles'] == 'CUSTOMER'" @click="downVoteAnswer">Down-vote Answer</v-btn>
          </v-card-title>
          <h2>{{ this.answer.text }}</h2>
          <h2>Votes: {{this.answer.votes}}</h2>
          <h3>Author score: {{this.score}}</h3>
          <v-img
            :src="'data:image/png;base64,' + this.answer.image"
            height="300px"
          ></v-img>
          <v-card-text>
            <v-container>
              <v-layout row wrap>
                <v-flex xs12>
                </v-flex>
              </v-layout>
            </v-container>
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
import AnswerDialog from "@/components/AnswerDialog";

export default {
  name: "DetailedAnswer",
  components: {AnswerDialog},
  data() {
    return {
      answer: {},
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
      score: 0,
    };
  },
  created() {
    this.answer = this.$route.query.answer;
    this.refreshList();
  },
  methods: {
  editAnswer() {
      this.selectedAnswer = this.answer;
      this.dialogVisible = true;
    },
    deleteAnswer() {
      api.answers
        .remove(this.answer)
        .then(() => {
          this.$router.push({ path: "/questions" });
        })
    },
    downVoteAnswer() {
     api.answers.downVote(this.answer, this.$store.getters['auth/getId']);
    },
    upVoteAnswer() {
     api.answers.upVote(this.answer, this.$store.getters['auth/getId']);
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.score = await api.answers.getUserScore(this.answer);
    },
  },
};
</script>

<style scoped></style>
