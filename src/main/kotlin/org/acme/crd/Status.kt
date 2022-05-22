package org.acme.crd

class Status {
    enum class State {
        PROCESSED, UNKNOWN
    }

    var state = State.UNKNOWN

    constructor() {}
    constructor(state: State) {
        this.state = state
    }
}