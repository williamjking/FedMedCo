package softexcel.fedmedco

class Query {

    String queryField
    String criteria

    static constraints = {
        queryField nullable: true
        criteria nullable: true
    }
}
