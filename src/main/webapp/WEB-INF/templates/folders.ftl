<#include "base.ftl"/>

<#macro title>
    Folders
</#macro>

<#macro head>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/folders-style.css">
</#macro>

<#macro content>
    <div class="folders-grid">
        <ul>
            <#list folders as folder>
                <div class="folder-item">
                    <li class="folder-item" data-folder-id="${folder.id}" onclick="handleFolderClick(this)">
                        <img src="${contextPath}/static/images/folder-icon.png" alt="Folder" class="folder-image">
                        <span class="folder-name">${folder.name}</span>
                    </li>
                </div>
            </#list>
        </ul>
    </div>

    <script src="${contextPath}/static/js/addImageInFolder.js"></script>
</#macro>

<@page/>
