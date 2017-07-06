package com.andycaine.sloc.filters.comments;

interface CommentFilterState {
    void handle(String token, StringBuilder result, CommentFilter context);
}
