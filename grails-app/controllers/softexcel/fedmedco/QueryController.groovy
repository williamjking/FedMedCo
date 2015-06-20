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

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Query.list(params), model: [queryInstanceCount: Query.count()]
    }

    def show(Query queryInstance) {
        respond queryInstance
    }

    def create() {
        respond new Query(params)
    }

    def query() {
        respond new Query(params)
    }

    def search() {
        String category = params.category
        String subCategory = params.subcategory
        String field = params.queryField
        String queryString = params.criteria
        String openFDAURL = "https://api.fda.gov/"
        String aPath = category + '/' + subCategory + '.json'
        String completeQueryStr =  'search=' + field + ':' + queryString;

        log.debug(openFDAURL + completeQueryStr)

        RESTClient client = new RESTClient( openFDAURL )
        def resp = client.get(path: aPath, query:[search:field + ':' + queryString])
        log.debug( ">>>>>>>>>>>> Data >>>>>>> " + resp.getData() )

        redirect (action: "query")
    }

    @Transactional
    def save(Query queryInstance) {
        if (queryInstance == null) {
            notFound()
            return
        }

        if (queryInstance.hasErrors()) {
            respond queryInstance.errors, view: 'create'
            return
        }

        queryInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'query.label', default: 'Query'), queryInstance.id])
                redirect queryInstance
            }
            '*' { respond queryInstance, [status: CREATED] }
        }
    }

    def edit(Query queryInstance) {
        respond queryInstance
    }

    @Transactional
    def update(Query queryInstance) {
        if (queryInstance == null) {
            notFound()
            return
        }

        if (queryInstance.hasErrors()) {
            respond queryInstance.errors, view: 'edit'
            return
        }

        queryInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Query.label', default: 'Query'), queryInstance.id])
                redirect queryInstance
            }
            '*' { respond queryInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Query queryInstance) {

        if (queryInstance == null) {
            notFound()
            return
        }

        queryInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Query.label', default: 'Query'), queryInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
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
