package softexcel.fedmedco

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(QueryService)
class QueryServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test a string"() {

        when: "String passed to service is nonsteroidal anti-inflammatory drug"
        def response = service.replaceSpaceWithPlus("nonsteroidal anti-inflammatory drug")

        then: "Expected answer is nonsteroidal+anti-inflammatory+drug"
        assert response == 'nonsteroidal+anti-inflammatory+drug'
    }


    void "test null string throws exception"(){
        when:
        service.replaceSpaceWithPlus(null)
        then:
        thrown(NullPointerException)
    }

    void "test empty string throws exception"(){
        when:
        service.replaceSpaceWithPlus('')
        then:
        thrown(NullPointerException)
    }

    void "test string with numbers"(){
        when:
        def response = service.replaceSpaceWithPlus('he is number1 player')
        then:
        assert response == 'he+is+number1+player'
    }


    void "test string with no spaces"(){
        when:
        def response = service.replaceSpaceWithPlus('anupam')
        then:
        assert response == 'anupam'

    }

}
