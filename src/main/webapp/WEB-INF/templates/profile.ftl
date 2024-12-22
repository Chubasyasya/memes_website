<#include "base.ftl"/>
<#import "post.ftl" as postMacro>

<#macro title>
    Профиль
</#macro>

<#macro head>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/profile-settings-style.css">
</#macro>

<#macro content>
    <div class="grid-container">
        <div class="left-column"></div>

        <div class="center-column">
            <div class="account-card">
                <h3 class="account-name">${currentAccount.name}</h3>

                <#if currentAccount.status??>
                    <p class="account-status">Статус: ${currentAccount.status}</p>
                <#else>
                    <p>Статус: отсутствует</p>
                </#if>
            </div>
            <button id="createPostBtn" class="create-post-btn">Создать публикацию</button>

            <form method="post" action="${contextPath}/postPublishing" id="postForm" enctype="multipart/form-data" style = "display: none">
                <label for="postText">Текст публикации</label>
                <textarea name="postText" id="postText" placeholder="Введите текст"></textarea>

                <div id="fileInputs">
                    <label for="publication-image-1">Добавить изображение</label>
                    <input type="file" name="publication-image" id="publication-image-1" accept=".jpg, .jpeg, .png" />
                </div>

                <input type="submit" value="Опубликовать">
            </form>



            <div id="feed"></div>
            <button id="loadMore">Загрузить ещё</button>
        </div>

        <div class="right-column"></div>
    </div>

    <script>
        const ownerId = "${ownerId}";
    </script>
    <script src="${contextPath}/static/js/loadProfilePublications.js"></script>

    <script src="${contextPath}/static/js/profile.js"></script>
    <script src="${contextPath}/static/js/likeFeedPublication.js"></script>
    <script src="${contextPath}/static/js/multy-image-publication-form.js"></script>
</#macro>

<@page/>
