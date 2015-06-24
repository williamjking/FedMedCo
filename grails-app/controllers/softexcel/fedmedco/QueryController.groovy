package softexcel.fedmedco

import grails.plugin.springsecurity.annotation.Secured

//import grails.plugins.rest.client.*

//import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import groovyx.net.http.RESTClient
import groovyx.net.http.ContentType

@Secured(['ROLE_QUERY'])
@Transactional(readOnly = true)
class QueryController {

    def queryService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Query.list(params), model: [queryInstanceCount: Query.count()]
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
            log.debug ("Params passed to search  ==> " + params.sort{it.key.toLowerCase()})
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


            log.debug("Assembled criteria " + assembleAllFields)

            def completeQuery = [search:assembleAllFields]

            if (params.count != '') {
                if (params.exactCount) completeQuery.put('count', params.count + '.exact')
                else completeQuery.put('count', params.count)
            }
            if (params.limit != '' && params.limit?.isInteger()) completeQuery.put('limit', params.limit)
            if (params.skip != '' && params.skip?.isInteger()) completeQuery.put('skip', params.skip)

            log.debug("Complete query = " + completeQuery)

            log.debug(completeQuery)

            RESTClient client = new RESTClient( openFDAURL )
            def resp = client.get(path: aPath, query:completeQuery)

            forward (action: "show", params:[queryResults: resp.getData()] )
        } catch (Exception ioe) {
            log.error ioe
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue ('queryField', 'an.error.message')
            forward (action: "query", params:[queryInstance: queryParams, errorMessage: ioe.message])
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
