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
        //def instance = new FieldList(schemaName: schemaName, fields: fields)
		FieldList instance = FieldList.findOrCreateBySchemaName(schemaName)
		instance.fields = fields
        instance.save(flush: flush)
        instance
    }
}
