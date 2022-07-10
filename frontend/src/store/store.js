import { createStore } from "vuex";
const USER_TOKEN_LOCALSTORAGE_KEY = "USER_TOKEN_LOCALSTORAGE_KEY";
const store = createStore({
    state() {
        return {
            allUnits: new Array(30),
            theIntro: "",
            currentRootIndex: -1,
            currentUnitIndex: -1,
            user: {
                token: ""
            }
        }
    },
    mutations: {
        cacheCurrentUnit(state, unit) {
            let index = unit.index - 1
            state.currentUnitIndex = index
            state.allUnits[index] = unit
        },
        setCurrentUnitIndex(state, unitIndex) {
            state.currentUnitIndex = unitIndex - 1
        },
        setCurrentRootIndex(state, index) {
            state.currentRootIndex = index;
        },
        incCurrentRootIndex(state) {
            const roots = state.allUnits[state.currentUnitIndex].roots;
            if (state.currentRootIndex < roots.length - 1) {
                state.currentRootIndex += 1
            }
        },
        decCurrentRootIndex(state) {
            if (state.currentRootIndex > 0) {
                state.currentRootIndex -= 1
            }

        },
        setTheIntro(state, intro) {
            state.theIntro = intro;
        },
        setUserToken(state, token) {
            window.localStorage.setItem(USER_TOKEN_LOCALSTORAGE_KEY, token)
            state.user.token = token;
        },
        clearUserToken(state) {
            window.localStorage.removeItem(USER_TOKEN_LOCALSTORAGE_KEY)
            state.user.token = ""
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
        quizzesOfCurrentUnit: (state) => {
            return state.allUnits[state.currentUnitIndex].quizzes
        },

        specialSectionOfCurrentUnit: (state) => {
            return state.allUnits[state.currentUnitIndex].specialSectionWords
        },

        currentRootCache: (state) => {
            const unit = state.allUnits[state.currentUnitIndex];
            return unit.roots[state.currentRootIndex]
        },
        currentUnitCache: (state) => state.allUnits[state.currentUnitIndex],
        userToken: (state) => {
            if (state.user.token) {
                return state.user.token;
            }
            return window.localStorage.getItem(USER_TOKEN_LOCALSTORAGE_KEY)
        }
    }
})

export default store;