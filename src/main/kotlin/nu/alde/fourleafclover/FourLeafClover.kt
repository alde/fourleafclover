package nu.alde.fourleafclover

import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import nu.alde.fourleafclover.models.CloverReport
import java.io.File
import java.nio.file.Path


class FourLeafClover {
    /**
     * @param report Clover XML file content.
     */
    fun process(report: String): CloverReport {
        return fromXml(report, CloverReport::class.java)
    }

    /**
     * @param reportFile Path to where report file can be found.
     */
    fun process(reportFile: Path): CloverReport {
        return fromXml(reportFile.toFile(), CloverReport::class.java)
    }

    companion object Mapper {
        private val XML = XmlMapper()
                .registerModule(
                        SimpleModule().addDeserializer<Any>(
                                Any::class.java, CustomUntypedObjectDeserializer()))
                .registerModule(KotlinModule())

        fun <T> fromXml(value: String, clazz: Class<T>): T {
            return XML.readValue(value, clazz)
        }

        fun <T> fromXml(file: File, clazz: Class<T>): T {
            return XML.readValue(file, clazz)
        }
    }
}
