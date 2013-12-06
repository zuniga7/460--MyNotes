CREATE TABLE MyNotesUser(
    UserEmail VARCHAR (120) NOT NULL,
    UserName VARCHAR (120) NOT NULL,
    PRIMARY KEY (UserEmail)
);

CREATE TABLE Creation(
    PRIMARY KEY (CreationID),
    FOREIGN KEY (UserEmail) REFERENCES MyNotesUser(UserEmail),
    UserEmail VARCHAR (120) NOT NULL,
    CreationID INTEGER NOT NULL
);

CREATE TABLE Board(
    PRIMARY KEY (BoardName),
    FOREIGN KEY (CreationID) REFERENCES Creation(CreationID),
    BoardName VARCHAR (120) NOT NULL,
    CreationID INTEGER NOT NULL
);

CREATE TABLE Subscribes(
    FOREIGN KEY (UserEmail) REFERENCES MyNotesUser(UserEmail),
    FOREIGN KEY (BoardName) REFERENCES Board(BoardName),
    PRIMARY KEY (UserEmail, BoardName),
    UserEmail VARCHAR (120) NOT NULL,
    BoardName VARCHAR (120) NOT NULL
);

CREATE TABLE Card(
    FOREIGN KEY (BoardName) REFERENCES Board(BoardName),
    FOREIGN KEY (CreationID) REFERENCES Creation(CreationID),
    PRIMARY KEY (BoardName, TaskName),
    BoardName VARCHAR (120) NOT NULL,
    TaskName VARCHAR (120) NOT NULL,
    CreationID INTEGER NOT NULL,
    Description VARCHAR (120) NOT NULL,
    DeadlineDay INTEGER NOT NULL,
    DeadlineMonth INTEGER NOT NULL,
    DeadlineYear INTEGER NOT NULL
);

CREATE TABLE AssignedTo(
    FOREIGN KEY (BoardName, TaskName) REFERENCES Card,
    FOREIGN KEY (UserEmail) REFERENCES MyNotesUser,
    PRIMARY KEY (BoardName, TaskName, UserEmail),
    BoardName VARCHAR (120) NOT NULL,
    TaskName VARCHAR (120) NOT NULL,
    UserEmail VARCHAR (120) NOT NULL
);
