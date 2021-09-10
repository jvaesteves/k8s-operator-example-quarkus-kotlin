package org.acme.controller

import io.javaoperatorsdk.operator.api.*
import org.acme.crd.Pong

@Controller(namespaces=[Controller.WATCH_CURRENT_NAMESPACE])
class PongController : ResourceController<Pong> {

    override fun deleteResource(resource: Pong, context: Context<Pong>): DeleteControl {
        return DeleteControl.DEFAULT_DELETE
    }

    override fun createOrUpdateResource(resource: Pong, context: Context<Pong>): UpdateControl<Pong> {
        println("PONG: Received status ${resource.spec.statusCode} for URL ${resource.spec.url}")
        return UpdateControl.noUpdate()
    }
}