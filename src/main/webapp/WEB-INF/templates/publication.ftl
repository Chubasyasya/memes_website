<#import "post.ftl" as postMacro>
<#import "comment.ftl" as commentMacro>
<#include "base.ftl"/>

<#macro head>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/publication-style.css?v=1.0">
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/comment-style.css">
</#macro>

<#macro content>
    <div class="content-wrapper">
        <@postMacro.renderPost publication=publication/>

        <form action="${contextPath}/comment/create" method="post" class="comment-form">
            <input type="hidden" name="publicationId" value="${publication.id}">
            <textarea name="content" rows="4" cols="50" class="comment-input" placeholder="Ваш комментарий"></textarea>
            <input type="submit" value="Отправить" class="comment-form-submit-btn">
        </form>

        <#list comments as comment>
            <@commentMacro.renderComment comment=comment currentAccountId=accountId/>
        </#list>
    </div>
    <script src="${contextPath}/static/js/likePublication.js"></script>
</#macro>

<@page/>
