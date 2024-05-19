set search_path to szp;

create or replace function vacation_validation()
	returns trigger
	language plpgsql
	as $$
	begin
		
		if(new.ending - new.beginning < 0) then
			raise exception 'Vacation beginning date must not be after the ending date';
			return null;
		end if;
	
		return new;
	end
	$$;

create trigger vacation_validation
	before insert or update on szp.vacation 
	for each row
	execute procedure vacation_validation();

create or replace function employee_validation()
	returns trigger
	language plpgsql
	as $$
	begin
		
		if(LENGTH(new.pesel) != 11 or new.pesel !~ '^[0-9\.]+$') then
			raise exception 'Pesel in wrong format';
			return null;
		end if;
		
		new.first_name := initcap(new.first_name);	
		if(new.second_name is not null) then new.second_name := initcap(new.second_name);
		end if;
		new.last_name := initcap(new.last_name);
	
		return new;
	
	end
	$$;
	
create trigger employee_validation
	before insert or update on szp.employee 
	for each row
	execute procedure employee_validation();

create or replace function client_validation()
	returns trigger
	language plpgsql
	as $$
	begin
		
		if(LENGTH(new.phone_number) != 9 or new.phone_number !~ '^[0-9\.]+$') then
			raise exception 'Phone number in wrong format';
			return null;
		end if;
		
		if not (new.email like '%@%') then
			raise exception 'Email address in wrong format';
			return null;
		end if;
	
		new.first_name := initcap(new.first_name);	
		if(new.second_name is not null) then new.second_name := initcap(new.second_name);
		end if;
		new.last_name := initcap(new.last_name);
	
		return new;
	
	end
	$$;
	
create trigger client_validation
	before insert or update on szp.client 
	for each row
	execute procedure client_validation();




