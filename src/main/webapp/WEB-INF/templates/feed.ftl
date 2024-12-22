<#include "base.ftl">
<#import "post.ftl" as postMacro>

<#macro head>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/feed-style.css">
</#macro>

<#macro title>
    Лента
</#macro>

<#macro content>
    <div class="grid-container">
        <div class="left-column"></div>

        <div class="center-column">
            <div class="search-container">
                <input type="text" id="searchInput" placeholder="Поиск по публикациям">
                <button id="searchButton">Поиск</button>
            </div>
            <div class="sort-container">
                <select id="sortFilter">
                    <option value="date-desc">Сортировка по дате (позже)</option>
                    <option value="date-asc">Сортировка по дате (раньше)</option>
                    <option value="likes_amount-desc">Сортировка по количеству лайков (больше)</option>
                    <option value="likes_amount-asc">Сортировка по количеству лайков (меньше)</option>
                </select>
            </div>

            <div id="feed"></div>
            <button id="loadMore">Load More</button>
        </div>
        <div class="right-column"></div>
    </div>

    <script src="${contextPath}/static/js/loadPublications.js?v=1.1"></script>
    <script src="${contextPath}/static/js/likeFeedPublication.js"></script>
</#macro>


<@page/>
