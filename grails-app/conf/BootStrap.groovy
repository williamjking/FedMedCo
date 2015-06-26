import groovy.json.JsonSlurper
import softexcel.fedmedco.FieldList
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

		def faersSchema = 'https://raw.githubusercontent.com/FDA/openfda/master/schemas/faers_mapping.json'.toURL().text
		def splSchema = 'https://raw.githubusercontent.com/FDA/openfda/master/schemas/spl_mapping.json'.toURL().text
		def resSchema = 'https://raw.githubusercontent.com/FDA/openfda/master/schemas/res_mapping.json'.toURL().text
		def maudeSchema = 'https://raw.githubusercontent.com/FDA/openfda/master/schemas/maude_mapping.json'.toURL().text
		
		def jsonSlurper = new JsonSlurper()
		def faers = jsonSlurper.parseText(faersSchema).safetyreport.properties
		def spl = jsonSlurper.parseText(splSchema).spl.properties
		def res = jsonSlurper.parseText(resSchema).enforcementreport.properties
		def maude = jsonSlurper.parseText(maudeSchema).maude.properties
		
		def faersList = []
		mapToFieldList(faersList, "", faers)
		FieldList.create "faers", faersList, true
		
		def splList = []
		mapToFieldList(splList, "", spl)
		FieldList.create "spl", splList, true
		
		def resList = []
		mapToFieldList(resList, "", res)
		FieldList.create "res", resList, true
		
		def maudeList = []
		mapToFieldList(maudeList, "", maude)
		FieldList.create "maude", maudeList, true
		
    }
    def destroy = {
    }
	
	private String mapToFieldList(List list, String currentField, Map jsonMap) {
		jsonMap.each {
			prop, value -> 
			if (!prop.endsWith("_exact") && !prop.startsWith("@")) {
				def updatedField = currentField
				if (prop == "properties") {
					updatedField += "."
				} else {
					updatedField += prop
				}
				
				if (value.type != null) {
					list.add(updatedField)
				} else if (value instanceof Map){
					mapToFieldList(list, updatedField, value)
				} else {
					log.warn "Unknown element in schema: " + prop + " with value: " + value
				}
			}
		}
	}
}
