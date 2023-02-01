package coroutineScope
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun CoroutineScope.blockingCall(ctx: CoroutineContext) =
    launch(ctx) {
        runBlocking {
            println("Taking delay")
            delay(500)
            println("foo bar")
        }
    }

fun CoroutineScope.suspendingCall(ctx: CoroutineContext) =
    launch(ctx) {
        println("Taking delay")
        delay(500)
        println("foo bar")
    }

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    println("Main start")
    val ctx = newSingleThreadContext("MyOwnThread")

    runBlocking {
        repeat(5) {
            suspendingCall(ctx)
            // blockingCall(ctx)
        }
    }

    println("Main end")
}