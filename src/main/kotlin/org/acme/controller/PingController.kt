package org.acme.controller

import io.fabric8.kubernetes.client.KubernetesClient
import io.javaoperatorsdk.operator.api.*
import org.acme.crd.Ping
import org.acme.crd.PingStatus
import org.acme.crd.Pong
import org.acme.crd.PongSpec
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import javax.inject.Inject

@Controller(namespaces = [Controller.WATCH_CURRENT_NAMESPACE])
class PingController : ResourceController<Ping> {
    @Inject
    lateinit var k8sClient: KubernetesClient

    private val httpClient: HttpClient = HttpClient.newHttpClient()

    private fun buildHttpRequest(url: String): HttpRequest {
        return HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build()
    }

    private fun updateResourceStatus(resource: Ping, status: PingStatus.Status): UpdateControl<Ping> {
        resource.status.requestStatus = status
        return UpdateControl.updateCustomResourceAndStatus(resource)
    }

    private fun buildPongResource(resource: Ping): UpdateControl<Ping> {
        val request = buildHttpRequest(resource.spec.url)
        val response = httpClient.send(request, BodyHandlers.ofString())

        val pong = Pong().apply {
            spec = PongSpec().apply {
                url = response.uri().toString()
                statusCode = response.statusCode()
            }
        }

        k8sClient.resources(Pong::class.java).create(pong)
        return updateResourceStatus(resource, PingStatus.Status.COMPLETED)
    }

    override fun deleteResource(resource: Ping, context: Context<Ping>): DeleteControl {
        return DeleteControl.DEFAULT_DELETE
    }

    override fun createOrUpdateResource(resource: Ping, context: Context<Ping>): UpdateControl<Ping> {
        if (resource.status == null) {
            println("Started request for URL: ${resource.spec.url}")
            resource.status = PingStatus()
            return UpdateControl.updateCustomResourceAndStatus(resource)
        }
        println("[${resource.status.requestStatus}] Received PING request for URL: ${resource.spec.url}")
        return when (resource.status.requestStatus) {
            PingStatus.Status.CREATED -> updateResourceStatus(resource, PingStatus.Status.PROCESSING)
            PingStatus.Status.PROCESSING -> buildPongResource(resource)
            else -> UpdateControl.noUpdate()
        }
    }

}