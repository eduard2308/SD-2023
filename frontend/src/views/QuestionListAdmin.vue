<template>
  <v-card>
    <v-card-title>
      Questions
      <v-btn small @click="switchToUsers()">Users</v-btn>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
      @click:row="editItem"
    ></v-data-table>
    <QuestionDialogAdmin
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></QuestionDialogAdmin>
  </v-card>
</template>

<script>
import api from "../api";
import QuestionDialogAdmin from "../components/QuestionDialogAdmin";

export default {
  name: "ItemList",
  components: { QuestionDialogAdmin },
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
    editItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.itemsAdmin.allItems();
    },
    switchToUsers(){
        this.$router.push('/users');
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
