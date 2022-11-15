
window.isEditing = false;
const todos = document.querySelector('.todo-list');
const inputEdit = document.querySelector('.edit');


todos.ondblclick = (e) => {
    const labelTitle = e.target;
    if (labelTitle.closest('label')) {
        const item = getParent(labelTitle, 'li');
        startEdit(item, labelTitle.textContent);
    }
}

todos.onkeyup = (e) => {
    const inputTitle = e.target;
    if (inputTitle.closest('.edit') && e.keyCode === 27) {
        const item = getParent(inputTitle, 'li');
        cancelEdit(item);
    }
}

function startEdit(selector, value){
    if (isEditing === false && selector) {
    	isEditing = !isEditing;
        const inputTitle = selector.querySelector('input.edit');
        inputTitle.value = value;
        selector.classList.add('editing');
    }
}

function cancelEdit(selector) {
    if (isEditing === true && selector) {
        selector.classList.remove('editing');
        isEditing = false;
    }
}

function getParent(element, selector) {
    while (element.parentElement) {
        if (element.parentElement.matches(selector)) {
            return element.parentElement;
        }
        element = element.parentElement;
    }
}