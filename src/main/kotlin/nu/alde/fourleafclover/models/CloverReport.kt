package nu.alde.fourleafclover.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "coverage")
data class CloverReport(
    @JacksonXmlProperty(isAttribute = true) val generated: String,
    @JacksonXmlProperty(isAttribute = true, localName = "clover") val version: String,
    @JacksonXmlProperty(isAttribute = false) val project: Project
)
