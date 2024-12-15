package no.sondre

import jakarta.annotation.Priority
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Alternative
import org.eclipse.microprofile.openapi.OASFilter
import org.eclipse.microprofile.openapi.models.media.Schema


@Alternative
@Priority(1)
@ApplicationScoped
class CustomOpenAPIFilter : OASFilter {
    //
    override fun filterSchema(schema: Schema?): Schema? {
        val properties = schema?.properties
        if (properties != null) {
            val toToKeep = schema.required.toList()
            val mutableProperties = properties.toMutableMap()
            schema.properties = mutableProperties.filter { it.key in toToKeep }
        }
        return schema
    }
}
