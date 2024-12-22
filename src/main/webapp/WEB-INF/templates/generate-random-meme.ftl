<#include "base.ftl">
<#import "post.ftl" as postMacro>

<#macro head>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/random-meme-generator-style.css">
</#macro>

<#macro content>
    <div class="content-container">
        <a href="${contextPath}/generate-random-meme" class="generate-button">Генерировать</a>

        <#if publication??>
            <div class="publication">
                <@postMacro.renderPost publication=publication/>
            </div>
        <#else>
            <p>Нет доступных публикаций.</p>
        </#if>
    </div>
</#macro>

<@page/>