CREATE TABLE contacts
(
    id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    phone VARCHAR(30) UNIQUE,
    email VARCHAR(254) UNIQUE

);
CREATE TABLE address
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    house_number SMALLINT    NOT NULL,
    street       VARCHAR(70) NOT NULL,
    city         VARCHAR(70) NOT NULL,
    county     VARCHAR(70) NOT NULL,
    post_code    VARCHAR(70) NOT NULL
);
CREATE TABLE arrival_time
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    check_in  VARCHAR(5) NOT NULL,
    check_out VARCHAR(5)
);



CREATE TABLE hotels
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(70)  NOT NULL,
    description     VARCHAR(350) NOT NULL,
    brand           VARCHAR(70)  NOT NULL,
    address_id      BIGINT NULL,
    contacts_id     BIGINT NULL,
    arrival_time_id BIGINT NULL,
    CONSTRAINT fk_hotels_address FOREIGN KEY (address_id) REFERENCES address (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_hotels_contacts FOREIGN KEY (contacts_id) REFERENCES contacts (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_hotels_arrival_time FOREIGN KEY (arrival_time_id) REFERENCES arrival_time (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE hotels_amenities (
                                 hotels_id BIGINT NOT NULL,
                                 amenities VARCHAR(255) NOT NULL,
                                 PRIMARY KEY (hotels_id, amenities),
                                 FOREIGN KEY (hotels_id) REFERENCES hotels(id) ON DELETE CASCADE
);