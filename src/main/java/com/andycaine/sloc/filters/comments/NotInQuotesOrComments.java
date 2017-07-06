package com.andycaine.sloc.filters.comments;

import static java.util.Arrays.asList;

class NotInQuotesOrComments implements CommentFilterState {

    @Override
    public void handle(String token, StringBuilder result, CommentFilter context) {
        if (isQuote(token)) {
            result.append(token);
            context.setFilterState(new InQuotes(token));
        } else if (context.isLineCommentStart(token)) {
            context.setFilterState(new InLineComment());
        } else if (context.isBlockCommentStart(token)) {
            context.setFilterState(new InBlockComment());
        } else {
            result.append(token);
        }
    }


    private boolean isQuote(String token) {
        return asList("'", "\"").contains(token);
    }
}
