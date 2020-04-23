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
            textArea.value = "";
            textArea.required;

            addDeleteButton(section, textArea);

            //document.getElementById(section + "div").append(addDeleteButton(section, textArea));
            break;
        case 'EXPERIENCE':
        case 'EDUCATION':
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
    //TODO FIX THIS
    addDescriptionList("Название организации", "text", parentDiv, section);
    addDescriptionList("Ссылка", "text", parentDiv, section + parentDivs.length++ + "urlAdr");


    /*    var dt = document.createElement("dt");
        var titleOrg = document.textContent = "Название организации";
        dt.append(titleOrg);
        document.createElement("br").append(dt);
        parentDiv.appendChild(dt);
        var dd = document.createElement("dd");
        var inputField = document.createElement("input");
        inputField.type = "text";
        inputField.id = section;
        inputField.name = section;
        inputField.size = 20;
        inputField.required;
        dd.append(inputField);
        document.createElement("br").append(dd);
        parentDiv.appendChild(dd);*/


    document.getElementById(section + "addButtonOrg").remove();
}

function addDescriptionList(textContent, inputType, parentDiv, id) {
    var dl = document.createElement("dl");
    var dt = document.createElement("dt");
    var dd = document.createElement("dd");
    dt.textContent = textContent;
    var inputField = document.createElement("input");
    inputField.type = inputType;
    inputField.id = id;
    inputField.name = id;
    inputField.size = 20;
    inputField.required;
    dd.append(inputField);
    dl.append(dt);
    dl.append(dd);
    parentDiv.append(dl);
}


function deleteOrganization(index) {

}

