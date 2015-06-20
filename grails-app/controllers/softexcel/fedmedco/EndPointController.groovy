package softexcel.fedmedco

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional


@Secured(['ROLE_SU'])
@Transactional(readOnly = true)
class EndPointController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond EndPoint.list(params), model: [endPointInstanceCount: EndPoint.count()]
    }

    def show(EndPoint endPointInstance) {
        respond endPointInstance
    }

    def create() {
        respond new EndPoint(params)
    }

    @Transactional
    def save(EndPoint endPointInstance) {
        if (endPointInstance == null) {
            notFound()
            return
        }

        if (endPointInstance.hasErrors()) {
            respond endPointInstance.errors, view: 'create'
            return
        }

        endPointInstance.save flush: true


        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'endPoint.label', default: 'EndPoint'), endPointInstance.id])
                redirect endPointInstance
            }
            '*' { respond endPointInstance, [status: CREATED] }
        }
    }

    def edit(EndPoint endPointInstance) {
        respond endPointInstance
    }

    @Transactional
    def update(EndPoint endPointInstance) {
        if (endPointInstance == null) {
            notFound()
            return
        }

        if (endPointInstance.hasErrors()) {
            respond endPointInstance.errors, view: 'edit'
            return
        }

        endPointInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'EndPoint.label', default: 'EndPoint'), endPointInstance.id])
                redirect endPointInstance
            }
            '*' { respond endPointInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(EndPoint endPointInstance) {

        if (endPointInstance == null) {
            notFound()
            return
        }

        endPointInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'EndPoint.label', default: 'EndPoint'), endPointInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'endPoint.label', default: 'EndPoint'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
