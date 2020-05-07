function sectionSelector(section) {
    var parentDiv = document.getElementById(section + "div");
    switch (section) {
        case 'PERSONAL':
        case 'OBJECTIVE':
            var parent = addSection(parentDiv, "text", section);
            createDeleteButton(parent, section, "", function () {
                deleteSection(section);
            });
            document.getElementById(section + "addButton").remove();
            break;
        case 'ACHIEVEMENT':
        case 'QUALIFICATIONS':
            var parent = addSection(parentDiv, "textarea", section);
            createDeleteButton(parent, section, "", function () {
                deleteSection(section);
            });
            document.getElementById(section + "addButton").remove();
            break;
        case 'EXPERIENCE':
        case 'EDUCATION':
            var orgDivs = document.getElementsByClassName(section + "orgs");
            var index = section + orgDivs.length;
            var orgDiv = document.createElement("div");
            orgDiv.id = index + "div";
            orgDiv.className = section + "orgs";
            //TODO add parentDiv
            createDeleteButton(orgDiv, index, "организацию", function () {
                deleteOrgPos(orgDiv.getAttribute("id"));
            });
            //TODO addSection->addOrganizationSection
            addOrganizationSection(parentDiv, orgDiv, "Название организации", "text", section, section);
            addOrganizationSection(parentDiv, orgDiv, "Ссылка", "text", section + "urlAdr",section + "urlAdr");
            createAddButton(orgDiv, index, "должность", function () {
                addPosition(index);
            });

            addPosition(index);

            break;
    }
}

function addSection(parent, inputType, idName) {
    var child = parent.firstElementChild;
    switch (inputType) {
        case "text":
            var inputField = document.createElement("input");
            inputField.type = inputType;
            inputField.id = idName;
            inputField.name = idName;
            inputField.size = 70;
            inputField.required;
            child.appendChild(inputField);
            break;
        case "textarea":
            var textField = document.createElement("textarea");
            textField.type = inputType;
            textField.id = idName;
            textField.name = idName;
            textField.rows = 4;
            textField.cols = 70;
            textField.style.resize = 'none';
            textField.required;
            child.appendChild(textField);
            break;
    }

    return child;
}

function deleteSection(id) {
    var parent = document.getElementById(id + "div").firstElementChild;
    createAddButton(parent, id, "", function () {
        sectionSelector(id);
    });
    document.getElementById(id).remove();
    document.getElementById(id + "deleteButton").remove();
}

function addOrganizationSection(parent, parentOrg, textContent, inputType, id, name) {
    var child = parent.firstElementChild;
    var dl = document.createElement("dl");
    var dd = document.createElement("dd");
    var dt = document.createElement("dt");
    dt.textContent = textContent;
    var inputField = document.createElement("input");
    inputField.type = inputType;
    inputField.id = id;
    inputField.name = name;
    inputField.size = 50;
    inputField.required;

    dd.append(inputField);
    dl.append(dt);
    dl.append(dd);
    parentOrg.append(dl);
    child.appendChild(parentOrg);
    return dd;
}

function addPosition(id) {
    var orgDiv = document.getElementById(id + "div");
    var posDivs = document.getElementsByClassName(id + "pos");
    var posIndex = posDivs.length;
    var posDiv = document.createElement("div");
    posDiv.id = id + "pos" + posIndex;
    posDiv.className = id + "pos";
    //TODO addSection->addOrganizationSection
    addOrganizationSection(orgDiv, posDiv, "Начало", "date", id + "startPeriod", id + "startPeriod");
    //TODO addOrganizationSection problems with args 4 -> 3
    var endPeriod = addOrganizationSection(orgDiv, posDiv, "Окончание", "date", id + "endPeriod" + posIndex, id + "endPeriod");
    var checkBox = document.createElement("input");
    checkBox.type = "checkBox";
    checkBox.id = id + "checkNow" + posIndex;
    checkBox.name = id + "checkNow";
    checkBox.onclick = function () {
        checkNow(id, posIndex)
    };
    endPeriod.appendChild(checkBox);

    var labelCheckBox = document.createElement("label");
    labelCheckBox.htmlFor = id + "checkNow";
    labelCheckBox.textContent = "Сейчас";
    endPeriod.appendChild(labelCheckBox);
    //TODO addSection->addOrganizationSection
    addOrganizationSection(orgDiv, posDiv, "Позиция", "text", id + "position", id + "position");
    addOrganizationSection(orgDiv, posDiv, "Описание", "textarea", id + "desc", id + "desc");
    orgDiv.append(posDiv);
    createDeleteButton(posDiv, id, " должность", function () {
        deleteOrgPos(posDiv.getAttribute("id"));
    });
}

function deleteOrgPos(id) {
    document.getElementById(id).remove();
}

function createAddButton(parent, id, textContent, functionName) {
    var dd = document.createElement("dd");
    var addButton = document.createElement("button");
    addButton.type = "button";
    addButton.id = id + "addButton";
    addButton.onclick = functionName;
    addButton.textContent = "Добавить " + textContent;
    dd.append(addButton);
    parent.appendChild(dd);
}

function createDeleteButton(parentDiv, id, textContent, functionName) {
    var dd = document.createElement("dd");
    var delButton = document.createElement("button");
    delButton.type = "button";
    delButton.id = id + "deleteButton";
    delButton.onclick = functionName;
    delButton.textContent = "Удалить " + textContent;
    dd.append(delButton);
    parentDiv.appendChild(dd);
}

function checkNow(orgIndex, posIndex) {
    var checkBox = document.getElementById(orgIndex + "checkNow" + posIndex);
    var inputData = document.getElementById(orgIndex + "endPeriod" + posIndex);

    if (checkBox.checked === true) {
        inputData.disabled = true;
        inputData.value = "";
    } else {
        inputData.disabled = false;
    }
}
