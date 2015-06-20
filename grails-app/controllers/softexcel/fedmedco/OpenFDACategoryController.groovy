package softexcel.fedmedco

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured(['ROLE_SU'])
@Transactional(readOnly = true)
class OpenFDACategoryController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond OpenFDACategory.list(params), model:[openFDACategoryInstanceCount: OpenFDACategory.count()]
    }

    def show(OpenFDACategory openFDACategoryInstance) {
        respond openFDACategoryInstance
    }

    def create() {
        respond new OpenFDACategory(params)
    }


    @Transactional
    def save(OpenFDACategory openFDACategoryInstance) {
        if (openFDACategoryInstance == null) {
            notFound()
            return
        }

        if (openFDACategoryInstance.hasErrors()) {
            respond openFDACategoryInstance.errors, view:'create'
            return
        }

        openFDACategoryInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'openFDACategory.label', default: 'OpenFDACategory'), openFDACategoryInstance.id])
                redirect openFDACategoryInstance
            }
            '*' { respond openFDACategoryInstance, [status: CREATED] }
        }
    }

    def edit(OpenFDACategory openFDACategoryInstance) {
        respond openFDACategoryInstance
    }

    @Transactional
    def update(OpenFDACategory openFDACategoryInstance) {
        if (openFDACategoryInstance == null) {
            notFound()
            return
        }

        if (openFDACategoryInstance.hasErrors()) {
            respond openFDACategoryInstance.errors, view:'edit'
            return
        }

        openFDACategoryInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'OpenFDACategory.label', default: 'OpenFDACategory'), openFDACategoryInstance.id])
                redirect openFDACategoryInstance
            }
            '*'{ respond openFDACategoryInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(OpenFDACategory openFDACategoryInstance) {

        if (openFDACategoryInstance == null) {
            notFound()
            return
        }

        openFDACategoryInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'OpenFDACategory.label', default: 'OpenFDACategory'), openFDACategoryInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'openFDACategory.label', default: 'OpenFDACategory'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
