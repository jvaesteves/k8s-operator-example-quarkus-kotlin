package org.acme

import io.javaoperatorsdk.operator.Operator
import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain
import javax.inject.Inject


@QuarkusMain
class Main : QuarkusApplication {
    @Inject
    lateinit var operator: Operator

    override fun run(vararg args: String): Int {
        operator.start()
        Quarkus.waitForExit()
        return 0
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Quarkus.run(Main::class.java, *args)
        }
    }
}
