package softexcel.fedmedco

class OpenFDACategory {
    String category

    static hasMany = [endPoints:EndPoint]

    static mapWith = "mongo"

    static constraints = {
    }
	
	static OpenFDACategory create(String category, boolean flush = false) {
		def instance = new OpenFDACategory(category: category)
		instance.save(flush: flush, insert: true)
		instance
	}
}
