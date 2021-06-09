package com.nemesis.rio.utils.checksum

import java.io.File

interface FileChecksumGenerator {
    suspend fun generateChecksum(file: File): String
}
