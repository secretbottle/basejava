function addSection(section) {
    switch (section) {
        case 'PERSONAL':
        case 'OBJECTIVE':
            var textDiv = document.createElement("div");
            textDiv.id = section + "div";
            var dl = addDescriptionList(textDiv, "", "text", section);
            createDeleteButton(dl, section, "", function () {
                deleteSection(section);
            });
            document.getElementById(section + "textDiv").append(textDiv);
            document.getElementById(section + "addButton").remove();
            break;
        case 'ACHIEVEMENT':
        case 'QUALIFICATIONS':
            var listDiv = document.createElement("div");
            listDiv.id = section + "div";
            var dl = addDescriptionList(listDiv, "", "textarea", section);
            createDeleteButton(dl, section, "", function () {
                deleteSection(section);
            });
            document.getElementById(section + "listDiv").append(listDiv);
            document.getElementById(section + "addButton").remove();
            break;
        case 'EXPERIENCE':
        case 'EDUCATION':
            var orgDivs = document.getElementsByClassName(section + "orgs");
            var index = section + orgDivs.length;
            var orgDiv = document.createElement("div");
            orgDiv.id = index + "div";
            orgDiv.className = section + "orgs";
            createDeleteButton(orgDiv, index, "организацию", function () {
                deleteOrgPos(orgDiv.getAttribute("id"));
            });
            addDescriptionList(orgDiv, "Название организации", "text", section);
            addDescriptionList(orgDiv, "Ссылка", "text", section + "urlAdr");
            createAddButton(orgDiv, index, "должность", function () {
                addPosition(index);
            });

            document.getElementById(section + "div").append(orgDiv);
            addPosition(index);

            break;
    }
}

function deleteSection(id) {
    var parentDiv = document.getElementById(id + "div");
    createAddButton(parentDiv, id, "", function () {
        addSection(id);
    });
    document.getElementById(id).remove();
    document.getElementById(id + "deleteButton").remove();
}

function addPosition(id) {
    var orgDiv = document.getElementById(id + "div");
    var posDivs = document.getElementsByClassName(id + "pos");
    var posDiv = document.createElement("div");
    posDiv.id = id + "pos" + posDivs.length;
    posDiv.className = id + "pos";
    addDescriptionList(posDiv, "Начало", "date", id + "startPeriod");
    addDescriptionList(posDiv, "Окончание", "date", id + "endPeriod");
    addDescriptionList(posDiv, "Позиция", "text", id + "position");
    addDescriptionList(posDiv, "Описание", "textarea", id + "desc");
    orgDiv.append(posDiv);
    createDeleteButton(posDiv, id, " должность", function () {
        deleteOrgPos(posDiv.getAttribute("id"));
    });
}

function deleteOrgPos(id) {
    document.getElementById(id).remove();
}

function addDescriptionList(parentDiv, textContent, inputType, id) {
    var dl = document.createElement("dl");
    var dt = document.createElement("dt");
    var dd = document.createElement("dd");
    if (textContent !== "")
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

function createAddButton(parentDiv, id, textContent, functionName) {
    var dd = document.createElement("dd");
    var addButton = document.createElement("button");
    addButton.type = "button";
    addButton.id = id + "addButton";
    addButton.onclick = functionName;
    addButton.textContent = "Добавить " + textContent;
    dd.append(addButton);
    parentDiv.append(dd);
}

function createDeleteButton(parentDiv, id, textContent, functionName) {
    var dd = document.createElement("dd");
    var delButton = document.createElement("button");
    delButton.type = "button";
    delButton.id = id + "deleteButton";
    delButton.onclick = functionName;
    delButton.textContent = "Удалить " + textContent;
    dd.append(delButton);
    parentDiv.append(dd);
}
