package no.sondre

//import jakarta.annotation.Priority
//import jakarta.enterprise.context.ApplicationScoped
//import jakarta.enterprise.inject.Alternative
//import org.eclipse.microprofile.openapi.OASFilter
//import org.eclipse.microprofile.openapi.models.media.Schema
//

// TODO: this will hide all nullable fields, and that is not what we want
//  what we want is to hide all PRIVATE fields, but I am not sure how to do it without
//  adding annotations...
//@Alternative
//@Priority(1)
//@ApplicationScoped
//class CustomOpenAPIFilter : OASFilter {
//    override fun filterSchema(schema: Schema?): Schema? {
//        val properties = schema?.properties
//        if (properties != null) {
//            val toToKeep = schema.required.toList()
//            val mutableProperties = properties.toMutableMap()
//            schema.properties = mutableProperties.filter { it.key in toToKeep }
//        }
//        return schema
//    }
//}
