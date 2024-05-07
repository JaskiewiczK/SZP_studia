const editButton = document.getElementById('editButton');
const vacationButton = document.getElementById('vacButton');

editButton.addEventListener('click', () => {
    const container = document.querySelector('.employee-section .container');

    const isEditing = container.querySelector('.input-field');
    if (!isEditing) {
        const editContainer = document.createElement('div');
        editContainer.classList.add('edit-container');
        editContainer.insertAdjacentHTML('beforeend', '<br>');
        editContainer.insertAdjacentHTML('beforeend', '<p>Podaj nowe dane:</p>');
        editContainer.insertAdjacentHTML('beforeend', '<br>');
        const fields = ['Imię', 'Drugie imię','Nazwisko', 'Email', 'Data urodzenia','Pesel'];
        fields.forEach(field => {
            const input = document.createElement('input');
            input.setAttribute('type', 'text');
            input.setAttribute('placeholder', field);
            input.classList.add('input-field');
            editContainer.appendChild(input);
            editContainer.appendChild(document.createElement('br'));
        });

        const submitButton = document.createElement('button');
        submitButton.textContent = 'Zatwierdź';
        submitButton.classList.add('submit-button');
        submitButton.addEventListener('click', () => {
            console.log('Dane zostały zatwierdzone');

            editContainer.remove();
            editButton.style.display = 'block';

            vacationButton.style.display = 'block';
        });

        editContainer.appendChild(submitButton);
        editButton.style.display = 'none';
        container.appendChild(editContainer);
        const functionsSection = document.querySelector('.functions-section');
        functionsSection.style.display = 'block';
        vacationButton.style.display = 'none';
    }
});
vacationButton.addEventListener('click', () => {
    const container = document.querySelector('.employee-section .container');

    const isEditing = container.querySelector('.input-field');
    if (!isEditing) {
        const editContainer = document.createElement('div');
        editContainer.classList.add('edit-container');
        editContainer.insertAdjacentHTML('beforeend', '<br>');
        editContainer.insertAdjacentHTML('beforeend', '<p>Wybierz wniosek:</p>');
        editContainer.insertAdjacentHTML('beforeend', '<select>       <option value="opcja1">Zwolnienie lekarskie</option>        <option value="opcja2">wniosek 1 </option>        <option value="opcja3">wniosek 2</option>    </select>');
        editContainer.insertAdjacentHTML('beforeend', '<br>');
        const submitButton = document.createElement('button');
        submitButton.textContent = 'Wyślij wniosek';
        submitButton.classList.add('submit-button');
        submitButton.addEventListener('click', () => {
            console.log('Dane zostały zatwierdzone');

            editContainer.remove();
            editButton.style.display = 'block';

            vacationButton.style.display = 'block';
        });

        editContainer.appendChild(submitButton);
        editButton.style.display = 'none';
        container.appendChild(editContainer);
        const functionsSection = document.querySelector('.functions-section');
        functionsSection.style.display = 'block';
        vacationButton.style.display = 'none';
    }
});