<template>
  <v-container>
    <v-layout row wrap>
      <v-flex xs12 sm6 offset-sm3>
        <v-card>
          <v-card-title>
            <span class="headline">Answers</span>
          </v-card-title>
          <h1>{{ this.question.title }}</h1>
          <h2>{{ this.question.author.username }}</h2>
          <h2>{{ this.question.text }}</h2>
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
              @click:row="editItem"
            ></v-data-table>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
import api from "../api";
export default {
  name: "AnswerList",
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
        { text: "Author", value: "author.username" },
        { text: "Date", value: "date"},
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  created() {
    this.question = this.$route.query.question;
    this.refreshList();
  },
  methods: {
    editItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },
  },
};
</script>

<style scoped></style>
