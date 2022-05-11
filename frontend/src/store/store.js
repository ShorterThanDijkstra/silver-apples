import { createStore } from "vuex";
import axios from "axios";

const store = createStore({
    state() {
        return {
            backend: "http://localhost:8080/api/v1.0",
            allRoots: [],
            currentRoot: {},
            currentUnit: {}
        }
    },
    mutations: {
        init(state, allRoots) {
            state.allRoots = allRoots;
        },
        setCurrentRoot(state, root) {
            state.currentRoot = root;
        },
        setCurrentUnit(state, index) {
            let roots = state.allRoots[parseInt(index) - 1];
            state.currentUnit = { index: index, roots: roots }
        }
    },
    actions: {
        async allRoots(context) {
            const url = this.state.backend + "/roots"
            let data = await axios.get(url)
            context.commit("init", data.data.data)
        }
    },
})

export default store;