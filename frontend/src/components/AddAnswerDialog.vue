<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create answer" : "Edit question" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="answer.text" label="Title" />
          <v-file-input
              v-model="imageFile"
              label="Image"
              @change="onFileSelected"
          />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "AddAnswerDialog",
  props: {
    answer: Object,
    question: Object,
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
    persist() {
      this.formData.set("image", this.imageFile);
      this.formData.set("answer", JSON.stringify(this.answer));
      if (this.isNew) {
        this.formData.set("question_id", this.question.id);
        this.formData.set("user_id", this.$store.getters["auth/getId"]);
        api.answers.create(this.formData).then(() => this.$emit("refresh"));
      } else {
        this.formData.set("id", this.item.id);
        api.answers.edit(this.formData).then(() => this.$emit("refresh"));
      }
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
