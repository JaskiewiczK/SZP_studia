document.addEventListener('DOMContentLoaded', function() {
    const emploeeManageButton = document.getElementById('empmanButton');
    const harmonogramButton = document.getElementById('harmButton');
    const workplaceManageButton = document.getElementById('workplacemanButton');
    const newOrderButton = document.getElementById('neworderButton');
    const employeesContainer = document.getElementById('employeesContainer');
    let databaseContainer;
    createNewOrderForm();
    hideNewCustomerForm()
    removeCheckBox()
    hideNewAssignmentForm()
    emploeeManageButton.addEventListener('click', function() {
        hideMainButtons();
        employeesContainer.classList.add('show');
        createDynamicButtons();
        showDatabaseContainer();
        createEmployeeRecords();

    });

    workplaceManageButton.addEventListener('click', function() {
        hideMainButtons();
        employeesContainer.classList.add('show');
        createWorkPlaceButtons();
    });

    newOrderButton.addEventListener('click', function() {
        showCheckBox();
        hideMainButtons();
        showNewCustomerForm()
        showNewAssignmentForm()
        employeesContainer.classList.add('show');
        const backButton = document.createElement('button');
        backButton.textContent = 'Powrót';
        backButton.classList.add('btn');
        backButton.id = 'backButton';
        backButton.addEventListener('click', function() {
            showMainButtons();
            backButton.parentNode.removeChild(backButton);
            hideCustomersComboBox();
            hideNewCustomerForm()
            removeCheckBox()
            hideNewAssignmentForm()

        });
        

       
        buttonsContainer.appendChild(backButton);
    });

    function hideMainButtons(){
        harmonogramButton.style.display = 'none';
        workplaceManageButton.style.display = 'none';
        emploeeManageButton.style.display = 'none';
        newOrderButton.style.display = 'none';
    }
    function showMainButtons(){
        harmonogramButton.style.display = 'inline-block';
        emploeeManageButton.style.display = 'inline-block';
        workplaceManageButton.style.display = 'inline-block';
        newOrderButton.style.display = 'inline-block';
    }

    
    function createWorkPlaceButtons()
    {
        //add button
        const addButton = document.createElement('button');
        addButton.textContent = 'Dodaj stanowisko';
        addButton.classList.add('btn');
        addButton.id = 'addButton';
        const employeesHeader = document.createElement('h2');
        employeesHeader.textContent = 'Dodaj nowe stanowisko:';
        employeesHeader.id = 'employeesHeader';

        const employeesChooseHeader = document.createElement('h2');
        employeesChooseHeader.textContent = 'Wybierz pracownika:';
        employeesChooseHeader.id = 'employeesChooseHeader';

        const positionChooseHeader = document.createElement('h2');
        positionChooseHeader.textContent = 'Wybierz stanowisko:';
        positionChooseHeader.id = 'positionChooseHeader';

        addButton.addEventListener('click', function() {
            backButton.parentNode.removeChild(backButton);
            addButton.parentNode.removeChild(addButton);
            assignButton.parentNode.removeChild(assignButton);
            employeesContainer.appendChild(employeesHeader);
            const positionInput = document.createElement('input');
            positionInput.type = 'text';
            positionInput.placeholder = 'Nazwa stanowiska';
            positionInput.id = 'positionInput';
            employeesContainer.appendChild(positionInput);
            buttonsContainer.appendChild(submitButton);
            buttonsContainer.appendChild(backButton2);
            
        });
        const buttonsContainer = document.getElementById('buttonsContainer');
        buttonsContainer.appendChild(addButton);

        //assign button
        const assignButton = document.createElement('button');
        assignButton.textContent = 'Przypisz pracownikowi stanowisko';
        assignButton.classList.add('btn');
        assignButton.id = 'assignButton';
        assignButton.addEventListener('click', function() {
            backButton.parentNode.removeChild(backButton);
            addButton.parentNode.removeChild(addButton);
            assignButton.parentNode.removeChild(assignButton);
            employeesContainer.appendChild(employeesChooseHeader);
            createEmployeesComboBox()
            employeesContainer.appendChild(positionChooseHeader);
            createPositionsComboBox()
            employeesContainer.appendChild(confirmButton);
            employeesContainer.appendChild(backButton3);

        });
        buttonsContainer.appendChild(assignButton);

        //back button
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


        //second back button
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
        employeesHeader.parentNode.removeChild(employeesHeader);
        submitButton.parentNode.removeChild(submitButton);
    });

            //third back button
            const backButton3 = document.createElement('button');
            backButton3.textContent = 'Powrót';
            backButton3.classList.add('btn');
            backButton3.id = 'backButton3';
            backButton3.addEventListener('click', function() {
            backButton3.parentNode.removeChild(backButton3);
            buttonsContainer.appendChild(addButton);
            buttonsContainer.appendChild(assignButton);
            buttonsContainer.appendChild(backButton);
            hideEmployeesComboBox()
            hidePositionsComboBox()
            employeesChooseHeader.parentNode.removeChild(employeesChooseHeader);
            positionChooseHeader.parentNode.removeChild(positionChooseHeader);
            confirmButton.parentNode.removeChild(confirmButton);
        });

    //sumbit
        const submitButton = document.createElement('button');
        submitButton.textContent = 'Zatwierdź';
        submitButton.classList.add('btn');
        submitButton.addEventListener('click', function() {
        });
//confirm
        const confirmButton = document.createElement('button');
        confirmButton.textContent = 'Przypisz wybranemu pracownikowi wybrane stanowisko';
        confirmButton.classList.add('btn');
        confirmButton.id = 'confirmButton';
        confirmButton.addEventListener('click', function() {
        });


        buttonsContainer.appendChild(backButton);

    }

    function createNewOrderForm() {
        const chooseFromDatabaseCheckbox = document.createElement('input');
        chooseFromDatabaseCheckbox.type = 'checkbox';
        chooseFromDatabaseCheckbox.id = 'chooseFromDatabaseCheckbox';
        const chooseFromDatabaseLabel = document.createElement('label');
        chooseFromDatabaseLabel.textContent = 'Wybierz klienta z bazy';
        chooseFromDatabaseLabel.htmlFor = 'chooseFromDatabaseCheckbox';
        chooseFromDatabaseLabel.id = 'chooseFromDatabaseLabel';
        employeesContainer.appendChild(chooseFromDatabaseLabel);
        employeesContainer.appendChild(chooseFromDatabaseCheckbox);

            createCustomersComboBox()
        createNewCustomerForm()
        createNewAssignmentForm()
        chooseFromDatabaseCheckbox.checked = false;
        chooseFromDatabaseCheckbox.addEventListener('change', function() {
            if (chooseFromDatabaseCheckbox.checked) {
                showCustomersComboBox()
                hideNewCustomerForm()
            } else {
                hideCustomersComboBox();
                showNewCustomerForm()
            }
        });

    }
    function showCheckBox() {
        const chooseFromDatabaseCheckbox = document.getElementById('chooseFromDatabaseCheckbox');
        const chooseFromDatabaseLabel = document.getElementById('chooseFromDatabaseLabel');
        if (chooseFromDatabaseCheckbox && chooseFromDatabaseLabel) {
            chooseFromDatabaseCheckbox.checked = false;
            chooseFromDatabaseCheckbox.style.display = 'block';
            chooseFromDatabaseLabel.style.display = 'block';
        }
    }
    
    function removeCheckBox() {
        const chooseFromDatabaseCheckbox = document.getElementById('chooseFromDatabaseCheckbox');
        const chooseFromDatabaseLabel = document.getElementById('chooseFromDatabaseLabel');
        if (chooseFromDatabaseCheckbox && chooseFromDatabaseLabel) {
            chooseFromDatabaseCheckbox.style.display = 'none';
            chooseFromDatabaseLabel.style.display = 'none';
        }
    }

    function createNewCustomerForm() {
        const newCustomerForm = document.createElement('div');
        newCustomerForm.id = 'newCustomerForm';
        // const nameLabel = document.createElement('label');
        // nameLabel.textContent = 'Imię:';
        // nameLabel.id = 'nameLabel';
        // newCustomerForm.appendChild(nameLabel);
        const textcustomer = document.createElement('label');
        textcustomer.textContent = 'Wprowadź dane nowego klienta:';
        newCustomerForm.appendChild(textcustomer);
        const nameInput = document.createElement('input');
        nameInput.type = 'text';
        nameInput.placeholder = 'Imię';
        nameInput.id = 'nameInput';
        newCustomerForm.appendChild(nameInput);
        
        // const secNameLabel = document.createElement('label');
        // secNameLabel.textContent = 'Drugie imię:';
        // secNameLabel.id = 'secNameLabel';
        // newCustomerForm.appendChild(secNameLabel);
        
        const secNameInput = document.createElement('input');
        secNameInput.type = 'text';
        secNameInput.placeholder = 'Drugie imię (opcjonalnie)';
        secNameInput.id = 'secNameInput';
        newCustomerForm.appendChild(secNameInput);
        
        // const lastNameLabel = document.createElement('label');
        // lastNameLabel.textContent = 'Nazwisko:';
        // lastNameLabel.id = 'lastNameLabel';
        // newCustomerForm.appendChild(lastNameLabel);
        
        const lastNameInput = document.createElement('input');
        lastNameInput.type = 'text';
        lastNameInput.placeholder = 'Nazwisko';
        lastNameInput.id = 'lastNameInput';
        newCustomerForm.appendChild(lastNameInput);
        
        // const emailLabel = document.createElement('label');
        // emailLabel.textContent = 'Email:';
        // emailLabel.id = 'emailLabel';
        // newCustomerForm.appendChild(emailLabel);
        
        const emailInput = document.createElement('input');
        emailInput.type = 'text';
        emailInput.placeholder = 'E-mail';
        emailInput.id = 'emailInput';
        newCustomerForm.appendChild(emailInput);
        
        // const phoneLabel = document.createElement('label');
        // phoneLabel.textContent = 'Nr telefonu:';
        // phoneLabel.id = 'phoneLabel';
        // newCustomerForm.appendChild(phoneLabel);
        
        const phoneInput = document.createElement('input');
        phoneInput.type = 'text';
        phoneInput.placeholder = 'Nr telefonu';
        phoneInput.id = 'phoneInput';
        newCustomerForm.appendChild(phoneInput);
        
        // const bankAccountLabel = document.createElement('label');
        // bankAccountLabel.textContent = 'Nr konta bankowego:';
        // bankAccountLabel.id = 'bankAccountLabel';
        // newCustomerForm.appendChild(bankAccountLabel);
        
        const bankAccountInput = document.createElement('input');
        bankAccountInput.type = 'text';
        bankAccountInput.placeholder = 'Nr konta bankowego';
        bankAccountInput.id = 'bankAccountInput';
        newCustomerForm.appendChild(bankAccountInput);
        
        // const addButton = document.createElement('button');
        // addButton.textContent = 'Dodaj nowego klienta';
        // addButton.classList.add('btn');
        // addButton.addEventListener('click', function() {
        // });
        // newCustomerForm.appendChild(addButton);
        
        employeesContainer.appendChild(newCustomerForm);
    }
    
    function showNewCustomerForm() {
        const newCustomerForm = document.getElementById('newCustomerForm');
        if (newCustomerForm) {
            newCustomerForm.style.display = 'block';
        }
    }
    
    function hideNewCustomerForm() {
        const newCustomerForm = document.getElementById('newCustomerForm');
        if (newCustomerForm) {
            newCustomerForm.style.display = 'none';
        }
    }



    function createNewAssignmentForm() {
        const newAssignmentForm = document.createElement('div');
        newAssignmentForm.id = 'newAssignmentForm';
        // const nameLabel = document.createElement('label');
        // nameLabel.textContent = 'Imię:';
        // nameLabel.id = 'nameLabel';
        // newCustomerForm.appendChild(nameLabel);
        const textcustomer = document.createElement('label');
        textcustomer.textContent = 'Podaj dane zlecenia:';
        newAssignmentForm.appendChild(textcustomer);
        const nameInput = document.createElement('input');
        nameInput.type = 'text';
        nameInput.placeholder = 'Opis';
        nameInput.id = 'nameInput';
        newAssignmentForm.appendChild(nameInput);
        
        // const secNameLabel = document.createElement('label');
        // secNameLabel.textContent = 'Drugie imię:';
        // secNameLabel.id = 'secNameLabel';
        // newCustomerForm.appendChild(secNameLabel);
        
        const secNameInput = document.createElement('input');
        secNameInput.type = 'text';
        secNameInput.placeholder = 'Koszt';
        secNameInput.id = 'secNameInput';
        newAssignmentForm.appendChild(secNameInput);
        
        // const lastNameLabel = document.createElement('label');
        // lastNameLabel.textContent = 'Nazwisko:';
        // lastNameLabel.id = 'lastNameLabel';
        // newCustomerForm.appendChild(lastNameLabel);
        
        const lastNameInput = document.createElement('input');
        lastNameInput.type = 'text';
        lastNameInput.placeholder = 'Nr rej pojazdu';
        lastNameInput.id = 'lastNameInput';
        newAssignmentForm.appendChild(lastNameInput);
        
        // const emailLabel = document.createElement('label');
        // emailLabel.textContent = 'Email:';
        // emailLabel.id = 'emailLabel';
        // newCustomerForm.appendChild(emailLabel);
        
        const emailInput = document.createElement('input');
        emailInput.type = 'text';
        emailInput.placeholder = 'Stan licznika pojazdu';
        emailInput.id = 'emailInput';
        newAssignmentForm.appendChild(emailInput);
    
        
        const addButton = document.createElement('button');
        addButton.textContent = 'Dodaj zlecenie';
        addButton.classList.add('btn');
        addButton.addEventListener('click', function() {
        });
        newAssignmentForm.appendChild(addButton);
        
        employeesContainer.appendChild(newAssignmentForm);
    }
    
    function showNewAssignmentForm() {
        const newAssignmentForm = document.getElementById('newAssignmentForm');
        if (newAssignmentForm) {
            newAssignmentForm.style.display = 'block';
        }
    }
    
    function hideNewAssignmentForm() {
        const newAssignmentForm = document.getElementById('newAssignmentForm');
        if (newAssignmentForm) {
            newAssignmentForm.style.display = 'none';
        }
    }




    function createCustomersComboBox() {
        const customers = [
            'Klient 1',
            'Klient 2',
            'Klient 3',
            'Klient 4',
            'Klient 5'
        ];
    
        const comboBox = document.createElement('select');
        comboBox.id = 'customersComboBox';
        customers.forEach(customer => {
            const option = document.createElement('option');
            option.value = customer;
            option.textContent = customer;
            comboBox.appendChild(option);
        });
        
        employeesContainer.appendChild(comboBox);
        comboBox.style.display = 'none';
    }
    function showCustomersComboBox() {
        const comboBox = document.getElementById('customersComboBox');
        if (comboBox) {
            comboBox.style.display = 'block';
        }
    }
    
    function hideCustomersComboBox() {
        const comboBox = document.getElementById('customersComboBox');
        if (comboBox) {
            comboBox.style.display = 'none';
        }
    }

    function createEmployeesComboBox() {
        const employees = [
            'id:1, Name:John Last name:Doe',
            'Jane Smith',
            'Michael Johnson',
            'Emily Williams',
            'David Brown'
        ];

        const comboBox = document.createElement('select');
        comboBox.id = 'employeesComboBox';
        employees.forEach(employee => {
            const option = document.createElement('option');
            option.value = employee;
            option.textContent = employee;
            comboBox.appendChild(option);
        });
        employeesContainer.appendChild(comboBox);
    }
    
    function hideEmployeesComboBox() {
        const comboBox = document.getElementById('employeesComboBox');
        if (comboBox) {
            comboBox.parentNode.removeChild(comboBox);
        }
    }

    function createPositionsComboBox() {
        const positions = [
            'Manager',
            'Developer',
            'Designer',
            'Accountant',
            'Sales Representative'
        ];

        const comboBox = document.createElement('select');
        comboBox.id = 'positionsComboBox';

        positions.forEach(position => {
            const option = document.createElement('option');
            option.value = position;
            option.textContent = position;
            comboBox.appendChild(option);
        });
        employeesContainer.appendChild(comboBox);
    }
    
    function hidePositionsComboBox() {
        const comboBox = document.getElementById('positionsComboBox');
        if (comboBox) {
            comboBox.parentNode.removeChild(comboBox);
        }
    }



    function createDynamicButtons() {
        const addEmployeeButton = document.createElement('button');
        addEmployeeButton.textContent = 'Dodaj pracownika';
        addEmployeeButton.classList.add('btn');
        addEmployeeButton.id = 'addEmployeeButton';
        addEmployeeButton.addEventListener('click', function() {
            hideMainButtons();
            hideDatabaseContainer();
            removeDynamicButtons();



            const inputFields = document.createElement('div');
            inputFields.id = 'inputFields';

            const employeesHeader = document.createElement('h2');
            employeesHeader.textContent = 'Podaj dane nowego pracownika:';
            employeesHeader.id = 'employeesHeader';
            inputFields.appendChild(employeesHeader);
        
            // Imię
            const nameInput = document.createElement('input');
            nameInput.type = 'text';
            nameInput.placeholder = 'Imię';
            inputFields.appendChild(nameInput);
        
            // Nazwisko
            const surnameInput = document.createElement('input');
            surnameInput.type = 'text';
            surnameInput.placeholder = 'Nazwisko';
            inputFields.appendChild(surnameInput);
        
            // Email
            const emailInput = document.createElement('input');
            emailInput.type = 'email';
            emailInput.placeholder = 'Email';
            inputFields.appendChild(emailInput);
        
            // Data urodzenia
            const dobInput = document.createElement('input');
            dobInput.type = 'date';
            dobInput.placeholder = 'Data urodzenia';
            inputFields.appendChild(dobInput);
        

            const submitButton = document.createElement('button');
            submitButton.textContent = 'Zatwierdź';
            submitButton.classList.add('btn');
            submitButton.addEventListener('click', function() {
                createDynamicButtons();
                showDatabaseContainer();
                createEmployeeRecords();
                cancelButton.parentNode.removeChild(cancelButton);
                inputFields.parentNode.removeChild(inputFields);

            });
            inputFields.appendChild(submitButton);





            const cancelButton = document.createElement('button');
            cancelButton.textContent = 'Powrót';
            cancelButton.classList.add('btn');
            cancelButton.id = 'cancelkButton';
            cancelButton.addEventListener('click', function() {
                createDynamicButtons();
                showDatabaseContainer();
                createEmployeeRecords();
                cancelButton.parentNode.removeChild(cancelButton);
                inputFields.parentNode.removeChild(inputFields);
            });
            buttonsContainer.appendChild(cancelButton);
         employeesContainer.insertBefore(inputFields, databaseContainer);
        });

        const deleteEmployeeButton = document.createElement('button');
        deleteEmployeeButton.textContent = 'Zwolnij pracownika';
        deleteEmployeeButton.classList.add('btn');
        deleteEmployeeButton.id = 'deleteEmployeeButton';
        deleteEmployeeButton.addEventListener('click', function() {
            console.log('usun pracownika');
        });

        const backButton = document.createElement('button');
        backButton.textContent = 'Powrót';
        backButton.classList.add('btn');
        backButton.id = 'backButton';
        backButton.addEventListener('click', function() {
            showMainButtons()
            employeesContainer.classList.remove('show');
            removeDynamicButtons();
            hideDatabaseContainer();
        });


        const buttonsContainer = document.getElementById('buttonsContainer');
        buttonsContainer.appendChild(addEmployeeButton);
        buttonsContainer.appendChild(deleteEmployeeButton);
        buttonsContainer.appendChild(backButton);
    }

    function removeDynamicButtons() {
        const addEmployeeButton = document.getElementById('addEmployeeButton');
        const deleteEmployeeButton = document.getElementById('deleteEmployeeButton');
        const backButton = document.getElementById('backButton');
        addEmployeeButton.parentNode.removeChild(addEmployeeButton);
        deleteEmployeeButton.parentNode.removeChild(deleteEmployeeButton);
        backButton.parentNode.removeChild(backButton);
    }

    function createEmployeeRecords() {
        if (!databaseContainer) {
            console.error('databaseContainer nie istnieje.');
            return;
        }
        const employeesData = [
            { id: 1, name: 'tu sie wyswietli lisa pracowników z bazy' },
            { id: 2, name: 'Jeszcze ma byc data ur, email, stanowisko i wynagrodzenie' },
            { id: 3, name: 'Piotr Wiśniewski' },
            { id: 4, name: 'Jan Kowalski' },
            { id: 5, name: 'Anna Nowak' },
            { id: 6, name: 'Piotr Wiśniewski' },
            { id: 7, name: 'Jan Kowalski' },
            { id: 8, name: 'Anna Nowak' },
            { id: 9, name: 'Piotr Wiśniewski' }
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
    function showDatabaseContainer() {
        if (!databaseContainer) {
            databaseContainer = document.createElement('div');
            databaseContainer.id = 'databaseContainer';
            const employeesHeader = document.createElement('h2');
            employeesHeader.textContent = 'Lista pracowników:';
            employeesHeader.id = 'employeesHeader';
            employeesContainer.appendChild(employeesHeader);
            employeesContainer.appendChild(databaseContainer);

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


});
