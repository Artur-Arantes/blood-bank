CREATE TABLE users (
    id_usr BIGINT PRIMARY KEY AUTO_INCREMENT,
    log VARCHAR(50) NOT NULL,
    pwd VARCHAR(255) NOT NULL,
    act TINYINT(1) NOT NULL,
    role VARCHAR(50) DEFAULT 'USER'
);

CREATE TABLE permission (
    id_prm BIGINT PRIMARY KEY AUTO_INCREMENT,
    nam VARCHAR(255) NOT NULL
);

CREATE TABLE user_permission(
id_prm BIGINT NOT NULL,
id_usr BIGINT NOT NULL,
PRIMARY KEY(id_usr, id_prm),
FOREIGN KEY (id_usr) REFERENCES users(id_usr),
FOREIGN KEY (id_prm) REFERENCES permission(id_prm)
);


CREATE TABLE person (
    id_per BIGINT AUTO_INCREMENT PRIMARY KEY,
    nam VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    rg VARCHAR(20) NOT NULL UNIQUE,
    brt DATE NOT NULL,
    gnd VARCHAR(20) NOT NULL,
    mot VARCHAR(255),
    fat VARCHAR(255),
    eml VARCHAR(255)
);

CREATE TABLE medical_information (
    id_per BIGINT NOT NULL PRIMARY KEY,
    hgt DECIMAL(38,2) NOT NULL,
    wgt DECIMAL(38,2) NOT NULL,
    bld_typ VARCHAR(3) NOT NULL,
    CONSTRAINT fk_medical_information_person
        FOREIGN KEY (id_per) REFERENCES person(id_per) ON DELETE CASCADE
);

CREATE TABLE contact (
    id_per BIGINT NOT NULL PRIMARY KEY,
    hom_pho VARCHAR(15),
    cel VARCHAR(15),
    CONSTRAINT fk_contact_person
        FOREIGN KEY (id_per) REFERENCES person(id_per) ON DELETE CASCADE
);

CREATE TABLE address (
    id_per BIGINT NOT NULL PRIMARY KEY,
    zip_nmb VARCHAR(10) NOT NULL,
    adr VARCHAR(255) NOT NULL,
    nmb INT NOT NULL,
    ngh VARCHAR(255) NOT NULL,
    cit VARCHAR(100) NOT NULL,
    stt VARCHAR(2) NOT NULL,
    CONSTRAINT fk_address_person
        FOREIGN KEY (id_per) REFERENCES person(id_per) ON DELETE CASCADE
);

