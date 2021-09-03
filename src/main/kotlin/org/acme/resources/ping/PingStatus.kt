package org.acme.resources.ping

class PingStatus {
    lateinit var state: String
    var error: Boolean = false
    lateinit var message: String
}