<#include "base.ftl"/>
<#import "post.ftl" as postMacro>

<#macro title>
    Профиль
</#macro>

<#macro content>

    <button id="createPostBtn">Создать публикацию</button>

    <form method="post" action="${contextPath}/publication" id="postForm" enctype="multipart/form-data" style="display: none;">
        <label for="postText">Текст публикации</label>
        <textarea type="text" name="postText" id="postText" placeholder="Введите текст"></textarea>
        <input type="file" name="postImage" id="postImage" multiple>
        <input type="submit" value="Опубликовать">
    </form>


    <div id="feed"></div>
    <button id="loadMore">Load More</button>

    <script src="${contextPath}/static/js/loadPublications.js"></script>
    <script src="${contextPath}/static/js/profile.js"></script>
</#macro>

<@page/>
