<#include "base.ftl"/>

<#macro title>
    Folders
</#macro>

<#macro content>
    <#list folders as folder>
        <div>
            <p>${folder.name()}</p>
            <button onclick="window.location.href='${contextPath}/profile/folders/folder/${folder.id()}'">
                Перейти
            </button>
        </div>
    </#list>

</#macro>

<@page/>
