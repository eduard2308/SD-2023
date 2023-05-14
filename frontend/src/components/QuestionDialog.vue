<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create quesion" : "Edit question" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="item.title" label="Title" />
          <v-text-field v-model="item.text" label="Text" />
          <v-text-field v-model="item.tag" label="Tag" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="remove" v-if = "isNew === false">
            {{ "Delete" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "QuestionDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    remove() {
        api.items
            .remove({
              id: this.item.id,
              title: this.item.title,
              tag: this.item.tag,
              text: this.item.text,
              date: this.item.date,
            })
            .then(() => this.$emit("refresh"));
    },
    persist() {
      if (this.isNew) {
        api.items
          .create({
            title: this.item.title,
            tag: this.item.tag,
            text: this.item.text,
            author: this.$store.getters["auth/getUser"],
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.items
          .edit({
            id: this.item.id,
            title: this.item.title,
            tag: this.item.tag,
            text: this.item.text,
          })
          .then(() => this.$emit("refresh"));
      }
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
