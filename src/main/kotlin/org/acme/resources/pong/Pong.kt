package org.acme.resources.pong

import io.fabric8.kubernetes.api.model.Namespaced
import io.fabric8.kubernetes.client.CustomResource
import io.fabric8.kubernetes.model.annotation.Group
import io.fabric8.kubernetes.model.annotation.Version
import io.fabric8.kubernetes.model.annotation.Singular

@Group("org.acme")
@Version("v1")
@Singular("pong")
class Pong : CustomResource<PongSpec, Void>(), Namespaced