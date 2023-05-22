import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import QuestionList from "../views/QuestionList.vue";
import AnswerList from "../views/AnswerList.vue";
import QuestionListAdmin from "../views/QuestionListAdmin.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import AnswerListAdmin from "@/views/AnswerListAdmin";
import DetailedAnswer from "@/views/DetailedAnswer";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Questions" });
      }
    },
  },
  {
    path: "/questions/",
    name: "Questions",
    component: QuestionList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/questions/admin",
    name: "Questions",
    component: QuestionListAdmin,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/questions/:id",
    name: "Answers",
    component: AnswerList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/answers/:id",
    name: "DetailedAnswer",
    component: DetailedAnswer,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/questions/admin/:id",
    name: "AnswersAdmin",
    component: AnswerListAdmin,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
];

const router = new VueRouter({
  routes,
});

export default router;
