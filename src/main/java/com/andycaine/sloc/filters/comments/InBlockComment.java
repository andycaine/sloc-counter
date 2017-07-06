package com.andycaine.sloc.filters.comments;

class InBlockComment implements CommentFilterState {

    @Override
    public void handle(String token, StringBuilder result, CommentFilter context) {
        if (context.isBlockCommentEnd(token)) {
            context.setFilterState(new NotInQuotesOrComments());
        }
    }
}
