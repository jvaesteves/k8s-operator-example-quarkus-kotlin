package io.quarkiverse.operatorsdk.samples.pingpong

import io.fabric8.kubernetes.client.KubernetesClient
import io.javaoperatorsdk.operator.api.reconciler.*
import org.acme.crd.Ping
import org.acme.crd.Pong
import org.acme.crd.Status
import javax.inject.Inject

@ControllerConfiguration(namespaces = [Constants.WATCH_CURRENT_NAMESPACE])
class PingReconciler : Reconciler<Ping> {
    @Inject
    lateinit var client: KubernetesClient
    override fun reconcile(ping: Ping, context: Context): UpdateControl<Ping> {
        val status: Status = ping.status
        if (status != null && Status.State.PROCESSED === status.state) {
            return UpdateControl.noUpdate<Ping>()
        }
        val expectedPongResource: String = ping.metadata.name + "-pong"
        val pongResource = client.resources(Pong::class.java).withName(expectedPongResource)

        if (pongResource.get() == null) {
            val pong = Pong()
            pong.metadata.name = expectedPongResource
            pongResource.create(pong)
        }
        ping.status = Status(Status.State.PROCESSED)
        return UpdateControl.updateStatus<Ping>(ping)
    }
}