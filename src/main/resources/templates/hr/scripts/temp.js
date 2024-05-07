const addButton = document.createElement('button');
addButton.textContent = 'Dodaj stanowisko';
addButton.classList.add('btn');
addButton.id = 'addButton';

addButton.addEventListener('click', function() {
    backButton.parentNode.removeChild(backButton);
    addButton.parentNode.removeChild(addButton);
    assignButton.parentNode.removeChild(assignButton);
    const positionInput = document.createElement('input');
    positionInput.type = 'text';
    positionInput.placeholder = 'Nazwa stanowiska';
    positionInput.id = 'positionInput';
    buttonsContainer.appendChild(positionInput);
    buttonsContainer.appendChild(backButton2);
    buttonsContainer.appendChild(submitButton);
});
const buttonsContainer = document.getElementById('buttonsContainer');
buttonsContainer.appendChild(addButton);

const assignButton = document.createElement('button');
assignButton.textContent = 'Przypisz pracownikowi stanowisko';
assignButton.classList.add('btn');
assignButton.id = 'assignButton';
assignButton.addEventListener('click', function() {

});
buttonsContainer.appendChild(assignButton);

const backButton = document.createElement('button');
backButton.textContent = 'Powrót';
backButton.classList.add('btn');
backButton.id = 'backButton';
backButton.addEventListener('click', function() {
    showMainButtons()
    backButton.parentNode.removeChild(backButton);
    addButton.parentNode.removeChild(addButton);
    assignButton.parentNode.removeChild(assignButton);

});

buttonsContainer.appendChild(backButton);

const backButton2 = document.createElement('button');
backButton2.textContent = 'Powrót';
backButton2.classList.add('btn');
backButton2.id = 'backButton2';
backButton2.addEventListener('click', function() {
    backButton2.parentNode.removeChild(backButton2);
    buttonsContainer.appendChild(addButton);
    buttonsContainer.appendChild(assignButton);
    buttonsContainer.appendChild(backButton);
    const positionInput = document.getElementById('positionInput');
    positionInput.parentNode.removeChild(positionInput);
});

const submitButton = document.createElement('button');
submitButton.textContent = 'Zatwierdź';
submitButton.classList.add('btn');
submitButton.addEventListener('click', function() {
    inputFields.parentNode.removeChild(inputFields);
});


