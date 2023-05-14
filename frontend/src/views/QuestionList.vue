<template>
  <v-card>
    <v-card-title>
      Questions
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addItem">Add Question</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
      @click:row="seeAnswers"
    ></v-data-table>
    <QuestionDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></QuestionDialog>
  </v-card>
</template>

<script>
import api from "../api";
import QuestionDialog from "@/components/QuestionDialog";
import router from "@/router";

export default {
  name: "QuestionList",
  components: { QuestionDialog },
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author.username" },
        { text: "Tag", value: "tag"},
        { text: "Text", value: "text" },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    seeAnswers(question) {
      router.push({
        path: "/questions/{{question.id}}",
        query: { question: question },
      });
    },
    addItem() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
