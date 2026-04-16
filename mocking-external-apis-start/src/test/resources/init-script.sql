CREATE TABLE campaign (
    id        BIGSERIAL    PRIMARY KEY,
    code      VARCHAR(255) UNIQUE NOT NULL,
    name      VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE task (
    id          BIGSERIAL    PRIMARY KEY,
    name        VARCHAR(255),
    description VARCHAR(255),
    due_date    DATE,
    status      INTEGER,                  -- 0=TO_DO, 1=IN_PROGRESS, 2=ON_HOLD, 3=DONE
    campaign_id BIGINT NOT NULL REFERENCES campaign(id)
);

INSERT INTO campaign (code, name, description) VALUES ('C1', 'Campaign 1', 'Description of Campaign 1');
INSERT INTO campaign (code, name, description) VALUES ('C2', 'Campaign 2', 'About Campaign 2');
INSERT INTO campaign (code, name, description) VALUES ('C3', 'Campaign 3', 'About Campaign 3');

INSERT INTO task (name, description, due_date, status, campaign_id) VALUES ('Task 1', 'Task 1 Description', '2030-01-12', 0, 1);
INSERT INTO task (name, description, due_date, status, campaign_id) VALUES ('Task 2', 'Task 2 Description', '2030-02-10', 0, 1);
INSERT INTO task (name, description, due_date, status, campaign_id) VALUES ('Task 3', 'Task 3 Description', '2030-03-16', 0, 1);
INSERT INTO task (name, description, due_date, status, campaign_id) VALUES ('Task 4', 'Task 4 Description', '2030-06-25', 0, 2);
