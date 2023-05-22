<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Edit answer </v-toolbar>
        <v-form>
          <v-text-field v-model="answer.text" label="Text" />
          <v-img :src="'data:image/png;base64,' + this.answer.image" height="300px"></v-img>
          <v-file-input
              v-model="imageFile"
              label="Image"
              @change="onFileSelected"
          />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist()"> Save </v-btn>
          <v-btn @click="remove" v-if="isNew === false">
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
  name: "AnswerDialog",
  props: {
    answer: Object,
    opened: Boolean,
  },
  data() {
    return {
      imageFile: null,
      formData: new FormData(),
    };
  },
  methods: {
    onFileSelected(event) {
      this.imageFile = event.target.files[0];
    },
    remove() {
      api.answers
          .remove(this.answer)
          .then(() => this.$emit("refresh"));
    },
    persist() {
      this.formData.set("image", this.imageFile);
      this.formData.set("id", this.answer.id);
      this.formData.set("answer", JSON.stringify(this.answer));
      api.answers
          .edit(this.formData)
          .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.answer.id;
    },
  },
};
</script>

<style scoped></style>