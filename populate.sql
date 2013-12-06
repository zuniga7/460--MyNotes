INSERT INTO MyNotesUser VALUES('bob@email.arizona.edu', 'Bob');
INSERT INTO MyNotesUser VALUES('jbright@email.arizona.edu', 'Jesse');
INSERT INTO MyNotesUser VALUES('beau.bright@gmail.com', 'Beau');
INSERT INTO MyNotesUser VALUES('pj@yahoo.com', 'Paula');
INSERT INTO MyNotesUser VALUES('arn91@yahoo.com', 'Tony');

INSERT INTO Creation VALUES('bob@email.arizona.edu', 3);
INSERT INTO Creation VALUES('jbright@email.arizona.edu', 4);
INSERT INTO Creation VALUES('beau.bright@gmail.com', 1);
INSERT INTO Creation VALUES('pj@yahoo.com', 2);
INSERT INTO Creation VALUES('arn91@yahoo.com', 5);
INSERT INTO Creation VALUES('pj@yahoo.com', 6);

INSERT INTO Board VALUES('Gardening', 1);
INSERT INTO Board VALUES('Movies', 2);
INSERT INTO Board VALUES('Research', 3);
INSERT INTO Board VALUES('Study', 4);
INSERT INTO Board VALUES('Teaching', 5);
INSERT INTO Board VALUES('Travel', 6);

INSERT INTO Subscribes VALUES('bob@email.arizona.edu', 'Movies');
INSERT INTO Subscribes VALUES('jbright@email.arizona.edu', 'Gardening');
INSERT INTO Subscribes VALUES('jbright@email.arizona.edu', 'Study');
INSERT INTO Subscribes VALUES('jbright@email.arizona.edu', 'Movies');
INSERT INTO Subscribes VALUES('pj@yahoo.com', 'Travel');

INSERT INTO Card VALUES('Gardening', 'Watering', 7, 'Water garden', 21, 12, 2013);
INSERT INTO Card VALUES('Movies', 'Watching', 8, 'Watch different movies', 14, 2, 2014);
INSERT INTO Card VALUES('Research', 'Learn math', 9, 'Try to learn math', 16, 5, 2015);
