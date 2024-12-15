package no.sondre

import jakarta.annotation.Priority
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Alternative
import jakarta.inject.Qualifier
import org.eclipse.microprofile.openapi.OASFilter
import org.eclipse.microprofile.openapi.models.media.Schema
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties


//@Singleton
//class RegisterCustomModuleCustomizer : ObjectMapperCustomizer {
//    override fun customize(objectMapper: ObjectMapper) {
//        // Deserialization is as it should, without this, the only problem is OpenAPI
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.PUBLIC_ONLY)
////        objectMapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)
////        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
////        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
//    }
//}
//
//
//@ApplicationScoped
//class OpenApiCustomizer {
//
//    @Produces
//    fun customizeOpenApiObjectMapper(): ObjectMapper {
//        val o = ObjectMapper()
//        o.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.PUBLIC_ONLY)
//        return o
//    }
//}

@Alternative
@Priority(1)
@ApplicationScoped
class CustomOpenAPIFilter : OASFilter {
    //
//    override fun filterSchema(schema: Schema?): Schema? {
//        val properties = schema?.properties
//        if (properties != null) {
//            val mutableProperties = properties.toMutableMap()
//
//            val toRemove = mutableListOf<String>()
//            schema.javaClass.kotlin.declaredMemberProperties.forEach { member ->
//                if (member.visibility == KVisibility.PRIVATE) {
//                    toRemove.add(member.name)
//                }
//            }
//            toRemove.forEach { mutableProperties.remove(it) }
//            schema.properties = mutableProperties
//        }
//        return schema
//    }
    override fun filterSchema(schema: Schema): Schema? {
        val properties = schema.properties
        if (properties != null) {
            // Create a mutable copy of the properties map
            val mutableProperties = properties.toMutableMap()

            // Get the class name of the original data class (if available)
            val className = schema.javaClass::getName.name
            try {
                // Load the data class using reflection
                val clazz = Class.forName(className).kotlin

                // Check the visibility of each property
                val fieldsToRemove = mutableListOf<String>()
                for (fieldName in properties.keys) {
                    val property = clazz.memberProperties.find { it.name == fieldName }
                    if (property != null && property.visibility == KVisibility.PRIVATE) {
                        fieldsToRemove.add(fieldName)
                    }
                }

                // Remove private fields from the mutable map
                fieldsToRemove.forEach { mutableProperties.remove(it) }
            } catch (e: ClassNotFoundException) {
                // Class not found, log or handle gracefully
                println("Class $className not found: ${e.message}")
            }

            // Replace the schema's properties with the updated map
            schema.properties = mutableProperties
        }
        return schema
    }
}

//@ApplicationScoped
//class JacksonConfig {
//
//    @Produces
//    fun objectMapper(): ObjectMapper {
//        return ObjectMapper()
//            .setVisibility(
//                PropertyAccessor.FIELD,
//                JsonAutoDetect.Visibility.NONE
//            )
//            .setVisibility(
//                PropertyAccessor.GETTER,
//                JsonAutoDetect.Visibility.PUBLIC_ONLY
//            )
//            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
//    }
//}

