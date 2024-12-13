<#macro renderComment comments>
    <button class="commentButton">Показать комментарии</button>
    <div class="commentsSection hidden">
        <#list comments as comment>
            ${comment.content()}<br>
            Likes amount: ${comment.likesAmount()}<br>
            Dislikes amount: ${comment.dislikesAmount()}<br>
        </#list>
    </div>

    <script src="${contextPath}/static/js/commentContainer.js"/>
</#macro>