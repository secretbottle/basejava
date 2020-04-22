function addSection(section) {
    switch (section) {
        case 'PERSONAL':
        case 'OBJECTIVE':

            var inputField = document.createElement("input");
            inputField.type = "text";
            inputField.id = section;
            inputField.name = section;
            inputField.size = 70;
            inputField.value = "";
            inputField.required;

            document.getElementById(section + "div").append(addDeleteButton(section, inputField));
            break;
        case 'ACHIEVEMENT':
        case 'QUALIFICATIONS':
            var textArea = document.createElement("textarea");
            textArea.id = section;
            textArea.name = section;
            textArea.rows = 4;
            textArea.cols = 70;
            //textArea.style = 'resize:none;';
            textArea.required;

            document.getElementById(section + "div").append(addDeleteButton(section, textArea));
            break;
        case 'EXPERIENCE':
        case 'EDUCATION':
            break;
    }
}

function addDeleteButton(section, field) {
    document.getElementById(section + "addButton").remove();
    var dd = document.createElement("dd");
    dd.append(field);
    var delButton = document.createElement("button");
    delButton.type = "button";
    delButton.id = section + "deleteButton";
    delButton.onclick = function () {
        deleteSection(section);
    };
    delButton.textContent = "Удалить";
    dd.append(delButton);

    return dd;
}

function deleteSection(section) {
    document.getElementById(section + "deleteButton").remove();
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
}