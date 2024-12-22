<#macro renderComment comment, currentAccountId>
    <div class="comment-header">
        <span class="comment-date">${comment.creatorName}</span>
        <span class="comment-date">${comment.date}</span>
    </div>
    <div class="comment-content">
        ${comment.content}
    </div>
    <#if currentAccountId == comment.accountId>
        <form action="${contextPath}/comment/delete" method="post" class="delete-form">
            <input type="hidden" name="commentId" value="${comment.id}">
            <input type="hidden" name="publicationId" value="${comment.publicationId}">
            <button type="submit" class="delete-btn">
                Удалить комментарий
            </button>
        </form>
    </#if>
</#macro>