document.addEventListener('DOMContentLoaded', function() {
    const databaseButton = document.getElementById('databaseButton');
    const accountGeneratorButton = document.getElementById('accountGeneratorButton');
    const contentContainer = document.getElementById('contentContainer');
    let databaseContainer;
    let queryContainer;
    const buttonsContainer = document.getElementById('buttonsContainer');
    databaseButton.addEventListener('click', function() {
        hideMainButtons();
        contentContainer.classList.add('show');
        showDatabaseContainer();
        createDatabaseField();
        createQueryField();
        const backButton = document.createElement('button');
        backButton.textContent = 'Powrót';
        backButton.classList.add('btn');
        backButton.id = 'backButton';
        backButton.addEventListener('click', function() {
            showMainButtons()
            backButton.parentNode.removeChild(backButton);
            hideDatabaseContainer();
            hideQueryField();
        });
       
        buttonsContainer.appendChild(backButton);

        
    
    });

    accountGeneratorButton.addEventListener('click', function() {
        hideMainButtons();
        contentContainer.classList.add('show');
        createAddEmployeeField()
        const backButton = document.createElement('button');
        backButton.textContent = 'Powrót';
        backButton.classList.add('btn');
        backButton.id = 'backButton';
        backButton.addEventListener('click', function() {
            showMainButtons()
            backButton.parentNode.removeChild(backButton);
            hideAddEmployeeField()
        });
       
        buttonsContainer.appendChild(backButton);

        
    
    });

    function hideMainButtons(){
        databaseButton.style.display = 'none';
        accountGeneratorButton.style.display = 'none';
    }
    function showMainButtons(){
        databaseButton.style.display = 'inline-block';
        accountGeneratorButton.style.display = 'inline-block';
    }
    function showDatabaseContainer() {
        if (!databaseContainer) {
            databaseContainer = document.createElement('div');
            databaseContainer.id = 'databaseContainer';
            const employeesHeader = document.createElement('h2');
            employeesHeader.textContent = 'Zarządzanie bazą danych:';
            employeesHeader.id = 'employeesHeader';
            contentContainer.appendChild(employeesHeader);
            contentContainer.appendChild(databaseContainer);

        }
    }
    function hideDatabaseContainer() {
        if (databaseContainer && databaseContainer.parentNode) {
            databaseContainer.parentNode.removeChild(databaseContainer);
            databaseContainer = null;
            const employeesHeader = document.getElementById('employeesHeader');
            if (employeesHeader) {
                employeesHeader.parentNode.removeChild(employeesHeader);
            }

        }
    }

    function createDatabaseField() {
        if (!databaseContainer) {
            console.error('databaseContainer nie istnieje.');
            return;
        }
        const employeesData = [
            { id: 1, name: 'rekordy z bazy danych po zapytaniu sql' },
            { id: 2, name: '' },
        ];

        const databaseRecords = document.createElement('div');
        employeesData.forEach(employee => {
            const record = document.createElement('div');
            record.classList.add('record');
            record.textContent = `${employee.id}. ${employee.name}`;
            record.addEventListener('click', function() {
                record.classList.toggle('selected');
            });

            databaseRecords.appendChild(record);
        });

        databaseContainer.innerHTML = ''; 
        databaseContainer.appendChild(databaseRecords);
    }

    function createQueryField() {
        if (!queryContainer) {
            queryContainer = document.createElement('div');
            queryContainer.id = 'queryContainer';
            const queryInput = document.createElement('input');
            queryInput.setAttribute('type', 'text');
            queryInput.setAttribute('placeholder', 'Wprowadź zapytanie SQL...');
            queryInput.classList.add('input-field');
            queryContainer.appendChild(queryInput);
            contentContainer.appendChild(queryContainer);
        }
        const executeQueryButton = document.createElement('button');
        executeQueryButton.textContent = 'Wykonaj zapytanie SQL';
        executeQueryButton.classList.add('btn');
        executeQueryButton.id = 'executeQueryButton';
        executeQueryButton.addEventListener('click', function() {

        });
       
        buttonsContainer.appendChild(executeQueryButton);
    }

    function hideQueryField() {
        if (queryContainer && queryContainer.parentNode) {
            queryContainer.parentNode.removeChild(queryContainer);
            queryContainer = null;
        }
        const executeQueryButton = document.getElementById('executeQueryButton');
        executeQueryButton.parentNode.removeChild(executeQueryButton);
    }

    function createAddEmployeeField() {
        const addEmployeeContainer = document.createElement('div');
        addEmployeeContainer.id = 'addEmployeeContainer';
    
        addEmployeeContainer.insertAdjacentHTML('beforeend', '<h3>Podaj dane nowego pracownika:</h3>');
        addEmployeeContainer.insertAdjacentHTML('beforeend', '<br>');
        const firstNameInput = document.createElement('input');
        firstNameInput.type = 'text';
        firstNameInput.placeholder = 'Imię';
        firstNameInput.classList.add('input-field');
        addEmployeeContainer.appendChild(firstNameInput);

        const secondNameInput = document.createElement('input');
        secondNameInput.type = 'text';
        secondNameInput.placeholder = 'Dugie imię (opcjonalnie)';
        secondNameInput.classList.add('input-field');
        addEmployeeContainer.appendChild(secondNameInput);
    

        const lastNameInput = document.createElement('input');
        lastNameInput.type = 'text';
        lastNameInput.placeholder = 'Nazwisko';
        lastNameInput.classList.add('input-field');
        addEmployeeContainer.appendChild(lastNameInput);
    

        const emailInput = document.createElement('input');
        emailInput.type = 'email';
        emailInput.placeholder = 'E-mail';
        emailInput.classList.add('input-field');
        addEmployeeContainer.appendChild(emailInput);

        const peselInput = document.createElement('input');
        peselInput.type = 'text';
        peselInput.placeholder = 'Pesel';
        peselInput.classList.add('input-field');
        addEmployeeContainer.appendChild(peselInput);


        const birthDateInput = document.createElement('input');
        birthDateInput.type = 'date';
        birthDateInput.placeholder = 'Data urodzenia';
        birthDateInput.classList.add('input-field');
        addEmployeeContainer.appendChild(birthDateInput);



    
    
        const addButton = document.createElement('button');
        addButton.textContent = 'Wygeneruj konto dla pracownika';
        addButton.classList.add('btn');
        addButton.addEventListener('click', function() {
            firstNameInput.value = '';
            lastNameInput.value = '';
            emailInput.value = '';
        });
        addEmployeeContainer.appendChild(addButton);
        const contentContainer = document.getElementById('contentContainer');
        contentContainer.appendChild(addEmployeeContainer);
    }
    
    function hideAddEmployeeField() {
        const addEmployeeContainer = document.getElementById('addEmployeeContainer');
        if (addEmployeeContainer) {
            addEmployeeContainer.parentNode.removeChild(addEmployeeContainer);
        }
    }



});