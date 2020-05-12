$(function () {
    $(document).on("click", "button[id$='addButton']", function () {
        var button = $(this);
        var section = $(this).attr('id').slice(0, -9);
        var parentDiv = $('div#' + section + 'div');

        switch (section) {
            case 'PERSONAL':
            case 'OBJECTIVE':
                parentDiv.children().append($('<input>', {
                    type: 'text',
                    id: section,
                    name: section,
                    size: 70,
                    required: true
                }));
                createDeleteButton(parentDiv.children(), section, "");
                button.hide();
                break;
            case 'ACHIEVEMENT':
            case 'QUALIFICATIONS':
                parentDiv.children().append($('<textarea>', {
                    id: section,
                    name: section,
                    rows: 4,
                    cols: 70,
                    style: "resize:none;",
                    required: true
                }));
                createDeleteButton(parentDiv.children(), section, "");
                button.hide();
                break;
            case 'EXPERIENCE':
            case 'EDUCATION':
                var orgDivs = $('.' + section + 'orgs');
                var index = section + orgDivs.length;
                var orgDiv = $('<div>', {
                    id: index + 'div',
                    class: section + 'orgs'
                });
                parentDiv.children().children().last().append(orgDiv);

                addOrgSection(parentDiv, orgDiv, 'Название организации', 'text', section, section);
                addOrgSection(parentDiv, orgDiv, 'Ссылка', 'text', section + "urlAdr", section + "urlAdr");
                createAddButton(orgDiv, index, 'должность');
                createDeleteButton(orgDiv, index, 'организацию');
                addPosition(index);
                break;
            default:
                addPosition(section);
                break;
        }

    });

    $(document).on('click', "button[id$='deleteButton']", function () {
        var button = $(this);
        var section = $(this).attr('id').slice(0, -12);
        var parentDiv = $('div#' + section + 'div');
        switch (section) {
            case 'PERSONAL':
            case 'OBJECTIVE':
                $('input#' + section).hide();
                button.hide();
                createAddButton(parentDiv.children(), section, "");
                break;
            case 'ACHIEVEMENT':
            case 'QUALIFICATIONS':
                $('textarea#' + section).hide();
                button.hide();
                createAddButton(parentDiv.children(), section, "");
                break;
            case 'EXPERIENCE':
            case 'EDUCATION':
                button.parents().eq(0).remove();
                button.remove();
                break;
            default:
                console.log(section);
                console.log(button.parent());
                button.parent().remove();
                button.remove();
                break;
        }
    });

});

function addOrgSection(parent, parentOrg, textContent, type, id, name) {
    var dl = $('<dl>');
    dl.append($('<dt>', {
        text: textContent
    }));
    dl.append($('<input>', {
        type: type,
        id: id,
        name: name,
        size: 50,
        required: true
    }));
    parentOrg.append(dl);
    parent.append(parentOrg);
    return dl;
}

function addPosition(id) {
    var orgDiv = $('div#' + id + 'div');
    var posDivs = $('.' + id + 'pos');
    console.log("Позиций" + posDivs + posDivs.length);
    var posIndex = posDivs.length;
    var posDiv = $('<div>', {
        id: id + 'pos' + posIndex,
        class: id + 'pos'
    });
    console.log(orgDiv.children().last());
    addOrgSection(orgDiv.children().last(), posDiv, 'Начало', 'date', id + 'startPeriod', id + 'startPeriod');
    var endPeriod = addOrgSection(orgDiv.children().last(), posDiv, 'Окончание', 'date', id + 'endPeriod' + posIndex, id + 'endPeriod');
    var checkBox = $('<input>', {
        type: 'checkBox',
        id: id + 'checkNow' + posIndex,
        name: id + 'checkNow'
    });
    endPeriod.append(checkBox);
    endPeriod.last().append($('<lable>', {
        text: 'Сейчас'
    }));
    addOrgSection(orgDiv.children().last(), posDiv, 'Позиция', "text", id + "position", id + "position");
    addOrgSection(orgDiv.children().last(), posDiv, 'Описание', 'textarea', id + "desc", id + "desc");
    createDeleteButton(posDiv, id, " должность");
    orgDiv.append(posDiv);
}

function createAddButton(parent, id, textContent) {
    parent.append($('<button>', {
        type: 'button',
        id: id + "addButton",
        text: "Добавить " + textContent
    }));
}

function createDeleteButton(parent, id, textContent) {
    parent.append($('<button>', {
        type: 'button',
        id: id + "deleteButton",
        text: "Удалить " + textContent
    }));
}
