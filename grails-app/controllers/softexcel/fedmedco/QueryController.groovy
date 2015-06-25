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
            log.error e
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
		
		log.info "Returning field list for category: " + category + ", subcategory: " + subcategory
		render fieldList
	}

    def query(Query queryInstance, String errorMessage, String queryResults) {
        Query aQuery = queryInstance
        try {

            if (errorMessage == null) {
                render view:"query", model:[queryResults:queryResults]
            }
            else {
                if (aQuery == null) aQuery = new Query()
                aQuery.errors.rejectValue('queryField', 'an.error.message')
                render view:"query", model:[queryInstance:aQuery]
            }
        } catch (Exception e) {
            log.error e
            qQuery = new Query(params)
            aQuery.errors.rejectValue ('queryField', 'an.error.message')
            render view:"query", model:[queryInstance:aQuery]
        }
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
                        log.error(npe)
                    }
                }
            })


            def completeQuery = [search:assembleAllFields]

            if (params.count != '') {
                if (params.exactCount) completeQuery.put('count', params.count + '.exact')
                else completeQuery.put('count', params.count)
            }
            if (params.limit != '' && params.limit?.isInteger()) completeQuery.put('limit', params.limit)
            if (params.skip != '' && params.skip?.isInteger()) completeQuery.put('skip', params.skip)

            def url = openFDAURLBase + openFDAURLPath + "?" + completeQuery.collect { it }.join('&')

            def data = url.toURL().text
            def parsedData = new JsonSlurper().parseText(data)

            render view:"show", model: [
                    disclaimer: parsedData.meta.disclaimer,
                    lastUpdated: parsedData.meta.last_updated,
                    totalResults: parsedData.meta.results.total,
                    queryResults: data
            ]

        } catch (Exception ioe) {
            log.error ioe
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue ('queryField', 'an.error.message')
            forward (action: "index", params:[queryInstance: queryParams, errorMessage: ioe.message])
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
                    'patient.drug.openfda.generic_name:'+medicine+'+brand_name:'+medicine +
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
            log.error ioe
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue ('queryField', 'an.error.message')
            forward (action: "index", params:[queryInstance: queryParams, errorMessage: ioe.message])
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
