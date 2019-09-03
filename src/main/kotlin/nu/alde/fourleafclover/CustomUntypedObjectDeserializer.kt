package nu.alde.fourleafclover

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer

/**
 * The CustomUntypedObjectDeserializer will ensure that
 * ```
 * <x>
 *     <y />
 *     <y />
 * </x>
 * ```
 * is properly deserialized into a List of `y` elements.
 */
class CustomUntypedObjectDeserializer : UntypedObjectDeserializer(null, null) {

    override fun mapObject(parser: JsonParser, context: DeserializationContext): Any {
        val firstKey = firstKey(parser, context) ?: return emptyMap<String, Any>()
        parser.nextToken()
        val resultMap = LinkedHashMap<String, Any>()
        resultMap[firstKey] = deserialize(parser, context)
        return process(parser, context, resultMap)
    }

    private fun process(parser: JsonParser,
                        context: DeserializationContext,
                        collector: LinkedHashMap<String, Any>): Map<String, Any?> {
        val key = parser.nextFieldName() ?: return collector

        parser.nextToken()
        if (key !in collector) {
            collector[key] = deserialize(parser, context)
            return process(parser, context, collector)
        }

        if (collector[key] is MutableList<*>) {
            collector[key] = listOf(collector[key], deserialize(parser, context))
        } else {
            collector[key] = listOf(collector[key])
        }

        return process(parser, context, collector)
    }

    private fun firstKey(parser: JsonParser, context: DeserializationContext): String? {
        val currentToken = parser.currentToken
        if (currentToken == JsonToken.START_OBJECT) {
            return parser.nextFieldName()
        }
        if (currentToken == JsonToken.FIELD_NAME) {
            return parser.currentName
        }
        if (currentToken != JsonToken.END_OBJECT) {
            context.handleUnexpectedToken(handledType(), parser)
        }
        return null
    }
}
