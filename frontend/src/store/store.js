import { createStore } from "vuex";
const USER_TOKEN_LOCALSTORAGE_KEY = "USER_TOKEN_LOCALSTORAGE_KEY";
const store = createStore({
    state() {
        return {
            allUnits: new Array(30),

            theIntro: "",
            currentRoot: {},
            user: {
                token: ""
            }
        }
    },
    mutations: {
        setCurrentUnit(state, unit) {
            let index = unit.index - 1
            state.allUnits[index] = unit
        },
        setCurrentRoot(state, root) {
            state.currentRoot = root;
        },
        setTheIntro(state, intro) {
            state.theIntro = intro;
        },
        setUserToken(state, token) {
            localStorage.setItem(USER_TOKEN_LOCALSTORAGE_KEY, token)
            state.user.token = token;
        }
    },
    // actions: {
    // async getUnitByIndex(context, index) {
    //     const url = th is.state.backend + "/book/units/" + index
    //     let data = await axios.get(url)
    //     context.commit("setCurrentUnit", data.data.data)
    // },

    // async getTheIntro(context) {
    //     if (context.state.theIntro === null) {
    //         const url = this.state.backend + "/book/intro"
    //         let data = await axios.get(url)
    //         context.commit("setTheIntro", data.data.data)
    //     }
    // }
    // },
    getters: {
        quizzesOfCurrentUnit: (state) => (unitIndex) => {
            const index = parseInt(unitIndex) - 1
            return state.allUnits[index].quizzes
        },

        specialSectionOfCurrentUnit: (state) => (unitIndex) => {
            const index = parseInt(unitIndex) - 1
            return state.allUnits[index].specialSectionWords
        },

        currentUnitCache: (state) => (unitIndex) => state.allUnits[unitIndex - 1],
        userToken: (state) => {
            if (state.user.token) {
                return state.user.token;
            }
            return localStorage.getItem(USER_TOKEN_LOCALSTORAGE_KEY)
        }
    }
})

export default store;