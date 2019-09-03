package nu.alde.fourleafclover.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class CloverPackage(
        @JacksonXmlProperty(isAttribute = true) val name: String,
        @JacksonXmlProperty(isAttribute = false) val metrics: Metrics,
        @JacksonXmlProperty(isAttribute = false, localName = "file") @JacksonXmlElementWrapper(useWrapping = false) val files: List<File> = emptyList()
)
