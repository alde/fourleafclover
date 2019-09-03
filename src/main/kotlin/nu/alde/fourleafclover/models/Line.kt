package nu.alde.fourleafclover.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty


data class Line(
        @JacksonXmlProperty(isAttribute = true) val num: Int,
        @JacksonXmlProperty(isAttribute = true) val count: Int,
        @JacksonXmlProperty(isAttribute = true) val type: String,
        @JacksonXmlProperty(isAttribute = true) val truecount: Int?,
        @JacksonXmlProperty(isAttribute = true) val falsecount: Int?
)
