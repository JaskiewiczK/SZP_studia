class Calendar {
    constructor(input, options) {
        this.now = new Date();
        this.day = this.now.getDate();
        this.month = this.now.getMonth();
        this.year = this.now.getFullYear();
        this.input = input;
        this.divCnt = null;
        this.divTable = null;
        this.divDateText = null;
        this.divButtons = null;

        const defaultOptions = {
            closeOnSelect : false,
            onDateSelect : (day, month, year) => {
                const monthText = ((month + 1) < 10) ? "0" + (month + 1) : month + 1;
                const dayText =  (day < 10) ? "0" + day : day;
                this.input.value = `${dayText}-${monthText}-${this.year}`;
            }
        }
        this.options = Object.assign({}, defaultOptions, options);
    }

    //metoda tworząca przyciski prev-next
    createButtons() {
        const buttonPrev = document.createElement("button");
        buttonPrev.innerText = "<";
        buttonPrev.type = "button";
        buttonPrev.classList.add("input-prev");
        buttonPrev.addEventListener("click", e => {
            this.month--;
            if (this.month < 0) {
                this.month = 11;
                this.year--;
            }
            this.createCalendarTable();
            this.createDateText();
        });
        this.divButtons.appendChild(buttonPrev);

        const buttonNext = document.createElement("button");
        buttonNext.classList.add("input-next");
        buttonNext.innerText = ">";
        buttonNext.type = "button";
        buttonNext.addEventListener("click", e => {
            this.month++;
            if (this.month > 11) {
                this.month = 0;
                this.year++;
            }
            this.createCalendarTable();
            this.createDateText();
        });
        this.divButtons.appendChild(buttonNext);
    }

    //metoda wypisująca nazwę miesiąca i roku
    createDateText() {
        const monthNames = ["styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"];
        this.divDateText.innerHTML = monthNames[this.month] + " " + this.year;
    }

    //metoda tworząca tabele z kalendarzem
    createCalendarTable() {
        this.divTable.innerHTML = "";

        //tworzymy nazwy dni
        const tab = document.createElement("table");
        tab.classList.add("calendar-table");

        //tworzymy nagłówki dni
        let tr = document.createElement("tr");
        tr.classList.add("calendar-table-days-names");
        const days = ["Pon", "Wto", "Śro", "Czw", "Pią", "Sob", "Nie"];
        days.forEach(day => {
            const th = document.createElement("th");
            th.innerHTML = day;
            tr.appendChild(th);
        });
        tab.appendChild(tr);

        //pobieramy wszystkie dni danego miesiąca
        const daysInMonth = new Date(this.year, this.month+1, 0).getDate();

        //pobieramy pierwszy dzień miesiąca
        const tempDate = new Date(this.year, this.month, 1);
        let firstMonthDay = tempDate.getDay();
        if (firstMonthDay === 0) {
            firstMonthDay = 7;
        }

        //wszystkie komórki w tabeli
        const j = daysInMonth + firstMonthDay - 1;

        if (firstMonthDay - 1 !== 0) {
            tr = document.createElement("tr");
            tab.appendChild(tr);
        }

        //tworzymy puste komórki przed dniami miesiąca
        for (let i=0; i < firstMonthDay - 1; i++) {
            const td = document.createElement("td");
            td.innerHTML = "";
            tr.appendChild(td);
        }

        //tworzymy komórki dni
        for (let i = firstMonthDay-1; i<j; i++) {
            if(i % 7 === 0){
                tr = document.createElement("tr");
                tab.appendChild(tr);
            }

            const td = document.createElement("td");
            td.innerText = i - firstMonthDay + 2;
            td.dayNr = i - firstMonthDay + 2;
            td.classList.add("day");

            if (this.year === this.now.getFullYear() && this.month === this.now.getMonth() && this.day === i - firstMonthDay + 2) {
                td.classList.add("current-day")
            }

            tr.appendChild(td);
        }

        tab.appendChild(tr);

        this.divTable.appendChild(tab);
    }

    //podpinamy klik pod dni w tabeli kalendarza
    bindTableDaysEvent() {
        this.divTable.addEventListener("click", e => {
            if (e.target.tagName.toLowerCase() === "td" && e.target.classList.contains("day")) {
                if (this.options.closeOnSelect) {
                    this.hide();
                }
                this.options.onDateSelect(e.target.dayNr, this.month + 1, this.year);
            }
        });
    }

    //metoda ukrywa/pokazuje kalendarz
    toggleShow() {
        this.divCnt.classList.toggle("calendar-show");
    }

    //metoda pokazuje kalendarz
    show() {
        this.divCnt.classList.add("calendar-show");
    }

    //metoda ukrywa kalendarz
    hide() {
        this.divCnt.classList.remove("calendar-show");
    }

    //metoda inicjująca obiekt
    init() {
        //tworzymy div z całą zawartością
        this.divCnt = document.createElement("div");
        this.divCnt.classList.add("calendar");

        //tworzymy div z guzikami
        this.divButtons = document.createElement("div");
        this.divButtons.className = "calendar-prev-next";
        this.createButtons();

        //tworzymy div z nazwą miesiąca
        this.divDateText = document.createElement("div");
        this.divDateText.className = "date-name";
        this.createDateText();

        //tworzymy nagłówek kalendarza
        this.divHeader = document.createElement("div");
        this.divHeader.classList.add("calendar-header");

        this.divHeader.appendChild(this.divButtons);
        this.divHeader.appendChild(this.divDateText);
        this.divCnt.appendChild(this.divHeader);

        //tworzymy div z tabelą kalendarza
        this.divTable = document.createElement("div");
        this.divTable.className = "calendar-table-cnt";
        this.divCnt.appendChild(this.divTable);
        this.createCalendarTable();
        this.bindTableDaysEvent();

        //tworzymy wrapper dla input
        this.calendarWrapper = document.createElement("div");
        this.calendarWrapper.classList.add("input-calendar-cnt");
        this.input.parentElement.insertBefore(this.calendarWrapper, this.input);
        this.calendarWrapper.appendChild(this.input);
        this.calendarWrapper.appendChild(this.divCnt);

        this.input.classList.add("input-calendar");
        this.input.addEventListener("click", e => this.toggleShow());
        this.input.addEventListener("click", e => e.stopImmediatePropagation());
        this.divCnt.addEventListener("click", e => e.stopImmediatePropagation());
        document.addEventListener("click", e => this.hide());
    }
}