package com.nemesis.rio.utils.checksum

import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class MD5FileChecksumGeneratorTest {
    private lateinit var temporaryFile: File

    @BeforeTest
    fun init() {
        temporaryFile = File.createTempFile("file", null).apply { deleteOnExit() }
    }

    @Test
    fun `generate same checksums for same file`() = runBlocking {
        temporaryFile.writeBytes(byteArrayOf(1, 2, 3))
        val md5ChecksumGenerator = MD5FileChecksumGenerator()
        val firstChecksum = md5ChecksumGenerator.generateChecksum(temporaryFile)
        val secondChecksum = md5ChecksumGenerator.generateChecksum(temporaryFile)
        assertEquals(firstChecksum, secondChecksum)
    }

    @Test
    fun `generate different checksums if file changed`() = runBlocking {
        val md5ChecksumGenerator = MD5FileChecksumGenerator()
        temporaryFile.writeBytes(byteArrayOf(1, 2, 3))
        val firstChecksum = md5ChecksumGenerator.generateChecksum(temporaryFile)
        temporaryFile.appendBytes(byteArrayOf(4, 5, 6))
        val secondChecksum = md5ChecksumGenerator.generateChecksum(temporaryFile)
        assertNotEquals(firstChecksum, secondChecksum)
    }
}
