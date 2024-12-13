
<#macro renderPost publication>
    <div>
        ${publication.date().toString()}<br>
        ${publication.content()}<br>
        Comments amount ${publication.commentsAmount()}<br>
        Likes amount ${publication.likesAmount()}

        <#if publication.liked()>
            <p>You like it</p>
        <#else>
            <form method="post" action="${contextPath}/like">
                <input type="hidden" name="publicationId" value="${publication.id()}">
                <input type="hidden" name="accountId" value="${publication.accountId()}">
                <button type="submit">Like</button>
            </form>
        </#if>
    </div>

    <#list publication.images() as image>
        <img src="${image.path()}" alt="${image.name()}">
    </#list><br>

    <button id="showComments">Show comments</button>
    <script src="${contextPath}/static/js/loadComments.js"></script>
</#macro>
