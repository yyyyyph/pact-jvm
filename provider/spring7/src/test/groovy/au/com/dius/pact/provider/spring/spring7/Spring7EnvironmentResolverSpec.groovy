package au.com.dius.pact.provider.spring.spring7

import au.com.dius.pact.core.support.expressions.DataType
import au.com.dius.pact.core.support.expressions.ExpressionParser
import org.springframework.mock.env.MockEnvironment
import spock.lang.Specification

@SuppressWarnings('GStringExpressionWithinString')
class Spring7EnvironmentResolverSpec extends Specification {

  def 'resolves expression without default from Spring environment'() {
    given:
    def environment = new MockEnvironment().withProperty('PACT_FLOW_URL', 'https://example.pactflow.io')
    def resolver = new Spring7EnvironmentResolver(environment)

    expect:
    new ExpressionParser().parseExpression('${PACT_FLOW_URL}', DataType.RAW, resolver) == 'https://example.pactflow.io'
  }

  def 'resolves expression default when property is not defined'() {
    given:
    def resolver = new Spring7EnvironmentResolver(new MockEnvironment())

    expect:
    new ExpressionParser().parseExpression('${PACT_FLOW_URL::https//default.pactflow.io}', DataType.RAW, resolver) ==
      'https://default.pactflow.io'
  }

  def 'resolves property with nullable default from Spring environment'() {
    given:
    def environment = new MockEnvironment().withProperty('PACT_FLOW_TOKEN', 'token-value')
    def resolver = new Spring7EnvironmentResolver(environment)

    expect:
    resolver.resolveValue('PACT_FLOW_TOKEN', null) == 'token-value'
  }
}
