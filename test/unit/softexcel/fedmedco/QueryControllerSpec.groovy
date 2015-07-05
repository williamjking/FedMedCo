package softexcel.fedmedco


import grails.test.mixin.*
import groovy.json.JsonSlurper
import groovy.mock.interceptor.MockFor
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import spock.lang.*


@TestFor(QueryController)
@Mock([Query, FieldList])
class QueryControllerSpec extends Specification {

    QueryService queryService = new QueryService()


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }


    void "Test the index action returns the correct model"() {

        when: "The index action is executed with no error message"
        controller.index(null, null, "Error Message is null")

        then: "The model is correct"
        view == '/query/index'
        model.queryResults == 'Error Message is null'
    }

    void "Test the create action returns the correct model"() {
        when: "The index action is executed when error message is passed"
        controller.index(null, "error", null)

        then: "The model is correctly created"
        view == '/query/index'
        model.queryInstance instanceof Query
        model.queryInstance.hasErrors() ==  true
    }

    void "Test that correct field list is returned"() {
        when: "populate field is called with wrong fields a null list is returned"
        controller.populateFields("drug", "dummy")
        then:
        response.text == '[]'

    }

    void "Test Field List for faers"(){
        FieldList.create("faers", ['faerslist'], true)

        when: "populate field is called with drug and event"
        controller.populateFields("drug", "event")
        then:
        response.text == '[\'faerslist\']'

    }

    void "Test Field List for spl"(){
        FieldList.create("spl", ['spllist'], true)

        when: "populate field is called with drug and label"
        controller.populateFields("drug", "label")
        then:
        response.text == '[\'spllist\']'

    }

    void "Test Field List for drug enforcement"(){
        FieldList.create("res", ['reslist'], true)

        when: "populate field is called with drug and enforcement"
        controller.populateFields("drug", "enforcement")
        then:
        response.text == '[\'reslist\']'

    }

    void "Test Field List for device event"(){
        FieldList.create("maude", ['maudelist'], true)

        when: "populate field is called with device and event"
        controller.populateFields("device", "event")
        then:
        response.text == '[\'maudelist\']'

    }

    void "Test Field List for device enforcement"(){
        FieldList.create("res", ['reslist'], true)

        when: "populate field is called with device and enforcement"
        controller.populateFields("device", "enforcement")
        then:
        response.text == '[\'reslist\']'

    }


    void "Test Field List for food enforcement"(){
        FieldList.create("spl", ['spllist'], true)
        FieldList.create("res", ['reslist'], true)
        when: "populate field is called with food and enforcement"
        controller.populateFields("food", "enforcement")
        then:
        response.text == '[\'reslist\']'
    }


    void "Test that search method is called improperly"() {

        setup:
        controller.queryService = queryService
        queryService.transactionManager = Mock(PlatformTransactionManager) { getTransaction(_) >> Mock(TransactionStatus) }


        when: "The search action is executed with invalid parameters"
        params.category = 'drug'
        params.subcategory = 'dummy'
        params.fields_0='patient.drug.openfda.brand_name'
        params.criteria_0='advil'
        controller.search()

        then: "Them the index action is forwarded"
        view == '/query/index'
        model.queryInstance.hasErrors() == true
    }

    void "Test that search method is called properly"() {

        setup:
        controller.queryService = queryService
        queryService.transactionManager = Mock(PlatformTransactionManager) { getTransaction(_) >> Mock(TransactionStatus) }

        when: "The search action is executed with valid parameters"
        params.category = 'drug'
        params.subcategory = 'event'
        params.fields_0='patient.drug.openfda.brand_name'
        params.criteria_0='advil'
        controller.search()

        then: "Them the index action is forwarded"
        view == '/query/show'
        model.disclaimer != null
        model.queryResults != null
    }


    void "Test that search method is called properly and with count limit and skip"() {
        setup:
        controller.queryService = queryService
        queryService.transactionManager = Mock(PlatformTransactionManager) { getTransaction(_) >> Mock(TransactionStatus) }

        when: "The search action is executed with valid parameters"
        params.category = 'drug'
        params.subcategory = 'event'
        params.fields_0='patient.drug.openfda.brand_name'
        params.criteria_0='advil'
        params.criteria_exact_0=true
        params.fields_1='patient.patientsex'
        params.criteria_1='1'
        params.booleanOp_1='AND'
        params.count='patient.reaction.reactionmeddrapt'
        params.limit='5'
        params.skil='10'
        controller.search()

        then: "Them the index action is forwarded"
        view == '/query/show'
        model.disclaimer != null
        model.queryResults != null
    }

    void "Test that search method is called properly and with bogus limit and skip"() {
        setup:
        controller.queryService = queryService
        queryService.transactionManager = Mock(PlatformTransactionManager) { getTransaction(_) >> Mock(TransactionStatus) }

        when: "The search action is executed with valid parameters"
        params.category = 'drug'
        params.subcategory = 'event'
        params.fields_0='patient.drug.openfda.brand_name'
        params.criteria_0='advil'
        params.criteria_exact_0=true
        params.fields_1='patient.patientsex'
        params.criteria_1='1'
        params.booleanOp_1='AND'
        params.count='patient.reaction.reactionmeddrapt'
        params.limit='NaN'
        params.skil='NaN'
        controller.search()

        then: "Them the index action is forwarded"
        view == '/query/show'
        model.disclaimer != null
        model.queryResults != null
    }



    void "Test that medicineReactions throws an error when invalid medicine is passed"() {
        setup:
        controller.queryService = queryService
        queryService.transactionManager = Mock(PlatformTransactionManager) { getTransaction(_) >> Mock(TransactionStatus) }

        when: "The search action is executed with valid parameters"
        params.category = 'drug'
        params.subcategory = 'event'
        params.medicine='bogus medicine'

        controller.medicineReactions()

        then: "Them the index action is forwarded"
        view == '/query/index'
        model.queryInstance.hasErrors() == true
    }


    void "Test that medicineReactions works when valid medicine is passed"() {
        setup:
        controller.queryService = queryService
        queryService.transactionManager = Mock(PlatformTransactionManager) { getTransaction(_) >> Mock(TransactionStatus) }

        when: "The search action is executed with valid parameters"
        params.category = 'drug'
        params.subcategory = 'event'
        params.medicine='advil'

        controller.medicineReactions()

        then: "Them the index action is forwarded"
        view == '/query/medicineReactions'
        model.drugReactions != null
        model.drugReactions.medicine == 'advil'

    }

    void "Test that interestingFacts throws an error when invalid medicine is passed"() {
        setup:
        controller.queryService = queryService
        queryService.transactionManager = Mock(PlatformTransactionManager) { getTransaction(_) >> Mock(TransactionStatus) }

        when: "The search action is executed with valid parameters"
        params.category = 'drug'
        params.subcategory = 'label'
        params.medicine='bogus medicine'

        controller.interestingFacts()

        then: "Them the index action is forwarded"
        view == '/query/index'
        model.queryInstance.hasErrors() == true
    }


    void "Test that interestingFacts works when valid medicine is passed"() {
        setup:
        controller.queryService = queryService
        queryService.transactionManager = Mock(PlatformTransactionManager) { getTransaction(_) >> Mock(TransactionStatus) }

        when: "The search action is executed with valid parameters"
        params.category = 'drug'
        params.subcategory = 'label'
        params.medicine='advil'

        controller.interestingFacts()

        then: "Them the index action is forwarded"
        view == '/query/interestingFacts'
        model.facts != null
        model.facts.medicine == 'advil'
        model.facts.message =='Information of interest about the drug'
        model.facts.facts != null

    }

    void "Test that drugNames throws an error when invalid medicine is passed"() {
        setup:
        controller.queryService = queryService
        queryService.transactionManager = Mock(PlatformTransactionManager) { getTransaction(_) >> Mock(TransactionStatus) }

        when: "The search action is executed with valid parameters"
        params.category = 'drug'
        params.subcategory = 'label'
        params.medicine='bogus medicine'

        controller.drugNames()

        then: "Them the index action is forwarded"
        view == '/query/index'
        model.queryInstance.hasErrors() == true
    }


    void "Test that drugNames works when valid medicine is passed"() {
        setup:
        controller.queryService = queryService
        queryService.transactionManager = Mock(PlatformTransactionManager) { getTransaction(_) >> Mock(TransactionStatus) }

        when: "The search action is executed with valid parameters"
        params.category = 'drug'
        params.subcategory = 'label'
        params.medicine='advil'

        controller.drugNames()

        then: "Them the index action is forwarded"
        view == '/query/interestingFacts'
        model.facts != null
        model.facts.medicine == 'advil'
        model.facts.message == 'All the known names for the drug '
        model.facts.facts != null

    }


    void "Test that drugFoodInteraction throws an error when no food is passed"() {
        setup:
        controller.queryService = queryService
        queryService.transactionManager = Mock(PlatformTransactionManager) { getTransaction(_) >> Mock(TransactionStatus) }

        when: "The search action is executed with valid parameters"
        params.category = 'drug'
        params.subcategory = 'label'
        params.medicine='bogus medicine'

        controller.drugFoodInteractions()

        then: "Them the index action is forwarded"
        view == '/query/index'
        model.queryInstance.hasErrors() == true
    }

    void "Test that drugFoodInteraction throws an error when no medicine is passed"() {
        setup:
        controller.queryService = queryService
        queryService.transactionManager = Mock(PlatformTransactionManager) { getTransaction(_) >> Mock(TransactionStatus) }

        when: "The search action is executed with valid parameters"
        params.category = 'drug'
        params.subcategory = 'label'
        params.food='bogus food'

        controller.drugFoodInteractions()

        then: "Them the index action is forwarded"
        view == '/query/index'
        model.queryInstance.hasErrors() == true
    }


    void "Test that drugFoodInteraction works even when invalid medicine is passed"() {
        setup:
        controller.queryService = queryService
        queryService.transactionManager = Mock(PlatformTransactionManager) { getTransaction(_) >> Mock(TransactionStatus) }

        when: "The search action is executed with valid parameters"
        params.category = 'drug'
        params.subcategory = 'label'
        params.medicine='bogus'
        params.food='bogus'

        controller.drugFoodInteractions()

        then: "Them the index action is forwarded"
        view == '/query/drugInteractions'
        model.drugInteractions != null
        model.drugInteractions.medicine == 'bogus'
        model.drugInteractions.otherDrugOrFood == 'bogus'
        model.drugInteractions.interactions == null
        model.drugInteractions.adverseReactions == null
        model.boxedWarnings == null
        model.warningAndPrecautions == null

    }

    void "Test patientDeaths when no dates are provided" (){
        when: "Np dates are passed"
        controller.patientDeaths()

        then: "default dates are picked"
        model.beginDate == 2004
        model.endDate == 2015

        when: "only end date is passed"
        params.endDate = '2008'
        controller.patientDeaths ()

        then: "the begin date is made default"
        model.beginDate == 2004
        model.endDate == '2008'



    }

    void "Test only begin date is passed"(){
        when: "only begin date is passed"
        params.beginDate = '2003'
        controller.patientDeaths ()

        then: "the end date is made default"
        model.beginDate == '2003'
        model.endDate == 2015
    }

    void "Test patientDeath method" (){
        when:
        params.beginDate = '2005'
        params.endDate ='2009'
        controller.patientDeaths()

        then:
        view == "/query/map"
        model.beginDate == '2005'
        model.endDate == '2009'
        model.fillKeys != null
    }

    void "Test refreshDeath method"(){
        when:
        params.beginDate = '2005'
        params.endDate ='2009'
        controller.refreshPatientDeaths()

        then:
        response.json.beginDate == '2005'
        response.json.endDate == '2009'
    }

}
