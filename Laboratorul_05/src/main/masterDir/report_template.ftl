<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h1>${title}</h1>
<h4>${content}</h4>


<div class="employee-section">
    <h2>Employee Data:</h2>

    <#list idList as id>
        <div class="data">
            <h3>ID: ${id.id}</h3>
            <ul>
                <#list id.files as file>
                    <li><h3>${file}</h3></li>
                </#list>
            </ul>
        </div>
    </#list>
</div>
</body>
</html>