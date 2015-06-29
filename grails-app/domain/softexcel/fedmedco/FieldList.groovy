package softexcel.fedmedco

class FieldList {
	
	String schemaName
    static hasMany = [fields:String]

	static mapWith = "mongo"
	
	static mapping = {
		id name: 'schemaName', generator: 'assigned'
		version false
	}

    static FieldList create(String schemaName, List fields, boolean flush = false) {
		FieldList instance = FieldList.findOrCreateBySchemaName(schemaName)
		instance.fields = fields
        instance.save(flush: flush)
        instance
    }
}
