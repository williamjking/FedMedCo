package softexcel.fedmedco

class DrugReactions {

    def medicine
    def totalReportedReactions
    def topThreeReactions
    def severeReactions
    def moderateReactions
    def mildReactions


    static constraints = {
        medicine nullable: true
        totalReportedReactions nullable: true
        topThreeReactions nullable: true
        severeReactions nullable: true
        moderateReactions nullable: true
        mildReactions nullable: true
    }
}
