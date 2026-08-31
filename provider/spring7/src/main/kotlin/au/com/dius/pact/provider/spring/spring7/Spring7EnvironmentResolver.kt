package au.com.dius.pact.provider.spring.spring7

import au.com.dius.pact.core.support.expressions.SystemPropertyResolver
import au.com.dius.pact.core.support.expressions.ValueResolver
import org.springframework.core.env.Environment

class Spring7EnvironmentResolver(private val environment: Environment) : ValueResolver {
    override fun resolveValue(property: String?): String? {
        return if (property != null) {
            val tuple = SystemPropertyResolver.PropertyValueTuple(property).invoke()
            val name = tuple.propertyName
            if (name != null) {
                environment.getProperty(name) ?: tuple.defaultValue
            } else {
                null
            }
        } else {
            null
        }
    }

    override fun resolveValue(property: String?, default: String?): String? {
        return if (property != null) {
            environment.getProperty(property) ?: default
        } else {
            default
        }
    }

    override fun propertyDefined(property: String) = environment.containsProperty(property)
}
