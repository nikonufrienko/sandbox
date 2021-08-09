package com.company.test

import kotlinx.coroutines.delay
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.ConcurrentLinkedDeque

class ICMPPing {
    private val currentProcess: Process = ProcessBuilder("sh").redirectErrorStream(true).start()

    suspend fun performPingWithArgs(args: String, linesDeque: ConcurrentLinkedDeque<String>) {
        val os = DataOutputStream(currentProcess.outputStream)
        os.writeBytes("ping $args\n")
        os.flush()
        val reader = BufferedReader(InputStreamReader(currentProcess.inputStream))
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                linesDeque.addLast(line)
                delay(10)
            }
        } catch (e: IOException) {
        }
    }

    fun justPingByHost(host: String, currValueGetter: (String) -> Unit) {
        val os = DataOutputStream(currentProcess.outputStream)
        os.writeBytes("ping $host\n")
        os.flush()
        val reader = BufferedReader(InputStreamReader(currentProcess.inputStream))
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                line!!.split(" ").forEach {
                    if (it.contains("time="))
                        currValueGetter(it.split("=")[1])
                }
            }
        } catch (e: IOException) {
        }
    }

    fun stopExecuting() {
        currentProcess.destroy()
    }
}
