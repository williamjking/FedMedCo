package softexcel.fedmedco

import grails.transaction.Transactional

@Transactional
class QueryService {

    def replaceSpaceWithPlus(String input) {
        if (input == null || input == '')
            throw new NullPointerException("Empty String")

        def wordsMatcher = input =~ /([a-zA-Z0-9_\-]*)\s/
        def lastWord = input =~ /([a-zA-Z0-9_\-]*)$/
        def words = []
        wordsMatcher.each {aList -> words.add(aList[1])}

        if (words.size() > 0)
            words.join('+') + '+' + lastWord[0][1]
        else
            input
    }
}
