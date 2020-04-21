function addSection(section) {
    switch (section) {
        case 'PERSONAL':
        case 'OBJECTIVE':
            document.getElementById(section + "addButton").remove();
            var parentDiv = document.getElementById(section + "div");

            var dd = document.createElement("dd");
            var inputField = document.createElement("input");
            inputField.type = "text";
            inputField.id = section;
            inputField.name = section;
            inputField.size = 70;
            inputField.value = "";
            inputField.required;
            dd.append(inputField);

            var delButton = document.createElement("button");
            delButton.type = "button";
            delButton.id = section + "deleteButton";
            delButton.onclick = function () {
                deleteSection(section);
            };
            delButton.textContent = "Удалить";
            dd.append(delButton);

            parentDiv.append(dd);
            break;
        case 'ACHIEVEMENT':
        case 'QUALIFICATIONS':
            break;
        case 'EXPERIENCE':
        case 'EDUCATION':
            break;
        default:
            alert('Секция не найдена');
    }
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