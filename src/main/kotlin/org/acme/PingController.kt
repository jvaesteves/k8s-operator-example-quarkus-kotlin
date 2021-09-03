package org.acme

import io.fabric8.kubernetes.client.KubernetesClient
import io.javaoperatorsdk.operator.api.*
import javax.inject.Inject

@Controller(namespaces = [Controller.WATCH_CURRENT_NAMESPACE])
class PingController : ResourceController<PingRequest> {
    @Inject
    lateinit var client: KubernetesClient

    override fun deleteResource(resource: PingRequest, context: Context<PingRequest>): DeleteControl {
        return DeleteControl.DEFAULT_DELETE
    }

    override fun createOrUpdateResource(resource: PingRequest, context: Context<PingRequest>): UpdateControl<PingRequest> {
        println("PONG: ${resource.spec.payload}")
        return UpdateControl.noUpdate()
    }

}