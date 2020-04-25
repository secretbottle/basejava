function addSection(section) {
    var sectionDiv = document.getElementById(section + "div");
    switch (section) {
        case 'PERSONAL':
        case 'OBJECTIVE':
            addDescriptionList(sectionDiv, "", "text", section);
            createDeleteButton(sectionDiv, "", section + "deleteButton",  function () {
                deleteSection(section);
            });
            document.getElementById(section + "addButton").remove();
            break;
        case 'ACHIEVEMENT':
        case 'QUALIFICATIONS':
            addDescriptionList(sectionDiv, "", "textarea", section);
            createDeleteButton(sectionDiv, "", section + "deleteButton",  function () {
                deleteSection(section);
            });
            document.getElementById(section + "addButton").remove();
            break;
        case 'EXPERIENCE':
        case 'EDUCATION':
            var orgDivs = document.getElementsByClassName(section + "orgs");
            var index = section + orgDivs.length;
            var orgDiv = document.createElement("div");
            orgDiv.id = index + "orgs";
            orgDiv.className = section + "orgs";
            addDescriptionList(orgDiv, "Название организации", "text", section);
            addDescriptionList(orgDiv, "Ссылка", "text", index + "urlAdr");
            addDescriptionList(orgDiv, "Начало", "date", index + "startPeriod");
            addDescriptionList(orgDiv, "Окончание", "date", index + "endPeriod");
            addDescriptionList(orgDiv, "Позиция", "text", index + "position");
            addDescriptionList(orgDiv, "Описание", "textarea", index + "desc");
            sectionDiv.append(orgDiv);
            createDeleteButton(orgDiv, "организацию", index + "deleteButton", function () {
                deleteOrganization(index + "orgs");
            });
            break;
    }
}

function deleteSection(id) {
    createAddButton(id, "");
    document.getElementById(id).remove();
    document.getElementById(id + "deleteButton").remove();
}

function deleteOrganization(id) {
    document.getElementById(id).remove();
}

function addDescriptionList(parentDiv, textContent, inputType, id) {
    var dl = document.createElement("dl");
    var dt = document.createElement("dt");
    var dd = document.createElement("dd");
    if(textContent !== "")
        dt.textContent = textContent;

    switch (inputType) {
        case "text":
        case "date":
            var inputField = document.createElement("input");
            inputField.type = inputType;
            inputField.id = id;
            inputField.name = id;
            inputField.size = 70;
            inputField.required;
            dd.append(inputField);
            break;
        case "textarea":
            var textField = document.createElement("textarea");
            textField.type = inputType;
            textField.id = id;
            textField.name = id;
            textField.rows = 4;
            textField.cols = 70;
            textField.style.resize = 'none';
            textField.required;
            dd.append(textField);
            break;
    }
    dl.append(dt);
    dl.append(dd);
    parentDiv.append(dl);
    return dl;
}

function createAddButton(section, text) {
    var dd = document.createElement("dd");
    var addButton = document.createElement("button");
    addButton.type = "button";
    addButton.id = section + "addButton";
    addButton.onclick = function () {
        addSection(section);
    };
    addButton.textContent = "Добавить " + text;
    dd.append(addButton);
    document.getElementById(section + "div").append(dd);
}

function createDeleteButton(parentDiv, text, id, functionName) {
    var dd = document.createElement("dd");
    var delButton = document.createElement("button");
    delButton.type = "button";
    delButton.id = id;
    delButton.onclick = functionName;
    delButton.textContent = "Удалить " + text;
    dd.append(delButton);
    parentDiv.appendChild(dd);
}
