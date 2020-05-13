$(function () {
    $(document).on("click", "button[id$='addButton']", function () {
        var button = $(this);
        var section = $(this).attr('id').slice(0, -9);
        var parentDiv = $('div#' + section + 'div');
        switch (section) {
            case 'PERSONAL':
            case 'OBJECTIVE':
                parentDiv.children().append(createInput('text',section, section));
                createDeleteButton(parentDiv.children(), section, "");
                button.remove();
                break;
            case 'ACHIEVEMENT':
            case 'QUALIFICATIONS':
                parentDiv.children().append(createInput('textarea', section, section));
                createDeleteButton(parentDiv.children(), section, "");
                button.remove();
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
                $('input#' + section).remove();
                button.remove();
                createAddButton(parentDiv.children(), section, "");
                break;
            case 'ACHIEVEMENT':
            case 'QUALIFICATIONS':
                $('textarea#' + section).remove();
                button.remove();
                createAddButton(parentDiv.children(), section, "");
                break;
            case 'EXPERIENCE':
            case 'EDUCATION':
                button.parents().eq(0).remove();
                button.remove();
                break;
            default:
                button.parent().remove();
                button.remove();
                break;
        }
    });

    $(document).on('click', "input[name*='checkNow']", function () {
        var checkBox = $(this);
        var endPeriod = checkBox.prev();
        if (checkBox.prop('checked') === true) {
            endPeriod.prop('disabled', true);
            endPeriod.prop('value', '');
        } else {
            endPeriod.prop('disabled', false);
        }
    })
});

function createInput(type, id, name) {
    switch (type) {
        case 'text':
        case 'date':
            return $('<input>', {
                type: type,
                id: id,
                name: name,
                size: 50,
                required: true
            });
        case 'textarea':
            return $('<textarea>', {
                type: type,
                id: id,
                name: name,
                rows: 4,
                cols: 70,
                style: "resize:none;",
                required: true
            });
    }
}

function addOrgSection(parent, parentOrg, textContent, type, id, name) {
    var dl = $('<dl>');
    dl.append($('<dt>', {
        text: textContent
    }));
    switch (type) {
        case 'text':
        case 'date':
            dl.append(createInput(type, id, name));
            break;
        case 'textarea':
            dl.append(createInput(type, id, name));
            break;
    }
    parentOrg.append(dl);
    parent.append(parentOrg);
    return dl;
}

function addPosition(id) {
    var orgDiv = $('div#' + id + 'div');
    var posDivs = $('.' + id + 'pos');
    var posIndex = posDivs.length;
    var posDiv = $('<div>', {
        id: id + 'pos' + posIndex,
        class: id + 'pos'
    });
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
    addOrgSection(orgDiv.children().last(), posDiv, 'Позиция', 'text', id + 'position', id + 'position');
    addOrgSection(orgDiv.children().last(), posDiv, 'Описание', 'textarea', id + 'desc', id + 'desc');
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