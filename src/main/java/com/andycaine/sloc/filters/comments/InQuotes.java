package com.andycaine.sloc.filters.comments;

class InQuotes implements CommentFilterState {

    private final String quoteToken;

    InQuotes(String quoteToken) {
        this.quoteToken = quoteToken;
    }

    @Override
    public void handle(String token, StringBuilder result, CommentFilter context) {
        result.append(token);
        if (token.equals(quoteToken)) {
            context.setFilterState(new NotInQuotesOrComments());
        }
    }
}
