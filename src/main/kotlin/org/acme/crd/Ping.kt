package org.acme.crd

import io.fabric8.kubernetes.api.model.Namespaced
import io.fabric8.kubernetes.client.CustomResource
import io.fabric8.kubernetes.model.annotation.Group
import io.fabric8.kubernetes.model.annotation.PrinterColumn
import io.fabric8.kubernetes.model.annotation.Version

@Group("org.acme")
@Version("v1")
class Ping : CustomResource<PingSpec, PingStatus>(), Namespaced

class PingSpec {
    lateinit var url: String
}

class PingStatus {
    enum class Status {
        CREATED, PROCESSING, COMPLETED
    }
    @PrinterColumn
    var requestStatus: Status = Status.CREATED

    @PrinterColumn
    var statusCode: Int? = null
}