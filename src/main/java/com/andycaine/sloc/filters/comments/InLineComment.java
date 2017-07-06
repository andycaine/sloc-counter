package com.andycaine.sloc.filters.comments;

import static java.util.Arrays.asList;

class InLineComment implements CommentFilterState {

    @Override
    public void handle(String token, StringBuilder result, CommentFilter context) {
        if (asList("\r\n", "\n").contains(token)) {
            context.setFilterState(new NotInQuotesOrComments());
            result.append(token);
        }
    }
}
