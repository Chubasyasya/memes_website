<#include "base.ftl">
<#import "post.ftl" as postMacro>

<#macro title>
    Лента
</#macro>

<#macro content>
    <div id="feed"></div>
    <button id="loadMore">Load More</button>

    <script src="${contextPath}/static/js/loadPublications.js"></script>
    <script src="${contextPath}/static/js/commentContainer.js"></script>
</#macro>

<@page/>
