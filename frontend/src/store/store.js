import { createStore } from "vuex";
import axios from "axios";

const store = createStore({
    state() {
        return {
            backend: "http://localhost:8080/api/v1.0",

            currentUnit: {},

            currentRoot: {}
        }
    },
    mutations: {
        setCurrentUnit(state, unit) {
            state.currentUnit = unit;
        },
        setCurrentRoot(state, root) {
            state.currentRoot = root;
        }
    },
    actions: {
        async getUnitByIndex(context, index) {
            const url = this.state.backend + "/units/" + index
            let data = await axios.get(url)
            context.commit("setCurrentUnit", data.data.data)
        }
    },
    getters: {
        quizzesOfCurrentUnit: (state) => (_) => state.currentUnit.quizzes,
        specialSectionOfCurrentUnit: (state) => (_) => state.currentUnit.specialSectionWords

    }
})

export default store;