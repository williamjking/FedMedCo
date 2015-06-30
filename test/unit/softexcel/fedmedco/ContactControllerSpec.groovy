package softexcel.fedmedco


import grails.test.mixin.*
import spock.lang.*

@TestFor(ContactController)
@Mock(Contact)
class ContactControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        !model.contactInstanceList
        model.contactInstanceCount == 0
    }

}
