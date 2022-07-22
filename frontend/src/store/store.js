import { createStore } from "vuex";
const USER_LOCALSTORAGE_KEY = "USER_LOCALSTORAGE_KEY";
const store = createStore({
    state() {
        return {
            allUnits: new Array(30),
            theIntro: "",
            currentRootIndex: -1,
            currentUnitIndex: -1,
            user: {
                username: "",
                email: "",
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
        setUser(state, user) {
            localStorage.setItem(USER_LOCALSTORAGE_KEY, JSON.stringify(user))
            state.user = user;
        },
        clearUser(state) {
            localStorage.removeItem(USER_LOCALSTORAGE_KEY)
            state.user = {}
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
        userToken: (state, getters) => {
            const user = getters.userCache
            if (user) {
                return user.token
            }
        },
        userCache: (state) => {
            if (state.user.token && state.user.username && state.user.email) {
                return state.user;
            }
            const user = localStorage.getItem(USER_LOCALSTORAGE_KEY)
            if (user) { return JSON.parse(user) }

        },
        findWordInCurrentRoot: (state) => (name) => {
            const unit = state.allUnits[state.currentUnitIndex];
            const words = unit.roots[state.currentRootIndex].words
            return words.find(word => word.spell === name)
        }
    }
})

export default store;