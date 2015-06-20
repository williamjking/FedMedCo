import softexcel.fedmedco.Role
import softexcel.fedmedco.User
import softexcel.fedmedco.UserRole


class BootStrap {

    def init = { servletContext ->
        def queryRole =   Role.findByAuthority('ROLE_QUERY')
        if (queryRole == null) {
            queryRole = new Role(authority: 'ROLE_QUERY')
            queryRole.save(flush: true)
        }

        def superUserRole =   Role.findByAuthority('ROLE_SU')
        if (superUserRole == null) {
            superUserRole = new Role(authority: 'ROLE_SU')
            superUserRole.save(flush: true)
        }

        def john = User.findByUsername('john')
        if (john == null) {
            john = new User(username: 'john', password: 'password')
            john.save(flush: true)
        }

        def su = User.findByUsername('root')
        if (su == null) {
            su = new User(username: 'root', password: 'password')
            su.save(flush: true)
        }


        UserRole.create su, superUserRole, true
        UserRole.create su, queryRole, true
        UserRole.create john, queryRole, true

    }
    def destroy = {
    }
}
