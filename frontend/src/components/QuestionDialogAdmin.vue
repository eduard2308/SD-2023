<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Edit question </v-toolbar>
        <v-form>
          <v-text-field v-model="item.title" label="Title" />
          <v-text-field v-model="item.text" label="Text" />
          <v-text-field v-model="item.tag" label="Tag" />
          <v-text-field v-model="item.date" label="Date" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist()"> Save </v-btn>
          <v-btn @click="remove" v-if="isNew === false">
            {{ "Delete" }}
          </v-btn>
          <v-btn @click="seeAnswers"> View Answers </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
import router from "@/router";

export default {
  name: "QuestionDialogAdmin",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    remove() {
      api.itemsAdmin
          .remove({
            id: this.item.id,
            title: this.item.title,
            tag: this.item.tag,
            text: this.item.text,
            date: this.item.date,
          })
          .then(() => this.$emit("refresh"));
    },
    seeAnswers() {
      router.push({
        path: "/questions/admin/{{question.id}}",
        query: { question: this.item },
      });
    },
    persist() {
      api.itemsAdmin
          .edit({
            id: this.item.id,
            title: this.item.title,
            text: this.item.text,
            tag: this.item.tag,
          })
          .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.item.id;
    },
  },
};
</script>

<style scoped></style>