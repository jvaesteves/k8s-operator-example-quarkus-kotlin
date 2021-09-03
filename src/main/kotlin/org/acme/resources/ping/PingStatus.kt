package org.acme.resources.ping

class PingStatus {
    enum class Status {
        CREATED, PROCESSING, COMPLETED
    }
    var requestStatus: Status = Status.CREATED
    var statusCode: Int? = null
}