package nu.alde.fourleafclover.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class File(
        @JacksonXmlProperty(isAttribute = true) val name: String,
        @JacksonXmlProperty(isAttribute = true) val path: String,
        @JacksonXmlProperty(isAttribute = false) val metrics: Metrics,
        @JacksonXmlProperty(isAttribute = false, localName = "line") @JacksonXmlElementWrapper(useWrapping = false) val lines: List<Line> = emptyList()
)
