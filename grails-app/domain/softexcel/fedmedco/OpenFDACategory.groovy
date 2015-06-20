package softexcel.fedmedco

class OpenFDACategory {
    String category

    static hasMany = [endPoints:EndPoint]

    static mapWith = "mongo"

    static constraints = {
    }
}
