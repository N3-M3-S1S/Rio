package com.nemesis.rio.utils.checksum

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.math.BigInteger
import java.security.DigestInputStream
import java.security.MessageDigest

class MD5FileChecksumGenerator : FileChecksumGenerator {

    override suspend fun generateChecksum(file: File): String = withContext(Dispatchers.Default) {
        val md = MessageDigest.getInstance("MD5")
        DigestInputStream(file.inputStream(), md).use {
            @Suppress("ControlFlowWithEmptyBody", "BlockingMethodInNonBlockingContext")
            while (it.read() != -1); // read all bytes in the file to generate checksum
        }
        val rawChecksum = md.digest()
        BigInteger(1, rawChecksum).toString(16).padStart(32, '0') // convert raw checksum to string
    }
}
