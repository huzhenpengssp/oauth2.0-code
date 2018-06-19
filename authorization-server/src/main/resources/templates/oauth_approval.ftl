<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<head>
    <title>approval</title>
    <meta charset="utf-8"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12 well">
            <h3>授权页</h3>
            应用名 : <span text="${clientName}">${clientName}</span>

            <form id='confirmationForm' name='confirmationForm' action="/oauth/authorize" method='post'>
                <input name='user_oauth_approval' value='true' type='hidden' />
                <input name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}" type="hidden"/>
                <#list scopeList as item>
                    <ul>
                        <li>
                            <div class='form-group'>${item}:
                                <input type='radio' name='${item}' value='true'>Approve</input>
                                <input type='radio' name='${item}' value='false' checked>Deny</input>
                            </div>
                        </li>
                    </ul>
                </#list>
                <input name='authorize' value='授权' type='submit' />
            </form>
        </div>
    </div>
</div>
</body>
</html>

