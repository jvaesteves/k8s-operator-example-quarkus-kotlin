package org.acme.resources.ping

import io.fabric8.kubernetes.client.KubernetesClient
import io.javaoperatorsdk.operator.api.*
import javax.inject.Inject

@Controller(namespaces = [Controller.WATCH_CURRENT_NAMESPACE])
class PingController : ResourceController<Ping> {
    @Inject
    lateinit var client: KubernetesClient

    override fun deleteResource(resource: Ping, context: Context<Ping>): DeleteControl {
        return DeleteControl.DEFAULT_DELETE
    }

    override fun createOrUpdateResource(resource: Ping, context: Context<Ping>): UpdateControl<Ping> {
        println("PONG: ${resource.spec.payload}")
        return UpdateControl.noUpdate()
    }

}