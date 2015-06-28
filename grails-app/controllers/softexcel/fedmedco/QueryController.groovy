package softexcel.fedmedco

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import groovy.json.JsonSlurper


@Secured(['ROLE_QUERY'])
@Transactional(readOnly = true)
class QueryController {

    def queryService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Query queryInstance, String errorMessage, String queryResults) {
        Query aQuery = queryInstance
        try {
            if (errorMessage == null) {
                render view:"index", model:[queryResults:queryResults]
            }
            else {
                if (aQuery == null) aQuery = new Query()
                aQuery.errors.rejectValue('queryField', 'an.error.message')
                render view:"index", model:[queryInstance:aQuery]
            }
        } catch (Exception e) {
            log.error e.message, e
            qQuery = new Query(params)
            aQuery.errors.rejectValue ('queryField', 'an.error.message')
            render view:"index", model:[queryInstance:aQuery]
        }
    }

	def populateFields(String category, String subcategory) {
		def name
		if (category == "drug" && subcategory == "event") {
			name = "faers"
		} else if (category == "drug" && subcategory == "label") {
			name = "spl"
		} else if (category == "drug" && subcategory == "enforcement") {
			name = "res"
		} else if (category == "device" && subcategory == "event") {
			name = "maude"
		} else if (category == "device" && subcategory == "enforcement") {
			name = "res"
		} else if (category == "food" && subcategory == "enforcement") {
			name = "res"
		}
		
		def fieldList = []
		if (name != null) {
			fieldList = FieldList.findBySchemaName(name).fields
		}

		render fieldList
	}

    def search() {
        try {
            String category = params.category
            String subCategory = params.subcategory
            String openFDAURLBase = "https://api.fda.gov/"
            String openFDAURLPath = category + '/' + subCategory + '.json'
            String assembleAllFields = null

            params.each ({key, value ->
                def matcher = key =~ /fields_(.*)/

                if (matcher && value) {
                   def index = matcher[0][1]

                    try {
                        def criteria = queryService.replaceSpaceWithPlus(params.get("criteria_"+index))
                        if (params.get("criteria_exact_"+index) != null) criteria =  '"' + criteria +'"'

                        if (assembleAllFields == null) assembleAllFields = value + ':' + criteria
                        else if (params.get("booleanOp_"+index) == 'OR') assembleAllFields = assembleAllFields + '+' +  value + ':' + criteria
                        else assembleAllFields = assembleAllFields + '+AND+' +  value + ':' + criteria
                    }
                    catch (NullPointerException npe){
                        log.error(npe.message, npe)
                    }
                }
            })


            def completeQuery = [search:assembleAllFields]

            if (params.count && params.count != '') {
                if (params.exactCount) completeQuery.put('count', params.count + '.exact')
                else completeQuery.put('count', params.count)
            }
            if (params.limit && params.limit != '' && params.limit?.isInteger()) completeQuery.put('limit', params.limit)
            if (params.skip && params.skip != '' && params.skip?.isInteger()) completeQuery.put('skip', params.skip)

            def url = openFDAURLBase + openFDAURLPath + "?" + completeQuery.collect { it }.join('&')

            def data = url.toURL().text
            def parsedData = new JsonSlurper().parseText(data)

            render view:"show", model: [
                    disclaimer: parsedData?.meta?.disclaimer,
                    lastUpdated: parsedData?.meta?.last_updated,
                    totalResults: parsedData?.meta?.results?.total,
                    queryResults: data
            ]

        } catch (Exception ioe) {
            log.error ioe.message, ioe
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue ('queryField', 'an.error.message')
            render (view: "index", model:[queryInstance: queryParams, errorMessage: ioe.message])
        }
    }


    def medicineReactions() {
        try {
            String category = 'drug'
            String subCategory = 'event'
            String medicine = '"' + queryService.replaceSpaceWithPlus(params.medicine) +'"'
            String medicineQuery = "https://api.fda.gov/" +
                    category +
                    '/' +
                    subCategory +
                    '.json?search=' +
                    'patient.drug.openfda.generic_name:'+medicine+'+patient.drug.openfda.brand_name:'+medicine +
                    '+patient.drug.openfda.pharm_class_epc:'+medicine+
                    '&count=patient.reaction.reactionmeddrapt.exact'

            def data = new JsonSlurper().parse(medicineQuery.toURL())

            def mapToAnalyzeData = [:]

            data.results.each{mapToAnalyzeData.put(it.count, it.term)}
            def min = mapToAnalyzeData.min {it.key}
            def max = mapToAnalyzeData.max {it.key}

            Integer oneThird = (max.key - min.key)/3;

            Map lowerThird = mapToAnalyzeData.subMap(min.key..min.key+oneThird)
            Map middleThird = mapToAnalyzeData.subMap(min.key+oneThird+1..min.key+oneThird+oneThird)
            Map topThird = mapToAnalyzeData.subMap(min.key+oneThird+oneThird+1..max.key+1)

            def topThreeReactions = []

            def i = 0

            topThird.reverseEach {
                if (i<3) topThreeReactions.add(it.value)
                i++
            }

            DrugReactions drugReactions = new DrugReactions()

            drugReactions.medicine = params.medicine
            drugReactions.totalReportedReactions = mapToAnalyzeData.size()
            drugReactions.topThreeReactions =  topThreeReactions
            drugReactions.severeReactions = topThird
            drugReactions.moderateReactions = middleThird
            drugReactions.mildReactions = lowerThird

            render(view:'medicineReactions', model:[drugReactions:drugReactions])
        } catch (Exception ioe) {
            log.error ioe.message, ioe
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue ('queryField', 'an.error.message')
            render (view: "index", model:[queryInstance: queryParams, errorMessage: ioe.message])
        }
    }


    def interestingFacts(){
        try {
            String category = 'drug'
            String subCategory = 'label'
            String medicine = '"' + queryService.replaceSpaceWithPlus(params.medicine) +'"'
            String medicineQuery = "https://api.fda.gov/" +
                    category +
                    '/' +
                    subCategory +
                    '.json?search=' +
                    'openfda.generic_name:'+medicine+'+openfda.brand_name:'+medicine +
                    '+openfda.pharm_class_epc:'+medicine

            def data = new JsonSlurper().parse(medicineQuery.toURL())
            def facts = data.results

            if (facts == null) throw new Exception ("Could not find any interesting data for the drug " + params.medicine)

            InterestingFacts interestingFacts = new InterestingFacts()
            interestingFacts.medicine = params.medicine


            if (facts.purpose && facts.purpose[0])interestingFacts.facts['Purpose'] = facts.purpose[0][0]
            if (facts.stop_use && facts.stop_use[0])interestingFacts.facts['Stop Using if'] = facts.stop_use[0][0]
            if (facts.storage_and_handling && facts.storage_and_handling[0])interestingFacts.facts['Storage and Handling'] = facts.storage_and_handling[0][0]
            if (facts.ask_doctor && facts.ask_doctor[0])interestingFacts.facts['What to ask a doctor'] = facts.ask_doctor[0][0]
            else if (facts.ask_doctor_pharmasist && facts.ask_doctor_pharmacist[0])interestingFacts.facts['What to ask a pharmacist'] = facts.ask_doctor_pharmacist[0][0]
            if (facts.boxed_warning && facts.boxed_warning[0])interestingFacts.facts['Serious Warnings'] = facts.boxed_warning[0][0]
            if (facts.warnings_and_precautions && facts.warnings_and_precautions[0])interestingFacts.facts['Warnings and Precautions'] = facts.warnings_and_precautions[0][0]
            else if (facts.warnings && facts.warnings[0])interestingFacts.facts['Warnings'] = facts.warnings[0][0]
            else if (facts.precautions && facts.precautions[0])interestingFacts.facts['Precautions'] = facts.precautions[0][0]
            if (facts.pregnancy_or_breast_feeding && facts.pregnancy_or_breast_feeding[0])interestingFacts.facts['Pregnancy or Breast Feeding'] = facts.pregnancy_or_breast_feeding[0][0]
            else if (facts.pregnancy && facts.pregnancy[0])interestingFacts.facts['Pregnancy'] = facts.pregnancy[0][0]

            render(view:'interestingFacts', model:[facts:interestingFacts])
        }
        catch (Exception ioe) {
            log.error ioe.message, ioe
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue('queryField', 'an.error.message')
            render(view: "index", model: [queryInstance: queryParams, errorMessage: ioe.message])
        }
    }


    def drugFoodInteractions(){
        try{
            String category = 'drug'
            String subCategory = 'label'
            String medicine = '"' + queryService.replaceSpaceWithPlus(params.medicine) +'"'
            String food = '"' + queryService.replaceSpaceWithPlus(params.food) +'"'

            def jsonSlurper = new JsonSlurper();

            String baseQuery =  "https://api.fda.gov/" +
                    category +
                    '/' +
                    subCategory +
                    '.json?search=' +
                    '(openfda.generic_name:'+medicine+'+openfda.brand_name:'+ medicine +
                    '+openfda.pharm_class_epc:'+medicine

            String drugInteractionQuery =  baseQuery + ')+AND+drug_interactions:' + food

            DrugInteractions drugInteractions = new DrugInteractions()
            drugInteractions.medicine = params.medicine
            drugInteractions.otherDrugOrFood = params.food

            try{
                def response = jsonSlurper.parse(drugInteractionQuery.toURL())
                if (response?.results?.drug_interactions[0])
                    drugInteractions.interactions = response?.results?.drug_interactions[0][0].replaceAll(~"(?i)(${params.food})"){all, text -> "<mark>${text}</mark>"}

            }
            catch (Exception e){
            }

            try{
                drugInteractionQuery = baseQuery + ')+AND+adverse_reactions:' + food
                def response = jsonSlurper.parse(drugInteractionQuery.toURL())
                if (response?.results?.adverse_reactions[0])
                    drugInteractions.adverseReactions = response?.results?.adverse_reactions[0][0].replaceAll(~"(?i)(${params.food})"){all, text -> "<mark>${text}</mark>"}
            }
            catch (Exception e){
            }

            try{
                drugInteractionQuery = baseQuery + ')+AND+boxed_warning:' + food
                def response = jsonSlurper.parse(drugInteractionQuery.toURL())
                if (response?.results?.boxed_warning[0])
                    drugInteractions.boxedWarnings = response?.results?.boxed_warning[0][0].replaceAll(~"(?i)(${params.food})"){all, text -> "<mark>${text}</mark>"}
            }
            catch (Exception e){
            }

            try{
                drugInteractionQuery = baseQuery + ')+AND+user_safety_warnings:' + food
                def response = jsonSlurper.parse(drugInteractionQuery.toURL())
                if (response?.results?.user_safety_warnings[0])
                    drugInteractions.warningAndPrecautions = response?.results?.user_safety_warnings[0][0].replaceAll(~"(?i)(${params.food})"){all, text -> "<mark>${text}</mark>"}
            }
            catch (Exception e){
            }


            render(view:'drugInteractions', model:[drugInteractions: drugInteractions])
        }
        catch(NullPointerException npe){
            log.error npe.message, npe
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue('queryField', 'required.fields.messaage')
            render(view: "index", model: [queryInstance: queryParams, errorMessage: npe.message])
        }
        catch (Exception e){
            log.error e.message, e
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue('queryField', 'an.error.message')
            render(view: "index", model: [queryInstance: queryParams, errorMessage: e.message])
        }

    }


    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'query.label', default: 'Query'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
