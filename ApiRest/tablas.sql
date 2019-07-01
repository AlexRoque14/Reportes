	CREATE PROCEDURE usersAddorEdit(
		IN _id INT,
        IN _name varchar(45),
        IN _email varchar(45),
		IN _password varchar(45)
    )
    
    BEGIN
		IF _id = 0 THEN
			INSERT INTO users(name, email, password)
            VALUES(_name, _email, _password);
			SET _id = LAST_INSERT_ID();
		ELSE
			UPDATE users
            SET 
				name = _name,
                email = _email,
                WHERE id = _id;
		END IF;
        
		SELECT _id AS id;
    END