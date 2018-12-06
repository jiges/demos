这里是主页,登录用户：${username ! '未登录'}
<#if username??>
    <a href="/logout">退出登录</a>
<#else>
    <a href="/login">登录</a>
</#if>
