INSERT INTO Campaign(id, code, name, description) VALUES (default, 'C1', 'Campaign 1', 'Description of Campaign 1');
INSERT INTO Campaign(id, code, name, description) VALUES (default, 'C2', 'Campaign 2', 'About Campaign 2');
INSERT INTO Campaign(id, code, name, description) VALUES (default, 'C3', 'Campaign 3', 'About Campaign 3');

INSERT INTO Task(id, name, due_date, description, campaign_id, status) VALUES (default, 'Task 1', '2030-01-12', 'Task 1 Description', 1, 0);
INSERT INTO Task(id, name, due_date, description, campaign_id, status) VALUES (default, 'Task 2', '2030-02-10', 'Task 2 Description', 1, 0);
INSERT INTO Task(id, name, due_date, description, campaign_id, status) VALUES (default, 'Task 3', '2030-03-16', 'Task 3 Description', 1, 0);
INSERT INTO Task(id, name, due_date, description, campaign_id, status) VALUES (default, 'Task 4', '2030-06-25', 'Task 4 Description', 2, 0);

