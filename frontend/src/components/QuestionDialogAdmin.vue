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
          <v-file-input
            v-model="imageFile"
            label="Image"
            @change="onFileSelected"
          />
          <v-text-field v-model="item.tag" label="Tag" />
          <v-text-field v-model="item.date" label="Date" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist"> Save </v-btn>
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
  name: "QuestionDialogAdmin",
  props: {
    item: Object,
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
    persist() {
      this.formData.set("image", this.imageFile);
      this.formData.set("id", this.item.id);
      this.formData.set("question", JSON.stringify(this.item));
      api.itemsAdmin.edit(this.formData).then(() => this.$emit("refresh"));
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
