package softexcel.fedmedco

class EndPoint {
    String endPoint

    static mapWith = "mongo"

    static constraints = {
    }
	
	static EndPoint create(String endPoint, boolean flush = false) {
		def instance = new EndPoint(endPoint: endPoint)
		instance.save(flush: flush, insert: true)
		instance
	}
}
