package nu.alde.fourleafclover.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Project(
        @JacksonXmlProperty(isAttribute = true) val timestamp: String,
        @JacksonXmlProperty(isAttribute = true) val name: String,
        @JacksonXmlProperty(isAttribute = false) val metrics: Metrics,
        @JacksonXmlProperty(isAttribute = false, localName = "package") @JacksonXmlElementWrapper(useWrapping = false) val packages: List<CloverPackage> = emptyList()
)
