DROP DATABASE IF EXISTS SRV_DB;
CREATE DATABASE SRV_DB;
USE SRV_DB;

DROP TABLE IF EXISTS MessageChangeHistory;
DROP TABLE IF EXISTS Message;
DROP TABLE IF EXISTS PartyLoginUser;
DROP TABLE IF EXISTS PartyChangeHistory;
DROP TABLE IF EXISTS Party;
DROP TABLE IF EXISTS LoginUser;

-- -------------- --
-- TABLE CREATION --
-- -------------- --
CREATE TABLE LoginUser(
LoginUserId INT NOT NULL AUTO_INCREMENT,
Email varchar(50) NOT NULL UNIQUE,
FirstName VARCHAR(50) NOT NULL,
LastName VARCHAR(50),
Password VARCHAR(50),
UserRole varchar(20),
DateCreated datetime,
DateModified datetime,
ModifiedBy int,
DateDeleted datetime,
Primary KEY(LoginUserId),
foreign key (ModifiedBy) references LoginUser(LoginUserId)
);

CREATE TABLE LoginUserChangeHistory(
LoginUserChangeHistoryID int not null auto_increment,
LoginUserid int not null,
Action varchar(50) not null,
ColumnChanged varchar(50) not null,
OldValue varchar(256),
NewValue varchar(256),
DateModified datetime,
ModifiedBy int,
primary key(LoginUserChangeHistoryID),
foreign key (LoginUserId) references LoginUser(LoginUserId),
foreign key (ModifiedBy) references LoginUser(LoginUserId)
);

CREATE TABLE Party(
PartyId INT NOT NULL AUTO_INCREMENT,
Name VARCHAR(50) not null unique,
AdminUserId int not null,
DateCreated datetime not null,
CreatedBy int not null,
DateModified datetime,
ModifiedBy int,
PRIMARY KEY(PartyId),
foreign key(AdminUserId) references LoginUser(LoginUserID),
foreign key(CreatedBy) references LoginUser(LoginUserID),
foreign key(ModifiedBy) references LoginUser(LoginUserID)
);

CREATE TABLE PartyChangeHistory(
PartyChangeHistoryID int not null auto_increment,
PartyId int not null,
Action varchar(50) not null,
ColumnChanged varchar(50) not null,
OldValue varchar(256),
NewValue varchar(256),
DateModified datetime,
ModifiedBy int,
primary key(PartyChangeHistoryID),
foreign key(ModifiedBy) references LoginUser(LoginUserId)
);

CREATE TABLE PartyLoginUser(
PartyId INT not null,
LoginUserId int not null,
DateCreated datetime not null,
CreatedBy int not null,
PRIMARY KEY(PartyId, LoginUserId),
FOREIGN KEY(PartyId) REFERENCES Party(PartyId),
foreign key(LoginUserId) references LoginUser(LoginUserId),
foreign key(CreatedBy) references LoginUser(LoginUserId)
);

CREATE TABLE PartyLoginUserDeleted(
PartyLoginUserDeletedId INT NOT NULL AUTO_INCREMENT,
PartyId INT not null,
LoginUserId int not null,
DateCreated datetime not null,
CreatedBy int not null,
DateDeleted datetime not null,
DeletedBy int not null,
PRIMARY KEY(PartyLoginUserDeletedId),
foreign key(LoginUserId) references LoginUser(LoginUserId),
foreign key(CreatedBy) references LoginUser(LoginUserId),
foreign key(DeletedBy) references LoginUser(LoginUserId)
);

CREATE TABLE Message(
MessageId INT NOT NULL auto_increment,
MessageText varchar(1000) NOT NULL,
ParentMessageId int,
PartyId int not null,
DateCreated datetime not null,
CreatedBy int not null,
DateModified datetime,
ModifiedBy int,
DateDeleted datetime,
DeletedBy int,
PRIMARY KEY(MessageId),
foreign key(ParentMessageId) references Message(MessageId),
FOREIGN KEY(PartyId) REFERENCES Party(PartyId),
foreign key(CreatedBy) references LoginUser(LoginUserId),
foreign key(ModifiedBy) references LoginUser(LoginUserId),
foreign key(DeletedBy) references LoginUser(LoginUserId)
);

CREATE TABLE MessageChangeHistory(
MessageChangeHistoryId int not null auto_increment,
MessageId int not null,
Action varchar(50) not null,
ColumnChanged varchar(50) not null,
OldValue varchar(256),
NewValue varchar(256),
DateModified datetime,
ModifiedBy int,
primary key(MessageChangeHistoryId),
foreign key(MessageId) references Message(MessageId),
foreign key(ModifiedBy) references LoginUser(LoginUserId)
);

-- ---------------- --
-- TRIGGER CREATION --
-- ---------------- --

delimiter $$
create trigger after_loginuser_insert
after insert
on LoginUser for each row
	begin
		INSERT INTO loginuserchangehistory(LoginUserId, Action, ColumnChanged, OldValue, NewValue, DateModified, ModifiedBy)
		VALUES(New.LoginuserId, "INSERT","Email", null, NEW.email, New.DateCreated, New.LoginUserId),
		(New.LoginuserId, "INSERT","FirstName", null, NEW.FirstName, New.DateCreated, New.LoginUserId),
		(New.LoginuserId, "INSERT","LastName", null, NEW.LastName, New.DateCreated, New.LoginUserId),
		(New.LoginuserId, "INSERT","UserRole", null, NEW.userrole, New.DateCreated, New.LoginUserId);
	end
$$

create trigger after_loginuser_update
after update
on LoginUser for each row
begin
	IF OLD.email <> new.email THEN
		INSERT INTO loginuserchangehistory(LoginUserId, Action, ColumnChanged, OldValue, NewValue, DateModified, ModifiedBy)
		VALUE(New.LoginuserId, "UPDATE","Email", OLD.email, NEW.email, New.datemodified, New.modifiedby);
	END IF;
    if old.firstname <> new.firstname then
		INSERT INTO loginuserchangehistory(LoginUserId, Action, ColumnChanged, OldValue, NewValue, DateModified, ModifiedBy)
        value(New.LoginuserId, "UPDATE","FirstName", old.firstname, NEW.FirstName, New.datemodified, New.modifiedby);
	end if;
    if old.lastname <> new.lastname then
		INSERT INTO loginuserchangehistory(LoginUserId, Action, ColumnChanged, OldValue, NewValue, DateModified, ModifiedBy)
        value(New.LoginuserId, "UPDATE","LastName", old.lastname, NEW.LastName, New.datemodified, New.modifiedby);
	end if;
    if old.userrole <> new.userrole then
		INSERT INTO loginuserchangehistory(LoginUserId, Action, ColumnChanged, OldValue, NewValue, DateModified, ModifiedBy)
        value(New.LoginuserId, "UPDATE","UserRole", old.userrole, NEW.userrole, New.datemodified, New.modifiedby);
	end if;
    if new.DateDeleted is not null then
		INSERT INTO loginuserchangehistory(LoginUserId, Action, ColumnChanged, OldValue, NewValue, DateModified, ModifiedBy)
        value(New.LoginuserId, "DATE DELETE", "DateDeleted", old.DateDeleted, NEW.DateDeleted, New.datemodified, New.modifiedby);
	end if;
end $$ 

create trigger after_party_insert
after insert
on Party for each row
	begin
		INSERT INTO partychangehistory(PartyId, Action, ColumnChanged, OldValue, NewValue, DateModified, ModifiedBy)
		VALUES(New.PartyId, "INSERT","name", null, NEW.name, New.DateCreated, New.createdby),
		(New.partyid, "INSERT","AdminUserId", null, NEW.AdminUserId, New.DateCreated, New.createdby);
	end
$$

create trigger after_party_update
after update
on Party for each row
begin
	IF OLD.name <> new.name THEN
		INSERT INTO partychangehistory(PartyId, Action, ColumnChanged, OldValue, NewValue, DateModified, ModifiedBy)
		VALUE(New.PartyId, "UPDATE","name", OLD.name, NEW.name, New.datemodified, New.modifiedby);
	END IF;
    if old.AdminUserId <> new.AdminUserId then
		INSERT INTO partychangehistory(PartyId, Action, ColumnChanged, OldValue, NewValue, DateModified, ModifiedBy)
        value(New.PartyId, "UPDATE","AdminUserId", old.adminUserId, NEW.AdminUserId, New.datemodified, New.modifiedby);
	end if;
end $$

CREATE TRIGGER before_party_delete
BEFORE DELETE
ON Party FOR EACH ROW
BEGIN
    INSERT INTO partychangehistory(PartyId, Action, ColumnChanged, OldValue, NewValue, DateModified, ModifiedBy)
	values (old.partyId, "DELETE","name", OLD.name, null, old.datemodified, old.modifiedby),
    (old.partyId, "DELETE","AdminUserId", OLD.AdminUserId, null, old.datemodified, old.modifiedby);
END$$   

create trigger after_message_insert
after insert
on Message for each row
	begin
		INSERT INTO messagechangehistory(MessageId, Action, ColumnChanged, OldValue, NewValue, DateModified, ModifiedBy)
		VALUES(New.MessageId, "INSERT","Text", null, NEW.MessageText, New.DateCreated, New.CreatedBy),
		(New.MessageId, "INSERT","ParentMessageId", null, NEW.ParentMessageId, New.DateCreated, New.CreatedBy),
		(New.MessageId, "INSERT","PartyId", null, NEW.PartyId, New.DateCreated, New.CreatedBy);
	end
$$

create trigger after_message_update
after update
on Message for each row
begin
	IF OLD.MessageText <> new.MessageText THEN
		INSERT INTO messagechangehistory(MessageId, Action, ColumnChanged, OldValue, NewValue, DateModified, ModifiedBy)
		VALUE(New.MessageId, "UPDATE","MessageText", OLD.MessageText, NEW.MessageText, New.datemodified, New.modifiedby);
	END IF;
    if new.DateDeleted is not null then
		INSERT INTO messagechangehistory(MessageId, Action, ColumnChanged, OldValue, NewValue, DateModified, ModifiedBy)
        value(New.MessageId, "DATE DELETE","DateDeleted", OLD.DateDeleted, NEW.DateDeleted, New.dateDeleted, New.DeletedBy);
	end if;
end $$ 

delimiter ;

-- ------------- --
-- DATA CREATION --
-- ------------- --

Use SRV_DB;

INSERT INTO loginuser (Email, FirstName, LastName, Password, UserRole, DateCreated)
VALUES 
("1", "Admin2", "AdminLastName2", "1", "ADMIN", current_timestamp()),
("2", "User", "UserLastName", "2", "USER", current_timestamp()),
("admin1@srv.ink", "Admin", "AdminLastName", "qwer1234!", "ADMIN", current_timestamp()),
("user2@srv.ink", "User2", "UserLastName2", "qwer1234!", "USER", current_timestamp()),
("user3@srv.ink", "User3", "UserLastName3", "qwer1234!", "USER", current_timestamp()),
("user4@srv.ink", "User4", "UserLastName4", "qwer1234!", "USER", current_timestamp()),
("admin2@srv.ink", "Admin2", "AdminLastName2", "qwer1234!", "ADMIN", current_timestamp());

INSERT INTO party
(Name, AdminUserId, DateCreated, CreatedBy)
VALUES ("Welcome", 1, current_timestamp(), 1),
	("Atlanta Trip", 2, current_timestamp(), 1),
	("MT group", 3, current_timestamp(), 3),
	("Family group", 4, current_timestamp(), 3);

insert into PartyLoginUser
(PartyId, LoginUserId, DateCreated, CreatedBy)
VALUES(1,1,current_timestamp(),1),
	(2,2,current_timestamp(),2),
	(1,2,current_timestamp(),3),
	(2,1,current_timestamp(),2),
	(1,3,current_timestamp(),2),
	(3,1,current_timestamp(),2),
	(2,3,current_timestamp(),2),
	(3,2,current_timestamp(),2);

insert into Message (MessageText, ParentMessageId, PartyId, DateCreated, CreatedBy)
VALUES ("this is the first message", null, 1, current_timestamp(), 1),
("this is an answer to the first message", 1, 1, current_timestamp(), 2),
("this is another answer to the first message", 1, 1, current_timestamp(), 3),
("this is a message three deep for the first message", 2, 1, current_timestamp(), 1),
("another conversation starter", null, 1, current_timestamp(), 1),
("lalalala", null, 2, current_timestamp(), 1),
("huhuhuhuhuhu", 6, 2, current_timestamp(), 1),
("wewewewewe", null, 3, current_timestamp(), 1),
("aksjfklajshfdlakjh", null, 4, current_timestamp(), 1),
("aSDSDS", null, 4, current_timestamp(), 3),
("DGERRSFA", null, 4, current_timestamp(), 4);
