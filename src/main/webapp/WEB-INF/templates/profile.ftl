<#include "base.ftl"/>

<#macro title>
    Профиль
</#macro>

<#macro content>
    <p>Профиль</p>

    <button id="createPostBtn">Создать публикацию</button>

    <form method="post" action="${contextPath}/publication" id="postForm" enctype="multipart/form-data" style="display: none;">
        <label for="postText">Текст публикации</label>
        <textarea type="text" name="postText" id="postText" placeholder="Введите текст"></textarea>
        <input type="file" name="postImage" id="postImage" multiple>
        <button type="submit">Опубликовать</button>
    </form>

    <script src="${contextPath}/js/profile.js"></script>
</#macro>

<@page/>
