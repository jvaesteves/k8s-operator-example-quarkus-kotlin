package org.acme

import io.fabric8.kubernetes.client.KubernetesClient
import io.javaoperatorsdk.operator.api.*
import javax.inject.Inject

@Controller(namespaces = ["default"])
class PingController : ResourceController<PingRequest> {
    @Inject
    lateinit var client: KubernetesClient

    override fun deleteResource(resource: PingRequest, context: Context<PingRequest>): DeleteControl {
        return DeleteControl.DEFAULT_DELETE
    }

    override fun createOrUpdateResource(resource: PingRequest, context: Context<PingRequest>): UpdateControl<PingRequest> {
        val status = resource.status
        println("PONG: ${resource.spec.payload}")
//        if (status != null) {
//            println("${status.state} ${status.error} ${status.message}")
//        }
        return UpdateControl.noUpdate()
    }

}