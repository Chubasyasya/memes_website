<#import "header-logged-out.ftl" as hlo>
<#import "header-logged-in.ftl" as hli>

<#macro content></#macro>
<#macro title></#macro>
<#macro page>
    <html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<#--        <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/header.css">-->

        <link rel="stylesheet" href="${contextPath}/static/css/comment-style.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/post-style.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/header-logged-in-style.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/header-logged-out-style.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/account-style.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/profile-grid-style.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/post-publication-style.css">
        <@head/>
    </head>
    <body style="background-image: url('${contextPath}/static/images/${session.getAttribute("background")!"default-background.jpg"}');
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
            background-repeat: no-repeat;">
    <#if isVariablePage?? && isVariablePage>
        <#if isLoggedIn?? && isLoggedIn>
            <@hli.header/>
        <#else>
            <@hlo.header/>
        </#if>
    <#else>
        <@hli.header/>
    </#if>

    <@content/>
    </body>
    </html>
</#macro>
