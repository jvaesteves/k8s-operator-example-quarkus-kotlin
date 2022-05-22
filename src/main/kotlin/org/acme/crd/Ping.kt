package org.acme.crd

import io.fabric8.kubernetes.api.model.Namespaced
import io.fabric8.kubernetes.client.CustomResource
import io.fabric8.kubernetes.model.annotation.Group
import io.fabric8.kubernetes.model.annotation.PrinterColumn
import io.fabric8.kubernetes.model.annotation.Version

@Group("org.acme")
@Version("v1")
class Ping : CustomResource<PingSpec, Status>(), Namespaced {
    override fun initSpec() = PingSpec()
    override fun initStatus() = Status()
}

class PingSpec {
    lateinit var url: String
}

class PingStatus {
    @PrinterColumn
    var requestStatus: Status = Status(Status.State.UNKNOWN)

    @PrinterColumn
    var statusCode: Int? = null
}