package callbacks

import kotlinx.coroutines.delay
import kotlin.system.measureTimeMillis

fun getTokenAsync(cb: (String) -> Unit) {
    Thread.sleep(1000)
    println("Got Token")
    cb("some_token")
}

fun createPostAsync(token: String, item: String, cbSuc: (String) -> Unit, cbErr: (String) -> Unit) {
    println("Creating Post...")
    Thread.sleep(2000)
    println("Post Created, Checking token...")
    if (token == "some_token") {
        println("Checked Success")
        cbSuc(item)
    } else {
        cbErr("Error with some token")
    }
}

fun processPost(post: String) {
    println(post)
}

fun main() {
    getTokenAsync { token ->
        createPostAsync(token, "Post #1", {
            post -> processPost(post)
        }, {
            err -> println(err)
        })
    }
}