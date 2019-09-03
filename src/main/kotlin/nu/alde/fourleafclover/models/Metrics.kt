package nu.alde.fourleafclover.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty


data class Metrics(
        @JacksonXmlProperty(isAttribute = true) val statements: Int,
        @JacksonXmlProperty(isAttribute = true, localName = "coveredstatements") val coveredStatements: Int,
        @JacksonXmlProperty(isAttribute = true) val conditionals: Int,
        @JacksonXmlProperty(isAttribute = true, localName = "coveredconditionals") val coveredConditionals: Int,
        @JacksonXmlProperty(isAttribute = true) val methods: Int,
        @JacksonXmlProperty(isAttribute = true, localName = "coveredmethods") val coveredMethods: Int,
        @JacksonXmlProperty(isAttribute = true) val elements: Int,
        @JacksonXmlProperty(isAttribute = true, localName = "coveredelements") val coveredElements: Int,
        @JacksonXmlProperty(isAttribute = true) val complexity: Int,
        @JacksonXmlProperty(isAttribute = true, localName = "loc") val linesOfCode: Int,
        @JacksonXmlProperty(isAttribute = true, localName = "ncloc") val nonCommentLinesOfCode: Int,
        @JacksonXmlProperty(isAttribute = true) val packages: Int,
        @JacksonXmlProperty(isAttribute = true) val files: Int,
        @JacksonXmlProperty(isAttribute = true) val classes: Int
) {
    private fun percent(numerator: Int, denominator: Int) : Double {
        if (denominator == 0) {
            return 0.0
        }
        return (numerator.toDouble() / denominator) * 100
    }
    fun statementsPercent(): Double {
        return percent(coveredStatements, statements)
    }

    fun conditionalsPercent(): Double {
        return percent(coveredConditionals, conditionals)
    }
    fun methodsPercent(): Double {
        return percent(coveredMethods, methods)
    }
    fun elementsPercent(): Double {
        return percent(coveredElements, elements)
    }
}
