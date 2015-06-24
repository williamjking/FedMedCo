package softexcel.fedmedco

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import grails.transaction.Transactional
import groovy.json.JsonSlurper
import groovyx.net.http.RESTClient
import groovyx.net.http.ContentType

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

    def show(Query queryInstance, String queryResults) {
        render view:"show", model: [queryResults: queryResults]
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
            String field = params.queryField
            String queryString = params.criteria
            String openFDAURL = "https://api.fda.gov/"
            String aPath = category + '/' + subCategory + '.json'

            String assembleAllFields = null

            if (params.exactCriteria) queryString = '"' + queryString +'"'

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

            RESTClient client = new RESTClient( openFDAURL )
            def resp = client.get(path: aPath, query:completeQuery)

            def dataAsJSON = resp.getData() as JSON

            forward (action: "show", params:[queryResults: dataAsJSON.toString()] )
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
            String openFDAURL = "https://api.fda.gov/"
            String aPath = category + '/' + subCategory + '.json'


            def completeQuery = [search:"patient.drug.openfda.generic_name:"+medicine+"+brand_name:"+medicine]
            completeQuery.put('count', 'patient.reaction.reactionmeddrapt.exact')

            RESTClient client = new RESTClient( openFDAURL )
            def resp = client.get(path: aPath, query:completeQuery)

            def dataAsJSON = resp.getData() as JSON

            def data = resp.getData()

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
