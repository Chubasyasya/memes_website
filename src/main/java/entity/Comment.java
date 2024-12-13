package entity;

public record Comment (long id, String content, int likesAmount, int dislikesAmount, long publicationId, long accountId){
}
