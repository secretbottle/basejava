function addSection(section) {
    switch (section) {
        case 'PERSONAL':
        case 'OBJECTIVE':
            var inputField = document.createElement("input");
            inputField.type = "text";
            inputField.id = section;
            inputField.name = section;
            inputField.size = 70;
            inputField.required;
            addDeleteButton(section, inputField);
            break;
        case 'ACHIEVEMENT':
        case 'QUALIFICATIONS':
            var textArea = document.createElement("textarea");
            textArea.id = section;
            textArea.name = section;
            textArea.rows = 4;
            textArea.cols = 70;
            textArea.style.resize = 'none';
            //textArea.value = "";
            textArea.required;
            addDeleteButton(section, textArea);
            break;
    }
}

function addDeleteButton(section, field) {
    var dl = document.createElement("dl");
    var ddFld = document.createElement("dd");
    ddFld.append(field);

    var ddBtn = document.createElement("dd");
    var delButton = document.createElement("button");
    delButton.type = "button";
    delButton.id = section + "deleteButton";
    delButton.onclick = function () {
        deleteSection(section);
    };
    delButton.textContent = "Удалить";
    ddBtn.append(delButton);
    dl.append(ddFld);
    dl.append(ddBtn);
    document.getElementById(section + "div").append(dl);
    document.getElementById(section + "addButton").remove();
}

function deleteSection(section) {

    document.getElementById(section).remove();
    var parentDiv = document.getElementById(section + "div");
    var addButton = document.createElement("button");
    addButton.type = "button";
    addButton.id = section + "addButton";
    addButton.onclick = function () {
        addSection(section);
    };
    addButton.textContent = "Добавить";

    parentDiv.append(addButton);
    document.getElementById(section + "deleteButton").remove();
}

function addOrganization(section) {
    var parentDiv = document.getElementById(section + "div");
    var parentDivs = document.getElementsByClassName(section + "orgs");
    var index = section + parentDivs.length;
    addDescriptionList("Название организации", "text", parentDiv, section);
    addDescriptionList("Ссылка", "text", parentDiv, index + "urlAdr");
    addDescriptionList("Начало", "date", parentDiv, index + "startPeriod");
    addDescriptionList("Окончание", "date", parentDiv, index + "endPeriod");
    addDescriptionList("Позиция", "text", parentDiv, index + "position");
    addDescriptionList("Описание", "textarea", parentDiv, index + "desc");

    var btn = createDeleteButton(index, index, function () {
        deleteOrganization(index, section);
    });
    parentDiv.append(btn);

    document.getElementById(section + "addButtonOrg").remove();
}

function addDescriptionList(textContent, inputType, parentDiv, id, index, ddBtn) {
    var dl = document.createElement("dl");
    var dt = document.createElement("dt");
    var dd = document.createElement("dd");
    dt.textContent = textContent;
    switch (inputType) {
        case "text":
        case "date":
            var inputField = document.createElement("input");
            inputField.type = inputType;
            inputField.id = id;
            inputField.name = id;
            inputField.size = 20;
            inputField.className = index;
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
            textField.value = "";
            inputField.className = index;
            textField.required;
            dd.append(textField);
            break;
    }
    dl.append(ddBtn);
    dl.append(dt);
    dl.append(dd);
    parentDiv.append(dl);

    return dl;
}

function deleteOrganization(id, section) {
    document.getElementsByClassName(id).remove();
    createAddButton(section, "Организацию")
}

function createAddButton(section, text) {
    var dd = document.createElement("dd");
    var addButton = document.createElement("button");
    addButton.type = "button";
    addButton.id = index;
    addButton.className = index;
    addButton.onclick = function () {
        addOrganization(this.id);
    };
    addButton.textContent = "Добавить " + text;
    dd.append(addButton);
}

function createDeleteButton(id, className, functionName, text){
    var dd = document.createElement("dd");
    var delButton = document.createElement("button");
    delButton.type = "button";
    delButton.id = id;
    delButton.className = className;
    delButton.onclick = functionName;
    delButton.textContent = "Удалить" + text;
    dd.append(delButton);
    return dd;
}

