<template>
  <v-container>
    <v-layout row wrap>
      <v-flex xs12 sm6 offset-sm3>
        <v-card>
          <v-card-title>
            <span class="headline">Detailed Answer</span>
          </v-card-title>
          <h2>{{ this.answer.text }}</h2>
          <v-img
            :src="'data:image/png;base64,' + this.answer.image"
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
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
import api from "../api";
export default {
  name: "DetailedAnswer",
  data() {
    return {
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
      selectedItem: {},
    };
  },
  methods: {
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },
  },
};
</script>

<style scoped></style>
